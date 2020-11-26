package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AccumulatedRestManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@Data
@AllArgsConstructor
public class AccumulatedRestManagementDto {

 // 積休管理区分
    private int accumulatedManage;
    
    public static AccumulatedRestManagementDto fromDomain(AccumulatedRestManagement domain) {
        return new AccumulatedRestManagementDto(domain.getAccumulatedManage().value);
    }
    
    public AccumulatedRestManagement toDomain() {
        return new AccumulatedRestManagement(EnumAdaptor.valueOf(accumulatedManage, ManageDistinct.class));
    }
}
