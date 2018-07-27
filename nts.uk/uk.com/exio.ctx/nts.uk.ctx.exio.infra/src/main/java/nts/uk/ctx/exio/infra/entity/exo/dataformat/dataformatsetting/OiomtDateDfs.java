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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 日付型データ形式設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_DATE_DFS")
public class OiomtDateDfs extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtDateDfsPk dateDfsPk;

	/**
	 * NULL値置換
	 */
	@Basic(optional = false)
	@Column(name = "NULL_VALUE_SUBSTITUTION")
	public int nullValueSubstitution;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE")
	public int fixedValue;

	/**
	 * 形式選択
	 */
	@Basic(optional = false)
	@Column(name = "FORMAT_SELECTION")
	public int formatSelection;

	/**
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_FIXED_VALUE")
	public String valueOfFixedValue;

	/**
	 * NULL値置換の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_NULL_VALUE_SUBS")
	public String valueOfNullValueSubs;

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
				domain.getNullValueReplace().value, domain.getFixedValue().value, domain.getFormatSelection().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null,
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null);
	}
}
