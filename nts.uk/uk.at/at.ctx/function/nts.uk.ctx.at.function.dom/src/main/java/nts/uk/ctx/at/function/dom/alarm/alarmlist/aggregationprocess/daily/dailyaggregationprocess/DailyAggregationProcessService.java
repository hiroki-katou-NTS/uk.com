package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.CollapseTargetPersonAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.FuncEmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyAggregationProcessService {

	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;
	
	@Inject
	private CollapseTargetPersonAdapter collapseTargetPersonAdapter;
	
	@Inject
	private ErrorAlarmWorkRecordAdapter errorAlarmWkRcAdapter;
	
	public void dailyAggregationProcess(String patternCode, PeriodByAlarmCategory period, FuncEmployeeSearchDto employee) {
		String companyID = AppContexts.user().companyId();
		//ドメインモデル「カテゴリ別アラームチェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID, AlarmCategory.DAILY.value, patternCode);
		//対象者を絞り込む
		List<String> data = this.collapseTargetPersonAdapter.getListEmployeeID(GeneralDate.today());

		//カテゴリアラームチェック条件．抽出条件を元に日次データをチェックする (lấy list error alarm tu list code)
		DailyAlarmCondition dailyAlarmCondition = (DailyAlarmCondition) alCheckConByCategory.get().getExtractionCondition();
		List<String> listCode = dailyAlarmCondition.getErrorAlarmCode();
		List<ErrorAlarmWorkRecordAdapterDto> listErrorAlarmWorkRecord = this.errorAlarmWkRcAdapter.getListErAlByListCode(companyID, listCode);
		//
		for(ErrorAlarmWorkRecordAdapterDto errorAlarmWorkRecord : listErrorAlarmWorkRecord ) {
			//エラーアラームチェック条件
			if(errorAlarmWorkRecord.getTypeAtr() == 0){
				
			}
			//アラームチェック条件
			else if(errorAlarmWorkRecord.getTypeAtr() == 1){
						
			}else {
				
			}
		}
	}
}
