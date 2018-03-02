package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess.DailyAggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AggregationProcessService {
	
	@Inject
	private AlarmPatternSettingRepository alPatternSettingRepo;
	
	@Inject 
	private DailyAggregationProcessService dailyAggregationProcessService;
	
	public List<AlarmExtraValueWkReDto> processAlarmListWorkRecord(List<String> listEmployee,
			String checkPatternCode, List<PeriodByAlarmCategory> periodByCategory) {
		String companyID = AppContexts.user().companyId();
		
		// パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定」を取得する
		Optional<AlarmPatternSetting> alarmPatternSetting = this.alPatternSettingRepo.findByAlarmPatternCode(companyID, checkPatternCode);
		
		// 従業員ごと に行う(for list employee)
		for (String employee : listEmployee) {
			// 次のチェック条件コードで集計する(loop list by category)
			for (CheckCondition checkCondition : alarmPatternSetting.get().getCheckConList()) {
				// get Period by category
				Optional<PeriodByAlarmCategory> outputScreenA = periodByCategory.stream()
						.filter(c -> c.getCategory() == checkCondition.getAlarmCategory().value).findFirst();
				for (String checkConditionCode : checkCondition.getCheckConditionList()) {

					// カテゴリ：日次のチェック条件(daily)
					if (checkCondition.isDaily()) {
						// アルゴリズム「日次の集計処理」を実行する
						dailyAggregationProcessService.dailyAggregationProcess(checkConditionCode, outputScreenA.get(), employee);
					}
					// カテゴリ：4週4休のチェック条件(4 week 4 day)
					else if (checkCondition.is4W4D()) {
						// アルゴリズム「4週4休の集計処理」を実行する
						//TotalProcess4Week4Day(checkConditionCode, outputScreenA.get(), employee);
					}
					// カテゴリ：月次のチェック条件 (monthly)
					else if (checkCondition.isMonthly()) {
						// tạm thời chưa làm
					}
				}

			}

		}

		return null;
	}

}
