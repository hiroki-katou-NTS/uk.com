package nts.uk.ctx.exio.infra.entity.exi.dataformat;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtExAcItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 文字型データ形式設定
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_AC_FM_CHAC")
public class OiomtExAcFmChac extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExAcFmChacPk chrDataFormatSetPk;

	/**
	 * コード編集
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDITING")
	public int cdEditing;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE")
	public int fixedValue;

	/**
	 * 有効桁長
	 */
	@Basic(optional = false)
	@Column(name = "EFFECTIVE_DIGIT_LENGTH")
	public int effectiveDigitLength;

	/**
	 * コード変換コード
	 */
	@Basic(optional = true)
	@Column(name = "CD_CONVERT_CD")
	public String cdConvertCd;

	/**
	 * コード編集方法
	 */
	@Basic(optional = true)
	@Column(name = "CD_EDIT_METHOD")
	public Integer cdEditMethod;

	/**
	 * コード編集桁
	 */
	@Basic(optional = true)
	@Column(name = "CD_EDIT_DIGIT")
	public Integer cdEditDigit;

	/**
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_VAL")
	public String fixedVal;

	/**
	 * 有効桁数開始桁
	 */
	@Basic(optional = true)
	@Column(name = "START_DIGIT")
	public Integer startDigit;

	/**
	 * 有効桁数終了桁
	 */
	@Basic(optional = true)
	@Column(name = "END_DIGIT")
	public Integer endDigit;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "SYSTEM_TYPE", referencedColumnName = "SYSTEM_TYPE", insertable = false, updatable = false),
			@JoinColumn(name = "CONDITION_SET_CD", referencedColumnName = "CONDITION_SET_CD", insertable = false, updatable = false),
			@JoinColumn(name = "ACCEPT_ITEM_NUM", referencedColumnName = "ACCEPT_ITEM_NUMBER", insertable = false, updatable = false) })
	public OiomtExAcItem acceptItem;

	@Override
	protected Object getKey() {
		return chrDataFormatSetPk;
	}

	public OiomtExAcFmChac(String cid, int sysType, String conditionCode, int acceptItemNum, int cdEditing,
			int fixedValue, int effectiveDigitLength, String cdConvertCd, Integer cdEditMethod, Integer cdEditDigit,
			String fixedVal, Integer startDigit, Integer endDigit) {
		super();
		this.chrDataFormatSetPk = new OiomtExAcFmChacPk(cid, sysType, conditionCode, acceptItemNum);
		this.cdEditing = cdEditing;
		this.fixedValue = fixedValue;
		this.effectiveDigitLength = effectiveDigitLength;
		this.cdConvertCd = cdConvertCd;
		this.cdEditMethod = cdEditMethod;
		this.cdEditDigit = cdEditDigit;
		this.fixedVal = fixedVal;
		this.startDigit = startDigit;
		this.endDigit = endDigit;
	}

	public static OiomtExAcFmChac fromDomain(StdAcceptItem item, ChrDataFormatSet domain) {
		return new OiomtExAcFmChac(item.getCid(), item.getSystemType().value, item.getConditionSetCd().v(),
				item.getAcceptItemNumber(), domain.getCdEditing().value, domain.getFixedValue().value,
				domain.getEffectiveDigitLength().value,
				domain.getCdConvertCd().isPresent() ? domain.getCdConvertCd().get().v() : null,
				domain.getCdEditMethod().isPresent() ? domain.getCdEditMethod().get().value : null,
				domain.getCdEditDigit().isPresent() ? domain.getCdEditDigit().get().v() : null,
				domain.getFixedVal().isPresent() ? domain.getFixedVal().get().v() : null,
				domain.getStartDigit().isPresent() ? domain.getStartDigit().get().v() : null,
				domain.getEndDigit().isPresent() ? domain.getEndDigit().get().v() : null);
	}

	public ChrDataFormatSet toDomain() {
		return new ChrDataFormatSet(ItemType.CHARACTER.value, this.cdEditing, this.fixedValue,
				this.effectiveDigitLength, this.cdConvertCd, this.cdEditMethod, this.cdEditDigit, this.fixedVal,
				this.startDigit, this.endDigit);
	}
}
