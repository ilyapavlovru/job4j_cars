package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

public class AdRepository implements Store, AutoCloseable {
    private static final AdRepository INST = new AdRepository();
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

    @Override
    public Collection<Adv> findAllAds() {
        return tx(
                session -> session.createQuery(
                        "from ru.job4j.cars.model.Adv").list()
        );
    }

    @Override
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

    @Override
    public Collection<Adv> findAdsWithPhoto() {
        return tx(
                session -> {
                    return session.createQuery(
                            "FROM Adv AS a WHERE a.image is not null")
                            .list();
                }
        );
    }

    @Override
    public Collection<Adv> findAllActiveAds() {
        return tx(
                session -> {
                    return session.createQuery(
                            "FROM Adv AS a WHERE a.status = 'Продается'")
                            .list();
                }
        );
    }

    @Override
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
    public Collection<Adv> findAllAdsByUserId(int userId) {
        return tx(
                session -> {
                    return session.createQuery(
                            "FROM Adv AS a WHERE a.user.id = :userId")
                            .setParameter("userId", userId)
                            .list();
                }
        );
    }

    @Override
    public Adv findAdvById(int id) {
        return tx(
                session -> session.get(Adv.class, id)
        );
    }

    @Override
    public Collection<CarBrand> findAllCarBrands() {
        return tx(
                session -> session.createQuery(
                        "from ru.job4j.cars.model.CarBrand").list()
        );
    }

    @Override
    public Collection<CarBodyType> findAllCarBodyTypes() {
        return tx(
                session -> session.createQuery(
                        "from ru.job4j.cars.model.CarBodyType").list()
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return tx(
                session -> {
                    Query query = session.createQuery("from ru.job4j.cars.model.User where email = :email");
                    query.setParameter("email", email);
                    return (User) query.uniqueResult();
                }
        );
    }

    @Override
    public User findUserByPhone(String phone) {
        return tx(
                session -> {
                    Query query = session.createQuery("from ru.job4j.cars.model.User where phone = :phone");
                    query.setParameter("phone", phone);
                    return (User) query.uniqueResult();
                }
        );
    }

    @Override
    public User addUser(User user) {
        return tx(
                session -> {
                    session.save(user);
                    return user;
                }
        );
    }

    @Override
    public Role findRoleByName(String name) {
        return tx(
                session -> {
                    Query query = session.createQuery("from ru.job4j.cars.model.Role where name = :name");
                    query.setParameter("name", name);
                    return (Role) query.uniqueResult();
                }
        );
    }

    @Override
    public Role addRole(Role role) {
        return tx(
                session -> {
                    session.save(role);
                    return role;
                }
        );
    }

    @Override
    public CarBrand findCarBrandById(Integer carBrandId) {
        return tx(
                session -> session.get(CarBrand.class, carBrandId)
        );
    }

    @Override
    public CarBodyType findCarBodyTypeById(Integer carBodyTypeId) {
        return tx(
                session -> session.get(CarBodyType.class, carBodyTypeId)
        );
    }

    @Override
    public Adv saveAdv(Adv adv) {
        return tx(
                session -> {
                    session.save(adv);
                    return adv;
                }
        );
    }

    @Override
    public Adv updateAdv(Adv adv) {
        return tx(
                session -> {
                    session.update(adv);
                    return adv;
                }
        );
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
