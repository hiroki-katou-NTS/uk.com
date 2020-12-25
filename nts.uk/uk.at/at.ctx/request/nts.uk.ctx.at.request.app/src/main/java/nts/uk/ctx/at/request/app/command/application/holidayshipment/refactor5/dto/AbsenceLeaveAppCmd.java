package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor
public class AbsenceLeaveAppCmd {
	
	public String appID;
	
	/** For KAF011A */
	public ApplicationInsertCmd applicationInsert;
	
	/** For KAF011B */
	public ApplicationUpdateCmd applicationUpdate;
	
	public List<TimeZoneWithWorkNoDto> workingHours;
	
	public WorkInformationDto workInformation;
	
	public Integer workChangeUse;
	
	public String changeSourceHoliday;
	
	public List<LeaveComDayOffManaDto> leaveComDayOffMana;
	
	public List<PayoutSubofHDManagementDto> payoutSubofHDManagements;
	
	public List<LeaveComDayOffManaDto> leaveComDayOffManaOld;
	
	public List<PayoutSubofHDManagementDto> payoutSubofHDManagementsOld;
	
	/** Use for Insert KAF011A */
	public AbsenceLeaveApp toDomainInsert() {
		return new AbsenceLeaveApp(
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				workInformation.toDomain(), 
				NotUseAtr.valueOf(this.workChangeUse), 
				StringUtils.isEmpty(this.changeSourceHoliday) ? Optional.empty() : Optional.of(GeneralDate.fromString(this.changeSourceHoliday, "yyyy/MM/dd")), 
				TypeApplicationHolidays.Abs, 
				applicationInsert.toDomain());
	}

	/** Use for update KAF011B */
	public AbsenceLeaveApp toDomainUpdate(ApplicationDto applicationDto) {
		return new AbsenceLeaveApp(
				this.workingHours.stream().map(c-> c.toDomain()).collect(Collectors.toList()), 
				workInformation.toDomain(), 
				NotUseAtr.valueOf(this.workChangeUse), 
				StringUtils.isEmpty(this.changeSourceHoliday) ? Optional.empty() : Optional.of(GeneralDate.fromString(this.changeSourceHoliday, "yyyy/MM/dd")), 
				TypeApplicationHolidays.Abs, 
				applicationUpdate.toDomain(applicationDto));
	}

}
