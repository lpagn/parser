package ar.edu.itba.sds;

import java.io.*;
import java.util.Properties;

import static java.lang.System.exit;

public class ReadConf {

	private static ReadConf singleton;
	
	String generatormaxagents = "";
	String simulationdt= "";
	String simulationdt2 = "";
	String simulationtime = "";

	private ReadConf () {
		getPropValues();
	}

	// Get proprety values, make use of maven structure and configuration

	private void getPropValues() {

		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			File initialFile = new File("./src/main/resources/config.properties");
			inputStream = new FileInputStream(initialFile);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read file\n");
			exit(-1);
		}

		try {
			prop.load(inputStream);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		this.generatormaxagents = prop.getProperty("generator.max-agents");
		this.simulationdt = prop.getProperty("simulation.dt");
		this.simulationdt2 = prop.getProperty("simulation.dt2");
		this.simulationtime = prop.getProperty("simulation.time");
		
	}

	public static ReadConf getInstance () {

		if (singleton == null) {
			singleton = new ReadConf();
		}

		return singleton;
	}

}