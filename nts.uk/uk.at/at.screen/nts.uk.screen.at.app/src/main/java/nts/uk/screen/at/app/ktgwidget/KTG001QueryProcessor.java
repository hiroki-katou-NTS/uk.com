
package nts.uk.screen.at.app.ktgwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTarget;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTrackRecordApprovalDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class KTG001QueryProcessor {

	@Inject
	private CheckTrackRecordApprovalDay checkTrackRecordApprovalDay;

	@Inject
	private ClosureService closureService;

	public boolean checkDataDayPerConfirm(String employeeId) {
		String CID = AppContexts.user().companyId();

		// 基準日の会社の締めをすべて取得する
		List<ClosureInfo> listClosure = closureService.getAllClosureInfo();

		// 取得した「締め」からチェック対象を作成する
		List<CheckTargetItem> listCheckTargetItem = new ArrayList<>();
		for (ClosureInfo closure : listClosure) {
			listCheckTargetItem.add(new CheckTargetItem(closure.getClosureId().value, closure.getCurrentMonth()));
		}
		// CheckTargetOutPut checkTargetOutPut = new
		// CheckTargetOutPut(listCheckTargetItem);

		// 承認すべき月の実績があるかチェックする
		boolean result = checkTrackRecordApprovalDay.checkTrackRecordApprovalDay(CID, employeeId, listCheckTargetItem
				.stream().map(c -> new CheckTarget(c.getClosureId(), c.getYearMonth())).collect(Collectors.toList()));
		return result;
	}

	public boolean checkDataDayPerConfirmVer2() {
		String employeeID = AppContexts.user().employeeId();
		// ユーザー固有情報「トップページ表示年月」を取得する
		// Lấy thong tin user " Month year hiển thị top page"
		int yearmonth = 0;

		// 日別実績確認すべきデータ有無取得
		// Lấy có không có tất cả data xác nhận kết quả các ngày
		boolean result = dataDailyResults(employeeID, yearmonth);

		return result;

	}

	public boolean dataDailyResults(String employeeID, int yearmonth) {
		String CID = AppContexts.user().companyId();
		// 全締めの当月と期間を取得する
		// Get thời gian và all closure tháng đó
		List<ClosureInfo> listClosure = closureService.getAllClosureInfo();
		// 取得した「締め」からチェック対象を作成する
		List<CheckTargetItem> listCheckTargetItem = new ArrayList<>();
		for (ClosureInfo closure : listClosure) {
			if (closure.getCurrentMonth().equals(yearmonth)) {
				listCheckTargetItem.add(new CheckTargetItem(closure.getClosureId().value, closure.getCurrentMonth()));
			} else {
				listCheckTargetItem
						.add(new CheckTargetItem(closure.getClosureId().value, closure.getCurrentMonth().addMonths(1)));
			}
		}
		boolean result = checkTrackRecordApprovalDay.checkTrackRecordApprovalDay(CID, employeeID, listCheckTargetItem
				.stream().map(c -> new CheckTarget(c.getClosureId(), c.getYearMonth())).collect(Collectors.toList()));

		return result;
	}
}