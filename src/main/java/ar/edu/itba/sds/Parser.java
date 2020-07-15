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
	
	double[] X;
	double[] X_last;
	
	double[] Y;
	double[] Y_last;
	
	int[] S;
	int[] S_last;

	double avgT = 0;
	double[] T;

	double avgD = 0;
	double[] D;

	double avgS0 = 0;
	double[] S0;

	double avgS1 = 0;
	double[] S1;

	double avgS2 = 0;
	double[] S2;

	double avgS3 = 0;
	double[] S3;

	double avgS4 = 0;
	double[] S4;

	double avgS5 = 0;
	double[] S5;

	double[] percentages;

	int N;
	double dt;
	double dt2;
	double simulationTime;
	
	public Parser(int N , double dt , double dt2 , double simulationTime) {
		this.N = N;
		this.dt = dt;
		this.dt2 = dt2;
		this.simulationTime = simulationTime;
		N = N + 1; //agent's id are numbered starting by 1, so the first elements of every array will always be 0
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
		String line;
		try {
	        @SuppressWarnings("resource")
			BufferedReader bufferreader = new BufferedReader(new FileReader(filename));
	        while ((line = bufferreader.readLine()) != null) {
	        	String[] arr = line.split(" ");
	        	int id = 0;
	        	double x = 0;
	        	double y = 0;
	        	int state = 0;
	        	for(@SuppressWarnings("unused") String str : arr) {
	        		id = Integer.parseInt(arr[0]);
	        		presence_last[id] = presence[id];
	        		presence[id] = true;
	        		x = Double.parseDouble(arr[1]);
	        		y = Double.parseDouble(arr[2]);
	        		state = Integer.parseInt(arr[5]);
	        	}
				X_last[id] = X[id];
	        	X[id] = x;
	        	Y_last[id] = Y[id];
	        	Y[id] = y;
	        	S_last[id] = S[id];
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

	public String percentages(){
		StringBuilder ret = new StringBuilder();
		ret.append("State ").append("Porcentual average time per state [s]").append(" \n");
		for(int i = 0 ; i < 5  ; i++) {
			ret.append(i).append(" ").append(percentages[i]).append(" \n");
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
	
	public void averages() {
		avgT = average(T);
		avgD = average(D);
	}

	public void percentage(){
		percentages = new double[5];
		percentages[0] = average(S0) / T[0];
		percentages[1] = average(S1) / T[1];
		percentages[2] = average(S2) / T[2];
		percentages[3] = average(S3) / T[3];
		percentages[4] = average(S4) / T[4];
//		percentages[5] = average(S5) / T[5];
	}
	
	public double average(double[]x) {
		double sum = 0;
		for(int i = 0 ; i < x.length ; i++) { // skip the first one and include the last one
			sum += x[i];
		}
		if(N > 1 ){
			return sum / N;
		};
		return 0;
	}
	
}
