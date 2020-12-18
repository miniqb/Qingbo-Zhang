package Judge;

public class ResultJudge extends Judge{
    private static ResultJudge me;
    public static ResultJudge Init() {
        if(me == null)
            me = new ResultJudge();
        return me;
    }

    private ResultJudge(){}
    @Override
    public boolean DoJudge() {
        return false;
    }
}
