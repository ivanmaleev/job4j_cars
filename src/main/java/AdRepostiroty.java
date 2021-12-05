import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Advertisement;
import ru.job4j.model.CarBrand;

import java.time.LocalDate;
import java.util.List;

public class AdRepostiroty {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public List<Advertisement> findAdsLastDay() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "from Advertisement ad "
                        + "where ad.created between :startdate and :enddate");
        query.setParameter("startdate", LocalDate.now().minusDays(1));
        query.setParameter("enddate", LocalDate.now());
        List<Advertisement> ads = query.list();
        session.getTransaction().commit();
        session.close();
        return ads;
    }

    public List<Advertisement> findAdsWithPhoto() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "from Advertisement ad "
                        + "join fetch ad.car car "
                        + "where car.photoPath != '' and car.photoPath != null");
        List<Advertisement> ads = query.list();
        session.getTransaction().commit();
        session.close();
        return ads;
    }

    public List<Advertisement> findAdsByBrand(CarBrand brand) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "from Advertisement ad "
                        + "join fetch ad.car car "
                        + "where car.brand = :brand");
        query.setParameter("brand", brand);
        List<Advertisement> ads = query.list();
        session.getTransaction().commit();
        session.close();
        return ads;
    }
}
