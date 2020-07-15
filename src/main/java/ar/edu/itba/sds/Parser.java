package ar.edu.itba.sds;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	List<Boolean> presence;
	List<Boolean> presence_last;
	
	List<Integer> id;
	List<Integer> id_last;
	
	List<Double> X;
	List<Double> X_last;
	
	List<Double> Y;
	List<Double> Y_last;
	
	List<Integer> S;
	List<Integer> S_last;

	double avgT = 0;
	List<Double> T;

	double avgD = 0;
	List<Double> D;

	double avgS0 = 0;
	List<Double> S0;

	double avgS1 = 0;
	List<Double> S1;

	double avgS2 = 0;
	List<Double> S2;

	double avgS3 = 0;
	List<Double> S3;

	double avgS4 = 0;
	List<Double> S4;

	double avgS5 = 0;
	List<Double> S5;

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
		presence = new ArrayList<Boolean>(N);
		presence_last = new ArrayList<Boolean>(N);
		id = new ArrayList<Integer>(N);
		id_last = new ArrayList<Integer>(N);
		X = new ArrayList<Double>(N);
		X_last = new ArrayList<Double>(N);
		Y = new ArrayList<Double>(N);
		Y_last = new ArrayList<Double>(N);
		S = new ArrayList<Integer>(N);
		S_last = new ArrayList<Integer>(N);
		T =  new ArrayList<Double>(N);
		D =  new ArrayList<Double>(N);
		S0 = new ArrayList<Double>(N);
		S1 = new ArrayList<Double>(N);
		S2 = new ArrayList<Double>(N);
		S3 = new ArrayList<Double>(N);
		S4 = new ArrayList<Double>(N);
		S5 = new ArrayList<Double>(N);

		for(int i = 0 ; i < N ; i++){
			presence.add(false);
			presence_last.add(false);
			id.add(0);
			id_last.add(0);
			X.add(0.0);
			X_last.add(0.0);
			Y.add(0.0);
			Y_last.add(0.0);
			S.add(0);
			S_last.add(0);
			T.add(0.0);
			D.add(0.0);
			S0.add(0.0);
			S1.add(0.0);
			S2.add(0.0);
			S3.add(0.0);
			S4.add(0.0);
			S5.add(0.0);
		}
	}

	public void run() {
		/*
		iterate through each file and parse each of them
		*/
		double clock = dt2;
		double time = 0;
		while(time < simulationTime){ // do not look for the last file
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
	        		if(id >= presence.size()){
	        			presence.add(false);
	        			presence_last.add(false);
	        			X.add(0.0);
	        			X_last.add(0.0);
	        			Y.add(0.0);
	        			Y_last.add(0.0);
	        			S_last.add(0);
	        			S.add(0);
	        			T.add(0.0);
	        			D.add(0.0);
	        			S0.add(0.0);
	        			S1.add(0.0);
						S2.add(0.0);
						S3.add(0.0);
						S4.add(0.0);
						S5.add(0.0);
					}
	        		//System.out.println(filename);
	        		presence_last.set(id , presence.get(id));
	        		presence.set(id , true);
	        		x = Double.parseDouble(arr[1]);
	        		y = Double.parseDouble(arr[2]);
	        		state = Integer.parseInt(arr[5]);
	        	}
				X_last.set(id , X.get(id));
	        	X.set(id,x);
	        	Y_last.set(id ,  Y.get(id));
	        	Y.set(id,y);
	        	S_last.set(id,S.get(id));
	        	S.set(id,state);
	        }
	    } catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	}
	
	public void calculate() {
		for(int id = 0 ; id < presence.size() ; id++){
			if(presence.get(id) && presence_last.get(id)) {
				//the agent is in both this file and the last one
				double r = distance(X_last.get(id),X.get(id),Y_last.get(id),Y.get(id));
				D.set(id,D.get(id) + r);
				T.set(id,T.get(id) + dt2);
				switch(S.get(id)) {
					case 0:
						S0.set(id,S0.get(id) + dt2);
					break;
					case 1:
						S1.set(id,S1.get(id) + dt2);
					break;
					case 2:
						S2.set(id,S2.get(id) + dt2);
					break;
					case 3:
						S3.set(id,S3.get(id) + dt2);
					break;
					case 4:
						S4.set(id,S4.get(id) + dt2);
					break;
					case 5:
						S5.set(id,S5.get(id) + dt2);
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
		for(int i = 0 ; i < D.size() ; i++) {
			ret.append(i).append(" ").append(D.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public String times() {
		/*
		id	Total Time [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time [s]").append(" \n");
		for(int i = 0 ; i < T.size() ; i++) {
			ret.append(i).append(" ").append(T.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS0() {
		/*
		id	Total Time in State 0 [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 0 [s]").append(" \n");
		for(int i = 0 ; i < S0.size() ; i++) {
			ret.append(i).append(" ").append(S0.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS1() {
		/*
		id	Total Time in State 1 [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 1 [s]").append(" \n");
		for(int i = 0 ; i < S1.size() ; i++) {
			ret.append(i).append(" ").append(S1.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS2() {
		/*
		id	Total Time in State 2 [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 2 [s]").append(" \n");
		for(int i = 0 ; i < S2.size() ; i++) {
			ret.append(i).append(" ").append(S2.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS3() {
		/*
		id	Total Time in State 3 [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 3 [s]").append(" \n");
		for(int i = 0 ; i < S3.size() ; i++) {
			ret.append(i).append(" ").append(S3.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS4() {
		/*
		id	Total Time in State 4 [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 4 [s]").append(" \n");
		for(int i = 0 ; i < S4.size() ; i++) {
			ret.append(i).append(" ").append(S4.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public String timesS5() {
		/*
		id	Total Time in State 5 [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time in State 5 [s]").append(" \n");
		for(int i = 0 ; i < S5.size() ; i++) {
			ret.append(i).append(" ").append(S5.get(i)).append(" \n");
		}
		return ret.toString();
	}

	public String compare() {
		/*
		id Total Time [s]	Total Time in States Sum [s]
		*/
		StringBuilder ret = new StringBuilder();
		ret.append("ID ").append("Total Time [s]	").append("Total Time in State Sum [s]").append(" \n");
		for(int i = 0 ; i < S.size() ; i++) {
			ret.append(i).append(" ").append(T.get(i)).append("\t").append(S0.get(i) + S1.get(i)+ S2.get(i) + S3.get(i) + S4.get(i) +S5.get(i)).append(" \n");
		}
		return ret.toString();
	}
	
	public void averages() {
		avgT = average(T);
		avgD = average(D);
	}

	public double average(List<Double>x) {
		double sum = 0;
		for(int i = 0 ; i < x.size() ; i++) { // skip the first one and include the last one
			sum += x.get(i);
		}
		if(N > 1 ){
			return sum / N;
		};
		return 0;
	}
	
}
