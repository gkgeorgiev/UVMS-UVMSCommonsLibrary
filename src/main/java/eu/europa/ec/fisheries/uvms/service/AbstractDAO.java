package eu.europa.ec.fisheries.uvms.service;

import eu.europa.ec.fisheries.uvms.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is responsible for all application level database interaction.
 * It provides unified apis for all basic CRUD operations like Create, Read, Update, Delete.
 */
public abstract class AbstractDAO<T> implements DAO<T> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDAO.class);

    @Override
    public T createEntity(final T entity) throws ServiceException {
        try {
            LOG.debug("Persisting entity : " + entity.getClass().getSimpleName());
            getEntityManager().persist(entity);
        } catch (Exception e) {
            LOG.error("Error occurred during Persisting entity : " + entity.getClass().getSimpleName());
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return entity;
    }

    @Override
    public T updateEntity(final T entity) throws ServiceException {
        try {
            LOG.debug("Updating entity : " + entity.getClass().getSimpleName());
            getEntityManager().merge(entity);
        } catch (Exception e) {
            LOG.error("Error occurred during updating entity : " + entity.getClass().getSimpleName());
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return entity;
    }

    @Override
    public T findEntityById(final Class<T> entityClass, final Object id) throws ServiceException {
        T obj;
        try {
            LOG.debug("Finding entity : " + entityClass.getSimpleName() + " with ID : " + id.toString());
            obj = getEntityManager().find(entityClass, id);
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity : " + entityClass.getSimpleName() + " with ID : " + id.toString());
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEntityByNativeQuery(String nativeQuery) throws ServiceException {
        List<T> objectList;
        try {
            LOG.debug("Finding entity by native query : " + nativeQuery);
            objectList = getEntityManager().createNativeQuery(nativeQuery).getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity by native query");
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEntityByNativeQuery(String nativeQuery, Map<String, String> parameters) throws ServiceException {
        List<T> objectList;
        try {
            LOG.debug("Finding entity by native query : " + nativeQuery);
            Query query = getEntityManager().createNativeQuery(nativeQuery);
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            objectList = query.getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity by native query");
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    public List<T> findEntityByHqlQuery(final Class<T> entityClass, final String hqlQuery) throws ServiceException {
        List<T> objectList;
        try {
            LOG.debug("Finding entity for query : " + hqlQuery);
            objectList = getEntityManager().createQuery(hqlQuery, entityClass).getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity for query : " + hqlQuery);
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEntityByHqlQuery(final Class<T> entityClass, final String hqlQuery, final Map<Integer, String> parameters) throws ServiceException {
        List objectList;
        try {
            LOG.debug("Finding entity for query : " + hqlQuery);
            Set<Map.Entry<Integer, String>> rawParameters = parameters.entrySet();
            Query query = getEntityManager().createQuery(hqlQuery, entityClass);
            for (Map.Entry<Integer, String> entry : rawParameters) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            objectList = query.getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity for query : " + hqlQuery);
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEntityByHqlQuery(final Class<T> entityClass, final String hqlQuery, final Map<Integer, String> parameters, final int maxResultLimit) throws ServiceException {
        List objectList;
        try {
            LOG.debug("Finding entity for query : " + hqlQuery);
            Set<Map.Entry<Integer, String>> rawParameters = parameters.entrySet();
            Query query = getEntityManager().createQuery(hqlQuery, entityClass);
            for (Map.Entry<Integer, String> entry : rawParameters) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            if (maxResultLimit > 0) {
                query.setMaxResults(maxResultLimit);
            }
            objectList = query.getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity for query : " + hqlQuery);
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEntityByNamedQuery(final Class<T> entityClass, final String queryName) throws ServiceException {
        List objectList;
        try {
            LOG.debug("Finding entity for query : " + queryName);
            TypedQuery<T> query = getEntityManager().createNamedQuery(queryName, entityClass);
            objectList = query.getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity for query : {}", queryName);
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEntityByNamedQuery(Class<T> entityClass, String queryName, Map<String, String> parameters) throws ServiceException {
        List objectList;
        try {
            LOG.debug("Finding entity for query : " + queryName);
            TypedQuery<T> query = getEntityManager().createNamedQuery(queryName, entityClass);
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            objectList = query.getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity for query : {}", queryName);
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findEntityByNamedQuery(Class<T> entityClass, String queryName, Map<String, String> parameters, int maxResultLimit) throws ServiceException {
        List objectList;
        try {
            LOG.debug("Finding entity for query : " + queryName);
            TypedQuery<T> query = getEntityManager().createNamedQuery(queryName, entityClass);
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            if (maxResultLimit > 0) {
                query.setMaxResults(maxResultLimit);
            }
            objectList = query.getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred during finding entity for query : {}", queryName);
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAllEntity(final Class<T> entityClass) throws ServiceException {
        List objectList;
        try {
            LOG.debug("Finding all entity list for : " + entityClass.getSimpleName());
            objectList = getEntityManager().createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        } catch (Exception e) {
            LOG.error("Error occurred while finding all entity list for : " + entityClass.getSimpleName());
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
        return objectList;
    }

    @Override
    public void deleteEntity(final T entity, final Object id) throws ServiceException {
        try {
            LOG.debug("Deleting entity : " + entity.getClass().getSimpleName());
            Object ref = getEntityManager().getReference(entity.getClass(), id);
            getEntityManager().remove(ref);
        } catch (Exception e) {
            LOG.error("Error occurred during deleting entity : " + entity.getClass().getSimpleName());
            LOG.error("Exception cause: ", e.getCause());
            throw new ServiceException("");
        }
    }

    public abstract EntityManager getEntityManager();

}