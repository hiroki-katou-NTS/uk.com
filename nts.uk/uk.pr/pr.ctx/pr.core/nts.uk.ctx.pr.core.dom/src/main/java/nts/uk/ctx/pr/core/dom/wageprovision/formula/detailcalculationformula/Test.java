package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

public class Test {
    public static void main(String[] args) {
        testRegexByOperator();
        testRegexByCondition();
    }
    private static void testRegexByOperator () {
        String test = "支給{payment 1}＋支給{payment 2}ー支給{payment 3}＋支給{payment 4}÷支給{payment 5}";
        String regex = "([\\＋|ー|\\×|÷|\\^|\\(|\\)|\\>|\\<|\\≦|\\≧|\\＝|\\≠|\\,])";
        String regex1 = "(?<=[＋ー*×÷^()><≦≧＝≠,])|(?=[＋ー*×÷^()><≦≧＝≠,])";
        String [] result = test.split(regex1);
        for(String x : result) {
            System.out.println(x);
        }
    }
    private static void testRegexByCondition () {
        String test = "a>2＝1≠6";
        String regex = "\\>|\\<|\\≦|\\≧|\\＝|\\≠";
        String regex1 = "(?<=[><≦≧＝≠])|(?=[><≦≧＝≠])";
        String [] result = test.split(regex1);
        for(String x : result) {
            System.out.println(x);
        }
    }
}
