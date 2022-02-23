/**
 * 5:57:43 PM Aug 28, 2017
 */
package nts.uk.screen.at.ws.dailyperformance.correction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.task.AsyncTaskInfo;
import nts.uk.screen.at.app.dailymodify.command.*;
import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.web.session.HttpSubSession;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.DailyPerformanceAuthoritySetting;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.MonthlyPerfomanceAuthorityFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.bs.employee.dom.employee.service.SearchEmployeeService;
import nts.uk.ctx.bs.employee.dom.employee.service.dto.EmployeeSearchData;
import nts.uk.ctx.bs.employee.dom.employee.service.dto.EmployeeSearchDto;

import nts.uk.screen.at.app.dailyperformance.correction.DPUpdateColWidthCommandHandler;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.DisplayRemainingHolidayNumber;
import nts.uk.screen.at.app.dailyperformance.correction.HolidayRemainParam;
import nts.uk.screen.at.app.dailyperformance.correction.InfomationInitScreenProcess;
import nts.uk.screen.at.app.dailyperformance.correction.UpdateColWidthCommand;
import nts.uk.screen.at.app.dailyperformance.correction.calctime.DailyCorrectCalcTimeService;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalConfirmCache;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPHeaderDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParentDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPParams;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCalcDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCalculationDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIU;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIUDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DatePeriodInfo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DatePeriodInfoDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.EmpAndDate;
import nts.uk.screen.at.app.dailyperformance.correction.dto.EmpAndDateDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ErAlWorkRecordShortDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorReferenceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.GetWkpIDOutput;
import nts.uk.screen.at.app.dailyperformance.correction.dto.GetWkpIDParam;
import nts.uk.screen.at.app.dailyperformance.correction.dto.HolidayRemainNumberDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.InitParamOutput;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;
import nts.uk.screen.at.app.dailyperformance.correction.dto.calctime.DCCalcTime;
import nts.uk.screen.at.app.dailyperformance.correction.dto.calctime.DCCalcTimeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.calctime.DCCalcTimeParam;
import nts.uk.screen.at.app.dailyperformance.correction.dto.calctime.DCCalcTimeParamDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.mobile.ErrorParam;
import nts.uk.screen.at.app.dailyperformance.correction.dto.mobile.MasterDialogParam;
import nts.uk.screen.at.app.dailyperformance.correction.flex.CalcFlexDto;
import nts.uk.screen.at.app.dailyperformance.correction.flex.CheckBeforeCalcFlex;
import nts.uk.screen.at.app.dailyperformance.correction.gendate.GenDateDto;
import nts.uk.screen.at.app.dailyperformance.correction.gendate.GenDateProcessDto;
import nts.uk.screen.at.app.dailyperformance.correction.gendate.GenDateProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.kdw003b.DailyPerformErrorReferDto;
import nts.uk.screen.at.app.dailyperformance.correction.kdw003b.DailyPerformErrorReferExportDto;
import nts.uk.screen.at.app.dailyperformance.correction.kdw003b.DailyPerformErrorReferExportService;
import nts.uk.screen.at.app.dailyperformance.correction.kdw003b.DailyPerformErrorReferFinder;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.DPLoadRowProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.DPPramLoadRow;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.DPPramLoadRowDto;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.onlycheckbox.DPLoadVerProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.onlycheckbox.LoadVerData;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.onlycheckbox.LoadVerDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.onlycheckbox.LoadVerDataResult;
import nts.uk.screen.at.app.dailyperformance.correction.loadupdate.onlycheckbox.LoadVerDataResultDto;
import nts.uk.screen.at.app.dailyperformance.correction.lock.button.DPDisplayLockParam;
import nts.uk.screen.at.app.dailyperformance.correction.lock.button.DPDisplayLockParamDto;
import nts.uk.screen.at.app.dailyperformance.correction.lock.button.DPDisplayLockProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.DPCorrectionProcessorMob;
import nts.uk.screen.at.app.dailyperformance.correction.month.asynctask.MonthParamInit;
import nts.uk.screen.at.app.dailyperformance.correction.month.asynctask.ParamCommonAsync;
import nts.uk.screen.at.app.dailyperformance.correction.month.asynctask.ProcessMonthScreen;
import nts.uk.screen.at.app.dailyperformance.correction.searchemployee.FindEmployeeBase;
import nts.uk.screen.at.app.dailyperformance.correction.selecterrorcode.DailyPerformanceErrorCodeProcessor;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Path("screen/at/correctionofdailyperformance")
@Produces("application/json")
public class DailyPerformanceCorrectionWebService {

	@Inject
	private DailyPerformanceCorrectionProcessor processor;
	
	@Inject
	private DailyPerformanceErrorCodeProcessor errorProcessor;
	
	@Inject
	private DPUpdateColWidthCommandHandler commandHandler;
	
	@Inject
	private DataDialogWithTypeProcessor dialogProcessor;
	
	@Inject
	private DataDialogWithTypeProcessor dataDialogWithTypeProcessor;
	
	@Inject
	private PersonalTightCommandFacade personalTightCommandFacade;
	
	@Inject
	private CheckBeforeCalcFlex checkBeforeCalcFlex;
	
	@Inject
	private DPLoadRowProcessor loadRowProcessor;
	
	@Inject
	private DailyPerformErrorReferExportService dailyPerformErrorExportService;

	@Inject
	private DailyPerformErrorReferFinder dailyPerforErrorReferFinder;
	
	@Inject
	private FindEmployeeBase findEmployeeBase;
	
	@Inject
	private DailyCorrectCalcTimeService dailyCorrectCalcTimeService;
	
	@Inject
	private DailyCalculationRCommandFacade dailyCalculationService;
	
	@Inject
	private DisplayRemainingHolidayNumber remainNumberService;

	@Inject
	private DPDisplayLockProcessor dpDisplayLockProcessor;
	
	@Inject
	private DailyPerformanceAuthoritySetting dailyPerformanceAuthoritySetting;
	
	@Inject
	private MonthlyPerfomanceAuthorityFinder monthlyPerfomanceAuthorityFinder;
	
	@Inject
	private HttpSubSession session;
	
	@Inject
	private DPLoadVerProcessor dPLoadVerProcessor;
	
	@Inject
	private InfomationInitScreenProcess infomationInit;
	
	@Inject
	private ProcessMonthScreen processMonthScreen;
	
	@Inject
	private GenDateProcessor genDateProcessor;
	
	@Inject
	private DailyModifyRCommandFacade dailyModifyRCommandFacade;
	
	@Inject
	private DPCorrectionProcessorMob dpCorrectionProcessorMob;
	
	@Inject
	private WorkplacePub workplacePub;

	@Inject
	private SearchEmployeeService searchEmployeeService;

	@Inject
	private AsyncExecuteMonthlyAggregateCommandHandler execMonthlyAggregateHandler;
	
	@POST
	@Path("startScreen")
	public DailyPerformanceCorrectionDto startScreen(DPParams params ) throws InterruptedException{
		Integer closureId = params.closureId;
//		DailyPerformanceCorrectionDto screenDto = (DailyPerformanceCorrectionDto) session.getAttribute("resultReturn");
		DailyPerformanceCorrectionDto screenDto = params.screenDto;
		DailyPerformanceCorrectionDto dtoResult = this.processor.generateData(screenDto, params.lstEmployee, params.initScreen, params.mode, params.displayFormat, params.correctionOfDaily, params.formatCodes, params.showError, params.showLock, params.objectShare, closureId);

//		session.setAttribute("domainOlds", dtoResult.getDomainOld());
//		session.setAttribute("domainOldForLog", cloneListDto(dtoResult.getDomainOld()));
//		session.setAttribute("domainEdits", null);
//		session.setAttribute("itemIdRCs", dtoResult.getLstControlDisplayItem() == null ? null : dtoResult.getLstControlDisplayItem().getMapDPAttendance());
//		session.setAttribute("dataSource", dtoResult.getLstData());
//		session.setAttribute("closureId", dtoResult.getClosureId());
//		session.setAttribute("resultReturn", null);
//		session.setAttribute("approvalConfirm", dtoResult.getApprovalConfirmCache());
		DataSessionDto dataSessionDto = new DataSessionDto();
		dataSessionDto.setDomainOlds(dtoResult.getDomainOld());
		dataSessionDto.setDomainOldForLog(cloneListDto(dtoResult.getDomainOld()));
		dataSessionDto.setDomainEdits(null);
		dataSessionDto.setItemIdRCs(dtoResult.getLstControlDisplayItem() == null ? null : dtoResult.getLstControlDisplayItem().getMapDPAttendance());
		dataSessionDto.setDataSource(dtoResult.getLstData());
		dataSessionDto.setClosureId(dtoResult.getClosureId());
		dataSessionDto.setResultReturn(null);
		dataSessionDto.setApprovalConfirmCache(dtoResult.getApprovalConfirmCache());
		
		dtoResult.setApprovalConfirmCache(null);
		dtoResult.setLstCellState(dtoResult.getMapCellState().values().stream().collect(Collectors.toList()));
		dtoResult.setMapCellState(null);
//		removeSession();
		dataSessionDto.setLstSidDateErrorCalc(Collections.emptyList());
		dataSessionDto.setErrorAllCalc(false);
		
		dtoResult.setDataSessionDto(dataSessionDto);
		
		dtoResult.setDomainOld(Collections.emptyList());
		return dtoResult;
	}
	
	@POST
	@Path("errorCode")
	public DailyPerformanceCorrectionDto condition(DPParams params ) throws InterruptedException{
		Integer closureId = params.screenDto.getDataSessionDto().getClosureId();
		val results = this.errorProcessor.generateData(params.dateRange, params.lstEmployee, params.initScreen, params.mode, params.displayFormat, params.correctionOfDaily, params.errorCodes, params.formatCodes, params.showLock, closureId);
		params.screenDto.getDataSessionDto().setDomainOlds(results.getDomainOld());
		params.screenDto.getDataSessionDto().setDomainOldForLog(cloneListDto(results.getDomainOld()));
		params.screenDto.getDataSessionDto().setDomainEdits(null);
		params.screenDto.getDataSessionDto().setItemIdRCs(results.getLstControlDisplayItem() == null ? null : results.getLstControlDisplayItem().getMapDPAttendance());
		params.screenDto.getDataSessionDto().setDataSource(results.getLstData());
		params.screenDto.getDataSessionDto().setClosureId(results.getClosureId());
		params.screenDto.getDataSessionDto().setResultReturn(null);
		params.screenDto.getDataSessionDto().setApprovalConfirmCache(results.getApprovalConfirmCache());
		results.setApprovalConfirmCache(null);
		results.setLstCellState(results.getMapCellState().values().stream().collect(Collectors.toList()));
		results.setMapCellState(null);
		// removeSession();
		params.screenDto.getDataSessionDto().setLstSidDateErrorCalc(Collections.emptyList());
		params.screenDto.getDataSessionDto().setErrorAllCalc(false);
		results.setDomainOld(Collections.emptyList());
		return results;
	}
	
	@POST
	@Path("initParam")
	public InitParamOutput initScreen(DPParams params) throws InterruptedException{
//		params.dpStateParam = (DPCorrectionStateParam)session.getAttribute("dpStateParam");
		//TODO : truyền dưới ui ?
		Pair<DailyPerformanceCorrectionDto, ParamCommonAsync> dtoResult = this.infomationInit.initGetParam(params);
//		session.setAttribute("dpStateParam", dtoResult.getLeft().getStateParam());
//		session.setAttribute("resultReturn", dtoResult.getLeft());
//		session.setAttribute("resultMonthReturn", dtoResult.getRight());
		return new InitParamOutput(dtoResult.getLeft(),dtoResult.getRight(),dtoResult.getLeft().getStateParam());
	}
	
	@POST
	@Path("loadMonth")
	@SuppressWarnings("unchecked")
	public DailyPerformanceCorrectionDto loadMonth(MonthParamInit monthInit) throws InterruptedException{
//		ParamCommonAsync paramCommonAsync = (ParamCommonAsync) session.getAttribute("resultMonthReturn");
		ParamCommonAsync paramCommonAsync = monthInit.getParamCommonAsync();
//		DPCorrectionStateParam stateParam = (DPCorrectionStateParam)session.getAttribute("dpStateParam");
		DPCorrectionStateParam stateParam = monthInit.getDpStateParam();
		
//		Object objectCacheMonth = session.getAttribute("domainMonths");
//		Optional<MonthlyRecordWorkDto> domainMonthOpt = objectCacheMonth == null ? Optional.empty()
//				: (Optional<MonthlyRecordWorkDto>) objectCacheMonth;
		paramCommonAsync.setStateParam(stateParam);
		paramCommonAsync.setDomainMonthOpt(Optional.empty());
		paramCommonAsync.setLoadAfterCalc(monthInit.isLoadAfterCalc());
		DailyPerformanceCorrectionDto result = this.processMonthScreen.processMonth(paramCommonAsync);
//		session.setAttribute("domainMonths", result.getMonthResult() == null ? null : result.getMonthResult().getDomainMonthOpt());
		
		//session.setAttribute("resultMonthReturn", null);
		return result;
	}
	
	@POST
	@Path("getErrors")
	public List<ErrorReferenceDto> getError(DPParams params ) {
		Integer closureId = params.screenDto.getDataSessionDto().getClosureId();
		return this.processor.getListErrorRefer(params.dateRange, params.lstEmployee, closureId);
	}
	
	@POST
	@Path("updatecolumnwidth")
	public void getError(UpdateColWidthCommand command){
		this.commandHandler.handle(command);
	}
	
	@POST
	@Path("findCodeName")
	public CodeName findCodeName(DPParamDialog param){
		return this.dialogProcessor.getTypeDialog(param.getTypeDialog(), param.getParam());
	}
	
	@POST
	@Path("findAllCodeName")
	public List<CodeName> findAllCodeName(DPParamDialog param){
		return this.dialogProcessor.getAllTypeDialog(param.getTypeDialog(), param.getParam());
	}
	
	@POST
	@Path("addAndUpdate")
	@SuppressWarnings("unchecked")
	public DataResultAfterIUDto addAndUpdate(DPItemParentDto dataParentDto) {
		setDataParent(dataParentDto);
		DataResultAfterIU dataResultAfterIU =  dailyModifyRCommandFacade.insertItemDomain(dataParentDto.getDataParent());
//		//TODO: set cache month
//		if(dataResultAfterIU.getDomainMonthOpt().isPresent()) {
			dataParentDto.getDataSessionDto().setDomainMonthOpt(null); // domainMonths
//		}

		  dataParentDto.getDataSessionDto().setLstSidDateErrorCalc(dataResultAfterIU.getLstSidDateDomainError()); // lstSidDateErrorCalc
		  dataParentDto.getDataSessionDto().setErrorAllCalc(dataResultAfterIU.isErrorAllSidDate()); // errorAllCalc
		  
		DataResultAfterIUDto dataResultAfterIUDto = new DataResultAfterIUDto(dataResultAfterIU, dataParentDto.getDataSessionDto());
		return dataResultAfterIUDto;
	}

	@POST
	@Path("execMonthlyAggregateAsync")
	public AsyncTaskInfo execMonthlyAggregateAsync(DPItemParentDto dataParentDto){
		setDataParent(dataParentDto);
		return execMonthlyAggregateHandler.handle(dataParentDto.getDataParent());
	}

	private void setDataParent(DPItemParentDto dataParentDto) {
		val domain = dataParentDto.getDataSessionDto().getDomainEdits();
		
		List<DailyRecordDto> dailyEdits = new ArrayList<>();
		if (domain == null) {
			dailyEdits = dataParentDto.getDataSessionDto().getDomainOlds();
		} else {
			dailyEdits = (List<DailyRecordDto>) domain;
		}
		dataParentDto.getDataParent().setDailyEdits(dailyEdits);
		dataParentDto.getDataParent().setDailyOlds(dataParentDto.getDataSessionDto().getDomainOlds());
		dataParentDto.getDataParent().setDailyOldForLog(dataParentDto.getDataSessionDto().getDomainOldForLog());
		dataParentDto.getDataParent().setLstAttendanceItem(dataParentDto.getDataSessionDto().getItemIdRCs());
		dataParentDto.getDataParent().setErrorAllSidDate(dataParentDto.getDataSessionDto().isErrorAllCalc());
		dataParentDto.getDataParent().setLstSidDateDomainError(dataParentDto.getDataSessionDto().getLstSidDateErrorCalc());
		dataParentDto.getDataParent().setApprovalConfirmCache(dataParentDto.getDataSessionDto().getApprovalConfirmCache());

		Object objectCacheMonth = dataParentDto.getDataSessionDto().getDomainMonthOpt();
		Optional<MonthlyRecordWorkDto> domainMonthOpt = objectCacheMonth == null ? Optional.empty()
				: (Optional<MonthlyRecordWorkDto>) objectCacheMonth;
		dataParentDto.getDataParent().setDomainMonthOpt(domainMonthOpt);
	}

	@POST
	@Path("insertClosure")
	public void insertClosure(EmpAndDateDto empAndDateDto){
		ApprovalConfirmCache approvalConfirmCache = empAndDateDto.getDataSessionDto().getApprovalConfirmCache(); //approvalConfirm
		DPCorrectionStateParam stateParam = empAndDateDto.getDataSessionDto().getDpStateParam(); //dpStateParam
		ApprovalConfirmCache result = personalTightCommandFacade.addRemovePersonalTight(empAndDateDto.getEmpAndDate().getEmployeeId(), empAndDateDto.getEmpAndDate().getDate(), approvalConfirmCache, stateParam, true);
		empAndDateDto.getDataSessionDto().setApprovalConfirmCache(result); // approvalConfirm
	}
	
	@POST
	@Path("releaseClosure")
	public ReleaseClosureDto releaseClosure(EmpAndDateDto empAndDateDto){
		ApprovalConfirmCache approvalConfirmCache = empAndDateDto.getDataSessionDto().getApprovalConfirmCache(); //approvalConfirm
		DPCorrectionStateParam stateParam = empAndDateDto.getDataSessionDto().getDpStateParam(); //dpStateParam
		ApprovalConfirmCache result = personalTightCommandFacade.addRemovePersonalTight(empAndDateDto.getEmpAndDate().getEmployeeId(), empAndDateDto.getEmpAndDate().getDate(), approvalConfirmCache, stateParam, false);
		empAndDateDto.getDataSessionDto().setApprovalConfirmCache(result); // approvalConfirm
		ReleaseClosureDto dto = new ReleaseClosureDto(new JavaTypeResult<String>(""), empAndDateDto.getDataSessionDto());
		return dto;
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
	    final Set<Object> seen = new HashSet<>();
	    return t -> seen.add(keyExtractor.apply(t));
	}
	
	@POST
	@Path("getApplication")
	public List<Integer> getApplicationName() {
		return dataDialogWithTypeProcessor.getNameAppliction();
	}
	
	@POST
	@Path("getFlexCheck")
	public JavaTypeResult<String>  getValueTimeFlex(CalcFlexDto calc) {
		return new JavaTypeResult<String>(checkBeforeCalcFlex.getConditionCalcFlex(calc));
	}
	
	@POST
	@Path("loadRow")
	@SuppressWarnings("unchecked")
	public DailyPerformanceCorrectionDto reloadRow(DPPramLoadRowDto param) {
		val domain  = param.getDataSessionDto().getDomainEdits();
		List<DailyRecordDto> dailyEdits = new ArrayList<>();
		if(domain == null){
			dailyEdits = param.getDataSessionDto().getDomainOlds();
		}else{
			dailyEdits = (List<DailyRecordDto>) domain;
		}
		param.getDpPramLoadRow().setDailys(dailyEdits);
		param.getDpPramLoadRow().setLstSidDateDomainError(param.getDataSessionDto().getLstSidDateErrorCalc()); // lstSidDateErrorCalc
		param.getDpPramLoadRow().setErrorAllSidDate(param.getDataSessionDto().isErrorAllCalc()); // errorAllCalc
		Integer closureId = param.getDataSessionDto().getClosureId(); // closureId
		param.getDpPramLoadRow().setClosureId(closureId);
		param.getDpPramLoadRow().setApprovalConfirmCache(param.getDataSessionDto().getApprovalConfirmCache()); // approvalConfirm
		DPCorrectionStateParam stateParam = param.getDataSessionDto().getDpStateParam(); // dpStateParam
		param.getDpPramLoadRow().setStateParam(stateParam);
		
		Object objectCacheMonth = param.getDataSessionDto().getDomainMonthOpt(); // domainMonths
		Optional<MonthlyRecordWorkDto> domainMonthOpt = objectCacheMonth == null ? Optional.empty()
				: (Optional<MonthlyRecordWorkDto>) objectCacheMonth;
		param.getDpPramLoadRow().setDomainMonthOpt(domainMonthOpt);
		val result = loadRowProcessor.reloadGrid(param.getDpPramLoadRow());
		if(!param.getDpPramLoadRow().getOnlyLoadMonth()) {
			param.getDataSessionDto().setApprovalConfirmCache(result.getApprovalConfirmCache()); // approvalConfirm
			param.getDataSessionDto().setDomainOlds(result.getDomainOld()); // domainOlds
			param.getDataSessionDto().setDomainOldForLog(result.getDomainOldForLog()); // domainOldForLog
		}
		result.setApprovalConfirmCache(null);
		result.setDomainOld(Collections.emptyList());
		param.getDataSessionDto().setDomainEdits(null); // domainEdits
		result.setLstCellState(result.getMapCellState().values().stream().collect(Collectors.toList()));
		result.setMapCellState(null);
		return result;
	}

	@POST
	@Path("loadVerData")
	@SuppressWarnings("unchecked")
	public LoadVerDataResultDto addAndUpdate(LoadVerDataDto loadVerDataDto) {
		val domain = loadVerDataDto.getDataSessionDto().getDomainEdits(); // domainEdits
		List<DailyRecordDto> dailyEdits = new ArrayList<>();
		if (domain == null) {
			dailyEdits = loadVerDataDto.getDataSessionDto().getDomainOlds(); //domainOlds
		} else {
			dailyEdits = (List<DailyRecordDto>) domain;
		}
		loadVerDataDto.getLoadVerData().setLstDomainOld(dailyEdits);
		loadVerDataDto.getLoadVerData().setApprovalConfirmCache(loadVerDataDto.getDataSessionDto().getApprovalConfirmCache()); //approvalConfirm
		DPCorrectionStateParam stateParam = loadVerDataDto.getDataSessionDto().getDpStateParam(); //dpStateParam
		loadVerDataDto.getLoadVerData().setStateParam(stateParam);
		val result = dPLoadVerProcessor.loadVerAfterCheckbox(loadVerDataDto.getLoadVerData());
		loadVerDataDto.getDataSessionDto().setDomainEdits(null); // domainEdits
		loadVerDataDto.getDataSessionDto().setDomainOlds(result.getLstDomainOld()); // domainOlds
		result.setLstDomainOld(new ArrayList<>());
		loadVerDataDto.getDataSessionDto().setApprovalConfirmCache(result.getApprovalConfirmCache()); // approvalConfirm
		result.setApprovalConfirmCache(null);
		LoadVerDataResultDto dto = new LoadVerDataResultDto(result, loadVerDataDto.getDataSessionDto());
		return dto;
	}
	
	@POST
	@Path("exportCsv")
	public ExportServiceResult exportCsvErrorInfor(List<DailyPerformErrorReferExportDto> command) {
		return this.dailyPerformErrorExportService.start(command);
	}

	@POST
	@Path("getErrAndAppTypeCd")
	public DailyPerformErrorReferDto findByCidAndListErrCd(List<String> listErrorCode) {
		return this.dailyPerforErrorReferFinder.findByCidAndListErrCd(listErrorCode);
	}
	
	/**
	 * typeOfAttendanceItem = 0 to case is monthly
	 * 
	 * @param dailyAttendanceItemIds
	 * @return
	 */
	@POST
	@Path("getNameMonthlyAttItem")
	public List<AttItemName> getNameOfMonthlyAttendanceItem(List<Integer> monthlyAttendanceItemIds) {
		return this.monthlyPerfomanceAuthorityFinder.getListAttendanceItemName(monthlyAttendanceItemIds);
	}
	
	@POST
	@Path("getnameattItembytype/{type}")
	public List<AttItemName> getNameOfAttendanceItemByType(@PathParam(value = "type") int type) {
		return this.monthlyPerfomanceAuthorityFinder.getListAttendanceItemNameByType(type);
	}
	
	@POST
	@Path("getNamedailyAttItem")
	public List<AttItemName> getNameOfDailyAttendanceItem(List<Integer> dailyAttendanceItemIds) {
		return this.dailyPerformanceAuthoritySetting.getListAttendanceItemName(dailyAttendanceItemIds);
	}
	

	@POST
	@Path("get-info/{employeeCode}")
	public EmployeeSearchData getInfo(@PathParam(value = "employeeCode") String employeeCode) {
		EmployeeSearchDto dto = new EmployeeSearchDto();
		dto.setBaseDate(GeneralDate.today());
		dto.setSystem("1");
		dto.setEmployeeCode(employeeCode);
		return this.searchEmployeeService.searchByCode(dto);
	}
	
	@POST
	@Path("calcTime")
	@SuppressWarnings("unchecked")
	public DCCalcTimeDto calcTime(DCCalcTimeParamDto dcTimeParam) {
		val domain  = dcTimeParam.getDataSessionDto().getDomainEdits(); // domainEdits
		List<DailyRecordDto> dailyEdits = new ArrayList<>();
		if(domain == null){
			dailyEdits = cloneListDto(dcTimeParam.getDataSessionDto().getDomainOlds());
		}else{
			dailyEdits = (List<DailyRecordDto>) domain;
		}
		
		val result = dailyCorrectCalcTimeService.calcTime(dailyEdits, dcTimeParam.getCalcTimeParam().getItemEdits(), dcTimeParam.getCalcTimeParam().getChangeSpr31(), dcTimeParam.getCalcTimeParam().getChangeSpr34(), dcTimeParam.getCalcTimeParam().isNotChangeCell());
		dcTimeParam.getDataSessionDto().setDomainEdits(result.getDailyEdits());
		result.setDailyEdits(Collections.emptyList());
		DCCalcTimeDto dcCalcTimeDto = new DCCalcTimeDto(result, dcTimeParam.getDataSessionDto());
		return dcCalcTimeDto;
	}
	
	@POST
	@Path("calculation")
	@SuppressWarnings("unchecked")
	public DailyPerformanceCalcDto calculation(DPItemParentDto dataParentDto) {
		val domain  = dataParentDto.getDataSessionDto().getDomainEdits();
		List<DailyRecordDto> dailyEdits = new ArrayList<>();
		if(domain == null){
			dailyEdits = cloneListDto(dataParentDto.getDataSessionDto().getDomainOlds());
		}else{
			dailyEdits = (List<DailyRecordDto>) domain;
		}
		dataParentDto.getDataParent().setDailyEdits(dailyEdits);
		dataParentDto.getDataParent().setDailyOlds(dataParentDto.getDataSessionDto().getDomainOlds()); // domainOlds
		
		dataParentDto.getDataParent().setLstAttendanceItem(dataParentDto.getDataSessionDto().getItemIdRCs()); //itemIdRCs
		dataParentDto.getDataParent().setLstData(dataParentDto.getDataSessionDto().getDataSource()); // dataSource
		Object objectCacheMonth = dataParentDto.getDataSessionDto().getDomainMonthOpt(); // domainMonths
		Optional<MonthlyRecordWorkDto> domainMonthOpt = objectCacheMonth == null ? Optional.empty()
				: (Optional<MonthlyRecordWorkDto>) objectCacheMonth;
		dataParentDto.getDataParent().setDomainMonthOpt(domainMonthOpt);

		ParamCommonAsync paramCommonAsync = dataParentDto.getDataSessionDto().getParamCommonAsync(); // resultMonthReturn
		dataParentDto.getDataParent().setParamCommonAsync(paramCommonAsync);
		val result = dailyCalculationService.calculateCorrectedResults(dataParentDto.getDataParent());

		dataParentDto.getDataSessionDto().setDomainEdits(result.getCalculatedRows());// domainEdits
		dataParentDto.getDataSessionDto().setLstSidDateErrorCalc(result.getLstSidDateDomainError());// lstSidDateErrorCalc
		dataParentDto.getDataSessionDto().setErrorAllCalc(result.isErrorAllSidDate());// errorAllCalc
		result.setCalculatedRows(Collections.emptyList());
		
		DailyPerformanceCalcDto calcDto = new DailyPerformanceCalcDto(result, dataParentDto.getDataSessionDto());
		return calcDto;
	}
	
	@POST
	@Path("getRemainNum")
	public HolidayRemainNumberDto getRemainNumb(HolidayRemainParam param) {
		return remainNumberService.getRemainingHolidayNumber(param.getEmployeeId(), param.getClosureDate());
	}

	@POST
	@Path("lock")
	public DailyPerformanceCorrectionDto processLock(DPDisplayLockParamDto param) {
		Integer closureId = param.getDataSessionDto().getClosureId();
		param.getDpDisplayLockParam().setClosureId(closureId);
		DPCorrectionStateParam stateParam = param.getDataSessionDto().getDpStateParam();
		param.getDpDisplayLockParam().setStateParam(stateParam);
		DailyPerformanceCorrectionDto dtoResult = dpDisplayLockProcessor.processDisplayLock(param.getDpDisplayLockParam());
		dtoResult.setLstCellState(dtoResult.getMapCellState().values().stream().collect(Collectors.toList()));
		dtoResult.setMapCellState(null);
		return dtoResult;
	}

	@POST
	@Path("gendate")
	public DatePeriodInfo genDateFromYM(GenDateProcessDto param) {
		DPCorrectionStateParam stateParam = param.getDataSessionDto().getDpStateParam();
		DatePeriodInfo dateInfo = genDateProcessor.genDateFromYearMonth(param.getGenDateDto());
		if (stateParam != null && dateInfo != null) {
			stateParam.setDateInfo(dateInfo);
			param.getDataSessionDto().setDpStateParam(stateParam); 
		}
		return dateInfo;
	}
	
	private List<DailyRecordDto> cloneListDto(List<DailyRecordDto> dtos){
		if(dtos == null) return new ArrayList<>();
		return dtos.stream().map(x -> x.clone()).collect(Collectors.toList());
	}
	
	private void removeSession() {
		session.setAttribute("lstSidDateErrorCalc", Collections.emptyList());
		session.setAttribute("errorAllCalc", false);
	}
	
	@POST
	@Path("getErrorMobile")
	public List<ErAlWorkRecordShortDto> getErrorMobile(ErrorParam errorParam) {
		return dpCorrectionProcessorMob.getErrorMobile(
				new DatePeriod(errorParam.getStartDate(), errorParam.getEndDate()), 
				errorParam.getEmployeeIDLst(), 
				errorParam.getAttendanceItemID());
	}

	@POST
	@Path("getMasterDialogMob")
	public Map<Integer, List<CodeName>> getMasterDialog(MasterDialogParam masterDialogParam) {
		String companyID = AppContexts.user().companyId();
		Map<Integer, Map<String, CodeName>> allMasterData = dialogProcessor.getAllCodeNameWT(
				masterDialogParam.getTypes(),
				companyID,				
				masterDialogParam.getEmployeeID(),
				masterDialogParam.getWorkTypeCD(),
				masterDialogParam.getDate());
		return allMasterData.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new ArrayList<CodeName>(entry.getValue().values())));
	}
	
	@POST
	@Path("getMasterTaskSupMob")
	public Map<Integer, List<CodeName>> getMasterTaskSup(MasterDialogParam masterDialogParam) {
		Map<Integer, Map<String, CodeName>> allMasterData = dialogProcessor.getAllCodeNameByItemId(				
				masterDialogParam.getDate(), 
				masterDialogParam.getItemIds());
		return allMasterData.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> new ArrayList<CodeName>(entry.getValue().values())));
	}

	
	@POST
	@Path("getPrimitiveAll")
	public Map<Integer, String> getPrimitiveAll() {
		return DPHeaderDto.getPrimitiveAll();
	}
	
	@SuppressWarnings("unchecked")
    @POST
	@Path("findWplIDByCode")
	public GetWkpIDOutput findWplIDByCode(GetWkpIDParam param) {
	    val domain  = param.getDataSessionDto().getDomainEdits();
        List<DailyRecordDto> dailyEdits = new ArrayList<>();
        if(domain == null){
            dailyEdits = cloneListDto(param.getDataSessionDto().getDomainOlds());
        }else{
            dailyEdits = (List<DailyRecordDto>) domain;
        }
        
        Optional<DailyRecordDto> dailyEditOpt = dailyEdits.stream().filter(x -> {
            return x.getDate().toString("yyyy/MM/dd").equals(param.getBaseDate());
        }).findFirst();
        
        return new GetWkpIDOutput(dailyEditOpt.map(x -> x.getAffiliationInfo().getWorkplaceID()).orElse(null));
//	    return new GetWkpIDOutput(workplacePub.getWkpNewByCdDate(
//	            param.getCompanyId(), 
//	            param.getWkpCode(), 
//	            GeneralDate.fromString(param.baseDate, "yyyy/MM/dd")).orElse(null));
	}
}
