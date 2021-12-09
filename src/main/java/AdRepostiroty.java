import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Advertisement;
import ru.job4j.model.CarBrand;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class AdRepostiroty {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

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
