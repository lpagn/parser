package ar.edu.itba.sds;

import org.junit.Test;

public class ParserTest {
    Parser parser = new Parser(4,1,1,10);

    @Test
    public void parserTest(){
        parser.parse("src/test/resources/test0.txt");
        parser.calculate();
        parser.parse("src/test/resources/test1.txt");
        parser.calculate();
        parser.parse("src/test/resources/test2.txt");
        parser.calculate();

        parser.averages();
        parser.percentage();

        System.out.println(parser.distances());
        System.out.println("Average distance: " + parser.avgD);

        System.out.println(parser.times());

        System.out.println(parser.timesS0());

        System.out.println(parser.timesS1());

        System.out.println(parser.percentages());
    }

}
