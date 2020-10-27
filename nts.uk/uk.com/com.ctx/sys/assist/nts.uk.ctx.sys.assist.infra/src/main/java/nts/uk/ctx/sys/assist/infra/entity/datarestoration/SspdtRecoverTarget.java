package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 対象者
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPDT_RECOVER_TARGET")
public class SspdtRecoverTarget extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public SspdtRecoverTargetPk targetPk;

	/**
	 * 社員コード
	 */
	@Basic(optional = true)
	@Column(name = "SCD")
	public String scd;

	/**
	 * ビジネスネーム
	 */
	@Basic(optional = true)
	@Column(name = "BUSSINESS_NAME")
	public String bussinessName;

	@Override
	protected Object getKey() {
		return targetPk;
	}

	public Target toDomain() {
		return new Target(this.targetPk.dataRecoveryProcessId, this.targetPk.sid, this.scd, this.bussinessName);
	}

	public static SspdtRecoverTarget toEntity(Target domain) {
		return new SspdtRecoverTarget(new SspdtRecoverTargetPk(domain.getDataRecoveryProcessId(), domain.getSid()),
				domain.getScd().orElse(null), domain.getBussinessName().orElse(null));
	}
}
