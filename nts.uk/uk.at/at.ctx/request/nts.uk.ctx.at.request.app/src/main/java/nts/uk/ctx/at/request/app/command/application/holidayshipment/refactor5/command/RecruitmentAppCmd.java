package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class RecruitmentAppCmd {
	
	public ApplicationDto application;
	
	/** For KAF011A */
	public ApplicationInsertCmd applicationInsert;
	
	/** For KAF011B */
	public ApplicationUpdateCmd applicationUpdate;
	
	public WorkInformationDto workInformation;
	
	public List<TimeZoneWithWorkNoDto> workingHours;
	
	public List<LeaveComDayOffManaDto> leaveComDayOffMana;
	
	/** Use for save KAF011A */
	public RecruitmentApp toDomainInsertRec() {
		return new RecruitmentApp(
				workInformation.toDomain(), 
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				TypeApplicationHolidays.Rec, 
				applicationInsert.toDomain());
	}
	
	/** Use for update KAF011B */
	public RecruitmentApp toDomainUpdateRec(ApplicationDto applicationDto) {
		return new RecruitmentApp(
				workInformation.toDomain(),
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				TypeApplicationHolidays.Rec, 
				applicationUpdate.toDomain(applicationDto));
	}
	
	public RecruitmentApp toDomain() {
	    return new RecruitmentApp(
                workInformation.toDomain(), 
                this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
                TypeApplicationHolidays.Rec, 
                application.toDomain());
	}
	
	public static RecruitmentAppCmd fromDomain(RecruitmentApp domain) {
		return new RecruitmentAppCmd(
				ApplicationDto.fromDomain(domain),
				null,
				null,
				WorkInformationDto.fromDomain(domain.getWorkInformation()),
				domain.getWorkingHours().stream().map(c->TimeZoneWithWorkNoDto.fromDomain(c)).collect(Collectors.toList()),
				new ArrayList<>());
	}

}
