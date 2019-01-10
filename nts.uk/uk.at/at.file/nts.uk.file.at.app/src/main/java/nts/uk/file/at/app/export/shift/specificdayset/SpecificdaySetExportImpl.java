
package nts.uk.file.at.app.export.shift.specificdayset;

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
@DomainID(value = "SpecificdaySet")
public class SpecificdaySetExportImpl implements MasterListData {

	@Inject
	private SpecificdaySetReportRepository specificdaySetReportRepository;
	
	@Inject
	private WorkplaceConfigInfoFinder workplaceConfigInfoFinder;
	
	 @Override
	 public List<SheetData> extraSheets(MasterListExportQuery query) {
		 List<SheetData> sheetDatas = new ArrayList<>();
		 //add the work place sheet
		 SheetData sheetWorkplaceData = new SheetData(getMasterDatasForWorkplace(query), getHeaderColumnsForWorkplace(query),null, null, TextResource.localize("Com_Workplace"));
		 sheetDatas.add(sheetWorkplaceData);
		 return sheetDatas;
	 }

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();

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
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		
//		Period period = specificdaySetReportRepository.getBaseDateByCompany(companyId, query.getStartDate(), query.getEndDate());
		Optional<Map<String, List<SpecificdaySetCompanyReportData>>> mapSetReportDatas = specificdaySetReportRepository
				.findAllSpecificdaySetCompany(companyId, query.getStartDate(), query.getEndDate());

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
		return TextResource.localize("Com_Company");
	}

	private Optional<MasterData> newCompanyMasterData(String yearMonth,
			Optional<List<SpecificdaySetCompanyReportData>> specificdaySetReportDatas) {
		Map<String, Object> data = new HashMap<>();
		if (specificdaySetReportDatas.isPresent()) {
			data.put("年月", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			putEmptyToColumsCompany(data);
			specificdaySetReportDatas.get().stream().sorted(Comparator.comparing(SpecificdaySetCompanyReportData::getDay))
					.forEachOrdered(x -> {
						putDataToColumns(data, x);
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

	private void putDataToColumns(Map<String, Object> data, SpecificdaySetCompanyReportData setReportData) {
		int day = setReportData.getDay();
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			if (day == i) {
				String value = (String) data.get(key);
				if (value != null && !value.isEmpty()) {
					value += "," + setReportData.getSpecificDateItemName().v();
				}
				else if (value != null && value.isEmpty()){
					value += setReportData.getSpecificDateItemName().v();
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

		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KSM002_95"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KSM002_96"), ColumnTextAlign.LEFT, "", true));
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
	
	
	
	/**
	 * get data for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterData> getMasterDatasForWorkplace(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		// Period period =
		// specificdaySetReportRepository.getBaseDateByCompany(companyId,
		// query.getStartDate(), query.getEndDate());
		Optional<Map<String, List<SpecificdaySetWorkplaceReportData>>> mapSetReportDatas = specificdaySetReportRepository
				.findAllSpecificdaySetWorkplace(companyId, query.getStartDate(), query.getEndDate());

		WkpConfigInfoFindObject wkpConfigInfoFindObject = new WkpConfigInfoFindObject();
		wkpConfigInfoFindObject.setSystemType(2);
		wkpConfigInfoFindObject.setBaseDate(GeneralDate.ymd(9999, 12, 31));
		wkpConfigInfoFindObject.setRestrictionOfReferenceRange(true);
		List<WorkplaceHierarchyDto> workplaceHierarchyDtos = spreadOutWorkplaceInfos(
				workplaceConfigInfoFinder.findAllByBaseDate(wkpConfigInfoFindObject));

		Map<String, List<WorkplaceHierarchyDto>> mapWorkPlace = workplaceHierarchyDtos.stream()
				.filter(x -> x.getCode() != null && x.getName() != null)
				.collect(Collectors.groupingBy(WorkplaceHierarchyDto::getCode));

		if (mapSetReportDatas.isPresent()) {
			workplaceHierarchyDtos.forEach(dto -> {
				String workplaceCode = dto.getCode();
				Optional<List<SpecificdaySetWorkplaceReportData>> dataByCode = Optional
						.ofNullable(mapSetReportDatas.get().get(workplaceCode));
				if (dataByCode.isPresent()) {
					dataByCode.get().stream().forEach(x -> {
						x.setHierarchyCode(dto.getHierarchyCode());
					});
				}
			});

			mapSetReportDatas.get().entrySet().stream().sorted((e1, e2) -> {
				List<SpecificdaySetWorkplaceReportData> list1 = e1.getValue();
				List<SpecificdaySetWorkplaceReportData> list2 = e2.getValue();
				if (!CollectionUtil.isEmpty(list1) && !CollectionUtil.isEmpty(list2) 
						&& list1.get(0).getHierarchyCode() != null && list2.get(0).getHierarchyCode() != null)
					return list1.get(0).getHierarchyCode().compareTo(list2.get(0).getHierarchyCode());
				else
					return 0;
			}).forEachOrdered(x -> {
				Optional<List<WorkplaceHierarchyDto>> workplaceHierarchyListByCode = Optional
						.ofNullable(mapWorkPlace.get(x.getKey()));
				Optional<WorkplaceHierarchyDto> workplaceHierarchyDto = Optional.empty();
				if (workplaceHierarchyListByCode.isPresent()) {
					workplaceHierarchyDto = Optional.ofNullable(workplaceHierarchyListByCode.get().get(0));
					Optional<List<SpecificdaySetWorkplaceReportData>> listDataPerOneWp = Optional
							.ofNullable(x.getValue());
					if (listDataPerOneWp.isPresent()) {
						Map<String, List<SpecificdaySetWorkplaceReportData>> mapDataByYearMonth = listDataPerOneWp.get()
								.stream()
								.collect(Collectors.groupingBy(SpecificdaySetWorkplaceReportData::getYearMonth));
						List<String> yearMonthKeys = mapDataByYearMonth.keySet().stream().sorted()
								.collect(Collectors.toList());
						for (int i = 0; i < yearMonthKeys.size(); i++) {
							String yearMonth = yearMonthKeys.get(i);
							List<SpecificdaySetWorkplaceReportData> listDataPerOneRow = mapDataByYearMonth
									.get(yearMonth);
							Optional<MasterData> row = newWorkplaceMasterData(i, yearMonth,
									Optional.ofNullable(listDataPerOneRow), workplaceHierarchyDto);
							if (row.isPresent()) {
								datas.add(row.get());
							}
						}
					}
				}
			});

		}

		return datas;
	}
	
	
	/* 
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
	 * create row data for WorkPlace sheet
	 * @param yearMonth
	 * @param specificdaySetReportDatas
	 * @return
	 */
	private Optional<MasterData> newWorkplaceMasterData(Integer index, String yearMonth,
			Optional<List<SpecificdaySetWorkplaceReportData>> specificdaySetReportDatas, Optional<WorkplaceHierarchyDto> workplaceHierarchyDto) {
		Map<String, Object> data = new HashMap<>();
		if (specificdaySetReportDatas.isPresent()) {
			//put empty to columns
			putEmptyToColumWorkplace(data);
			if (index == 0) {
				if (workplaceHierarchyDto.isPresent()) {
					data.put("コード", workplaceHierarchyDto.get().getCode());
					data.put("名称", workplaceHierarchyDto.get().getName());
				}
				else {
					data.put("コード", specificdaySetReportDatas.get().get(0).getWorkplaceCode());
					data.put("名称", "マスタ未登録");
				}
			}
			data.put("年月", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			
			specificdaySetReportDatas.get().stream().sorted(Comparator.comparing(SpecificdaySetWorkplaceReportData::getDay))
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
	private void putDataToColumnsWorkplace(Map<String, Object> data, SpecificdaySetWorkplaceReportData setReportData) {
		int day = setReportData.getDay();
		for (int i = 1; i <= 31; i++) {
			String key = i + "日";
			if (day == i) {
				String value = (String) data.get(key);
				if (value != null && !value.isEmpty()) {
					value += "," + setReportData.getSpecificDateItemName().v();
				}
				else if (value != null && value.isEmpty()){
					value += setReportData.getSpecificDateItemName().v();
				}
				data.put(key, value);
			}

		}
	}
}
