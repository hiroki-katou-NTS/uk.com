package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.HolidayManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@Data
@AllArgsConstructor
public class HolidayManagementDto {

    // 紐づけ管理区分
    private int linkingManagement;
    
    // 振休管理区分
    private int holidayManagement;
    
    public static HolidayManagementDto fromDomain(HolidayManagement domain) {
        return new HolidayManagementDto(
                domain.getLinkingManagement().value, 
                domain.getHolidayManagement().value);
    }
    
    public HolidayManagement toDomain() {
        return new HolidayManagement(
                EnumAdaptor.valueOf(linkingManagement, ManageDistinct.class), 
                EnumAdaptor.valueOf(holidayManagement, ManageDistinct.class));
    }
}
