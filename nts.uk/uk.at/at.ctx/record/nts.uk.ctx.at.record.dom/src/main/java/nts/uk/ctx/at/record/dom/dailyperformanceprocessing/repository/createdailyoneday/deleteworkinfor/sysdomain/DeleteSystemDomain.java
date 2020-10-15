package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.deleteworkinfor.sysdomain;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;

/**
 * 「情報」系のドメイン削除する
 * @author tutk
 *
 */
@Stateless
public class DeleteSystemDomain {
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private AffiliationInforOfDailyPerforRepository affInforOfDailyPerforRepo;
	
	@Inject
	private SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDaily;
	
	@Inject
	private CalAttrOfDailyPerformanceRepository calAttrOfDaily;
	
	public void delete(String companyId,String employeeId,GeneralDate ymd) {
		//「日別実績の勤務情報」を削除する
		workInformationRepository.delete(employeeId, ymd);
		//「日別実績の所属情報」を削除する
		affInforOfDailyPerforRepo.delete(employeeId, ymd);
		//「日別実績の特定日」を削除する
		specificDateAttrOfDaily.deleteByEmployeeIdAndDate(employeeId, ymd);
		//「日別実績の計算区分」を削除する
		calAttrOfDaily.deleteByKey(employeeId, ymd);
	}

}
