package nts.uk.ctx.exio.infra.entity.exo.categoryitemdata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 外部出力カテゴリ項目データ
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CTG_ITEM_DATA")
public class OiomtCtgItemData extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtCtgItemDataPk ctgItemDataPk;

	/**
	 * TBL別名
	 */
	@Basic(optional = false)
	@Column(name = "TBL_ALIAS")
	public String tblAlias;

	/**
	 * データ型
	 */
	@Basic(optional = false)
	@Column(name = "DATA_TYPE")
	public int dataType;

	/**
	 * テーブル
	 */
	@Basic(optional = false)
	@Column(name = "TABLE_NAME")
	public String tableName;

	/**
	 * フィールド
	 */
	@Basic(optional = false)
	@Column(name = "FIELD_NAME")
	public String fieldName;

	/**
	 * 主キー区分
	 */
	@Basic(optional = true)
	@Column(name = "PRIMARYKEY_CLASSFICATION")
	public int primarykeyClassfication;

	/**
	 * 日付区分
	 */
	@Basic(optional = true)
	@Column(name = "DATE_CLASSFICATION")
	public String dateClassfication;

	/**
	 * 特殊項目
	 */
	@Basic(optional = false)
	@Column(name = "SPECIAL_ITEM")
	public int specialItem;

	/**
	 * 表示テーブル名
	 */
	@Basic(optional = false)
	@Column(name = "DISPLAY_TABLE_NAME")
	public String displayTableName;

	/**
	 * 表示区分
	 */
	@Basic(optional = false)
	@Column(name = "DISPLAY_CLASSFICATION")
	public int displayClassfication;

	/**
	 * 項目名
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_NAME")
	public String itemName;

	/**
	 * 必須区分
	 */
	@Basic(optional = false)
	@Column(name = "REQUIRED_CATEGORY")
	public int requiredCategory;

	/**
	 * 検索値コード
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_VALUE_CD")
	public String searchValueCd;

	@Override
	protected Object getKey() {
		return ctgItemDataPk;
	}

	public CtgItemData toDomain() {
		return new CtgItemData(this.tblAlias, this.ctgItemDataPk.categoryId, this.dataType, this.tableName,
				this.fieldName, this.primarykeyClassfication, this.dateClassfication, this.specialItem,
				this.displayTableName, this.displayClassfication, this.ctgItemDataPk.itemNo, this.itemName,
				this.requiredCategory, this.searchValueCd);
	}

	public static OiomtCtgItemData toEntity(CtgItemData domain) {
		return new OiomtCtgItemData(new OiomtCtgItemDataPk(domain.getCategoryId().v(), domain.getItemNo().v()),
				domain.getTblAlias(), domain.getDataType().value, domain.getTableName(), domain.getFieldName(),
				domain.getPrimarykeyClassfication().get().value, domain.getDateClassfication().get(), domain.getSpecialItem().value,
				domain.getDisplayTableName(), domain.getDisplayClassfication().value, domain.getItemName().v(),
				domain.getRequiredCategory().value, domain.getSearchValueCd().get());
	}

	public OiomtCtgItemData(OiomtCtgItemDataPk ctgItemDataPk, String tblAlias, int dataType, String tableName,
			String fieldName, int primarykeyClassfication, String dateClassfication, int specialItem,
			String displayTableName, int displayClassfication, String itemName, int requiredCategory,
			String searchValueCd) {
		super();
		this.ctgItemDataPk = ctgItemDataPk;
		this.tblAlias = tblAlias;
		this.dataType = dataType;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.primarykeyClassfication = primarykeyClassfication;
		this.dateClassfication = dateClassfication;
		this.specialItem = specialItem;
		this.displayTableName = displayTableName;
		this.displayClassfication = displayClassfication;
		this.itemName = itemName;
		this.requiredCategory = requiredCategory;
		this.searchValueCd = searchValueCd;
	}

}
