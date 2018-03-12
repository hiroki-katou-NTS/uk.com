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
			this.userId.equals(castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		
		return hash;
	}


	public SgwmtOtherSysAccPK(String userId) {
		super();
		this.userId = userId;
	}
	
	
}