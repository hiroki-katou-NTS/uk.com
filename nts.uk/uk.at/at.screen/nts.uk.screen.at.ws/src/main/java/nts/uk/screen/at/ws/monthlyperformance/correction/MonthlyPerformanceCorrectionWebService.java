package nts.uk.screen.at.ws.monthlyperformance.correction;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormatFinder;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionFinder;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;
import nts.uk.screen.at.app.monthlyperformance.correction.MPUpdateColWidthCommand;
import nts.uk.screen.at.app.monthlyperformance.correction.MPUpdateColWidthCommandHandler;
import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceReload;
import nts.uk.screen.at.app.monthlyperformance.correction.command.MonModifyCommandFacade;
import nts.uk.screen.at.app.monthlyperformance.correction.command.MonthModifyCommandFacade;
import nts.uk.screen.at.app.monthlyperformance.correction.command.MonthlyPerformanceCorrectionUpdateCommand;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.EditStateOfMonthlyPerformanceDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPItemDetail;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPItemParent;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.param.MonthlyPerformanceParam;
import nts.uk.screen.at.app.monthlyperformance.correction.param.ReloadMonthlyPerformanceParam;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	private MonthlyCorrectConditionFinder monthlyCorrectionCondFinder;
	
	@Inject
	private MonthlyPerformanceReload monthlyPerformanceReload;
	
	@Inject
	private MPUpdateColWidthCommandHandler commandHandler;
	
	@Inject
	private MonModifyCommandFacade monModifyCommandFacade;
	
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
	public List<MonthlyCorrectConditionDto> getMonthlyErrorList() {
		return monthlyCorrectionCondFinder.findUseMonthlyCorrectCondition();
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
	@Path("updatecolumnwidth")
	public void getError(MPUpdateColWidthCommand command){
		this.commandHandler.handle(command);
	}
	
	@POST
	@Path("addAndUpdate")
	public Map<Integer, List<MPItemParent>> addAndUpdate(MPItemParent dataParent) {
		return this.monModifyCommandFacade.insertItemDomain(dataParent);
	}
}
