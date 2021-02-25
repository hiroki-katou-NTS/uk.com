package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * 
 * @author anhnm
 * 振休管理
 * 
 */
@AllArgsConstructor
@Getter
public class HolidayManagement {
    
    // 紐づけ管理区分
    private ManageDistinct linkingManagement;
    
    // 振休管理区分
    private ManageDistinct holidayManagement;
}
