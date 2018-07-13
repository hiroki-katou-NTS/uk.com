package nts.uk.screen.at.app.ktgwidget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UseClassification;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	private ClosureRepository closureRepository;
	
	@Inject
	private ClosureService closureService;
	
	public boolean checkDataApprove() {
		
		String cid = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		//get employee code
		Optional<BsEmploymentHistoryImport> empHistoryOpt = shareEmpAdapter.findEmploymentHistory(cid, employeeID, GeneralDate.today());
		if(!empHistoryOpt.isPresent()){
			throw new RuntimeException("Not found Employment history by employeeId:" + employeeID);
		}
		BsEmploymentHistoryImport empHistory = empHistoryOpt.get();
		//get closureID (締めID)
		Optional<ClosureEmployment> closureEmploymentOpt = closureEmploymentRepo.findByEmploymentCD(cid, empHistory.getEmploymentCode());
		int closureID = 1;
		if(closureEmploymentOpt.isPresent()){
			closureID = closureEmploymentOpt.get().getClosureId();
		}
		//Execute the algorithm "Acquire processing year and month"
		Optional<Closure> closure = closureRepository.findClosureHistory(cid, closureID, UseClassification.USE.value);
		if(!closure.isPresent()){
			throw new RuntimeException("Not found closure");
		} 
		YearMonth yearMonth = closure.get().getClosureMonth().getProcessingYm();
		
		DatePeriod datePeriod = closureService.getClosurePeriod(closureID, yearMonth);
		
		GeneralDate startDate = datePeriod.start();
		GeneralDate endDate = datePeriod.end();
		
		// parameter: RootType(就業日別確認) = EMPLOYMENT_APPLICATION(0,"就業申請"),
		boolean checkDateApproved = dailyPerformanceAdapter.checkDataApproveed(startDate, endDate.addMonths(11), employeeID, 0, cid);

		return checkDateApproved;
	}
}
