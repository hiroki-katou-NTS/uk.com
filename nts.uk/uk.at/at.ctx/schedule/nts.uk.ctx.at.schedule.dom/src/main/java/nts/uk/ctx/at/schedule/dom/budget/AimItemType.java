package nts.uk.ctx.at.schedule.dom.budget;

public enum AimItemType {
    //1: 夜勤
    Night_shift(0),

    //2:  就業
    Employment(1),

    //3: 法定内残業
    Legal_overtime(2),

    //4: 法定外残業
    Non_statutory_overtime(3),

    //5: 総労働
    Total_labor(5);

    public final int value;

    AimItemType(int value) {
        this.value = value;
    }
}
