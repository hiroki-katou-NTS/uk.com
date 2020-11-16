package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTarget;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.checktrackrecord.CheckTargetItemDto;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.checktrackrecord.CheckTrackRecord;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.3.月別実績の承認すべきデータの取得
 * 
 * @author tutt
 *
 */
@Stateless
public class GetMonthDisplayAtr {

	@Inject
	private CheckTargetFinder checkTargetFinder;

	@Inject
	private CheckTrackRecord checkTrackRecord;

	// 月別実績の承認すべきデータの取得
	public Boolean get(List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList,
			List<ClosureIdPresentClosingPeriod> closingPeriods, String employeeId, String companyId, Integer yearMonth,
			int closureId) {
		ApprovedAppStatusDetailedSetting monthPerformanceDataSetting = approvedAppStatusDetailedSettingList.stream()
				.filter(a -> a.getItem().value == ApprovedApplicationStatusItem.MONTHLY_RESULT_DATA.value)
				.collect(Collectors.toList()).get(0);

		if (monthPerformanceDataSetting.getDisplayType().value == NotUseAtr.NOT_USE.value) {
			return false;

		} else {

			// トップページの設定により対象年月と締めIDを取得する
			CheckTarget checkTarget = checkTargetFinder.getCheckTarget(closingPeriods, closureId, yearMonth);

			// 承認すべき月の実績があるかチェックする
			List<CheckTargetItemDto> listCheckTargetItemExport = new ArrayList<>();

			CheckTargetItemDto checkTargetItemDto = new CheckTargetItemDto(checkTarget.getClosureId(),
					checkTarget.getYearMonth());
			listCheckTargetItemExport.add(checkTargetItemDto);

			return checkTrackRecord.checkTrackRecord(companyId, employeeId, listCheckTargetItemExport);

		}
	}
}
