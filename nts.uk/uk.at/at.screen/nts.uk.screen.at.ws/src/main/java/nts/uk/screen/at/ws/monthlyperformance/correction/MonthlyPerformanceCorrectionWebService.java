package nts.uk.screen.at.ws.monthlyperformance.correction;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatFinder;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;
import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceReload;
import nts.uk.screen.at.app.monthlyperformance.correction.command.MonthModifyCommandFacade;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ErrorAlarmWorkRecordDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPItemDetail;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPItemParent;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.param.MonthlyPerformanceParam;
import nts.uk.screen.at.app.monthlyperformance.correction.param.ReloadMonthlyPerformanceParam;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;

/**
 * TODO
 */
@Path("screen/at/monthlyperformance")
@Produces("application/json")
public class MonthlyPerformanceCorrectionWebService {
	@Inject
	private MonthlyPerformanceCorrectionProcessor processor;
	/** 会社の月別実績の修正のフォーマット */
	@Inject
	private MonPfmCorrectionFormatFinder monPfmCorrectionFormatFinder;
	@Inject
	private MonthlyPerformanceReload monthlyPerformanceReload;
	
	@Inject
	private MonthModifyCommandFacade monthModifyCommandFacade;
	
	@POST
	@Path("startScreen")
	public MonthlyPerformanceCorrectionDto startScreen(MonthlyPerformanceParam param) throws InterruptedException {
		return processor.initScreen(param);
	}
	@POST
	@Path("updateScreen")
	public MonthlyPerformanceCorrectionDto updateScreen(ReloadMonthlyPerformanceParam param) throws InterruptedException {
		return monthlyPerformanceReload.reloadScreen(param);
	}
	@POST
	@Path("getErrorList")
	public List<ErrorAlarmWorkRecordDto> getMonthlyErrorList() {
		//TODO wait domain 社員の月別実績エラー一覧
		return Arrays.asList(new ErrorAlarmWorkRecordDto("", "001", "Error 001", 0, 0, 0, "001", 0, "#FFFFFF", 0, BigDecimal.valueOf(0)));
	}
	@POST
	@Path("getFormatCodeList")
	public List<DailyPerformanceFormatDto> getAll() {		
		return monPfmCorrectionFormatFinder.getAllMonPfmCorrectionFormat()
				.stream()
				.map(x->new DailyPerformanceFormatDto(x.getCompanyID(), x.getMonthlyPfmFormatCode(), x.getMonPfmCorrectionFormatName()))
				.collect(Collectors.toList());
//		return null;
	}
	@POST
	@Path("addAndUpdate")
	public Map<Integer, List<MPItemParent>> addAndUpdate(MPItemParent dataParent) {
		
		Map<String, List<MPItemDetail>> mapItemDetail = dataParent.getMPItemDetails().stream()
				.collect(Collectors.groupingBy(x -> x.getEmployeeId()));

		mapItemDetail.entrySet().forEach(item -> {
			List<MPItemDetail> rowDatas = item.getValue();
			monthModifyCommandFacade.handleUpdate(new MonthlyModifyQuery(rowDatas.stream().map(x -> {
				return ItemValue.builder().itemId(x.getItemId()).layout(x.getLayoutCode()).value(x.getValue())
						.valueType(ValueType.valueOf(x.getValueType())).withPath("");
			}).collect(Collectors.toList()), dataParent.getYearMonth(), item.getKey(), dataParent.getClosureId(),
					dataParent.getClosureDate(), Collections.emptyList()));
		});
		
		return Collections.emptyMap();
	}
}
