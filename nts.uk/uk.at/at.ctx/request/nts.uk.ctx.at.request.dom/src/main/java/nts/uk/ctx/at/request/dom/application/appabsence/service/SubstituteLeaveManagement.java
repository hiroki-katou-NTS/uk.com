package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 
 * @author anhnm
 * 代休管理
 * 
 */
@AllArgsConstructor
@Getter
public class SubstituteLeaveManagement {
    // 時間代休消化単位
    private TimeDigestiveUnit timeDigestiveUnit;
    
    // 時間代休管理区分
    private ManageDistinct timeAllowanceManagement;
    
    // 紐づけ管理区分
    private ManageDistinct linkingManagement;
    
    // 代休管理区分
    private ManageDistinct substituteLeaveManagement;
}
