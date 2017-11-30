/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;

/**
 * The Class AcquisitionSetting.
 */
// 取得設定
@Builder
public class AcquisitionSetting {

    /** The permit type. */
    // 先取り許可
    public ApplyPermission permitType;
    
    /** The annual priority. */
    // 年休消化優先
    public AnnualPriority annualPriority;
}
