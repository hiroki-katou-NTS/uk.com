package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.loginlog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the SGWMT_LOGIN_LOG database table.
 * 
 */
@Entity
@Table(name="SGWMT_LOGIN_LOG")
@Getter
@Setter
public class SgwmtLoginLog extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private SgwmtLoginLogPK sgwmtLoginLogPK;

	/** The operation section. */
	@Column(name="OPERATION_SECTION")
	private Integer operationSection;

	/** The success or failure. */
	@Column(name="SUCCESS_OR_FAILURE")
	private Integer successOrFailure;

	/**
	 * Instantiates a new sgwmt login log.
	 */
	public SgwmtLoginLog() {
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.sgwmtLoginLogPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sgwmtLoginLogPK == null) ? 0 : sgwmtLoginLogPK.hashCode());
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
		SgwmtLoginLog other = (SgwmtLoginLog) obj;
		if (sgwmtLoginLogPK == null) {
			if (other.sgwmtLoginLogPK != null)
				return false;
		} else if (!sgwmtLoginLogPK.equals(other.sgwmtLoginLogPK))
			return false;
		return true;
	}
}