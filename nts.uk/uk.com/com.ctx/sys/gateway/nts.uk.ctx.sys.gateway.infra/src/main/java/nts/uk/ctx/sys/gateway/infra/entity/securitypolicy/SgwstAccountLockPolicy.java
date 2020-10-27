package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SGWMT_ACC_LOCK_POLICY")
public class SgwstAccountLockPolicy extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "CONTRACT_CODE")
	public String contractCode;
	@Column(name = "ERROR_COUNT")
	public BigDecimal errorCount;
	@Column(name = "LOCK_INTERVAL")
	public BigDecimal lockInterval;
	@Column(name = "LOCK_OUT_MESSAGE")
	public String lockOutMessage;
	@Column(name = "IS_USE")
	public BigDecimal isUse;
	@Override
	protected Object getKey() {
		return this.contractCode;
	}

}
