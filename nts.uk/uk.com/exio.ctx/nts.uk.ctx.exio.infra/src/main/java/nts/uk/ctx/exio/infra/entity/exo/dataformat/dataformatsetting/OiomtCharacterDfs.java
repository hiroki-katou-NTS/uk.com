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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 文字型データ形式設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CHARACTER_DFS")
public class OiomtCharacterDfs extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtCharacterDfsPk characterDfsPk;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_VALUE_REPLACE")
	public int nullValueReplace;

	/**
	 * コード編集
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDITTING")
	public int cdEditting;

	/**
	 * コード編集方法
	 */
	@Basic(optional = false)
	@Column(name = "CD_EDITTING_METHOD")
	public int cdEdittingMethod;

	/**
	 * スペース編集
	 */
	@Basic(optional = false)
	@Column(name = "SPACE_EDITTING")
	public int spaceEditting;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE")
	public int fixedValue;

	/**
	 * 有効桁数
	 */
	@Basic(optional = false)
	@Column(name = "EFFECT_DIGIT_LENGTH")
	public int effectDigitLength;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_FIXED_VALUE")
	public String valueOfFixedValue;

	/**
	 * コード変換コード
	 */
	@Basic(optional = true)
	@Column(name = "CD_CONVERT_CD")
	public String cdConvertCd;

	/**
	 * コード編集桁
	 */
	@Basic(optional = true)
	@Column(name = "CD_EDIT_DIGIT")
	public Integer cdEditDigit;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_NULL_VALUE_REPLACE")
	public String valueOfNullValueReplace;

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
				new OiomtCharacterDfsPk(domain.getCid(), domain.getConditionSettingCode().v(),
						domain.getOutputItemCode().v()),
				domain.getNullValueReplace().value, domain.getCdEditting().value, domain.getCdEdittingMethod().value,
				domain.getSpaceEditting().value, domain.getFixedValue().value, domain.getEffectDigitLength().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null,
				domain.getConvertCode().isPresent() ? domain.getConvertCode().get().v() : null,
				domain.getCdEditDigit().isPresent() ? domain.getCdEditDigit().get().v() : null,
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
				domain.getStartDigit().isPresent() ? domain.getStartDigit().get().v() : null,
				domain.getEndDigit().isPresent() ? domain.getEndDigit().get().v() : null);
	}
}
