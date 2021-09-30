package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import model.Bola;
import processing.core.PApplet;

public class MainServer extends PApplet {
	
	private BufferedWriter bfw;
	private BufferedReader bfr;
	private int bx, by, r, g, b;
	private Bola bola;
	
public static void main(String[] args) {
		
		PApplet.main(MainServer.class.getName());
	}
	
	public void settings() {
		
		size(500, 500);
		
	}
	
	public void setup() {
		
		bx = 250;
		by = 250;
		r = 255;
		g = 255;
		b = 255;
		
		
		bola = new Bola(250, 250, 255, 255, 255);
		
		try {
			
			System.out.println("Iniciando Server");
			
			//puerto para que incie
			ServerSocket ss = new ServerSocket(9000);
			System.out.println("Esperando cliente");
			
			Socket conexion = ss.accept();// stops flujo hasta que haya conexion
			System.out.println("Se conecto un cliente");
			
			//para leer los msg
			InputStream is = conexion.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			bfr = new BufferedReader(isr);
			
			//escribir los msg
			OutputStream os = conexion.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			bfw = new BufferedWriter(osw);
			
			getMsgs();
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	public void draw() {
		
		//System.out.println(mouseX+","+mouseY);
		
		background(169, 245, 255);
		textAlign(CENTER);
		textSize(18);
		text("Server", 250, 30);
		
		fill (bola.getR(), bola.getG(), bola.getB());
		ellipse (bola.getPosX(), bola.getPosY(), 20, 20);
		
		
	}
	
	
	public void getMsgs() {
		
		new Thread(() -> {
			
		
		while(true) {
			
			try {
				
				System.out.println("Esperando mensaje del cliente");
				//System.out.println(bfr.readLine());
				String msg = bfr.readLine();
				System.out.println("Recibido: " + msg);
				
				Gson gson = new Gson();
				bola = gson.fromJson(msg, Bola.class);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}).start();
		
		
	}
	
	public void sentMsg() {
		
		
		
	}

}
