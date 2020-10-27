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
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtExAcItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 日付型データ形式設定
 */
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_AC_FM_DATE")
public class OiomtExAcFmDate extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExAcFmDatePk dateDataFormSetPk;

	/**
	 * 固定値
	 */
	@Basic(optional = false)
	@Column(name = "FIXED_VALUE")
	public int fixedValue;

	/**
	 * 固定値の値
	 */
	@Basic(optional = true)
	@Column(name = "VALUE_OF_FIXED_VALUE")
	public String valueOfFixedValue;

	/**
	 * 形式選択
	 */
	@Basic(optional = false)
	@Column(name = "FORMAT_SELECTION")
	public int formatSelection;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "SYSTEM_TYPE", referencedColumnName = "SYSTEM_TYPE", insertable = false, updatable = false),
			@JoinColumn(name = "CONDITION_SET_CD", referencedColumnName = "CONDITION_SET_CD", insertable = false, updatable = false),
			@JoinColumn(name = "ACCEPT_ITEM_NUM", referencedColumnName = "ACCEPT_ITEM_NUMBER", insertable = false, updatable = false) })
	public OiomtExAcItem acceptItem;

	@Override
	protected Object getKey() {
		return dateDataFormSetPk;
	}

	public OiomtExAcFmDate(String cid, int sysType, String conditionCode, int acceptItemNum, int fixedValue,
			int formatSelection, String valueOfFixedValue) {
		super();
		this.dateDataFormSetPk = new OiomtExAcFmDatePk(cid, sysType, conditionCode, acceptItemNum);
		this.fixedValue = fixedValue;
		this.valueOfFixedValue = valueOfFixedValue;
		this.formatSelection = formatSelection;
	}

	public static OiomtExAcFmDate fromDomain(StdAcceptItem item, DateDataFormSet domain) {
		return new OiomtExAcFmDate(item.getCid(), item.getSystemType().value, item.getConditionSetCd().v(),
				item.getAcceptItemNumber(), domain.getFixedValue().value, domain.getFormatSelection().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null);
	}

	public DateDataFormSet toDomain() {
		return new DateDataFormSet(ItemType.DATE.value, this.fixedValue, this.formatSelection, this.valueOfFixedValue);
	}

}
