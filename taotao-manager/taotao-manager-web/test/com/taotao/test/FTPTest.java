package com.taotao.test;

import com.taotao.common.util.FtpUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author:WangYichao
 * @Description:ftp服务测试
 * @Date:Created in 2018/1/2 8:19
 */
public class FTPTest {

    @Test
    public void uploadImage() throws Exception {

        FileInputStream fileInputStream = new FileInputStream(new File("D:\\ys7\\2.jpg"));

        FtpUtil.uploadFile("192.168.28.129",21,"ftpuser","19941014","/home/ftpuser/www/images","2018/1/2","test2.jpg",fileInputStream);

    }
}
