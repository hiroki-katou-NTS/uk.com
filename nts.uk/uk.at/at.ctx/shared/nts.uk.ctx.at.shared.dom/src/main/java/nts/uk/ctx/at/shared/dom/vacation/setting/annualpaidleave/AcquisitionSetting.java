/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Builder;

/**
 * The Class AcquisitionSetting.
 */
// 取得設定
@Builder
public class AcquisitionSetting {
    
    /** The annual priority. */
    // 年休消化優先
    public AnnualPriority annualPriority;
}
