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

@Stateless
@DomainID(value = "MonthlyWorkSetting")
public class WorkMonthlySettingExportImpl implements MasterListData {
	
	@Inject
	private WorkMonthlySettingReportRepository workMonthlySettingReportRepository;

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
					Map<String, List<WorkMonthlySettingReportData>> mapDataByYearMonth = 
					listDataPerOneWp.get().stream().collect(Collectors.groupingBy(WorkMonthlySettingReportData::getYearMonth));
					List<String> yearMonthKeys = mapDataByYearMonth.keySet().stream().sorted().collect(Collectors.toList());
					for(int i = 0; i < yearMonthKeys.size(); i++) {
						String yearMonth = yearMonthKeys.get(i);
						List<WorkMonthlySettingReportData> listDataPerOneRow = mapDataByYearMonth.get(yearMonth);
						Optional<MasterData> row = newWorkSetMasterData(i, yearMonth, Optional.ofNullable(listDataPerOneRow));
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
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			data.put(key, setReportData.getWorkSetName());
		}
	}
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KSM004_98"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KSM004_99"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("年月", TextResource.localize("KSM002_63"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("1日", TextResource.localize("KSM002_64"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("2日", TextResource.localize("KSM002_65"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("3日", TextResource.localize("KSM002_66"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("4日", TextResource.localize("KSM002_67"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("5日", TextResource.localize("KSM002_68"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("6日", TextResource.localize("KSM002_69"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("7日", TextResource.localize("KSM002_70"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("8日", TextResource.localize("KSM002_71"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("9日", TextResource.localize("KSM002_72"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("10日", TextResource.localize("KSM002_73"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("11日", TextResource.localize("KSM002_74"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("12日", TextResource.localize("KSM002_75"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("13日", TextResource.localize("KSM002_76"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("14日", TextResource.localize("KSM002_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("15日", TextResource.localize("KSM002_78"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("16日", TextResource.localize("KSM002_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("17日", TextResource.localize("KSM002_80"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("18日", TextResource.localize("KSM002_81"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("19日", TextResource.localize("KSM002_82"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("20日", TextResource.localize("KSM002_83"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("21日", TextResource.localize("KSM002_84"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("22日", TextResource.localize("KSM002_85"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("23日", TextResource.localize("KSM002_86"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("24日", TextResource.localize("KSM002_87"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("25日", TextResource.localize("KSM002_88"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("26日", TextResource.localize("KSM002_89"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("27日", TextResource.localize("KSM002_90"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("28日", TextResource.localize("KSM002_91"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("29日", TextResource.localize("KSM002_92"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("30日", TextResource.localize("KSM002_93"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("31日", TextResource.localize("KSM002_94"), ColumnTextAlign.LEFT, "", true));

		return columns;
	}

	@Override
	public String mainSheetName() {
		return TextResource.localize("Com_Company");
	}

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		return MasterListData.super.extraSheets(query);
	}
	
}
