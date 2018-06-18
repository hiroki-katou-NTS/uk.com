package nts.uk.file.at.infra.dailyschedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceItemValueImport;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarksContentChoice;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.HierarchyCode;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;
import nts.uk.file.at.app.export.dailyschedule.AttendanceResultImportAdapter;
import nts.uk.file.at.app.export.dailyschedule.FileOutputType;
import nts.uk.file.at.app.export.dailyschedule.FormOutputType;
import nts.uk.file.at.app.export.dailyschedule.OutputConditionSetting;
import nts.uk.file.at.app.export.dailyschedule.PageBreakIndicator;
import nts.uk.file.at.app.export.dailyschedule.TotalDayCountWs;
import nts.uk.file.at.app.export.dailyschedule.TotalWorkplaceHierachy;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleSettingTotalOutput;
import nts.uk.file.at.app.export.dailyschedule.data.DailyPerformanceHeaderData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyPerformanceReportData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyPersonalPerformanceData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyReportData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyWorkplaceData;
import nts.uk.file.at.app.export.dailyschedule.data.DetailedDailyPerformanceReportData;
import nts.uk.file.at.app.export.dailyschedule.data.EmployeeReportData;
import nts.uk.file.at.app.export.dailyschedule.data.OutputItemSetting;
import nts.uk.file.at.app.export.dailyschedule.data.WorkplaceDailyReportData;
import nts.uk.file.at.app.export.dailyschedule.data.WorkplaceReportData;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalCountDay;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalSumRowOfDailySchedule;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;
import nts.uk.file.at.app.export.dailyschedule.totalsum.WorkplaceTotal;
import nts.uk.file.at.app.export.employee.jobtitle.EmployeeJobHistExport;
import nts.uk.file.at.app.export.employee.jobtitle.JobTitleImportAdapter;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeWorkScheduleOutputConditionGenerator.
 * @author HoangNDH
 */
@Stateless
public class AsposeWorkScheduleOutputConditionGenerator extends AsposeCellsReportGenerator implements WorkScheduleOutputGenerator{

	/** The workplace config repository. */
	@Inject
	private WorkplaceConfigInfoRepository workplaceConfigRepository;
	
	/** The workplace info repository. */
	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;
	
	/** The error alarm repository. */
	@Inject
	private EmployeeDailyPerErrorRepository errorAlarmRepository;
	
	/** The attendace adapter. */
	@Inject
	private AttendanceResultImportAdapter attendaceAdapter;
	
	/** The total day count ws. */
	@Inject
	private TotalDayCountWs totalDayCountWs;
	
	/** The error alarm work record adapter. */
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepo;
	
	/** The edit state finder. */
	@Inject
	private EditStateOfDailyPerformanceFinder editStateFinder;
	
	/** The workplace adapter. */
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	/**  The employment adapter. */
	@Inject
	private ScEmploymentAdapter employmentAdapter;
	
	/** The output item repo. */
	@Inject
	private OutputItemDailyWorkScheduleRepository outputItemRepo;
	
	/** The employee adapter. */
	@Inject
	private SCEmployeeAdapter employeeAdapter;
	
	/** The finder. */
	@Inject
	private EmploymentRepository employmentRepository;
	
	/** The attendance name service. */
	@Inject
	private DailyAttendanceItemNameDomainService attendanceNameService;
	
	/** The job title adapter. */
	@Inject
	private JobTitleImportAdapter jobTitleAdapter;
	
	/** The company repository. */
	@Inject
	private CompanyRepository companyRepository;
	
	/** The worktype repo. */
	@Inject
	private WorkTypeRepository worktypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepo;
	
	/** The filename. */
	private final String filename = "report/KWR001.xlsx";
	
	/** The Constant CHUNK_SIZE. */
	private static final int CHUNK_SIZE = 16;
	
	/** The Constant DATA_COLUMN_INDEX. */
	private static final int[] DATA_COLUMN_INDEX = {3, 8, 10, 14, 16, 39};
	
	/** The font family. */
	private final String FONT_FAMILY = "ＭＳ ゴシック";
	
	/** The font size. */
	private final int FONT_SIZE = 9;
	
	/* (non-Javadoc)
	 * @see nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator#generate(nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition, nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo, nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery)
	 */
	@Override
	public void generate(FileGeneratorContext generatorContext, WorkScheduleOutputQuery query) {
		val reportContext = this.createContext(filename);
		WorkScheduleOutputCondition condition = query.getCondition();
		
		// ドメインモデル「日別勤務表の出力項目」を取得する
		Optional<OutputItemDailyWorkSchedule> optOutputItemDailyWork = outputItemRepo.findByCidAndCode(AppContexts.user().companyId(), query.getCondition().getCode().v());
		if (!optOutputItemDailyWork.isPresent()) {
			throw new BusinessException(new RawErrorMessage("Msg_1141"));
		}
		
		Workbook workbook;
		try {
			workbook = new Workbook(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
			
			workbook = reportContext.getWorkbook();
			
			WorkbookDesigner designer = new WorkbookDesigner();
			designer.setWorkbook(workbook);
			
			TotalSumRowOfDailySchedule sum = new TotalSumRowOfDailySchedule();
			sum.setPersonalTotal(new ArrayList<>());
			
			/**
			 * Collect data
			 */
			DailyPerformanceReportData reportData = new DailyPerformanceReportData();
			collectData(reportData, query, condition, optOutputItemDailyWork.get());
			
			Worksheet sheet;
			Integer currentRow, dateRow;
			
			// Calculate row size and get sheet
			int nListOutputCode = reportData.getHeaderData().getLstOutputItemSettingCode().size();
			int nSize;
			if (nListOutputCode % CHUNK_SIZE == 0) {
				nSize = reportData.getHeaderData().getLstOutputItemSettingCode().size() / CHUNK_SIZE;
			}
			else {
				nSize = reportData.getHeaderData().getLstOutputItemSettingCode().size() / CHUNK_SIZE + 1;
			}
			if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
				switch (nSize) {
				case 1:
					sheet = workbook.getWorksheets().get(0);
					break;
				case 2:
					sheet = workbook.getWorksheets().get(1);
					break;
				case 3:
				default:
					sheet = workbook.getWorksheets().get(2);
				}
				
			} else {
				switch (nSize) {
				case 1:
					sheet = workbook.getWorksheets().get(3);
					break;
				case 2:
					sheet = workbook.getWorksheets().get(4);
					break;
				case 3:
				default:
					sheet = workbook.getWorksheets().get(5);
					
				}
			}
			currentRow = 2;
			dateRow = 0;
			
			WorksheetCollection sheetCollection = workbook.getWorksheets();
			
			// Write header data
			writeHeaderData(query, sheet, reportData, dateRow);
			
			// Copy footer
			//copyFooter(sheet, sheetCollection.get(6));
			 	
			// Set all fixed cell
			setFixedData(condition, sheet, reportData, currentRow);
			
			// Write display map
			writeDisplayMap(sheet.getCells(),reportData, currentRow, nSize);
			
			currentRow+=nSize*2;
			
			// Create row page tracker
			RowPageTracker rowPageTracker = new RowPageTracker();
			rowPageTracker.initMaxRowAllowed(nSize, condition.getOutputType());
			
			// Write detailed work schedule
			if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE)
				currentRow = writeDetailedWorkSchedule(currentRow, sheetCollection, sheet, reportData.getWorkplaceReportData(), nSize, condition, rowPageTracker);
			else {
				DailyReportData dailyReportData = reportData.getDailyReportData();
				currentRow = writeDetailedDailySchedule(currentRow, sheetCollection, sheet, dailyReportData, nSize, condition, rowPageTracker);
			}
			
			// Rename sheet
			sheet.setName(WorkScheOutputConstants.SHEET_FILE_NAME);
			
			// Move to first position
			sheet.moveTo(0);
			
			// Delete the rest of workbook
			int sheetCount = sheetCollection.getCount();
			for (int i = sheetCount - 1; i > 0; i--) {
				sheetCollection.removeAt(i);
			}
			
			// Process designer
			reportContext.processDesigner();
			
			// Get current date and format it
			DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN);
			String currentFormattedDate = LocalDateTime.now().format(jpFormatter);
			
			// Save workbook
			if (query.getFileType() == FileOutputType.FILE_TYPE_EXCEL)
				reportContext.saveAsExcel(this.createNewFile(generatorContext, WorkScheOutputConstants.SHEET_FILE_NAME + "_" + currentFormattedDate + ".xlsx"));
			else {
				// Create print area
				createPrintArea(currentRow, sheet);
				reportContext.saveAsPdf(this.createNewFile(generatorContext, WorkScheOutputConstants.SHEET_FILE_NAME + "_" + currentFormattedDate + ".pdf"));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * Sets the font style.
	 *
	 * @param style the new font style
	 */
	private void setFontStyle (Style style){
		
		Font font = style.getFont();
		font.setSize(FONT_SIZE);
		font.setName(FONT_FAMILY);
	}
	
	/**
	 * Collect data.
	 *
	 * @param reportData the report data
	 * @param query the query
	 * @param condition the condition
	 * @param outputItem the output item
	 */
	public void collectData(DailyPerformanceReportData reportData, WorkScheduleOutputQuery query, WorkScheduleOutputCondition condition, OutputItemDailyWorkSchedule outputItem) {
		reportData.setHeaderData(new DailyPerformanceHeaderData());
		collectHeaderData(reportData.getHeaderData(), condition);
		collectDisplayMap(reportData.getHeaderData(), outputItem);
		GeneralDate endDate = query.getEndDate();
		
		WorkScheduleQueryData queryData = new WorkScheduleQueryData();
		queryData.setQuery(query);
		
		List<GeneralDate> lstDate = new DateRange(query.getStartDate(), endDate).toListDate();
		queryData.setDatePeriod(lstDate);
		
		Map<String, WorkplaceInfo> lstWorkplace = new TreeMap<>(); // Automatically sort by code, will need to check hierarchy later
		List<String> lstWorkplaceId = new ArrayList<>();
		
		for (String employeeId: query.getEmployeeId()) {
			WkpHistImport workplaceImport = workplaceAdapter.findWkpBySid(employeeId, endDate);
			lstWorkplaceId.add(workplaceImport.getWorkplaceId());
			queryData.getLstWorkplaceImport().add(workplaceImport);
		}
		
		String companyId = AppContexts.user().companyId();
		
		List<WorkplaceConfigInfo> lstWorkplaceConfigInfo = workplaceConfigRepository.findByWkpIdsAtTime(companyId, endDate, lstWorkplaceId);
		queryData.setLstWorkplaceConfigInfo(lstWorkplaceConfigInfo);
		
		// Collect child workplace, automatically sort into tree map
		for (String entry: lstWorkplaceId) {
			lstWorkplace.putAll(collectWorkplaceHierarchy(entry, endDate, lstWorkplaceConfigInfo));
		}
		
		List<AttendanceResultImport> lstAttendanceResultImport = attendaceAdapter.getValueOf(query.getEmployeeId(), new DatePeriod(query.getStartDate(), query.getEndDate()), AttendanceResultImportAdapter.convertList(outputItem.getLstDisplayedAttendance(), x -> x.getAttendanceDisplay()));
		queryData.setLstAttendanceResultImport(lstAttendanceResultImport);
		
		// Check lowest level of employee and highest level of output setting, and attendance result count is 0
		// 階層累計行のみ出力する設定の場合、データ取得件数は0件として扱い、エラーメッセージを表示(#Msg_37#)
		int lowestEmployeeLevel = checkLowestWorkplaceLevel(lstWorkplace);
		TotalWorkplaceHierachy outputSetting = condition.getSettingDetailTotalOutput().getWorkplaceHierarchyTotal();
		int highestOutputLevel = outputSetting.getHighestLevelEnabled();
		if (lowestEmployeeLevel < highestOutputLevel && lstAttendanceResultImport.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Msg_37"));
		}
		
		List<WorkType> lstWorkType = worktypeRepo.findByCompanyId(companyId);
		queryData.setLstWorkType(lstWorkType);
		List<WorkTimeSetting> lstWorkTime = workTimeRepo.findByCompanyId(companyId);
		queryData.setLstWorkTime(lstWorkTime);
		
		// Sort attendance display
		List<AttendanceItemsDisplay> lstAttendanceItemsDisplay = outputItem.getLstDisplayedAttendance().stream().sorted((o1,o2) -> o1.getOrderNo() - o2.getOrderNo()).collect(Collectors.toList());
		queryData.setLstDisplayItem(lstAttendanceItemsDisplay);
		
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			WorkplaceReportData data = new WorkplaceReportData();
			data.workplaceCode = "";
			reportData.setWorkplaceReportData(data);
			
			analyzeInfoExportByEmployee(lstWorkplace, data);
			
			List<EmployeeDto> lstEmloyeeDto = employeeAdapter.findByEmployeeIds(query.getEmployeeId());
			
			for (EmployeeDto dto: lstEmloyeeDto) {
				EmployeeReportData employeeData = collectEmployeePerformanceDataByEmployee(reportData, queryData, dto);
				
				// Calculate total count day
				if (condition.getSettingDetailTotalOutput().isTotalNumberDay()) {
					totalDayCountWs.calculateAllDayCount(dto.getEmployeeId(), new DateRange(query.getStartDate(), query.getEndDate()), employeeData.totalCountDay);
				}
			}
			
			calculateTotalExportByEmployee(data, lstAttendanceItemsDisplay);
		
		}
		else {
			DailyReportData dailyReportData = new DailyReportData();
			reportData.setDailyReportData(dailyReportData);
			DateRange dateRange = new DateRange(query.getStartDate(), query.getEndDate());
			List<WorkplaceDailyReportData> lstReportData = dateRange.toListDate().stream().map(x -> {
				WorkplaceDailyReportData dailyWorkplaceReportData = new WorkplaceDailyReportData();
				dailyWorkplaceReportData.lstWorkplaceData = new DailyWorkplaceData();
				dailyWorkplaceReportData.lstWorkplaceData.workplaceCode = "";
				dailyWorkplaceReportData.date = x;
				return dailyWorkplaceReportData;
			}).collect(Collectors.toList());
			dailyReportData.setLstDailyReportData(lstReportData);
			
			final Map<String, WorkplaceInfo> lstWorkplaceTemp = lstWorkplace;
			List<String> lstAddedCode = new ArrayList<>();
			lstReportData.forEach(x -> {
				analyzeInfoExportByDate(lstWorkplaceTemp, x.getLstWorkplaceData(), lstAddedCode);
			});
			
			collectEmployeePerformanceDataByDate(reportData, queryData);
			// Calculate workplace total
			lstReportData.forEach(dailyData -> {
				calculateTotalExportByDate(dailyData.getLstWorkplaceData());
			});
			
			// Calculate gross total
			calculateGrossTotalByDate(dailyReportData);
		}
	}
	
	/**
	 * Get lowest workplace hierarchy level.
	 *
	 * @param lstWorkplace the lst workplace
	 * @return the int
	 */
	private int checkLowestWorkplaceLevel(Map<String, WorkplaceInfo> lstWorkplace) {
		int lowestLevel = 0;
		
		for (Map.Entry<String, WorkplaceInfo> entry: lstWorkplace.entrySet()) {
			String key = entry.getKey();
			int level = key.length() / 3;
			if (lowestLevel < level) {
				lowestLevel = level;
			}
		}
		
		return lowestLevel;
	}

	/**
	 * Collect employee performance data by date.
	 *
	 * @param reportData the report data
	 * @param query the query
	 * @param lstAttendanceResultImport the lst attendance result import
	 * @param datePeriod the date period
	 * @param lstWorkType the lst work type
	 * @param lstDisplayItem the lst display item
	 */
	private void collectEmployeePerformanceDataByDate(DailyPerformanceReportData reportData, WorkScheduleQueryData queryData) {
		String companyId = AppContexts.user().companyId();
		WorkScheduleOutputQuery query = queryData.getQuery();
		GeneralDate endDate = query.getEndDate();
		WorkScheduleOutputCondition condition = query.getCondition();
		// Always has item because this has passed error check
		OutputItemDailyWorkSchedule outSche = outputItemRepo.findByCidAndCode(companyId, condition.getCode().v()).get();
		
		// Get all data from query data container
		List<GeneralDate> datePeriod = queryData.getDatePeriod();
		List<AttendanceResultImport> lstAttendanceResultImport = queryData.getLstAttendanceResultImport();
		List<AttendanceItemsDisplay> lstDisplayItem = queryData.getLstDisplayItem();
		List<WorkType> lstWorkType = queryData.getLstWorkType();
		List<WorkTimeSetting> lstWorkTime = queryData.getLstWorkTime();
		List<WkpHistImport> lstWorkplaceHistImport = queryData.getLstWorkplaceImport();
		
		// Get all error alarm code
		List<ErrorAlarmWorkRecordCode> lstErAlCode = condition.getErrorAlarmCode().get();
		
		List<String> lstEmployeeId = query.getEmployeeId();
		List<EmployeeDto> lstEmployeeDto = employeeAdapter.findByEmployeeIds(lstEmployeeId);
		
		List<EmployeeDailyPerError> errorListAllEmployee = errorAlarmRepository.finds(lstEmployeeId, new DatePeriod(query.getStartDate(), endDate));
		// Get list remark check box from screen C (UI)
		List<PrintRemarksContent> lstRemarkContent = outSche.getLstRemarkContent();
		
		Set<String> lstAllErrorCode = errorListAllEmployee.stream().map(x -> x.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toSet());
		List<String> tempErrorCode = new ArrayList<>();
		tempErrorCode.addAll(lstAllErrorCode);
		List<ErrorAlarmWorkRecord> lstAllErrorRecord = errorAlarmWorkRecordRepo.getListErAlByListCode(companyId, tempErrorCode);
		
		for (String employeeId: lstEmployeeId) {
			Optional<EmployeeDto> optEmployeeDto = lstEmployeeDto.stream().filter(employee -> StringUtils.equalsAnyIgnoreCase(employee.getEmployeeId(),employeeId)).findFirst();
			
			datePeriod.stream().forEach(date -> {
				Optional<WorkplaceDailyReportData> optDailyWorkplaceData = reportData.getDailyReportData().getLstDailyReportData().stream().filter(x -> x.getDate().compareTo(date) == 0).findFirst();
				DailyWorkplaceData dailyWorkplaceData = findWorkplace(employeeId,optDailyWorkplaceData.get().getLstWorkplaceData(), endDate, lstWorkplaceHistImport, queryData.getLstWorkplaceConfigInfo());
				if (dailyWorkplaceData != null) {
					DailyPersonalPerformanceData personalPerformanceDate = new DailyPersonalPerformanceData();
					if (optEmployeeDto.isPresent())
						personalPerformanceDate.setEmployeeName(optEmployeeDto.get().getEmployeeName());
					dailyWorkplaceData.getLstDailyPersonalData().add(personalPerformanceDate);
					
					lstAttendanceResultImport.stream().filter(x -> (x.getEmployeeId().equals(employeeId) && x.getWorkingDate().compareTo(date) == 0)).forEach(x -> {
						GeneralDate workingDate = x.getWorkingDate();
						
						// ドメインモデル「社員の日別実績エラー一覧」を取得する
						List<EmployeeDailyPerError> errorList = errorListAllEmployee.stream()
								.filter(error -> StringUtils.equalsAnyIgnoreCase(error.getEmployeeID(), employeeId) && error.getDate().compareTo(workingDate) == 0).collect(Collectors.toList());
						
						List<String> lstRemarkContentStr = new ArrayList<>();
						if (query.getCondition().getErrorAlarmCode().isPresent()) {
							lstRemarkContent.stream().filter(remark -> remark.isUsedClassification()).forEach(remark -> {
								// Get possible content based on remark choice
								Optional<PrintRemarksContent> optContent = getRemarkContent(employeeId, workingDate, errorList, remark.getPrintItem(), lstErAlCode);
								if (optContent.isPresent()) {
									// Add to remark
									lstRemarkContentStr.add(TextResource.localize(optContent.get().getPrintItem().shortName));
								}
							});
							
							personalPerformanceDate.detailedErrorData = String.join("、", lstRemarkContentStr);
						}
						
						// ER/AL
						if (query.getCondition().getConditionSetting() == OutputConditionSetting.USE_CONDITION) {
							boolean erMark = false, alMark = false;
							
							List<String> lstErrorCode = errorList.stream().map(error -> error.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList());
							// ドメインモデル「勤務実績のエラーアラーム」を取得する
							//List<ErrorAlarmWorkRecord> lstErrorRecord = errorAlarmWorkRecordRepo.getListErAlByListCode(companyId, lstErrorCode);
							
							List<ErrorAlarmWorkRecord> lstErrorRecord = lstAllErrorRecord.stream().filter(err -> {
								return  lstErrorCode.contains(err.getCode().v());
							}).collect(Collectors.toList());
							
							for (ErrorAlarmWorkRecord error : lstErrorRecord) {
								// コードから区分を取得する
								switch (error.getTypeAtr()) {
								case ALARM: // 区分　＝　エラー　のとき　AL
									alMark = true; 
									break;
								case ERROR: // 区分　=　アラーム　のとき　ER
									erMark = true;
									break;
								case OTHER:
									break;
								}
								if (erMark && alMark) break;
							}
							if (erMark && alMark) {
								personalPerformanceDate.errorAlarmCode = WorkScheOutputConstants.ER_AL;
							}
							else if (erMark) {
								personalPerformanceDate.errorAlarmCode = WorkScheOutputConstants.ER;
							}
							else if (alMark) {
								personalPerformanceDate.errorAlarmCode = WorkScheOutputConstants.AL;
							}
						}
						personalPerformanceDate.actualValue = new ArrayList<>();
						lstDisplayItem.stream().forEach(item -> {
							Optional<AttendanceItemValueImport> optItemValue = x.getAttendanceItems().stream().filter(att -> att.getItemId() == item.getAttendanceDisplay()).findFirst();
							if (optItemValue.isPresent()) {
								AttendanceItemValueImport itemValue = optItemValue.get();
								if (itemValue.getValueType() == ActualValue.STRING) {
									int attendanceId = itemValue.getItemId();
									switch (attendanceId) {
									case 1:
									case 28: // Work type
										Optional<WorkType> optWorkType = lstWorkType.stream().filter(type -> type.getWorkTypeCode().v().equalsIgnoreCase(itemValue.getValue())).findFirst();
										optWorkType.ifPresent(workType -> {
											if (outSche.getWorkTypeNameDisplay() == NameWorkTypeOrHourZone.OFFICIAL_NAME)
												itemValue.setValue(workType.getName().v());
											else
												itemValue.setValue(workType.getAbbreviationName().v());
										});
										break;
									case 2:
									case 29: // Work time
										Optional<WorkTimeSetting> optWorkTime = lstWorkTime.stream().filter(type -> type.getWorktimeCode().v().equalsIgnoreCase(itemValue.getValue())).findFirst();
										optWorkTime.ifPresent(workTime -> {
											if (outSche.getWorkTypeNameDisplay() == NameWorkTypeOrHourZone.OFFICIAL_NAME)
												itemValue.setValue(workTime.getWorkTimeDisplayName().getWorkTimeName().v());
											else
												itemValue.setValue(workTime.getWorkTimeDisplayName().getWorkTimeAbName().v());
										});
										break;
									default:
										break;
									}
								}
								personalPerformanceDate.actualValue.add(new ActualValue(itemValue.getItemId(), itemValue.getValue(), itemValue.getValueType()));
							}
							else {
								personalPerformanceDate.actualValue.add(new ActualValue(0, "", ActualValue.STRING));
							}
						});
					});
				}
			});
			
			
		}
	}

	/**
	 * Collect employee performance data by employee.
	 *
	 * @param reportData the report data
	 * @param query the query
	 * @param lstAttendanceResultImport the lst attendance result import
	 * @param employeeId the employee id
	 * @param lstWorkType the lst work type
	 * @param lstDisplayItem the lst display item
	 * @return the employee report data
	 */
	private EmployeeReportData collectEmployeePerformanceDataByEmployee(DailyPerformanceReportData reportData, WorkScheduleQueryData queryData, EmployeeDto employeeDto) {
		String companyId = AppContexts.user().companyId();
		WorkScheduleOutputQuery query = queryData.getQuery();
		GeneralDate endDate = query.getEndDate();
		WorkScheduleOutputCondition condition = query.getCondition();
		
		// Always has item because this has passed error check
		OutputItemDailyWorkSchedule outSche = outputItemRepo.findByCidAndCode(companyId, condition.getCode().v()).get();
		
		// Get all data from query data container
		List<AttendanceResultImport> lstAttendanceResultImport = queryData.getLstAttendanceResultImport();
		List<AttendanceItemsDisplay> lstDisplayItem = queryData.getLstDisplayItem();
		List<WorkType> lstWorkType = queryData.getLstWorkType();
		List<WorkTimeSetting> lstWorkTime = queryData.getLstWorkTime();
		List<WkpHistImport> lstWorkplaceImport = queryData.getLstWorkplaceImport();
		List<WorkplaceConfigInfo> lstWorkplaceConfigInfo = queryData.getLstWorkplaceConfigInfo();
		String employeeId = employeeDto.getEmployeeId();
		
		WorkplaceReportData workplaceData = findWorkplace(employeeId, reportData.getWorkplaceReportData(), endDate, lstWorkplaceImport, lstWorkplaceConfigInfo);
		EmployeeReportData employeeData = new EmployeeReportData();
		
		List<ErrorAlarmWorkRecordCode> lstErAlCode = condition.getErrorAlarmCode().get();
		
		// Employee code and name
		employeeData.employeeName = employeeDto.getEmployeeName();
		employeeData.employeeCode = employeeDto.getEmployeeCode();
		
		// Employment
		Optional<EmploymentHistoryImported> optEmploymentImport = employmentAdapter.getEmpHistBySid(companyId, employeeId, endDate);
		if (optEmploymentImport.isPresent()) {
			String employmentCode = optEmploymentImport.get().getEmploymentCode();
			Optional<Employment> optEmployment = employmentRepository.findEmployment(companyId, employmentCode);
			if (optEmployment.isPresent()) {
				Employment employment = optEmployment.get();
				employeeData.employmentName = employment.getEmploymentName().v();
			}
		}
		
		// Job title
		Optional<EmployeeJobHistExport> optJobTitle = jobTitleAdapter.findBySid(employeeId, endDate);
		if (optJobTitle.isPresent())
			employeeData.position = optJobTitle.get().getJobTitleName();
		else
			employeeData.position = "";
		
		employeeData.lstDetailedPerformance = new ArrayList<>();
		workplaceData.lstEmployeeReportData.add(employeeData);
		lstAttendanceResultImport.stream().filter(x -> x.getEmployeeId().equals(employeeId)).sorted((o1,o2) -> o1.getWorkingDate().compareTo(o2.getWorkingDate())).forEach(x -> {
			GeneralDate workingDate = x.getWorkingDate();
			
			DetailedDailyPerformanceReportData detailedDate = new DetailedDailyPerformanceReportData();
			detailedDate.setDate(workingDate);
			detailedDate.setDayOfWeek(String.valueOf(workingDate.dayOfWeek()));
			employeeData.lstDetailedPerformance.add(detailedDate);
			
			// ドメインモデル「社員の日別実績エラー一覧」を取得する
			List<EmployeeDailyPerError> errorList = errorAlarmRepository.find(employeeId, workingDate);
			List<String> lstRemarkContentStr = new ArrayList<>();
			if (query.getCondition().getErrorAlarmCode().isPresent()) {
				// Get list remark check box from screen C (UI)
				List<PrintRemarksContent> lstRemarkContent = outSche.getLstRemarkContent();
				lstRemarkContent.stream().filter(remark -> remark.isUsedClassification()).forEach(remark -> {
					// Get possible content based on remark choice
					Optional<PrintRemarksContent> optContent = getRemarkContent(employeeId, workingDate, errorList, remark.getPrintItem(), lstErAlCode);
					if (optContent.isPresent()) {
						// Add to remark
						lstRemarkContentStr.add(TextResource.localize(optContent.get().getPrintItem().shortName));
					}
				});
				
				detailedDate.errorDetail = String.join("、", lstRemarkContentStr);
			}
			
			// ER/AL
			if (query.getCondition().getConditionSetting() == OutputConditionSetting.USE_CONDITION) {
				boolean erMark = false, alMark = false;
				List<String> lstErrorCode = errorList.stream().map(error -> error.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList());
				// ドメインモデル「勤務実績のエラーアラーム」を取得する
				List<ErrorAlarmWorkRecord> lstErrorRecord = errorAlarmWorkRecordRepo.getListErAlByListCode(companyId, lstErrorCode);
				
				for (ErrorAlarmWorkRecord error : lstErrorRecord) {
					// コードから区分を取得する
					switch (error.getTypeAtr()) {
					case ALARM: // 区分　＝　エラー　のとき　AL
						alMark = true; 
						break;
					case ERROR: // 区分　=　アラーム　のとき　ER
						erMark = true;
						break;
					case OTHER:
						break;
					}
					if (erMark && alMark) break;
				}
				if (erMark && alMark) {
					detailedDate.errorAlarmMark = WorkScheOutputConstants.ER_AL;
				}
				else if (erMark) {
					detailedDate.errorAlarmMark = WorkScheOutputConstants.ER;
				}
				else if (alMark) {
					detailedDate.errorAlarmMark = WorkScheOutputConstants.AL;
				}
			}
			
			detailedDate.actualValue = new ArrayList<>();
			lstDisplayItem.stream().forEach(item -> {
				Optional<AttendanceItemValueImport> optItemValue = x.getAttendanceItems().stream().filter(att -> att.getItemId() == item.getAttendanceDisplay()).findFirst();
				if (optItemValue.isPresent()) {
					AttendanceItemValueImport itemValue = optItemValue.get();
					if (itemValue.getValueType() == ActualValue.STRING && itemValue.getValue() != null) {
						int attendanceId = itemValue.getItemId();
						switch (attendanceId) {
						case 1:
						case 28: // Work type
							Optional<WorkType> optWorkType = lstWorkType.stream().filter(type -> type.getWorkTypeCode().v().equalsIgnoreCase(itemValue.getValue())).findFirst();
							optWorkType.ifPresent(workType -> {
								if (outSche.getWorkTypeNameDisplay() == NameWorkTypeOrHourZone.OFFICIAL_NAME)
									itemValue.setValue(workType.getName().v());
								else
									itemValue.setValue(workType.getAbbreviationName().v());
							});
							break;
						case 2:
						case 29: // Work time
							Optional<WorkTimeSetting> optWorkTime = lstWorkTime.stream().filter(type -> type.getWorktimeCode().v().equalsIgnoreCase(itemValue.getValue())).findFirst();
							optWorkTime.ifPresent(workTime -> {
								if (outSche.getWorkTypeNameDisplay() == NameWorkTypeOrHourZone.OFFICIAL_NAME)
									itemValue.setValue(workTime.getWorkTimeDisplayName().getWorkTimeName().v());
								else
									itemValue.setValue(workTime.getWorkTimeDisplayName().getWorkTimeAbName().v());
							});
							break;
						default:
							break;
						}
					}
					detailedDate.actualValue.add(new ActualValue(itemValue.getItemId(), itemValue.getValue(), itemValue.getValueType()));
				}
				else {
					detailedDate.actualValue.add(new ActualValue(0, "", ActualValue.STRING));
				}
			});
		});
		return employeeData;
	}
	
	/**
	 * Calculate total (export by employee).
	 *
	 * @param workplaceData the workplace data
	 * @param lstAttendanceId the lst attendance id
	 */
	public void calculateTotalExportByEmployee(WorkplaceReportData workplaceData, List<AttendanceItemsDisplay> lstAttendanceId) {
		// Recursive
		workplaceData.getLstChildWorkplaceReportData().values().forEach(x -> {
			calculateTotalExportByEmployee(x, lstAttendanceId);
		});
		// Workplace
		workplaceData.workplaceTotal = new WorkplaceTotal();
		workplaceData.workplaceTotal.setWorkplaceId(workplaceData.getWorkplaceCode());
		List<TotalValue> lstTotalValue = new ArrayList<>();
		workplaceData.workplaceTotal.setTotalWorkplaceValue(lstTotalValue);
		
		// Calculate total of child workplaces
		final List<TotalValue> lstWorkplaceGrossTotal = lstAttendanceId.stream().map(item -> {
			TotalValue totalVal = new TotalValue();
			totalVal.setAttendanceId(item.getAttendanceDisplay());
			totalVal.setValue("0");
			totalVal.setValueType(TotalValue.STRING);
			return totalVal;
		}).collect(Collectors.toList());
		workplaceData.setGrossTotal(lstWorkplaceGrossTotal);
		workplaceData.getLstChildWorkplaceReportData().values().forEach(child -> {
			child.getGrossTotal().stream().forEach(val -> {
				int attendanceId = val.getAttendanceId();
				
				switch (val.getValueType()) {
				case ActualValue.INTEGER:
				case ActualValue.BIG_DECIMAL:
					int currentValue = (int) val.value();
					TotalValue totalVal = lstWorkplaceGrossTotal.stream().filter(attendance -> attendance.getAttendanceId() == attendanceId).findFirst().get();
					totalVal.setValue(String.valueOf(Integer.parseInt(totalVal.getValue()) + currentValue));
					totalVal.setValueType(val.getValueType());
					break;
				case ActualValue.DATE: // Unknown calculate method
					break;
				case ActualValue.STRING: // Not calculated
					break;
				}
			});
		});
		
		// Calculate total of employees of current level (after calculating total of child workplace)
		List<EmployeeReportData> lstReportData = workplaceData.getLstEmployeeReportData();
		if (lstReportData != null && lstReportData.size() > 0) {
			workplaceData.getLstEmployeeReportData().stream().forEach(employeeData -> {
				// Put attendanceId into map
				lstAttendanceId.stream().forEach(attendanceId -> {
					int attendanceDisplay = attendanceId.getAttendanceDisplay();
					if (!employeeData.mapPersonalTotal.containsKey(attendanceDisplay)) {
						employeeData.mapPersonalTotal.put(attendanceDisplay, new TotalValue(attendanceDisplay, "0", TotalValue.STRING));
						TotalValue totalVal = new TotalValue();
						totalVal.setAttendanceId(attendanceDisplay);
						totalVal.setValue("0");
						totalVal.setValueType(TotalValue.STRING);
						Optional<TotalValue> optWorkplaceTotalValue = lstTotalValue.stream().filter(x -> x.getAttendanceId() == attendanceDisplay).findFirst();
						if (!optWorkplaceTotalValue.isPresent()) {
							lstTotalValue.add(totalVal);
						}
					}
				});
				
				// Add into total by attendanceId
				employeeData.getLstDetailedPerformance().stream().forEach(detail -> {
					detail.actualValue.stream().forEach((aVal) -> {
						int attdId = aVal.getAttendanceId();
						int valueType = aVal.getValueType();
						
						switch (valueType) {
						case ActualValue.INTEGER:
						case ActualValue.BIG_DECIMAL:
							int currentValue = (int) aVal.value();
							TotalValue personalTotal = employeeData.mapPersonalTotal.get(attdId);
							personalTotal.setValue(String.valueOf(Integer.parseInt(personalTotal.getValue()) + (int) currentValue));
							employeeData.mapPersonalTotal.put(attdId, personalTotal);
							TotalValue totalVal = lstTotalValue.stream().filter(attendance -> attendance.getAttendanceId() == attdId).findFirst().get();
							totalVal.setValue(String.valueOf(Integer.parseInt(totalVal.getValue()) + currentValue));
							TotalValue totalGrossVal = lstWorkplaceGrossTotal.stream().filter(attendance -> attendance.getAttendanceId() == attdId).findFirst().get();
							totalGrossVal.setValue(String.valueOf(Integer.parseInt(totalGrossVal.getValue()) + currentValue));
							
							// Change value type
							personalTotal.setValueType(valueType);
							totalVal.setValueType(valueType);
							totalGrossVal.setValueType(valueType);
							break;
						case ActualValue.DATE: // Unknown calculate method
							break;
						case ActualValue.STRING: // Not calculated
							break;
						}
					});
				});
			});
		}
		
		// Calculate pure gross total of root workplace
		if (workplaceData.level == 0) {
			List<TotalValue> lstTotalVal = new ArrayList<>();
			workplaceData.setGrossTotal(lstTotalVal);
			
			// Sum of all workplace total and workplace hierarchy total
			workplaceData.lstChildWorkplaceReportData.forEach((k,child) -> {
				child.getGrossTotal().forEach(item -> {
					Optional<TotalValue> optTotalVal = lstTotalVal.stream().filter(x -> x.getAttendanceId() == item.getAttendanceId()).findFirst();
					if (optTotalVal.isPresent()) {
						TotalValue totalVal = optTotalVal.get();
						int valueType = totalVal.getValueType();
						switch (valueType) {
						case ActualValue.INTEGER:
						case ActualValue.BIG_DECIMAL:
							totalVal.setValue(totalVal.getValue() + item.getValue());
							break;
						case ActualValue.DATE: // Unknown calculate method
							break;
						case ActualValue.STRING: // Not calculated
							break;
						}
					}
					else {
						TotalValue totalVal = new TotalValue();
						totalVal.setAttendanceId(item.getAttendanceId());
						totalVal.setValue(item.getValue());
						totalVal.setValueType(item.getValueType());
						lstTotalVal.add(totalVal);
					}
				});
			});
		}
	}
	
	/**
	 * Calculate total (export by date).
	 *
	 * @param workplaceData the workplace data
	 */
	public void calculateTotalExportByDate(DailyWorkplaceData workplaceData) {
		// Recursive
		workplaceData.getLstChildWorkplaceData().values().forEach(x -> {
			calculateTotalExportByDate(x);
		});
		// Workplace
		workplaceData.workplaceTotal = new WorkplaceTotal();
		workplaceData.workplaceTotal.setWorkplaceId(workplaceData.getWorkplaceCode());
		List<TotalValue> lstTotalValue = new ArrayList<>();
		workplaceData.workplaceTotal.setTotalWorkplaceValue(lstTotalValue);
		workplaceData.workplaceHierarchyTotal = new WorkplaceTotal();
		workplaceData.workplaceHierarchyTotal.setWorkplaceId(workplaceData.getWorkplaceCode());
		List<TotalValue> lstTotalHierarchyValue = new ArrayList<>();
		workplaceData.workplaceHierarchyTotal.setTotalWorkplaceValue(lstTotalHierarchyValue);
		
		List<DailyPersonalPerformanceData> performanceData = workplaceData.getLstDailyPersonalData();
		
		// Employee
		if (performanceData != null && performanceData.size() > 0) {
			for (DailyPersonalPerformanceData data: performanceData) {
				List<ActualValue> lstActualValue = data.getActualValue();
				if (lstActualValue == null) continue;
				lstActualValue.forEach(actualValue -> {
					int valueType = actualValue.getValueType();
					Optional<TotalValue> optTotalVal = lstTotalValue.stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
					TotalValue totalValue;
					if (optTotalVal.isPresent()) {
						totalValue = optTotalVal.get();
						if ((totalValue.getValueType() == TotalValue.INTEGER || totalValue.getValueType() == TotalValue.BIG_DECIMAL) && actualValue.getValue() != null) {
							totalValue.setValue(String.valueOf(Integer.parseInt(totalValue.getValue()) + Integer.parseInt(actualValue.getValue())));
						}
					}
					else {
						totalValue = new TotalValue();
						totalValue.setAttendanceId(actualValue.getAttendanceId());
						if (valueType == TotalValue.INTEGER || valueType == TotalValue.BIG_DECIMAL) {
							if (actualValue.getValue() != null) {
								totalValue.setValue(actualValue.getValue());
							} else {
								totalValue.setValue("0");
							}
						}
						totalValue.setValueType(valueType);
						lstTotalValue.add(totalValue);
					}
					
					Optional<TotalValue> optTotalWorkplaceVal = lstTotalHierarchyValue.stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
					TotalValue totalWorkplaceValue;
					if (optTotalWorkplaceVal.isPresent()) {
						totalWorkplaceValue = optTotalWorkplaceVal.get();
						if ((totalWorkplaceValue.getValueType() == TotalValue.INTEGER || totalWorkplaceValue.getValueType() == TotalValue.BIG_DECIMAL) && actualValue.getValue() != null) {
							totalWorkplaceValue.setValue(String.valueOf(Integer.parseInt(totalWorkplaceValue.getValue()) + Integer.parseInt(actualValue.getValue())));
						}
					}
					else {
						totalWorkplaceValue = new TotalValue();
						totalWorkplaceValue.setAttendanceId(actualValue.getAttendanceId());
						if (valueType == TotalValue.INTEGER || valueType == TotalValue.BIG_DECIMAL) {
							if (actualValue.getValue() != null) {
								totalWorkplaceValue.setValue(actualValue.getValue());
							} else {
								totalWorkplaceValue.setValue("0");
							}
						}
						totalWorkplaceValue.setValueType(valueType);
						lstTotalHierarchyValue.add(totalWorkplaceValue);
					}
				});
			}
		}
		
		// Workplace
		Map<String, DailyWorkplaceData> lstReportData = workplaceData.getLstChildWorkplaceData();
		lstReportData.values().forEach((value) -> {
			List<TotalValue> lstActualValue = value.getWorkplaceTotal().getTotalWorkplaceValue();
			lstActualValue.forEach(actualValue -> {
				if (actualValue == null) return;
				int valueType = actualValue.getValueType();
				Optional<TotalValue> optTotalVal = lstTotalValue.stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
				TotalValue totalValue;
				if (optTotalVal.isPresent()) {
					totalValue = optTotalVal.get();
					if ((totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL) && actualValue.getValue() != null) {
						totalValue.setValue(String.valueOf(Integer.parseInt(totalValue.getValue()) + Integer.parseInt(actualValue.getValue())));
					}
				}
				else {
					totalValue = new TotalValue();
					totalValue.setAttendanceId(actualValue.getAttendanceId());
					if (valueType == ActualValue.INTEGER || valueType == ActualValue.BIG_DECIMAL) {
						if (actualValue.getValue() != null) {
							totalValue.setValue(actualValue.getValue());
						} else {
							totalValue.setValue("0");
						}
					}
					totalValue.setValueType(valueType);
					lstTotalValue.add(totalValue);
				}
			});
		});
	}
	
	/**
	 * Calculate gross total by date.
	 *
	 * @param dailyReportData the daily report data
	 */
	public void calculateGrossTotalByDate(DailyReportData dailyReportData) {
		List<WorkplaceDailyReportData> lstWorkplaceDailyReportData = dailyReportData.getLstDailyReportData();
		List<TotalValue> lstGrossTotal = new ArrayList<>();
		dailyReportData.setListTotalValue(lstGrossTotal);
		
		lstWorkplaceDailyReportData.stream().forEach(workplaceDaily -> {
			WorkplaceTotal workplaceTotal = workplaceDaily.getLstWorkplaceData().getWorkplaceTotal();
			List<TotalValue> lstTotalVal = workplaceTotal.getTotalWorkplaceValue();
			lstTotalVal.stream().forEach(totalVal -> {
				// Literally 0-1 result in list
				Optional<TotalValue> optGrossTotal = lstGrossTotal.stream().filter(x -> x.getAttendanceId() == totalVal.getAttendanceId()).findFirst();
				TotalValue totalValue;
				if (optGrossTotal.isPresent()) {
					totalValue = optGrossTotal.get();
					if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL) {
						totalValue.setValue(String.valueOf(Integer.parseInt(totalValue.getValue()) + Integer.parseInt(totalVal.getValue())));
					}
				}
				else {
					totalValue = new TotalValue();
					totalValue.setAttendanceId(totalVal.getAttendanceId());
					if (totalVal.getValueType() == ActualValue.INTEGER || totalVal.getValueType() == ActualValue.BIG_DECIMAL) {
						totalValue.setValue(totalVal.getValue());
					}
					totalValue.setValueType(totalVal.getValueType());
					lstGrossTotal.add(totalValue);
				}
			});
		});
	}
	
	/**
	 * Find workplace (export by employee).
	 *
	 * @param employeeId the employee id
	 * @param rootWorkplace the root workplace
	 * @param baseDate the base date
	 * @return the workplace report data
	 */
	public WorkplaceReportData findWorkplace(String employeeId, WorkplaceReportData rootWorkplace, GeneralDate baseDate,
			List<WkpHistImport> lstWorkplaceHistImport, List<WorkplaceConfigInfo> lstWorkplaceConfigInfo) {
		Map<String, WorkplaceReportData> mapWorkplaceInfo = rootWorkplace.getLstChildWorkplaceReportData();
		WkpHistImport workplaceImport = lstWorkplaceHistImport.stream().filter(hist -> StringUtils.equalsIgnoreCase(hist.getEmployeeId(), employeeId) ).findFirst().get();
		WorkplaceHierarchy code = lstWorkplaceConfigInfo.stream().filter(x -> StringUtils.equalsIgnoreCase(x.getLstWkpHierarchy().get(0).getWorkplaceId(), workplaceImport.getWorkplaceId())).findFirst().get().getLstWkpHierarchy().get(0);
		HierarchyCode hierarchyCode = code.getHierarchyCode();
		if (mapWorkplaceInfo.containsKey(hierarchyCode.v())) {
			return mapWorkplaceInfo.get(hierarchyCode.v());
		}
		else {
			for (Map.Entry<String, WorkplaceReportData> keySet : mapWorkplaceInfo.entrySet()) {
				WorkplaceReportData data = findWorkplace(employeeId, keySet.getValue(), baseDate, lstWorkplaceHistImport, lstWorkplaceConfigInfo);
				if (data != null)
					return data;
			}
		}
		return null;
	}
	
	/**
	 * Find workplace (export by date).
	 *
	 * @param employeeId the employee id
	 * @param rootWorkplace the root workplace
	 * @param baseDate the base date
	 * @return the workplace report data
	 */
	public DailyWorkplaceData findWorkplace(String employeeId, DailyWorkplaceData rootWorkplace, GeneralDate baseDate, 
			List<WkpHistImport> lstWkpHistImport, List<WorkplaceConfigInfo> lstWorkplaceConfigInfo) {
		
		Map<String, DailyWorkplaceData> mapWorkplaceInfo = rootWorkplace.getLstChildWorkplaceData();
		WkpHistImport workplaceImport = lstWkpHistImport.stream().filter(hist -> StringUtils.equalsIgnoreCase(hist.getEmployeeId(), employeeId) ).findFirst().get();
		WorkplaceHierarchy code = lstWorkplaceConfigInfo.stream().filter(x -> StringUtils.equalsIgnoreCase(x.getLstWkpHierarchy().get(0).getWorkplaceId(), workplaceImport.getWorkplaceId())).findFirst().get().getLstWkpHierarchy().get(0);
		HierarchyCode hierarchyCode = code.getHierarchyCode();
		if (mapWorkplaceInfo.containsKey(hierarchyCode.v())) {
			return mapWorkplaceInfo.get(hierarchyCode.v());
		}
		else {
			for (Map.Entry<String, DailyWorkplaceData> keySet : mapWorkplaceInfo.entrySet()) {
				DailyWorkplaceData data = findWorkplace(employeeId, keySet.getValue(), baseDate, lstWkpHistImport, lstWorkplaceConfigInfo);
				if (data != null)
					return data;
			}
		}
		return null;
	}
	
	/**
	 * Collect workplace hierarchy.
	 *
	 * @param workplaceId the workplace id
	 * @param baseDate the base date
	 * @return the map
	 */
	public Map<String, WorkplaceInfo> collectWorkplaceHierarchy(String workplaceId, GeneralDate baseDate, List<WorkplaceConfigInfo> lstWorkplaceConfigInfo) {
		Map<String, WorkplaceInfo> lstWorkplace = new TreeMap<>();
		Optional<WorkplaceConfigInfo> optHierachyInfo = lstWorkplaceConfigInfo.stream().filter(x -> StringUtils.equalsIgnoreCase(x.getLstWkpHierarchy().get(0).getWorkplaceId(), workplaceId)).findFirst();
		optHierachyInfo.ifPresent(info -> {
			List<WorkplaceHierarchy> lstHierarchy = info.getLstWkpHierarchy();
			for (WorkplaceHierarchy hierarchy: lstHierarchy) {
				WorkplaceInfo workplace = workplaceInfoRepository.findByWkpId(hierarchy.getWorkplaceId(), baseDate).get();
				lstWorkplace.put(hierarchy.getHierarchyCode().v(), workplace);
				
				// Recursive
				if (!StringUtils.equals(workplace.getWorkplaceId(), workplaceId)) {
					collectWorkplaceHierarchy(workplace.getWorkplaceId(), baseDate, lstWorkplaceConfigInfo);
				}
			}
		});
		return lstWorkplace;
	}
	
	/**
	 * Analyze info (export by employee).
	 *
	 * @param lstWorkplace the lst workplace
	 * @param parent the parent
	 * @param lstAddedWorkplace the lst added workplace
	 */
	private void analyzeInfoExportByEmployee(Map<String, WorkplaceInfo> lstWorkplace, WorkplaceReportData parent) {
		boolean isFoundParent;
		// Add workplace into respective parent
		for (Map.Entry<String, WorkplaceInfo> entry : lstWorkplace.entrySet()) {
			String k = entry.getKey();
			WorkplaceInfo wpi = entry.getValue();
			
			isFoundParent = false;
			
			// Init an workplace report data
			WorkplaceReportData wrp = new WorkplaceReportData();
			wrp.workplaceCode = wpi.getWorkplaceCode().v();
			wrp.workplaceName = wpi.getWorkplaceName().v();
			wrp.lstEmployeeReportData = new ArrayList<>();
			wrp.level = k.length() / 3;
			
			// Break down code
			String breakDownCode;
			for (int level = k.length(); level >= 3; level -= 3) {
				breakDownCode = k.substring(0, level);
				
				// Find workplace based on breakdown code
				WorkplaceReportData targetWorkplace = findWorkplaceByHierarchyCodeExportEmployee(parent, breakDownCode);
				
				// Find parent workplace, add into its child
				if (targetWorkplace != null) {
					wrp.parent = targetWorkplace;
					targetWorkplace.lstChildWorkplaceReportData.put(k, wrp);
					isFoundParent = true;
					break;
				}
			}

			// Not found, then add to root
			if (!isFoundParent) {
				wrp.parent = parent;
				parent.lstChildWorkplaceReportData.put(k, wrp);
			}
		}
	}
	
	/**
	 * Find workplace by hierarchy code.
	 *
	 * @param rootWorkplace the root workplace
	 * @param hierarchyCode the hierarchy code
	 * @return the workplace report data
	 */
	private WorkplaceReportData findWorkplaceByHierarchyCodeExportEmployee(WorkplaceReportData rootWorkplace, String hierarchyCode) {
		WorkplaceReportData workplaceReportData = null;
		
		for (Map.Entry<String, WorkplaceReportData> entry : rootWorkplace.lstChildWorkplaceReportData.entrySet()) {
			if (workplaceReportData != null) {
				break;
			}
			String key = entry.getKey();
			WorkplaceReportData childWorkplace = entry.getValue();
			if (StringUtils.equalsIgnoreCase(key, hierarchyCode)) {
				workplaceReportData = childWorkplace;
				break;
			}
			workplaceReportData = findWorkplaceByHierarchyCodeExportEmployee(childWorkplace, hierarchyCode);
		}
		
		return workplaceReportData;
	}
	
	
	/**
	 * Analyze info (export by date).
	 *
	 * @param lstWorkplace the lst workplace
	 * @param parent the parent
	 * @param lstAddedWorkplace the lst added workplace
	 */
	private void analyzeInfoExportByDate(Map<String, WorkplaceInfo> lstWorkplace, DailyWorkplaceData parent, List<String> lstAddedWorkplace) {
		parent.lstChildWorkplaceData = new TreeMap<>();
		
		boolean isFoundParent;
		// Add workplace into respective parent
		for (Map.Entry<String, WorkplaceInfo> entry : lstWorkplace.entrySet()) {
			String k = entry.getKey();
			WorkplaceInfo wpi = entry.getValue();
			
			isFoundParent = false;
			
			// Init an workplace report data
			DailyWorkplaceData wrp = new DailyWorkplaceData();
			wrp.workplaceCode = wpi.getWorkplaceCode().v();
			wrp.workplaceName = wpi.getWorkplaceName().v();
			wrp.level = k.length() / 3;
			
			// Break down code
			String breakDownCode;
			for (int level = k.length(); level >= 3; level -= 3) {
				breakDownCode = k.substring(0, level);
				
				// Find workplace based on breakdown code
				DailyWorkplaceData targetWorkplace = findWorkplaceByHierarchyCodeExportDate(parent, breakDownCode);
				
				// Find parent workplace, add into its child
				if (targetWorkplace != null) {
					wrp.parent = targetWorkplace;
					targetWorkplace.lstChildWorkplaceData.put(k, wrp);
					isFoundParent = true;
					break;
				}
			}

			// Not found, then add to root
			if (!isFoundParent) {
				wrp.parent = parent;
				parent.lstChildWorkplaceData.put(k, wrp);
			}
		}
	}
	
	private DailyWorkplaceData findWorkplaceByHierarchyCodeExportDate(DailyWorkplaceData rootWorkplace, String hierarchyCode) {
		DailyWorkplaceData workplaceReportData = null;
		
		for (Map.Entry<String, DailyWorkplaceData> entry : rootWorkplace.lstChildWorkplaceData.entrySet()) {
			if (workplaceReportData != null) {
				break;
			}
			String key = entry.getKey();
			DailyWorkplaceData childWorkplace = entry.getValue();
			if (StringUtils.equalsIgnoreCase(key, hierarchyCode)) {
				workplaceReportData = childWorkplace;
				break;
			}
			workplaceReportData = findWorkplaceByHierarchyCodeExportDate(childWorkplace, hierarchyCode);
		}
		
		return workplaceReportData;
	}
	
	/**
	 * Collect header data.
	 *
	 * @param headerData the header data
	 * @param condition the condition
	 */
	public void collectHeaderData(DailyPerformanceHeaderData headerData, WorkScheduleOutputCondition condition) {
		Optional<Company> optCompany = companyRepository.find(AppContexts.user().companyId());
		if (optCompany.isPresent())
			headerData.companyName = optCompany.get().getCompanyName().v();
		else
			headerData.companyName = "";
		headerData.setFixedHeaderData(new ArrayList<>());
		headerData.fixedHeaderData.add(WorkScheOutputConstants.ERAL);
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			headerData.fixedHeaderData.add(WorkScheOutputConstants.DATE);
			headerData.fixedHeaderData.add(WorkScheOutputConstants.DAYMONTH);
			headerData.fixedHeaderData.add(WorkScheOutputConstants.DAY);
		} else {
			headerData.fixedHeaderData.add(WorkScheOutputConstants.PERSONAL_NAME);
		}
		headerData.fixedHeaderData.add(WorkScheOutputConstants.REMARK);
	}
	
	/**
	 * Collect display map.
	 *
	 * @param headerData the header data
	 * @param condition the condition
	 */
	public void collectDisplayMap(DailyPerformanceHeaderData headerData, OutputItemDailyWorkSchedule condition) {
		List<AttendanceItemsDisplay> lstItem = condition.getLstDisplayedAttendance();
		headerData.setLstOutputItemSettingCode(new ArrayList<>());
		
		List<Integer> lstAttendanceId = lstItem.stream().sorted((o1, o2) -> (o1.getOrderNo() - o2.getOrderNo())).map(x -> x.getAttendanceDisplay()).collect(Collectors.toList());
		List<DailyAttendanceItem> lstDailyAttendanceItem = attendanceNameService.getNameOfDailyAttendanceItem(lstAttendanceId);
		
		lstAttendanceId.stream().forEach(x -> {
			DailyAttendanceItem attendanceItem = lstDailyAttendanceItem.stream().filter(item -> item.getAttendanceItemId() == x).findFirst().get();
			OutputItemSetting setting = new OutputItemSetting();
			setting.setItemCode(attendanceItem.getAttendanceItemDisplayNumber());
			setting.setItemName(attendanceItem.getAttendanceItemName());
			headerData.lstOutputItemSettingCode.add(setting);
		});
	}
	
	/**
	 * Write header data.
	 *
	 * @param query the query
	 * @param sheet the sheet
	 * @param reportData the report data
	 * @param dateRow the date row
	 */
	public void writeHeaderData(WorkScheduleOutputQuery query, Worksheet sheet, DailyPerformanceReportData reportData, int dateRow) {
		PageSetup pageSetup = sheet.getPageSetup();
		pageSetup.setHeader(0, "&8 " + reportData.getHeaderData().companyName);

		// Set header date
		DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
		pageSetup.setHeader(2, "&8 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");
		
		Cells cells = sheet.getCells();
		Cell periodCell = cells.get(dateRow,0);
		
		DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyy/M/d (E)", Locale.JAPAN);
		String periodStr = WorkScheOutputConstants.PERIOD + " " + query.getStartDate().toLocalDate().format(jpFormatter) + " ～ " + query.getEndDate().toLocalDate().format(jpFormatter);
		periodCell.setValue(periodStr);
	}
	
	/**
	 * Copy footer.
	 *
	 * @param sheet the sheet
	 * @param templateSheet the template sheet
	 */
	public void copyFooter(Worksheet sheet, Worksheet templateSheet) {
		PageSetup sheetPageSetup = sheet.getPageSetup();
		PageSetup templateSheetSetup = templateSheet.getPageSetup();
		String footer = templateSheetSetup.getFooter(0);
		sheetPageSetup.setFooter(0, footer);
	}
	
	/**
	 * Set all fixed text (header, footer..., all fixed resource text).
	 *
	 * @param condition the condition
	 * @param sheet the sheet
	 * @param reportData the report data
	 * @param currentRow the current row
	 */
	public void setFixedData(WorkScheduleOutputCondition condition, Worksheet sheet, DailyPerformanceReportData reportData, int currentRow) {
		DailyPerformanceHeaderData headerData = reportData.getHeaderData();
		// Set fixed value
		Cells cells = sheet.getCells();
		
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			// A2_1
			Cell erAlCell = cells.get(currentRow, 0);
			erAlCell.setValue(headerData.getFixedHeaderData().get(0));
			
			// A2_2
			Cell dateCell = cells.get(currentRow, 1);
			dateCell.setValue(headerData.getFixedHeaderData().get(1));
			
			// A2_3
			Cell dayMonCell = cells.get(currentRow + 1, 1);
			dayMonCell.setValue(headerData.getFixedHeaderData().get(2));
			
			// A2_4
			Cell dayCell = cells.get(currentRow + 1, 2);
			dayCell.setValue(headerData.getFixedHeaderData().get(3));
			
			// A2_6
			Cell remarkCell = cells.get(currentRow, 35);
			remarkCell.setValue(headerData.getFixedHeaderData().get(4));
		}
		else {
			// B2_1
			Cell erAlCell = cells.get(currentRow, 0);
			erAlCell.setValue(headerData.getFixedHeaderData().get(0));
			
			// B2_2
			Cell dateCell = cells.get(currentRow, 1);
			dateCell.setValue(headerData.getFixedHeaderData().get(1));
			
			// B2_4
			Cell remarkCell = cells.get(currentRow, 35);
			remarkCell.setValue(headerData.getFixedHeaderData().get(2));
		}
		
	}
	
	/**
	 * Write display map (48 cells max).
	 *
	 * @param cells cell map
	 * @param reportData the report data
	 * @param currentRow the current row
	 * @param nSize the n size
	 */
	public void writeDisplayMap(Cells cells, DailyPerformanceReportData reportData, int currentRow, int nSize) {
		List<OutputItemSetting> lstItem = reportData.getHeaderData().getLstOutputItemSettingCode();
		
		// Divide list into smaller lists (max 16 items)
		int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);

		int start, length;
		List<OutputItemSetting> lstItemRow;
		
        for(int i = 0; i < numOfChunks; i++) {
            start = i * CHUNK_SIZE; 
            length = Math.min(lstItem.size() - start, CHUNK_SIZE);

            lstItemRow = lstItem.subList(start, start + length);
            
            OutputItemSetting outputItem;
            
            for (int j = 0; j < length; j++) {
            	outputItem = lstItemRow.get(j);
            	// Column 4, 6, 8,...
            	// Row 3, 4, 5
            	Cell cell = cells.get(currentRow + i*2, DATA_COLUMN_INDEX[0] + j * 2); 
            	cell.setValue(outputItem.getItemName());
            	
            	cell = cells.get(currentRow + i*2 + 1, DATA_COLUMN_INDEX[0] + j * 2); 
            	cell.setValue(outputItem.getItemCode());
            }
        }
	}
	
	/**
	 * Write detail work schedule.
	 *
	 * @param currentRow the current row
	 * @param templateSheetCollection the template sheet collection
	 * @param sheet the sheet
	 * @param workplaceReportData the workplace report data
	 * @param dataRowCount the data row count
	 * @param condition the condition
	 * @return the int
	 * @throws Exception the exception
	 */
	public int writeDetailedWorkSchedule(int currentRow, WorksheetCollection templateSheetCollection, Worksheet sheet, WorkplaceReportData workplaceReportData, 
			int dataRowCount, WorkScheduleOutputCondition condition, RowPageTracker rowPageTracker) throws Exception {
		Cells cells = sheet.getCells();
		
		WorkScheduleSettingTotalOutput totalOutput = condition.getSettingDetailTotalOutput();
		List<EmployeeReportData> lstEmployeeReportData = workplaceReportData.getLstEmployeeReportData();
		
		// Root workplace won't have employee
		if (lstEmployeeReportData != null && lstEmployeeReportData.size() > 0) {
			Iterator<EmployeeReportData> iteratorEmployee = lstEmployeeReportData.iterator();
			while (iteratorEmployee.hasNext()) {
				EmployeeReportData employeeReportData = iteratorEmployee.next();
				
				Range workplaceRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WORKPLACE_ROW);
				Range workplaceRange = cells.createRange(currentRow, 0, 1, 39);
				workplaceRange.copy(workplaceRangeTemp);
				rowPageTracker.useOneRowAndCheckResetRemainingRow();
				
				// A3_1
				Cell workplaceTagCell = cells.get(currentRow, 0);
				workplaceTagCell.setValue(WorkScheOutputConstants.WORKPLACE);
				
				// A3_2
				Cell workplaceInfo = cells.get(currentRow, DATA_COLUMN_INDEX[0]);
				workplaceInfo.setValue(workplaceReportData.getWorkplaceCode() + " " + workplaceReportData.getWorkplaceName());
				
				currentRow++;
				
				Range employeeRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_EMPLOYEE_ROW);
				Range employeeRange = cells.createRange(currentRow, 0, 1, 39);
				employeeRange.copy(employeeRangeTemp);
				rowPageTracker.useOneRowAndCheckResetRemainingRow();
				
				// A4_1
				Cell employeeTagCell = cells.get(currentRow, 0);
				employeeTagCell.setValue(WorkScheOutputConstants.EMPLOYEE);
				
				// A4_2
				Cell employeeCell = cells.get(currentRow, 3);
				employeeCell.setValue(employeeReportData.employeeCode + " " + employeeReportData.employeeName);
				
				// A4_3
				Cell employmentTagCell = cells.get(currentRow, 9);
				employmentTagCell.setValue(WorkScheOutputConstants.EMPLOYMENT);
				
				// A4_5
				Cell employmentCell = cells.get(currentRow, 11);
				employmentCell.setValue(employeeReportData.employmentName);
				
				// A4_6
				Cell jobTitleTagCell = cells.get(currentRow, 15);
				jobTitleTagCell.setValue(WorkScheOutputConstants.POSITION);

				// A4_7
				Cell jobTitleCell = cells.get(currentRow, 17);
				jobTitleCell.setValue(employeeReportData.position);
				
				currentRow++;
				boolean colorWhite = true; // true = white, false = light blue, start with white row
				
				// Detail personal performance
				if (totalOutput.isDetails()) {
					List<DetailedDailyPerformanceReportData> lstDetailedDailyPerformance = employeeReportData.getLstDetailedPerformance();
					for (DetailedDailyPerformanceReportData detailedDailyPerformanceReportData: lstDetailedDailyPerformance) {
						Range dateRangeTemp;
						
						if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
							sheet.getHorizontalPageBreaks().add(currentRow);
							rowPageTracker.resetRemainingRow();
						}
						if (colorWhite) // White row
							dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + dataRowCount);
						else // Light blue row
							dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + dataRowCount);
						Range dateRange = cells.createRange(currentRow, 0, dataRowCount, 39);
						dateRange.copy(dateRangeTemp);
						dateRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
						if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
							rowPageTracker.useRemainingRow(dataRowCount);
							rowPageTracker.resetRemainingRow();
						}
						else {
							rowPageTracker.useRemainingRow(dataRowCount);
						}
						
						// A5_1
						Cell erAlMark = cells.get(currentRow, 0);
						erAlMark.setValue(detailedDailyPerformanceReportData.getErrorAlarmMark());
						
						// A5_2
						Cell dateCell = cells.get(currentRow, 1);
						dateCell.setValue(detailedDailyPerformanceReportData.getDate().toString("MM/dd"));
						
						// A5_3
						DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("E", Locale.JAPAN);
						String jp = detailedDailyPerformanceReportData.getDate().toLocalDate().format(jpFormatter);
						Cell dayOfWeekCell = cells.get(currentRow, 2);
						dayOfWeekCell.setValue(jp);
						
						// A5_4
						List<ActualValue> lstItem = detailedDailyPerformanceReportData.getActualValue();
						
						// Divide list into smaller lists (max 16 items)
						int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);
	
						int curRow = currentRow;
						int start, length;
						List<ActualValue> lstItemRow;
						
				        for(int i = 0; i < numOfChunks; i++) {
				            start = i * CHUNK_SIZE;
				            length = Math.min(lstItem.size() - start, CHUNK_SIZE);
	
				            lstItemRow = lstItem.subList(start, start + length);
				            
				            for (int j = 0; j < length; j++) {
				            	// Column 4, 6, 8,...
				            	ActualValue actualValue = lstItemRow.get(j);
				            	Cell cell = cells.get(curRow, DATA_COLUMN_INDEX[0] + j * 2); 
				            	Style style = cell.getStyle();
				            	switch (actualValue.getValueType()) {
								case ActualValue.INTEGER:
									String value = actualValue.getValue();
									if (value != null)
										cell.setValue(getTimeAttr(value));
									else
										cell.setValue(getTimeAttr("0"));
									style.setHorizontalAlignment(TextAlignmentType.RIGHT);
									break;
								case ActualValue.BIG_DECIMAL:
								case ActualValue.DATE:
								case ActualValue.STRING:
									cell.setValue(actualValue.getValue());
									style.setHorizontalAlignment(TextAlignmentType.LEFT);
								}
				            	setFontStyle(style);
				            	cell.setStyle(style);
				            }
				            
				            curRow++;
				        }
				        
				        // A5_5
				        Cell remarkCell = cells.get(currentRow,35);
				        remarkCell.setValue(detailedDailyPerformanceReportData.getErrorDetail());
				        
				        currentRow += dataRowCount;
				        colorWhite = !colorWhite; // Change to other color
					}
				}
				
				// Personal total
				if (totalOutput.isPersonalTotal()) {
					if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
						sheet.getHorizontalPageBreaks().add(currentRow);
						rowPageTracker.resetRemainingRow();
					}
					
					Range personalTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
					Range personalTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
					personalTotalRange.copy(personalTotalRangeTemp);
					personalTotalRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
					if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
						rowPageTracker.useRemainingRow(dataRowCount);
						rowPageTracker.resetRemainingRow();
					}
					else {
						rowPageTracker.useRemainingRow(dataRowCount);
					}
					
					// A6_1
					Cell personalTotalCellTag = cells.get(currentRow, 0);
					personalTotalCellTag.setValue(WorkScheOutputConstants.PERSONAL_TOTAL);
					
					// A6_2
					Map<Integer, TotalValue> mapPersonalTotal = employeeReportData.getMapPersonalTotal();
					int numOfChunks = (int) Math.ceil( (double) mapPersonalTotal.size() / CHUNK_SIZE);
					int start, length;
					List<TotalValue> lstItemRow;
					
			        for(int i = 0; i < numOfChunks; i++) {
			            start = i * CHUNK_SIZE;
			            length = Math.min(mapPersonalTotal.size() - start, CHUNK_SIZE);
			            
			            lstItemRow = mapPersonalTotal.values().stream().collect(Collectors.toList()).subList(start, start + length);
			            int valueType;
			            
			            for (int j = 0; j < length; j++) {
			            	TotalValue totalValue = lstItemRow.get(j);
			            	String value = totalValue.getValue();
			            	valueType = totalValue.getValueType();

			            	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
			            	Style style = cell.getStyle();
			            	switch (valueType) {
							case ActualValue.INTEGER:
							case ActualValue.BIG_DECIMAL:
								if (value != null)
									cell.setValue(getTimeAttr(value));
								else
									cell.setValue(getTimeAttr("0"));
								style.setHorizontalAlignment(TextAlignmentType.RIGHT);
								break;
							case ActualValue.DATE:
							case ActualValue.STRING:
							}
			            	setFontStyle(style);
			            	cell.setStyle(style);
			            }
			            currentRow++;
			        }
				}
		        
				// Total count day
		        if (condition.getSettingDetailTotalOutput().isTotalNumberDay()) {
		        	// Use fixed 2 rows
		        	if (rowPageTracker.checkRemainingRowSufficient(2) < 0) {
						sheet.getHorizontalPageBreaks().add(currentRow);
						rowPageTracker.resetRemainingRow();
					}
			        
			        Range dayCountRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DAYCOUNT_ROW);
					Range dayCountRange = cells.createRange(currentRow, 0, 2, 39);
					dayCountRange.copy(dayCountRangeTemp);
					dayCountRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
					if (rowPageTracker.checkRemainingRowSufficient(2) == 0) {
						rowPageTracker.useRemainingRow(2);
						rowPageTracker.resetRemainingRow();
					}
					else {
						rowPageTracker.useRemainingRow(2);
					}
					
					// A7_1
					Cell dayCountTag = cells.get(currentRow, 0);
					dayCountTag.setValue(WorkScheOutputConstants.DAY_COUNT);
					
					// A7_2 -> A7_10
					for (int i = 0; i < WorkScheOutputConstants.DAY_COUNT_TITLES.length; i++) {
						Cell dayTypeTag = cells.get(currentRow, i*2 + 3);
						dayTypeTag.setValue(WorkScheOutputConstants.DAY_COUNT_TITLES[i]);
					}
					
					currentRow++;
				
					// A7_11 -> A7_19
					TotalCountDay totalCountDay = employeeReportData.getTotalCountDay();
					totalCountDay.initAllDayCount();
					
					for (int i = 0; i < WorkScheOutputConstants.DAY_COUNT_TITLES.length; i++) {
						Cell dayTypeTag = cells.get(currentRow, i*2 + 3);
						dayTypeTag.setValue(totalCountDay.getAllDayCount().get(i));
					}
					
					currentRow++;
		        }
		        
		        // Page break by employee
				if (condition.getPageBreakIndicator() == PageBreakIndicator.EMPLOYEE && iteratorEmployee.hasNext()) {
					rowPageTracker.resetRemainingRow();
					sheet.getHorizontalPageBreaks().add(currentRow);
				}
			}
		}
		
		// Skip writing total for root, use gross total instead
		if (lstEmployeeReportData != null && lstEmployeeReportData.size() > 0 && totalOutput.isWorkplaceTotal()) {
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
				rowPageTracker.resetRemainingRow();
				sheet.getHorizontalPageBreaks().add(currentRow);
			}
			
			Range workplaceTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range workplaceTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
				rowPageTracker.useRemainingRow(dataRowCount);
				rowPageTracker.resetRemainingRow();
			}
			else {
				rowPageTracker.useRemainingRow(dataRowCount);
			}
			
			workplaceTotalRange.copy(workplaceTotalRangeTemp);
			workplaceTotalRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			
			// A8_1
			Cell workplaceTotalCellTag = cells.get(currentRow, 0);
			workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_TOTAL);
			
			// A8_2
			WorkplaceTotal workplaceTotal = workplaceReportData.getWorkplaceTotal();
			int numOfChunks = (int) Math.ceil( (double) workplaceTotal.getTotalWorkplaceValue().size() / CHUNK_SIZE);
			int start, length;
			List<TotalValue> lstItemRow;
			
	        for(int i = 0; i < numOfChunks; i++) {
	            start = i * CHUNK_SIZE;
	            length = Math.min(workplaceTotal.getTotalWorkplaceValue().size() - start, CHUNK_SIZE);
	
	            lstItemRow = workplaceTotal.getTotalWorkplaceValue().subList(start, start + length);
	            
	            for (int j = 0; j < length; j++) {
	            	TotalValue totalValue = lstItemRow.get(j);
	            	String value = totalValue.getValue();
	            	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
	            	Style style = cell.getStyle();
	            	switch (totalValue.getValueType()) {
					case ActualValue.INTEGER:
						if (value != null)
							cell.setValue(getTimeAttr(value));
						else
							cell.setValue(getTimeAttr("0"));
						style.setHorizontalAlignment(TextAlignmentType.RIGHT);
						break;
					case ActualValue.BIG_DECIMAL:
					case ActualValue.DATE:
					case ActualValue.STRING:
					}
	            	setFontStyle(style);
	            	cell.setStyle(style);
	            }
	            currentRow++;
	        }
		}
		
		Map<String, WorkplaceReportData> mapChildWorkplace = workplaceReportData.getLstChildWorkplaceReportData();
		if (((condition.getPageBreakIndicator() == PageBreakIndicator.WORKPLACE || 
				condition.getPageBreakIndicator() == PageBreakIndicator.EMPLOYEE) &&
				mapChildWorkplace.size() > 0) && workplaceReportData.level != 0) {
			Range lastRowRange = cells.createRange(currentRow - 1, 0, 1, 39);
        	lastRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        	rowPageTracker.resetRemainingRow();
			sheet.getHorizontalPageBreaks().add(currentRow);
		}
		// Process to child workplace
		Iterator<Map.Entry<String, WorkplaceReportData>> it = mapChildWorkplace.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WorkplaceReportData> entry = it.next();
			currentRow = writeDetailedWorkSchedule(currentRow, templateSheetCollection, sheet, entry.getValue(), dataRowCount, condition, rowPageTracker);
			
			// Page break by workplace
			if (condition.getPageBreakIndicator() == PageBreakIndicator.WORKPLACE && it.hasNext()) {
				rowPageTracker.resetRemainingRow();
				sheet.getHorizontalPageBreaks().add(currentRow);
			}
		}
		
		/**
		 * A9 - A13
		 */
		TotalWorkplaceHierachy totalHierarchyOption = totalOutput.getWorkplaceHierarchyTotal();
		if (totalOutput.isGrossTotal() || totalOutput.isCumulativeWorkplace()) {
			
			int level = workplaceReportData.getLevel();
			
			if (findWorkplaceHigherEnabledLevel(workplaceReportData, totalHierarchyOption) <= level) {
				String tagStr;
				if (level != 0 && level >= totalHierarchyOption.getHighestLevelEnabled() && totalOutput.isCumulativeWorkplace()) {
					// Condition: not root workplace (lvl = 0), level within picked hierarchy zone, enable cumulative workplace.
					tagStr = WorkScheOutputConstants.WORKPLACE_HIERARCHY_TOTAL + workplaceReportData.getLevel();
				} else if (totalOutput.isGrossTotal() && level == 0)
					// Condition: enable gross total, also implicitly root workplace
					tagStr = WorkScheOutputConstants.GROSS_TOTAL;
				else
					tagStr = null;
				
				if (tagStr != null) {
					if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
						sheet.getHorizontalPageBreaks().add(currentRow);
						rowPageTracker.resetRemainingRow();
					}
					
					Range wkpHierTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
					Range wkpHierTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
					wkpHierTotalRange.copy(wkpHierTotalRangeTemp);
					wkpHierTotalRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
					if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
						rowPageTracker.useRemainingRow(dataRowCount);
						rowPageTracker.resetRemainingRow();
					}
					else {
						rowPageTracker.useRemainingRow(dataRowCount);
					}
					
					// A9_1 - A13_1
					Cell workplaceTotalCellTag = cells.get(currentRow, 0);
					workplaceTotalCellTag.setValue(tagStr);
					
					// A9_2 - A13_2
					int numOfChunks = (int) Math.ceil( (double) workplaceReportData.getGrossTotal().size() / CHUNK_SIZE);
					int start, length;
					List<TotalValue> lstItemRow;
					
			        for(int i = 0; i < numOfChunks; i++) {
			            start = i * CHUNK_SIZE;
			            length = Math.min(workplaceReportData.getGrossTotal().size() - start, CHUNK_SIZE);
			
			            lstItemRow = workplaceReportData.getGrossTotal().subList(start, start + length);
			            
			            for (int j = 0; j < length; j++) {
			            	TotalValue totalValue = lstItemRow.get(j);
			            	String value = totalValue.getValue();
			            	Cell cell = cells.get(currentRow + i, DATA_COLUMN_INDEX[0] + j * 2); 
			            	Style style = cell.getStyle();
			            	switch (totalValue.getValueType()) {
							case ActualValue.INTEGER:
								if (value != null)
									cell.setValue(getTimeAttr(value));
								else
									cell.setValue(getTimeAttr("0"));
								style.setHorizontalAlignment(TextAlignmentType.RIGHT);
								break;
							case ActualValue.BIG_DECIMAL:
							case ActualValue.DATE:
							case ActualValue.STRING:
							}
			            	setFontStyle(style);
			            	cell.setStyle(style);
			            }
			        }
			        currentRow += dataRowCount;
				}
			}
		}
		
		return currentRow;
	}
	
	/**
	 * Write detailed daily schedule.
	 *
	 * @param currentRow the current row
	 * @param templateSheetCollection the template sheet collection
	 * @param sheet the sheet
	 * @param dailyReport the daily report
	 * @param dataRowCount the data row count
	 * @param condition the condition
	 * @return the int
	 * @throws Exception the exception
	 */
	public int writeDetailedDailySchedule(int currentRow, WorksheetCollection templateSheetCollection, Worksheet sheet, DailyReportData dailyReport,
			int dataRowCount, WorkScheduleOutputCondition condition, RowPageTracker rowPageTracker) throws Exception {
		Cells cells = sheet.getCells();
		List<WorkplaceDailyReportData> lstDailyReportData = dailyReport.getLstDailyReportData();
		
		Iterator<WorkplaceDailyReportData> iteratorWorkplaceData  = lstDailyReportData.iterator();
		
		while(iteratorWorkplaceData.hasNext()) {
			WorkplaceDailyReportData dailyReportData = iteratorWorkplaceData.next();
			Range dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DATE_ROW);
			Range dateRange = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
			dateRange.copy(dateRangeTemp);
			rowPageTracker.useOneRowAndCheckResetRemainingRow();
			//dateRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
			
			// B3_1
			Cell dateTagCell = cells.get(currentRow, 0);
			dateTagCell.setValue(WorkScheOutputConstants.DATE_BRACKET);
			
			// B3_2
			DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd (E)", Locale.JAPAN);
			String date = dailyReportData.getDate().toLocalDate().format(jpFormatter);
			Cell dateCell = cells.get(currentRow, 2);
			dateCell.setValue(date);
			
			currentRow++;
			
			DailyWorkplaceData rootWorkplace = dailyReportData.getLstWorkplaceData();
			currentRow = writeDailyDetailedPerformanceDataOnWorkplace(currentRow, sheet, templateSheetCollection, rootWorkplace, dataRowCount, condition, rowPageTracker);
		
			if (iteratorWorkplaceData.hasNext()) {
				// Page break (regardless of setting, see example template sheet ★ 日別勤務表-日別3行-1)
				rowPageTracker.resetRemainingRow();
				sheet.getHorizontalPageBreaks().add(currentRow);
			}
		}
		
		if (condition.getSettingDetailTotalOutput().isGrossTotal()) {
			// Gross total after all the rest of the data
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
				sheet.getHorizontalPageBreaks().add(currentRow);
				rowPageTracker.resetRemainingRow();
			}
			Range wkpGrossTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range wkpGrossTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
			wkpGrossTotalRange.copy(wkpGrossTotalRangeTemp);
			wkpGrossTotalRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
				rowPageTracker.useRemainingRow(dataRowCount);
				rowPageTracker.resetRemainingRow();
			}
			else {
				rowPageTracker.useRemainingRow(dataRowCount);
			}
			
			Cell grossTotalCellTag = cells.get(currentRow, 0);
			grossTotalCellTag.setValue(WorkScheOutputConstants.GROSS_TOTAL);
			
			currentRow = writeGrossTotal(currentRow, dailyReport.getListTotalValue(), sheet, dataRowCount, rowPageTracker);
		}
		
		return currentRow;
	}
	
	/**
	 * Write daily detailed performance data on workplace.
	 *
	 * @param currentRow the current row
	 * @param sheet the sheet
	 * @param templateSheetCollection the template sheet collection
	 * @param rootWorkplace the root workplace
	 * @param dataRowCount the data row count
	 * @param condition the condition
	 * @return the int
	 * @throws Exception the exception
	 */
	private int writeDailyDetailedPerformanceDataOnWorkplace(int currentRow, Worksheet sheet, WorksheetCollection templateSheetCollection, DailyWorkplaceData rootWorkplace, int dataRowCount, WorkScheduleOutputCondition condition, RowPageTracker rowPageTracker) throws Exception {
		Cells cells = sheet.getCells();
		Range workplaceRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DATE_ROW);
		Range workplaceRange = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
		workplaceRange.copy(workplaceRangeTemp);
		
		boolean colorWhite = true; // true = white, false = light blue, start with white row
		
		List<DailyPersonalPerformanceData> employeeReportData = rootWorkplace.getLstDailyPersonalData();
		if (employeeReportData != null && !employeeReportData.isEmpty()) {
			rowPageTracker.useOneRowAndCheckResetRemainingRow();
			// B4_1
			Cell workplaceTagCell = cells.get(currentRow, 0);
			workplaceTagCell.setValue(WorkScheOutputConstants.WORKPLACE);
			
			// B4_2
			Cell workplaceCell = cells.get(currentRow, 2);
			workplaceCell.setValue(rootWorkplace.getWorkplaceCode() + " " + rootWorkplace.getWorkplaceName());
			
			currentRow++;
			
			Iterator<DailyPersonalPerformanceData> dataIterator = employeeReportData.iterator();
			
			// Employee data
			while (dataIterator.hasNext() && condition.getSettingDetailTotalOutput().isDetails()){
				DailyPersonalPerformanceData employee = dataIterator.next();
				
				if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
					sheet.getHorizontalPageBreaks().add(currentRow);
					rowPageTracker.resetRemainingRow();
				}
				
				Range dateRangeTemp;
				if (colorWhite) // White row
					dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + dataRowCount);
				else // Light blue row
					dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + dataRowCount);
				Range dateRange = cells.createRange(currentRow, 0, dataRowCount, DATA_COLUMN_INDEX[5]);
				dateRange.copy(dateRangeTemp);
				dateRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
				if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
					rowPageTracker.useRemainingRow(dataRowCount);
					rowPageTracker.resetRemainingRow();
				}
				else {
					rowPageTracker.useRemainingRow(dataRowCount);
				}
				
				// B5_1
				Cell erAlMark = cells.get(currentRow, 0);
				erAlMark.setValue(employee.getErrorAlarmCode());
				
				// B5_2
				Cell employeeCell = cells.get(currentRow, 1);
				employeeCell.setValue(employee.getEmployeeName());
				
				Range employeeRange = cells.createRange(currentRow, 1, dataRowCount, 2);
				employeeRange.merge();
				
				List<ActualValue> lstItem = employee.getActualValue();
				if (lstItem != null) {
					// B5_3
					// Divide list into smaller lists (max 16 items)
					int size = lstItem.size();
					int numOfChunks = (int)Math.ceil((double)size / CHUNK_SIZE);
					int start, length;
					List<ActualValue> lstItemRow;
					
					int curRow = currentRow;
		
			        for(int i = 0; i < numOfChunks; i++) {
			            start = i * CHUNK_SIZE;
			            length = Math.min(size - start, CHUNK_SIZE);
		
			            lstItemRow = lstItem.subList(start, start + length);
			            for (int j = 0; j < length; j++) {
			            	// Column 4, 6, 8,...
			            	ActualValue actualValue = lstItemRow.get(j);
			            	Cell cell = cells.get(curRow, DATA_COLUMN_INDEX[0] + j * 2); 
			            	Style style = cell.getStyle();
			            	switch (actualValue.getValueType()) {
							case ActualValue.INTEGER:
								String value = actualValue.getValue();
								if (value != null)
									cell.setValue(getTimeAttr(value));
								else
									cell.setValue(getTimeAttr("0"));
								style.setHorizontalAlignment(TextAlignmentType.RIGHT);
								break;
							case ActualValue.BIG_DECIMAL:
							case ActualValue.DATE:
							case ActualValue.STRING:
								cell.setValue(actualValue.getValue());
								style.setHorizontalAlignment(TextAlignmentType.LEFT);
							}
			            	setFontStyle(style);
			            	cell.setStyle(style);
			            }
			            
			            curRow++;
			        }
			        
			        // B5_4
			        Cell remarkCell = cells.get(currentRow,35);
			        remarkCell.setValue(employee.getDetailedErrorData());
				}
		        currentRow += dataRowCount;
		        colorWhite = !colorWhite; // Change to other color
		        
		        // Only break when has next iterator
		        if (dataIterator.hasNext() && condition.getPageBreakIndicator() == PageBreakIndicator.EMPLOYEE) {
		        	// Thin border for last row of page
		        	Range lastRowRange = cells.createRange(currentRow - 1, 0, 1, 39);
		        	lastRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		        	
		        	// Page break by employee
					sheet.getHorizontalPageBreaks().add(currentRow);
					rowPageTracker.resetRemainingRow();
		        }
			}
		}
		
		// Workplace total, root workplace use gross total instead
		if (condition.getSettingDetailTotalOutput().isWorkplaceTotal() && rootWorkplace.getLevel() != 0) {
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
				sheet.getHorizontalPageBreaks().add(currentRow);
				rowPageTracker.resetRemainingRow();
			}
			Range workplaceTotalTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range workplaceTotal = cells.createRange(currentRow, 0, dataRowCount, DATA_COLUMN_INDEX[5]);
			workplaceTotal.copy(workplaceTotalTemp);
			workplaceTotal.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
			workplaceTotal.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
				rowPageTracker.useRemainingRow(dataRowCount);
				rowPageTracker.resetRemainingRow();
			}
			else {
				rowPageTracker.useRemainingRow(dataRowCount);
			}
			
			// B6_1
			Cell workplaceTotalCellTag = cells.get(currentRow, 0);
			workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_TOTAL);
			
			// B6_2
			currentRow = writeWorkplaceTotal(currentRow, rootWorkplace, sheet, dataRowCount, true);
		}
		
		boolean firstWorkplace = true;

		
		Map<String, DailyWorkplaceData> mapChildWorkplace = rootWorkplace.getLstChildWorkplaceData();
		if (((condition.getPageBreakIndicator() == PageBreakIndicator.WORKPLACE || 
				condition.getPageBreakIndicator() == PageBreakIndicator.EMPLOYEE) &&
				mapChildWorkplace.size() > 0) && rootWorkplace.level != 0) {
			Range lastRowRange = cells.createRange(currentRow - 1, 0, 1, 39);
        	lastRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        	rowPageTracker.resetRemainingRow();
			sheet.getHorizontalPageBreaks().add(currentRow);
		}
		// Child workplace
		for (Map.Entry<String, DailyWorkplaceData> entry: mapChildWorkplace.entrySet()) {
			// Page break by workplace
			if ((condition.getPageBreakIndicator() == PageBreakIndicator.WORKPLACE || 
					condition.getPageBreakIndicator() == PageBreakIndicator.EMPLOYEE) && !firstWorkplace) {
				Range lastRowRange = cells.createRange(currentRow - 1, 0, 1, 39);
	        	lastRowRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
	        	rowPageTracker.resetRemainingRow();
				sheet.getHorizontalPageBreaks().add(currentRow);
			}
			firstWorkplace = false;
			
			currentRow = writeDailyDetailedPerformanceDataOnWorkplace(currentRow, sheet, templateSheetCollection, entry.getValue(), dataRowCount, condition, rowPageTracker);
		}
		
		// Workplace hierarchy total
		int level = rootWorkplace.getLevel();
		TotalWorkplaceHierachy settingTotalHierarchy = condition.getSettingDetailTotalOutput().getWorkplaceHierarchyTotal();
		if (level != 0 && level >= settingTotalHierarchy.getHighestLevelEnabled() 
				&& condition.getSettingDetailTotalOutput().isCumulativeWorkplace() && settingTotalHierarchy.checkLevelEnabled(level)) {
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) < 0) {
				sheet.getHorizontalPageBreaks().add(currentRow);
				rowPageTracker.resetRemainingRow();
			}
			Range wkpHierTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range wkpHierTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
			wkpHierTotalRange.copy(wkpHierTotalRangeTemp);
			wkpHierTotalRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			if (rowPageTracker.checkRemainingRowSufficient(dataRowCount) == 0) {
				rowPageTracker.useRemainingRow(dataRowCount);
				rowPageTracker.resetRemainingRow();
			}
			else {
				rowPageTracker.useRemainingRow(dataRowCount);
			}
			
			// B7_1 - B11_1
			Cell workplaceTotalCellTag = cells.get(currentRow, 0);
			workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_HIERARCHY_TOTAL + rootWorkplace.getLevel());
			
			// B7_2 - B11_2
			currentRow = writeWorkplaceTotal(currentRow, rootWorkplace, sheet, dataRowCount, false);
		}
		
		return currentRow;
	}

	/**
	 * Write workplace total for export type by date.
	 *
	 * @param currentRow the current row
	 * @param rootWorkplace the root workplace
	 * @param cells the cells
	 * @return the int
	 */
	private int writeWorkplaceTotal(int currentRow, DailyWorkplaceData rootWorkplace, Worksheet sheet, int dataRowCount, boolean totalType) {
		List<TotalValue> totalWorkplaceValue;
		if (!totalType)
			totalWorkplaceValue = rootWorkplace.getWorkplaceTotal().getTotalWorkplaceValue();
		else
			totalWorkplaceValue = rootWorkplace.getWorkplaceHierarchyTotal().getTotalWorkplaceValue();
		int size = totalWorkplaceValue.size();
		if (size == 0) {
			currentRow += dataRowCount;
		}
		else {
			
			Cells cells = sheet.getCells();
			
			int numOfChunks = (int) Math.ceil( (double) size / CHUNK_SIZE);
			int start, length;
			List<TotalValue> lstItemRow;
			
			for(int i = 0; i < numOfChunks; i++) {
			    start = i * CHUNK_SIZE;
			    length = Math.min(size - start, CHUNK_SIZE);

			    lstItemRow = totalWorkplaceValue.stream().collect(Collectors.toList()).subList(start, start + length);
			    TotalValue totalValue;
			    
			    for (int j = 0; j < length; j++) {
			    	totalValue = lstItemRow.get(j);
			    	String value = totalValue.getValue();
		        	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
		        	Style style = cell.getStyle();
	            	switch (totalValue.getValueType()) {
					case ActualValue.INTEGER:
						if (value != null)
							cell.setValue(getTimeAttr(value));
						else
							cell.setValue(getTimeAttr("0"));
						style.setHorizontalAlignment(TextAlignmentType.RIGHT);
						break;
					case ActualValue.BIG_DECIMAL:
					case ActualValue.DATE:
					case ActualValue.STRING:
					}
	            	setFontStyle(style);
	            	cell.setStyle(style);
			    }
			    currentRow++;
			}
			
			if (numOfChunks == 0) currentRow++;
		}
		
		return currentRow;
	}
	
	/**
	 * Write gross total.
	 *
	 * @param currentRow the current row
	 * @param lstGrossTotal the lst gross total
	 * @param cells the cells
	 * @return the int
	 */
	private int writeGrossTotal(int currentRow, List<TotalValue> lstGrossTotal, Worksheet sheet, int dataRowCount, RowPageTracker rowPageTracker) {
		int size = lstGrossTotal.size();
		if (size == 0) {
			currentRow += dataRowCount;
		}
		else {
			Cells cells = sheet.getCells();
			
			int numOfChunks = (int) Math.ceil( (double) size / CHUNK_SIZE);
			int start, length;
			List<TotalValue> lstItemRow;
			
			for(int i = 0; i < numOfChunks; i++) {
			    start = i * CHUNK_SIZE;
			    length = Math.min(lstGrossTotal.size() - start, CHUNK_SIZE);

			    lstItemRow = lstGrossTotal.stream().collect(Collectors.toList()).subList(start, start + length);
			    
			    TotalValue totalValue;
			    
			    for (int j = 0; j < length; j++) {
			    	totalValue =lstItemRow.get(j);
			    	String value = totalValue.getValue();
		        	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
		        	Style style = cell.getStyle();
	            	switch (totalValue.getValueType()) {
					case ActualValue.INTEGER:
						if (value != null)
							cell.setValue(getTimeAttr(value));
						else
							cell.setValue(getTimeAttr("0"));
						style.setHorizontalAlignment(TextAlignmentType.RIGHT);
						break;
					case ActualValue.BIG_DECIMAL:
					case ActualValue.DATE:
					case ActualValue.STRING:
						cell.setValue(value);
						style.setHorizontalAlignment(TextAlignmentType.LEFT);
					}
	            	setFontStyle(style);
	            	cell.setStyle(style);
			    }
			    currentRow++;
			}
			
			if (numOfChunks == 0) currentRow++;
		}
		
		return currentRow;
	}
	
	/**
	 * Create print area of the entire report, important for exporting to PDF.
	 *
	 * @param currentRow the current row
	 * @param sheet the sheet
	 */
	public void createPrintArea(int currentRow, Worksheet sheet) {
		// Get the last column and row name
		Cells cells = sheet.getCells();
		Cell lastCell = cells.get(currentRow - 1, 38);
		String lastCellName = lastCell.getName();
		
		PageSetup pageSetup = sheet.getPageSetup();
		pageSetup.setPrintArea("A1:" + lastCellName);
	}
	
	/**
	 * Gets the time attr.
	 *
	 * @param rawValue the raw value
	 * @return the time attr
	 */
	public String getTimeAttr(String rawValue) {
		int value = Integer.parseInt(rawValue);
		int minute = value % 60;
		return String.valueOf(value / 60) + ":" + (minute < 10 ? "0" + minute : String.valueOf(minute));
	}
	
	/**
	 * Find workplace higher enabled level.
	 *
	 * @param workplaceReportData the workplace report data
	 * @param hierarchy the hierarchy
	 * @return the int
	 */
	public int findWorkplaceHigherEnabledLevel(WorkplaceReportData workplaceReportData, TotalWorkplaceHierachy hierarchy) {
		int currentLevel = workplaceReportData.getLevel();
		if (currentLevel != 0) {
			int lowerEnabledLevel = hierarchy.getLowerClosestSelectedHierarchyLevel(currentLevel);
			if (lowerEnabledLevel == 0) {
				WorkplaceReportData parentWorkplace = workplaceReportData.getParent();
				return findWorkplaceHigherEnabledLevel(parentWorkplace, hierarchy);
			}
			return lowerEnabledLevel;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Gets the remark content.
	 *
	 * @param employeeId the employee id
	 * @param currentDate the current date
	 * @param errorList the error list
	 * @param choice the choice
	 * @param lstOutputErrorCode the lst output error code
	 * @return the remark content
	 */
	public Optional<PrintRemarksContent> getRemarkContent(String employeeId, GeneralDate currentDate, List<EmployeeDailyPerError> errorList, RemarksContentChoice choice, List<ErrorAlarmWorkRecordCode> lstOutputErrorCode) {
		PrintRemarksContent printRemarksContent = null;
		
		// 遅刻早退
		List<String> errorCodeList = lstOutputErrorCode.stream().map(x -> x.v()).collect(Collectors.toList());
		if (errorList.size() > 0 && (errorCodeList.contains(SystemFixedErrorAlarm.LEAVE_EARLY.value) || errorCodeList.contains(SystemFixedErrorAlarm.LATE.value))) {
			// Late come
			boolean isLateCome = false, isEarlyLeave = false;
			Optional<EmployeeDailyPerError> optErrorLateCome = errorList.stream()
					.filter(x -> x.getErrorAlarmWorkRecordCode().v().contains(SystemFixedErrorAlarm.LATE.value)).findFirst();
			if (optErrorLateCome.isPresent() && (choice == RemarksContentChoice.LEAVING_EARLY)) {
				isLateCome = true;
			}
			
			// Early leave
			Optional<EmployeeDailyPerError> optErrorEarlyLeave = errorList.stream()
					.filter(x -> x.getErrorAlarmWorkRecordCode().v().contains(SystemFixedErrorAlarm.LEAVE_EARLY.value)).findFirst();
			if (optErrorEarlyLeave.isPresent() && (choice == RemarksContentChoice.LEAVING_EARLY)) {
				isEarlyLeave = true;
			}
			
			// Both case
			if (isLateCome && isEarlyLeave) {
				printRemarksContent = new PrintRemarksContent(1, RemarksContentChoice.LATE_COME_EARLY_LEAVE.value);
			}
			// Late come
			else if (isLateCome) {
				printRemarksContent = new PrintRemarksContent(1, RemarksContentChoice.LATE_COME.value);
			}
			// Early leave
			else if (isEarlyLeave) {
				printRemarksContent = new PrintRemarksContent(1, RemarksContentChoice.LEAVING_EARLY.value);
			}
		}
		
		// 手入力
		List<EditStateOfDailyPerformanceDto> editStateDto = editStateFinder.finds(employeeId, currentDate);
		List<GeneralDate> lstEditStateDate = new ArrayList<>();
		// Likely lstEditStateDate only has 0-1 element
		editStateDto.stream().filter(x -> x.getEditStateSetting() == EditStateSetting.HAND_CORRECTION_MYSELF.value
									   || x.getEditStateSetting() == EditStateSetting.HAND_CORRECTION_OTHER.value).forEach(x -> lstEditStateDate.add(x.getYmd()));
		
		if (lstEditStateDate.size() > 0 && choice == RemarksContentChoice.MANUAL_INPUT) {
			printRemarksContent = new PrintRemarksContent(1, RemarksContentChoice.MANUAL_INPUT.value);
		}
		
		// 承認反映
		List<GeneralDate> lstReflectApprovalDate = new ArrayList<>();
		lstReflectApprovalDate = editStateDto.stream().filter(x -> x.getEditStateSetting() == EditStateSetting.REFLECT_APPLICATION.value).map(x -> {return x.getYmd();}).collect(Collectors.toList());
		if (lstReflectApprovalDate.size() > 0 && choice == RemarksContentChoice.ACKNOWLEDGMENT) {
			printRemarksContent = new PrintRemarksContent(1, RemarksContentChoice.ACKNOWLEDGMENT.value);
		}
		
		return Optional.ofNullable(printRemarksContent);
	}
}
