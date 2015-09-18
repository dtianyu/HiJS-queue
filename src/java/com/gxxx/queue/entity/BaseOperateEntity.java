/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gxxx.queue.entity;

import com.gxxx.comm.Lib;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kevindong
 */
@MappedSuperclass
public abstract class BaseOperateEntity extends BaseEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "status")
    protected String status;
    @Size(max = 20)
    @Column(name = "creator")
    protected String creator;
    @Column(name = "credate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date credate;
    @Size(max = 20)
    @Column(name = "optuser")
    protected String optuser;
    @Column(name = "optdate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date optdate;
    @Size(max = 20)
    @Column(name = "cfmuser")
    protected String cfmuser;
    @Column(name = "cfmdate")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date cfmdate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCredate() {
        return credate;
    }

    public void setCredate(Date credate) {
        this.credate = credate;
    }

    public String getOptuser() {
        return optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser;
    }

    public Date getOptdate() {
        return optdate;
    }

    public void setOptdate(Date optdate) {
        this.optdate = optdate;
    }

    public String getCfmuser() {
        return cfmuser;
    }

    public void setCfmuser(String cfmuser) {
        this.cfmuser = cfmuser;
    }

    public Date getCfmdate() {
        return cfmdate;
    }

    public void setCfmdate(Date cfmdate) {
        this.cfmdate = cfmdate;
    }
    
    public void setStatusToNew(){
        this.setStatus("N");
    }
    
    public void setStatusToModify(){
        this.setStatus("M");
    }
    
    public void setCreatorToSystem(){
        this.setCreator("system");
    }
    
    public void setCredateToNow(){
        this.setCredate(Lib.getDate());
    }
    
    public void setOptuserToSystem(){
        this.setOptuser("system");
    }
    
    public void setOptdateToNow(){
        this.setOptdate(Lib.getDate());
    }
            

}
