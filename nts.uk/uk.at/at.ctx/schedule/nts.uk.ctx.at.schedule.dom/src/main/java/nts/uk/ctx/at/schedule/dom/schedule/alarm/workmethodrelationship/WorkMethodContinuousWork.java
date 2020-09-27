package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
/**
 * 勤務方法(連続勤務)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.勤務方法の関係性.勤務方法(連続勤務)
 * @author lan_lt
 *
 */
@AllArgsConstructor
public class WorkMethodContinuousWork implements WorkMethod{
	
	@Override
	public WorkMethodClassfication getWorkMethodClassification() {
		return WorkMethodClassfication.CONTINUOSWORK;
	}

	@Override
	public boolean determineIfApplicable(WorkMethod.Require require, WorkInformation workInfo) {
		Optional<WorkType> workType = require.getWorkType(workInfo.getWorkTypeCode().v());
		if (workType.isPresent()) {
			return workType.get().getDailyWork().isOneDay() && workType.get().getDailyWork().isContinueWork();
		}

		return false;
	}

	public interface Require{
		//[R-1] 勤務種類を取得する	
		Optional<WorkType> getWorkType(String workTypeCode);
		
	}
}
