package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;

/**
 * @author thanhpv
 *
 */
@Getter
@AllArgsConstructor
public class AbsenceLeaveAppDto {
	
	public ApplicationDto application;
	
	public List<TimeZoneWithWorkNoDto> workingHours;
	
	public WorkInformationDto workInformation;
	
	public Integer workChangeUse;
	
	public String changeSourceHoliday;
	
	public AbsenceLeaveAppDto(AbsenceLeaveApp domain) {
		this.application = ApplicationDto.fromDomain(domain);
		this.workingHours = domain.getWorkingHours().stream().map(c->TimeZoneWithWorkNoDto.fromDomain(c)).collect(Collectors.toList());
		this.workInformation = WorkInformationDto.fromDomain(domain.getWorkInformation());
		this.workChangeUse = domain.getWorkChangeUse().value;
		this.changeSourceHoliday = domain.getChangeSourceHoliday().map(c->c.toString()).orElse(null);
	}

}
