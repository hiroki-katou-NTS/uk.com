package nts.uk.ctx.exio.infra.entity.exo.condset;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 外部出力出力条件設定（定型）
 */
@Data
@Entity
@Table(name = "OIOMT_EX_OUT_CND")
@EqualsAndHashCode(callSuper = true)
public class OiomtStdOutputCondSet extends UkJpaEntity
		implements StdOutputCondSet.MementoGetter, StdOutputCondSet.MementoSetter, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	private OiomtStdOutputCondSetPk stdOutputCondSetPk;

	/**
	 * 外部出力条件名称
	 */
	@Basic(optional = false)
	@Column(name = "CND_SET_NAME")
	private String conditionSetName;

	/**
	 * カテゴリID
	 */
	@Basic(optional = false)
	@Column(name = "CTG_ID")
	private int categoryId;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "CND_OUT_NAME")
	private int conditionOutputName;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_OUTPUT_NAME")
	private int itemOutputName;

	/**
	 * 区切り文字
	 */
	@Basic(optional = false)
	@Column(name = "DELIMITER")
	private int delimiter;

	/**
	 * 文字列形式
	 */
	@Basic(optional = false)
	@Column(name = "STRING_FORMAT")
	private int stringFormat;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "AUTO_EXECUTION")
	private int autoExecution;

	/**
	 * Gets primary key of entity.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.stdOutputCondSetPk;
	}

	/**
	 * No args constructor.
	 */
	protected OiomtStdOutputCondSet() {
	}

	/**
	 * Creates new entity from domain and memento.
	 *
	 * @param domain the domain require <code>not null</code>
	 */
	public OiomtStdOutputCondSet(@NonNull StdOutputCondSet domain) {
		domain.setMemento(this);
	}

	/**
	 * Sets company id.
	 *
	 * @param companyId the company id
	 */
	@Override
	public void setCompanyId(String companyId) {
		if (this.stdOutputCondSetPk == null) {
			this.stdOutputCondSetPk = new OiomtStdOutputCondSetPk();
		}
		this.stdOutputCondSetPk.cid = companyId;
	}

	/**
	 * Sets condition set code.
	 *
	 * @param conditionSetCode the condition set code
	 */
	@Override
	public void setConditionSetCode(String conditionSetCode) {
		if (this.stdOutputCondSetPk == null) {
			this.stdOutputCondSetPk = new OiomtStdOutputCondSetPk();
		}
		this.stdOutputCondSetPk.conditionSetCd = conditionSetCode;
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return this.stdOutputCondSetPk.cid;
	}

	/**
	 * Gets condition set code.
	 *
	 * @return the condition set code
	 */
	@Override
	public String getConditionSetCode() {
		return this.stdOutputCondSetPk.conditionSetCd;
	}

}
