package nts.uk.file.at.app.export.otpitem;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value = "CalFormulasItem")
public class CalFormulasItemExportImpl implements MasterListData {

	@Inject
	private CalFormulasItemRepository calFormulasItemRepository;

	// /** The Constant TABLE_ONE. */
	// private static final String TABLE_ONE = "Table 001";
	//
	// /** The Constant TABLE_TWO. */
	// private static final String TABLE_TWO = "Table 002";
	//
	// /** The Constant TABLE_THREE. */
	// private static final String TABLE_THREE = "Table 003";

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_76, TextResource.localize(CalFormulasItemColumn.KMK002_76), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_77, TextResource.localize(CalFormulasItemColumn.KMK002_77), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_78, TextResource.localize(CalFormulasItemColumn.KMK002_78), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_79, TextResource.localize(CalFormulasItemColumn.KMK002_79), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_80, TextResource.localize(CalFormulasItemColumn.KMK002_80), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_81, TextResource.localize(CalFormulasItemColumn.KMK002_81), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_82, TextResource.localize(CalFormulasItemColumn.KMK002_82), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_83, TextResource.localize(CalFormulasItemColumn.KMK002_83), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_84, TextResource.localize(CalFormulasItemColumn.KMK002_84), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_85, TextResource.localize(CalFormulasItemColumn.KMK002_85), ColumnTextAlign.RIGHT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_86, TextResource.localize(CalFormulasItemColumn.KMK002_86), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_87, TextResource.localize(CalFormulasItemColumn.KMK002_87), ColumnTextAlign.RIGHT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_88, TextResource.localize(CalFormulasItemColumn.KMK002_88), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_89, TextResource.localize(CalFormulasItemColumn.KMK002_89), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_90, TextResource.localize(CalFormulasItemColumn.KMK002_90), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_91, TextResource.localize(CalFormulasItemColumn.KMK002_91), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_92, TextResource.localize(CalFormulasItemColumn.KMK002_92), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_93, TextResource.localize(CalFormulasItemColumn.KMK002_93), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_94, TextResource.localize(CalFormulasItemColumn.KMK002_94), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_95, TextResource.localize(CalFormulasItemColumn.KMK002_95), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_96, TextResource.localize(CalFormulasItemColumn.KMK002_96), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_97, TextResource.localize(CalFormulasItemColumn.KMK002_97), ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		datas = calFormulasItemRepository.getDataTableOneExport(companyId);
		return datas;
	}

	// @Override
	// public Map<String, List<MasterHeaderColumn>>
	// getExtraHeaderColumn(MasterListExportQuery query) {
	// Map<String, List<MasterHeaderColumn>> mapColum = new LinkedHashMap<>();
	// mapColum.put(TABLE_ONE, this.getHeaderColumnOnes(query));
	// mapColum.put(TABLE_TWO, this.getHeaderColumnTwos(query));
	// mapColum.put(TABLE_THREE, this.getHeaderColumnThrees(query));
	// return mapColum;
	// };

	// public List<MasterHeaderColumn> getHeaderColumnOnes(MasterListExportQuery
	// query) {
	// List<MasterHeaderColumn> columns = new ArrayList<>();
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_74,
	// TextResource.localize(KMK002_74),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_75,
	// TextResource.localize(KMK002_75),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_76,
	// TextResource.localize(KMK002_76),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_77,
	// TextResource.localize(KMK002_77),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_78,
	// TextResource.localize(KMK002_78),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_79,
	// TextResource.localize(KMK002_79),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_80,
	// TextResource.localize(KMK002_80),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_81,
	// TextResource.localize(KMK002_81),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_82,
	// TextResource.localize(KMK002_82),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_83,
	// TextResource.localize(KMK002_83),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_84,
	// TextResource.localize(KMK002_84),
	// ColumnTextAlign.CENTER, "", true));
	// return columns;
	// }
	//
	// public List<MasterHeaderColumn> getHeaderColumnTwos(MasterListExportQuery
	// query) {
	// String companyId = AppContexts.user().companyId();
	// List<MasterHeaderColumn> columns = new ArrayList<>();
	//
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_85,
	// TextResource.localize("KMK002_85"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_86,
	// TextResource.localize("KMK002_86"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_87,
	// TextResource.localize("KMK002_87"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_104,
	// TextResource.localize("KMK002_104"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_105,
	// TextResource.localize("KMK002_105"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_106,
	// TextResource.localize("KMK002_106"),
	// ColumnTextAlign.CENTER, "", true));
	//
	// return columns;
	// }
	//
	// public List<MasterHeaderColumn>
	// getHeaderColumnThrees(MasterListExportQuery query) {
	// List<MasterHeaderColumn> columns = new ArrayList<>();
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_88,
	// TextResource.localize("KMK002_88"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_89,
	// TextResource.localize("KMK002_89"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_90,
	// TextResource.localize("KMK002_90"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_91,
	// TextResource.localize("KMK002_91"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_92,
	// TextResource.localize("KMK002_92"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_93,
	// TextResource.localize("KMK002_93"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_94,
	// TextResource.localize("KMK002_94"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_95,
	// TextResource.localize("KMK002_95"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_96,
	// TextResource.localize("KMK002_96"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_97,
	// TextResource.localize("KMK002_97"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_98,
	// TextResource.localize("KMK002_98"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_99,
	// TextResource.localize("KMK002_99"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_100,
	// TextResource.localize("KMK002_100"),
	// ColumnTextAlign.CENTER, "", true));
	// columns.add(new MasterHeaderColumn(CalFormulasItemColumn.KMK002_101,
	// TextResource.localize("KMK002_101"),
	// ColumnTextAlign.CENTER, "", true));
	//
	// return columns;
	// }

	/*
	 * @Override public Map<String, List<MasterData>>
	 * getExtraMasterData(MasterListExportQuery query) { Map<String,
	 * List<MasterData>> mapTableData = new LinkedHashMap<>();
	 * mapTableData.put(TABLE_ONE, this.getMasterDataOne(query));
	 * mapTableData.put(TABLE_TWO, this.getMasterDataTwo(query));
	 * mapTableData.put(TABLE_THREE, this.getMasterDataThree(query)); return
	 * mapTableData; };
	 * 
	 * private List<MasterData> getMasterDataOne(MasterListExportQuery query) {
	 * String companyId = AppContexts.user().companyId(); List<MasterData> datas
	 * = new ArrayList<>(); // List<OptionalItem> listOptionalItem =
	 * optItemRepo.findAll(companyId); // if
	 * (CollectionUtil.isEmpty(listOptionalItem)) { // throw new
	 * BusinessException("Msg_7"); // } else { //
	 * listOptionalItem.stream().forEach(c -> { // Map<String, Object> data =
	 * new HashMap<>(); // data.put(KMK002_74, c.getOptionalItemNo()); //
	 * data.put(KMK002_75, c.getOptionalItemName()); // data.put(KMK002_76,
	 * c.getOptionalItemAtr()); // data.put(KMK002_77, c.getUsageAtr()); //
	 * data.put(KMK002_78, c.getPerformanceAtr()); // data.put(KMK002_79,
	 * c.getEmpConditionAtr()); // data.put(KMK002_80, c.getUnit()); //
	 * data.put(KMK002_81, c.getCalcResultRange().getUpperLimit().value); // //
	 * // Check CalcResultRange // switch (c.getOptionalItemAtr()) { // case
	 * NUMBER: // data.put(KMK002_82, //
	 * c.getCalcResultRange().getNumberRange().get().getUpperLimit().orElse(null
	 * )); // break; // case AMOUNT: // data.put(KMK002_82, //
	 * c.getCalcResultRange().getAmountRange().get().getUpperLimit().orElse(null
	 * )); // break; // case TIME: // data.put(KMK002_82, //
	 * c.getCalcResultRange().getTimeRange().get().getUpperLimit().orElse(null))
	 * ; // break; // } // // data.put(KMK002_83,
	 * c.getCalcResultRange().getLowerLimit().value); // // Check
	 * CalcResultRange // switch (c.getOptionalItemAtr()) { // case NUMBER: //
	 * data.put(KMK002_84, //
	 * c.getCalcResultRange().getNumberRange().get().getLowerLimit().orElse(null
	 * )); // break; // case AMOUNT: // data.put(KMK002_84, //
	 * c.getCalcResultRange().getAmountRange().get().getLowerLimit().orElse(null
	 * )); // break; // case TIME: // data.put(KMK002_84, //
	 * c.getCalcResultRange().getTimeRange().get().getLowerLimit().orElse(null))
	 * ; // break; // } // datas.add(new MasterData(data, null, "")); // }); //
	 * }
	 * 
	 * datas = calFormulasItemRepository.getDataTableOneExport(companyId);
	 * return datas; }
	 * 
	 * private List<MasterData> getMasterDataTwo(MasterListExportQuery query) {
	 * String companyId = AppContexts.user().companyId(); List<MasterData> datas
	 * = new ArrayList<>(); // List<OptionalItem> listOptionalItemNo = //
	 * optItemRepo.findAll(companyId); // List<CalFormulasItemExportData>
	 * listOptionalItem = //
	 * calFormulasItemRepository.findAllCalFormulasItem(companyId, //
	 * languageId); // List<SyEmploymentImport> syEmploymentImport = //
	 * syEmploymentAdapter.findByCid(companyId);
	 * 
	 * // if (CollectionUtil.isEmpty(listOptionalItem)) { // throw new
	 * BusinessException("Msg_7"); // } else { // listOptionalItemNo.forEach(x
	 * -> { // Integer optionalItemNo = x.getOptionalItemNo().v(); //
	 * List<CalFormulasItemExportData> calFormulasByItemNo = //
	 * listOptionalItem.stream() // .filter(op -> //
	 * optionalItemNo.equals(Integer.valueOf(op.getOptionalItemNo()))) //
	 * .collect(Collectors.toList()); // calFormulasByItemNo.forEach(formulaItem
	 * -> { // Map<String, Object> data = new HashMap<>(); //
	 * data.put(KMK002_85, formulaItem.getOptionalItemNo()); //
	 * data.put(KMK002_86, formulaItem.getOptionalItemName()); //
	 * data.put(KMK002_87, formulaItem.getEmpConditionAtr()); //
	 * syEmploymentImport.forEach(header -> { // data.put(EMPLOYMENT_NO_ +
	 * header.getEmploymentCode(), //
	 * formulaItem.getEmpConditions().get(header.getEmploymentCode())); // });
	 * // datas.add(new MasterData(data, null, "")); // }); // }); // }
	 * 
	 * datas = calFormulasItemRepository.getDataTableTwoExport(companyId);
	 * return datas; }
	 * 
	 * private List<MasterData> getMasterDataThree(MasterListExportQuery query)
	 * { String companyId = AppContexts.user().companyId(); List<MasterData>
	 * datas = new ArrayList<>(); // List<CalFormulasItemTableExportData>
	 * listOptionalItem = // optItemRepo.findAllCalFormulasTableItem(companyId,
	 * // languageId); // if (CollectionUtil.isEmpty(listOptionalItem)) { //
	 * throw new BusinessException("Msg_7"); // } else { //
	 * listOptionalItem.stream().forEach(c -> { // Map<String, Object> data =
	 * new HashMap<>(); // // datas.add(new MasterData(data, null, "")); // });
	 * // }
	 * 
	 * datas = calFormulasItemRepository.getDataTableThreeExport(companyId);
	 * return datas; }
	 */
}
