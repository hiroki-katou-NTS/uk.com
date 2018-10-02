package nts.uk.ctx.at.record.dom.monthly.updatedomain;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerErrorRepository;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.dom.monthly.remarks.RemarksMonthlyRecordRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeeklyRepository;

@Stateless
public class UpdateAllDomainMonthServiceImpl implements UpdateAllDomainMonthService {

	@Inject
	private AffiliationInfoOfMonthlyRepository affRepo;
	
	@Inject
	private AttendanceTimeOfMonthlyRepository attTimeRepo;
	
	@Inject
	private AnyItemOfMonthlyRepository anyTimeRepo;
	
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRepo;
	
	@Inject
	private RsvLeaRemNumEachMonthRepository rsvLearepo;
	
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
	
	@Override
	public void insertUpdateAll(List<IntegrationOfMonthly> domains) {
		domains.forEach(domain ->{
			
			if(domain.getAttendanceTime().isPresent()){
				attTimeRepo.persistAndUpdate(domain.getAttendanceTime().get(), domain.getAffiliationInfo());
			}
			
			if(!CollectionUtil.isEmpty(domain.getAnyItemList())){
				domain.getAnyItemList().forEach(any ->{
					anyTimeRepo.persistAndUpdate(any);
				});
			}
			
			if(domain.getAnnualLeaveRemain().isPresent()){
				annLeaRepo.persistAndUpdate(domain.getAnnualLeaveRemain().get());
			}
			
			if(domain.getReserveLeaveRemain().isPresent()){
				rsvLearepo.persistAndUpdate(domain.getReserveLeaveRemain().get());
			}
			
			if(!CollectionUtil.isEmpty(domain.getEmployeeMonthlyPerErrorList())){
				domain.getEmployeeMonthlyPerErrorList().forEach(x ->{
					empErrorRepo.insertAll(x);
				});
			}
		});
		
	}

	@Override
	public void merge(List<IntegrationOfMonthly> domains) {
		domains.forEach(d -> {
			if(d.getAffiliationInfo().isPresent()){
				timeRepo.persistAndUpdate(new TimeOfMonthly(d.getAttendanceTime(), d.getAffiliationInfo()));
				
				MonthMergeKey key = createKey(d.getAffiliationInfo().get());
				remainRepo.persistAndUpdate(key, getRemains(d, key));
				
				d.getEmployeeMonthlyPerErrorList().forEach(x -> empErrorRepo.insertAll(x));
				
				anyTimeRepo.persistAndUpdate(d.getAnyItemList());
				
				d.getRemarks().forEach(r -> remarksRepo.persistAndUpdate(r));
				
				d.getAttendanceTimeOfWeekList().forEach(tw -> timeWeekRepo.persistAndUpdate(tw));
				
				d.getAgreementTime().ifPresent(at -> agreementRepo.persistAndUpdate(at));
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
