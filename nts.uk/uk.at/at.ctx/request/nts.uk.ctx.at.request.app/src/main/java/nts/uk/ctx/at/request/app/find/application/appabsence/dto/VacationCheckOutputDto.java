package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.VacationCheckOutput;

@AllArgsConstructor
@NoArgsConstructor
public class VacationCheckOutputDto {
	 // 代休紐付管理をクリアする
    public boolean clearManageSubsHoliday;
    
    // 振休紐付管理をクリアする
    public boolean clearManageHolidayString;
    
    public static VacationCheckOutputDto fromDomain(VacationCheckOutput output) {
    	return new VacationCheckOutputDto(
    			output.clearManageSubsHoliday,
    			output.clearManageHolidayString);
    }
}
