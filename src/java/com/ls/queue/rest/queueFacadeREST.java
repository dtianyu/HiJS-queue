/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ls.queue.rest;

import com.gprinter.command.EscCommand.ENABLE;
import com.gprinter.command.EscCommand.FONT;
import com.gprinter.command.EscCommand.JUSTIFICATION;
import com.ls.queue.entity.queue;
import com.ls.queue.print.PrintBean;
import java.util.List;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author kevindong
 */
@Stateless
@DependsOn("PrintBean")
@Path("queue")
public class queueFacadeREST extends AbstractFacade<queue> {

    @PersistenceContext(unitName = "HiJS-queuePU")
    private EntityManager em;

    @EJB
    private PrintBean printBean;

    public queueFacadeREST() {
        super(queue.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(queue entity) {
        
        super.create(entity);
        //开始打印
        if (printBean.getPrinter() == null) {
            printBean.init();
        }
        printBean.open("192.168.0.99", 9100);
        printBean.getEsc().addInitializePrinter();
        printBean.getEsc().addPrintAndFeedLines((byte) 1);
        //设置打印居中
        printBean.getEsc().addSelectJustification(JUSTIFICATION.CENTER);
        //设置为倍高倍宽
        printBean.getEsc().addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);
        //打印店铺名称
        printBean.getEsc().addText("某某餐饮" + "\n");
        printBean.getEsc().addPrintAndLineFeed();

        printBean.getEsc().addPrintAndFeedLines((byte) 1);
        //打印排队号码
        printBean.getEsc().addText(String.valueOf(entity.getId()) + "\n");
        /*打印文字*/
        printBean.getEsc().addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);//取消倍高倍宽
        //设置打印左对齐
        //printBean.getEsc().addSelectJustification(JUSTIFICATION.LEFT);
        printBean.getEsc().addPrintAndLineFeed();
        printBean.getEsc().addText("你前面还有 9" + " 人等待!\n");

        //printBean.getEsc().addSelectJustification(JUSTIFICATION.CENTER);
        printBean.getEsc().addPrintAndLineFeed();
        //打印结束
        printBean.getEsc().addText("请您稍等片刻,我们竭诚为您服务!\r\n");
        printBean.getEsc().addPrintAndFeedLines((byte) 4);

        printBean.print();

    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Integer id, queue entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public queue find(@PathParam("id") Integer id) {       
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<queue> findAll() {
        return super.findAll();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
