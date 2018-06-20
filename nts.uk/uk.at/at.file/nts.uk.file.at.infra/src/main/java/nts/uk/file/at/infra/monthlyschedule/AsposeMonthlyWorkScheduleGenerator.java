/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.monthlyschedule;

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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceItemValueImport;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkSchedule;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.employment.EmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.SCEmployeeAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
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
import nts.uk.file.at.app.export.dailyschedule.FileOutputType;
import nts.uk.file.at.app.export.dailyschedule.FormOutputType;
import nts.uk.file.at.app.export.dailyschedule.OutputConditionSetting;
import nts.uk.file.at.app.export.dailyschedule.TotalWorkplaceHierachy;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;
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
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;
import nts.uk.file.at.app.export.dailyschedule.totalsum.WorkplaceTotal;
import nts.uk.file.at.app.export.employee.jobtitle.EmployeeJobHistExport;
import nts.uk.file.at.app.export.employee.jobtitle.JobTitleImportAdapter;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleGenerator;
import nts.uk.file.at.app.export.monthlyschedule.MonthlyWorkScheduleQuery;
import nts.uk.file.at.infra.dailyschedule.WorkScheOutputConstants;
import nts.uk.file.at.infra.dailyschedule.WorkScheduleQueryData;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeMonthlyWorkScheduleGenerator.
 */
@Stateless
public class AsposeMonthlyWorkScheduleGenerator extends AsposeCellsReportGenerator
		implements MonthlyWorkScheduleGenerator {

	/** The company repository. */
	@Inject
	private CompanyRepository companyRepository;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Inject
	private WorkplaceConfigInfoRepository workplaceConfigRepository;

	/** The employee adapter. */
	@Inject
	private SCEmployeeAdapter employeeAdapter;
	
	/** The workplace info repository. */
	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;

	/**  The employment adapter. */
	@Inject
	private ScEmploymentAdapter employmentAdapter;

	/** The job title adapter. */
	@Inject
	private JobTitleImportAdapter jobTitleAdapter;

	/** The finder. */
	@Inject
	private EmploymentRepository employmentRepository;

	/** The Constant TEMPLATE. */
	private static final String TEMPLATE = "report/KWR006.xlsx";

	/** The Constant CHUNK_SIZE. */
	private static final int CHUNK_SIZE = 13;

	private static final int COL_MERGED_SIZE = 2;

	/** The Constant SHEET_FILE_NAME. */
	private static final String SHEET_FILE_NAME = "日別勤務表";

	/** The Constant DATA_COLUMN_INDEX. */
	private static final int[] DATA_COLUMN_INDEX = { 3, 8, 10, 14, 16, 39 };

	/** The font family. */
	private final String FONT_FAMILY = "ＭＳ ゴシック";

	/** The font size. */
	private final int FONT_SIZE = 9;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator#
	 * generate(nts.uk.file.at.app.export.dailyschedule.
	 * WorkScheduleOutputCondition,
	 * nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo,
	 * nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery)
	 */
	@Override
	public void generate(FileGeneratorContext generatorContext, MonthlyWorkScheduleQuery query) {
		val reportContext = this.createContext(TEMPLATE);

		Workbook workbook;

		try {

			workbook = reportContext.getWorkbook();
			WorksheetCollection sheetCollection = workbook.getWorksheets();
			Worksheet sheet = sheetCollection.get(0);

			//this.setPageHeader(sheet, GeneralDate.today(), GeneralDate.today(), "LOL");
			//this.setItemHeader(sheet.getCells(), sheetCollection);

			//this.printData(sheet.getCells(), sheetCollection);

			// Rename sheet
			sheet.setName(WorkScheOutputConstants.SHEET_FILE_NAME);

			// Move to first position
			sheet.moveTo(0);

			// Delete the rest of workbook
			int sheetCount = sheetCollection.getCount();
			for (int i = sheetCount - 1; i > 0; i--) {
				sheetCollection.removeAt(i);
			}

			WorkbookDesigner designer = new WorkbookDesigner();
			designer.setWorkbook(workbook);

			// Process designer
			reportContext.processDesigner();

			// Get current date and format it
			DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.JAPAN);
			String currentFormattedDate = LocalDateTime.now().format(jpFormatter);
			String fileName = WorkScheOutputConstants.SHEET_FILE_NAME + "_" + currentFormattedDate;

			// Save workbook
			if (query.getFileType() == FileOutputType.FILE_TYPE_EXCEL)
				reportContext.saveAsExcel(this.createNewFile(generatorContext, fileName + ".xlsx"));
			else {
				reportContext.saveAsPdf(this.createNewFile(generatorContext, fileName + ".pdf"));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Sets the page header.
	 *
	 * @param sheet the sheet
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param companyName the company name
	 */
	private void setPageHeader(Worksheet sheet, GeneralDate startDate, GeneralDate endDate, String companyName) {
		PageSetup pageSetup = sheet.getPageSetup();
		Cells cells = sheet.getCells();

		// set company name
		pageSetup.setHeader(0, "&8 " + companyName);

		// set title header
		pageSetup.setHeader(1, "&8 " + TextResource.localize("KWR006_1"));

		// Set header date time + page number
		DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  H:mm", Locale.JAPAN);
		pageSetup.setHeader(2, "&8 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage &P ");

		// Set period cell
		Cell periodCell = cells.get(0, 0);
		DateTimeFormatter jpFormatter = DateTimeFormatter.ofPattern("yyyy/M/d (E)", Locale.JAPAN);
		String periodStr = WorkScheOutputConstants.PERIOD + " " + startDate.toLocalDate().format(jpFormatter) + " ～ "
				+ endDate.toLocalDate().format(jpFormatter);
		periodCell.setValue(periodStr);
	}

	/**
	 * Sets the item header.
	 */
	private void setItemHeader(Cells cells, WorksheetCollection sheetCollection) {
		// Set item header (A2_1/B2_1) 
		Cell itemHeader = cells.get("A3");
		itemHeader.setValue(TextResource.localize("KWR006_65")); // yearmonth
		//itemHeader.setValue(TextResource.localize("KWR006_76")); // personal name

		// remark header
		Range remarkHeader = sheetCollection.getRangeByName("REMARK");
		remarkHeader.setValue(TextResource.localize("KWR006_67"));

		// create mock data.
		List<String> mock = new ArrayList<>();
		int count = 0;
		while (count < 50) {
			mock.add("test-item"+count);
			count++;
		}

		int currentRow = 2;
		int currentCol = 3;
		final int finalCol = currentCol + CHUNK_SIZE * COL_MERGED_SIZE;
		Iterator<String> iterator = mock.iterator();

		while (iterator.hasNext()) {
			if (currentCol == finalCol) {
				currentRow++;
				// reset col
				currentCol = 3;
			}

			String string = iterator.next();
			Cell currentCell = cells.get(currentRow, currentCol);
			currentCell.setValue(string);
			currentCol += COL_MERGED_SIZE;
		}

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
		// lay name monthly attendance item
		List<MonthlyAttendanceItem> lstDailyAttendanceItem = new ArrayList<>();//attendanceNameService.getNameOfDailyAttendanceItem(lstAttendanceId);
		
		lstAttendanceId.stream().forEach(x -> {
			MonthlyAttendanceItem attendanceItem = lstDailyAttendanceItem.stream().filter(item -> item.getAttendanceItemId() == x).findFirst().get();
			OutputItemSetting setting = new OutputItemSetting();
			setting.setItemCode(attendanceItem.getDisplayNumber());
			setting.setItemName(attendanceItem.getAttendanceName().v());
			headerData.lstOutputItemSettingCode.add(setting);
		});
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
		//TODO: domain man C
		OutputItemMonthlyWorkSchedule outSche = new OutputItemMonthlyWorkSchedule();//outputItemRepo.findByCidAndCode(companyId, condition.getCode().v()).get();
		
		// Get all data from query data container
		List<AttendanceResultImport> lstAttendanceResultImport = queryData.getLstAttendanceResultImport();
		List<AttendanceItemsDisplay> lstDisplayItem = queryData.getLstDisplayItem();
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
			
			//TODO: set remark content
			
			detailedDate.actualValue = new ArrayList<>();
			lstDisplayItem.stream().forEach(item -> {
				Optional<AttendanceItemValueImport> optItemValue = x.getAttendanceItems().stream().filter(att -> att.getItemId() == item.getAttendanceDisplay()).findFirst();
				if (optItemValue.isPresent()) {
					AttendanceItemValueImport itemValue = optItemValue.get();
					ValueType valueTypeEnum = EnumAdaptor.valueOf(itemValue.getValueType(), ValueType.class);
					
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
		
		//TODO: request 436
		List<AttendanceResultImport> lstAttendanceResultImport = new ArrayList<>();//attendaceAdapter.getValueOf(query.getEmployeeId(), new DatePeriod(query.getStartDate(), query.getEndDate()), AttendanceResultImportAdapter.convertList(outputItem.getLstDisplayedAttendance(), x -> x.getAttendanceDisplay()));
		queryData.setLstAttendanceResultImport(lstAttendanceResultImport);
		
		// Check lowest level of employee and highest level of output setting, and attendance result count is 0
		// 階層累計行のみ出力する設定の場合、データ取得件数は0件として扱い、エラーメッセージを表示(#Msg_37#)
		int lowestEmployeeLevel = checkLowestWorkplaceLevel(lstWorkplace);
		TotalWorkplaceHierachy outputSetting = condition.getSettingDetailTotalOutput().getWorkplaceHierarchyTotal();
		int highestOutputLevel = outputSetting.getHighestLevelEnabled();
		if (lowestEmployeeLevel < highestOutputLevel && lstAttendanceResultImport.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Msg_37"));
		}

		
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
		//TODO domain man C
		OutputItemMonthlyWorkSchedule outSche = new OutputItemMonthlyWorkSchedule(); //outputItemRepo.findByCidAndCode(companyId, condition.getCode().v()).get();
		
		// Get all data from query data container
		List<GeneralDate> datePeriod = queryData.getDatePeriod();
		List<AttendanceResultImport> lstAttendanceResultImport = queryData.getLstAttendanceResultImport();
		List<AttendanceItemsDisplay> lstDisplayItem = queryData.getLstDisplayItem();
		List<WkpHistImport> lstWorkplaceHistImport = queryData.getLstWorkplaceImport();
		
		List<String> lstEmployeeId = query.getEmployeeId();
		List<EmployeeDto> lstEmployeeDto = employeeAdapter.findByEmployeeIds(lstEmployeeId);
		
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
						
						personalPerformanceDate.actualValue = new ArrayList<>();
						lstDisplayItem.stream().forEach(item -> {
							Optional<AttendanceItemValueImport> optItemValue = x.getAttendanceItems().stream().filter(att -> att.getItemId() == item.getAttendanceDisplay()).findFirst();
							if (optItemValue.isPresent()) {
								AttendanceItemValueImport itemValue = optItemValue.get();
								ValueType valueTypeEnum = EnumAdaptor.valueOf(itemValue.getValueType(), ValueType.class);
								
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

	//TODO: sua thanh monthly
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
				if (val.value() == null) return;
				int attendanceId = val.getAttendanceId();
				TotalValue totalVal = lstWorkplaceGrossTotal.stream().filter(attendance -> attendance.getAttendanceId() == attendanceId).findFirst().get();
				ValueType valueTypeEnum = EnumAdaptor.valueOf(val.getValueType(), ValueType.class);
				if (valueTypeEnum.isInteger()) {
					int currentValue = (int) val.value();
					totalVal.setValue(String.valueOf(Integer.parseInt(totalVal.getValue()) + currentValue));
					totalVal.setValueType(val.getValueType());
				}
				if (valueTypeEnum.isDouble()) {
					double currentValueDouble = (double) val.value();
					totalVal.setValue(String.valueOf(Double.parseDouble(totalVal.getValue()) + currentValueDouble));
					totalVal.setValueType(val.getValueType());
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
						
						// Get all total
						ValueType valueTypeEnum = EnumAdaptor.valueOf(valueType, ValueType.class);
						TotalValue personalTotal = employeeData.mapPersonalTotal.get(attdId);
						TotalValue totalVal = lstTotalValue.stream().filter(attendance -> attendance.getAttendanceId() == attdId).findFirst().get();
						TotalValue totalGrossVal = lstWorkplaceGrossTotal.stream().filter(attendance -> attendance.getAttendanceId() == attdId).findFirst().get();
						
						// Change value type
						personalTotal.setValueType(valueType);
						totalVal.setValueType(valueType);
						totalGrossVal.setValueType(valueType);
						
						if (aVal.value() == null) return;
						
						if (valueTypeEnum.isInteger()) {
							int currentValue = (int) aVal.value();
							personalTotal.setValue(String.valueOf(Integer.parseInt(personalTotal.getValue()) + currentValue));
							employeeData.mapPersonalTotal.put(attdId, personalTotal);
							totalVal.setValue(String.valueOf(Integer.parseInt(totalVal.getValue()) + currentValue));
							totalGrossVal.setValue(String.valueOf(Integer.parseInt(totalGrossVal.getValue()) + currentValue));
							employeeData.mapPersonalTotal.put(attdId, personalTotal);
						}
						if (valueTypeEnum.isDouble()) {
							double currentValueDouble = (double) aVal.value();
							personalTotal.setValue(String.valueOf(Double.parseDouble(personalTotal.getValue()) + currentValueDouble));
							employeeData.mapPersonalTotal.put(attdId, personalTotal);
							totalVal.setValue(String.valueOf(Double.parseDouble(totalVal.getValue()) + currentValueDouble));
							totalGrossVal.setValue(String.valueOf(Double.parseDouble(personalTotal.getValue()) + currentValueDouble));
							employeeData.mapPersonalTotal.put(attdId, personalTotal);
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
					if (item.value() == null) return;
					Optional<TotalValue> optTotalVal = lstTotalVal.stream().filter(x -> x.getAttendanceId() == item.getAttendanceId()).findFirst();
					if (optTotalVal.isPresent()) {
						TotalValue totalVal = optTotalVal.get();
						int valueType = totalVal.getValueType();
						ValueType valueTypeEnum = EnumAdaptor.valueOf(valueType, ValueType.class);
						if (valueTypeEnum.isInteger()) {
							totalVal.setValue(String.valueOf((int) totalVal.value() + (int) item.value()));
						}
						if (valueTypeEnum.isDouble()) {
							totalVal.setValue(String.valueOf((double) totalVal.value() + (double) item.value()));
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
						int totalValueType = totalValue.getValueType();
						ValueType valueTypeEnum = EnumAdaptor.valueOf(totalValueType, ValueType.class);
						if (valueTypeEnum.isInteger()) {
							totalValue.setValue(String.valueOf((int) totalValue.value() + (int) actualValue.value()));
						}
						if (valueTypeEnum.isDouble()) {
							totalValue.setValue(String.valueOf((double) totalValue.value() + (double) actualValue.value()));
						}
					}
					else {
						totalValue = new TotalValue();
						totalValue.setAttendanceId(actualValue.getAttendanceId());
						ValueType valueTypeEnum = EnumAdaptor.valueOf(actualValue.getValueType(), ValueType.class);
						if (valueTypeEnum.isInteger() || valueTypeEnum.isDouble()) {
							if (actualValue.getValue() != null) {
								totalValue.setValue(actualValue.getValue());
							} else {
								totalValue.setValue("0");
							}
						}
						else { // This case also deals with null value
							totalValue.setValue(actualValue.getValue());
						}
						totalValue.setValueType(valueType);
						lstTotalValue.add(totalValue);
					}
					
					Optional<TotalValue> optTotalWorkplaceVal = lstTotalHierarchyValue.stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
					TotalValue totalWorkplaceValue;
					if (optTotalWorkplaceVal.isPresent()) {
						totalWorkplaceValue = optTotalWorkplaceVal.get();
						int totalValueType = totalValue.getValueType();
						ValueType valueTypeEnum = EnumAdaptor.valueOf(totalValueType, ValueType.class);
						if (valueTypeEnum.isInteger()) {
							totalValue.setValue(String.valueOf((int) totalWorkplaceValue.value() + (int) actualValue.value()));
						}
						if (valueTypeEnum.isDouble()) {
							totalValue.setValue(String.valueOf((double) totalWorkplaceValue.value() + (double) actualValue.value()));
						}
					}
					else {
						totalWorkplaceValue = new TotalValue();
						totalWorkplaceValue.setAttendanceId(actualValue.getAttendanceId());
						ValueType valueTypeEnum = EnumAdaptor.valueOf(actualValue.getValueType(), ValueType.class);
						if (valueTypeEnum.isInteger() || valueTypeEnum.isDouble()) {
							if (actualValue.getValue() != null) {
								totalWorkplaceValue.setValue(actualValue.getValue());
							} else {
								totalWorkplaceValue.setValue("0");
							}
						}
						else { // This case also deals with null value
							totalWorkplaceValue.setValue(actualValue.getValue());
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
				int valueType = actualValue.getValueType();
				Optional<TotalValue> optTotalVal = lstTotalValue.stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
				TotalValue totalValue;
				if (optTotalVal.isPresent()) {
					totalValue = optTotalVal.get();
					int totalValueType = totalValue.getValueType();
					ValueType valueTypeEnum = EnumAdaptor.valueOf(totalValueType, ValueType.class);
					if (valueTypeEnum.isInteger()) {
						totalValue.setValue(String.valueOf((int) totalValue.value() + (int) actualValue.value()));
					}
					if (valueTypeEnum.isDouble()) {
						totalValue.setValue(String.valueOf((double) totalValue.value() + (double) actualValue.value()));
					}
				}
				else {
					totalValue = new TotalValue();
					totalValue.setAttendanceId(actualValue.getAttendanceId());
					ValueType valueTypeEnum = EnumAdaptor.valueOf(actualValue.getValueType(), ValueType.class);
					if (valueTypeEnum.isInteger() || valueTypeEnum.isDouble()) {
						if (actualValue.getValue() != null) {
							totalValue.setValue(actualValue.getValue());
						} else {
							totalValue.setValue("0");
						}
					}
					else { // This case also deals with null value
						totalValue.setValue(actualValue.getValue());
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
				if (totalVal.value() == null) return;
				// Literally 0-1 result in list
				Optional<TotalValue> optGrossTotal = lstGrossTotal.stream().filter(x -> x.getAttendanceId() == totalVal.getAttendanceId()).findFirst();
				TotalValue totalValue;
				if (optGrossTotal.isPresent()) {
					totalValue = optGrossTotal.get();
					int totalValueType = totalValue.getValueType();
					ValueType valueTypeEnum = EnumAdaptor.valueOf(totalValueType, ValueType.class);
					if (valueTypeEnum.isInteger()) {
						totalValue.setValue(String.valueOf((int) totalValue.value() + (int) totalVal.value()));
					}
					if (valueTypeEnum.isDouble()) {
						totalValue.setValue(String.valueOf((double) totalValue.value() + (double) totalVal.value()));
					}
				}
				else {
					totalValue = new TotalValue();
					totalValue.setAttendanceId(totalVal.getAttendanceId());
					ValueType valueTypeEnum = EnumAdaptor.valueOf(totalVal.getValueType(), ValueType.class);
					if (valueTypeEnum.isInteger() || valueTypeEnum.isDouble()) {
						totalValue.setValue(totalVal.getValue());
					}
					totalValue.setValueType(totalVal.getValueType());
					lstGrossTotal.add(totalValue);
				}
			});
		});
	}

	private void printData(Cells cells, WorksheetCollection sheetCollection) throws Exception {
		// create mock data.
		List<String> mock = new ArrayList<>();
		int count = 0;
		while (count < 80) {
			mock.add("test-value"+count);
			count++;
		}

		// print header data
		int currentRow = 8;
		// workplace
		Range workplaceRangeTempplate = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WORKPLACE_ROW);
		Range workplaceRange = cells.createRange(currentRow, 0, 1, 39);
		workplaceRange.copy(workplaceRangeTempplate);

		// A3_1
		Cell workplaceTagCell = cells.get(currentRow, 0);
		workplaceTagCell.setValue(TextResource.localize("KWR006_68"));
		
		// A3_2
		Cell workplaceInfo = cells.get(currentRow, DATA_COLUMN_INDEX[0]);
		workplaceInfo.setValue("test test");

		// employee
		currentRow++;

		Range employeeRangeTemp = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_EMPLOYEE_ROW);
		Range employeeRange = cells.createRange(currentRow, 0, 1, 39);
		employeeRange.copy(employeeRangeTemp);

		// A4_1
		Cell employeeTagCell = cells.get(currentRow, 0);
		employeeTagCell.setValue(TextResource.localize("KWR006_69"));
		
		// A4_2
		Cell employeeCell = cells.get(currentRow, 3);
		employeeCell.setValue("Code + name");
		
		// A4_3
		Cell employmentTagCell = cells.get(currentRow, 9);
		employmentTagCell.setValue(WorkScheOutputConstants.EMPLOYMENT);
		
		// A4_5
		Cell employmentCell = cells.get(currentRow, 11);
		employmentCell.setValue("name");
		
		// A4_6
		Cell jobTitleTagCell = cells.get(currentRow, 15);
		jobTitleTagCell.setValue(WorkScheOutputConstants.POSITION);

		// A4_7
		Cell jobTitleCell = cells.get(currentRow, 17);
		jobTitleCell.setValue("Job");

		// print data
		currentRow++;

		int currentCol = 3;
		int rowCount = 1;
		final int maxRowCount = 3;
		final int finalCol = currentCol + CHUNK_SIZE * COL_MERGED_SIZE;
		Iterator<String> iterator = mock.iterator();

		// range template
		Range whiteRange = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_WHITE_ROW + "3");
		Range blueRange = sheetCollection.getRangeByName(WorkScheOutputConstants.RANGE_LIGHTBLUE_ROW + "3");
		boolean isWhite = true;
		
		while (iterator.hasNext()) {
			if (rowCount == 1) {
				Range itemRange = cells.createRange(currentRow, 0, maxRowCount, 39);
				if (isWhite) {
					itemRange.copy(whiteRange);
				} else {
					itemRange.copy(blueRange);
				}
			}

			// set value to cell
			String string = iterator.next();
			Cell currentCell = cells.get(currentRow, currentCol);
			currentCell.setValue(string);
			currentCol += COL_MERGED_SIZE;

			if (currentCol == finalCol) {
				// next row
				rowCount++;
				currentRow++;
				// reset col
				currentCol = 3;
			}

			if (rowCount == 4) {
				// reset row count
				rowCount = 1;
				isWhite = !isWhite;
			}
		}
		
	}
}