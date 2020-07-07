package ar.edu.itba.sds;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Parser" );
        Parser parser = new Parser(10,0.05,0.5,700);// This parameters should be read from a config file
        parser.run();
        System.out.println(parser.distances());
        System.out.println(parser.times());
        System.out.println(parser.timesS0());
        System.out.println(parser.timesS1());
        System.out.println(parser.timesS2());
        System.out.println(parser.timesS3());
        System.out.println(parser.timesS4());
        System.out.println(parser.timesS5());
        System.out.println(parser.compare());
    }
}