package Player;

public class ChessPlayer implements Player{
    private byte group;
    private byte choice;

    @Override
    public byte GetGroup() {
        return group;
    }

    @Override
    public byte GetChoice() {
        return choice;
    }

    public void MakeChoice(){}

    public void setGroup(byte group) {
        this.group = group;
    }


}
