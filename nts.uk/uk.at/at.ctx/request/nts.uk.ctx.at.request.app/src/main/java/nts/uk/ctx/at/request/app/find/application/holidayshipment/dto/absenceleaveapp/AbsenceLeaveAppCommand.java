package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationInsertCmd;
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
public class AbsenceLeaveAppCommand {
	
	public ApplicationInsertCmd application;
	
	public List<TimeZoneWithWorkNoDto> workingHours;
	
	public WorkInformationDto workInformation;
	
	public Integer workChangeUse;
	
	public String changeSourceHoliday;
	
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
