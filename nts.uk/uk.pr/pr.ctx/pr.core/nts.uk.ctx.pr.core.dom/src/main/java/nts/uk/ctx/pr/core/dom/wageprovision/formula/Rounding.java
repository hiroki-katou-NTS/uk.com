package nts.uk.ctx.pr.core.dom.wageprovision.formula;
/**
 * 端数処理
 */
public enum Rounding {

    ROUND_UP(0, "切り上げ"),
    TRUNCATION(1, "切り捨て"),
    DOWN_1_UP_2(2, "一捨二入"),
    DOWN_2_UP_3(3, "二捨三入"),
    DOWN_3_UP_4(4, "三捨四入"),
    DOWN_4_UP_5(5, "四捨五入"),
    DOWN_5_UP_6(6, "五捨六入"),
    DOWN_6_UP_7(7, "六捨七入"),
    DOWN_7_UP_8(8, "七捨八入"),
    DOWN_8_UP_9(9, "八捨九入");


    /** The value. */
    public final int value;

    /** The name id. */
    public final String nameId;
    private Rounding(int value, String nameId)
    {
        this.value = value;
        this.nameId = nameId;
    }
}
