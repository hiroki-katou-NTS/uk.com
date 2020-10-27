package nts.uk.ctx.exio.infra.entity.exi.opmanage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部受入動作管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOTT_EX_AC_OP_MNG")
public class OiottExAcOpMng extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiottExAcOpMngPk exAcOpManagePk;

	/**
	 * エラー件数
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_COUNT")
	public int errorCount;

	/**
	 * 中断するしない
	 */
	@Basic(optional = false)
	@Column(name = "INTERRUPTION")
	public int interruption;

	/**
	 * 処理カウント
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_COUNT")
	public int processCount;

	/**
	 * 処理トータルカウント
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_TOTAL_COUNT")
	public int processTotalCount;

	/**
	 * 動作状態
	 */
	@Basic(optional = false)
	@Column(name = "STATE_BEHAVIOR")
	public int stateBehavior;

	@Override
	protected Object getKey() {
		return exAcOpManagePk;
	}
}
