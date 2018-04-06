package nts.uk.screen.at.app.ktgwidget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhpv
 *
 */
@Stateless
public class KTG002QueryProcessor {
	
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private DailyPerformanceAdapter dailyPerformanceAdapter;
	
	@Inject
	private ShareEmploymentAdapter shareEmpAdapter;

	@Inject
	private ShClosurePub shClosurePub;
	
	public boolean checkDataApprove() {
		
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
		//get closureID (締めID)
		int closureID = closureEmploymentOpt.get().getClosureId();

		// Execute the algorithm "Acquire processing year and month"
		Optional<PresentClosingPeriodExport> presentClosingPeriod = shClosurePub.find(cid, closureID);
		if(!presentClosingPeriod.isPresent()){
			throw new RuntimeException("Not found presentClosingPeriod");
		} 
		
		GeneralDate startDate = presentClosingPeriod.get().getClosureStartDate();
		GeneralDate endDate = presentClosingPeriod.get().getClosureEndDate();
		
		// RootType(就業日別確認) = EMPLOYMENT_APPLICATION(0,"就業申請"),
		boolean checkDateApproved = dailyPerformanceAdapter.checkDataApproveed(startDate, endDate.addMonths(11), employeeID, 0, cid);

		return checkDateApproved;
	}
}
