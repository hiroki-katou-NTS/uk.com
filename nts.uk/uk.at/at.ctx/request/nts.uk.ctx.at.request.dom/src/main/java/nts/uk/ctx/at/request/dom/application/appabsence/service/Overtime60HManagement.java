package nts.uk.ctx.at.request.dom.application.appabsence.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

/**
 * 
 * @author anhnm
 * 60H超休管理
 * 
 */
@AllArgsConstructor
@Getter
public class Overtime60HManagement {

    // 60H超休管理区分
    private ManageDistinct overrest60HManagement;
    
    // 60H超休消化単位
    private TimeDigestiveUnit super60HDigestion;
}
