package Player;

public class ChessPlayer {
    private byte group;
    private byte choice;

    public ChessPlayer(){}
    public byte GetGroup() {
        return group;
    }

    public byte GetChoice() {
        return choice;
    }

    public void MakeChoice(byte choice){
        this.choice=choice;
    }

    public void SetGroup(byte group) {
        this.group = group;
    }


}
