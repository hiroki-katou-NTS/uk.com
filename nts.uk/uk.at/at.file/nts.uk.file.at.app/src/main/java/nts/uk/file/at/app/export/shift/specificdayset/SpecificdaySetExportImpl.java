
package nts.uk.file.at.app.export.shift.specificdayset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

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
		// add the work place sheet
		SheetData sheetWorkplaceData = new SheetData(getMasterDatasForWorkplace(query),
				getHeaderColumnsForWorkplace(query), null, null, TextResource.localize("KSM002_99"),MasterListMode.FISCAL_YEAR_RANGE);
		sheetDatas.add(sheetWorkplaceData);
		
		return sheetDatas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();

		columns.add(new MasterHeaderColumn("??????", TextResource.localize("KSM002_63"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("1???", TextResource.localize("KSM002_64"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("2???", TextResource.localize("KSM002_65"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("3???", TextResource.localize("KSM002_66"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("4???", TextResource.localize("KSM002_67"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("5???", TextResource.localize("KSM002_68"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("6???", TextResource.localize("KSM002_69"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("7???", TextResource.localize("KSM002_70"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("8???", TextResource.localize("KSM002_71"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("9???", TextResource.localize("KSM002_72"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("10???", TextResource.localize("KSM002_73"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("11???", TextResource.localize("KSM002_74"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("12???", TextResource.localize("KSM002_75"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("13???", TextResource.localize("KSM002_76"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("14???", TextResource.localize("KSM002_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("15???", TextResource.localize("KSM002_78"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("16???", TextResource.localize("KSM002_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("17???", TextResource.localize("KSM002_80"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("18???", TextResource.localize("KSM002_81"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("19???", TextResource.localize("KSM002_82"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("20???", TextResource.localize("KSM002_83"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("21???", TextResource.localize("KSM002_84"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("22???", TextResource.localize("KSM002_85"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("23???", TextResource.localize("KSM002_86"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("24???", TextResource.localize("KSM002_87"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("25???", TextResource.localize("KSM002_88"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("26???", TextResource.localize("KSM002_89"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("27???", TextResource.localize("KSM002_90"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("28???", TextResource.localize("KSM002_91"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("29???", TextResource.localize("KSM002_92"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("30???", TextResource.localize("KSM002_93"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("31???", TextResource.localize("KSM002_94"), ColumnTextAlign.LEFT, "", true));

		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();

		// Period period =
		// specificdaySetReportRepository.getBaseDateByCompany(companyId,
		// query.getStartDate(), query.getEndDate());
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
		return TextResource.localize("KSM002_98");
	}
	
	

	@Override
	public MasterListMode mainSheetMode() {
		return MasterListMode.FISCAL_YEAR_RANGE;
	}

	private Optional<MasterData> newCompanyMasterData(String yearMonth,
			Optional<List<SpecificdaySetCompanyReportData>> specificdaySetReportDatas) {
		Map<String, Object> data = new HashMap<>();
		if (specificdaySetReportDatas.isPresent()) {
			data.put("??????", yearMonth.substring(0, 4) + "/" + yearMonth.substring(4, yearMonth.length()));
			putEmptyToColumsCompany(data);
			specificdaySetReportDatas.get().stream()
					.sorted(Comparator.comparing(SpecificdaySetCompanyReportData::getDay)).forEachOrdered(x -> {
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
		rowData.get("??????").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
		for (int i = 1; i <= 31; i++) {
			String key = i + "???";
			rowData.get(key).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		}
	}

	private void putEmptyToColumsCompany(Map<String, Object> data) {

		for (int i = 1; i <= 31; i++) {
			String key = i + "???";
			data.put(key, "");
		}
	}

	private void putDataToColumns(Map<String, Object> data, SpecificdaySetCompanyReportData setReportData) {
		int day = setReportData.getDay();
		String key = day + "???";
		String value = (String) data.get(key);
		if (value != null && !value.isEmpty()) {
			value += "," + setReportData.getSpecificDateItemName().v();
		} else if (value != null && value.isEmpty()) {
			value += setReportData.getSpecificDateItemName().v();
		}
		data.put(key, value);
	}

	/**
	 * get header for WorkPlace Sheet
	 * 
	 * @param query
	 * @return
	 */
	private List<MasterHeaderColumn> getHeaderColumnsForWorkplace(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();

		columns.add(new MasterHeaderColumn("?????????", TextResource.localize("KSM002_95"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("??????", TextResource.localize("KSM002_96"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("??????", TextResource.localize("KSM002_63"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("1???", TextResource.localize("KSM002_64"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("2???", TextResource.localize("KSM002_65"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("3???", TextResource.localize("KSM002_66"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("4???", TextResource.localize("KSM002_67"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("5???", TextResource.localize("KSM002_68"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("6???", TextResource.localize("KSM002_69"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("7???", TextResource.localize("KSM002_70"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("8???", TextResource.localize("KSM002_71"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("9???", TextResource.localize("KSM002_72"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("10???", TextResource.localize("KSM002_73"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("11???", TextResource.localize("KSM002_74"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("12???", TextResource.localize("KSM002_75"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("13???", TextResource.localize("KSM002_76"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("14???", TextResource.localize("KSM002_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("15???", TextResource.localize("KSM002_78"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("16???", TextResource.localize("KSM002_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("17???", TextResource.localize("KSM002_80"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("18???", TextResource.localize("KSM002_81"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("19???", TextResource.localize("KSM002_82"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("20???", TextResource.localize("KSM002_83"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("21???", TextResource.localize("KSM002_84"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("22???", TextResource.localize("KSM002_85"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("23???", TextResource.localize("KSM002_86"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("24???", TextResource.localize("KSM002_87"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("25???", TextResource.localize("KSM002_88"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("26???", TextResource.localize("KSM002_89"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("27???", TextResource.localize("KSM002_90"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("28???", TextResource.localize("KSM002_91"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("29???", TextResource.localize("KSM002_92"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("30???", TextResource.localize("KSM002_93"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("31???", TextResource.localize("KSM002_94"), ColumnTextAlign.LEFT, "", true));

		return columns;
	}

	/**
	 * get data for WorkPlace Sheet
	 * 
	 * @param query
	 * @return
	 */
	private List<MasterData> getMasterDatasForWorkplace(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		Optional<Map<String, List<SpecificdaySetWorkplaceReportData>>> mapSetReportDatas = specificdaySetReportRepository
				.findAllSpecificdaySetWorkplace(companyId, query.getStartDate(), query.getEndDate());

		WkpConfigInfoFindObject wkpConfigInfoFindObject = new WkpConfigInfoFindObject();
		wkpConfigInfoFindObject.setSystemType(2);
		wkpConfigInfoFindObject.setBaseDate(GeneralDate.ymd(9999, 12, 31));
		wkpConfigInfoFindObject.setRestrictionOfReferenceRange(true);
		List<WorkplaceHierarchyDto> workplaceHierarchyDtos = spreadOutWorkplaceInfos(
				workplaceConfigInfoFinder.findAllByBaseDate(wkpConfigInfoFindObject));

		if (mapSetReportDatas.isPresent()) {
			//put hierarchy code to data
			if (!CollectionUtil.isEmpty(workplaceHierarchyDtos)) {
				workplaceHierarchyDtos.stream().forEach(x -> {
					String wpId = x.getWorkplaceId();
					String hierarchyCode = x.getHierarchyCode();
					String code = x.getCode();
					String name = x.getName();

					Optional<List<SpecificdaySetWorkplaceReportData>> dataByWpId = mapSetReportDatas.isPresent()
							? Optional.ofNullable(mapSetReportDatas.get().get(wpId)) : Optional.empty();

					if (dataByWpId.isPresent()) {
						dataByWpId.get().stream().forEach(y -> {
							y.setHierarchyCode(Optional.of(hierarchyCode));
							y.setWorkplaceCode(Optional.of(code));
							y.setWorkplaceName(Optional.of(name));
						});
					}
				});
			}
			
			//sort by hierarchy code
			mapSetReportDatas.get().entrySet().stream().sorted((e1, e2) -> {
				List<SpecificdaySetWorkplaceReportData> list1 = e1.getValue();
				List<SpecificdaySetWorkplaceReportData> list2 = e2.getValue();
				if (!CollectionUtil.isEmpty(list1) && !CollectionUtil.isEmpty(list2)) {
					Optional<String> hierarchyCode1 = list1.get(0).getHierarchyCode();
					Optional<String> hierarchyCode2 = list2.get(0).getHierarchyCode();
					if (hierarchyCode1.isPresent() && hierarchyCode2.isPresent())
						return hierarchyCode1.get().compareTo(hierarchyCode2.get());
					else if (hierarchyCode1.isPresent() && !hierarchyCode2.isPresent())
						return 1;
					else if (!hierarchyCode1.isPresent() && hierarchyCode2.isPresent())
						return -1;
					else
						return 0;
				}
				return 0;
			}).forEachOrdered(dto -> {
				//export 
				List<SpecificdaySetWorkplaceReportData> dataByCode = dto.getValue();
				if (!CollectionUtil.isEmpty(dataByCode)) {
					SpecificdaySetWorkplaceReportData firstObject = dataByCode.get(0);
					if (firstObject.getHierarchyCode().isPresent()) {
//							|| (!firstObject.getHierarchyCode().isPresent()
//							&& !firstObject.getWorkplaceCode().isPresent())) {
						Map<String, List<SpecificdaySetWorkplaceReportData>> mapDataByYearMonth = dataByCode.stream()
								.collect(Collectors.groupingBy(SpecificdaySetWorkplaceReportData::getYearMonth));
						AtomicInteger index = new AtomicInteger(0);
						mapDataByYearMonth.keySet().stream().sorted().collect(Collectors.toList()).stream()
								.forEach(yearMonth -> {
									List<SpecificdaySetWorkplaceReportData> listDataPerOneRow = mapDataByYearMonth
											.get(yearMonth);
									datas.add(newWorkplaceMasterData(index.get(), Optional.of(yearMonth),
											Optional.ofNullable(listDataPerOneRow)));
									index.getAndIncrement();

								});
					}
				}
			});
		}

		return datas;
	}

	/*
	 * @param workplaceHierarchyDtos
	 * 
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
	 * 
	 * @param yearMonth
	 * @param specificdaySetReportDatas
	 * @return
	 */
	private MasterData newWorkplaceMasterData(Integer index, Optional<String> yearMonth,
			Optional<List<SpecificdaySetWorkplaceReportData>> specificdaySetReportDatas) {
		Map<String, Object> data = new HashMap<>();

		// put empty to columns
		putEmptyToColumWorkplace(data);
		if (index == 0) {
			SpecificdaySetWorkplaceReportData firstObject = specificdaySetReportDatas.get().get(0);
			if (firstObject.getWorkplaceCode().isPresent()) {
				data.put("?????????", firstObject.getWorkplaceCode().get());
				data.put("??????", firstObject.getWorkplaceName().get());
			}
			else {
				data.put("?????????", "");
				data.put("??????", TextResource.localize("KSM002_100"));
			}
		}

		if (specificdaySetReportDatas.isPresent() && yearMonth.isPresent()) {
			data.put("??????",
					yearMonth.get().substring(0, 4) + "/" + yearMonth.get().substring(4, yearMonth.get().length()));

			specificdaySetReportDatas.get().stream()
					.sorted(Comparator.comparing(SpecificdaySetWorkplaceReportData::getDay)).forEachOrdered(x -> {
						putDataToColumnsWorkplace(data, x);
					});
		}

		MasterData masterData = new MasterData(data, null, "");
		alignDataWorkspace(masterData.getRowData());
		return masterData;
	}

	/**
	 * 
	 * @param rowData
	 */
	private void alignDataWorkspace(Map<String, MasterCellData> rowData) {
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
	private void putEmptyToColumWorkplace(Map<String, Object> data) {
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
	private void putDataToColumnsWorkplace(Map<String, Object> data, SpecificdaySetWorkplaceReportData setReportData) {
		String key = setReportData.getDay() + "???";
		String value = (String) data.get(key);
		if (value != null && !value.isEmpty()) {
			value += "," + setReportData.getSpecificDateItemName().v();
		} else if (value != null && value.isEmpty()) {
			value += setReportData.getSpecificDateItemName().v();
		}
		data.put(key, value);
	}
}
