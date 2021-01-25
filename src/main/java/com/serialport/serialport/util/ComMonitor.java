package com.serialport.serialport.util;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Scanner;

@Component
public class ComMonitor {

    private static MyReaderThread t = new MyReaderThread();
    public static final int RATE = 115200;			//波特率
    public static final int DATA_BITS = 8;		//数据位
    public static final int STOP_BITS = 1;		//停止位
    public static final int PARITY = 0;		//校验位

    public static void init(){
        System.out.println("请输入语音识别串口号，按回车键确认");
        Scanner scanner = new Scanner(System.in);
        String port = scanner.nextLine();
        if (port == null || port.length() == 0){
            System.out.println("请输入语音识别串口号！");
        }
        openComMonitor(port);
    }

    public static void openComMonitor(String port){
        CommPortIdentifier portId;
        SerialPort serialPort;
        InputStream is;
        try {
            portId = CommPortIdentifier.getPortIdentifier(port);
            serialPort = ((SerialPort) portId.open("Bar_Scan", 20000));
            is = serialPort.getInputStream();
            // 给串口添加监听器
            serialPort.addEventListener(t);
            // 设置当有数据到达时唤醒监听接收线程
            serialPort.notifyOnDataAvailable(true);
            // 设置当通信中断时唤醒中断线程
            serialPort.notifyOnBreakInterrupt(true);

            serialPort.setSerialPortParams(RATE, DATA_BITS, STOP_BITS, PARITY);

            t.init(serialPort, is);
            System.out.println("串口"+port+"打开成功");
        } catch (Exception e){
            System.out.println("串口"+port+"打开错误");
            init();
        }
    }
}
