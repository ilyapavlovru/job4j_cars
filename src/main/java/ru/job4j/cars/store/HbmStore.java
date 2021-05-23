package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.cars.model.Adv;
import ru.job4j.cars.model.Role;
import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.function.Function;

public class HbmStore implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

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
//
//    @Override
//    public Collection<Category> findAllCategories() {
//        return tx(
//                session -> session.createQuery("from ru.job4j.todo.model.Category").list()
//        );
//    }
//
//    @Override
//    public Item addItem(Item item, String[] ids) {
//        return tx(
//                session -> {
//                    for (String id : ids) {
//                        Category category = session.find(Category.class, Integer.parseInt(id));
//                        item.addCategory(category);
//                    }
//                    session.save(item);
//                    return item;
//                }
//        );
//    }
//
//    @Override
//    public Item findItemById(int id) {
//        return tx(
//                session -> session.get(Item.class, id)
//        );
//    }
//
//    @Override
//    public boolean replaceItem(Item item) {
//        return tx(
//                session -> {
//                    session.update(item);
//                    return true;
//                }
//        );
//    }

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
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
