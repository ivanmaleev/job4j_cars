import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Advertisement;
import ru.job4j.model.CarBrand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class AdRepostiroty {

    private static final AdRepostiroty INSTANCE = new AdRepostiroty();

    private static final Logger LOG = LogManager.getLogger(AdRepostiroty.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private AdRepostiroty() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        AdRepostiroty.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error("Invalid parameters", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOG.error("Driver not loaded", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final AdRepostiroty INST = new AdRepostiroty();
    }

    public static AdRepostiroty instOf() {
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

    public List<Advertisement> findAdsLastDay() {
        return query(session -> {
            Query<Advertisement> query = session.createQuery(
                    "from Advertisement ad "
                            + "where ad.created between :startdate and :enddate");
            query.setParameter("startdate", LocalDate.now().minusDays(1));
            query.setParameter("enddate", LocalDate.now());
            return query.list();
        });
    }

    public List<Advertisement> findAdsWithPhoto() {
        return query(session -> session.createQuery(
                "from Advertisement ad "
                        + "join fetch ad.car car "
                        + "where car.photoPath != '' "
                        + "and car.photoPath != null")
                .list());
    }

    public List<Advertisement> findAdsByBrand(CarBrand brand) {
        return query(session -> session.createQuery(
                "from Advertisement ad "
                        + "join fetch ad.car car "
                        + "where car.brand = :brand")
                .list());
    }
}
