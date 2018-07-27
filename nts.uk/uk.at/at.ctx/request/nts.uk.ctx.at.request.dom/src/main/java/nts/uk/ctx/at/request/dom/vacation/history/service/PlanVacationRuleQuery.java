package nts.uk.ctx.at.request.dom.vacation.history.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface PlanVacationRuleQuery {
	/**
	 * 指定する期間内の計画休暇のルールの履歴を取得する
	 * @param cid ・会社ID
	 * @param workTypeCode ・勤務種類コード
	 * @param dateData 	・開始日
	 * 					・終了日
	 * @return
	 */
	List<PlanVacationHistory> getHisOfInThePeriodVacationSetting(String cid, String workTypeCode, DatePeriod dateData);

}
