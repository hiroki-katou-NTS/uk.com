package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
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



	public void dailyAggregationProcess(String checkConditionCode, PeriodByAlarmCategory period,
			FuncEmployeeSearchDto employee) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「カテゴリ別アラームチェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID,
				AlarmCategory.DAILY.value, checkConditionCode);

		// カテゴリアラームチェック条件．抽出条件を元に日次データをチェックする
		DailyAlarmCondition dailyAlarmCondition = (DailyAlarmCondition) alCheckConByCategory.get()
				.getExtractionCondition();

		// tab2: 日別実績のエラーアラーム
			



	}
}
