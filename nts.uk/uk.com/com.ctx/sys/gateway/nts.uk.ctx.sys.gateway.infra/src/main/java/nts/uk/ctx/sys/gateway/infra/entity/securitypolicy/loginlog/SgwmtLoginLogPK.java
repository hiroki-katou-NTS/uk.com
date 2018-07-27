package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.loginlog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.time.GeneralDateTime;

/**
 * The primary key class for the SGWMT_LOGIN_LOG database table.
 * 
 */
@Embeddable
@Getter
@Setter
public class SgwmtLoginLogPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The user id. */
	@Column(name="USER_ID")
	private String userId;

	/** The contract cd. */
	@Column(name="CONTRACT_CD")
	private String contractCd;

	/** The program id. */
	@Column(name="PROGRAM_ID")
	private String programId;
	
	/** The process date time. */
	@Column(name="PROCESS_DATE_TIME")
	@Convert(converter = GeneralDateTimeToDBConverter.class)
	private GeneralDateTime processDateTime;

	/**
	 * Instantiates a new sgwmt login log PK.
	 */
	public SgwmtLoginLogPK() {
		super();
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SgwmtLoginLogPK)) {
			return false;
		}
		SgwmtLoginLogPK castOther = (SgwmtLoginLogPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.contractCd.equals(castOther.contractCd)
			&& this.programId.equals(castOther.programId)
			&& this.processDateTime.equals(castOther.processDateTime);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.contractCd.hashCode();
		hash = hash * prime + this.programId.hashCode();
		hash = hash * prime + this.processDateTime.hashCode();
		
		return hash;
	}
}