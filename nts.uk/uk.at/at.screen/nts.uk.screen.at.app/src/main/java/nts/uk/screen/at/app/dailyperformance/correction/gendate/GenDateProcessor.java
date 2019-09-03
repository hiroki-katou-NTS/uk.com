package nts.uk.screen.at.app.dailyperformance.correction.gendate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DatePeriodInfo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DisplayFormat;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GenDateProcessor {
	
	@Inject
	private DailyPerformanceCorrectionProcessor processor;
	
	//対象年月の変更
	public DatePeriodInfo genDateFromYearMonth(GenDateDto param) {
		return processor.updatePeriod(Optional.of(YearMonth.of(param.getYearMonth())), DisplayFormat.Individual.value,
				AppContexts.user().employeeId(), null);
	}

}
