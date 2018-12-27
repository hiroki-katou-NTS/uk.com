
package nts.uk.file.at.app.export.shift.businesscalendar.daycalendar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.Period;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WkpConfigInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WorkplaceHierarchyDto;
import nts.uk.ctx.bs.employee.app.find.workplace.config.info.WorkplaceConfigInfoFinder;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 *
 * SpecificDay Setting export implements
 * 
 * @author HiepTH
 *
 */
@Stateless
@DomainID(value = "DayCalendar")
public class DayCalendarExportImpl implements MasterListData {

	@Inject
	private PublicHolidayRepository publicHolidayRepository;

	@Inject
	private DayCalendarReportRepository dayCalendarReportRepository;
	
	@Inject
	private WorkplaceConfigInfoFinder workplaceConfigInfoFinder;
	
//	private Period period;
	
	 @Override
	 public List<SheetData> extraSheets(MasterListExportQuery query) {
		 String companyId = AppContexts.user().companyId();
//		 period = dayCalendarReportRepository.getBaseDateByCompany(companyId, query.getStartDate(), query.getEndDate());
		 List<SheetData> sheetDatas = new ArrayList<>();
		 //add the work place sheet
		 SheetData sheetCompanyData = new SheetData(getMasterDatasCompany(query), getHeaderColumnsCompany(query),null, null, TextResource.localize("KSM004_95"));
		 SheetData sheetWorkplaceData = new SheetData(getMasterDatasForWorkplace(query), 
				 getHeaderColumnsForWorkplace(query),null, null, TextResource.localize("KSM004_101"));
		 SheetData sheetClassData = new SheetData(getMasterDatasForClass(query), 
				 getHeaderColumnsForClass(query),null, null, TextResource.localize("KSM004_102"));
		 sheetDatas.add(sheetCompanyData);
		 sheetDatas.add(sheetWorkplaceData);
		 sheetDatas.add(sheetClassData);
		 return sheetDatas;
	 }
	 
	@Override
	public String mainSheetName() {
		return TextResource.localize("KSM004_56");
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("年月日", TextResource.localize("KSM004_23"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("祝日名称", TextResource.localize("KSM004_24"), ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		publicHolidayRepository.getpHolidayWhileDate(companyId, query.getStartDate(), query.getEndDate()).stream()
			.sorted(Comparator.comparing(PublicHoliday::getDate)).forEachOrdered(x -> {
				Map<String, Object> row = new HashMap<>();
				putEmptyToColumHoliday(row);
				row.put("年月日", x.getDate());
				row.put("祝日名称", x.getHolidayName());
				MasterData rowData = new MasterData(row, null, "");
				alignDataHoliday(rowData);
				datas.add(rowData);
			});

		return datas;
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumHoliday(Map<String, Object> data) {
		data.put("年月日", "");
		data.put("祝日名称", "");
	}
	
	/**
	 * align data holiday
	 * @param rowData
	 */
	private void alignDataHoliday(MasterData rowData) {
		rowData.cellAt("年月日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		rowData.cellAt("祝日名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
	}
	
	private void getHeaderColumnsDate(List<MasterHeaderColumn> columns) {
		columns.add(new MasterHeaderColumn("1日", TextResource.localize("KSM004_64"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("2日", TextResource.localize("KSM004_65"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("3日", TextResource.localize("KSM004_66"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("4日", TextResource.localize("KSM004_67"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("5日", TextResource.localize("KSM004_68"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("6日", TextResource.localize("KSM004_69"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("7日", TextResource.localize("KSM004_70"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("8日", TextResource.localize("KSM004_71"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("9日", TextResource.localize("KSM004_72"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("10日", TextResource.localize("KSM004_73"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("11日", TextResource.localize("KSM004_74"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("12日", TextResource.localize("KSM004_75"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("13日", TextResource.localize("KSM004_76"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("14日", TextResource.localize("KSM004_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("15日", TextResource.localize("KSM004_78"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("16日", TextResource.localize("KSM004_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("17日", TextResource.localize("KSM004_80"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("18日", TextResource.localize("KSM004_81"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("19日", TextResource.localize("KSM004_82"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("20日", TextResource.localize("KSM004_83"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("21日", TextResource.localize("KSM004_84"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("22日", TextResource.localize("KSM004_85"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("23日", TextResource.localize("KSM004_86"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("24日", TextResource.localize("KSM004_87"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("25日", TextResource.localize("KSM004_88"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("26日", TextResource.localize("KSM004_89"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("27日", TextResource.localize("KSM004_90"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("28日", TextResource.localize("KSM004_91"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("29日", TextResource.localize("KSM004_92"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("30日", TextResource.localize("KSM004_93"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("31日", TextResource.localize("KSM004_94"), ColumnTextAlign.LEFT, "", true));
	}

	public List<MasterHeaderColumn> getHeaderColumnsCompany(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("年月", TextResource.localize("KSM004_63"), ColumnTextAlign.LEFT, "", true));
		getHeaderColumnsDate(columns);
		return columns;
	}

	private List<MasterData> getMasterDatasCompany(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		
		Optional<Map<String, List<CompanyCalendarReportData>>> mapSetReportDatas = dayCalendarReportRepository
				.findCalendarCompanyByDate(companyId, query.getStartDate(), query.getEndDate());

		if (mapSetReportDatas.isPresent()) {
			mapSetReportDatas.get().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
				Optional<MasterData> row = newCompanyMasterData(x.getKey(), Optional.ofNullable(x.getValue()));
				if (row.isPresent()) {
					datas.add(row.get());
				}
			});

		}

		return datas;
	}
	

	private Optional<MasterData> newCompanyMasterData(String yearMonth,
			Optional<List<CompanyCalendarReportData>> companyCalendarReportDatas) {
		Map<String, Object> data = new HashMap<>();
		if (companyCalendarReportDatas.isPresent()) {
			data.put("年月", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			putEmptyToColumsCompany(data);
			companyCalendarReportDatas.get().stream().sorted(Comparator.comparing(CompanyCalendarReportData::getDay))
					.forEachOrdered(x -> {
						putDataToColumnsCompany(data, x);
					});

			MasterData rowMaster = new MasterData(data, null, "");
			alignDataCompany(rowMaster.getRowData());
			return Optional.of(rowMaster);
		}
		return Optional.empty();
	}
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataCompany(Map<String, MasterCellData> rowData) {
		rowData.get("年月").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			rowData.get(key).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		}
	}

	private void putEmptyToColumsCompany(Map<String, Object> data) {
		
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			data.put(key, "");
		}
	}

	private void putDataToColumnsCompany(Map<String, Object> data, CompanyCalendarReportData setReportData) {
		int day = setReportData.getDay();
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			if (day == i) {
				String value = (String) data.get(key);
				if (value != null && !value.isEmpty()) {
					value += "," + setReportData.getWorkingDayAtrName();
				}
				else if (value != null && value.isEmpty()){
					value += setReportData.getWorkingDayAtrName();
				}
				data.put(key, value);
			}
		}
	}
	
	/**
	 * get header for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterHeaderColumn> getHeaderColumnsForWorkplace(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KSM004_99"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KSM004_100"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("年月", TextResource.localize("KSM004_63"), ColumnTextAlign.LEFT, "", true));
		getHeaderColumnsDate(columns);

		return columns;
	}
	/**
	 * 
	 * @param workplaceHierarchyDtos
	 * @return
	 */
	private List<WorkplaceHierarchyDto> spreadOutWorkplaceInfos(List<WorkplaceHierarchyDto> workplaceHierarchyDtos) {
		List<WorkplaceHierarchyDto> listWorkplaceHierarchyDtos = new ArrayList<>();
		workplaceHierarchyDtos.stream().forEach(x -> {
			listWorkplaceHierarchyDtos.add(x);
			if (!CollectionUtil.isEmpty(x.getChilds())) {
				listWorkplaceHierarchyDtos.addAll(spreadOutWorkplaceInfos(x.getChilds()));
			}
			
		});
		return listWorkplaceHierarchyDtos;
	}
	
	/**
	 * get data for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterData> getMasterDatasForWorkplace(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<Map<String, List<WorkplaceCalendarReportData>>> mapSetReportDatas = dayCalendarReportRepository
				.findCalendarWorkplaceByDate(companyId, query.getStartDate(), query.getEndDate());
		
		WkpConfigInfoFindObject wkpConfigInfoFindObject = new WkpConfigInfoFindObject();
		wkpConfigInfoFindObject.setSystemType(2);
		wkpConfigInfoFindObject.setBaseDate(GeneralDate.ymd(9999, 12, 31));
		wkpConfigInfoFindObject.setRestrictionOfReferenceRange(true);
		List<WorkplaceHierarchyDto> workplaceHierarchyDtos = spreadOutWorkplaceInfos(workplaceConfigInfoFinder.findAllByBaseDate(wkpConfigInfoFindObject));
		Map<String, WorkplaceHierarchyDto> mapWorkPlace = workplaceHierarchyDtos.stream()
				.collect(Collectors.toMap(WorkplaceHierarchyDto::getCode, Function.identity()));
		
		if (mapSetReportDatas.isPresent()) {
			mapSetReportDatas.get().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
				WorkplaceHierarchyDto workplaceHierarchyDto = mapWorkPlace.get(x.getKey());
				Optional<List<WorkplaceCalendarReportData>> listDataPerOneWp = Optional.ofNullable(x.getValue());
				if (listDataPerOneWp.isPresent()) {
					Map<String, List<WorkplaceCalendarReportData>> mapDataByYearMonth = 
					listDataPerOneWp.get().stream().collect(Collectors.groupingBy(WorkplaceCalendarReportData::getYearMonth));
					List<String> yearMonthKeys = mapDataByYearMonth.keySet().stream().sorted().collect(Collectors.toList());
					for(int i = 0; i < yearMonthKeys.size(); i++) {
						String yearMonth = yearMonthKeys.get(i);
						List<WorkplaceCalendarReportData> listDataPerOneRow = mapDataByYearMonth.get(yearMonth);
						Optional<MasterData> row = newWorkplaceMasterData(i, yearMonth, Optional.ofNullable(listDataPerOneRow), workplaceHierarchyDto);
						if (row.isPresent()) {
							datas.add(row.get());
						}
					}
				}
			});

		}

		return datas;
	}
	
	/**
	 * create row data for WorkPlace sheet
	 * @param yearMonth
	 * @param specificdaySetReportDatas
	 * @return
	 */
	private Optional<MasterData> newWorkplaceMasterData(Integer index, String yearMonth,
			Optional<List<WorkplaceCalendarReportData>> specificdaySetReportDatas, WorkplaceHierarchyDto workplaceHierarchyDto) {
		Map<String, Object> data = new HashMap<>();
		if (specificdaySetReportDatas.isPresent()) {
			//put empty to columns
			putEmptyToColumWorkplace(data);
			if (index == 0) {
				if (workplaceHierarchyDto != null) {
					data.put("コード", workplaceHierarchyDto.getCode());
					data.put("名称", workplaceHierarchyDto.getName());
				}
			}
			data.put("年月", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			
			specificdaySetReportDatas.get().stream().sorted(Comparator.comparing(WorkplaceCalendarReportData::getDay))
					.forEachOrdered(x -> {
						putDataToColumnsWorkplace(data, x);
					});

			MasterData masterData = new MasterData(data, null, "");
			alignDataWorkspace(masterData.getRowData());
			return Optional.of(masterData);
		}
		return Optional.empty();
	}
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataWorkspace(Map<String, MasterCellData> rowData) {
		rowData.get("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("年月").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			rowData.get(key).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		}
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumWorkplace(Map<String, Object> data) {
		data.put("コード", "");
		data.put("名称", "");
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			data.put(key, "");
		}
	}

	/**
	 * 
	 * @param data
	 * @param setReportData
	 */
	private void putDataToColumnsWorkplace(Map<String, Object> data, WorkplaceCalendarReportData setReportData) {
		int day = setReportData.getDay();
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			if (day == i) {
				String value = (String) data.get(key);
				if (value != null && !value.isEmpty()) {
					value += "," + setReportData.getWorkingDayAtrName();
				}
				else if (value != null && value.isEmpty()){
					value += setReportData.getWorkingDayAtrName();
				}
				data.put(key, value);
			}

		}
	}
	
	
	/**
	 * get header for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterHeaderColumn> getHeaderColumnsForClass(MasterListExportQuery query) {
		return getHeaderColumnsForWorkplace(query);
	}
	
	
	
	/**
	 * get data for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterData> getMasterDatasForClass(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<Map<String, List<ClassCalendarReportData>>> mapSetReportDatas = dayCalendarReportRepository
				.findCalendarClassByDate(companyId, query.getStartDate(), query.getEndDate());

		if (mapSetReportDatas.isPresent()) {
			mapSetReportDatas.get().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
				Optional<List<ClassCalendarReportData>> listDataPerOneWp = Optional.ofNullable(x.getValue());
				if (listDataPerOneWp.isPresent()) {
					Map<String, List<ClassCalendarReportData>> mapDataByYearMonth = 
					listDataPerOneWp.get().stream().collect(Collectors.groupingBy(ClassCalendarReportData::getYearMonth));
					List<String> yearMonthKeys = mapDataByYearMonth.keySet().stream().sorted().collect(Collectors.toList());
					for(int i = 0; i < yearMonthKeys.size(); i++) {
						String yearMonth = yearMonthKeys.get(i);
						List<ClassCalendarReportData> listDataPerOneRow = mapDataByYearMonth.get(yearMonth);
						Optional<MasterData> row = newClassMasterData(i, yearMonth, Optional.ofNullable(listDataPerOneRow));
						if (row.isPresent()) {
							datas.add(row.get());
						}
					}
				}
			});

		}

		return datas;
	}
	
	/**
	 * create row data for WorkPlace sheet
	 * @param yearMonth
	 * @param specificdaySetReportDatas
	 * @return
	 */
	private Optional<MasterData> newClassMasterData(Integer index, String yearMonth,
			Optional<List<ClassCalendarReportData>> classCalendarReportDatas) {
		Map<String, Object> data = new HashMap<>();
		if (classCalendarReportDatas.isPresent()) {
			//put empty to columns
			putEmptyToColumClass(data);
			if (index == 0) {
				ClassCalendarReportData setWorkplaceReportData = classCalendarReportDatas.get().get(0);
				data.put("コード", setWorkplaceReportData.getClassId());
				data.put("名称", setWorkplaceReportData.getClassName());
			}
			data.put("年月", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			
			classCalendarReportDatas.get().stream().sorted(Comparator.comparing(ClassCalendarReportData::getDay))
					.forEachOrdered(x -> {
						putDataToColumnsClass(data, x);
					});

			MasterData masterData = new MasterData(data, null, "");
			alignDataClass(masterData.getRowData());
			return Optional.of(masterData);
		}
		return Optional.empty();
	}
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataClass(Map<String, MasterCellData> rowData) {
		rowData.get("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("年月").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			rowData.get(key).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		}
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumClass(Map<String, Object> data) {
		data.put("コード", "");
		data.put("名称", "");
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			data.put(key, "");
		}
	}

	/**
	 * 
	 * @param data
	 * @param setReportData
	 */
	private void putDataToColumnsClass(Map<String, Object> data, ClassCalendarReportData setReportData) {
		int day = setReportData.getDay();
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			if (day == i) {
				String value = (String) data.get(key);
				if (value != null && !value.isEmpty()) {
					value += "," + setReportData.getWorkingDayAtrName();
				}
				else if (value != null && value.isEmpty()){
					value += setReportData.getWorkingDayAtrName();
				}
				data.put(key, value);
			}

		}
	}
}
