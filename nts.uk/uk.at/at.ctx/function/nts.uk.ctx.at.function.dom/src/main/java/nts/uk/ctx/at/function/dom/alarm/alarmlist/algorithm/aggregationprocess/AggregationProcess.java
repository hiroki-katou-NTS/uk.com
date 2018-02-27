package nts.uk.ctx.at.function.dom.alarm.alarmlist.algorithm.aggregationprocess;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.OutputScreenA;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.algorithm.aggregationprocess.daily.dailyaggregationprocess.DailyAggregationProcess;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AggregationProcess {
	
	@Inject
	private AlarmPatternSettingRepository alPatternSettingRepo;
	
	@Inject 
	private DailyAggregationProcess dailyAggregationProcess;
	
	public List<AlarmExtraValueWkReDto> processAlarmListWorkRecord(List<String> listEmployee,
			String checkPatternCode, List<OutputScreenA> listOutputScreenA) {
		String companyID = AppContexts.user().companyId();
		// パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定」を取得する
		Optional<AlarmPatternSetting> alarmPatternSetting = this.alPatternSettingRepo.findByAlarmPatternCode(companyID,
				checkPatternCode);
		// 従業員ごと に行う(for list employee)
		for (String employee : listEmployee) {
			// 次のチェック条件コードで集計する(loop list by category)
			for (CheckCondition checkCondition : alarmPatternSetting.get().getCheckConList()) {
				// get Period by category
				Optional<OutputScreenA> outputScreenA = listOutputScreenA.stream()
						.filter(c -> c.getCategory() == checkCondition.getAlarmCategory().value).findFirst();
				for (String checkConditionCode : checkCondition.getCheckConditionList()) {

					// カテゴリ：日次のチェック条件( daily)
					if (checkCondition.getAlarmCategory().equals(AlarmCategory.DAILY)) {

						// アルゴリズム「日次の集計処理」を実行する
						this.dailyAggregationProcess.dailyAggregationProcess(checkConditionCode, outputScreenA.get(), employee);
					}
					// カテゴリ：4週4休のチェック条件(4 week 4 day)
					if (checkCondition.getAlarmCategory().equals(AlarmCategory.SCHEDULE_4WEEK)) {
						// アルゴリズム「4週4休の集計処理」を実行する
						//TotalProcess4Week4Day(checkConditionCode, outputScreenA.get(), employee);

					}
					// カテゴリ：月次のチェック条件 (month
					if (checkCondition.getAlarmCategory().equals(AlarmCategory.MONTHLY)) {
						// tạm thời chưa làm
					}
				}

			}

		}

		return null;
	}

}
