package nts.uk.screen.at.app.ktgwidget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.PresenceDataApprovedImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class KTG001QueryProcessor {
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private ShClosurePub shClosurePub;
	
	/**
	 * 日別実績確認すべきデータ有無表示
	 * @return
	 */
	@Inject
	private DailyPerformanceAdapter dailyPerformanceAdapter;
	
	@Inject
	private ShareEmploymentAdapter shareEmpAdapter;
	
	public PresenceDataApprovedImport confirmDailyActual() {
		// アルゴリズム「雇用に基づく締めを取得する」をする
		String cid = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		
		Optional<BsEmploymentHistoryImport> empHistoryOpt = shareEmpAdapter.findEmploymentHistory(cid, employeeID, GeneralDate.today());
		if(!empHistoryOpt.isPresent()){
			throw new RuntimeException("Not found Employment history by employeeId:" + employeeID);
		}
		
		BsEmploymentHistoryImport empHistory = empHistoryOpt.get();
		
		Optional<ClosureEmployment> closureEmploymentOpt = closureEmploymentRepo.findByEmploymentCD(cid, empHistory.getEmploymentCode());
		if(!closureEmploymentOpt.isPresent()){
			throw new RuntimeException("Not found Employment history by employeeCd:" + empHistory.getEmploymentCode());
		}
		
		int closureID = closureEmploymentOpt.get().getClosureId();
		
		// アルゴリズム「処理年月と締め期間を取得する」を実行する
		Optional<PresentClosingPeriodExport> presentClosingPeriod = shClosurePub.find(cid, closureID);
		YearMonth processingYm 			=  presentClosingPeriod.get().getProcessingYm();
		GeneralDate closureStartDate 	=  presentClosingPeriod.get().getClosureStartDate();
		GeneralDate closureEndDate      =  presentClosingPeriod.get().getClosureEndDate();

		// "Acquire 「日別実績確認有無取得」"
			/*input
			· Employee ID
			· Date (start date) <= Tightening start date
			· Date (end date) <= closing end date + 1 month
			· Route type <= Employment application*/
		PresenceDataApprovedImport result = dailyPerformanceAdapter.findByIdDateAndType(employeeID, closureStartDate, closureEndDate.addMonths(1), 0);
		
		// RoleType.EMPLOYMENT = 3
		boolean checkDateApproved = dailyPerformanceAdapter.checkDataApproveed(closureStartDate, closureEndDate, employeeID, 3, cid);
		
		return result;
	}
}
