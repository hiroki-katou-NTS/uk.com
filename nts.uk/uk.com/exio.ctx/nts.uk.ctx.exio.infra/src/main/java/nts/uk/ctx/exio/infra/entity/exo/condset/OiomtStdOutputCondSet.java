package nts.uk.ctx.exio.infra.entity.exo.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部出力出力条件設定（定型）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_CND")
public class OiomtStdOutputCondSet extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtStdOutputCondSetPk stdOutputCondSetPk;

	/**
	 * 外部出力条件名称
	 */
	@Basic(optional = false)
	@Column(name = "CND_SET_NAME")
	public String conditionSetName;
	
	/**
	 * カテゴリID
	 */
	@Basic(optional = false)
	@Column(name = "CTG_ID")
	public int categoryId;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "CND_OUT_NAME")
	public int conditionOutputName;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_OUTPUT_NAME")
	public int itemOutputName;

	/**
	 * 区切り文字
	 */
	@Basic(optional = false)
	@Column(name = "DELIMITER")
	public int delimiter;

	/**
	 * 文字列形式
	 */
	@Basic(optional = false)
	@Column(name = "STRING_FORMAT")
	public int stringFormat;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "AUTO_EXECUTION")
	public int autoExecution;

	@Override
	protected Object getKey() {
		return stdOutputCondSetPk;
	}
}
