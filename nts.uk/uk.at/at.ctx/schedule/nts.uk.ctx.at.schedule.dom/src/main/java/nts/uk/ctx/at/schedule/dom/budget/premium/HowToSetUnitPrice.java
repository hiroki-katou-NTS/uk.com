package nts.uk.ctx.at.schedule.dom.budget.premium;


/**
 * 単価の設定方法
 */
public enum HowToSetUnitPrice {

    //1: 割増率を設定する
    SET_PREMIUM_RATE(0),

    //2: 単価を設定する
    SET_UNIT_PRICE(1);

    public final int value;

    HowToSetUnitPrice(int value) {
        this.value = value;
    }


}
