/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ls.queue.print;

import com.gprinter.command.EscCommand;
import com.gprinter.command.GpCom;
import com.gprinter.command.GpCom.ERROR_CODE;
import com.gprinter.io.GpDevice;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author kevindong
 */
@Startup
@Singleton
@LocalBean
public class PrintBean {

    private GpDevice printer;
    private EscCommand esc ;

    private String ip;
    private int port;

    public PrintBean() {

    }

    @PostConstruct
    public void construct() {
        init();
    }

    //实例化打印设备
    public void init() {
        if (getPrinter() == null) {
            this.printer = new GpDevice();
        }
        if(getEsc() == null){
            this.esc = new EscCommand();
        }
    }
    
    //连接到打印设备,目前只支持网络连接
    public ERROR_CODE open(String ip, int port) {       
        this.ip = ip;
        this.port = port;
        ERROR_CODE errorCode = this.getPrinter().openEthernetPort(0, this.ip, this.port);
        return errorCode;
    }
    
    public void addText(){
        
    }
    
  
     public void print() {

        if (this.getPrinter().getConnectState() == 3 && this.getEsc()!=null) {
            Vector<Byte> printData = esc.getCommand(); //发送数据
            Byte[] Bytes = printData.toArray(new Byte[printData.size()]);
            byte[] bytes = ArrayUtils.toPrimitive(Bytes);
            String str = Base64.encodeBase64String(bytes);
            int rel;
            try {
                rel = this.getPrinter().sendEscCommand(0, str);
                GpCom.ERROR_CODE errorCode = GpCom.ERROR_CODE.values()[rel];
                if (errorCode == GpCom.ERROR_CODE.SUCCESS) {
                    this.esc.getCommand().clear();
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    /**
     * @return the printer
     */
    public GpDevice getPrinter() {
        return printer;
    }

    /**
     * @return the esc
     */
    public EscCommand getEsc() {
        return esc;
    }

}
