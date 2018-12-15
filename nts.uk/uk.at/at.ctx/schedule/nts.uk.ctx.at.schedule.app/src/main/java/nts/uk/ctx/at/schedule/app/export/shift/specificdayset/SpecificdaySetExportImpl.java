package nts.uk.ctx.at.schedule.app.export.shift.specificdayset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
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
@DomainID(value = "SpecificdaySet")
public class SpecificdaySetExportImpl implements MasterListData {

	@Inject
	private SpecificdaySetReportRepository specificdaySetReportRepository;

	 @Override
	 public List<SheetData> extraSheets(MasterListExportQuery query) {
		 List<SheetData> sheetDatas = new ArrayList<>();
		 //add the work place sheet
		 SheetData sheetWorkplaceData = new SheetData(getMasterDatasForWorkplace(query), getHeaderColumnsForWorkplace(query),null, null, "職場");
		 sheetDatas.add(sheetWorkplaceData);
		 return sheetDatas;
	 }

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();

		columns.add(new MasterHeaderColumn("年月", "年月", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日", "1日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("2日", "2日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("3日", "3日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("4日", "4日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("5日", "5日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("6日", "6日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("7日", "7日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("8日", "8日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("9日", "9日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("10日", "10日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("11日", "11日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("12日", "12日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("13日", "13日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("14日", "14日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("15日", "15日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("16日", "16日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("17日", "17日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("18日", "18日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("19日", "19日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("20日", "20日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("21日", "21日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("22日", "22日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("23日", "23日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("24日", "24日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("25日", "25日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("26日", "26日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("27日", "27日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("28日", "28日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("29日", "29日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("30日", "30日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("31日", "31日", ColumnTextAlign.CENTER, "", true));

		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<Map<String, List<SpecificdaySetReportData>>> mapSetReportDatas = specificdaySetReportRepository
				.findAllSpecificdaySetCompany(companyId);

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
	
	
	@Override
	public String mainSheetName() {
		return "会社";
	}

	private Optional<MasterData> newCompanyMasterData(String yearMonth,
			Optional<List<SpecificdaySetReportData>> specificdaySetReportDatas) {
		Map<String, Object> data = new HashMap<>();
		if (specificdaySetReportDatas.isPresent()) {
			data.put("年月", yearMonth);
			putEmptyToColumsCompany(data);
			specificdaySetReportDatas.get().stream().sorted(Comparator.comparing(SpecificdaySetReportData::getDay))
					.forEachOrdered(x -> {
						putDataToColumns(data, x);
					});

			return Optional.of(new MasterData(data, null, ""));
		}
		return Optional.empty();
	}

	private void putEmptyToColumsCompany(Map<String, Object> data) {
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			data.put(key, "");
		}
	}

	private void putDataToColumns(Map<String, Object> data, SpecificdaySetReportData setReportData) {
		int day = setReportData.getDay();
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			if (day == i) {
				String value = (String) data.get(key);
				if (value != null) {
					value += "," + setReportData.getSpecificDateItemName().v();
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

		columns.add(new MasterHeaderColumn("年月", "年月", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日", "1日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("2日", "2日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("3日", "3日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("4日", "4日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("5日", "5日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("6日", "6日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("7日", "7日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("8日", "8日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("9日", "9日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("10日", "10日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("11日", "11日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("12日", "12日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("13日", "13日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("14日", "14日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("15日", "15日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("16日", "16日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("17日", "17日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("18日", "18日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("19日", "19日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("20日", "20日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("21日", "21日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("22日", "22日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("23日", "23日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("24日", "24日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("25日", "25日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("26日", "26日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("27日", "27日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("28日", "28日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("29日", "29日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("30日", "30日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("31日", "31日", ColumnTextAlign.CENTER, "", true));

		return columns;
	}
	
	
	
	/**
	 * get data for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterData> getMasterDatasForWorkplace(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<Map<String, List<SpecificdaySetReportData>>> mapSetReportDatas = specificdaySetReportRepository
				.findAllSpecificdaySetCompany(companyId);

		if (mapSetReportDatas.isPresent()) {
			mapSetReportDatas.get().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x -> {
				Optional<MasterData> row = newWorkplaceMasterData(x.getKey(), Optional.ofNullable(x.getValue()));
				if (row.isPresent()) {
					datas.add(row.get());
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
	private Optional<MasterData> newWorkplaceMasterData(String yearMonth,
			Optional<List<SpecificdaySetReportData>> specificdaySetReportDatas) {
		Map<String, Object> data = new HashMap<>();
		if (specificdaySetReportDatas.isPresent()) {
			data.put("年月", yearMonth);
			putEmptyToColumWorkplace(data);
			specificdaySetReportDatas.get().stream().sorted(Comparator.comparing(SpecificdaySetReportData::getDay))
					.forEachOrdered(x -> {
						putDataToColumnsWorkplace(data, x);
					});

			return Optional.of(new MasterData(data, null, ""));
		}
		return Optional.empty();
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumWorkplace(Map<String, Object> data) {
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
	private void putDataToColumnsWorkplace(Map<String, Object> data, SpecificdaySetReportData setReportData) {
		int day = setReportData.getDay();
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			if (day == i) {
				String value = (String) data.get(key);
				if (value != null) {
					value += "," + setReportData.getSpecificDateItemName().v();
				}
				data.put(key, value);
			}

		}
	}
}
