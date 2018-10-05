package nts.uk.ctx.at.record.dom.monthly.updatedomain;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.createmonthlyapprover.CreateMonthlyApproverAdapter;
import nts.uk.ctx.at.record.dom.attendanceitem.StoredProcdureProcess;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerErrorRepository;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecordRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeeklyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateAllDomainMonthServiceImpl implements UpdateAllDomainMonthService {
	
	@Inject
	private EmployeeMonthlyPerErrorRepository empErrorRepo;
	
	@Inject 
	private TimeOfMonthlyRepository timeRepo;
	
	@Inject 
	private RemainMergeRepository remainRepo;
	
	@Inject
	private RemarksMonthlyRecordRepository remarksRepo;
	
	@Inject
	private AttendanceTimeOfWeeklyRepository timeWeekRepo;
	
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementRepo;
	
	@Inject
	private StoredProcdureProcess storedProcedureProcess;
	
	@Inject
	private CreateMonthlyApproverAdapter createMonthlyApproverAd;

	@Override
	public void merge(List<IntegrationOfMonthly> domains, GeneralDate targetDate) {
		String companyId = AppContexts.user().companyId();
		domains.forEach(d -> {
			if(d.getAffiliationInfo().isPresent()){
				timeRepo.persistAndUpdate(new TimeOfMonthly(d.getAttendanceTime(), d.getAffiliationInfo()));
				
				if(d.getAttendanceTime().isPresent()){
					this.createMonthlyApproverAd.createApprovalStatusMonth(
							d.getAffiliationInfo().get().getEmployeeId(), targetDate, d.getAffiliationInfo().get().getYearMonth(), 
							d.getAffiliationInfo().get().getClosureId().value, d.getAffiliationInfo().get().getClosureDate());
				}
				
				d.getEmployeeMonthlyPerErrorList().forEach(x -> empErrorRepo.insertAll(x));
				
				d.getAttendanceTimeOfWeekList().stream().forEach(atw -> this.timeWeekRepo.persistAndUpdate(atw));
				
				this.storedProcedureProcess.monthlyProcessing(
						companyId,
						d.getAffiliationInfo().get().getEmployeeId(),
						d.getAffiliationInfo().get().getYearMonth(),
						d.getAffiliationInfo().get().getClosureId(),
						d.getAffiliationInfo().get().getClosureDate(),
						d.getAttendanceTime(),
						d.getAnyItemList());
				
//				anyTimeRepo.persistAndUpdate(d.getAnyItemList());
				
				d.getAgreementTime().ifPresent(at -> agreementRepo.persistAndUpdate(at));
				
				MonthMergeKey key = createKey(d.getAffiliationInfo().get());
				remainRepo.persistAndUpdate(key, getRemains(d, key));
				
				d.getRemarks().forEach(r -> remarksRepo.persistAndUpdate(r));
			}
		});
	}
	
	private MonthMergeKey createKey(AffiliationInfoOfMonthly d) {
		MonthMergeKey key = new MonthMergeKey();
		
		key.setClosureId(d.getClosureId());
		key.setClosureDate(d.getClosureDate());
		key.setEmployeeId(d.getEmployeeId());
		key.setYearMonth(d.getYearMonth());
		
		return key;
	}
	
	private RemainMerge getRemains(IntegrationOfMonthly d, MonthMergeKey key) {
		RemainMerge remains = new RemainMerge();
		
		remains.setAbsenceLeaveRemainData(d.getAbsenceLeaveRemain().orElse(null));
		remains.setAnnLeaRemNumEachMonth(d.getAnnualLeaveRemain().orElse(null));
		remains.setMonCareHdRemain(d.getCare().orElse(null));
		remains.setMonChildHdRemain(d.getChildCare().orElse(null));
		remains.setMonthlyDayoffRemainData(d.getMonthlyDayoffRemain().orElse(null));
		remains.setMonthMergeKey(key);
		remains.setRsvLeaRemNumEachMonth(d.getReserveLeaveRemain().orElse(null));
		remains.setSpecialHolidayRemainDataMerge(d.getSpecialLeaveRemainList());
		
		return remains;
	}

}
