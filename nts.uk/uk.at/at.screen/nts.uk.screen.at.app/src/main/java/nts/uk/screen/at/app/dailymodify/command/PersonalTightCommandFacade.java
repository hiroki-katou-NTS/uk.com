package nts.uk.screen.at.app.dailymodify.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ApprovalStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.approval.ApprovalStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmStatusActualDayChange;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.ParamRegisterConfirmMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.RegisterConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.SelfConfirm;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalConfirmCache;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DatePeriodInfo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.AggrPeriodClosure;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class PersonalTightCommandFacade {
	
	@Inject
	private RegisterConfirmationMonth registerConfirmationMonth;
	
	@Inject
	private ApprovalStatusActualDayChange approvalStatusActualDayChange;
	
	@Inject
	private ConfirmStatusActualDayChange confirmStatusActualDayChange;

	
	//月の本人確認を登録する
	public ApprovalConfirmCache addRemovePersonalTight(String employeeId, GeneralDate date, ApprovalConfirmCache approvalConfirmCache, DPCorrectionStateParam stateParam, boolean isRegister) {
		String companyId = AppContexts.user().companyId();
		if(stateParam == null || stateParam.getDateInfo() == null) {
			return createCache(approvalConfirmCache, companyId);
		}
		DatePeriodInfo dateInfo = stateParam.getDateInfo();
		AggrPeriodClosure aggrPeriod = dateInfo.getLstClosureCache().stream()
				.filter(x -> x.getClosureId() == dateInfo.getClosureId() && x.getYearMonth() == dateInfo.getYearMonth())
				.findFirst().orElse(null);
		
		if(aggrPeriod == null) return createCache(approvalConfirmCache, companyId);
		//アルゴリズム「月の本人確認を登録する」を実行する
		if (isRegister) {
			registerConfirmationMonth.registerConfirmationMonth(new ParamRegisterConfirmMonth(
					YearMonth.of(aggrPeriod.getYearMonth()), Arrays.asList(new SelfConfirm(employeeId, isRegister)),
					aggrPeriod.getClosureId().value, new ClosureDate(aggrPeriod.getClosureDate().getClosureDay().v(),
							aggrPeriod.getClosureDate().getLastDayOfMonth()),
					GeneralDate.today()));
		} else {
			registerConfirmationMonth.registerConfirmationMonth(new ParamRegisterConfirmMonth(
					YearMonth.of(aggrPeriod.getYearMonth()), Arrays.asList(new SelfConfirm(employeeId, isRegister)),
					aggrPeriod.getClosureId().value, new ClosureDate(aggrPeriod.getClosureDate().getClosureDay().v(),
							aggrPeriod.getClosureDate().getLastDayOfMonth()),
					GeneralDate.today()));
		}
		
		return createCache(approvalConfirmCache, companyId);

	}
	
	private ApprovalConfirmCache createCache(ApprovalConfirmCache cache, String companyId) {
		
		String sId = AppContexts.user().employeeId();
		
		List<ConfirmStatusActualResult> confirmResults = confirmStatusActualDayChange.processConfirmStatus(companyId,
				sId, cache.getEmployeeIds(), Optional.of(cache.getPeriod()), Optional.empty());
		List<ApprovalStatusActualResult> approvalResults = approvalStatusActualDayChange.processApprovalStatus(
				companyId, sId, cache.getEmployeeIds(), Optional.of(cache.getPeriod()), Optional.empty(),
				cache.getMode());

		ApprovalConfirmCache cacheNew = new ApprovalConfirmCache(AppContexts.user().employeeId(),
				cache.getEmployeeIds(), cache.getPeriod(), cache.getMode(),
				confirmResults, approvalResults);
		return cacheNew;
	}
}
