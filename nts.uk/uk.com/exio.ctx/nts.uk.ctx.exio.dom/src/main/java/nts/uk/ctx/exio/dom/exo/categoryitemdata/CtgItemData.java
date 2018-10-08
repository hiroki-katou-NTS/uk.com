package nts.uk.ctx.exio.dom.exo.categoryitemdata;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.ctx.exio.dom.exo.outputitem.OutputItemName;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 外部出力カテゴリ項目データ
 */

@Getter
public class CtgItemData extends AggregateRoot {

	/**
	 * TBL別名
	 */
	private String tblAlias;

	/**
	 * カテゴリID
	 */
	private CategoryCd categoryId;

	/**
	 * データ型
	 */
	private DataType dataType;

	/**
	 * テーブル
	 */
	private String tableName;

	/**
	 * フィールド
	 */
	private String fieldName;

	/**
	 * 主キー区分
	 */
	private Optional<NotUseAtr> primarykeyClassfication;

	/**
	 * 日付区分
	 */
	private Optional<String> dateClassfication;

	/**
	 * 特殊項目
	 */
	private SpecialItem specialItem;

	/**
	 * 表示テーブル名
	 */
	private String displayTableName;

	/**
	 * 表示区分
	 */
	private NotUseAtr displayClassfication;

	/**
	 * 項目NO
	 */
	private ItemNo itemNo;

	/**
	 * 項目名
	 */
	private OutputItemName itemName;

	/**
	 * 必須区分
	 */
	private NotUseAtr requiredCategory;

	/**
	 * 検索値コード
	 */
	private Optional<String> searchValueCd;

	public CtgItemData(String tblAlias, int dataType, String tableName, String fieldName, int primarykeyClassfication,
			String dateClassfication, int specialItem, String displayTableName, int displayClassfication,
			String itemName, int requiredCategory, String searchValueCd) {
		super();
		this.tblAlias = tblAlias;
		this.dataType = EnumAdaptor.valueOf(dataType, DataType.class);
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.primarykeyClassfication = Objects.isNull(primarykeyClassfication) ? Optional.empty()
				: Optional.of(EnumAdaptor.valueOf(primarykeyClassfication, NotUseAtr.class));
		this.dateClassfication = Optional.ofNullable(dateClassfication);
		this.specialItem = EnumAdaptor.valueOf(specialItem, SpecialItem.class);
		this.displayTableName = displayTableName;
		this.displayClassfication = EnumAdaptor.valueOf(displayClassfication, NotUseAtr.class);
		this.itemName = new OutputItemName(itemName);
		this.requiredCategory = EnumAdaptor.valueOf(requiredCategory, NotUseAtr.class);
		this.searchValueCd = Optional.ofNullable(searchValueCd);
	}

	public CtgItemData(String tblAlias, int categoryId, int dataType, String tableName, String fieldName,
			int primarykeyClassfication, String dateClassfication, int specialItem, String displayTableName,
			int displayClassfication, int itemNo, String itemName, int requiredCategory, String searchValueCd) {
		super();
		this.tblAlias = tblAlias;
		this.categoryId = new CategoryCd(categoryId);
		this.dataType = EnumAdaptor.valueOf(dataType, DataType.class);
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.primarykeyClassfication = Objects.isNull(primarykeyClassfication) ? Optional.empty()
				: Optional.of(EnumAdaptor.valueOf(primarykeyClassfication, NotUseAtr.class));
		this.dateClassfication = Optional.ofNullable(dateClassfication);
		this.specialItem = EnumAdaptor.valueOf(specialItem, SpecialItem.class);
		this.displayTableName = displayTableName;
		this.displayClassfication = EnumAdaptor.valueOf(displayClassfication, NotUseAtr.class);
		this.itemNo = new ItemNo(itemNo);
		this.itemName = new OutputItemName(itemName);
		this.requiredCategory = EnumAdaptor.valueOf(requiredCategory, NotUseAtr.class);
		this.searchValueCd = Optional.ofNullable(searchValueCd);
	}

}
