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
	private String vehical_json_filename;
	private Thread vehicle;
	private Scanner sc ;
	Vehicle(int track_id,String filename){
		this.vehical_json_filename=filename;
		FileReader jsonFile;
		try {
			jsonFile = new FileReader(this.vehical_json_filename);
			Object obj = new JSONParser().parse(jsonFile);
			JSONObject jo = (JSONObject)obj;
			jsonFile.close();
			this.name=((JSONObject)jo.get("id")).get("name").toString();
			this.vehicle_id=((JSONObject)jo.get("id")).get("vehicleIdentificationnumber").toString();
			this.position= new Point(Integer.parseInt(((JSONObject)jo.get("position")).get("latitude").toString()),Integer.parseInt(((JSONObject)jo.get("position")).get("longitude").toString()));
			this.status_of_charge=Integer.parseInt(((JSONObject)jo.get("attributes")).get("soc").toString());
			JSONObject heading = (JSONObject) ((JSONObject)jo.get("attributes")).get("heading");
			this.next_position=new Point(Integer.parseInt(heading.get("latitude").toString()),Integer.parseInt(heading.get("longitude").toString()));
			this.trip_status=((JSONObject)jo.get("attributes")).get("trip_status").toString();
			this.track = Track.getMyTrack(track_id);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
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
				FileReader jsonFile = new FileReader(this.vehical_json_filename);
				Object obj = new JSONParser().parse(jsonFile);
				JSONObject jo = (JSONObject)obj;
				jsonFile.close();
				this.trip_status = ((JSONObject)jo.get("attributes")).get("trip_status").toString().trim();
				System.out.println(this.trip_status);
				if(this.trip_status.equals(new String("start"))) {
					System.out.println("Enter new Status");
					String userinput = this.sc.nextLine();
					((JSONObject)jo.get("attributes")).put("trip_status", userinput );
					Thread.sleep(start*1000);
				}else if(this.trip_status.equals(new String("pause"))) {
					System.out.println("Enter new Status");
					String userinput = this.sc.nextLine();
					((JSONObject)jo.get("attributes")).put("trip_status", userinput );
					Thread.sleep(pause * 1000);
				}else if(this.trip_status.equals(new String("stop"))) {
					System.out.println("Enter new Status");
					String userinput = this.sc.nextLine();
					((JSONObject)jo.get("attributes")).put("trip_status", userinput );
					Thread.sleep(stop * 1000);
				}
				try (FileWriter file = new FileWriter(this.vehical_json_filename)) 
	            {
					System.out.println(jo.toString());
	                file.write(jo.toString());
	                System.out.println("Successfully updated json object to file...!!");
	                file.close();
	            }catch(Exception e)
				{
	            	e.printStackTrace();
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
