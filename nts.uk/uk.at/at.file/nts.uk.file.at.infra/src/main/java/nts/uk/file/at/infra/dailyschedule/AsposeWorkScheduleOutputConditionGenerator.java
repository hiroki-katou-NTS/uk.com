package nts.uk.file.at.infra.dailyschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.management.Query;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceItemValueImport;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarksContentChoice;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceDto;
import nts.uk.ctx.at.record.app.find.dailyperform.editstate.EditStateOfDailyPerformanceFinder;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmClassification;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentInfo;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoGetMemento;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceHierarchy;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;
import nts.uk.file.at.app.export.dailyschedule.IndividualDailyWorkSchedule;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputService;
import nts.uk.file.at.app.export.dailyschedule.totalsum.PersonalTotal;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalSumRowOfDailySchedule;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeWorkScheduleOutputConditionGenerator extends AsposeCellsReportGenerator implements WorkScheduleOutputGenerator{

	@Inject
	WorkplaceConfigInfoRepository workplaceConfigRepository;
	
	@Inject
	WorkplaceInfoRepository workplaceInfoRepository;
	
	@Inject
	EmployeeDailyPerErrorRepository errorAlarmRepository;
	
	@Inject
	private ErrorAlarmWorkRecordAdapter errorAlarmWorkRecordAdapter;
	
	@Inject
	private DailyRecordWorkFinder fullFinder;
	
	@Inject
	private EditStateOfDailyPerformanceFinder editStateFinder;
	
	@Inject
	private PersonRepository personRepository;
	
	@Inject
	private EmploymentRepository employmentRepository;
	
	private static final int CHUNK_SIZE = 16;
	
	private static final int MAX_ITEM_ROW = 3;
	
	private static final String ITEM_DISPLAY_PREFIX = "ItemDisplay";
	
	private static final int[] DATA_COLUMN_INDEX = {3, 8, 10, 14, 16, };
	
	private static final int[] DAY_COUNT_COLUMN_INDEX = {3, 5, 7, 9, 11, 13, 15, 17, 19};
	
	public static void main (String args[]) {
		WorkScheduleOutputQuery query = new WorkScheduleOutputQuery();
		query.setStartDate(GeneralDate.ymd(2018, 4, 20));
		query.setEndDate(GeneralDate.ymd(2018, 4, 30));
		query.setOutputCode(1);
		query.setUserId("AAA");
		query.setWorkplaceId("WorkplaceA");
		
		WorkScheduleOutputCondition condition = new WorkScheduleOutputCondition();
		condition = WorkScheduleOutputService.updateOutputCondition(condition, query);
		
		WorkplaceConfigInfo workplace = new WorkplaceConfigInfo(new WorkplaceConfigInfoGetMemento() {
			
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
		
		
		
		new AsposeWorkScheduleOutputConditionGenerator().generate(condition, workplace, query);
	}
	
	@Override
	public void generate(WorkScheduleOutputCondition reportData, WorkplaceConfigInfo workplace, WorkScheduleOutputQuery query) {
		Workbook workbook;
		try {
			workbook = new Workbook("template/日別勤務表.xlsx");
			
			WorkbookDesigner designer = new WorkbookDesigner();
			designer.setWorkbook(workbook);
			
			// Get the 1st sheet
			Worksheet sheet = workbook.getWorksheets().get(0);
			
			// Write header data
			writeHeaderData(reportData, sheet);
			
			// Set all fixed cell
			setFixedData(workbook);
			
			// Write display map
			writeDisplayMap(workbook.getWorksheets().get(0).getCells(),reportData);
			
			TotalSumRowOfDailySchedule sum = new TotalSumRowOfDailySchedule();
			
			int currentRow = 8;
			writeDetailedWorkSchedule(currentRow, workbook.getWorksheets().get(0).getCells(), workplace, query, reportData, sum);
			
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
	 * Write header data.
	 *
	 * @param data the data
	 * @param sheet the sheet
	 */
	public void writeHeaderData(WorkScheduleOutputCondition data, Worksheet sheet) {
		PageSetup pageSetup = sheet.getPageSetup();
		//pageSetup.setHeader(0, "&8 " + AppContexts.user().companyId());
		pageSetup.setHeader(0, "&8 " + "CompanyAAA");
	}
	
	/**
	 * Set all fixed text
	 * @param workbook
	 */
	public void setFixedData(Workbook workbook) {
		// Set fixed value
		Range range = workbook.getWorksheets().getRangeByName("ERAL");
		
		Cell cell = range.getCellOrNull(0, 0);
		cell.setValue(WorkScheOutputConstants.ERAL);
		
		range = workbook.getWorksheets().getRangeByName("Date");
		range.getCellOrNull(0, 0).setValue(WorkScheOutputConstants.DATE);
		
		range = workbook.getWorksheets().getRangeByName("DayMon");
		range.getCellOrNull(0, 0).setValue(WorkScheOutputConstants.DAYMONTH);
		
		range = workbook.getWorksheets().getRangeByName("Day");
		range.getCellOrNull(0, 0).setValue(WorkScheOutputConstants.DAY);
		
		range = workbook.getWorksheets().getRangeByName("Remark");
		range.getCellOrNull(0, 0).setValue(WorkScheOutputConstants.REMARK);
	}
	
	/**
	 * Write display map (48 cells max)
	 * @param cells cell map
	 */
	public void writeDisplayMap(Cells cells, WorkScheduleOutputCondition reportData) {
		List<OutputItemSettingCode> lstItem = reportData.getCode();
		
		// Divide list into smaller lists (max 16 items)
		int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);

        for(int i = 0; i < numOfChunks; i++) {
            int start = i * CHUNK_SIZE;
            int length = Math.min(lstItem.size() - start, CHUNK_SIZE);

            List<OutputItemSettingCode> lstItemRow = lstItem.subList(start, start + length);
            
            //List<Integer> temp = lstItemRow.stream().map(x -> x.v()).collect(Collectors.toList());
            
            //designer.setDataSource(ITEM_DISPLAY_PREFIX + i + 1, temp);
            
            for (int j = 0; j < CHUNK_SIZE; j++) {
            	// Column 4, 6, 8,...
            	// Row 3, 4, 5
            	Cell cell = cells.get(i * 2 + 2, DATA_COLUMN_INDEX[0] + j * 2); 
            	cell.setValue(lstItemRow.get(j).v());
            }
        }
	}
	
	/**
	 * Write daily performance
	 * @param cells
	 * @param reportData
	 */
	public void writeDailyPerformance(Cells cells, int currentRow, IndividualDailyWorkSchedule indDailyWorkSche, PersonalTotal personalTotal) {
		List<ActualValue> lstItem = indDailyWorkSche.getActualValue();
		
		// Divide list into smaller lists (max 16 items)
		int numOfChunks = (int)Math.ceil((double)lstItem.size() / CHUNK_SIZE);

        for(int i = currentRow; i < currentRow + numOfChunks; i++) {
            int start = i * CHUNK_SIZE;
            int length = Math.min(lstItem.size() - start, CHUNK_SIZE);

            List<ActualValue> lstItemRow = lstItem.subList(start, start + length);
            
            //List<Integer> temp = lstItemRow.stream().map(x -> x.v()).collect(Collectors.toList());
            
            //designer.setDataSource(ITEM_DISPLAY_PREFIX + i + 1, temp);
            
            for (int j = 0; j < CHUNK_SIZE; j++) {
            	// Column 4, 6, 8,...
            	// Row 3, 4, 5
            	
            	ActualValue actualValue = lstItemRow.get(j);
            	Cell cell = cells.get(currentRow, DATA_COLUMN_INDEX[0] + j * 2); 
            	cell.setValue(actualValue.getValue());
            	
            	// Calculate personal total
            	Optional<TotalValue> optTotalValue = personalTotal.getPersonalSumTotal().stream().filter(x -> x.getAttendanceId() == actualValue.getAttendanceId()).findFirst();
            	if (optTotalValue.isPresent()) {
            		optTotalValue.get().setValue(optTotalValue.get().getValue() + actualValue.getValue());
            	}
            	else {
            		TotalValue totalValue = new TotalValue();
            		totalValue.setAttendanceId(actualValue.getAttendanceId());
            		totalValue.setValue(actualValue.getValue());
            		personalTotal.getPersonalSumTotal().add(totalValue);
            	}
            }
        }
	}
	
	/**
	 * Write personal total
	 * @param currentRow
	 * @param cells
	 * @param personalTotal
	 * @param employeeId
	 */
	public void writePersonalTotal(int currentRow, Cells cells, PersonalTotal personalTotal, int employeeId) {
		// A6_1
		Cell personalTotalCellTag = cells.get(currentRow, 0);
		personalTotalCellTag.setValue(WorkScheOutputConstants.PERSONAL_TOTAL);
		
		// A6_2
		// Divide list into smaller lists (max 16 items)
		int numOfChunks = (int)Math.ceil((double)personalTotal.getPersonalSumTotal().size() / CHUNK_SIZE);

        for(int i = 0; i < numOfChunks; i++) {
            int start = i * CHUNK_SIZE;
            int length = Math.min(personalTotal.getPersonalSumTotal().size() - start, CHUNK_SIZE);

            List<TotalValue> lstItemRow = personalTotal.getPersonalSumTotal().subList(start, start + length);
            
            //List<Integer> temp = lstItemRow.stream().map(x -> x.v()).collect(Collectors.toList());
            
            //designer.setDataSource(ITEM_DISPLAY_PREFIX + i + 1, temp);
            
            for (int j = 0; j < CHUNK_SIZE; j++) {
            	// Column 4, 6, 8,...
            	// Row 3, 4, 5
            	Cell cell = cells.get(i * 2 + 2, DATA_COLUMN_INDEX[0] + j * 2); 
            	cell.setValue(lstItemRow.get(j).getValue());
            }
        }
	}
	
	/**
	 * Write personal day count
	 * @param currentRow
	 * @param cells
	 * @param employeeId
	 */
	public void writeDayCount(int currentRow, Cells cells, int employeeId) {
		// A7_1
		Cell dayCountTag = cells.get(currentRow, 0);
		dayCountTag.setValue(WorkScheOutputConstants.DAY_COUNT);
		
		// A7_2 -> A7_10
		for (int i = 0; i < WorkScheOutputConstants.DAY_COUNT_TITLES.length; i++) {
			Cell dayTypeTag = cells.get(currentRow, i + 3);
			dayTypeTag.setValue(WorkScheOutputConstants.DAY_COUNT_TITLES[i]);
		}
		
		// A7_11 -> A7_19
		
	}
	
	/**
	 * Write detail work schedule
	 * @param currentRow
	 * @param cells
	 * @param workplaceConfig
	 * @param query
	 * @param condition
	 * @param sum
	 * @throws Exception
	 */
	public void writeDetailedWorkSchedule(int currentRow, Cells cells, WorkplaceConfigInfo workplaceConfig, WorkScheduleOutputQuery query, WorkScheduleOutputCondition condition, TotalSumRowOfDailySchedule sum) throws Exception {
		List<WorkplaceHierarchy> lstHierachy = workplaceConfig.getLstWkpHierarchy();
		
		// Print detailed information recursively
		for (WorkplaceHierarchy hierachy : lstHierachy) {
			// Workplace information
			Optional<WorkplaceConfigInfo> optHierachyInfo = workplaceConfigRepository.find(AppContexts.user().companyId(), query.getEndDate(), hierachy.getWorkplaceId());
			if (optHierachyInfo.isPresent()) {
				WorkplaceConfigInfo hierachyInfo = optHierachyInfo.get();
				
				for (String employeeId: query.getEmployeeId()) {
					// Workplace tag
					Cell workplaceTagCell = cells.get(currentRow, 0);
					workplaceTagCell.setValue(WorkScheOutputConstants.WORKPLACE);
					
					// Workplace info
					WorkplaceInfo workplace = workplaceInfoRepository.findByWkpId(query.getWorkplaceId(), query.getEndDate()).get();
					Cell workplaceInfo = cells.get(currentRow, DATA_COLUMN_INDEX[0]);
					workplaceInfo.setValue(workplace.getWorkplaceId() + " " + workplace.getWorkplaceName());
					
					// Clone a new WorkScheduleOutputQuery with child workplace id
					WorkScheduleOutputQuery cloneWorkSche = query.clone();
					cloneWorkSche.setWorkplaceId(workplace.getWorkplaceId());
					
					currentRow++;
					
					// Print employee detail
					// Employee tag
					Cell employeeTagCell = cells.get(currentRow, 0);
					employeeTagCell.setValue(WorkScheOutputConstants.WORKPLACE);
					
					// Person info
					Person person = personRepository.getByPersonId(employeeId).get();
					PersonNameGroup nameGroup = person.getPersonNameGroup();
					
					Cell employeeInfo = cells.get(currentRow, DATA_COLUMN_INDEX[0]);
					employeeInfo.setValue(employeeId + " " + nameGroup.getPersonName());
					
					// Employment tag
					Cell employmentTag = cells.get(currentRow, DATA_COLUMN_INDEX[1]);
					employmentTag.setValue(WorkScheOutputConstants.EMPLOYMENT);
					
					// TODO: Employment info
					
					// Create personal total
					PersonalTotal personalTotal = new PersonalTotal();
					personalTotal.setEmployeeId(Integer.parseInt(employeeId));
					sum.getPersonalTotal().add(personalTotal);
					
					// Detailed schedule
					DateRange dateRange = new DateRange();
					dateRange.setStartDate(query.getStartDate());
					dateRange.setEndDate(query.getEndDate());
					List<GeneralDate> lstDate = dateRange.toListDate();
					for (GeneralDate currentDate : lstDate) {
						// A5_2
						Cell dateCell = cells.get(currentRow, 1);
						dateCell.setValue(currentDate.toString("mm/dd"));
						
						// Request list 332
						IndividualDailyWorkSchedule indDailyWorkSche = new IndividualDailyWorkSchedule();
						indDailyWorkSche.setEmployeeId(Integer.parseInt(employeeId));
						List<ActualValue> lstActualValue = new ArrayList<>();
						indDailyWorkSche.setActualValue(lstActualValue);
						
						AttendanceResultImport attendanceResultImport = getValueOf(employeeId, currentDate, convertList(condition.getCode(), x -> x.v()));
						attendanceResultImport.getAttendanceItems().stream().map(x -> lstActualValue.add(new ActualValue(x.getItemId(), x.value())));
						
						// A5_3
						Cell dayOfWeekCell = cells.get(currentRow, 2);
						dayOfWeekCell.setValue(currentDate.dayOfWeek());
						
						// Write daily performance (A5_4)
						writeDailyPerformance(cells,currentRow, indDailyWorkSche, personalTotal);
						
						// A5_5
						List<EmployeeDailyPerError> errorList = errorAlarmRepository.find(employeeId, currentDate);
						List<Integer> lstRemarkContentStr = new ArrayList<>();
						Optional<PrintRemarksContent> optRemarkContentLeavingEarly = getRemarkContent(employeeId, currentDate, errorList, RemarksContentChoice.LEAVING_EARLY);
						Optional<PrintRemarksContent> optRemarkContentManualInput = getRemarkContent(employeeId, currentDate, errorList, RemarksContentChoice.MANUAL_INPUT);
						if (optRemarkContentLeavingEarly.isPresent())
							lstRemarkContentStr.add(optRemarkContentLeavingEarly.get().getPrintitem().value);
						if (optRemarkContentManualInput.isPresent())
							lstRemarkContentStr.add(optRemarkContentManualInput.get().getPrintitem().value);
						Cell remarkCell = cells.get(currentRow,35);
						remarkCell.setValue(String.join("、", lstRemarkContentStr.stream().map(x -> String.valueOf(x)).collect(Collectors.toList())));
						
						// A5_1
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
						Cell erAlMark = cells.get(currentRow, 0);
						if (erMark && alMark) {
							erAlMark.setValue(WorkScheOutputConstants.ER + "/" + WorkScheOutputConstants.AL);
						}
						else if (erMark) {
							erAlMark.setValue(WorkScheOutputConstants.ER);
						}
						else if (alMark) {
							erAlMark.setValue(WorkScheOutputConstants.AL);
						}
					}
					
					// Personal total (A6)
					writePersonalTotal(currentRow, cells, personalTotal, Integer.parseInt(employeeId));
					
					// Day count (A7)
				}
				
				// Recursive into lower level workplace
				writeDetailedWorkSchedule(currentRow, cells, hierachyInfo, query, condition, sum);
			}
		}
	}
	
	public AttendanceResultImport getValueOf(String employeeId, GeneralDate workingDate, List<Integer> itemIds) {
		DailyRecordDto itemDtos = this.fullFinder.find(employeeId, workingDate);
		return getAndConvert(employeeId, workingDate, itemDtos, itemIds);
	}
	
	private AttendanceResultImport getAndConvert(String employeeId, GeneralDate workingDate, DailyRecordDto data, List<Integer> itemIds){
		return AttendanceResultImport.builder().employeeId(employeeId).workingDate(workingDate)
				.attendanceItems(AttendanceItemUtil.toItemValues(data, itemIds).stream()
						.map(c -> new AttendanceItemValueImport(c.getValueType().value, c.itemId(), c.value()))
						.collect(Collectors.toList())).build();
	}
	
	public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
	    return from.stream().map(func).collect(Collectors.toList());
	}
	
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
