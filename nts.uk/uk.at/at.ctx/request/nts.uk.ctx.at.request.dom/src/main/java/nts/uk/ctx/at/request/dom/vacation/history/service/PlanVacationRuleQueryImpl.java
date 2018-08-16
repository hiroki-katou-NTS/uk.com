package nts.uk.ctx.at.request.dom.vacation.history.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class PlanVacationRuleQueryImpl implements PlanVacationRuleQuery{
	@Inject
	private VacationHistoryRepository vacationHisRepos;
	@Override
	public List<PlanVacationHistory> getHisOfInThePeriodVacationSetting(String cid, String workTypeCode,
			DatePeriod dateData) {
		//ドメインモデル「計画休暇のルールの履歴」を取得する
		List<PlanVacationHistory> lstVaction = vacationHisRepos.findByWorkTypeAndPeriod(cid, workTypeCode, dateData);
		//取得した「計画休暇のルートの履歴」の件数をチェックする
		
		return lstVaction;
	}

}
