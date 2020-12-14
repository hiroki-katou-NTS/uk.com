package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 
 * @author anhnm
 * 年休管理
 *
 */
@AllArgsConstructor
@Getter
public class AnualLeaveManagement {
    
    // 時間年休消化単位
    private TimeDigestiveUnit timeAnnualLeave;
    
    // 時間年休管理区分
    private ManageDistinct timeAnnualLeaveManage;
    
    // 年休管理区分
    private ManageDistinct annualLeaveManageDistinct;
}
