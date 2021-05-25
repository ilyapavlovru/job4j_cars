package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Adv;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

public class AdRepository implements AutoCloseable {
    public static final AdRepository INST = new AdRepository();
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private AdRepository() {
    }

    public static AdRepository instOf() {
        return INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Collection<Adv> findTodayAds() {
        return tx(
                session -> {
                    LocalDateTime startDateTime = LocalDateTime.now().toLocalDate().atTime(0, 0);
                    Date date = java.sql.Timestamp.valueOf(startDateTime);
                    return session.createQuery(
                            "FROM Adv AS a WHERE a.created > :stDate")
                            .setParameter("stDate", date)
                            .list();
                }
        );
    }

    public Collection<Adv> findAdsWithPhoto() {
        return tx(
                session -> {
                    return session.createQuery(
                            "FROM Adv AS a WHERE a.image is not null")
                            .list();
                }
        );
    }

    public Collection<Adv> findAdsByCarBrandId(int carBrandId) {
        return tx(
                session -> {
                    return session.createQuery(
                            "FROM Adv AS a WHERE a.carBrand.id = :carBrandId")
                            .setParameter("carBrandId", carBrandId)
                            .list();
                }
        );
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
