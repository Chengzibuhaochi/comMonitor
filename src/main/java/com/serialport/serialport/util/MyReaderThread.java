package com.serialport.serialport.util;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;

public class MyReaderThread extends Thread implements SerialPortEventListener {
    private SerialPort serialPort;
    private InputStream is;

    public void init(SerialPort serialPort, InputStream is) {
        this.serialPort = serialPort;
        this.is = is;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        int count;
        try {
            count = is.available();
        } catch (IOException e) {
            System.out.println("串口数据解析错误");
            return;
        }

        String[] command = new String[count];

        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                try {
                    for (int i = 0; i < count; i++) {
                        int b = this.is.read();
                        command[i] = RadixConvertUtil.decToHex(b);
                    }

                } catch (IOException e) {
                    System.out.println("串口数据解析错误");
                }
        }
        mapperCommand(command);
    }

    public static String mapperCommand(String[] commands){
        // 解析串口输入指令
        if (commands.length == 0) return null;
        String commandHex = StringUtils.join(commands);
        String command = RadixConvertUtil.hexToGbk(commandHex);
        if (command.startsWith("abc:"))
            command = command.substring(4);
        System.out.println("识别到指令："+command);

        return command;
    }
}
