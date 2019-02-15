package nts.uk.ctx.at.schedule.app.export.shift.pattern.work;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
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
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID(value = "MonthlyWorkSetting")
public class WorkMonthlySettingExportImpl implements MasterListData {
	
	@Inject
	private WorkMonthlySettingReportRepository workMonthlySettingReportRepository;
	
	@Inject
	private CompanyEventRepository companyEventRepository;

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
//		Period period = specificdaySetReportRepository.getBaseDateByCompany(companyId, query.getStartDate(), query.getEndDate());
		Optional<Map<String, List<WorkMonthlySettingReportData>>> mapSetReportDatas = workMonthlySettingReportRepository
				.findAllWorkMonthlySet(companyId, query.getStartDate(), query.getEndDate());

		if (mapSetReportDatas.isPresent()) {
			mapSetReportDatas.get().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
				Optional<List<WorkMonthlySettingReportData>> listDataPerOneWp = Optional.ofNullable(x.getValue());
				if (listDataPerOneWp.isPresent()) {
					if (listDataPerOneWp.get().get(0).getDate().isPresent()) {
						Map<String, List<WorkMonthlySettingReportData>> mapDataByYearMonth = listDataPerOneWp.get()
								.stream().collect(Collectors.groupingBy(WorkMonthlySettingReportData::getYearMonth));
						List<String> yearMonthKeys = mapDataByYearMonth.keySet().stream().sorted()
								.collect(Collectors.toList());
						for (int i = 0; i < yearMonthKeys.size(); i++) {
							String yearMonth = yearMonthKeys.get(i);
							List<WorkMonthlySettingReportData> listDataPerOneRow = mapDataByYearMonth.get(yearMonth);
							Optional<MasterData> row = newWorkSetMasterData(i, yearMonth,
									Optional.ofNullable(listDataPerOneRow));
							if (row.isPresent()) {
								datas.add(row.get());
							}
						}
					}
					else {
//						datas.add(newWSWithCodeNameMasterData(listDataPerOneWp.get().get(0)));
					}
				}
			});
		}

		return datas;
	}
	
//	private MasterData newWSWithCodeNameMasterData(WorkMonthlySettingReportData setWorkplaceReportData) {
//		
//		Map<String, Object> data = new HashMap<>();
//		// put empty to columns
//		putEmptyToColumWorkSet(data);
//		data.put("コード", setWorkplaceReportData.getPattenCode());
//		data.put("名称", setWorkplaceReportData.getPatternName());
//
//		MasterData masterData = new MasterData(data, null, "");
//		alignDataWorkSet(masterData.getRowData());
//		return masterData;
//	}

	/**
	 * create row data for WorkPlace sheet
	 * @param yearMonth
	 * @param specificdaySetReportDatas
	 * @return
	 */
	private Optional<MasterData> newWorkSetMasterData(Integer index, String yearMonth,
			Optional<List<WorkMonthlySettingReportData>> wOptional) {
		Map<String, Object> data = new HashMap<>();
		if (wOptional.isPresent()) {
			//put empty to columns
			putEmptyToColumWorkSet(data);
			if (index == 0) {
				WorkMonthlySettingReportData setWorkplaceReportData = wOptional.get().get(0);
				data.put("コード", setWorkplaceReportData.getPattenCode());
				data.put("名称", setWorkplaceReportData.getPatternName());
			}
			data.put("年月", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			
			wOptional.get().stream().sorted(Comparator.comparing(WorkMonthlySettingReportData::getDay))
					.forEachOrdered(x -> {
						putDataToColumnsWorkSet(data, x);
					});

			MasterData masterData = new MasterData(data, null, "");
			alignDataWorkSet(masterData.getRowData());
			return Optional.of(masterData);
		}
		return Optional.empty();
	}
	
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataWorkSet(Map<String, MasterCellData> rowData) {
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
	private void putEmptyToColumWorkSet(Map<String, Object> data) {
		data.put("コード", "");
		data.put("名称", "");
		data.put("年月", "");
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
	private void putDataToColumnsWorkSet(Map<String, Object> data, WorkMonthlySettingReportData setReportData) {
		if(setReportData.getWorkSetName().isPresent()) {
			String key = setReportData.getDay() + "日";
			String value = setReportData.getWorkSetName().get();
			
			Optional<GeneralDate> d = setReportData.getDate();
			if (d.isPresent()){
				List<GeneralDate> lst = new ArrayList<>();
				lst.add(d.get());
				String companyId = AppContexts.user().companyId();
				List<CompanyEvent> lstEvent = companyEventRepository.getCompanyEventsByListDate(companyId, lst);
				if (!lstEvent.isEmpty()){
					CompanyEvent e = lstEvent.get(0);
					if (e.getEventName() != null){
						value += "「" + e.getEventName() + "」"; 
					}
				}
			}
			data.put(key, value);
		}
	}
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KSM005_13"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KSM005_14"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("年月", TextResource.localize("KSM005_47"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("1日", TextResource.localize("KSM005_48"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("2日", TextResource.localize("KSM005_49"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("3日", TextResource.localize("KSM005_50"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("4日", TextResource.localize("KSM005_51"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("5日", TextResource.localize("KSM005_52"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("6日", TextResource.localize("KSM005_53"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("7日", TextResource.localize("KSM005_54"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("8日", TextResource.localize("KSM005_55"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("9日", TextResource.localize("KSM005_56"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("10日", TextResource.localize("KSM005_57"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("11日", TextResource.localize("KSM005_58"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("12日", TextResource.localize("KSM005_59"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("13日", TextResource.localize("KSM005_60"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("14日", TextResource.localize("KSM005_61"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("15日", TextResource.localize("KSM005_62"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("16日", TextResource.localize("KSM005_63"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("17日", TextResource.localize("KSM005_64"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("18日", TextResource.localize("KSM005_65"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("19日", TextResource.localize("KSM005_66"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("20日", TextResource.localize("KSM005_67"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("21日", TextResource.localize("KSM005_68"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("22日", TextResource.localize("KSM005_69"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("23日", TextResource.localize("KSM005_70"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("24日", TextResource.localize("KSM005_71"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("25日", TextResource.localize("KSM005_72"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("26日", TextResource.localize("KSM005_73"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("27日", TextResource.localize("KSM005_74"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("28日", TextResource.localize("KSM005_75"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("29日", TextResource.localize("KSM005_76"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("30日", TextResource.localize("KSM005_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("31日", TextResource.localize("KSM005_78"), ColumnTextAlign.LEFT, "", true));

		return columns;
	}

	@Override
	public String mainSheetName() {
		return TextResource.localize("KSM005_3");
	}
	
	

	@Override
	public MasterListMode mainSheetMode() {
		return MasterListMode.FISCAL_YEAR_RANGE;
	}

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		 List<SheetData> sheetDatas = new ArrayList<>();
		 SheetData sheetPersionSet = new SheetData(getMasterDatasForPersionSet(query), 
				 getHeaderColumnsForPersionSet(query),null, null, TextResource.localize("KSM005_6"), MasterListMode.BASE_DATE);
		 sheetDatas.add(sheetPersionSet);
		 
		return sheetDatas;
	}
	
	public List<MasterHeaderColumn> getHeaderColumnsForPersionSet(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KSM005_13"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KSM005_14"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("履歴開始日", TextResource.localize("KSM005_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("履歴終了日", TextResource.localize("KSM005_80"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("対象月間パターン", TextResource.localize("KSM005_22"), ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	
	public List<MasterData> getMasterDatasForPersionSet(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<List<PersionalWorkMonthlySettingReportData>> optReportDatas = workMonthlySettingReportRepository
				.findAllPersionWorkMonthlySet(companyId, query.getBaseDate());
		if(optReportDatas.isPresent()) {
			List<PersionalWorkMonthlySettingReportData> reportDatas = optReportDatas.get();
			reportDatas.stream().sorted(Comparator.comparing(PersionalWorkMonthlySettingReportData::getScd)).forEachOrdered(x -> {
				datas.add(newPersionSetMasterData(x));
			});
		}
		return datas;
	}
	
	
	private MasterData newPersionSetMasterData(PersionalWorkMonthlySettingReportData persionalData) {
		Map<String, Object> data = new HashMap<>();
		putEmptyToColumPersionSet(data);
		data.put("コード", persionalData.getScd());
		data.put("名称", persionalData.getName());
		data.put("履歴開始日", persionalData.getStartDate());
		data.put("履歴終了日", persionalData.getEndDate());
		data.put("対象月間パターン", persionalData.getPatternCode() + persionalData.getPatternName());
		MasterData masterData = new MasterData(data, null, "");
		alignDataPersionSet(masterData.getRowData());
		return masterData;
	}
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataPersionSet(Map<String, MasterCellData> rowData) {
		rowData.get("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("履歴開始日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		rowData.get("履歴終了日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		rowData.get("対象月間パターン").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumPersionSet(Map<String, Object> data) {
		data.put("コード", "");
		data.put("名称", "");
		data.put("履歴開始日", "");
		data.put("履歴終了日", "");
		data.put("対象月間パターン", "");
	}
}
