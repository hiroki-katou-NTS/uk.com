
package nts.uk.screen.at.app.ktgwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedDataExecutionResultDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.ClosureIdPresentClosingPeriod;
import nts.uk.shr.com.context.AppContexts;

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

		// 3. 全ての承認すべき情報を取得する
		// 3.1.承認すべき申請データの取得

		// 4. ログイン者が担当者か判断する
		if (roleExportRepoAdapter.getRoleWhetherLogin().isEmployeeCharge()) {
			approvedDataExecutionResult.setHaveParticipant(true);
		} else {
			approvedDataExecutionResult.setHaveParticipant(false);
		}

		approvedDataExecutionResult.setTopPagePartName(standardWidget.getName().v());
		approvedDataExecutionResult.setClosingPeriods(closingPeriods);
		approvedDataExecutionResult
				.setApprovedAppStatusDetailedSettings(standardWidget.getApprovedAppStatusDetailedSettingList());

		return approvedDataExecutionResult;
	}
}