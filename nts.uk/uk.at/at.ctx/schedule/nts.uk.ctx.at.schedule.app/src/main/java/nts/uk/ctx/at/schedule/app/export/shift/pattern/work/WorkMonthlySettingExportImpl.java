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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.shared.app.find.workingconditionitem.WorkingConditionItemFinder;
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
	
	@Inject
	private WorkingConditionItemFinder workingConditionItemFinder;

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
//		data.put("?????????", setWorkplaceReportData.getPattenCode());
//		data.put("??????", setWorkplaceReportData.getPatternName());
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
				data.put("?????????", setWorkplaceReportData.getPattenCode());
				data.put("??????", setWorkplaceReportData.getPatternName());
			}
			data.put("??????", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			
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
		rowData.get("?????????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("??????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("??????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		for (int i = 1; i <= 31; i++) {
			String key = i + "???";
			rowData.get(key).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		}
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumWorkSet(Map<String, Object> data) {
		data.put("?????????", "");
		data.put("??????", "");
		data.put("??????", "");
		for (int i = 1; i <= 31; i++) {
			String key = i + "???";
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
			String key = setReportData.getDay() + "???";
			String value = setReportData.getWorkSetName().get();
			
			/*Optional<GeneralDate> d = setReportData.getDate();
			if (d.isPresent()){
				List<GeneralDate> lst = new ArrayList<>();
				lst.add(d.get());
				String companyId = AppContexts.user().companyId();
				List<CompanyEvent> lstEvent = companyEventRepository.getCompanyEventsByListDate(companyId, lst);
				if (!CollectionUtil.isEmpty(lstEvent)){
					CompanyEvent e = lstEvent.get(0);
					if (e.getEventName() != null){
						value += "???" + e.getEventName() + "???"; 
					}
				}
			}*/
			if (setReportData.getEventName() != null){
				value += "???" + setReportData.getEventName() + "???"; 
			}
			data.put(key, value);
		}
	}
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("?????????", TextResource.localize("KSM005_13"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("??????", TextResource.localize("KSM005_14"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("??????", TextResource.localize("KSM005_47"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("1???", TextResource.localize("KSM005_48"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("2???", TextResource.localize("KSM005_49"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("3???", TextResource.localize("KSM005_50"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("4???", TextResource.localize("KSM005_51"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("5???", TextResource.localize("KSM005_52"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("6???", TextResource.localize("KSM005_53"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("7???", TextResource.localize("KSM005_54"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("8???", TextResource.localize("KSM005_55"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("9???", TextResource.localize("KSM005_56"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("10???", TextResource.localize("KSM005_57"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("11???", TextResource.localize("KSM005_58"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("12???", TextResource.localize("KSM005_59"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("13???", TextResource.localize("KSM005_60"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("14???", TextResource.localize("KSM005_61"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("15???", TextResource.localize("KSM005_62"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("16???", TextResource.localize("KSM005_63"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("17???", TextResource.localize("KSM005_64"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("18???", TextResource.localize("KSM005_65"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("19???", TextResource.localize("KSM005_66"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("20???", TextResource.localize("KSM005_67"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("21???", TextResource.localize("KSM005_68"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("22???", TextResource.localize("KSM005_69"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("23???", TextResource.localize("KSM005_70"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("24???", TextResource.localize("KSM005_71"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("25???", TextResource.localize("KSM005_72"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("26???", TextResource.localize("KSM005_73"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("27???", TextResource.localize("KSM005_74"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("28???", TextResource.localize("KSM005_75"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("29???", TextResource.localize("KSM005_76"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("30???", TextResource.localize("KSM005_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("31???", TextResource.localize("KSM005_78"), ColumnTextAlign.LEFT, "", true));

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
		columns.add(new MasterHeaderColumn("?????????", TextResource.localize("KSM005_13"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("??????", TextResource.localize("KSM005_14"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("???????????????", TextResource.localize("KSM005_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("???????????????", TextResource.localize("KSM005_80"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("????????????????????????", TextResource.localize("KSM005_22"), ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	
	public List<MasterData> getMasterDatasForPersionSet(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<List<PersionalWorkMonthlySettingReportData>> optReportDatas = workMonthlySettingReportRepository
				.findAllPersionWorkMonthlySet(companyId, query.getBaseDate());
		if(optReportDatas.isPresent()) {
			List<PersionalWorkMonthlySettingReportData> reportDatas = optReportDatas.get();
			List<String> lstSids = new ArrayList<>();
			reportDatas.stream().forEachOrdered(x -> {
				if (!lstSids.contains(x.getSid())){
					lstSids.add(x.getSid());
				}
			});
			List<String> lstSidExcepts = new ArrayList<>();
			if (!lstSids.isEmpty()){
				lstSidExcepts = workingConditionItemFinder.findBySidsAndNewestHistory(lstSids);
			}
			if (!lstSidExcepts.isEmpty()){
				reportDatas.stream().sorted(Comparator.comparing(PersionalWorkMonthlySettingReportData::getScd));
				
				for (PersionalWorkMonthlySettingReportData x : reportDatas) {
					if (!lstSidExcepts.contains(x.getSid())){
						datas.add(newPersionSetMasterData(x));
					}
				}
			}
		}
		return datas;
	}
	
	
	private MasterData newPersionSetMasterData(PersionalWorkMonthlySettingReportData persionalData) {
		Map<String, Object> data = new HashMap<>();
		putEmptyToColumPersionSet(data);
		data.put("?????????", persionalData.getScd());
		data.put("??????", persionalData.getName());
		data.put("???????????????", persionalData.getStartDate());
		data.put("???????????????", persionalData.getEndDate());
		data.put("????????????????????????", persionalData.getPatternCode() + persionalData.getPatternName());
		MasterData masterData = new MasterData(data, null, "");
		alignDataPersionSet(masterData.getRowData());
		return masterData;
	}
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataPersionSet(Map<String, MasterCellData> rowData) {
		rowData.get("?????????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("??????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.get("???????????????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		rowData.get("???????????????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		rowData.get("????????????????????????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumPersionSet(Map<String, Object> data) {
		data.put("?????????", "");
		data.put("??????", "");
		data.put("???????????????", "");
		data.put("???????????????", "");
		data.put("????????????????????????", "");
	}
}
