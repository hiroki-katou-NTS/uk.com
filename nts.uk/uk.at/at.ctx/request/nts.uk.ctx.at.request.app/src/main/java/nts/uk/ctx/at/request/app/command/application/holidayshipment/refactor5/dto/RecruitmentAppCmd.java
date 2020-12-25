package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationInsertCmd;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationUpdateCmd;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;

/**
 * @author thanhpv
 *
 */
@Getter
@AllArgsConstructor
public class RecruitmentAppCmd {
	
	public String appID;
	
	/** For KAF011A */
	public ApplicationInsertCmd applicationInsert;
	
	/** For KAF011B */
	public ApplicationUpdateCmd applicationUpdate;
	
	public List<TimeZoneWithWorkNoDto> workingHours;
	
	public WorkInformationDto workInformation;
	
	public List<LeaveComDayOffManaDto> leaveComDayOffMana;
	
	public List<LeaveComDayOffManaDto> leaveComDayOffManaOld;
	
	/** Use for save KAF011A */
	public RecruitmentApp toDomainInsert() {
		return new RecruitmentApp(
				workInformation.toDomain(), 
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				TypeApplicationHolidays.Rec, 
				applicationInsert.toDomain());
	}
	
	/** Use for update KAF011B */
	public RecruitmentApp toDomainUpdate(ApplicationDto applicationDto) {
		return new RecruitmentApp(
				workInformation.toDomain(),
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				TypeApplicationHolidays.Rec, 
				applicationUpdate.toDomain(applicationDto));
	}

}
