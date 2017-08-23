/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "SGWMT_USER")

/**
 * Instantiates a new sgwmt user.
 */
@NoArgsConstructor
public class SgwmtUser extends UkJpaEntity implements Serializable {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
    
    /** The user id. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "USER_ID")
    private String userId;
    
    /** The password. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 66)
    @Column(name = "PASSWORD")
    private String password;
    
    /** The login id. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "LOGIN_ID")
    private String loginId;
    
    /** The contract cd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    
    /** The expiration date. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPIRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    
    /** The special user. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPECIAL_USER")
    private short specialUser;
    
    /** The multi com. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "MULTI_COM")
    private short multiCom;
    
    /** The mail add. */
    @Size(max = 80)
    @Column(name = "MAIL_ADD")
    private String mailAdd;
    
    /** The user name. */
    @Size(max = 30)
    @Column(name = "USER_NAME")
    private String userName;
    
    /** The asso sid. */
    @Size(max = 36)
    @Column(name = "ASSO_SID")
    private String assoSid;

    /**
     * Instantiates a new sgwmt user.
     *
     * @param userId the user id
     * @param exclusVer the exclus ver
     * @param password the password
     * @param loginId the login id
     * @param contractCd the contract cd
     * @param expirationDate the expiration date
     * @param specialUser the special user
     * @param multiCom the multi com
     */
    public SgwmtUser(String userId, int exclusVer, String password, String loginId, String contractCd, Date expirationDate, short specialUser, short multiCom) {
        this.userId = userId;
        this.password = password;
        this.loginId = loginId;
        this.contractCd = contractCd;
        this.expirationDate = expirationDate;
        this.specialUser = specialUser;
        this.multiCom = multiCom;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SgwmtUser)) {
            return false;
        }
        SgwmtUser other = (SgwmtUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.userId;
	}
}
