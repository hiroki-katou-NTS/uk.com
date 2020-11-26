package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

@AllArgsConstructor
@Data
public class AnualLeaveManagementDto {

 // 時間年休消化単位
    private int timeAnnualLeave;
    
    // 時間年休管理区分
    private int timeAnnualLeaveManage;
    
    // 年休管理区分
    private int annualLeaveManageDistinct;
    
    public static AnualLeaveManagementDto fromDomain(AnualLeaveManagement domain) {
        return new AnualLeaveManagementDto(
                domain.getTimeAnnualLeave().value, 
                domain.getTimeAnnualLeaveManage().value, 
                domain.getAnnualLeaveManageDistinct().value);
    }
    
    public AnualLeaveManagement toDomain() {
        return new AnualLeaveManagement(
                EnumAdaptor.valueOf(timeAnnualLeave, TimeDigestiveUnit.class), 
                EnumAdaptor.valueOf(timeAnnualLeaveManage, ManageDistinct.class), 
                EnumAdaptor.valueOf(annualLeaveManageDistinct, ManageDistinct.class));
    }
}
