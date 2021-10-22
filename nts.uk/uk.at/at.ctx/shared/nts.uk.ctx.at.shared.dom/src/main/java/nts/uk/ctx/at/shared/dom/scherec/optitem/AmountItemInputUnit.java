package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.AllArgsConstructor;

/**
 * 金額項目の入力単位
 */
@AllArgsConstructor
public enum AmountItemInputUnit {
    // 0:1
    ONE(0),

    // 1:10
    TEN(1),

    // 2:100
    ONE_HUNDRED(2),

    // 3:1000
    ONE_THOUSAND(3),

    // 4:10000
    TEN_THOUSAND(4);

    public int value;
}
