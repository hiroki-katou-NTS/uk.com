package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 
 * @author anhnm
 * 介護看護休暇管理
 * 
 */
@AllArgsConstructor
@Getter
public class NursingCareLeaveManagement {

    // 子の看護管理区分
    private ManageDistinct childNursingManagement;
    
    // 時間介護の消化単位
    private TimeDigestiveUnit timeCareDigestive;
    
    // 時間介護の管理区分
    private ManageDistinct timeCareManagement;
    
    // 時間子の看護の消化単位
    private TimeDigestiveUnit timeChildNursingDigestive;
    
    // 時間子の看護の管理区分
    private ManageDistinct timeChildNursingManagement;
    
    // 介護管理区分
    private ManageDistinct longTermCareManagement;
}
