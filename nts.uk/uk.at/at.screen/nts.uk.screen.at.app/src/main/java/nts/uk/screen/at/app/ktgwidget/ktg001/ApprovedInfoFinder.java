package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.全ての承認すべき情報を取得する.全ての承認すべき情報を取得する
 * 
 * @author tutt
 *
 */
@Stateless
public class ApprovedInfoFinder {

	@Inject
	private GetAppDisplayAtr appDisplayAtr;

	@Inject
	private GetDayDisplayAtr dayDisplayAtr;

	@Inject
	private GetMonthDisplayAtr monthDisplayAtr;

	@Inject
	private GetArgDisplayAtr argDisplayAtr;

	// .承認すべきデータのウィジェットを起動する
	public ApprovedDataExecutionResultDto get(ApprovedDataExecutionResultDto approvedDataExecutionResultDto,
			StandardWidget standardWidget, List<ClosureIdPresentClosingPeriod> closingPeriods, String employeeId,
			String companyId, Integer yearMonth, int closureId) {

		// 3.1.承認すべき申請データの取得
		List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList = standardWidget
				.getApprovedAppStatusDetailedSettingList();

		// 承認すべき申請データ有無表示_（3次用）
		approvedDataExecutionResultDto.setAppDisplayAtr(
				appDisplayAtr.get(approvedAppStatusDetailedSettingList, closingPeriods, employeeId, companyId));

		// 3.2. 日別実績の承認すべきデータの取得
		approvedDataExecutionResultDto.setDayDisplayAtr(dayDisplayAtr.get(approvedAppStatusDetailedSettingList,
				closingPeriods, employeeId, companyId, yearMonth, closureId));

		// 3.3. 月別実績の承認すべきデータの取得
		approvedDataExecutionResultDto.setMonthDisplayAtr(monthDisplayAtr.get(approvedAppStatusDetailedSettingList,
				closingPeriods, employeeId, companyId, yearMonth, closureId));

		// 3.4. 4.36協定申請の承認すべきデータの取得
		approvedDataExecutionResultDto.setAgrDisplayAtr(argDisplayAtr.get(approvedAppStatusDetailedSettingList,
				closingPeriods, employeeId, companyId, yearMonth, closureId));

		return approvedDataExecutionResultDto;
	}
}
