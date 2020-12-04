package nts.uk.ctx.at.request.app.command.application.holidayshipment;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationInsertCmd;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;

@Getter
@AllArgsConstructor
public class RecruitmentAppCommand {

	public ApplicationInsertCmd application;
	
	public List<TimeZoneWithWorkNoDto> workingHours;
	
	public WorkInformationDto workInformation;

	public RecruitmentApp toDomain() {
		return new RecruitmentApp(
				this.workInformation.toDomain(),
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()),
				TypeApplicationHolidays.Rec, 
				this.application.toDomain());
	}

}
