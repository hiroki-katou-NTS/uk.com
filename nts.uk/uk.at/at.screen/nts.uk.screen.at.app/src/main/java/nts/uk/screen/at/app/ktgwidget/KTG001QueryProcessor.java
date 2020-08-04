
package nts.uk.screen.at.app.ktgwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTarget;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTrackRecordApprovalDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.ktgwidget.find.KTG001Dto;
import nts.uk.screen.at.app.ktgwidget.find.TargetDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class KTG001QueryProcessor {

	@Inject
	private CheckTrackRecordApprovalDay checkTrackRecordApprovalDay;

	@Inject
	private ClosureService closureService;
	
	@Inject
	private InitDisplayPeriodSwitchSetFinder displayPeriodfinder;

	public boolean checkDataDayPerConfirm3(String employeeId) {
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

	public KTG001Dto checkDataDayPerConfirm(Integer currentOrNextMonth, int closureId) {
		String employeeID = AppContexts.user().employeeId();
		List<CheckTarget> listCheckTargetItem = new ArrayList<>();
		boolean result = true;
		// ユーザー固有情報「トップページ表示年月」を取得する
		// Lấy thong tin user " Month year hiển thị top page"
		//[RQ609]ログイン社員のシステム日時点の処理対象年月を取得する
		if(currentOrNextMonth == null){
			InitDisplayPeriodSwitchSetDto rq609 = displayPeriodfinder.targetDateFromLogin();
			listCheckTargetItem = rq609.getListDateProcessed().stream().map(x -> new CheckTarget(x.getClosureID(), x.getTargetDate())).collect(Collectors.toList());
		}else{
			// 日別実績確認すべきデータ有無取得
			// Lấy có không có tất cả data xác nhận kết quả các ngày
			listCheckTargetItem = dataDailyResults(employeeID, currentOrNextMonth);
		}
		List<CheckTarget> temp = listCheckTargetItem.stream().filter(x -> x.getClosureId() == closureId).collect(Collectors.toList());
		// request list 593
		result = checkTrackRecordApprovalDay.checkTrackRecordApprovalDay(AppContexts.user().companyId(), employeeID, temp);
		return new KTG001Dto(result, currentOrNextMonth, listCheckTargetItem.stream()
																.map(x -> new TargetDto(x.getClosureId(), x.getYearMonth().toString())).collect(Collectors.toList()));
	}

	public List<CheckTarget> dataDailyResults(String employeeID, int yearmonth) {
		// 全締めの当月と期間を取得する
		// Get thời gian và all closure tháng đó
		List<ClosureInfo> listClosure = closureService.getAllClosureInfo();
		// 取得した「締め」からチェック対象を作成する
		List<CheckTarget> listCheckTarget = new ArrayList<>();
		for (ClosureInfo closure : listClosure) {
			if (yearmonth == 1) {
				listCheckTarget.add(new CheckTarget(closure.getClosureId().value, closure.getCurrentMonth()));
			} else {
				listCheckTarget.add(new CheckTarget(closure.getClosureId().value, closure.getCurrentMonth().addMonths(1)));
			}
		}
		return listCheckTarget;
	}
}