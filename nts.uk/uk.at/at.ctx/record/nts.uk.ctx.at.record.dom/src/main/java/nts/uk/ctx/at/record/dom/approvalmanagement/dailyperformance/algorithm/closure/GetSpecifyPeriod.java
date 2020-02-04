package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author ThanhNX 
 * 指定した年月の締め期間を取得する
 */
@Stateless
public class GetSpecifyPeriod {

	@Inject
	private ClosureRepository closureRepository;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ClosureHistPeriod> getSpecifyPeriod(YearMonth yearMonth) {
		String companyId = AppContexts.user().companyId();
		List<ClosureHistPeriod> lstResult = new ArrayList<>();

		List<Closure> lstClosure = closureRepository.findAllActive(companyId, UseClassification.UseClass_Use);
		lstClosure.forEach(closure -> {
			List<DatePeriod> lstDatePeriod = closure.getPeriodByYearMonth(yearMonth);
			GeneralDate startDateMin = lstDatePeriod.stream().map(x -> x.start()).sorted((x, y) -> x.compareTo(y))
					.findFirst().orElse(null);
			GeneralDate endDateMax = lstDatePeriod.stream().map(x -> x.end()).sorted((x, y) -> y.compareTo(x))
					.findFirst().orElse(null);
			if (endDateMax != null && endDateMax != null) {
				lstResult.add(new ClosureHistPeriod(closure.getClosureId(), new DatePeriod(startDateMin, endDateMax)));
			}
		});
		return lstResult;
	}

}
