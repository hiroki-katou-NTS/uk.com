package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.SubstituteLeaveManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

@Data
@AllArgsConstructor
public class SubstituteLeaveManagementDto {

 // 時間代休消化単位
    private int timeDigestiveUnit;
    
    // 時間代休管理区分
    private int timeAllowanceManagement;
    
    // 紐づけ管理区分
    private int linkingManagement;
    
    // 代休管理区分
    private int substituteLeaveManagement;
    
    public static SubstituteLeaveManagementDto fromDomain(SubstituteLeaveManagement domain) {
        return new SubstituteLeaveManagementDto(
                domain.getTimeDigestiveUnit().value, 
                domain.getTimeAllowanceManagement().value, 
                domain.getLinkingManagement().value, 
                domain.getSubstituteLeaveManagement().value);
    }
    
    public SubstituteLeaveManagement toDomain() {
        return new SubstituteLeaveManagement(
                EnumAdaptor.valueOf(timeDigestiveUnit, TimeDigestiveUnit.class), 
                EnumAdaptor.valueOf(timeAllowanceManagement, ManageDistinct.class), 
                EnumAdaptor.valueOf(linkingManagement, ManageDistinct.class), 
                EnumAdaptor.valueOf(substituteLeaveManagement, ManageDistinct.class));
    }
}
