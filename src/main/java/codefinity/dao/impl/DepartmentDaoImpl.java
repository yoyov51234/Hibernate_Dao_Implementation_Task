package codefinity.dao.impl;

import codefinity.dao.DepartmentDao;
import codefinity.model.Department;
import codefinity.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DepartmentDaoImpl implements DepartmentDao {


    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();


    @Override
    public Department add(Department department) {

        Session currentSession =null ;
        Transaction transaction =null;
        try {
            currentSession = sessionFactory.openSession();
            transaction = currentSession.beginTransaction();

            currentSession.persist(department);// 自动生成了id,然后自动协会了deparment对象
            transaction.commit();

        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Cannot add new department",ex);
            }
        } finally {
            if (currentSession != null) {
                currentSession.close();
            }
        }

        return department;
    }

    @Override
    public Department getById(int id) {


        try (Session session = sessionFactory.openSession()) {
            return session.get(Department.class, id);

        } catch (Exception ex) {
            throw new RuntimeException("Cannot get department by id" + id, ex);
        }




    }
}
