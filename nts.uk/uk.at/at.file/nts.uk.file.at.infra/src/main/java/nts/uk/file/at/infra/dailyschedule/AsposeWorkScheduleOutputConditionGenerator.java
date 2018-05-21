package nts.uk.file.at.infra.dailyschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmClassification;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeAdapterInforFinder;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;
import nts.uk.file.at.app.export.dailyschedule.AttendanceResultImportAdapter;
import nts.uk.file.at.app.export.dailyschedule.FormOutputType;
import nts.uk.file.at.app.export.dailyschedule.TotalDayCountWs;
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
import nts.uk.file.at.app.export.dailyschedule.data.WorkplaceReportData;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalCountDay;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalSumRowOfDailySchedule;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;
import nts.uk.file.at.app.export.dailyschedule.totalsum.WorkplaceTotal;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.context.AppContexts;
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
	private ErrorAlarmWorkRecordAdapter errorAlarmWorkRecordAdapter;
	
	/** The edit state finder. */
	@Inject
	private EditStateOfDailyPerformanceFinder editStateFinder;
	
	/** The person repository. */
	@Inject
	private PersonRepository personRepository;
	
	/** The workplace adapter. */
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	/** The employment repository. */
	@Inject
	private EmploymentRepository employmentRepository;
	
	/** The output item repo. */
	@Inject
	private OutputItemDailyWorkScheduleRepository outputItemRepo;
	
	/** The finder. */
	@Inject
	private EmployeeAdapterInforFinder finder;
	
	/** The filename. */
	private final String filename = "report/KWR001.xlsx";
	
	/** The Constant CHUNK_SIZE. */
	private static final int CHUNK_SIZE = 16;
	
	/** The Constant MAX_ITEM_ROW. */
	private static final int MAX_ITEM_ROW = 3;
	
	/** The Constant ITEM_DISPLAY_PREFIX. */
	private static final String ITEM_DISPLAY_PREFIX = "ItemDisplay";
	
	/** The Constant DATA_COLUMN_INDEX. */
	private static final int[] DATA_COLUMN_INDEX = {3, 8, 10, 14, 16, 39};
	
	/** The Constant DAY_COUNT_COLUMN_INDEX. */
	private static final int[] DAY_COUNT_COLUMN_INDEX = {3, 5, 7, 9, 11, 13, 15, 17, 19};
	
	/* (non-Javadoc)
	 * @see nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator#generate(nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition, nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo, nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery)
	 */
	@Override
	public void generate(WorkScheduleOutputQuery query) {
		WorkScheduleOutputCondition condition = query.getCondition();
		
		// Dummy area
		condition.setOutputType(FormOutputType.BY_EMPLOYEE);
		
		// ドメインモデル「日別勤務表の出力項目」を取得する
		Optional<OutputItemDailyWorkSchedule> optOutputItemDailyWork = outputItemRepo.findByCidAndCode(AppContexts.user().companyId(), query.getCondition().getCode().v());
		if (!optOutputItemDailyWork.isPresent()) {
			throw new BusinessException(new RawErrorMessage("Msg_1141"));
		}
		
		List<AttendanceItemsDisplay> lstAttendanceDisplay = optOutputItemDailyWork.get().getLstDisplayedAttendance();
		
		Workbook workbook;
		try {
			workbook = new Workbook(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
			
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
			
			// Write header data
			writeHeaderData(condition, sheet, reportData);
			 	
			// Set all fixed cell
			setFixedData(sheet, reportData, currentRow);
			
			// Write display map
			writeDisplayMap(sheet.getCells(),reportData, currentRow, nSize);
			
			currentRow+=nSize*2;
			
			// Write detailed work schedule
			writeDetailedWorkSchedule(currentRow, workbook, reportData.getWorkplaceReportData(), nSize, condition);
			
			// Process designer
			designer.process(false);
			
			// Save workbook
			workbook.save("D:/Dummy/testKWR.xlsx");
			System.out.println("Done");
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
		collectHeaderData(reportData.getHeaderData());
		collectDisplayMap(reportData.getHeaderData(), outputItem);
		
		Map<String, WorkplaceInfo> lstWorkplace = new TreeMap<>(); // Automatically sort by code, will need to check hierarchy later
		List<String> lstWorkplaceId = new ArrayList<>();
		
		for (String employeeId: query.getEmployeeId()) {
			WkpHistImport workplaceImport = workplaceAdapter.findWkpBySid(employeeId, query.getEndDate());
			lstWorkplaceId.add(workplaceImport.getWorkplaceId());
		}
		
		// Collect child workplace, automatically sort into tree map
		for (String entry: lstWorkplaceId) {
			lstWorkplace = collectWorkplaceHierarchy(entry, query.getEndDate());
		}
		
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			WorkplaceReportData data = new WorkplaceReportData();
			data.workplaceCode = "";
			reportData.setWorkplaceReportData(data);
		}
		else {
			DateRange dateRange = new DateRange(query.getStartDate(), query.getEndDate());
			List<DailyReportData> lstReportData = dateRange.toListDate().stream().map(x -> {
				DailyReportData dailyReportData = new DailyReportData();
				dailyReportData.lstWorkplaceData = new DailyWorkplaceData();
				dailyReportData.lstWorkplaceData.workplaceCode = "";
				dailyReportData.lstWorkplaceData.workplaceName = "AAA";
				dailyReportData.date = x;
				return dailyReportData;
			}).collect(Collectors.toList());
			reportData.setLstDailyReportData(lstReportData);
		}
		
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			List<String> lstAddedCode = new ArrayList<>();
			analyzeInfo(lstWorkplace, reportData.getWorkplaceReportData(), lstAddedCode);
			
			for (Map.Entry<String, WorkplaceInfo> entry: lstWorkplace.entrySet()) {
				if (!lstAddedCode.contains(entry.getKey())) {
					WorkplaceReportData wrp = new WorkplaceReportData();
					WorkplaceInfo info = entry.getValue();
					wrp.workplaceCode = info.getWorkplaceCode().v();
					wrp.workplaceName = info.getWorkplaceName().v();
					wrp.lstEmployeeReportData = new ArrayList<>();
					wrp.lstChildWorkplaceReportData = new TreeMap<>();
					
					reportData.getWorkplaceReportData().lstChildWorkplaceReportData.put(entry.getKey(), wrp);
				}
			}
		} else {
			final Map<String, WorkplaceInfo> lstWorkplaceTemp = lstWorkplace;
			reportData.getLstDailyReportData().forEach(x -> {
				analyzeInfo(lstWorkplaceTemp, x.getLstWorkplaceData());
			});
		}
		
		List<AttendanceResultImport> lstAttendanceResultImport = attendaceAdapter.getValueOf(query.getEmployeeId(), new DatePeriod(query.getStartDate(), query.getEndDate()), AttendanceResultImportAdapter.convertList(outputItem.getLstDisplayedAttendance(), x -> x.getAttendanceDisplay()));
		if (condition.getOutputType() == FormOutputType.BY_EMPLOYEE) {
			for (String employeeId: query.getEmployeeId()) {
				EmployeeReportData employeeData = collectedEmployeePerformanceData(reportData, query, lstAttendanceResultImport, employeeId);
				
				// Calculate total count day
				totalDayCountWs.calculateAllDayCount(employeeId, new DateRange(query.getStartDate(), query.getEndDate()), employeeData.totalCountDay);
				
			}
			
			calculateTotal(reportData.getWorkplaceReportData(), outputItem.getLstDisplayedAttendance());
		}
		else {
			List<GeneralDate> lstDate = new DateRange(query.getStartDate(), query.getEndDate()).toListDate();
			for (GeneralDate date: lstDate) {
				collectedEmployeePerformanceData(reportData, query, lstAttendanceResultImport, date);
			}
			// Calculate workplace total
			reportData.getLstDailyReportData().forEach(dailyData -> {
				calculateTotal(dailyData.getLstWorkplaceData());
			});
		}
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
			List<AttendanceResultImport> lstAttendanceResultImport, GeneralDate date) {
		for (String employeeId: query.getEmployeeId()) {
			DailyWorkplaceData dailyWorkplaceData = findWorkplace(employeeId, reportData.getLstDailyReportData().stream().filter(x -> x.getDate().equals(date)).findFirst().get().getLstWorkplaceData(), query.getEndDate());
			if (dailyWorkplaceData == null) continue;
			DailyPersonalPerformanceData personalPerformanceDate = new DailyPersonalPerformanceData();
			personalPerformanceDate.setEmployeeName("AAA"); // TODO: Employee name
			
			lstAttendanceResultImport.stream().filter(x -> x.getEmployeeId().equals(employeeId)).sorted((o1,o2) -> o1.getWorkingDate().compareTo(o2.getWorkingDate())).forEach(x -> {
				// Error alarm code
				List<EmployeeDailyPerError> errorList = errorAlarmRepository.find(employeeId, x.getWorkingDate());
				List<Integer> lstRemarkContentStr = new ArrayList<>();
				Optional<PrintRemarksContent> optRemarkContentLeavingEarly = getRemarkContent(employeeId, x.getWorkingDate(), errorList, RemarksContentChoice.LEAVING_EARLY);
				Optional<PrintRemarksContent> optRemarkContentManualInput = getRemarkContent(employeeId, x.getWorkingDate(), errorList, RemarksContentChoice.MANUAL_INPUT);
				if (optRemarkContentLeavingEarly.isPresent())
					lstRemarkContentStr.add(optRemarkContentLeavingEarly.get().getPrintitem().value);
				if (optRemarkContentManualInput.isPresent())
					lstRemarkContentStr.add(optRemarkContentManualInput.get().getPrintitem().value);
				personalPerformanceDate.detailedErrorData = String.join("、", lstRemarkContentStr.stream().map(y -> String.valueOf(y)).collect(Collectors.toList()));
				
				// ER/AL
				boolean erMark = false, alMark = false;
				for (EmployeeDailyPerError error : errorList) {
					ErrorAlarmWorkRecordAdapterDto record = errorAlarmWorkRecordAdapter.findByErrorAlamCheckId(error.getErrorAlarmWorkRecordCode().v());
					if (record.getTypeAtr() == ErrorAlarmClassification.ALARM.value) {
						alMark = true;
					}
					if (record.getTypeAtr() == ErrorAlarmClassification.ERROR.value) {
						erMark = true;
					}
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
				personalPerformanceDate.actualValue = new ArrayList<>();
				x.getAttendanceItems().stream().forEach(a -> personalPerformanceDate.actualValue.add(new ActualValue(a.getItemId(), a.getValue(), a.getValueType())));
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
		employeeData.employeeId = employeeId;
		// TODO: Employee personal data
		employeeData.employeeName = "AAA";
		employeeData.employmentName = "BBB";
		employeeData.position = "Employee";
		employeeData.lstDetailedPerformance = new ArrayList<>();
		workplaceData.lstEmployeeReportData.add(employeeData);
		lstAttendanceResultImport.stream().filter(x -> x.getEmployeeId().equals(employeeId)).sorted((o1,o2) -> o1.getWorkingDate().compareTo(o2.getWorkingDate())).forEach(x -> {
			DetailedDailyPerformanceReportData detailedDate = new DetailedDailyPerformanceReportData();
			detailedDate.setDate(x.getWorkingDate());
			detailedDate.setDayOfWeek(String.valueOf(x.getWorkingDate().dayOfWeek()));
			employeeData.lstDetailedPerformance.add(detailedDate);
			
			// Error alarm code
			List<EmployeeDailyPerError> errorList = errorAlarmRepository.find(employeeId, x.getWorkingDate());
			List<Integer> lstRemarkContentStr = new ArrayList<>();
			Optional<PrintRemarksContent> optRemarkContentLeavingEarly = getRemarkContent(employeeId, x.getWorkingDate(), errorList, RemarksContentChoice.LEAVING_EARLY);
			Optional<PrintRemarksContent> optRemarkContentManualInput = getRemarkContent(employeeId, x.getWorkingDate(), errorList, RemarksContentChoice.MANUAL_INPUT);
			if (optRemarkContentLeavingEarly.isPresent())
				lstRemarkContentStr.add(optRemarkContentLeavingEarly.get().getPrintitem().value);
			if (optRemarkContentManualInput.isPresent())
				lstRemarkContentStr.add(optRemarkContentManualInput.get().getPrintitem().value);
			detailedDate.errorDetail = String.join("、", lstRemarkContentStr.stream().map(y -> String.valueOf(y)).collect(Collectors.toList()));
			
			// Dummy
			detailedDate.errorDetail = "Error";
			
			// ER/AL
			boolean erMark = false, alMark = false;
			for (EmployeeDailyPerError error : errorList) {
				ErrorAlarmWorkRecordAdapterDto record = errorAlarmWorkRecordAdapter.findByErrorAlamCheckId(error.getErrorAlarmWorkRecordCode().v());
				if (record.getTypeAtr() == ErrorAlarmClassification.ALARM.value) {
					alMark = true;
				}
				if (record.getTypeAtr() == ErrorAlarmClassification.ERROR.value) {
					erMark = true;
				}
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
			
			// Dummy
			detailedDate.errorAlarmMark = "ER/AL";
			
			detailedDate.actualValue = new ArrayList<>();
			//x.getAttendanceItems().stream().forEach(a -> detailedDate.actualValue.add(new ActualValue(a.getItemId(), a.getValue(), a.getValueType())));
			x.getAttendanceItems().stream().forEach(a -> detailedDate.actualValue.add(new ActualValue(a.getItemId(), "1", 0)));
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
		String code = workplaceData.getWorkplaceCode();
		// Recursive
		workplaceData.getLstChildWorkplaceReportData().values().forEach(x -> {
			calculateTotal(x, lstAttendanceId);
		});
		// Workplace
		workplaceData.workplaceTotal = new WorkplaceTotal();
		workplaceData.workplaceTotal.setWorkplaceId(workplaceData.getWorkplaceCode());
		List<TotalValue> lstTotalValue = new ArrayList<>();
		workplaceData.workplaceTotal.setTotalWorkplaceValue(lstTotalValue);
		
		// Employee
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
						
						// Integer/Big Decimal
						if (aVal.getValueType() == ActualValue.INTEGER || aVal.getValueType() == ActualValue.BIG_DECIMAL) {
							//int currentValue = Integer.parseInt(employeeData.mapPersonalTotal.get(attdId));
							int currentValue = (int) aVal.value();
							employeeData.mapPersonalTotal.put(attdId, String.valueOf(Integer.parseInt(employeeData.mapPersonalTotal.get(attdId)) + (int) currentValue));
							TotalValue totalVal = lstTotalValue.stream().filter(attendance -> attendance.getAttendanceId() == attdId).findFirst().get();
							totalVal.setValue(String.valueOf(Integer.parseInt(totalVal.getValue()) + currentValue));
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
		
		if (performanceData != null && performanceData.size() > 0) {
			performanceData.forEach(data -> {
				List<ActualValue> lstActualValue = data.getActualValue();
				lstActualValue.forEach(actualValue -> {
					int index = performanceData.indexOf(actualValue);
					TotalValue totalValue;
					if (lstTotalValue.size() - 2 == index) {
						totalValue = new TotalValue();
						totalValue.setAttendanceId(actualValue.getAttendanceId());
						if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL)
							totalValue.setValue(actualValue.getValue());
						lstTotalValue.add(totalValue);
					}
					else {
						totalValue = lstTotalValue.get(index);
						if (totalValue.getValueType() == ActualValue.INTEGER || totalValue.getValueType() == ActualValue.BIG_DECIMAL) {
							totalValue.setValue(String.valueOf(Integer.parseInt(totalValue.getValue()) + Integer.parseInt(actualValue.getValue())));
						}
					}
				});
			});
		}
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
	private void analyzeInfo(Map<String, WorkplaceInfo> lstWorkplace, DailyWorkplaceData parent) {
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
				
				parent.lstChildWorkplaceData.put(wrp.workplaceCode, wrp);
			}
		});
		
		parent.lstChildWorkplaceData.keySet().forEach((key) -> {
			analyzeInfo(lstWorkplace, parent.lstChildWorkplaceData.get(key) );
		});
	}
	
	/**
	 * Collect header data.
	 *
	 * @param headerData the header data
	 */
	public void collectHeaderData(DailyPerformanceHeaderData headerData) {
		headerData.companyName = AppContexts.user().companyCode(); // TODO: change to company name
		headerData.setFixedHeaderData(new ArrayList<>());
		headerData.fixedHeaderData.add(WorkScheOutputConstants.ERAL);
		headerData.fixedHeaderData.add(WorkScheOutputConstants.DATE);
		headerData.fixedHeaderData.add(WorkScheOutputConstants.DAYMONTH);
		headerData.fixedHeaderData.add(WorkScheOutputConstants.DAY);
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
		lstItem.stream().forEach(x -> headerData.lstOutputItemSettingCode.add(String.valueOf(x.getAttendanceDisplay())));
	}
	
	/**
	 * Write header data.
	 *
	 * @param data the data
	 * @param sheet the sheet
	 * @param reportData the report data
	 */
	public void writeHeaderData(WorkScheduleOutputCondition data, Worksheet sheet, DailyPerformanceReportData reportData) {
		PageSetup pageSetup = sheet.getPageSetup();
		pageSetup.setHeader(0, "&8 " + reportData.getHeaderData().companyName);
	}
	
	/**
	 * Set all fixed text.
	 *
	 * @param workbook the new fixed data
	 * @param reportData the report data
	 */
	public void setFixedData(Worksheet sheet, DailyPerformanceReportData reportData, int currentRow) {
		DailyPerformanceHeaderData headerData = reportData.getHeaderData();
		// Set fixed value
		Cells cells = sheet.getCells();
		
		Cell erAlCell = cells.get(currentRow, 0);
		erAlCell.setValue(headerData.getFixedHeaderData().get(0));
		
		Cell dateCell = cells.get(currentRow, 1);
		dateCell.setValue(headerData.getFixedHeaderData().get(1));
		
		Cell dayMonCell = cells.get(currentRow + 1, 1);
		dayMonCell.setValue(headerData.getFixedHeaderData().get(2));
		
		Cell dayCell = cells.get(currentRow + 1, 2);
		dayCell.setValue(headerData.getFixedHeaderData().get(3));
		
		Cell remarkCell = cells.get(currentRow, 34);
		remarkCell.setValue(headerData.getFixedHeaderData().get(4));
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
	public void writeDetailedWorkSchedule(int currentRow, Workbook workbook, WorkplaceReportData workplaceReportData, int dataRowCount, WorkScheduleOutputCondition condition) throws Exception {
		Cells cells = workbook.getWorksheets().get(0).getCells();
		WorksheetCollection templateSheetCollection = workbook.getWorksheets();
		
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
				// A4_3 -> A4_7
				
				currentRow++;
				boolean colorWhite = true; // true = white, false = light blue, start with white row
				
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
						dateCell.setValue(detailedDailyPerformanceReportData.getDate()); // TODO: mm/dd
						
						// A5_3
						Cell dayOfWeekCell = cells.get(currentRow, 2);
						dayOfWeekCell.setValue(detailedDailyPerformanceReportData.getDate().dayOfWeek());
						
						// A5_4
						List<ActualValue> lstItem = detailedDailyPerformanceReportData.getActualValue();
						
						// Divide list into smaller lists (max 16 items)
						int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);
	
				        for(int i = 0; i < numOfChunks; i++) {
				            int start = i * CHUNK_SIZE;
				            int length = Math.min(lstItem.size() - start, CHUNK_SIZE);
	
				            List<ActualValue> lstItemRow = lstItem.subList(start, start + length);
				            
				            for (int j = 0; j < length; j++) {
				            	// Column 4, 6, 8,...
				            	// Row 3, 4, 5
				            	ActualValue actualValue = lstItemRow.get(j);
				            	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
				            	cell.setValue(actualValue.getValue());
				            }
				            
				            currentRow++;
				        }
				        
				        // A5_5
				        Cell remarkCell = cells.get(currentRow,35);
				        remarkCell.setValue(detailedDailyPerformanceReportData.getErrorDetail());
				        
				        currentRow++;
				        colorWhite = !colorWhite; // Change to other color
					}
				}
				
				
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
		}
		
		// Process to child workplace
		Map<String, WorkplaceReportData> mapChildWorkplace = workplaceReportData.getLstChildWorkplaceReportData();
		for (Map.Entry<String, WorkplaceReportData> entry: mapChildWorkplace.entrySet()) {
			writeDetailedWorkSchedule(currentRow, workbook, entry.getValue(), dataRowCount, condition);
		}
		
		// Skip writing total for root, use gross total instead
		if (lstEmployeeReportData != null && lstEmployeeReportData.size() > 0) {
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
		            	Cell cell = cells.get(currentRow + i, DATA_COLUMN_INDEX[0] + j * 2); 
		            	cell.setValue(value);
	            	}
	            }
	        }
		}
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
	public void writeDetailedDailySchedule(int currentRow, Workbook workbook, List<DailyReportData> lstDailyReportData, int dataRowCount) throws Exception {
		Cells cells = workbook.getWorksheets().get(1).getCells(); // By date
		WorksheetCollection templateSheetCollection = workbook.getWorksheets();
		
		for (DailyReportData dailyReportData: lstDailyReportData) {
			Range dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DATE_ROW);
			Range dateRange = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
			dateRange.copy(dateRangeTemp);
			
			// B3_1
			Cell dateTagCell = cells.get(currentRow, 0);
			dateTagCell.setValue(WorkScheOutputConstants.DATE_BRACKET);
			
			// B3_2
			Cell dateCell = cells.get(currentRow, DATA_COLUMN_INDEX[0]);
			dateCell.setValue(dailyReportData.getDate().toString()); // TODO: format date
			
			currentRow++;
			
			DailyWorkplaceData rootWorkplace = dailyReportData.getLstWorkplaceData();
			writeDailyDetailedPerformanceDataOnWorkplace(currentRow, cells, templateSheetCollection, rootWorkplace, dataRowCount);
		}
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
	private void writeDailyDetailedPerformanceDataOnWorkplace(int currentRow, Cells cells, WorksheetCollection templateSheetCollection, DailyWorkplaceData rootWorkplace, int dataRowCount) throws Exception {
		Range workplaceRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DATE_ROW);
		Range workplaceRange = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
		workplaceRange.copy(workplaceRangeTemp);
		
		// B4_1
		Cell workplaceTagCell = cells.get(currentRow, 0);
		workplaceTagCell.setValue(WorkScheOutputConstants.WORKPLACE);
		
		// B4_2
		Cell workplaceCell = cells.get(currentRow, DATA_COLUMN_INDEX[0]);
		workplaceCell.setValue(rootWorkplace.getWorkplaceCode() + " " + rootWorkplace.getWorkplaceName());
		
		currentRow++;
		boolean colorWhite = true; // true = white, false = light blue, start with white row
		
		List<DailyPersonalPerformanceData> employeeReportData = rootWorkplace.getLstDailyPersonalData();
		for (DailyPersonalPerformanceData employee : employeeReportData){
			Range dateRangeTemp;
			if (colorWhite) // White row
				dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + dataRowCount);
			else // Light blue row
				dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + dataRowCount);
			Range dateRange = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
			dateRange.copy(dateRangeTemp);
			
			// B5_1
			Cell erAlMark = cells.get(currentRow, 0);
			erAlMark.setValue(employee.getErrorAlarmCode());
			
			// B5_2
			Cell employeeCell = cells.get(currentRow, 1);
			employeeCell.setValue(employee.getEmployeeName());
			
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
	            
	            // B5_4
		        Cell remarkCell = cells.get(currentRow,35);
		        remarkCell.setValue(employee.getDetailedErrorData());
	        }
		}
		
		Range workplaceTotalTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW);
		Range workplaceTotal = cells.createRange(currentRow, 0, 1, DATA_COLUMN_INDEX[5]);
		workplaceTotal.copy(workplaceTotalTemp);
		
		// B6_1
		Cell workplaceTotalCellTag = cells.get(currentRow, 0);
		workplaceTotalCellTag.setValue(WorkScheOutputConstants.WORKPLACE_TOTAL);
		
		// B6_2
		List<TotalValue> totalWorkplaceValue = rootWorkplace.getWorkplaceTotal().getTotalWorkplaceValue();
		int numOfChunks = (int) Math.ceil( (double) totalWorkplaceValue.size() / CHUNK_SIZE);
		
        for(int i = 0; i < numOfChunks; i++) {
            int start = i * CHUNK_SIZE;
            int length = Math.min(totalWorkplaceValue.size() - start, CHUNK_SIZE);

            List<TotalValue> lstItemRow = totalWorkplaceValue.stream().collect(Collectors.toList()).subList(start, start + length);
            
            for (int j = 0; j < length; j++) {
            	String value = lstItemRow.get(j).getValue();
            	if (value != "0") {
	            	Cell cell = cells.get(i * 2 + 2, DATA_COLUMN_INDEX[0] + j * 2); 
	            	cell.setValue(value);
            	}
            }
        }
        
		// Child workplace
		Map<String, DailyWorkplaceData> mapChildWorkplace = rootWorkplace.getLstChildWorkplaceData();
		for (Map.Entry<String, DailyWorkplaceData> entry: mapChildWorkplace.entrySet()) {
			writeDailyDetailedPerformanceDataOnWorkplace(currentRow, cells, templateSheetCollection, entry.getValue(), dataRowCount);
		}
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
	public Optional<PrintRemarksContent> getRemarkContent(String employeeId, GeneralDate currentDate, List<EmployeeDailyPerError> errorList, RemarksContentChoice choice) {
		// 遅刻早退
		PrintRemarksContent printRemarksContent = null;
		if (errorList.size() > 0) {
			Optional<EmployeeDailyPerError> optError = errorList.stream().filter(x -> x.getErrorAlarmWorkRecordCode().v().contains(SystemFixedErrorAlarm.LEAVE_EARLY.value)).findFirst();
			if (optError.isPresent() && choice == RemarksContentChoice.LEAVING_EARLY) {
				printRemarksContent = new PrintRemarksContent(1, RemarksContentChoice.LEAVING_EARLY.value);
			}
		}
		
		// 手入力
		List<EditStateOfDailyPerformanceDto> editStateDto = editStateFinder.finds(employeeId, currentDate);
		List<GeneralDate> lstEditStateDate = new ArrayList<>();
		// Likely lstEditStateDate only has 0-1 element
		editStateDto.stream().filter(x -> x.getEditStateSetting() == EditStateSetting.HAND_CORRECTION_MYSELF.value).forEach(x -> lstEditStateDate.add(x.getYmd()));
		
		if (lstEditStateDate.size() > 0 && choice == RemarksContentChoice.MANUAL_INPUT) {
			printRemarksContent = new PrintRemarksContent(1, RemarksContentChoice.MANUAL_INPUT.value);
		}
		
		return printRemarksContent == null? Optional.empty() : Optional.of(printRemarksContent);
	}
}
