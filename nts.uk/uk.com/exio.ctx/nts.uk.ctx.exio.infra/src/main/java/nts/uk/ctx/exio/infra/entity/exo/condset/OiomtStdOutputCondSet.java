package nts.uk.ctx.exio.infra.entity.exo.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 出力条件設定（定型）
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_STD_OUTPUT_COND_SET")
public class OiomtStdOutputCondSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtStdOutputCondSetPk stdOutputCondSetPk;

	/**
	 * カテゴリID
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_ID")
	public int categoryId;

	/**
	 * 区切り文字
	 */
	@Basic(optional = false)
	@Column(name = "DELIMITER")
	public int delimiter;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "ITEM_OUTPUT_NAME")
	public int itemOutputName;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "AUTO_EXECUTION")
	public int autoExecution;

	/**
	 * 外部出力条件名称
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SET_NAME")
	public String conditionSetName;

	/**
	 * するしない区分
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_OUTPUT_NAME")
	public int conditionOutputName;

	/**
	 * 文字列形式
	 */
	@Basic(optional = false)
	@Column(name = "STRING_FORMAT")
	public int stringFormat;

	@Override
	protected Object getKey() {
		return stdOutputCondSetPk;
	}
}
