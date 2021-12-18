package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Advertisement;
import ru.job4j.model.CarBrand;
import ru.job4j.model.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class AdRepostiroty implements Store {

    private static String storePath;

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private AdRepostiroty() {
    }

    private static final class Lazy {
        private static final Store INST = new AdRepostiroty();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T query(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static String getStorePath() {
        if (storePath == null) {
            Properties cfg = new Properties();
            try (BufferedReader io = new BufferedReader(
                    new InputStreamReader(
                            AdRepostiroty.class.getClassLoader()
                                    .getResourceAsStream("db.properties")
                    )
            )) {
                cfg.load(io);
            } catch (Exception e) {
                e.printStackTrace();
            }
            storePath = cfg.getProperty("storepath");
        }
        return storePath;
    }

    @Override
    public List<Advertisement> findAllUnsoldAds() {
        return query(session -> {
            Query<Advertisement> query = session.createQuery(
                    "from Advertisement ad "
                            + "join fetch ad.user "
                            + "where ad.sold = false "
                            + "order by ad.id");
            return query.list();
        });
    }

    @Override
    public List<Advertisement> findAllAdsByUserId(int userid) {
        return query(session -> {
            Query<Advertisement> query = session.createQuery(
                    "from Advertisement ad join fetch ad.user "
                            + "where ad.user.id = :userid "
                            + "order by ad.id");
            query.setParameter("userid", userid);
            return query.list();
        });
    }

    @Override
    public List<Advertisement> findAdsLastDay() {
        return query(session -> {
            Query<Advertisement> query = session.createQuery(
                    "from Advertisement ad left join fetch ad.user "
                            + "where ad.created between :startdate and :enddate "
                            + "and ad.sold = false "
                            + "order by ad.id");
            query.setParameter("startdate",
                    Timestamp.from(Instant.now().minus(1, ChronoUnit.DAYS)));
            query.setParameter("enddate", Timestamp.from(Instant.now()));
            return query.list();
        });
    }

    @Override
    public List<Advertisement> findAdsWithPhoto() {
        return query(session -> session.createQuery(
                "from Advertisement ad "
                        + "join fetch ad.car car "
                        + "where car.photoPath != '' "
                        + "and car.photoPath != null "
                        + "and ad.sold = false "
                        + "order by ad.id")
                .list());
    }

    @Override
    public List<Advertisement> findAdsByBrand(CarBrand brand) {
        return query(session -> session.createQuery(
                "from Advertisement ad "
                        + "join fetch ad.car car "
                        + "where car.carBrand = :brand "
                        + "and ad.sold = false "
                        + "order by ad.id")
                .list());
    }

    @Override
    public Advertisement findAdById(int id) {
        return query(session -> {
            Query<Advertisement> query = session.createQuery(
                    "from Advertisement ad "
                            + "join fetch ad.car car "
                            + "where ad.id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    public Advertisement saveAd(Advertisement advertisement) {
        if (advertisement.getId() == 0) {
            createAd(advertisement);
        } else {
            updateAd(advertisement);
        }
        return advertisement;
    }

    private Advertisement updateAd(Advertisement advertisement) {
        return query(session -> {
            session.update(advertisement);
            return advertisement;
        });
    }

    private Advertisement createAd(Advertisement advertisement) {
        return query(session -> {
            session.save(advertisement);
            return advertisement;
        });
    }

    @Override
    public User saveUser(User user) {
        return query(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public User findUserByEmail(String email) {
        return query(session -> {
            Query<User> query = session.createQuery(
                    "from User where email = : email");
            query.setParameter("email", email);
            return query.uniqueResult();
        });
    }

    @Override
    public User findUserById(int id) {
        return query(session -> {
            Query<User> query = session.createQuery(
                    "from User where id = : id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    @Override
    public CarBrand findCarBrandByName(String brand) {
        return query(session -> {
            Query<CarBrand> query = session.createQuery(
                    "from CarBrand where name = : name");
            query.setParameter("name", brand);
            return query.uniqueResult();
        });
    }

    @Override
    public CarBrand saveCarBrand(CarBrand carBrand) {
        return query(session -> {
            session.save(carBrand);
            return carBrand;
        });
    }
}
