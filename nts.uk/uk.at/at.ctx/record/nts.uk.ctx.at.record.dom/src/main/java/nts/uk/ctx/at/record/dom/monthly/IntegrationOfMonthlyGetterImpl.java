package nts.uk.ctx.at.record.dom.monthly;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthlyGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.EditStateOfMonthlyPerRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMergeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remarks.RemarksMonthlyRecordRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class IntegrationOfMonthlyGetterImpl implements IntegrationOfMonthlyGetter {

	@Inject 
	private TimeOfMonthlyRepository timeOfMonthlyRepo;
	
	@Inject
	private RemainMergeRepository remainMergeRep;
	
	@Inject
	private AnyItemOfMonthlyRepository anyItemOfMonthlyRepo;
	
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;
	
	@Inject
	private AttendanceTimeOfWeeklyRepository attendanceTimeOfWeeklyRepo;
	
	@Inject
	private RemarksMonthlyRecordRepository remarksMonthlyRecordRepo;
	
	@Inject
	private EditStateOfMonthlyPerRepository rditStateOfMonthlyPerRepo;
	
	@Override
	public IntegrationOfMonthly get(String sid, YearMonth ym, ClosureId closureId, ClosureDate closureDate) {
		
		val time = timeOfMonthlyRepo.find(sid, ym, closureId, closureDate);
		val remain = remainMergeRep.find(sid, ym, closureId, closureDate);
		val anyItem = anyItemOfMonthlyRepo.findByEmployees(Arrays.asList(sid), ym, closureId, closureDate);
		val agreementTime = agreementTimeOfManagePeriodRepo.find(sid, ym);
		val weekTime = attendanceTimeOfWeeklyRepo.findByClosure(sid, ym, closureId, closureDate);
		val remarks = remarksMonthlyRecordRepo.find(sid, ym, closureId, closureDate);
		val editState = rditStateOfMonthlyPerRepo.findByClosure(sid, ym, closureId, closureDate);
		
		return new IntegrationOfMonthly(time.flatMap(c -> c.getAttendanceTime()), time.flatMap(c -> c.getAffiliation()), 
				anyItem, agreementTime, remain.map(c -> c.getAnnLeaRemNumEachMonth()), remain.map(c -> c.getRsvLeaRemNumEachMonth()),
				remain.map(c -> c.getAbsenceLeaveRemainData()), remain.map(c -> c.getMonthlyDayoffRemainData()), 
				remain.map(c -> c.getSpecialHolidayRemainData()).orElse(new ArrayList<>()), weekTime, new ArrayList<>(), 
				remarks, remain.map(c -> c.getMonCareHdRemain()), remain.map(c -> c.getMonChildHdRemain())
				, remain.map(c -> c.getMonPublicHoliday()), editState);
	}

}
