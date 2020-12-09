package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	
	public AbsenceLeaveApp toDomain() {
		return new AbsenceLeaveApp(
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				workInformation.toDomain(), 
				NotUseAtr.valueOf(this.workChangeUse), 
				StringUtils.isEmpty(this.changeSourceHoliday) ? Optional.empty() : Optional.of(GeneralDate.fromString(this.changeSourceHoliday, "yyyy/MM/dd")), 
				TypeApplicationHolidays.Abs, 
				application.toDomain());
	}

}
