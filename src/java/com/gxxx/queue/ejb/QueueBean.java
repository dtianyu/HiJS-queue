/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gxxx.queue.ejb;

import com.gxxx.comm.SuperEJB;
import com.gxxx.queue.entity.Queue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kevindong
 */
@Stateless
public class QueueBean extends SuperEJB<Queue> {
           
    @PersistenceContext(unitName = "HiJS-queuePU")
    private EntityManager em;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date basedate ;

    public QueueBean() {
        this.className = "Queue";
    }
    
    public int getSeq(Date date,String type) {
        Query query;
        query = em.createNamedQuery("Queue.getMaxSeqBySeqdateAndSeqtype");
        query.setParameter("seqdate", date);
        query.setParameter("seqtype", type);
        try {
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception e) {
            return 0;
        }
    }
       
}

