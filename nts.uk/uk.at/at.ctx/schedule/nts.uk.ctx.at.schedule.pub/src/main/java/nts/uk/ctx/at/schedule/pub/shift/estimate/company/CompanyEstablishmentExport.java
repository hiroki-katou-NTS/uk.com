/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pub.shift.estimate.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 全社目安設定

@Getter
@AllArgsConstructor
public class CompanyEstablishmentExport {

    //会社ID
    private String companyId;

    // 対象年
    private int targetYear;

    //詳細設定
    //private EstimateDetailSetting advancedSetting;

}
