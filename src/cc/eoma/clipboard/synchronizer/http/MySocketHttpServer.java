package cc.eoma.clipboard.synchronizer.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 使用 ServerSocket 实现 HTTP 服务器. 供文件共享使用，具体实现后续开发
 * @Author goma
 * @Date 2021/9/2 下午5:28
 * @Version 1.0
 */
public class MySocketHttpServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("启动服务器....");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> receive(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void receive(Socket socket) {
        try {
            System.out.println("TID:" + Thread.currentThread().getId() + " 客户端:" + socket.getInetAddress().getHostAddress() + "已连接到服务器");
            InputStream inputStream = socket.getInputStream();//拿到in out putStream，就想干啥干啥了
            OutputStream outputStream = socket.getOutputStream();
            byte[] bytes = new byte[inputStream.available()];
            int result = inputStream.read(bytes);//读取请求的所有内容，实质是好几行String，里面存有http信息
            if (result != -1) {
                String request = new String(bytes, 0, result);
                if ("".equals(request) || request.startsWith("GET /favicon.ico")) {
                    return;
                }
                System.out.println("===>" + request);
            }

            // 返回 HTML 信息
//            String body = "<h1>hi</h1>";//可以是html文件，读文本文件进来就行了
//            // 返回客户端
//            StringBuilder response = new StringBuilder();
//            response.append("HTTP/1.1 200 OK\r\n");// 响应行
//            // 响应头
//            response.append("cache-control: private;\r\n");
//            response.append("content-type: text/html; charset=utf-8\r\n");
//            // 返回信息字节数（不包含头信息长度）
//            response.append("content-length: ").append(body.getBytes(StandardCharsets.UTF_8).length).append("\r\n");
//            response.append("\r\n");
//            response.append(body);
//            response.append("\r\n");
//            outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));//按照协议，将返回请求由outputStream写入
//            outputStream.flush();

            // 文件下载配置
            FileInputStream fis = new FileInputStream(new File("/home/goma/Downloads/Apifox-linux-latest.zip"));
            StringBuilder response = new StringBuilder();
            response.append("HTTP/1.1 200 OK\r\n");// 响应行
            response.append("cache-control: private;\r\n");

            // 图片头设置
//            response.append("content-type: image/png; charset=utf-8\r\n");

            // zip头设置
            response.append("content-type: application/octet-stream; charset=utf-8\r\n");
            response.append("Content-Disposition: attachment;filename=xxx.zip\r\n");

            response.append("content-length: ").append(fis.available()).append("\r\n");
            response.append("\r\n");

            byte[] header = response.toString().getBytes(StandardCharsets.UTF_8);
            outputStream.write(header, 0, header.length);
            int len = 0;
            byte[] buffer = new byte[1024 * 100];
            while ((len = fis.read(buffer)) != -1) {
                // TODO GOMA 可以控制下载速度？
                Thread.sleep(100);

                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
