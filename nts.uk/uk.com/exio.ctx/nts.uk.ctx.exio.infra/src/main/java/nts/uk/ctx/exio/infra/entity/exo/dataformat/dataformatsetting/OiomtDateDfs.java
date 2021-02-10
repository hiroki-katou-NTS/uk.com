package nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部出力日付型データ形式設定（項目単位）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_FM_DATE")
public class OiomtDateDfs extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtDateDfsPk dateDfsPk;

	/**
	 * 形式選択
	 */
	@Basic(optional = false)
	@Column(name = "FORMAT_SELECTION")
	public int formatSelection;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_REPLACE_VAL_ATR")
	public int nullValueSubstitution;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "NULL_REPLACE_VAL")
	public String valueOfNullValueSubs;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VAL_ATR")
	public int fixedValue;

	/**
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "FIXED_VAL")
	public String valueOfFixedValue;

	@Override
	protected Object getKey() {
		return dateDfsPk;
	}

	public DateFormatSetting toDomain() {
		return new DateFormatSetting(this.dateDfsPk.cid, this.nullValueSubstitution, this.fixedValue,
				this.valueOfFixedValue, this.valueOfNullValueSubs, this.formatSelection, this.dateDfsPk.condSetCd,
				this.dateDfsPk.outItemCd);
	}

	public static OiomtDateDfs toEntity(DateFormatSetting domain) {
		return new OiomtDateDfs(
				new OiomtDateDfsPk(domain.getCid(), domain.getConditionSettingCode().v(),
						domain.getOutputItemCode().v()),
				domain.getFormatSelection().value,
				domain.getNullValueReplace().value, 
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
				domain.getFixedValue().value, 
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null);
	}
}
