/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gxxx.queue.rest;

import com.gprinter.command.EscCommand;
import com.gxxx.queue.entity.Queue;
import com.gxxx.queue.ejb.PrintBean;
import com.gxxx.queue.ejb.QueueBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 *
 * @author kevindong
 */
@Stateless
@Path("queue")
public class QueueFacadeREST extends AbstractFacade<Queue> {

    @PersistenceContext(unitName = "HiJS-queuePU")
    private EntityManager em;

    @EJB
    private PrintBean printBean;
    @EJB
    private QueueBean queueBean;

    private String company;
    private String printerIp;
    private int printerPort;
    private Date date;
    private String type;
    private Date baseDate;
    private String simpleDate;
    private String breakfastBegin;
    private String breakfastEnd;
    private String lunchBegin;
    private String lunchEnd;
    private String dinnerBegin;
    private String dinnerEnd;

    public QueueFacadeREST() {
        super(Queue.class);
    }

    @Context
    public void getServletParams(ServletContext context) {
        company = context.getInitParameter("company");
        printerIp = context.getInitParameter("printerIp");
        printerPort = Integer.parseInt(context.getInitParameter("printerPort"));
        breakfastBegin = context.getInitParameter("breakfastBegin");
        breakfastEnd = context.getInitParameter("breakfastEnd");
        lunchBegin = context.getInitParameter("lunchBegin");
        lunchEnd = context.getInitParameter("lunchEnd");
        dinnerBegin = context.getInitParameter("dinnerBegin");
        dinnerEnd = context.getInitParameter("dinnerEnd");
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Queue entity) {
        try {
            this.setDate();
            this.setType();
        } catch (ParseException ex) {
            Logger.getLogger(QueueFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = queueBean.getSeq(this.date, this.type);
        entity.setSeq(i + 1);
        entity.setSeqdate(this.date);
        entity.setSeqtype(this.type);
        entity.setCreatorToSystem();
        entity.setCredateToNow();
        //更新数据
        super.create(entity);
        //开始打印
        if (printBean.getPrinter() == null) {
            printBean.init();
        }
        printBean.open(printerIp, printerPort);
        printBean.getEsc().addInitializePrinter();
        printBean.getEsc().addPrintAndFeedLines((byte) 1);
        //设置打印居中
        printBean.getEsc().addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        //设置为倍高倍宽
        printBean.getEsc().addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
        //打印店铺名称
        printBean.getEsc().addText(this.company + "\n");
        printBean.getEsc().addPrintAndLineFeed();

        printBean.getEsc().addPrintAndFeedLines((byte) 1);
        //打印排队号码
        printBean.getEsc().addText(String.valueOf(entity.getSeq()) + "\n");
        /*打印文字*/
        printBean.getEsc().addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);//取消倍高倍宽
        //设置打印左对齐
        //printBean.getEsc().addSelectJustification(JUSTIFICATION.LEFT);
        printBean.getEsc().addPrintAndLineFeed();
        printBean.getEsc().addText("你前面还有 " + i + " 人等待!\n");

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
    public void edit(@PathParam("id") Integer id, Queue entity) {
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
    public Queue find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Queue> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private void setDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.baseDate = Calendar.getInstance().getTime();
        this.simpleDate = sdf.format(baseDate);
        this.date = sdf.parse(this.simpleDate);
    }

    private void setType() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (this.baseDate.after(sdf.parse(this.simpleDate + " " + breakfastBegin)) && this.baseDate.before(sdf.parse(this.simpleDate + " " + breakfastEnd))) {
            this.type = "早市";
        } else if (this.baseDate.after(sdf.parse(this.simpleDate + " " + lunchBegin)) && this.baseDate.before(sdf.parse(this.simpleDate + " " + lunchEnd))) {
            this.type = "午市";
        } else if (this.baseDate.after(sdf.parse(this.simpleDate + " " + dinnerBegin)) && this.baseDate.before(sdf.parse(this.simpleDate + " " + dinnerEnd))) {
            this.type = "晚市";
        } else {
            this.type = "休市";
        }
    }

}
