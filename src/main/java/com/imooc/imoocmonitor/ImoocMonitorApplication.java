package com.imooc.imoocmonitor;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
@RestController
public class ImoocMonitorApplication {

	public static void main(String[] args) {

		SpringApplication.run(ImoocMonitorApplication.class, args);
	}

	@Autowired
	private Connection connection;

	@GetMapping
	public String hello() {
		return "hello";
	}

	@PostMapping("/monitor")
	public String ssh(String cmd) throws Exception{
		//实例化一个session对象
		Session session = null;
		String result ;
		try{
		session = connection.openSession();
		//执行命令
		session.execCommand(cmd, "UTF-8");
		//拿到我们的输出结果
		InputStream stdout = session.getStdout();
		//解析返回对象，拿到返回结果：String
		result = parseResult(stdout,"UTF-8");}
		finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}
	/**
	 * 解析脚本执行返回的结果集
	 *
	 * @param in      输入流对象
	 * @param charset 编码
	 * @return 以纯文本的格式返回
	 */
	private String parseResult(InputStream in, String charset) throws Exception {
		//用stringbuilder来操作字符串java.lang.autocloseable
		StringBuilder sb = new StringBuilder();
		try (
				//基于jdk1.7以后的特性，在try关键字后声明已经实现java.lang.AutoCloseable的对象
				InputStream stdout = new StreamGobbler(in);
				//声明读取器来读取输入流，可以操作字节流的读取器来读取stdout
				//因为在try()语句块中，我们可以为所欲为地声明已经实现了AutoCloseable接口的对象，让jdk自动为我们关闭读写流
				//不用显示调用它们的close()方法
				BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset))
		) {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		}
		return sb.toString();
	}

}
