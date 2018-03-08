package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.FuncEmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess.DailyAggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

@Stateless
@Transactional(value = TxType.REQUIRES_NEW)
public class ExtractAlarmForEmployeeService {
	@Inject 
	private DailyAggregationProcessService dailyAggregationProcessService;
	
	public List<ValueExtractAlarm> process(List<CheckCondition> checkConList, List<PeriodByAlarmCategory> periodByCategory, FuncEmployeeSearchDto employee){
		
		List<ValueExtractAlarm> result = new ArrayList<>();
		
		// 次のチェック条件コードで集計する(loop list by category)
		for (CheckCondition checkCondition : checkConList) {
			// get Period by category
			Optional<PeriodByAlarmCategory> period = periodByCategory.stream()
					.filter(c -> c.getCategory() == checkCondition.getAlarmCategory().value).findFirst();
			
			// カテゴリ：日次のチェック条件(daily)
			if (checkCondition.isDaily()) {
				for (String checkConditionCode : checkCondition.getCheckConditionList()) {						
					// アルゴリズム「日次の集計処理」を実行する
					result.addAll(dailyAggregationProcessService.dailyAggregationProcess(checkConditionCode, period.get(), employee));
				}
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
		return result;
	}
}
