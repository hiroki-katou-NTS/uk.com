package nts.uk.file.com.app.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMaster;
import nts.uk.ctx.bs.employee.dom.jobtitle.sequence.SequenceMasterRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "Sequence")
public class SequenceMasterExportImpl implements MasterListData{

	/** The Constant LANGUAGE_ID_JAPAN. */
	public static final String LANGUAGE_ID_JAPAN = "ja";

	/** The Constant TABLE_ONE. */
	private static final String TABLE_ONE = "Table 001";

	/** The Constant TABLE_TWO. */
	private static final String TABLE_TWO = "Table 002";

	/** The Constant START_COL. */
	public static final int START_COL = 1;
	
	/** The Constant START_BREAKDOWN_ITEM. */
	public static final int START_BREAKDOWN_ITEM = 2;
	
	/** The Constant NUMBER_COLS_END. */
	private static final String NUMBER_COLS_END = "Column End";

	/** The Constant NAME_VALUE_A7_5. */
	private static final String NAME_VALUE_A7_5 = "KMK010_66";

	/** The Constant NAME_VALUE_A9_4. */
	private static final String NAME_VALUE_A9_6 = "KMK010_67";
	
	/** The Constant NUMBER_COLS_1. */
	private static final String NUMBER_COLS_1 = "Column 1";
	
	/** The Constant NAME_VALUE_A5_1. */
	private static final String NAME_VALUE_A5_1 = "KMK010_49";
	
	/** The start col. */
	private int startCol = 0;
	
	@Inject
	private SequenceMasterRepository sequenceMasterRepository;

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		List<MasterData> masterDatas = new ArrayList<>();
		
		
		return null;
	}
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(NUMBER_COLS_1, TextResource.localize(NAME_VALUE_A5_1), ColumnTextAlign.LEFT,
				"", true));
		return columns;
	}

	@Override
	public Map<String, List<MasterData>> getExtraMasterData(MasterListExportQuery query) {
		Map<String, List<MasterData>> mapTableData = new LinkedHashMap<>();
		mapTableData.put(TABLE_ONE, this.getMasterDataOne(query));
		mapTableData.put(TABLE_TWO, this.getMasterDataTwo(query));
		
		return null;
	};
	
	@Override
	public Map<String, List<MasterHeaderColumn>> getExtraHeaderColumn(MasterListExportQuery query) {
		Map<String, List<MasterHeaderColumn>> mapColum = new LinkedHashMap<>();
		mapColum.put(TABLE_ONE, this.getHeaderColumnOnes(query));
		mapColum.put(TABLE_TWO, this.getHeaderColumnTwos(query));
		
		return mapColum;
	};
	
	private List<MasterData> getMasterDataOne(MasterListExportQuery query) {
		List<MasterData> masterDatas = new ArrayList<>();

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		
		
		
		
		
		
		
		
		return null;
	}
	
	private List<MasterData> getMasterDataTwo(MasterListExportQuery query) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		
		List<MasterData> datas = new ArrayList<>();
		
		List<SequenceMaster> listSequenceMaster = sequenceMasterRepository.findByCompanyId(companyId);
		
		if(CollectionUtil.isEmpty(listSequenceMaster)){
			throw new BusinessException("Msg_393");
		}else{
			listSequenceMaster.stream().forEach(c ->{
				Map<String, Object> data = new HashMap<>();
				data.put("コード", c.getSequenceCode());
				data.put("名称", c.getSequenceName());
				data.put("順位", c.getOrder());
				datas.add(new MasterData(data, null, ""));
			});
		}
		
		return null;
	}
	

	private boolean isLanugeJapan(String languageId) {
		return languageId.equals(LANGUAGE_ID_JAPAN);
	}
	
	/**
	 * Gets the header column ones.
	 *
	 * @return the header column ones
	 */
	public List<MasterHeaderColumn> getHeaderColumnOnes(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		if (!this.isLanugeJapan(query.getLanguageId())) {
			columns.add(new MasterHeaderColumn(NUMBER_COLS_END, TextResource.localize(NAME_VALUE_A7_5),
					ColumnTextAlign.LEFT, "", true));
		}
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("CMM013_5"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("職歴開始日", TextResource.localize(""), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("職歴終了", TextResource.localize(""), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("CMM013_12"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("36協定対象外", TextResource.localize("CMM013_52"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("序列コード", TextResource.localize("CMM013_14"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("序列名", TextResource.localize("CMM013_15"), ColumnTextAlign.LEFT,
				"", true));
		
		return columns;
	}
	
	/**
	 * Gets the header column twos.
	 *
	 * @return the header column twos
	 */
	public List<MasterHeaderColumn> getHeaderColumnTwos(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		if (!this.isLanugeJapan(query.getLanguageId())) {
			columns.add(new MasterHeaderColumn(NUMBER_COLS_END, TextResource.localize(NAME_VALUE_A9_6),
					ColumnTextAlign.LEFT, "", true));
		}
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("CMM013_48"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("CMM013_48"), ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("順位", TextResource.localize(""), ColumnTextAlign.RIGHT,
				"", true));
		return columns;
	}
	
	private String toUse(Boolean use) {
		if (use) {
			return "o";
		}
		return "-";
	}

	

}
