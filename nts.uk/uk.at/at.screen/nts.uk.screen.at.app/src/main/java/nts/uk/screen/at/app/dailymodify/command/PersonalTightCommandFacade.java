package nts.uk.screen.at.app.dailymodify.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ApprovalStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.approval.ApprovalStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmStatusActualDayChange;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.ParamRegisterConfirmMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.RegisterConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm.SelfConfirm;
import nts.uk.ctx.workflow.dom.approverstatemanagement.DailyConfirmAtr;
import nts.uk.ctx.workflow.pub.resultrecord.EmpPerformMonthParam;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppRootSttMonthExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.Request533Export;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalConfirmCache;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalStatusActualResultKDW003Dto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ConfirmStatusActualResultKDW003Dto;
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
	
	@Inject
	private IntermediateDataPub intermediateDataPub;
	
	//月の本人確認を登録する
	public ApprovalConfirmCache addRemovePersonalTight(String employeeId, GeneralDate date, ApprovalConfirmCache approvalConfirmCache, DPCorrectionStateParam stateParam, boolean isRegister) {
		String companyId = AppContexts.user().companyId();
		if(stateParam == null || stateParam.getDateInfo() == null) {
			return createCache(approvalConfirmCache, companyId);
		}
		DatePeriodInfo dateInfo = stateParam.getDateInfo();
		AggrPeriodClosure aggrPeriod = dateInfo.getLstClosureCache().stream()
				.filter(x -> x.getClosureId() == dateInfo.getClosureId())
				.findFirst().orElse(null);
		
		if(aggrPeriod == null) return createCache(approvalConfirmCache, companyId);
		//アルゴリズム「月の本人確認を登録する」を実行する
		if (isRegister) {
			registerConfirmationMonth.registerConfirmationMonth(new ParamRegisterConfirmMonth(
					YearMonth.of(aggrPeriod.getYearMonth()), Arrays.asList(new SelfConfirm(employeeId, isRegister)),
					aggrPeriod.getClosureId(), new ClosureDate(aggrPeriod.getClosureDate().getCloseDay(),
							aggrPeriod.getClosureDate().getLastDayOfMonthValue()),
					GeneralDate.today()));
		} else {
			//[No.533](中間データ版)承認対象者リストと日付リストから承認状況を取得する（月別）
			EmpPerformMonthParam param = new EmpPerformMonthParam(YearMonth.of(aggrPeriod.getYearMonth()),
					aggrPeriod.getClosureId(), aggrPeriod.getClosureDate().convertToClosureDateDto(), aggrPeriod.getPeriod().getEndDate(),
					employeeId);
			Request533Export export = intermediateDataPub.getAppRootStatusByEmpsMonth(Arrays.asList(param));
			if (export == null || export.getAppRootSttMonthExportLst().isEmpty())
				return null;
			AppRootSttMonthExport appRootExport = export.getAppRootSttMonthExportLst().get(0);
		
			if(appRootExport.getDailyConfirmAtr() != DailyConfirmAtr.UNAPPROVED.value) {
				throw new BusinessException("Msg_1501");
			}
			
			registerConfirmationMonth.registerConfirmationMonth(new ParamRegisterConfirmMonth(
					YearMonth.of(aggrPeriod.getYearMonth()), Arrays.asList(new SelfConfirm(employeeId, isRegister)),
					aggrPeriod.getClosureId(), new ClosureDate(aggrPeriod.getClosureDate().getCloseDay(),
							aggrPeriod.getClosureDate().getLastDayOfMonthValue()),
					GeneralDate.today()));
		}
		
		return createCache(approvalConfirmCache, companyId);

	}
	
	private ApprovalConfirmCache createCache(ApprovalConfirmCache cache, String companyId) {
		
		String sId = AppContexts.user().employeeId();
		
		List<ConfirmStatusActualResult> confirmResults = confirmStatusActualDayChange.processConfirmStatus(companyId,
				sId, cache.getEmployeeIds(), Optional.of(cache.getPeriod().convertToPeriod()), Optional.empty());
		List<ApprovalStatusActualResult> approvalResults = approvalStatusActualDayChange.processApprovalStatus(
				companyId, sId, cache.getEmployeeIds(), Optional.of(cache.getPeriod().convertToPeriod()), Optional.empty(),
				cache.getMode());

		List<ConfirmStatusActualResultKDW003Dto> lstConfirmStatusActualResultKDW003Dto = confirmResults.stream().map(c->ConfirmStatusActualResultKDW003Dto.fromDomain(c)).collect(Collectors.toList());
		List<ApprovalStatusActualResultKDW003Dto> lstApprovalStatusActualResultKDW003Dto = approvalResults.stream().map(c->ApprovalStatusActualResultKDW003Dto.fromDomain(c)).collect(Collectors.toList());
		ApprovalConfirmCache cacheNew = new ApprovalConfirmCache(AppContexts.user().employeeId(),
				cache.getEmployeeIds(), cache.getPeriod(), cache.getMode(),
				lstConfirmStatusActualResultKDW003Dto, lstApprovalStatusActualResultKDW003Dto);
		return cacheNew;
	}
}
