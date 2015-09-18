/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gxxx.queue.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevindong
 */
@Entity
@Table(name = "queue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Queue.getMaxSeqBySeqdateAndSeqtype", query = "SELECT MAX(q.seq) FROM Queue q WHERE q.seqdate = :seqdate and q.seqtype = :seqtype "),
    @NamedQuery(name = "Queue.getRowCount", query = "SELECT COUNT(q) FROM Queue q"),
    @NamedQuery(name = "Queue.findAll", query = "SELECT q FROM Queue q"),
    @NamedQuery(name = "Queue.findById", query = "SELECT q FROM Queue q WHERE q.id = :id"),
    @NamedQuery(name = "Queue.findBySeq", query = "SELECT q FROM Queue q WHERE q.seq = :seq"),
    @NamedQuery(name = "Queue.findByMobile", query = "SELECT q FROM Queue q WHERE q.mobile = :mobile"),
    @NamedQuery(name = "Queue.findByTableId", query = "SELECT q FROM Queue q WHERE q.tableid = :tableid"),
    @NamedQuery(name = "Queue.findByRemark", query = "SELECT q FROM Queue q WHERE q.remark = :remark"),
    @NamedQuery(name = "Queue.findByStatus", query = "SELECT q FROM Queue q WHERE q.status = :status")})
public class Queue extends BaseOperateEntity {

    @Basic(optional = false)
    @NotNull
    @Column(name = "seq")
    private int seq;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seqdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date seqdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "seqtype")
    private String seqtype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cnt")
    private int cnt;
    @Size(max = 20)
    @Column(name = "mobile")
    private String mobile;
    @Size(max = 10)
    @Column(name = "tableid")
    private String tableid;
    @Size(max = 200)
    @Column(name = "remark")
    private String remark;

    public Queue() {
    }

    public Queue(int seq, String status) {
        this.seq = seq;
        this.status = status;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Queue)) {
            return false;
        }
        Queue other = (Queue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gxxx.queue.entity.Queue[ id=" + id + " ]";
    }

    /**
     * @return the cnt
     */
    public int getCnt() {
        return cnt;
    }

    /**
     * @param cnt the cnt to set
     */
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    /**
     * @return the seqdate
     */
    public Date getSeqdate() {
        return seqdate;
    }

    /**
     * @param seqdate the seqdate to set
     */
    public void setSeqdate(Date seqdate) {
        this.seqdate = seqdate;
    }

    /**
     * @return the seqtype
     */
    public String getSeqtype() {
        return seqtype;
    }

    /**
     * @param seqtype the seqtype to set
     */
    public void setSeqtype(String seqtype) {
        this.seqtype = seqtype;
    }

}
