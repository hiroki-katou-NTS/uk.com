package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
/**
 * 勤務方法(休日)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.勤務方法の関係性.勤務方法(休日)
 * @author lan_lt
 *
 */
@AllArgsConstructor
public class WorkMethodHoliday implements WorkMethod{

	@Override
	public WorkMethodClassfication getWorkMethodClassification() {
		return WorkMethodClassfication.HOLIDAY;
	}

	@Override
	public boolean includes(WorkMethod.Require require, WorkInformation workInfo) {
		return require.checkHoliday(workInfo.getWorkTypeCode());
	}
	
	public static interface Require{
		//[R-1] 1日休日か
		boolean checkHoliday(WorkTypeCode workTypeCode);
		
	}

}
