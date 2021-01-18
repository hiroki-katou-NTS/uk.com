package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.Overtime60HManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

@Data
@AllArgsConstructor
public class Overtime60HManagementDto {

    // 60H超休管理区分
    private int overrest60HManagement;
    
    // 60H超休消化単位
    private int super60HDigestion;
    
    public static Overtime60HManagementDto fromDomain(Overtime60HManagement domain) {
        return new Overtime60HManagementDto(
                domain.getOverrest60HManagement().value, 
                domain.getSuper60HDigestion().value);
    }
    
    public Overtime60HManagement toDomain() {
        return new Overtime60HManagement(
                EnumAdaptor.valueOf(overrest60HManagement, ManageDistinct.class), 
                EnumAdaptor.valueOf(super60HDigestion, TimeDigestiveUnit.class));
    }
}
