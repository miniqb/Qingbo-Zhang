package Judge;

public class ThinkingJudge extends Judge{
    private static ThinkingJudge me;
    public static ThinkingJudge Init() {
        if(me == null)
            me = new ThinkingJudge();
        return me;
    }

    private ThinkingJudge(){}
    @Override
    public boolean DoJudge() {
        return false;
    }
}
