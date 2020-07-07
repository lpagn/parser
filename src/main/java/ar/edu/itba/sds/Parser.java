package ar.edu.itba.sds;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
	boolean [] presence;
	boolean [] presence_last;
	
	int [] id;
	int [] id_last;
	
	double X[];
	double X_last[];
	
	double Y[];
	double Y_last[];
	
	int S[];
	int S_last[];
	
	double T[];
	double D[];
	double S0[];
	double S1[];
	double S2[];
	double S3[];
	double S4[];
	double S5[];
	
	int N;
	double dt;
	double dt2;
	double simulationTime;
	
	public Parser(int N , double dt , double dt2 , double simulationTime) {
		this.N = N;
		this.dt = dt;
		this.dt2 = dt2;
		this.simulationTime = simulationTime;
		N = N + 1; //agent's id are numbered starting by 1
		presence = new boolean[N];
		presence_last = new boolean[N];
		
		id = new int[N];
		id_last = new int[N];
		
		X = new double[N];
		X_last = new double[N];
		
		Y = new double[N];
		Y_last = new double[N];
		
		S = new int[N];
		S_last = new int[N];
		
		T = new double [N];
		D = new double [N];
		S0 = new double[N];
		S1 = new double[N];
		S2 = new double[N];
		S3 = new double[N];
		S4 = new double[N];
		S5 = new double[N];
	}
	
	public void run() {
		/*
		iterate through each file and parse each of them
		*/
		double clock = dt2;
		double time = 0;
		while(time <= simulationTime){
		    if (time > clock) {
		        int filename = (int)(time/0.1);
		        String file = "results/" + filename  + ".txt";
		        //System.out.println(file);
		        parse(file);// after parse is called the arrays are set so that calculations can be performed
		        calculate();
		        clock += dt2;
		    }
		    time += dt;
		}
	}
	
	public void parse(String filename) {
		/*
		this method goes through every line of the x.txt
		it assigns in ID , X , Y , S (vx and vy are ignored!)
		the values id , x , y , ... , ... , s
		*/
		
		update();//for every array that is used in the Parser class, update does: arr_last = arr;
		
		String line;
		try {
	        @SuppressWarnings("resource")
			BufferedReader bufferreader = new BufferedReader(new FileReader(filename));
	        while ((line = bufferreader.readLine()) != null) {   
	        	//System.out.println(line);
	        	String[] arr = line.split(" ");
	        	int id = 0;
	        	double x = 0;
	        	double y = 0;
	        	int state = 0;
	        	for(@SuppressWarnings("unused") String str : arr) {
	        		//System.out.println(str);
	        		id = Integer.parseInt(arr[0]);
	        		presence[id] = true;
	        		x = Double.parseDouble(arr[1]);
	        		y = Double.parseDouble(arr[2]);
	        		state = Integer.parseInt(arr[5]);
	        	}
	        	//System.out.println(String.format("%d %.2f %.2f %d",id,x,y,state));
	        	X[id] = x;
	        	Y[id] = y;
	        	S[id] = state;
	        }
	    } catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	public void calculate() {
		for(int id = 0 ; id < N + 1 ; id++){
			if(presence[id] && presence_last[id]) {
				//the agent is in both this file and the last one
				double r = distance(X_last[id],X[id],Y_last[id],Y[id]);
				D[id] += r;
				T[id] += dt2;
				switch(S[id]) {
					case 0:
						S0[id] += dt2;
					break;
					case 1:
						S1[id] += dt2;
					break;
					case 2:
						S2[id] += dt2;
					break;
					case 3:
						S3[id] += dt2;
					break;
					case 4:
						S4[id] += dt2;
					break;
					case 5:
						S5[id] += dt2;
					break;
				}
			}
		}
	}
	
	public void update() {
		/*
		this method updates the last reference and cleans the current reference
		*/
		presence_last = presence;
		id_last = id;
		X_last = X;
		Y_last = Y;
		S_last = S;
		for(int i = 0 ; i < N + 1; i++) {
			presence[i]  = false;
			id[i] = 0;
			X[i] = 0;
			Y[i] = 0;
			S[i] = -1;
		}
	}
	
	private double distance(double x_last,double x,double y_last,double y) {
		return Math.sqrt(Math.pow(x_last - x, 2) + Math.pow(y_last - y, 2));
	}
	
	public String distances() {
		/*
		
		id	Total Distance [m]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Distance [m]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(D[i]).append(" \n");
		}
		return ret.toString();
	}
	
	public String times() {
		/*
		
		id	Total Time [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(T[i]).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS0() {
		/*
		
		id	Total Time in State 0 [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 0 [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(S0[i]).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS1() {
		/*
		
		id	Total Time in State 1 [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 1 [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(S1[i]).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS2() {
		/*
		
		id	Total Time in State 2 [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 2 [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(S2[i]).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS3() {
		/*
		
		id	Total Time in State 3 [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 3 [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(S3[i]).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS4() {
		/*
		
		id	Total Time in State 4 [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 4 [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(S4[i]).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS5() {
		/*
		
		id	Total Time in State 5 [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 5 [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(S5[i]).append(" \n");
		}
		return ret.toString();
	}
	

	public String compare() {
		/*
		
		id Total Time [s]	Total Time in States Sum [s]
		
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time [s]	").append("Total Time in State Sum [s]").append(" \n");
		for(int i = 0 ; i < N + 1 ; i++) {
			ret.append(i).append(" ").append(T[i]).append("\t").append(S0[i] + S1[i]+ S2[i]+ S3[i] + S4[i] +S5[i]).append(" \n");
		}
		return ret.toString();
	}
	
}
