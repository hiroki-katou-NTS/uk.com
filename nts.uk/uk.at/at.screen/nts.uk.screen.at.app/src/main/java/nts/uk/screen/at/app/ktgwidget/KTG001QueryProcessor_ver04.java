
package nts.uk.screen.at.app.ktgwidget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.management.RuntimeErrorException;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTarget;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedDataExecutionResultDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.ClosureIdPresentClosingPeriod;

/**
 * 
 * @author tutt
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class KTG001QueryProcessor_ver04 {

	@Inject
	private ApproveWidgetRepository approveWidgetRepository;

	@Inject
	private WorkClosureQueryProcessor workClosureQueryProcessor;

	@Inject
	private ShClosurePub shClosurePub;

	@Inject
	private RoleExportRepoAdapter roleExportRepoAdapter;

	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;

	@Inject
	private ApplicationRepository applicationRepository_New;

	@Inject
	private InitDisplayPeriodSwitchSetFinder displayPeriodfinder;

	/**
	 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.承認すべきデータのウィジェットを起動する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param yearMonth
	 * @return
	 */
	public ApprovedDataExecutionResultDto getApprovedDataExecutionResult(String companyId, String employeeId,
			int yearMonth) {
		ApprovedDataExecutionResultDto approvedDataExecutionResult = new ApprovedDataExecutionResultDto();
		List<ClosureIdPresentClosingPeriod> closingPeriods = new ArrayList<>();

		// 1. 指定するウィジェットの設定を取得する
		StandardWidget standardWidget = approveWidgetRepository.findByCompanyId(companyId);

		// 2. 全ての締めの処理年月と締め期間を取得する
		closingPeriods = getClosureIdPresentClosingPeriods(companyId);

		// 3. 全ての承認すべき情報を取得する

		// 3.1.承認すべき申請データの取得
		List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList = standardWidget
				.getApprovedAppStatusDetailedSettingList();

		ApprovedAppStatusDetailedSetting applicationDataSetting = approvedAppStatusDetailedSettingList.stream()
				.filter(a -> a.getItem().value == ApprovedApplicationStatusItem.APPLICATION_DATA.value)
				.collect(Collectors.toList()).get(0);

		if (applicationDataSetting.getDisplayType().value == NotUseAtr.NOT_USE.value) {
			approvedDataExecutionResult.setAppDisplayAtr(false);
		} else {

			// 承認すべき申請の対象期間を取得する
			PresentClosingPeriodImport periodImport = getPeriod(closingPeriods);

			// 承認すべき申請データ有無表示_（3次用）
			approvedDataExecutionResult.setAppDisplayAtr(getAppDisplayAtr(periodImport, employeeId, companyId));
		}

		// 3.2. 日別実績の承認すべきデータの取得
		ApprovedAppStatusDetailedSetting dailyPerformanceDataSetting = approvedAppStatusDetailedSettingList.stream()
				.filter(a -> a.getItem().value == ApprovedApplicationStatusItem.DAILY_PERFORMANCE_DATA.value)
				.collect(Collectors.toList()).get(0);

		if (dailyPerformanceDataSetting.getDisplayType().value == NotUseAtr.NOT_USE.value) {
			approvedDataExecutionResult.setDayDisplayAtr(false);
		} else {

			// トップページの設定により対象年月と締めIDを取得する
			// ユーザー固有情報「トップページ表示年月」を取得する
			List<CheckTarget> listCheckTargetItem = new ArrayList<>();

		}

		// 4. ログイン者が担当者か判断する

		approvedDataExecutionResult.setHaveParticipant(haveParticipant());
		approvedDataExecutionResult.setTopPagePartName(standardWidget.getName().v());
		approvedDataExecutionResult.setClosingPeriods(closingPeriods);
		approvedDataExecutionResult
				.setApprovedAppStatusDetailedSettings(standardWidget.getApprovedAppStatusDetailedSettingList());

		return approvedDataExecutionResult;
	}

	/**
	 * 2. 全ての締めの処理年月と締め期間を取得する
	 * 
	 * @param companyId
	 * @return
	 */
	public List<ClosureIdPresentClosingPeriod> getClosureIdPresentClosingPeriods(String companyId) {

		List<ClosureIdPresentClosingPeriod> closingPeriods = new ArrayList<>();

		// アルゴリズム「会社の締めを取得する」を実行する
		List<ClosureResultModel> closureResultModels = workClosureQueryProcessor
				.findClosureByReferenceDate(GeneralDate.today());
		List<Integer> closureIds = closureResultModels.stream().map(c -> c.getClosureId()).collect(Collectors.toList());

		// 取得した締めIDのリストでループする
		for (Integer closureId : closureIds) {

			// アルゴリズム「処理年月と締め期間を取得する」を実行する
			Optional<PresentClosingPeriodExport> presentClosingPeriod = shClosurePub.find(companyId, closureId);

			if (presentClosingPeriod.isPresent()) {
				ClosureIdPresentClosingPeriod closingPeriod = new ClosureIdPresentClosingPeriod();
				closingPeriod.setClosureId(closureId);
				closingPeriod.setCurrentClosingPeriod(new PresentClosingPeriodImport(
						presentClosingPeriod.get().getProcessingYm(), presentClosingPeriod.get().getClosureStartDate(),
						presentClosingPeriod.get().getClosureEndDate()));

				closingPeriods.add(closingPeriod);
			}
		}
		return closingPeriods;
	}

	/**
	 * 承認すべき申請の対象期間を取得する
	 * 
	 * @return
	 */
	public PresentClosingPeriodImport getPeriod(List<ClosureIdPresentClosingPeriod> closingPeriods) {

		List<GeneralDate> startDates = closingPeriods.stream()
				.map(c -> c.getCurrentClosingPeriod().getClosureStartDate()).collect(Collectors.toList());

		// 一番小さい締め開始日
		Optional<GeneralDate> startDate = startDates.stream().min(Comparator.comparing(GeneralDate::date));

		if (!startDate.isPresent()) {
			throw new RuntimeErrorException(new Error(), "Can't get Start Date");
		} else {
			// 終了日＝開始日 + ２年 - １日
			GeneralDate endDate = startDate.get().addYears(2).addDays(-1);

			YearMonth processingYm = closingPeriods.stream()
					.filter(c -> c.getCurrentClosingPeriod().getClosureStartDate() == startDate.get())
					.map(m -> m.getCurrentClosingPeriod().getProcessingYm()).collect(Collectors.toList()).get(0);

			return new PresentClosingPeriodImport(processingYm, startDate.get(), endDate);
		}
	}

	/**
	 * 承認すべき申請データ有無表示_（3次用）
	 * 
	 * @param periodImport
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	Boolean getAppDisplayAtr(PresentClosingPeriodImport periodImport, String employeeId, String companyId) {

		List<String> listApplicationID = approvalRootStateRepository.resultKTG002Mobile(
				periodImport.getClosureStartDate(), periodImport.getClosureEndDate(), employeeId, 0, companyId);

		// アルゴリズム「申請IDを使用して申請一覧を取得する」を実行する
		List<Application> listApplication = applicationRepository_New.findByListID(companyId, listApplicationID);

		// 「申請」．申請種類＝Input．申請種類 & 「申請」．実績反映状態<>差し戻し に該当する申請が存在するかチェックする
		List<Application> listApplicationFilter = listApplication.stream()
				.filter(c -> c.getAppReflectedState() != ReflectedState.REMAND).collect(Collectors.toList());

		return !listApplicationFilter.isEmpty();
	}

	/**
	 * 4. ログイン者が担当者か判断する
	 * 
	 * @return
	 */
	public Boolean haveParticipant() {
		return roleExportRepoAdapter.getRoleWhetherLogin().isEmployeeCharge();
	}

}