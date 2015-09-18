/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gxxx.comm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author C0160
 * @param <T>
 */
public abstract class SuperEJB<T> implements Serializable {

    @PersistenceContext(unitName = "HiJS-queuePU")
    protected EntityManager em;

    protected String className;

    public JsonArrayBuilder createJsonArrayBuilder(List<T> entityList) {
        if (entityList != null) {
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (T entity : entityList) {
                jab.add(createJsonObjectBuilder(entity));
            }
            return jab;
        } else {
            return null;
        }
    }

    public JsonObjectBuilder createJsonObjectBuilder(T entity) {
        return null;
    }

    public void delete(T entity) {
        try {
            if (em.contains(entity)) {
                em.remove(entity);
            } else {
                em.remove(em.merge(entity));
            }
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    public void persist(T entity) {
        em.persist(entity);
    }
    
    public EntityManager getEntityManager(){
        return em;
    }

    public int getRowCount() {
        Query query = em.createNamedQuery(getClassName() + ".getRowCount");
        if (query.getSingleResult() == null) {
            return 0;
        } else {
            return Integer.parseInt(query.getSingleResult().toString());
        }
    }

    public int getRowCount(Map<String, Object> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(e) FROM ");
        sb.append(this.className);
        sb.append(" e WHERE 1=1 ");
        if (filters != null) {
            for (Map.Entry<String, Object> e : filters.entrySet()) {
                if (e.getValue().getClass() == String.class) {
                    sb.append(" and e.").append(e.getKey()).append(" like :").append(e.getKey());
                } else {
                    sb.append(" and e.").append(e.getKey()).append(" = :").append(e.getKey());
                }
            }
        }
        Query query = em.createQuery(sb.toString());
        if (filters != null) {
            for (Map.Entry<String, Object> e : filters.entrySet()) {
                if (e.getValue().getClass() == String.class) {
                    query.setParameter(e.getKey(), "%" + e.getValue() + "%");
                } else {
                    query.setParameter(e.getKey(), e.getValue());
                }
            }
        }
        return Integer.parseInt(query.getSingleResult().toString());
    }

    public int getRowCountByUserId(int id) {
        Query query = em.createNamedQuery(getClassName() + ".getRowCountByUserId");
        query.setParameter("userid", id);
        if (query.getSingleResult() == null) {
            return 0;
        } else {
            return Integer.parseInt(query.getSingleResult().toString());
        }
    }

    public List<T> findAll() {
        Query query = em.createNamedQuery(getClassName() + ".findAll");
        return query.getResultList();
    }
      
    public List<T> findAll(Map<String, Object> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM ");
        sb.append(this.className);
        sb.append(" e WHERE 1=1 ");
        if (filters != null) {
            for (Map.Entry<String, Object> e : filters.entrySet()) {
                if (e.getValue().getClass() == String.class) {
                    sb.append(" and e.").append(e.getKey()).append(" like :").append(e.getKey());
                } else {
                    sb.append(" and e.").append(e.getKey()).append(" = :").append(e.getKey());
                }
            }
        }
        Query query = em.createQuery(sb.toString());
        if (filters != null) {
            for (Map.Entry<String, Object> e : filters.entrySet()) {
                if (e.getValue().getClass() == String.class) {
                    query.setParameter(e.getKey(), "%" + e.getValue() + "%");
                } else {
                    query.setParameter(e.getKey(), e.getValue());
                }
            }
        }
        return query.getResultList();
    }

    public List<T> findAll(int first, int pageSize) {
        Query query = em.createNamedQuery(getClassName() + ".findAll").setFirstResult(first).setMaxResults(first + pageSize);
        return query.getResultList();
    }

    public List<T> findAll(int first, int pageSize, Map<String, Object> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM ");
        sb.append(this.className);
        sb.append(" e WHERE 1=1 ");
        if (filters != null) {
            for (Map.Entry<String, Object> e : filters.entrySet()) {
                if (e.getValue().getClass() == String.class) {
                    sb.append(" and e.").append(e.getKey()).append(" like :").append(e.getKey());
                } else {
                    sb.append(" and e.").append(e.getKey()).append(" = :").append(e.getKey());
                }
            }
        }
        Query query = em.createQuery(sb.toString()).setFirstResult(first).setMaxResults(first + pageSize);
        if (filters != null) {
            for (Map.Entry<String, Object> e : filters.entrySet()) {
                if (e.getValue().getClass() == String.class) {
                    query.setParameter(e.getKey(), "%" + e.getValue() + "%");
                } else {
                    query.setParameter(e.getKey(), e.getValue());
                }
            }
        }
        return query.getResultList();
    }

    public T findById(int value) {
        Query query = em.createNamedQuery(getClassName() + ".findById");
        query.setParameter("id", value);
        try {
            Object entity = query.getSingleResult();
            if (entity != null) {
                return (T) entity;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<T> findByStatus(String status) {
        Query query = em.createNamedQuery(getClassName() + ".findByStatus");
        query.setParameter("status", status);
        return query.getResultList();
    }

    public List<T> findByStatus(String status, int first, int pageSize) {
        Query query = em.createNamedQuery(getClassName() + ".findByStatus").setFirstResult(first).setMaxResults(first + pageSize);
        query.setParameter("status", status);
        return query.getResultList();
    }

    public List<T> findByUserId(int id) {
        Query query;
        query = em.createNamedQuery(getClassName() + ".findByUserId");
        query.setParameter("userid", id);
        return query.getResultList();
    }

    public List<T> findByUserId(int id, int first, int pageSize) {
        Query query;
        query = em.createNamedQuery(getClassName() + ".findByUserId").setFirstResult(first).setMaxResults(pageSize);
        query.setParameter("userid", id);
        return query.getResultList();
    }

    public T getNextById(String value) {
        Query query = em.createNamedQuery(className + ".findNextById").setFirstResult(0).setMaxResults(1);
        query.setParameter("id", Integer.parseInt(value));
        List<T> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public T getPrevById(String value) {
        Query query = em.createNamedQuery(className + ".findPrevById").setFirstResult(0).setMaxResults(1);
        query.setParameter("id", Integer.parseInt(value));
        List<T> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public T update(T entity) {
        try {
            T e = em.merge(entity);
            return e;
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return this.className;
    }

}
