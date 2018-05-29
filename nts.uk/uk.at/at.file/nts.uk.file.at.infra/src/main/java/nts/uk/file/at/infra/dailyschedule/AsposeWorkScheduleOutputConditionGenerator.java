package nts.uk.file.at.infra.dailyschedule;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
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
import com.aspose.cells.FileFormatType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.PdfCompliance;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
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
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
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
import nts.uk.file.at.app.export.dailyschedule.data.DailyPerformanceHeaderData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyPerformanceReportData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyPersonalPerformanceData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyReportData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyWorkplaceData;
import nts.uk.file.at.app.export.dailyschedule.data.DetailedDailyPerformanceReportData;
import nts.uk.file.at.app.export.dailyschedule.data.EmployeeReportData;
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
	
	@Inject
	private SCEmployeeAdapter employeeAdapter;
	
	/** The finder. */
	@Inject
	private EmploymentRepository employmentRepository;
	
	@Inject
	private DailyAttendanceItemNameDomainService attendanceNameService;
	
	@Inject
	private JobTitleImportAdapter jobTitleAdapter;
	
	@Inject
	private CompanyRepository companyRepository;
	
	/** The filename. */
	private final String filename = "report/KWR001.xlsx";
	
	/** The Constant CHUNK_SIZE. */
	private static final int CHUNK_SIZE = 16;
	
	/** The Constant DATA_COLUMN_INDEX. */
	private static final int[] DATA_COLUMN_INDEX = {3, 8, 10, 14, 16, 39};
	
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
			Integer currentRow;
			
			// Calculate row size and get sheet
			int nSize = reportData.getHeaderData().getLstOutputItemSettingCode().size() / CHUNK_SIZE + 1;
			if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE)
				switch (nSize) {
				case 1:
					sheet = workbook.getWorksheets().get(0);
					currentRow = 3;
					break;
				case 2:
					sheet = workbook.getWorksheets().get(1);
					currentRow = 3;
					break;
				case 3:
				default:
					sheet = workbook.getWorksheets().get(2);
					currentRow = 2;
				}
			else {
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
				currentRow = 2;
			}
			
			// Write header data
			writeHeaderData(query, sheet, reportData);
			 	
			// Set all fixed cell
			setFixedData(condition, sheet, reportData, currentRow);
			
			// Write display map
			writeDisplayMap(sheet.getCells(),reportData, currentRow, nSize);
			
			currentRow+=nSize*2;
			
			WorksheetCollection sheetCollection = workbook.getWorksheets();
			
			// Write detailed work schedule
			if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE)
				writeDetailedWorkSchedule(currentRow, sheetCollection, sheet, reportData.getWorkplaceReportData(), nSize, condition);
			else {
				DailyReportData dailyReportData = reportData.getDailyReportData();
				writeDetailedDailySchedule(currentRow, sheetCollection, sheet, dailyReportData, nSize, condition);
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
			
			// Save workbook
			if (query.getFileType() == FileOutputType.FILE_TYPE_EXCEL)
				reportContext.saveAsExcel(this.createNewFile(generatorContext, WorkScheOutputConstants.SHEET_FILE_NAME + ".xlsx"));
			else {
				// Create print area
				createPrintArea(currentRow, sheet);
				reportContext.saveAsPdf(this.createNewFile(generatorContext, WorkScheOutputConstants.SHEET_FILE_NAME + ".pdf"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		Map<String, WorkplaceInfo> lstWorkplace = new TreeMap<>(); // Automatically sort by code, will need to check hierarchy later
		List<String> lstWorkplaceId = new ArrayList<>();
		
		for (String employeeId: query.getEmployeeId()) {
			WkpHistImport workplaceImport = workplaceAdapter.findWkpBySid(employeeId, query.getEndDate());
			lstWorkplaceId.add(workplaceImport.getWorkplaceId());
		}
		
		// Collect child workplace, automatically sort into tree map
		for (String entry: lstWorkplaceId) {
			lstWorkplace.putAll(collectWorkplaceHierarchy(entry, query.getEndDate()));
		}
		
		List<AttendanceResultImport> lstAttendanceResultImport = attendaceAdapter.getValueOf(query.getEmployeeId(), new DatePeriod(query.getStartDate(), query.getEndDate()), AttendanceResultImportAdapter.convertList(outputItem.getLstDisplayedAttendance(), x -> x.getAttendanceDisplay()));
		
		// Check lowest level of employee and highest level of output setting, and attendance result count is 0
		// 階層累計行のみ出力する設定の場合、データ取得件数は0件として扱い、エラーメッセージを表示(#Msg_37#)
		int lowestEmployeeLevel = checkLowestWorkplaceLevel(lstWorkplace);
		TotalWorkplaceHierachy outputSetting = condition.getSettingDetailTotalOutput().getWorkplaceHierarchyTotal();
		int highestOutputLevel = outputSetting.getHighestLevelEnabled();
		if (lowestEmployeeLevel > highestOutputLevel && lstAttendanceResultImport.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Msg_37"));
		}
		
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			WorkplaceReportData data = new WorkplaceReportData();
			data.workplaceCode = "";
			reportData.setWorkplaceReportData(data);
			
			List<String> lstAddedCode = new ArrayList<>();
			analyzeInfo(lstWorkplace, data, lstAddedCode);
			
			String key;
			for (Map.Entry<String, WorkplaceInfo> entry: lstWorkplace.entrySet()) {
				key = entry.getKey();
				if (!lstAddedCode.contains(key)) {
					WorkplaceReportData wrp = new WorkplaceReportData();
					WorkplaceInfo info = entry.getValue();
					wrp.workplaceCode = info.getWorkplaceCode().v();
					wrp.workplaceName = info.getWorkplaceName().v();
					wrp.level = key.length() / 3;
					wrp.lstEmployeeReportData = new ArrayList<>();
					wrp.lstChildWorkplaceReportData = new TreeMap<>();
					wrp.parent = data;
					data.lstChildWorkplaceReportData.put(key, wrp);
				}
			}
			
			for (String employeeId: query.getEmployeeId()) {
				EmployeeReportData employeeData = collectedEmployeePerformanceData(reportData, query, lstAttendanceResultImport, employeeId);
				
				// Calculate total count day
				totalDayCountWs.calculateAllDayCount(employeeId, new DateRange(query.getStartDate(), query.getEndDate()), employeeData.totalCountDay);
				
			}
			
			calculateTotal(data, outputItem.getLstDisplayedAttendance());
		
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
				analyzeInfo(lstWorkplaceTemp, x.getLstWorkplaceData(), lstAddedCode);
			});
			
			String key;
			for (Map.Entry<String, WorkplaceInfo> entry: lstWorkplace.entrySet()) {
				key = entry.getKey();
				if (!lstAddedCode.contains(key)) {
					String hierarchyCode = key;
					lstReportData.forEach(x -> {
						WorkplaceInfo info = entry.getValue();
						DailyWorkplaceData dailyWorkplaceData = new DailyWorkplaceData();
						dailyWorkplaceData.workplaceCode = info.getWorkplaceCode().v();
						dailyWorkplaceData.workplaceName = info.getWorkplaceName().v();
						dailyWorkplaceData.setLstDailyPersonalData(new ArrayList<>());
						dailyWorkplaceData.setLstChildWorkplaceData(new TreeMap<>());
						dailyWorkplaceData.level = hierarchyCode.length() / 3;
						dailyWorkplaceData.parent = x.getLstWorkplaceData();
						x.getLstWorkplaceData().lstChildWorkplaceData.put(hierarchyCode, dailyWorkplaceData);
					});
				}
			}
			
			List<GeneralDate> lstDate = new DateRange(query.getStartDate(), query.getEndDate()).toListDate();
			collectedEmployeePerformanceData(reportData, query, lstAttendanceResultImport, lstDate);
			// Calculate workplace total
			lstReportData.forEach(dailyData -> {
				calculateTotal(dailyData.getLstWorkplaceData());
			});
			
			// Calculate gross total
			List<TotalValue> lstGrossTotalValue = new ArrayList<>();
			dailyReportData.setListTotalValue(lstGrossTotalValue);
			calculateGrossTotalByDate(dailyReportData);
		}
	}
	
	/**
	 * Get lowest workplace hierarchy level
	 * @param lstWorkplace
	 * @return
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
	 * Collected employee performance data.
	 *
	 * @param reportData the report data
	 * @param query the query
	 * @param lstAttendanceResultImport the lst attendance result import
	 * @param date the date
	 */
	private void collectedEmployeePerformanceData(DailyPerformanceReportData reportData, WorkScheduleOutputQuery query,
			List<AttendanceResultImport> lstAttendanceResultImport, List<GeneralDate> datePeriod) {
		// Get all error alarm code
		List<ErrorAlarmWorkRecordCode> lstErAlCode = query.getCondition().getErrorAlarmCode().get();
		
		for (String employeeId: query.getEmployeeId()) {
			EmployeeDto employeeDto = employeeAdapter.findByEmployeeId(employeeId);
			
			datePeriod.stream().forEach(date -> {
				Optional<WorkplaceDailyReportData> optDailyWorkplaceData =  reportData.getDailyReportData().getLstDailyReportData().stream().filter(x -> x.getDate().compareTo(date) == 0).findFirst();
				DailyWorkplaceData dailyWorkplaceData = findWorkplace(employeeId,optDailyWorkplaceData.get().getLstWorkplaceData(), query.getEndDate());
				if (dailyWorkplaceData != null) {
					DailyPersonalPerformanceData personalPerformanceDate = new DailyPersonalPerformanceData();
					personalPerformanceDate.setEmployeeName(employeeDto.getEmployeeName());
					dailyWorkplaceData.getLstDailyPersonalData().add(personalPerformanceDate);
					
					lstAttendanceResultImport.stream().filter(x -> (x.getEmployeeId().equals(employeeId) && x.getWorkingDate().compareTo(date) == 0)).forEach(x -> {
						
						// ドメインモデル「社員の日別実績エラー一覧」を取得する
						List<EmployeeDailyPerError> errorList = errorAlarmRepository.find(employeeId, x.getWorkingDate());
						List<String> lstRemarkContentStr = new ArrayList<>();
						if (query.getCondition().getErrorAlarmCode().isPresent()) {
							// Always has item because this has passed error check
							OutputItemDailyWorkSchedule outSche = outputItemRepo.findByCidAndCode(AppContexts.user().companyId(), query.getCondition().getCode().v()).get();
							
							// Get list remark check box from screen C (UI)
							List<PrintRemarksContent> lstRemarkContent = outSche.getLstRemarkContent();
							lstRemarkContent.stream().filter(remark -> remark.isUsedClassification()).forEach(remark -> {
								// Get possible content based on remark choice
								Optional<PrintRemarksContent> optContent = getRemarkContent(employeeId, x.getWorkingDate(), errorList, remark.getPrintItem(), lstErAlCode);
								if (optContent.isPresent()) {
									// Add to remark
									lstRemarkContentStr.add(TextResource.localize(optContent.get().getPrintItem().nameId));
								}
							});
							
							personalPerformanceDate.detailedErrorData = String.join("、", lstRemarkContentStr);
						}
						
						// ER/AL
						if (query.getCondition().getConditionSetting() == OutputConditionSetting.USE_CONDITION) {
							boolean erMark = false, alMark = false;
							
							List<String> lstErrorCode = errorList.stream().map(error -> error.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList());
							// ドメインモデル「勤務実績のエラーアラーム」を取得する
							List<ErrorAlarmWorkRecord> lstErrorRecord = errorAlarmWorkRecordRepo.getListErAlByListCodeError(AppContexts.user().companyId(), lstErrorCode);
							
							for (ErrorAlarmWorkRecord error : lstErrorRecord) {
								// コードから区分を取得する
								switch (error.getTypeAtr()) {
								case ALARM: // 区分　＝　エラー　のとき　ER
									alMark = true; 
									break;
								case ERROR: // 区分　=　アラーム　のとき　AL
									erMark = true;
									break;
								case OTHER:
									break;
								}
								if (erMark && alMark) break;
							}
							if (erMark && alMark) {
								personalPerformanceDate.errorAlarmCode = WorkScheOutputConstants.ER + "/" + WorkScheOutputConstants.AL;
							}
							else if (erMark) {
								personalPerformanceDate.errorAlarmCode = WorkScheOutputConstants.ER;
							}
							else if (alMark) {
								personalPerformanceDate.errorAlarmCode = WorkScheOutputConstants.AL;
							}
						}
						personalPerformanceDate.actualValue = new ArrayList<>();
						x.getAttendanceItems().stream().forEach(a -> personalPerformanceDate.actualValue.add(new ActualValue(a.getItemId(), a.getValue(), a.getValueType())));
					});
				}
			});
			
			
		}
	}

	/**
	 * Collected employee performance data.
	 *
	 * @param reportData the report data
	 * @param query the query
	 * @param lstAttendanceResultImport the lst attendance result import
	 * @param employeeId the employee id
	 * @return the employee report data
	 */
	private EmployeeReportData collectedEmployeePerformanceData(DailyPerformanceReportData reportData, WorkScheduleOutputQuery query,
			List<AttendanceResultImport> lstAttendanceResultImport, String employeeId) {
		WorkplaceReportData workplaceData = findWorkplace(employeeId, reportData.getWorkplaceReportData(), query.getEndDate());
		EmployeeReportData employeeData = new EmployeeReportData();
		
		List<ErrorAlarmWorkRecordCode> lstErAlCode = query.getCondition().getErrorAlarmCode().get();
		
		// Employee code and name
		EmployeeDto employeeDto = employeeAdapter.findByEmployeeId(employeeId);
		employeeData.employeeName = employeeDto.getEmployeeName();
		employeeData.employeeCode = employeeDto.getEmployeeCode();
		
		// Employment
		Optional<EmploymentHistoryImported> optEmploymentImport = employmentAdapter.getEmpHistBySid(AppContexts.user().companyId(), employeeId, query.getEndDate());
		if (optEmploymentImport.isPresent()) {
			String employmentCode = optEmploymentImport.get().getEmploymentCode();
			Optional<Employment> optEmployment = employmentRepository.findEmployment(AppContexts.user().companyId(), employmentCode);
			if (optEmployment.isPresent()) {
				Employment employment = optEmployment.get();
				employeeData.employmentName = employment.getEmploymentName().v();
			}
		}
		
		// Job title
		Optional<EmployeeJobHistExport> optJobTitle = jobTitleAdapter.findBySid(employeeId, query.getEndDate());
		if (optJobTitle.isPresent())
			employeeData.position = optJobTitle.get().getJobTitleName();
		else
			employeeData.position = "";
		
		employeeData.lstDetailedPerformance = new ArrayList<>();
		workplaceData.lstEmployeeReportData.add(employeeData);
		lstAttendanceResultImport.stream().filter(x -> x.getEmployeeId().equals(employeeId)).sorted((o1,o2) -> o1.getWorkingDate().compareTo(o2.getWorkingDate())).forEach(x -> {
			DetailedDailyPerformanceReportData detailedDate = new DetailedDailyPerformanceReportData();
			detailedDate.setDate(x.getWorkingDate());
			detailedDate.setDayOfWeek(String.valueOf(x.getWorkingDate().dayOfWeek()));
			employeeData.lstDetailedPerformance.add(detailedDate);
			
			// ドメインモデル「社員の日別実績エラー一覧」を取得する
			List<EmployeeDailyPerError> errorList = errorAlarmRepository.find(employeeId, x.getWorkingDate());
			List<String> lstRemarkContentStr = new ArrayList<>();
			if (query.getCondition().getErrorAlarmCode().isPresent()) {
				// Always has item because this has passed error check
				OutputItemDailyWorkSchedule outSche = outputItemRepo.findByCidAndCode(AppContexts.user().companyId(), query.getCondition().getCode().v()).get();
				
				// Get list remark check box from screen C (UI)
				List<PrintRemarksContent> lstRemarkContent = outSche.getLstRemarkContent();
				lstRemarkContent.stream().filter(remark -> remark.isUsedClassification()).forEach(remark -> {
					// Get possible content based on remark choice
					Optional<PrintRemarksContent> optContent = getRemarkContent(employeeId, x.getWorkingDate(), errorList, remark.getPrintItem(), lstErAlCode);
					if (optContent.isPresent()) {
						// Add to remark
						lstRemarkContentStr.add(TextResource.localize(optContent.get().getPrintItem().nameId));
					}
				});
				
				detailedDate.errorDetail = String.join("、", lstRemarkContentStr);
			}
			
			// ER/AL
			if (query.getCondition().getConditionSetting() == OutputConditionSetting.USE_CONDITION) {
				boolean erMark = false, alMark = false;
				List<String> lstErrorCode = errorList.stream().map(error -> error.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList());
				// ドメインモデル「勤務実績のエラーアラーム」を取得する
				List<ErrorAlarmWorkRecord> lstErrorRecord = errorAlarmWorkRecordRepo.getListErAlByListCodeError(AppContexts.user().companyId(), lstErrorCode);
				
				for (ErrorAlarmWorkRecord error : lstErrorRecord) {
					// コードから区分を取得する
					switch (error.getTypeAtr()) {
					case ALARM: // 区分　＝　エラー　のとき　ER
						alMark = true; 
						break;
					case ERROR: // 区分　=　アラーム　のとき　AL
						erMark = true;
						break;
					case OTHER:
						break;
					}
					if (erMark && alMark) break;
				}
				if (erMark && alMark) {
					detailedDate.errorAlarmMark = WorkScheOutputConstants.ER + "/" + WorkScheOutputConstants.AL;
				}
				else if (erMark) {
					detailedDate.errorAlarmMark = WorkScheOutputConstants.ER;
				}
				else if (alMark) {
					detailedDate.errorAlarmMark = WorkScheOutputConstants.AL;
				}
			}
			
			detailedDate.actualValue = new ArrayList<>();
			x.getAttendanceItems().stream().forEach(a -> detailedDate.actualValue.add(new ActualValue(a.getItemId(), a.getValue(), a.getValueType())));
			//x.getAttendanceItems().stream().forEach(a -> detailedDate.actualValue.add(new ActualValue(a.getItemId(), "1", 0)));
		});
		return employeeData;
	}
	
	/**
	 * Calculate total.
	 *
	 * @param workplaceData the workplace data
	 * @param lstAttendanceId the lst attendance id
	 */
	public void calculateTotal(WorkplaceReportData workplaceData, List<AttendanceItemsDisplay> lstAttendanceId) {
		// Recursive
		workplaceData.getLstChildWorkplaceReportData().values().forEach(x -> {
			calculateTotal(x, lstAttendanceId);
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
			return totalVal;
		}).collect(Collectors.toList());
		workplaceData.setGrossTotal(lstWorkplaceGrossTotal);
		workplaceData.getLstChildWorkplaceReportData().values().forEach(child -> {
			child.getWorkplaceTotal().getTotalWorkplaceValue().stream().forEach(val -> {
				int attendanceId = val.getAttendanceId();
				
				switch (val.getValueType()) {
				case ActualValue.INTEGER:
				case ActualValue.BIG_DECIMAL:
					int currentValue = (int) val.value();
					TotalValue totalVal = lstWorkplaceGrossTotal.stream().filter(attendance -> attendance.getAttendanceId() == attendanceId).findFirst().get();
					totalVal.setValue(String.valueOf(Integer.parseInt(totalVal.getValue()) + currentValue));
					break;
				case ActualValue.DATE: // Unknown calculate method
					break;
				case ActualValue.STRING: // Not calculated
					break;
				}
			});
		});
		
		// Calculate total of employees of current level (after calculating total of child workplace
		List<EmployeeReportData> lstReportData = workplaceData.getLstEmployeeReportData();
		if (lstReportData != null && lstReportData.size() > 0) {
			workplaceData.getLstEmployeeReportData().stream().forEach(employeeData -> {
				// Put attendanceId into map
				lstAttendanceId.stream().forEach(attendanceId -> {
					employeeData.mapPersonalTotal.put(attendanceId.getAttendanceDisplay(), "0");
					TotalValue totalVal = new TotalValue();
					totalVal.setAttendanceId(attendanceId.getAttendanceDisplay());
					totalVal.setValue("0");
					totalVal.setValueType(0);
					lstTotalValue.add(totalVal);
				});
				
				// Add into total by attendanceId
				employeeData.getLstDetailedPerformance().stream().forEach(detail -> {
					detail.actualValue.stream().forEach((aVal) -> {
						int attdId = aVal.getAttendanceId();
						
						switch (aVal.getValueType()) {
						case ActualValue.INTEGER:
						case ActualValue.BIG_DECIMAL:
							int currentValue = (int) aVal.value();
							employeeData.mapPersonalTotal.put(attdId, String.valueOf(Integer.parseInt(employeeData.mapPersonalTotal.get(attdId)) + (int) currentValue));
							TotalValue totalVal = lstTotalValue.stream().filter(attendance -> attendance.getAttendanceId() == attdId).findFirst().get();
							totalVal.setValue(String.valueOf(Integer.parseInt(totalVal.getValue()) + currentValue));
							TotalValue totalGrossVal = lstWorkplaceGrossTotal.stream().filter(attendance -> attendance.getAttendanceId() == attdId).findFirst().get();
							totalGrossVal.setValue(String.valueOf(Integer.parseInt(totalGrossVal.getValue()) + currentValue));
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
	}
	
	/**
	 * Calculate total.
	 *
	 * @param workplaceData the workplace data
	 */
	public void calculateTotal(DailyWorkplaceData workplaceData) {
		// Recursive
		workplaceData.getLstChildWorkplaceData().values().forEach(x -> {
			calculateTotal(x);
		});
		// Workplace
		workplaceData.workplaceTotal = new WorkplaceTotal();
		workplaceData.workplaceTotal.setWorkplaceId(workplaceData.getWorkplaceCode());
		List<TotalValue> lstTotalValue = new ArrayList<>();
		workplaceData.workplaceTotal.setTotalWorkplaceValue(lstTotalValue);
		
		List<DailyPersonalPerformanceData> performanceData = workplaceData.getLstDailyPersonalData();
		
		// Employee
		if (performanceData != null && performanceData.size() > 0) {
			for (DailyPersonalPerformanceData data: performanceData) {
				List<ActualValue> lstActualValue = data.getActualValue();
				lstActualValue.forEach(actualValue -> {
					Optional<TotalValue> optTotalVal = lstTotalValue.stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
					TotalValue totalValue;
					if (optTotalVal.isPresent()) {
						totalValue = optTotalVal.get();
						if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL) {
							totalValue.setValue(String.valueOf(Integer.parseInt(totalValue.getValue()) + Integer.parseInt(actualValue.getValue())));
						}
					}
					else {
						totalValue = new TotalValue();
						totalValue.setAttendanceId(actualValue.getAttendanceId());
						if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL)
							totalValue.setValue(actualValue.getValue());
						lstTotalValue.add(totalValue);
					}
				});
			}
		}
		
		// Workplace
		Map<String, DailyWorkplaceData> lstReportData = workplaceData.getLstChildWorkplaceData();
		lstReportData.values().forEach((value) -> {
			List<TotalValue> lstActualValue = value.getWorkplaceTotal().getTotalWorkplaceValue();
			lstActualValue.forEach(actualValue -> {
				Optional<TotalValue> optTotalVal = lstTotalValue.stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
				TotalValue totalValue;
				if (optTotalVal.isPresent()) {
					totalValue = optTotalVal.get();
					if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL) {
						totalValue.setValue(String.valueOf(Integer.parseInt(totalValue.getValue()) + Integer.parseInt(actualValue.getValue())));
					}
				}
				else {
					totalValue = new TotalValue();
					totalValue.setAttendanceId(actualValue.getAttendanceId());
					if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL)
						totalValue.setValue(actualValue.getValue());
					lstTotalValue.add(totalValue);
				}
			});
		});
	}
	
	public void calculateGrossTotalByDate(DailyReportData dailyReportData) {
		List<WorkplaceDailyReportData> lstWorkplaceDailyReportData = dailyReportData.getLstDailyReportData();
		List<TotalValue> lstGrossTotal = dailyReportData.getListTotalValue();
		
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
					if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL)
						totalValue.setValue(totalVal.getValue());
					lstGrossTotal.add(totalValue);
				}
			});
		});
	}
	
	/**
	 * Find workplace.
	 *
	 * @param employeeId the employee id
	 * @param rootWorkplace the root workplace
	 * @param baseDate the base date
	 * @return the workplace report data
	 */
	public WorkplaceReportData findWorkplace(String employeeId, WorkplaceReportData rootWorkplace, GeneralDate baseDate) {
		Map<String, WorkplaceReportData> mapWorkplaceInfo = rootWorkplace.getLstChildWorkplaceReportData();
		WkpHistImport workplaceImport = workplaceAdapter.findWkpBySid(employeeId, baseDate);
		WorkplaceHierarchy code = workplaceConfigRepository.find(AppContexts.user().companyId(), baseDate, workplaceImport.getWorkplaceId()).get().getLstWkpHierarchy().get(0);
		if (mapWorkplaceInfo.containsKey(code.getHierarchyCode().v())) {
			return mapWorkplaceInfo.get(code.getHierarchyCode().v());
		}
		else {
			for (Map.Entry<String, WorkplaceReportData> keySet : mapWorkplaceInfo.entrySet()) {
				WorkplaceReportData data = findWorkplace(employeeId, keySet.getValue(), baseDate);
				if (data != null)
					return data;
			}
		}
		return null;
	}
	
	/**
	 * Find workplace.
	 *
	 * @param employeeId the employee id
	 * @param rootWorkplace the root workplace
	 * @param baseDate the base date
	 * @return the workplace report data
	 */
	public DailyWorkplaceData findWorkplace(String employeeId, DailyWorkplaceData rootWorkplace, GeneralDate baseDate) {
		
		Map<String, DailyWorkplaceData> mapWorkplaceInfo = rootWorkplace.getLstChildWorkplaceData();
		WkpHistImport workplaceImport = workplaceAdapter.findWkpBySid(employeeId, baseDate);
		WorkplaceHierarchy code = workplaceConfigRepository.find(AppContexts.user().companyId(), baseDate, workplaceImport.getWorkplaceId()).get().getLstWkpHierarchy().get(0);
		if (mapWorkplaceInfo.containsKey(code.getHierarchyCode().v())) {
			return mapWorkplaceInfo.get(code.getHierarchyCode().v());
		}
		else {
			for (Map.Entry<String, DailyWorkplaceData> keySet : mapWorkplaceInfo.entrySet()) {
				DailyWorkplaceData data = findWorkplace(employeeId, keySet.getValue(), baseDate);
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
	public Map<String, WorkplaceInfo> collectWorkplaceHierarchy(String workplaceId, GeneralDate baseDate) {
		Map<String, WorkplaceInfo> lstWorkplace = new TreeMap<>();
		Optional<WorkplaceConfigInfo> optHierachyInfo = workplaceConfigRepository.find(AppContexts.user().companyId(), baseDate, workplaceId);
		if (optHierachyInfo.isPresent()) {
			List<WorkplaceHierarchy> lstHierarchy = optHierachyInfo.get().getLstWkpHierarchy();
			for (WorkplaceHierarchy hierarchy: lstHierarchy) {
				WorkplaceInfo workplace = workplaceInfoRepository.findByWkpId(hierarchy.getWorkplaceId(), baseDate).get();
				lstWorkplace.put(hierarchy.getHierarchyCode().v(), workplace);
				
				// Recursive
				if (!StringUtils.equals(workplace.getWorkplaceId(), workplaceId)) {
					collectWorkplaceHierarchy(workplace.getWorkplaceId(), baseDate);
				}
			}
		}
		return lstWorkplace;
	}
	
	/**
	 * Creates the workplace hierarchy report data.
	 *
	 * @param lstWorkplace the lst workplace
	 * @param lstWorkplaceData the lst workplace data
	 */
	public void createWorkplaceHierarchyReportData(Map<String, WorkplaceInfo> lstWorkplace, Map<String, WorkplaceReportData> lstWorkplaceData) {
		for (Map.Entry<String, WorkplaceInfo> entry: lstWorkplace.entrySet()) {
			String hierarchyCode = entry.getKey();
			WorkplaceInfo info = entry.getValue();
			int level = hierarchyCode.length() / 3;
			
			WorkplaceReportData data = lstWorkplaceData.get(hierarchyCode);
			if (data == null) {
				data = new WorkplaceReportData();
				data.workplaceCode = hierarchyCode;
				data.workplaceName = info.getWorkplaceName().v();
				data.level = level;
			}
		}
	}
	
	/**
	 * Analyze info.
	 *
	 * @param lstWorkplace the lst workplace
	 * @param parent the parent
	 */
	private void analyzeInfo(Map<String, WorkplaceInfo> lstWorkplace, WorkplaceReportData parent, List<String> lstAddedWorkplace) {
		parent.lstChildWorkplaceReportData = new TreeMap<>();
		int keyLen = parent.workplaceCode.length() + 3;
		lstWorkplace.keySet().forEach((k) -> {
			WorkplaceInfo wpi = lstWorkplace.get(k);
			String wCode = k;
			if (wCode.length() == keyLen &&  k.indexOf(parent.workplaceCode) == 0) {
				WorkplaceReportData wrp = new WorkplaceReportData();
				wrp.workplaceCode = wCode;
				wrp.workplaceName = wpi.getWorkplaceName().v();
				wrp.lstEmployeeReportData = new ArrayList<>();
				wrp.level = k.length() / 3;
				wrp.parent = parent;
				parent.lstChildWorkplaceReportData.put(wrp.workplaceCode, wrp);
				
				lstAddedWorkplace.add(wCode);
			}
		});
		
		parent.lstChildWorkplaceReportData.keySet().forEach((key) -> {
			analyzeInfo(lstWorkplace, parent.lstChildWorkplaceReportData.get(key), lstAddedWorkplace);
		});
	}
	
	/**
	 * Analyze info.
	 *
	 * @param lstWorkplace the lst workplace
	 * @param parent the parent
	 */
	private void analyzeInfo(Map<String, WorkplaceInfo> lstWorkplace, DailyWorkplaceData parent, List<String> lstAddedWorkplace) {
		parent.lstChildWorkplaceData = new TreeMap<>();
		int keyLen = parent.workplaceCode.length() + 3;
		lstWorkplace.keySet().forEach((k) -> {
			WorkplaceInfo wpi = lstWorkplace.get(k);
			String wCode = k;
			if (wCode.length() == keyLen &&  k.indexOf(parent.workplaceCode) == 0) {
				DailyWorkplaceData wrp = new DailyWorkplaceData();
				wrp.workplaceCode = wCode;
				wrp.workplaceName = wpi.getWorkplaceName().v();
				wrp.lstDailyPersonalData = new ArrayList<>();
				wrp.level = k.length() / 3;
				wrp.parent = parent;
				parent.lstChildWorkplaceData.put(wrp.workplaceCode, wrp);
				lstAddedWorkplace.add(wCode);
			}
		});
		
		parent.lstChildWorkplaceData.keySet().forEach((key) -> {
			analyzeInfo(lstWorkplace, parent.lstChildWorkplaceData.get(key), lstAddedWorkplace);
		});
	}
	
	/**
	 * Collect header data.
	 *
	 * @param headerData the header data
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
		
		List<Integer> lstAttendanceId = lstItem.stream().map(x -> x.getAttendanceDisplay()).collect(Collectors.toList());
		List<DailyAttendanceItem> lstDailyAttendanceItem = attendanceNameService.getNameOfDailyAttendanceItem(lstAttendanceId);
		headerData.lstOutputItemSettingCode = lstDailyAttendanceItem.stream().map(x -> x.getAttendanceItemName()).collect(Collectors.toList());
	}
	
	/**
	 * Write header data.
	 *
	 * @param data the data
	 * @param sheet the sheet
	 * @param reportData the report data
	 */
	public void writeHeaderData(WorkScheduleOutputQuery query, Worksheet sheet, DailyPerformanceReportData reportData) {
		PageSetup pageSetup = sheet.getPageSetup();
		pageSetup.setHeader(0, "&8 " + reportData.getHeaderData().companyName);
		
		if (query.getCondition().getOutputType() == FormOutputType.BY_DATE) {
			Cells cells = sheet.getCells();
			Cell periodCell = cells.get(0,0);
			
			DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyy/M/d (E)", Locale.JAPAN);
			String periodStr = WorkScheOutputConstants.PERIOD + " " + query.getStartDate().toLocalDate().format(jpFormatter) + " ～ " + query.getEndDate().toLocalDate().format(jpFormatter);
			periodCell.setValue(periodStr);
		}
	}
	
	/**
	 * Set all fixed text.
	 *
	 * @param workbook the new fixed data
	 * @param reportData the report data
	 */
	public void setFixedData(WorkScheduleOutputCondition condition, Worksheet sheet, DailyPerformanceReportData reportData, int currentRow) {
		DailyPerformanceHeaderData headerData = reportData.getHeaderData();
		// Set fixed value
		Cells cells = sheet.getCells();
		
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			Cell erAlCell = cells.get(currentRow, 0);
			erAlCell.setValue(headerData.getFixedHeaderData().get(0));
			
			Cell dateCell = cells.get(currentRow, 1);
			dateCell.setValue(headerData.getFixedHeaderData().get(1));
			
			Cell dayMonCell = cells.get(currentRow + 1, 1);
			dayMonCell.setValue(headerData.getFixedHeaderData().get(2));
			
			Cell dayCell = cells.get(currentRow + 1, 2);
			dayCell.setValue(headerData.getFixedHeaderData().get(3));
			
			Cell remarkCell = cells.get(currentRow, 35);
			remarkCell.setValue(headerData.getFixedHeaderData().get(4));
		}
		else {
			Cell erAlCell = cells.get(currentRow, 0);
			erAlCell.setValue(headerData.getFixedHeaderData().get(0));
			
			Cell dateCell = cells.get(currentRow, 1);
			dateCell.setValue(headerData.getFixedHeaderData().get(1));
			
			Cell remarkCell = cells.get(currentRow, 35);
			remarkCell.setValue(headerData.getFixedHeaderData().get(2));
		}
		
	}
	
	/**
	 * Write display map (48 cells max).
	 *
	 * @param cells cell map
	 * @param reportData the report data
	 */
	public void writeDisplayMap(Cells cells, DailyPerformanceReportData reportData, int currentRow, int nSize) {
		List<String> lstItem = reportData.getHeaderData().getLstOutputItemSettingCode();
		
		// Divide list into smaller lists (max 16 items)
		int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);

        for(int i = 0; i < numOfChunks; i++) {
            int start = i * CHUNK_SIZE;
            int length = Math.min(lstItem.size() - start, CHUNK_SIZE);

            List<String> lstItemRow = lstItem.subList(start, start + length);
            
            for (int j = 0; j < length; j++) {
            	// Column 4, 6, 8,...
            	// Row 3, 4, 5
            	Cell cell = cells.get(currentRow + i, DATA_COLUMN_INDEX[0] + j * 2); 
            	cell.setValue(lstItemRow.get(j));
            }
        }
	}
	
	/**
	 * Write detail work schedule.
	 *
	 * @param currentRow the current row
	 * @param workbook the workbook
	 * @param workplaceReportData the workplace report data
	 * @param dataRowCount the data row count
	 * @param condition the condition
	 * @throws Exception the exception
	 */
	public int writeDetailedWorkSchedule(int currentRow, WorksheetCollection templateSheetCollection, Worksheet sheet, WorkplaceReportData workplaceReportData, int dataRowCount, WorkScheduleOutputCondition condition) throws Exception {
		Cells cells = sheet.getCells();
		
		List<EmployeeReportData> lstEmployeeReportData = workplaceReportData.getLstEmployeeReportData();
		
		// Root workplace won't have employee
		if (lstEmployeeReportData != null && lstEmployeeReportData.size() > 0) {
			for (EmployeeReportData employeeReportData: lstEmployeeReportData) {
				Range workplaceRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WORKPLACE_ROW);
				Range workplaceRange = cells.createRange(currentRow, 0, 1, 39);
				workplaceRange.copy(workplaceRangeTemp);
				
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
				if (condition.getSettingDetailTotalOutput().isDetails()) {
					List<DetailedDailyPerformanceReportData> lstDetailedDailyPerformance = employeeReportData.getLstDetailedPerformance();
					for (DetailedDailyPerformanceReportData detailedDailyPerformanceReportData: lstDetailedDailyPerformance) {
						Range dateRangeTemp;
						
						if (colorWhite) // White row
							dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + dataRowCount);
						else // Light blue row
							dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + dataRowCount);
						Range dateRange = cells.createRange(currentRow, 0, dataRowCount, 39);
						dateRange.copy(dateRangeTemp);
						
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
	
				        for(int i = 0; i < numOfChunks; i++) {
				            int start = i * CHUNK_SIZE;
				            int length = Math.min(lstItem.size() - start, CHUNK_SIZE);
	
				            List<ActualValue> lstItemRow = lstItem.subList(start, start + length);
				            
				            int curRow = currentRow;
				            
				            for (int j = 0; j < length; j++) {
				            	// Column 4, 6, 8,...
				            	// Row 3, 4, 5
				            	ActualValue actualValue = lstItemRow.get(j);
				            	Cell cell = cells.get(curRow, DATA_COLUMN_INDEX[0] + j * 2); 
				            	cell.setValue(actualValue.getValue());
				            }
				            
				            curRow++;
				        }
				        
				        // A5_5
				        Cell remarkCell = cells.get(currentRow,35);
				        remarkCell.setValue(detailedDailyPerformanceReportData.getErrorDetail());
				        
				        currentRow++;
				        colorWhite = !colorWhite; // Change to other color
					}
				}
				
				// Personal total
				if (condition.getSettingDetailTotalOutput().isPersonalTotal()) {
					// A6_1
					Range personalTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
					Range personalTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
					personalTotalRange.copy(personalTotalRangeTemp);
					
					Cell personalTotalCellTag = cells.get(currentRow, 0);
					personalTotalCellTag.setValue(WorkScheOutputConstants.PERSONAL_TOTAL);
					
					// A6_2
					int numOfChunks = (int) Math.ceil( (double) employeeReportData.getMapPersonalTotal().size() / CHUNK_SIZE);
					
			        for(int i = 0; i < numOfChunks; i++) {
			            int start = i * CHUNK_SIZE;
			            int length = Math.min(employeeReportData.getMapPersonalTotal().size() - start, CHUNK_SIZE);
	
			            List<String> lstItemRow = employeeReportData.getMapPersonalTotal().values().stream().collect(Collectors.toList()).subList(start, start + length);
			            
			            for (int j = 0; j < length; j++) {
			            	String value = lstItemRow.get(j);
			            	if (value != "0") {
				            	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
				            	cell.setValue(value);
			            	}
			            }
			            currentRow++;
			        }
				}
		        
				// Total count day
		        if (condition.getSettingDetailTotalOutput().isTotalNumberDay()) {
			        // A7_1
			        Range dayCountRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DAYCOUNT_ROW);
					Range dayCountRange = cells.createRange(currentRow, 0, 2, 39);
					dayCountRange.copy(dayCountRangeTemp);
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
				if (condition.getPageBreakIndicator() == PageBreakIndicator.EMPLOYEE)
					sheet.getHorizontalPageBreaks().add(currentRow);
			}
		}
		
		// Process to child workplace
		Map<String, WorkplaceReportData> mapChildWorkplace = workplaceReportData.getLstChildWorkplaceReportData();
		for (Map.Entry<String, WorkplaceReportData> entry: mapChildWorkplace.entrySet()) {
			currentRow = writeDetailedWorkSchedule(currentRow, templateSheetCollection, sheet, entry.getValue(), dataRowCount, condition);
		}
		
		// Skip writing total for root, use gross total instead
		if (lstEmployeeReportData != null && lstEmployeeReportData.size() > 0 && condition.getSettingDetailTotalOutput().isWorkplaceTotal()) {
			// A8_1
			Range workplaceTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range workplaceTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
			workplaceTotalRange.copy(workplaceTotalRangeTemp);
			Cell workplaceTotalCellTag = cells.get(currentRow, 0);
			workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_TOTAL);
			
			// A8_2
			WorkplaceTotal workplaceTotal = workplaceReportData.getWorkplaceTotal();
			int numOfChunks = (int) Math.ceil( (double) workplaceTotal.getTotalWorkplaceValue().size() / CHUNK_SIZE);
			
	        for(int i = 0; i < numOfChunks; i++) {
	            int start = i * CHUNK_SIZE;
	            int length = Math.min(workplaceTotal.getTotalWorkplaceValue().size() - start, CHUNK_SIZE);
	
	            List<TotalValue> lstItemRow = workplaceTotal.getTotalWorkplaceValue().subList(start, start + length);
	            
	            for (int j = 0; j < length; j++) {
	            	String value = lstItemRow.get(j).getValue();
	            	if (value != "0") {
		            	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
		            	cell.setValue(value);
	            	}
	            }
	            currentRow++;
	        }
		}
		
		/**
		 * A9 - A13
		 */
		// A9_1 - A13_1
		//if (condition.getSettingDetailTotalOutput().isGrossTotal() && condition.getSettingDetailTotalOutput().getWorkplaceHierachyTotal().checkLevelEnabled(workplaceReportData.getLevel())) {
			Range wkpHierTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range wkpHierTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
			wkpHierTotalRange.copy(wkpHierTotalRangeTemp);
			Cell workplaceTotalCellTag = cells.get(currentRow, 0);
			int level = workplaceReportData.getLevel();
			if (level != 0)
				workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_HIERARCHY_TOTAL + workplaceReportData.getLevel());
			else
				workplaceTotalCellTag.setValue(WorkScheOutputConstants.GROSS_TOTAL);
			
			// A9_2 - A13_2
			int numOfChunks = (int) Math.ceil( (double) workplaceReportData.getGrossTotal().size() / CHUNK_SIZE);
			
	        for(int i = 0; i < numOfChunks; i++) {
	            int start = i * CHUNK_SIZE;
	            int length = Math.min(workplaceReportData.getGrossTotal().size() - start, CHUNK_SIZE);
	
	            List<TotalValue> lstItemRow = workplaceReportData.getGrossTotal().subList(start, start + length);
	            
	            for (int j = 0; j < length; j++) {
	            	String value = lstItemRow.get(j).getValue();
	            	if (value != "0") {
		            	Cell cell = cells.get(currentRow + i, DATA_COLUMN_INDEX[0] + j * 2); 
		            	cell.setValue(value);
	            	}
	            }
	        }
		//}
		
		// Page break by workplace
		if (condition.getPageBreakIndicator() == PageBreakIndicator.WORKPLACE)
			sheet.getHorizontalPageBreaks().add(currentRow);
        
        currentRow++;
		
		return currentRow;
	}
	
	/**
	 * Write detailed daily schedule.
	 *
	 * @param currentRow the current row
	 * @param workbook the workbook
	 * @param lstDailyReportData the lst daily report data
	 * @param dataRowCount the data row count
	 * @throws Exception the exception
	 */
	public void writeDetailedDailySchedule(int currentRow, WorksheetCollection templateSheetCollection, Worksheet sheet, DailyReportData dailyReport, int dataRowCount, WorkScheduleOutputCondition condition) throws Exception {
		Cells cells = sheet.getCells();
		
		List<WorkplaceDailyReportData> lstDailyReportData = dailyReport.getLstDailyReportData();
		
		for (WorkplaceDailyReportData dailyReportData: lstDailyReportData) {
			Range dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DATE_ROW);
			Range dateRange = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
			dateRange.copy(dateRangeTemp);
			dateRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
			
			// B3_1
			Cell dateTagCell = cells.get(currentRow, 0);
			dateTagCell.setValue(WorkScheOutputConstants.DATE_BRACKET);
			
			// B3_2
			DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("MM/dd (E)", Locale.JAPAN);
			String date = dailyReportData.getDate().toLocalDate().format(jpFormatter);
			Cell dateCell = cells.get(currentRow, 2);
			dateCell.setValue(date);
			
			currentRow++;
			
			DailyWorkplaceData rootWorkplace = dailyReportData.getLstWorkplaceData();
			currentRow = writeDailyDetailedPerformanceDataOnWorkplace(currentRow, sheet, templateSheetCollection, rootWorkplace, dataRowCount, condition);
		
			// Page break (regardless of setting, see example template sheet ★ 日別勤務表-日別3行-1
			sheet.getHorizontalPageBreaks().add(currentRow);
		}
		
		// Gross total after all the rest of the data
		Range wkpGrossTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
		Range wkpGrossTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
		wkpGrossTotalRange.copy(wkpGrossTotalRangeTemp);
		
		Cell grossTotalCellTag = cells.get(currentRow, 0);
		grossTotalCellTag.setValue(WorkScheOutputConstants.GROSS_TOTAL);
		
		writeGrossTotal(currentRow, dailyReport.getListTotalValue(), cells);
	}
	
	/**
	 * Write daily detailed performance data on workplace.
	 *
	 * @param currentRow the current row
	 * @param cells the cells
	 * @param templateSheetCollection the template sheet collection
	 * @param rootWorkplace the root workplace
	 * @param dataRowCount the data row count
	 * @throws Exception the exception
	 */
	private int writeDailyDetailedPerformanceDataOnWorkplace(int currentRow, Worksheet sheet, WorksheetCollection templateSheetCollection, DailyWorkplaceData rootWorkplace, int dataRowCount, WorkScheduleOutputCondition condition) throws Exception {
		Cells cells = sheet.getCells();
		Range workplaceRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DATE_ROW);
		Range workplaceRange = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
		workplaceRange.copy(workplaceRangeTemp);
		boolean colorWhite = true; // true = white, false = light blue, start with white row
		
		List<DailyPersonalPerformanceData> employeeReportData = rootWorkplace.getLstDailyPersonalData();
		if (employeeReportData != null) {
			// B4_1
			Cell workplaceTagCell = cells.get(currentRow, 0);
			workplaceTagCell.setValue(WorkScheOutputConstants.WORKPLACE);
			
			// B4_2
			Cell workplaceCell = cells.get(currentRow, 2);
			workplaceCell.setValue(rootWorkplace.getWorkplaceCode() + " " + rootWorkplace.getWorkplaceName());
			
			// Remove top border
			Range borderRange = cells.createRange(currentRow, 7, 1, 17);
			borderRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getEmpty());
			
			currentRow++;
			
			// Employee data
			for (DailyPersonalPerformanceData employee : employeeReportData){
				Range dateRangeTemp;
				if (colorWhite) // White row
					dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + dataRowCount);
				else // Light blue row
					dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + dataRowCount);
				Range dateRange = cells.createRange(currentRow, 0, dataRowCount, DATA_COLUMN_INDEX[5]);
				dateRange.copy(dateRangeTemp);
				
				// B5_1
				Cell erAlMark = cells.get(currentRow, 0);
				erAlMark.setValue(employee.getErrorAlarmCode());
				
				// B5_2
				Cell employeeCell = cells.get(currentRow, 1);
				employeeCell.setValue(employee.getEmployeeName());
				
				Range employeeRange = cells.createRange(currentRow, 1, 1, 2);
				employeeRange.merge();
				
				// B5_3
				List<ActualValue> lstItem = employee.getActualValue();
				
				// Divide list into smaller lists (max 16 items)
				int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);
	
		        for(int i = 0; i < numOfChunks; i++) {
		            int start = i * CHUNK_SIZE;
		            int length = Math.min(lstItem.size() - start, CHUNK_SIZE);
	
		            List<ActualValue> lstItemRow = lstItem.subList(start, start + length);
		            int curRow = currentRow;
		            
		            for (int j = 0; j < length; j++) {
		            	// Column 4, 6, 8,...
		            	// Row 3, 4, 5
		            	ActualValue actualValue = lstItemRow.get(j);
		            	Cell cell = cells.get(curRow, DATA_COLUMN_INDEX[0] + j * 2); 
		            	cell.setValue(actualValue.getValue());
		            }
		            
		            curRow++;
		        }
		        
		        // B5_4
		        Cell remarkCell = cells.get(currentRow,35);
		        remarkCell.setValue(employee.getDetailedErrorData());
		        currentRow++;
		        colorWhite = !colorWhite; // Change to other color
		        
		        // Page break by employee
				if (condition.getPageBreakIndicator() == PageBreakIndicator.EMPLOYEE)
					sheet.getHorizontalPageBreaks().add(currentRow);
			}
		}
		
		// Page break by workplace
		if (condition.getPageBreakIndicator() == PageBreakIndicator.WORKPLACE)
			sheet.getHorizontalPageBreaks().add(currentRow);
        
		// Child workplace
		Map<String, DailyWorkplaceData> mapChildWorkplace = rootWorkplace.getLstChildWorkplaceData();
		for (Map.Entry<String, DailyWorkplaceData> entry: mapChildWorkplace.entrySet()) {
			currentRow = writeDailyDetailedPerformanceDataOnWorkplace(currentRow, sheet, templateSheetCollection, entry.getValue(), dataRowCount, condition);
		}
		
		// Workplace total, root workplace use gross total instead
		if (condition.getSettingDetailTotalOutput().isWorkplaceTotal() && rootWorkplace.getLevel() != 0) {
			Range workplaceTotalTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range workplaceTotal = cells.createRange(currentRow, 0, dataRowCount, DATA_COLUMN_INDEX[5]);
			workplaceTotal.copy(workplaceTotalTemp);
			workplaceTotal.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
			workplaceTotal.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.DOUBLE, Color.getBlack());
			
			// B6_1
			Cell workplaceTotalCellTag = cells.get(currentRow, 0);
			workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_TOTAL);
			
			// B6_2
			currentRow = writeWorkplaceTotal(currentRow, rootWorkplace, cells);
		}
		
		// Workplace hierarchy total
		int level = rootWorkplace.getLevel();
		if (level != 0) {
			Range wkpHierTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
			Range wkpHierTotalRange = cells.createRange(currentRow, 0, dataRowCount, 39);
			wkpHierTotalRange.copy(wkpHierTotalRangeTemp);
			
			// B7_1 - B11_1
			Cell workplaceTotalCellTag = cells.get(currentRow, 0);
			workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_HIERARCHY_TOTAL + rootWorkplace.getLevel());
			
			// B7_2 - B11_2
			currentRow = writeWorkplaceTotal(currentRow, rootWorkplace, cells);
		}
		
		return currentRow;
	}

	/**
	 * Write workplace total for export type by date
	 * @param currentRow
	 * @param rootWorkplace
	 * @param cells
	 * @return
	 */
	private int writeWorkplaceTotal(int currentRow, DailyWorkplaceData rootWorkplace, Cells cells) {
		List<TotalValue> totalWorkplaceValue = rootWorkplace.getWorkplaceTotal().getTotalWorkplaceValue();
		int numOfChunks = (int) Math.ceil( (double) totalWorkplaceValue.size() / CHUNK_SIZE);
		
		for(int i = 0; i < numOfChunks; i++) {
		    int start = i * CHUNK_SIZE;
		    int length = Math.min(totalWorkplaceValue.size() - start, CHUNK_SIZE);

		    List<TotalValue> lstItemRow = totalWorkplaceValue.stream().collect(Collectors.toList()).subList(start, start + length);
		    
		    for (int j = 0; j < length; j++) {
		    	String value = lstItemRow.get(j).getValue();
		    	if (value != "0") {
		        	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
		        	cell.setValue(value);
		    	}
		    }
		    currentRow++;
		}
		
		if (numOfChunks == 0) currentRow++;
		
		return currentRow;
	}
	
	private int writeGrossTotal(int currentRow, List<TotalValue> lstGrossTotal, Cells cells) {
		int numOfChunks = (int) Math.ceil( (double) lstGrossTotal.size() / CHUNK_SIZE);
		
		for(int i = 0; i < numOfChunks; i++) {
		    int start = i * CHUNK_SIZE;
		    int length = Math.min(lstGrossTotal.size() - start, CHUNK_SIZE);

		    List<TotalValue> lstItemRow = lstGrossTotal.stream().collect(Collectors.toList()).subList(start, start + length);
		    
		    for (int j = 0; j < length; j++) {
		    	String value = lstItemRow.get(j).getValue();
		    	if (value != "0") {
		        	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
		        	cell.setValue(value);
		    	}
		    }
		    currentRow++;
		}
		
		if (numOfChunks == 0) currentRow++;
		
		return currentRow;
	}
	
	/**
	 * Create print area of the entire report, important for exporting to PDF
	 * @param currentRow
	 */
	public void createPrintArea(int currentRow, Worksheet sheet) {
		// Get the last column and row name
		Cells cells = sheet.getCells();
		Cell lastCell = cells.get(currentRow, 39);
		String lastCellName = lastCell.getName();
		
		PageSetup pageSetup = sheet.getPageSetup();
		pageSetup.setPrintArea("A1:" + lastCellName);
	}
	
	/**
	 * Gets the remark content.
	 *
	 * @param employeeId the employee id
	 * @param currentDate the current date
	 * @param errorList the error list
	 * @param choice the choice
	 * @return the remark content
	 */
	public Optional<PrintRemarksContent> getRemarkContent(String employeeId, GeneralDate currentDate, List<EmployeeDailyPerError> errorList, RemarksContentChoice choice, List<ErrorAlarmWorkRecordCode> lstOutputErrorCode) {
		// 遅刻早退
		PrintRemarksContent printRemarksContent = null;
		if (errorList.size() > 0 && (lstOutputErrorCode.contains(SystemFixedErrorAlarm.LEAVE_EARLY.value) || lstOutputErrorCode.contains(SystemFixedErrorAlarm.LATE.value))) {
			Optional<EmployeeDailyPerError> optError = errorList.stream()
					.filter(x -> x.getErrorAlarmWorkRecordCode().v().contains(SystemFixedErrorAlarm.LEAVE_EARLY.value) || x.getErrorAlarmWorkRecordCode().v().contains(SystemFixedErrorAlarm.LATE.value)).findFirst();
			if (optError.isPresent() && (choice == RemarksContentChoice.LEAVING_EARLY)) {
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
		
		return printRemarksContent == null? Optional.empty() : Optional.of(printRemarksContent);
	}
}
