package nts.uk.ctx.at.record.pubimp.workrecord.remainingnumbermanagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.TempReserveLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.TempReserveLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.NumberOfRemainingHolidaysPub;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class NumberOfRemainingHolidayPubImp implements NumberOfRemainingHolidaysPub {
	
	@Inject
	private GetClosureStartForEmployee closureStartForEmployee;
	
	@Inject
	private GetAnnAndRsvRemNumWithinPeriod getAnnAndRsvRemNumWithinPeriod;
	
	@Inject
	private TempReserveLeaveMngRepository tempReserveLeaveMngRepo;
	
	@Override
	public int NumberOfRemainingHolidays(String employeeId, GeneralDate referenceDate) {

		String companyId = AppContexts.user().companyId();
		Optional<GeneralDate> closingDate = closureStartForEmployee.algorithm(employeeId);
		
		if(!closingDate.isPresent()) {
			throw new RuntimeException("集計開始日 Not Present");
		}
		AggrResultOfAnnAndRsvLeave numberOfHolidaysRemaining = getAnnAndRsvRemNumWithinPeriod.algorithm(companyId, employeeId, new DatePeriod(closingDate.get(), referenceDate), 
																										InterimRemainMngMode.OTHER, referenceDate, false, 
																										false, Optional.empty(), 
																										Optional.empty(), Optional.empty(), Optional.empty(), 
																										Optional.empty(), Optional.empty(), Optional.empty());
		if(!numberOfHolidaysRemaining.getReserveLeave().isPresent()) {
			throw new RuntimeException("積立年休  Not Present");
		}
		ReserveLeaveInfo yearHolidayInformation = numberOfHolidaysRemaining.getReserveLeave().get().getAsOfPeriodEnd();
		List<ReserveLeaveGrantRemainingData> numberOfGrantedData = numberOfHolidaysRemaining.getReserveLeave().get().getAsOfPeriodEnd().getGrantRemainingNumberList().stream().filter(c ->c.getExpirationStatus().value == LeaveExpirationStatus.EXPIRED.value).collect(Collectors.toList());
		
		List<TempReserveLeaveManagement> yearHolidayManagementData = tempReserveLeaveMngRepo.findByEmployeeId(employeeId);
		
		return 0;
	}

}
