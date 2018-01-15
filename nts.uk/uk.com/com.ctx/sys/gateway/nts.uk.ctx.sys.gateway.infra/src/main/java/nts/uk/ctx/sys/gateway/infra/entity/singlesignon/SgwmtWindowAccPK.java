package nts.uk.ctx.sys.gateway.infra.entity.singlesignon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The primary key class for the SGWMT_WINDOW_ACC database table.
 * 
 */
@Setter
@Getter
@Embeddable
public class SgwmtWindowAccPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="HOST_NAME")
	private String hostName;

	@Column(name="USER_NAME")
	private String userName;

	public SgwmtWindowAccPK() {
		super();
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SgwmtWindowAccPK)) {
			return false;
		}
		SgwmtWindowAccPK castOther = (SgwmtWindowAccPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.hostName.equals(castOther.hostName)
			&& this.userName.equals(castOther.userName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.hostName.hashCode();
		hash = hash * prime + this.userName.hashCode();
		
		return hash;
	}

	public SgwmtWindowAccPK(String userId, String userName, String hostName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.hostName = hostName;
	}
	
	
}