package nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部出力文字型データ形式設定（項目単位）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_FM_CHAC")
public class OiomtCharacterDfs extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtCharacterDfsPk characterDfsPk;

	/**
	 * 有効桁数
	 */
	@Basic(optional = false)
	@Column(name = "EFFECT_DIGIT_LENGTH")
	public int effectDigitLength;

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

	/**
	 * コード編集
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDIT")
	public int cdEditting;

	/**
	 * コード編集桁
	 */
	@Basic(optional = true)
	@Column(name = "CD_EDIT_DIGIT")
	public Integer cdEditDigit;

	/**
	 * コード編集方法
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDIT_METHOD")
	public int cdEdittingMethod;

	/**
	 * スペース編集
	 */
	@Basic(optional = false)
	@Column(name = "SPACE_EDIT")
	public int spaceEditting;

	/**
	 * コード変換コード
	 */
	@Basic(optional = true)
	@Column(name = "CONVERT_CD")
	public String cdConvertCd;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_REPLACE_VAL_ATR")
	public int nullValueReplace;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "NULL_REPLACE_VAL")
	public String valueOfNullValueReplace;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VAL_ATR")
	public int fixedValue;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_VAL")
	public String valueOfFixedValue;

	@Override
	protected Object getKey() {
		return characterDfsPk;
	}

	public CharacterDataFmSetting toDomain() {
		return new CharacterDataFmSetting(this.characterDfsPk.cid, this.nullValueReplace, this.valueOfNullValueReplace,
				this.cdEditting, this.fixedValue, this.cdEdittingMethod, this.cdEditDigit, this.cdConvertCd,
				this.spaceEditting, this.effectDigitLength, this.startDigit, this.endDigit, this.valueOfFixedValue,
				this.characterDfsPk.condSetCd, this.characterDfsPk.outItemCd);
	}

	public static OiomtCharacterDfs toEntity(CharacterDataFmSetting domain) {
		return new OiomtCharacterDfs(
				new OiomtCharacterDfsPk(
						domain.getCid(), 
						domain.getConditionSettingCode().v(),
						domain.getOutputItemCode().v()),
				domain.getEffectDigitLength().value,
				domain.getStartDigit().isPresent() ? domain.getStartDigit().get().v() : null,
				domain.getEndDigit().isPresent() ? domain.getEndDigit().get().v() : null,
				domain.getCdEditting().value, 
				domain.getCdEditDigit().isPresent() ? domain.getCdEditDigit().get().v() : null,
				domain.getCdEdittingMethod().value,
				domain.getSpaceEditting().value, 
				domain.getConvertCode().isPresent() ? domain.getConvertCode().get().v() : null,
				domain.getNullValueReplace().value, 
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
				domain.getFixedValue().value, 
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null);
	}
}
