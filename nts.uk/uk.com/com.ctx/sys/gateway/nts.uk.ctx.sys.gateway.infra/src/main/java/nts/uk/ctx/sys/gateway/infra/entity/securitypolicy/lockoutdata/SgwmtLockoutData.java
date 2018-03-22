package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.lockoutdata;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class SgwmtLockoutData.
 */
@Entity
@Table(name="SGWMT_LOCKOUT_DATA")
@Getter
@Setter
public class SgwmtLockoutData extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private SgwmtLockoutDataPK id;

	/** The exclus ver. */
	@Column(name="EXCLUS_VER")
	private BigDecimal exclusVer;

	/** The lock type. */
	@Column(name="LOCK_TYPE")
	private Integer lockType;

	/** The lockout date time. */
	@Column(name="LOCKOUT_DATE_TIME")
	@Convert(converter = GeneralDateTimeToDBConverter.class)
	private GeneralDateTime lockoutDateTime;

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
		return this.id;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}