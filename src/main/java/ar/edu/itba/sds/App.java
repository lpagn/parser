package ar.edu.itba.sds;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Parser" );
        
        ReadConf reader = ReadConf.getInstance();
        int N = Integer.parseInt(reader.generatormaxagents);
        double dt = Double.parseDouble(reader.simulationdt);
        double dt2 = Double.parseDouble(reader.simulationdt2);
        double simulationtime = Double.parseDouble(reader.simulationtime);
        
        System.out.println(String.format("%s %.2f %.2f %.2f",N,dt,dt2,simulationtime));
        Parser parser = new Parser(N,dt2,dt,simulationtime);// This parameters should be read from a config file

        parser.run();
        parser.averages();

        //TODO: average distance
        System.out.println(parser.distances());
        System.out.println("Average D: " + parser.avgD);
        //TODO: average time
        System.out.println(parser.times());
        System.out.println("Average T: " + parser.avgT);
        //TODO: average time in 0
        System.out.println(parser.timesS0());
        //TODO: average time in 1
        System.out.println(parser.timesS1());
        //TODO: average time in 2
        System.out.println(parser.timesS2());
        //TODO: average time in 3
        System.out.println(parser.timesS3());
        //TODO: average time in 4
        System.out.println(parser.timesS4());
        //TODO: average time in 5
        System.out.println(parser.timesS5());

        System.out.println(parser.compare());
        
        
    }
}