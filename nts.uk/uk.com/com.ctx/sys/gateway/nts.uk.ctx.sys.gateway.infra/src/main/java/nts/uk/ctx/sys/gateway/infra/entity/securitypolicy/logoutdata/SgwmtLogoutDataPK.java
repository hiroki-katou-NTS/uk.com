/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.entity.securitypolicy.logoutdata;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SgwmtLogoutDataPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user id. */
	@Column(name = "USER_ID")
    private String userId;
    
    /** The contract cd. */
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    
    /** The login method. */
    @Column(name = "LOGIN_METHOD")
    private int loginMethod;
    
    /** The logout date time. */
    @Column(name = "LOGOUT_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutDateTime;

    /**
     * Instantiates a new sgwmt logout data PK.
     */
    public SgwmtLogoutDataPK() {
    }

    /**
     * Instantiates a new sgwmt logout data PK.
     *
     * @param userId the user id
     * @param contractCd the contract cd
     * @param loginMethod the login method
     * @param logoutDateTime the logout date time
     */
    public SgwmtLogoutDataPK(String userId, String contractCd, int loginMethod, Date logoutDateTime) {
        this.userId = userId;
        this.contractCd = contractCd;
        this.loginMethod = loginMethod;
        this.logoutDateTime = logoutDateTime;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contractCd == null) ? 0 : contractCd.hashCode());
		result = prime * result + loginMethod;
		result = prime * result + ((logoutDateTime == null) ? 0 : logoutDateTime.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SgwmtLogoutDataPK other = (SgwmtLogoutDataPK) obj;
		if (contractCd == null) {
			if (other.contractCd != null)
				return false;
		} else if (!contractCd.equals(other.contractCd))
			return false;
		if (loginMethod != other.loginMethod)
			return false;
		if (logoutDateTime == null) {
			if (other.logoutDateTime != null)
				return false;
		} else if (!logoutDateTime.equals(other.logoutDateTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

    
}
