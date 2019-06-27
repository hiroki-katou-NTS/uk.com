package nts.uk.screen.at.app.ktgwidget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.checktrackrecord.CheckTargetItemDto;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.checktrackrecord.CheckTrackRecord;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class KTG030QueryProcessor {

	@Inject
	private CheckTrackRecord  checkTrackRecord;

	@Inject
	private ClosureService closureService;
	
	@Inject
	private InitDisplayPeriodSwitchSetFinder displayPeriodfinder;
	/**
	 *  月別実績確認すべきデータ有無取得
	 * @author tutk
	 */
	public boolean checkDataMonPerConfirm2(String employeeId) {
		String CID = AppContexts.user().companyId();
		
		//基準日の会社の締めをすべて取得する
		List<ClosureInfo> listClosure = closureService.getAllClosureInfo();
		
		
		//取得した「締め」からチェック対象を作成する
		List<CheckTargetItem> listCheckTargetItem = new ArrayList<>();
		for(ClosureInfo closure : listClosure) {
			listCheckTargetItem.add(new CheckTargetItem(closure.getClosureId().value,closure.getCurrentMonth()));
		}
		//CheckTargetOutPut checkTargetOutPut = new CheckTargetOutPut(listCheckTargetItem);
		
		//承認すべき月の実績があるかチェックする
		boolean result = checkTrackRecord.checkTrackRecord(CID, employeeId, listCheckTargetItem.stream().map(c -> new CheckTargetItemDto(c.getClosureId(), c.getYearMonth())).collect(Collectors.toList()));
		return result;
	}
	
	public boolean checkDataMonPerConfirm(Integer currentOrNextMonth){
		String employeeID = AppContexts.user().employeeId();
		boolean result = true;
		//ユーザー固有情報「トップページ表示年月」を取得する
		//Lấy thông tin  user " Month year hiển thị top page"
		if(currentOrNextMonth == null){
			InitDisplayPeriodSwitchSetDto rq609 = displayPeriodfinder.targetDateFromLogin();
			result = checkTrackRecord.checkTrackRecord(AppContexts.user().companyId(), employeeID, rq609.getListDateProcessed()
					.stream().map(x -> new CheckTargetItemDto(x.getClosureID(), x.getTargetDate())).collect(Collectors.toList()));
		}
		
		//月別実績確認すべきデータ有無取得
		//Lấy tất cả data xác nhận kết quả các ngày có hay không có
		result = dataMonthlyResult(employeeID, currentOrNextMonth);
		return result;
	}
	public boolean dataMonthlyResult(String employeeID ,int yearmonth){
		String CID = AppContexts.user().companyId();
		//全締めの当月と期間を取得する
		//Get thời gian và all closure tháng đó
		List<ClosureInfo> listClosure = closureService.getAllClosureInfo();
		List<CheckTargetItem> listCheckTargetItem = new ArrayList<>();
		for (ClosureInfo closure : listClosure) {
			if(yearmonth == 0){
				listCheckTargetItem.add(new CheckTargetItem(closure.getClosureId().value,closure.getCurrentMonth()));
				
			}
			else{
				listCheckTargetItem.add(new CheckTargetItem(closure.getClosureId().value,closure.getCurrentMonth().addMonths(1)));
			}
		
		}
		boolean result = checkTrackRecord.checkTrackRecord(CID, employeeID, listCheckTargetItem.stream().map(c -> new CheckTargetItemDto(c.getClosureId(), c.getYearMonth())).collect(Collectors.toList()));
		return result;
	}
}
