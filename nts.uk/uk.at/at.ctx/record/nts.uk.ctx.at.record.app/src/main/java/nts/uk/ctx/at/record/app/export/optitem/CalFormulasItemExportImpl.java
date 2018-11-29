package nts.uk.ctx.at.record.app.export.optitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.export.optitem.CalFormulasItemExportData;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
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
	private OptionalItemRepository optItemRepo;
	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;
	/** Table 001 **/
	private static final String KMK002_74 = "コードヘッダー";
	private static final String KMK002_75 = "コード名ヘッダー";
	private static final String KMK002_76 = "属性ヘッダー";
	private static final String KMK002_77 = "計算区分ヘッダー";
	private static final String KMK002_78 = "日別/月別ヘッダー";
	private static final String KMK002_79 = "雇用条件区分ヘッダー";
	private static final String KMK002_80 = "単位ヘッダー";
	private static final String KMK002_81 = "上限値ヘッダー";
	private static final String KMK002_82 = "上限値入力ヘッダー";
	private static final String KMK002_83 = "下限値ヘッダー";
	private static final String KMK002_84 = "下限値入力ヘッダー";

	/** Table 002 **/
	private static final String KMK002_85 = "コードヘッダー";
	private static final String KMK002_86 = "コード名ヘッダー";
	private static final String KMK002_87 = "雇用条件区分ヘッダー";
	private static final String EMPLOYMENT_NO_ = "EMPLOYMENT_NO_";

	/** Table 003 **/
	private static final String KMK002_88 = "コードヘッダー";
	private static final String KMK002_89 = "コード名ヘッダー";
	private static final String KMK002_90 = "計算区分ヘッダー";
	private static final String KMK002_91 = "日別/月別ヘッダー";
	private static final String KMK002_92 = "記号ヘッダー";
	private static final String KMK002_93 = "属性ヘッダー";
	private static final String KMK002_94 = "計算式名ヘッダー";
	private static final String KMK002_95 = "計算式設定ヘッダー";
	private static final String KMK002_96 = "日別丸めヘッダー";
	private static final String KMK002_97 = "日別端数処理ヘッダー";
	private static final String KMK002_98 = "月別丸めヘッダー";
	private static final String KMK002_99 = "月別端数処理ヘッダー";
	private static final String KMK002_100 = "マイナス区分ヘッダー";
	private static final String KMK002_101 = "適用計算式ヘッダー";

	/** The Constant TABLE_ONE. */
	private static final String TABLE_ONE = "Table 001";

	/** The Constant TABLE_TWO. */
	private static final String TABLE_TWO = "Table 002";

	/** The Constant TABLE_THREE. */
	private static final String TABLE_THREE = "Table 003";

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<MasterHeaderColumn>> getExtraHeaderColumn(MasterListExportQuery query) {
		Map<String, List<MasterHeaderColumn>> mapColum = new LinkedHashMap<>();
		mapColum.put(TABLE_ONE, this.getHeaderColumnOnes(query));
		mapColum.put(TABLE_TWO, this.getHeaderColumnTwos(query));
		mapColum.put(TABLE_THREE, this.getHeaderColumnThrees(query));
		return mapColum;
	};

	public List<MasterHeaderColumn> getHeaderColumnOnes(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(KMK002_74, TextResource.localize(KMK002_74), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_75, TextResource.localize(KMK002_75), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_76, TextResource.localize(KMK002_76), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_77, TextResource.localize(KMK002_77), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_78, TextResource.localize(KMK002_78), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_79, TextResource.localize(KMK002_79), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_80, TextResource.localize(KMK002_80), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_81, TextResource.localize(KMK002_81), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_82, TextResource.localize(KMK002_82), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_83, TextResource.localize(KMK002_83), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(KMK002_84, TextResource.localize(KMK002_84), ColumnTextAlign.CENTER, "", true));
		return columns;
	}

	public List<MasterHeaderColumn> getHeaderColumnTwos(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterHeaderColumn> columns = new ArrayList<>();
		List<SyEmploymentImport> syEmploymentImport = syEmploymentAdapter.findByCid(companyId);

		columns.add(new MasterHeaderColumn(KMK002_85, TextResource.localize("KMK002_85"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_86, TextResource.localize("KMK002_86"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_87, TextResource.localize("KMK002_87"), ColumnTextAlign.CENTER, "",
				true));
		for (SyEmploymentImport item : syEmploymentImport) {
			columns.add(new MasterHeaderColumn(EMPLOYMENT_NO_ + item.getEmploymentCode(), item.getEmploymentName(),
					ColumnTextAlign.CENTER, "", true));
		}
		return columns;
	}

	public List<MasterHeaderColumn> getHeaderColumnThrees(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(KMK002_88, TextResource.localize("KMK002_88"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_89, TextResource.localize("KMK002_89"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_90, TextResource.localize("KMK002_90"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_91, TextResource.localize("KMK002_91"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_92, TextResource.localize("KMK002_92"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_93, TextResource.localize("KMK002_93"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_94, TextResource.localize("KMK002_94"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_95, TextResource.localize("KMK002_95"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_96, TextResource.localize("KMK002_96"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_97, TextResource.localize("KMK002_97"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_98, TextResource.localize("KMK002_98"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_99, TextResource.localize("KMK002_99"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_100, TextResource.localize("KMK002_100"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(KMK002_101, TextResource.localize("KMK002_101"), ColumnTextAlign.CENTER, "",
				true));

		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<MasterData>> getExtraMasterData(MasterListExportQuery query) {
		Map<String, List<MasterData>> mapTableData = new LinkedHashMap<>();
		mapTableData.put(TABLE_ONE, this.getMasterDataOne(query));
		mapTableData.put(TABLE_TWO, this.getMasterDataTwo(query));
		mapTableData.put(TABLE_THREE, this.getMasterDataThree(query));
		return mapTableData;
	};

	private List<MasterData> getMasterDataOne(MasterListExportQuery query) {
		String languageId = query.getLanguageId();
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		List<OptionalItem> listOptionalItem = optItemRepo.findAll(companyId);
		if (CollectionUtil.isEmpty(listOptionalItem)) {
			throw new BusinessException("Msg_7");
		} else {
			listOptionalItem.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				data.put(KMK002_74, c.getOptionalItemNo());
				data.put(KMK002_75, c.getOptionalItemName());
				data.put(KMK002_76, c.getOptionalItemAtr());
				data.put(KMK002_77, c.getUsageAtr());
				data.put(KMK002_78, c.getPerformanceAtr());
				data.put(KMK002_79, c.getEmpConditionAtr());
				data.put(KMK002_80, c.getUnit());
				data.put(KMK002_81, c.getCalcResultRange().getUpperLimit().value);

				// Check CalcResultRange
				switch (c.getOptionalItemAtr()) {
				case NUMBER:
					data.put(KMK002_82, c.getCalcResultRange().getNumberRange().get().getUpperLimit().orElse(null));
					break;
				case AMOUNT:
					data.put(KMK002_82, c.getCalcResultRange().getAmountRange().get().getUpperLimit().orElse(null));
					break;
				case TIME:
					data.put(KMK002_82, c.getCalcResultRange().getTimeRange().get().getUpperLimit().orElse(null));
					break;
				}

				data.put(KMK002_83, c.getCalcResultRange().getLowerLimit().value);
				// Check CalcResultRange
				switch (c.getOptionalItemAtr()) {
				case NUMBER:
					data.put(KMK002_84, c.getCalcResultRange().getNumberRange().get().getLowerLimit().orElse(null));
					break;
				case AMOUNT:
					data.put(KMK002_84, c.getCalcResultRange().getAmountRange().get().getLowerLimit().orElse(null));
					break;
				case TIME:
					data.put(KMK002_84, c.getCalcResultRange().getTimeRange().get().getLowerLimit().orElse(null));
					break;
				}
				datas.add(new MasterData(data, null, ""));
			});
		}

		return datas;
	}

	private List<MasterData> getMasterDataTwo(MasterListExportQuery query) {
		String languageId = query.getLanguageId();
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		List<OptionalItem> listOptionalItemNo = optItemRepo.findAll(companyId);
		List<CalFormulasItemExportData> listOptionalItem = optItemRepo.findAllCalFormulasItem(companyId, languageId);
		List<SyEmploymentImport> syEmploymentImport = syEmploymentAdapter.findByCid(companyId);

		if (CollectionUtil.isEmpty(listOptionalItem)) {
			throw new BusinessException("Msg_7");
		} else {
			listOptionalItemNo.forEach(x -> {
				Integer optionalItemNo = x.getOptionalItemNo().v();
				List<CalFormulasItemExportData> calFormulasByItemNo = listOptionalItem.stream()
						.filter(op -> optionalItemNo.equals(Integer.valueOf(op.getOptionalItemNo())))
						.collect(Collectors.toList());
				calFormulasByItemNo.forEach(formulaItem -> {
					Map<String, Object> data = new HashMap<>();
					data.put(KMK002_85, formulaItem.getOptionalItemNo());
					data.put(KMK002_86, formulaItem.getOptionalItemName());
					data.put(KMK002_87, formulaItem.getEmpConditionAtr());
					syEmploymentImport.forEach(header -> {
						data.put(EMPLOYMENT_NO_ + header.getEmploymentCode(),
								formulaItem.getEmpConditions().get(header.getEmploymentCode()));
					});
					datas.add(new MasterData(data, null, ""));
				});
			});
		}
		return datas;
	}

	private List<MasterData> getMasterDataThree(MasterListExportQuery query) {
		String languageId = query.getLanguageId();
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		List<CalFormulasItemExportData> listOptionalItem = optItemRepo.findAllCalFormulasItem(companyId, languageId);
		if (CollectionUtil.isEmpty(listOptionalItem)) {
			throw new BusinessException("Msg_7");
		} else {
			listOptionalItem.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();

				datas.add(new MasterData(data, null, ""));
			});
		}

		return datas;
	}

}
