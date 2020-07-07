package ar.edu.itba.sds;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Parser" );

        Parser parser = new Parser(10,0.05,0.5,700);// This parameters should be read from a config file

        parser.run();

        //TODO: average distance
        System.out.println(parser.distances());
        //TODO: average time
        System.out.println(parser.times());
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