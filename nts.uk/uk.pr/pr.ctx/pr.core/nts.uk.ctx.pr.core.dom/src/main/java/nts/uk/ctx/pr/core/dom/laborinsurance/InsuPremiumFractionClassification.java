package nts.uk.ctx.pr.core.dom.laborinsurance;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum InsuPremiumFractionClassification {

    // 0:切り捨て
    TRUNCATION(0),

    // 1:切り上げ
    ROUND_UP(1),

    // 2:四捨五入
    ROUND_4_UP_5(2),

    // 3:五捨六入
    ROUND_5_UP_6(3),

    // 4:五捨五超入
    ROUND_SUPER_5(4);

    public final int value;
}

