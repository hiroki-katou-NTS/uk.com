package nts.uk.ctx.at.schedule.dom.budget.premium;


import lombok.AllArgsConstructor;

/**
 * Enum: 人件費単価端数処理
 */
@AllArgsConstructor
public enum  UnitPriceRounding {
    // 切り上げ
    ROUND_UP(0),

    //切り捨て
    TRUNCATION(1),

    //四捨五入
    DOWN_4_UP_5(2);


    public final int value;

}
