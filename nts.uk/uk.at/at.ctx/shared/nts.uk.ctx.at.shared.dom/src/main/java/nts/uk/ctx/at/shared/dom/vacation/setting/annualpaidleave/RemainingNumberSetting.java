/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Builder;

/**
 * The Class RemainingNumberSetting.
 */
// 残数設定
@Builder
public class RemainingNumberSetting {

    /** The retention year. */
    // 保持年数
    public RetentionYear retentionYear;

    /** The remaining day max number. */
    // 残数上限日数
    public MaxRemainingDay remainingDayMaxNumber;
}
