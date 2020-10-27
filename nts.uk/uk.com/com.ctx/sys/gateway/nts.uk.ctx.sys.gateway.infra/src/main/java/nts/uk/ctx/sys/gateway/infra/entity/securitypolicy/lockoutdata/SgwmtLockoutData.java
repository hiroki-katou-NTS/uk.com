package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class SgwmtLockoutData.
 */
@Entity
@Table(name="SGWMT_LOCKOUT_DATA")
@Getter
@Setter
public class SgwmtLockoutData extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private SgwmtLockoutDataPK sgwmtLockoutDataPK;

	/** The lock type. */
	@Column(name="LOCK_TYPE")
	private Integer lockType;

	/** The login method. */
	@Column(name="LOGIN_METHOD")
	private Integer loginMethod;
	
	/**
	 * Instantiates a new sgwmt lockout data.
	 */
	public SgwmtLockoutData() {
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.sgwmtLockoutDataPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sgwmtLockoutDataPK == null) ? 0 : sgwmtLockoutDataPK.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SgwmtLockoutData other = (SgwmtLockoutData) obj;
		if (sgwmtLockoutDataPK == null) {
			if (other.sgwmtLockoutDataPK != null)
				return false;
		} else if (!sgwmtLockoutDataPK.equals(other.sgwmtLockoutDataPK))
			return false;
		return true;
	}
	
}