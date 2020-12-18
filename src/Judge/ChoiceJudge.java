package Judge;

public class ChoiceJudge extends Judge{
    private static ChoiceJudge me;
    public static ChoiceJudge Init() {
        if(me == null)
            me = new ChoiceJudge();
        return me;
    }

    private ChoiceJudge(){}
    @Override
    public boolean DoJudge() {
        return false;
    }
}
