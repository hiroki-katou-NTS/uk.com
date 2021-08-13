package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.CheckTarget;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.checktrackrecord.CheckTargetItemDto;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.checktrackrecord.CheckTrackRecord;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedDataDetailDto;

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
	
	@Inject
	private ClosureRepository closureRepo;

	//OUTPUT：
	//EA4060
	//承認すべき月の実績(List＜締めID、年月、締名、boolean＞)
	public List<ApprovedDataDetailDto> get(List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList,
			List<ClosureIdPresentClosingPeriod> closingPeriods, String employeeId, String companyId, Integer yearMonth) {
		
		List<ApprovedDataDetailDto> approvedDataDetailList = new ArrayList<>();
		ApprovedAppStatusDetailedSetting monthPerformanceDataSetting = approvedAppStatusDetailedSettingList.stream()
				.filter(a -> a.getItem().value == ApprovedApplicationStatusItem.MONTHLY_RESULT_DATA.value)
				.collect(Collectors.toList()).get(0);

		if (monthPerformanceDataSetting.getDisplayType().value == NotUseAtr.NOT_USE.value) {
			return new ArrayList<>();

		} else {
			/*
			 * EA4060 
			 * List＜締めID, 現在の締め期間＞ をすべて、
			 * 締めID順にチェックする
			 */
			for (ClosureIdPresentClosingPeriod closingPeriod : closingPeriods) {
				
				ApprovedDataDetailDto approvedData = new ApprovedDataDetailDto();
				String closureName = "";
				
				// トップページの設定により対象年月と締めIDを取得する
				CheckTarget checkTarget = checkTargetFinder.getCheckTarget(closingPeriod, yearMonth);
				
				/*
				 * EA4060
				 *  【input】
				 *   ・締めID ⇐ ループ中の「締めID」 
				 *   ・対象年月 ⇐ 取得した「対象年月」 
				 *  【output】
				 *   ・締名 ⇐ 名称
				 * [RQ213] 締め日の名称を取得する
				 */
				//アルゴリズム「締めの名称を取得する」を実行する
				
				Optional<Closure> closure = closureRepo.findById(companyId, closingPeriod.getClosureId());
				if (closure.isPresent()) {
					
					Optional<ClosureHistory> closureHis = closure.get().getHistoryByYearMonth(checkTarget.getYearMonth());
					if (closureHis.isPresent()) {
						closureName = closureHis.get().getClosureName().v();
					}
				}
				
				// 承認すべき月の実績があるかチェックする
				List<CheckTargetItemDto> listCheckTargetItemExport = new ArrayList<>();

				CheckTargetItemDto checkTargetItemDto = new CheckTargetItemDto(checkTarget.getClosureId(),
						checkTarget.getYearMonth());
				listCheckTargetItemExport.add(checkTargetItemDto);
				
				Boolean check = checkTrackRecord.checkTrackRecord(companyId, employeeId, listCheckTargetItemExport);
				
				approvedData.setClosureId(closingPeriod.getClosureId());
				approvedData.setDisplayAtr(check);
				approvedData.setName(closureName);
				approvedData.setYearMonth(checkTarget.getYearMonth().v());
				
				approvedDataDetailList.add(approvedData);
			}
		}
		
		return approvedDataDetailList;
	}
}
