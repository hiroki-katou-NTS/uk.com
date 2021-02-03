package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.VacationCheckOutput;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;

@Data
@AllArgsConstructor
public class ChangeWorkTypeResultDto {

    // 代休紐付管理をクリアする
    public Boolean clearManageSubsHoliday;
    
    // 振休紐付管理をクリアする
    public Boolean clearManageHolidayString;
    
    //時間帯(使用区分付き)：optional
	public List<TimeZoneWithWorkNoDto> workingHours;

	public ChangeWorkTypeResultDto(VacationCheckOutput domain, List<TimeZoneWithWorkNoDto> workingHours) {
		super();
		this.clearManageSubsHoliday = domain.clearManageSubsHoliday;
		this.clearManageHolidayString = domain.clearManageHolidayString;
		this.workingHours = workingHours;
	}
    
}
