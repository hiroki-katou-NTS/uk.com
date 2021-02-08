package nts.uk.ctx.exio.infra.entity.exi.extcategory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "OIOMT_EX_ACP_CATEGORY_ITEM")
public class OiomtExAcpCategoryItem  extends UkJpaEntity implements Serializable {
	@EmbeddedId
	public OiomtExAcpCategoryItemPk pk;
	/**	契約コード */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCd;

	/**	外部受入カテゴリ 項目名*/
	@Basic(optional = false)
	@Column(name = "ITEM_NAME")
	public String itemName;
	
	/**	テーブル名 */
	@Basic(optional = false)
	@Column(name = "TABLE_NAME")
	public String tableName;
	
	/**	カラム名 */
	@Basic(optional = false)
	@Column(name = "COLUMN_NAME")
	public String columnName;
	
	/**	データ型 */
	@Basic(optional = false)
	@Column(name = "DATA_TYPE")
	public int dataType;
	
	/**	アルファベット禁止項目 */
	@Basic(optional = false)
	@Column(name = "ALPHA_USE_FLG")
	public Integer alphaUseFlg;
	
	/**主キー区分	 */
	@Basic(optional = false)
	@Column(name = "PRIMARY_KEY_FLG")
	public int primatyKeyFlg;
	
	/**primitiveValue名	 */
	@Basic(optional = false)
	@Column(name = "PRIMITIVE_VALUE_NAME")
	public String primitiveName;
	
	/**小数部桁数	 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_DIGIT")
	public Integer decimalDigit;
	
	/**	小数部単位区分 */
	@Basic(optional = false)
	@Column(name = "DECIMAL_UNIT")
	public Integer decimalUnit;
	
	/**	必須区分 */
	@Basic(optional = false)
	@Column(name = "REQUIRED_FLG")
	public int requiredFlg;
	
	/**数値範囲開始	 */
	@Basic(optional = false)
	@Column(name = "NUMBER_RANGE_START")
	public Double numberRangeStart;
	
	/**	数値範囲終了 */
	@Basic(optional = false)
	@Column(name = "NUMBER_RANGE_END")
	public Double numberRangeEnd;
	
	/**	数値範囲開始２ */
	@Basic(optional = false)
	@Column(name = "NUMBER_RANGE_START2")
	public Double numberRangeStart2;
	
	/**数値範囲終了２	 */
	@Basic(optional = false)
	@Column(name = "NUMBER_RANGE_END2")
	public Double numberRangeEnd2;
	
	/**	特殊区分 */
	@Basic(optional = false)
	@Column(name = "SPECIAL_FLG")
	public int specialFlg;
	
	/**	必須桁数 */
	@Basic(optional = false)
	@Column(name = "REQUIRED_NUMBER")
	public Integer requiredNumber;
	
	/**	表示区分 */
	@Basic(optional = false)
	@Column(name = "DISPLAY_FLG")
	public int displayFlg;
	
	/**	履歴区分 */
	@Basic(optional = false)
	@Column(name = "HISTORY_FLG")
	public Integer historyFlg;
	
	/**	履歴継続区分 */
	@Basic(optional = false)
	@Column(name = "HISTORY_CONTI_FLG")
	public Integer historyContiFlg;
		
	@ManyToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID") })
	public OiomtExAcpCategory acpCategory;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.pk;
	}

}
