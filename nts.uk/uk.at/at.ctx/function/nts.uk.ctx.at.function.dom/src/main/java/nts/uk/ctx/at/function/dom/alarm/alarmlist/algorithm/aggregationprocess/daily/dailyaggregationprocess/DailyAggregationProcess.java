package nts.uk.ctx.at.function.dom.alarm.alarmlist.algorithm.aggregationprocess.daily.dailyaggregationprocess;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.OutputScreenA;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyAggregationProcess {

	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;
	
	public void dailyAggregationProcess(String patternCode, OutputScreenA outputScreenA, String employee) {
		String companyID = AppContexts.user().companyId();
		//ドメインモデル「カテゴリ別アラームチェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID, AlarmCategory.DAILY.value, patternCode);
		//対象者を絞り込む
		//List<String>  listEmployeeID =  collapseTargetPerson(GeneralDate.today());
		
	}
}
