package nts.uk.screen.at.ws.monthlyperformance.correction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;
import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ErrorAlarmWorkRecordDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.param.MonthlyPerformanceParam;


@Path("screen/at/monthlyperformance")
@Produces("application/json")
public class MonthlyPerformanceCorrectionWebService {
	@Inject
	MonthlyPerformanceCorrectionProcessor processor;

	@POST
	@Path("startScreen")
	public MonthlyPerformanceCorrectionDto startScreen(MonthlyPerformanceParam param) throws InterruptedException {
		return processor.initScreen(param);
	}
	/**
	 * TODO
	 * @return
	 */
	@POST
	@Path("getErrorList")
	public List<ErrorAlarmWorkRecordDto> getMonthlyErrorList() {
		return Arrays.asList(new ErrorAlarmWorkRecordDto("", "001", "Error 001", 0, 0, 0, "001", 0, "#FFFFFF", 0, BigDecimal.valueOf(0)));
	}
	/**
	 * TODO
	 * @return
	 */
	@POST
	@Path("getFormatCodeList")
	public List<DailyPerformanceFormatDto> getAll() {
		return Arrays.asList(new DailyPerformanceFormatDto("", "001", "Error 001"));
	}
}
