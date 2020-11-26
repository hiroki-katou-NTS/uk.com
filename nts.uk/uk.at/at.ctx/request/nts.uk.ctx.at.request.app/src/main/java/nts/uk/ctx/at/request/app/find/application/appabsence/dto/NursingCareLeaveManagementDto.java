package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.NursingCareLeaveManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

@Data
@AllArgsConstructor
public class NursingCareLeaveManagementDto {

    // 子の看護管理区分
    private int childNursingManagement;
    
    // 時間介護の消化単位
    private int timeCareDigestive;
    
    // 時間介護の管理区分
    private int timeCareManagement;
    
    // 時間子の看護の消化単位
    private int timeChildNursingDigestive;
    
    // 時間子の看護の管理区分
    private int timeChildNursingManagement;
    
    // 介護管理区分
    private int longTermCareManagement;
    
    public static NursingCareLeaveManagementDto fromDomain(NursingCareLeaveManagement domain) {
        return new NursingCareLeaveManagementDto(
                domain.getChildNursingManagement().value, 
                domain.getTimeCareDigestive().value, 
                domain.getTimeCareManagement().value, 
                domain.getTimeChildNursingDigestive().value, 
                domain.getTimeChildNursingManagement().value, 
                domain.getLongTermCareManagement().value);
    }
    
    public NursingCareLeaveManagement toDomain() {
        return new NursingCareLeaveManagement(
                EnumAdaptor.valueOf(childNursingManagement, ManageDistinct.class), 
                EnumAdaptor.valueOf(timeCareDigestive, TimeDigestiveUnit.class), 
                EnumAdaptor.valueOf(timeCareManagement, ManageDistinct.class), 
                EnumAdaptor.valueOf(timeChildNursingDigestive, TimeDigestiveUnit.class), 
                EnumAdaptor.valueOf(timeChildNursingManagement, ManageDistinct.class), 
                EnumAdaptor.valueOf(longTermCareManagement, ManageDistinct.class));
    }
}
