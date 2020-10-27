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
 * The Class SgwdtLockout.
 */
@Entity
@Table(name="SGWDT_LOCKOUT")
@Getter
@Setter
public class SgwdtLockout extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private SgwdtLockoutPK sgwdtLockoutPK;

	/** The lock type. */
	@Column(name="LOCK_TYPE")
	private Integer lockType;

	/** The login method. */
	@Column(name="LOGIN_METHOD")
	private Integer loginMethod;
	
	/**
	 * Instantiates a new sgwmt lockout data.
	 */
	public SgwdtLockout() {
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.sgwdtLockoutPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sgwdtLockoutPK == null) ? 0 : sgwdtLockoutPK.hashCode());
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
		SgwdtLockout other = (SgwdtLockout) obj;
		if (sgwdtLockoutPK == null) {
			if (other.sgwdtLockoutPK != null)
				return false;
		} else if (!sgwdtLockoutPK.equals(other.sgwdtLockoutPK))
			return false;
		return true;
	}
	
}