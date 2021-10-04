package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.AllArgsConstructor;

/**
 * 回数項目の入力単位
 */
@AllArgsConstructor
public enum NumberItemInputUnit {
    // 0:0.01回
    ONE_HUNDREDTH(0),

    // 1:0.1回
    ONE_TENTH(1),

    // 2:0.5回
    ONE_HALF(2),

    // 3:1回
    ONE(3);

    public int value;
}
