package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationInsertCmd;
import nts.uk.ctx.at.request.app.command.application.common.ApplicationUpdateCmd;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.WorkInformationDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanhpv
 *
 */
@Getter
@NoArgsConstructor
public class AbsenceLeaveAppCmd extends RecruitmentAppCmd{
	
	public Boolean workChangeUse;
	
	public String changeSourceHoliday;
	
	public List<PayoutSubofHDManagementDto> payoutSubofHDManagements;
	
	public List<PayoutSubofHDManagementDto> payoutSubofHDManagementsOld;
	
	/** Use for Insert KAF011A */
	public AbsenceLeaveApp toDomainInsertAbs() {
		return new AbsenceLeaveApp(
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				workInformation.toDomain(), 
				NotUseAtr.valueOf(this.workChangeUse?1:0), 
				StringUtils.isEmpty(this.changeSourceHoliday) ? Optional.empty() : Optional.of(GeneralDate.fromString(this.changeSourceHoliday, "yyyy/MM/dd")), 
				TypeApplicationHolidays.Abs, 
				applicationInsert.toDomain());
	}

	/** Use for update KAF011B */
	public AbsenceLeaveApp toDomainUpdateAbs(ApplicationDto applicationDto) {
		return new AbsenceLeaveApp(
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				workInformation.toDomain(), 
				NotUseAtr.valueOf(this.workChangeUse?1:0), 
				StringUtils.isEmpty(this.changeSourceHoliday) ? Optional.empty() : Optional.of(GeneralDate.fromString(this.changeSourceHoliday, "yyyy/MM/dd")), 
				TypeApplicationHolidays.Abs, 
				applicationUpdate.toDomain(applicationDto));
	}
	
	public AbsenceLeaveApp toDomainAbs() {
	    return new AbsenceLeaveApp(
                this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
                workInformation.toDomain(), 
                NotUseAtr.valueOf(this.workChangeUse?1:0), 
                StringUtils.isEmpty(this.changeSourceHoliday) ? Optional.empty() : Optional.of(GeneralDate.fromString(this.changeSourceHoliday, "yyyy/MM/dd")), 
                TypeApplicationHolidays.Abs, 
                application.toDomain());
	}
	
	public static AbsenceLeaveAppCmd fromDomain(AbsenceLeaveApp domain) {
		return new AbsenceLeaveAppCmd(
				ApplicationDto.fromDomain(domain),
				null,
				null,
				WorkInformationDto.fromDomain(domain.getWorkInformation()),
				domain.getWorkingHours().stream().map(c->TimeZoneWithWorkNoDto.fromDomain(c)).collect(Collectors.toList()),
				new ArrayList<>(),
				domain.getWorkChangeUse().value,
				domain.getChangeSourceHoliday().map(c->c.toString()).orElse(null),
				new ArrayList<>());
	}

	public AbsenceLeaveAppCmd(ApplicationDto applicationDto, ApplicationInsertCmd applicationInsert,
			ApplicationUpdateCmd applicationUpdate, WorkInformationDto workInformation, 
			List<TimeZoneWithWorkNoDto> workingHours, List<LeaveComDayOffManaDto> leaveComDayOffMana,
			Integer workChangeUse, String changeSourceHoliday,
			List<PayoutSubofHDManagementDto> payoutSubofHDManagements) {
		super(applicationDto, applicationInsert, applicationUpdate, workInformation, workingHours, leaveComDayOffMana);
		this.workChangeUse = workChangeUse == 1;
		this.changeSourceHoliday = changeSourceHoliday;
		this.payoutSubofHDManagements = payoutSubofHDManagements;
	}

}
