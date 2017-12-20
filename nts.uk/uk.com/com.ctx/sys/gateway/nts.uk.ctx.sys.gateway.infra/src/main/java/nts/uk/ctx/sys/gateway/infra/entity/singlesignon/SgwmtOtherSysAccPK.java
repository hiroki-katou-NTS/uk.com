package nts.uk.ctx.sys.gateway.infra.entity.singlesignon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The primary key class for the SGWMT_OTHER_SYS_ACC database table.
 * 
 */
@Setter
@Getter
@Embeddable
public class SgwmtOtherSysAccPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID")
	private String userId;

	// company code
	@Column(name="CCD")
	private String ccd;

	@Column(name="USER_NAME")
	private String userName;

	public SgwmtOtherSysAccPK() {
		super();
	}
	

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SgwmtOtherSysAccPK)) {
			return false;
		}
		SgwmtOtherSysAccPK castOther = (SgwmtOtherSysAccPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.ccd.equals(castOther.ccd)
			&& this.userName.equals(castOther.userName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.ccd.hashCode();
		hash = hash * prime + this.userName.hashCode();
		
		return hash;
	}


	public SgwmtOtherSysAccPK(String userId, String ccd, String userName) {
		super();
		this.userId = userId;
		this.ccd = ccd;
		this.userName = userName;
	}
	
	
}