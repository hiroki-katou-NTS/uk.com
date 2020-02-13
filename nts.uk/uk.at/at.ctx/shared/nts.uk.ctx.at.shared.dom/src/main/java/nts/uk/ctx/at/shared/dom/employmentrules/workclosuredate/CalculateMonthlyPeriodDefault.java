package nts.uk.ctx.at.shared.dom.employmentrules.workclosuredate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 月別実績集計期間を算出する
 * 
 * @author tutk
 *
 */
@Stateless
public class CalculateMonthlyPeriodDefault implements CalculateMonthlyPeriodService {

	@Inject
	private ClosureService closureService;

	@Inject
	private ClosureRepository closureRepository;
	@Override
	public DatePeriod calculateMonthlyPeriod(Integer closureId, GeneralDate baseDate) {
		boolean checkOut = true;
		String companyId = AppContexts.user().companyId();
		//ドメイン「締め」を取得する
		Optional<Closure> closure = closureRepository.findById(companyId, closureId);
		YearMonth yearmonth = closure.get().getClosureMonth().getProcessingYm();
		DatePeriod datePeriodResult = closureService.getClosurePeriod(closureId, yearmonth);
		while (checkOut) {
			// アルゴリズム「当月の期間を算出する」を実行する
			DatePeriod datePeriod = closureService.getClosurePeriod(closureId, yearmonth);
			// 締め期間．開始年月日を月別実績集計期間開始年月日に設定する
			// 締め期間に基準日が含まれているかチェックする
			if (datePeriod.start().beforeOrEquals(baseDate) && baseDate.beforeOrEquals(datePeriod.end())) {
				return datePeriod.newSpan(datePeriodResult.start(), datePeriod.end());
			}else {
				//基準日と締め期間．開始年月日を比較する
				if(baseDate.before(datePeriod.start())) {
					//DatePeriod result = datePeriod.newSpan(datePeriodResult.start(), datePeriod.end());
					return datePeriod.newSpan(datePeriodResult.start(), datePeriod.end());
				}else {
					yearmonth = yearmonth.addMonths(1);
				}
			}
		}
		return datePeriodResult;
	}

}
