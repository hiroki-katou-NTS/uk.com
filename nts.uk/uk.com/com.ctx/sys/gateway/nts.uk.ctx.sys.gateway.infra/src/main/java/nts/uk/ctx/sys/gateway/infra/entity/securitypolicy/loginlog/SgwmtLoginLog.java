package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.loginlog;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import java.math.BigDecimal;


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
	private SgwmtLoginLogPK id;

	/** The exclus ver. */
	@Column(name="EXCLUS_VER")
	private BigDecimal exclusVer;

	/** The operation section. */
	@Column(name="OPERATION_SECTION")
	private Integer operationSection;

	/** The process date time. */
	@Column(name="PROCESS_DATE_TIME")
	@Convert(converter = GeneralDateTimeToDBConverter.class)
	private GeneralDateTime processDateTime;

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
		SgwmtLoginLog other = (SgwmtLoginLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}