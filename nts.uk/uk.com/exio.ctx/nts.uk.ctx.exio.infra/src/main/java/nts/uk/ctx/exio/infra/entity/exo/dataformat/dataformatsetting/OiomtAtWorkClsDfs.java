package nts.uk.ctx.exio.infra.entity.exo.dataformat.dataformatsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 在職区分型データ形式設定
 */
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_AT_WORK_CLS_DFS")
public class OiomtAtWorkClsDfs extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtAtWorkClsDfsPk atWorkClsDfsPk;

	/**
	 * 休業時出力
	 */
	@Basic(optional = true)
	@Column(name = "CLOSED_OUTPUT")
	public String closedOutput;

	/**
	 * 休職時出力
	 */
	@Basic(optional = true)
	@Column(name = "ABSENCE_OUTPUT")
	public String absenceOutput;

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
	 * 在職時出力
	 */
	@Basic(optional = true)
	@Column(name = "AT_WORK_OUTPUT")
	public String atWorkOutput;

	/**
	 * 退職時出力
	 */
	@Basic(optional = true)
	@Column(name = "RETIREMENT_OUTPUT")
	public String retirementOutput;

	@Override
	protected Object getKey() {
		return atWorkClsDfsPk;
	}
}
