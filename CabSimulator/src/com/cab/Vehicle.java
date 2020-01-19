package com.cab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Properties;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Vehicle extends Thread{
	private String name;
	private String vehicle_id;
	private Point position;
	private Point[] track;
	private int status_of_charge;
	private Point next_position;
	private String trip_status;
	private Thread vehicle;
	private Scanner sc ;
	Vehicle(String name,String vehicleid,Point pos,int charge,Point next,String status,int track_id){
		this.name=name;
		this.vehicle_id=vehicleid;
		this.position= pos;
		this.status_of_charge=charge;
		this.next_position=next;
		this.trip_status=status;
		this.track = Track.getMyTrack(track_id);
		this.vehicle = new Thread();
		this.sc= new Scanner(System.in);
		this.vehicle.start();
	}
	
	public void run() {
		
		try(InputStream input=new FileInputStream("config.properties")){
			
			Properties prop=new Properties();
			prop.load(input);
			int start = Integer.parseInt(prop.getProperty("start"));
			int pause = Integer.parseInt(prop.getProperty("pause"));
			int stop = Integer.parseInt(prop.getProperty("stop"));
			
			while(true) {
				Object obj = new JSONParser().parse(new FileReader("vehicle.json"));
				JSONObject jo = (JSONObject)obj;
				this.trip_status = ((JSONObject)jo.get("attributes")).get("trip_status").toString().trim();
				System.out.println(this.trip_status);
				if(this.trip_status.equals(new String("start"))) {
					System.out.println("Enter new Status");
					String userinput = this.sc.nextLine();
					((JSONObject)jo.get("attributes")).put("trip_status", userinput );
					try (FileWriter file = new FileWriter("vehicle.json")) 
		            {
						System.out.println(jo.toString());
		                file.write(jo.toString());
		                System.out.println("Successfully updated json object to file...!!");
		                file.close();
		            }catch(Exception e)
					{
		            	e.printStackTrace();
					}
					Thread.sleep(start*1000);
				}else if(this.trip_status.equals(new String("pause"))) {
					break;
				}else if(this.trip_status.equals(new String("stop"))) {
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
