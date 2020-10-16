package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.1.承認すべき申請データの取得
 * 
 * @author tutt
 *
 */
public class GetAppDisplayAtr {

	@Inject
	private PresentClosingPeriodFinder presentClosingPeriodFinder;

	@Inject
	private AppDisplayAtrFinder appDisplayAtrFinder;

	/**
	 * 1.承認すべき申請データの取得
	 * @param approvedAppStatusDetailedSettingList
	 * @param closingPeriods
	 * @param employeeId
	 * @param companyId
	 * @return
	 */
	public Boolean get(List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList,
			List<ClosureIdPresentClosingPeriod> closingPeriods, String employeeId, String companyId) {

		ApprovedAppStatusDetailedSetting applicationDataSetting = approvedAppStatusDetailedSettingList.stream()
				.filter(a -> a.getItem().value == ApprovedApplicationStatusItem.APPLICATION_DATA.value)
				.collect(Collectors.toList()).get(0);

		if (applicationDataSetting.getDisplayType().value == NotUseAtr.NOT_USE.value) {
			return false;

		} else {

			// 承認すべき申請の対象期間を取得する
			GetYearProcessAndPeriodDto periodImport = presentClosingPeriodFinder.getPeriod(closingPeriods);

			// 承認すべき申請データ有無表示_（3次用）
			return appDisplayAtrFinder.getAppDisplayAtr(periodImport, employeeId, companyId);
		}

	}

}
