package nts.uk.file.at.infra.dailyschedule;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarksContentChoice;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.identificationstatus.A;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmClassification;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeAdapterInforFinder;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;
import nts.uk.file.at.app.export.dailyschedule.AttendanceResultImportAdapter;
import nts.uk.file.at.app.export.dailyschedule.IndividualDailyWorkSchedule;
import nts.uk.file.at.app.export.dailyschedule.TotalDayCountWs;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputService;
import nts.uk.file.at.app.export.dailyschedule.data.DailyPerformanceHeaderData;
import nts.uk.file.at.app.export.dailyschedule.data.DailyPerformanceReportData;
import nts.uk.file.at.app.export.dailyschedule.data.DetailedDailyPerformanceReportData;
import nts.uk.file.at.app.export.dailyschedule.data.EmployeeReportData;
import nts.uk.file.at.app.export.dailyschedule.data.WorkplaceReportData;
import nts.uk.file.at.app.export.dailyschedule.totalsum.PersonalTotal;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalCountDay;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalSumRowOfDailySchedule;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;
import nts.uk.file.at.app.export.dailyschedule.totalsum.WorkplaceTotal;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class AsposeWorkScheduleOutputConditionGenerator.
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
	private static final int[] DATA_COLUMN_INDEX = {3, 8, 10, 14, 16, };
	
	/** The Constant DAY_COUNT_COLUMN_INDEX. */
	private static final int[] DAY_COUNT_COLUMN_INDEX = {3, 5, 7, 9, 11, 13, 15, 17, 19};
	
	/**
	 * Clear fake data.
	 *
	 * @param condition the condition
	 * @param query the query
	 * @param workplace the workplace
	 */
	public void clearFakeData (WorkScheduleOutputCondition condition, WorkScheduleOutputQuery query, WorkplaceConfigInfo workplace) {
		query = new WorkScheduleOutputQuery();
		query.setStartDate(GeneralDate.ymd(2018, 4, 20));
		query.setEndDate(GeneralDate.ymd(2018, 4, 30));
		query.setOutputCode(1);
		query.setUserId("AAA");
		query.setWorkplaceId("WorkplaceA");
		
		condition = new WorkScheduleOutputCondition();
		condition = WorkScheduleOutputService.updateOutputCondition(condition, query);
		
		workplace = new WorkplaceConfigInfo(new WorkplaceConfigInfoGetMemento() {
			
			@Override
			public List<WorkplaceHierarchy> getWkpHierarchy() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHistoryId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getCompanyId() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		//new AsposeWorkScheduleOutputConditionGenerator().generate(condition, workplace, query);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator#generate(nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition, nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo, nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery)
	 */
	@Override
	public void generate(WorkScheduleOutputCondition condition, WorkplaceConfigInfo workplace, WorkScheduleOutputQuery query) {
		Workbook workbook;
		try {
			workbook = new Workbook(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
			
			WorkbookDesigner designer = new WorkbookDesigner();
			designer.setWorkbook(workbook);
			
			// Dummy
			query = new WorkScheduleOutputQuery();
			query.setStartDate(GeneralDate.ymd(2018, 4, 20));
			query.setEndDate(GeneralDate.ymd(2018, 4, 22));
			query.setOutputCode(1);
			query.setUserId("AAA");
			query.setWorkplaceId("WorkplaceA");
		
			condition = new WorkScheduleOutputCondition();
			condition = WorkScheduleOutputService.updateOutputCondition(condition, query);
			
			workplace = workplaceConfigRepository.find(AppContexts.user().companyId(), "826a9564-df07-482c-a9d6-f934ab2c91ab").get();
			
			List<String> lstEmployeeId = new ArrayList<>();
//			lstEmployeeId.add("0011fcd1-06ca-4fd7-b009-9f1cab325fcb");
			lstEmployeeId.add("007d103d-453b-480c-83c7-b09b48814c99");
			lstEmployeeId.add("00c4ee0f-c890-40ae-8981-4bce064b0b21");
			query.setEmployeeId(lstEmployeeId);
			
			TotalSumRowOfDailySchedule sum = new TotalSumRowOfDailySchedule();
			sum.setPersonalTotal(new ArrayList<>());
			
			/**
			 * Collect data
			 */
			DailyPerformanceReportData reportData = new DailyPerformanceReportData();
			collectData(reportData, query, condition);
			
			
			//clearFakeData(reportData, query, workplace);
			
			// Get the 1st sheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			
			// Write header data
			writeHeaderData(condition, sheet, reportData);
			 	
			// Set all fixed cell
			setFixedData(workbook, reportData);
			
			// Write display map
			writeDisplayMap(workbook.getWorksheets().get(0).getCells(),reportData);
			
			int currentRow = 8;
			writeDetailedWorkSchedule(currentRow, workbook, reportData.getWorkplaceReportData(), condition.getCode().size() / 3 + 1);
			
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
	 */
	public void collectData(DailyPerformanceReportData reportData, WorkScheduleOutputQuery query, WorkScheduleOutputCondition condition) {
		collectHeaderData(reportData.getHeaderData());
		//collectDisplayMap(reportData.getHeaderData(), condition);
		
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
		
		WorkplaceReportData data = new WorkplaceReportData();
		data.workplaceCode = "";
		reportData.setWorkplaceReportData(data);
		
		analyzeInfo(lstWorkplace, reportData.getWorkplaceReportData());
		
		List<AttendanceResultImport> lstAttendanceResultImport = attendaceAdapter.getValueOf(query.getEmployeeId(), new DatePeriod(query.getStartDate(), query.getEndDate()), AttendanceResultImportAdapter.convertList(condition.getCode(), x -> x.v()));
		for (String employeeId: query.getEmployeeId()) {
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
				detailedDate.actualValue = new ArrayList<>();
				x.getAttendanceItems().stream().forEach(a -> detailedDate.actualValue.add(new ActualValue(a.getItemId(), a.getValue(), a.getValueType())));
			});
			
			// Calculate total count day
			totalDayCountWs.calculateAllDayCount(employeeId, new DateRange(query.getStartDate(), query.getEndDate()), employeeData.totalCountDay);
			
		}
		
		calculateTotal(reportData.getWorkplaceReportData(), condition.getCode());

	}
	
	/**
	 * Calculate total.
	 *
	 * @param workplaceData the workplace data
	 * @param lstAttendanceId the lst attendance id
	 */
	public void calculateTotal(WorkplaceReportData workplaceData, List<OutputItemSettingCode> lstAttendanceId) {
		String code = workplaceData.getWorkplaceCode();
		if (!code.equals("")) {
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
			workplaceData.getLstEmployeeReportData().stream().forEach(employeeData -> {
				// Put attendanceId into map
				lstAttendanceId.stream().forEach(attendanceId -> {
					employeeData.mapPersonalTotal.put(attendanceId.v(), "0");
				});
				
				// Add into total by attendanceId
				employeeData.getLstDetailedPerformance().stream().forEach(detail -> {
					detail.actualValue.stream().forEach((aVal) -> {
						int attdId = aVal.getAttendanceId();
						
						// Integer/Big Decimal
						if (aVal.getValueType() == ActualValue.INTEGER || aVal.getValueType() == ActualValue.BIG_DECIMAL) {
							//int currentValue = Integer.parseInt(employeeData.mapPersonalTotal.get(attdId));
							int currentValue = aVal.value();
							employeeData.mapPersonalTotal.put(attdId, currentValue + String.valueOf(aVal.getValue()));
						}
					});
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
		if (mapWorkplaceInfo.containsKey(code)) {
			return mapWorkplaceInfo.get(code);
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
	private void analyzeInfo(Map<String, WorkplaceInfo> lstWorkplace, WorkplaceReportData parent) {
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
			}
		});
		
		parent.lstChildWorkplaceReportData.keySet().forEach((key) -> {
			analyzeInfo(lstWorkplace, parent.lstChildWorkplaceReportData.get(key) );
		});
	}
	
	/**
	 * Collect header data.
	 *
	 * @param headerData the header data
	 */
	public void collectHeaderData(DailyPerformanceHeaderData headerData) {
		headerData = new DailyPerformanceHeaderData();
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
	public void collectDisplayMap(DailyPerformanceHeaderData headerData, WorkScheduleOutputCondition condition) {
		List<OutputItemSettingCode> lstItem = condition.getCode();
		headerData.setLstOutputItemSettingCode(new ArrayList<>());
		lstItem.stream().forEach(x -> headerData.lstOutputItemSettingCode.add(String.valueOf(x.v())));
	}
	
	/**
	 * Write header data.
	 *
	 * @param data the data
	 * @param sheet the sheet
	 */
	public void writeHeaderData(WorkScheduleOutputCondition data, Worksheet sheet, DailyPerformanceReportData reportData) {
		PageSetup pageSetup = sheet.getPageSetup();
		pageSetup.setHeader(0, "&8 " + reportData.getHeaderData().companyName);
	}
	
	/**
	 * Set all fixed text.
	 *
	 * @param workbook the new fixed data
	 */
	public void setFixedData(Workbook workbook, DailyPerformanceReportData reportData) {
		DailyPerformanceHeaderData headerData = reportData.getHeaderData();
		// Set fixed value
		Range range = workbook.getWorksheets().getRangeByName("ERAL");
		
		Cell cell = range.getCellOrNull(0, 0);
		cell.setValue(headerData.getFixedHeaderData().get(0));
		
		range = workbook.getWorksheets().getRangeByName("Date");
		range.getCellOrNull(0, 0).setValue(headerData.getFixedHeaderData().get(1));
		
		range = workbook.getWorksheets().getRangeByName("DayMon");
		range.getCellOrNull(0, 0).setValue(headerData.getFixedHeaderData().get(2));
		
		range = workbook.getWorksheets().getRangeByName("Day");
		range.getCellOrNull(0, 0).setValue(headerData.getFixedHeaderData().get(3));
		
		range = workbook.getWorksheets().getRangeByName("Remark");
		range.getCellOrNull(0, 0).setValue(headerData.getFixedHeaderData().get(4));
	}
	
	/**
	 * Write display map (48 cells max).
	 *
	 * @param cells cell map
	 * @param reportData the report data
	 */
	public void writeDisplayMap(Cells cells, DailyPerformanceReportData reportData) {
		List<String> lstItem = reportData.getHeaderData().getLstOutputItemSettingCode();
		
		// Divide list into smaller lists (max 16 items)
		int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);

        for(int i = 0; i < numOfChunks; i++) {
            int start = i * CHUNK_SIZE;
            int length = Math.min(lstItem.size() - start, CHUNK_SIZE);

            List<String> lstItemRow = lstItem.subList(start, start + length);
            
            for (int j = 0; j < CHUNK_SIZE; j++) {
            	// Column 4, 6, 8,...
            	// Row 3, 4, 5
            	Cell cell = cells.get(i * 2 + 2, DATA_COLUMN_INDEX[0] + j * 2); 
            	cell.setValue(lstItemRow.get(j));
            }
        }
	}
	
	/**
	 * Write detail work schedule.
	 *
	 * @param currentRow the current row
	 * @param cells the cells
	 * @param workplaceConfig the workplace config
	 * @param query the query
	 * @param condition the condition
	 * @param sum the sum
	 * @throws Exception the exception
	 */
	public void writeDetailedWorkSchedule(int currentRow, Workbook workbook, WorkplaceReportData workplaceReportData, int dataRowCount) throws Exception {
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
				int color = 0; // 0 = white, 1 = light blue, start with white row
				
				List<DetailedDailyPerformanceReportData> lstDetailedDailyPerformance = employeeReportData.getLstDetailedPerformance();
				for (DetailedDailyPerformanceReportData detailedDailyPerformanceReportData: lstDetailedDailyPerformance) {
					Range dateRangeTemp;
					if (color == 0) // White row
						dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + dataRowCount);
					else // Light blue row
						dateRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + dataRowCount);
					Range dateRange = cells.createRange(currentRow, 0, 1, 39);
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
			            int curRow = currentRow;
			            
			            for (int j = 0; j < CHUNK_SIZE; j++) {
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
			        
			        currentRow += dataRowCount;
				}
				
				// A6_1
				Range personalTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
				Range personalTotalRange = cells.createRange(currentRow, 0, 1, 39);
				personalTotalRange.copy(personalTotalRangeTemp);
				
				Cell personalTotalCellTag = cells.get(currentRow, 0);
				personalTotalCellTag.setValue(WorkScheOutputConstants.PERSONAL_TOTAL);
				
				// A6_2
				int numOfChunks = (int) Math.ceil( (double) employeeReportData.getMapPersonalTotal().size() / CHUNK_SIZE);
				
		        for(int i = 0; i < numOfChunks; i++) {
		            int start = i * CHUNK_SIZE;
		            int length = Math.min(employeeReportData.getMapPersonalTotal().size() - start, CHUNK_SIZE);

		            List<String> lstItemRow = employeeReportData.getMapPersonalTotal().values().stream().collect(Collectors.toList()).subList(start, start + length);
		            
		            for (int j = 0; j < CHUNK_SIZE; j++) {
		            	String value = lstItemRow.get(j);
		            	if (value != "0") {
			            	Cell cell = cells.get(i * 2 + 2, DATA_COLUMN_INDEX[0] + j * 2); 
			            	cell.setValue(value);
		            	}
		            }
		        }
		        
		        // A7_1
		        Range dayCountRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_DAYCOUNT_ROW);
				Range dayCountRange = cells.createRange(currentRow, 0, 1, 39);
				dayCountRange.copy(dayCountRangeTemp);
				Cell dayCountTag = cells.get(currentRow, 0);
				dayCountTag.setValue(WorkScheOutputConstants.DAY_COUNT);
				
				// A7_2 -> A7_10
				for (int i = 0; i < WorkScheOutputConstants.DAY_COUNT_TITLES.length; i++) {
					Cell dayTypeTag = cells.get(currentRow, i + 3);
					dayTypeTag.setValue(WorkScheOutputConstants.DAY_COUNT_TITLES[i]);
				}
				
				currentRow++;
				
				// A7_11 -> A7_19
				TotalCountDay totalCountDay = employeeReportData.getTotalCountDay();
				totalCountDay.initAllDayCount();
				
				for (int i = 0; i < WorkScheOutputConstants.DAY_COUNT_TITLES.length; i++) {
					Cell dayTypeTag = cells.get(currentRow, i + 3);
					dayTypeTag.setValue(totalCountDay.getAllDayCount().get(i));
				}
				
				currentRow++;
			}
		}
		
		// A8_1
		Range workplaceTotalRangeTemp = templateSheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_TOTAL_ROW + dataRowCount);
		Range workplaceTotalRange = cells.createRange(currentRow, 0, 1, 39);
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
            
            for (int j = 0; j < CHUNK_SIZE; j++) {
            	String value = lstItemRow.get(j).getValue();
            	if (value != "0") {
	            	Cell cell = cells.get(i * 2 + 2, DATA_COLUMN_INDEX[0] + j * 2); 
	            	cell.setValue(value);
            	}
            }
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
