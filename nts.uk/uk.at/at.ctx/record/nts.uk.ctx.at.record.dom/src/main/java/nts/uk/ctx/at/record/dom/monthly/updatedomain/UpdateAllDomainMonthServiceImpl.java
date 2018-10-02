package nts.uk.ctx.at.record.dom.monthly.updatedomain;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerErrorRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;

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
	private AgreementTimeOfManagePeriodRepository agreementTimeRepository;
	
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
			
			if(domain.getAgreementTime().isPresent()){
				agreementTimeRepository.persistAndUpdate(domain.getAgreementTime().get());
			}
		});
		
	}

}
