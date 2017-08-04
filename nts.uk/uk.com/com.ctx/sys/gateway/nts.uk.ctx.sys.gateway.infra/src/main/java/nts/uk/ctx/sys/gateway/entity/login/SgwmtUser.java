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
@NoArgsConstructor
public class SgwmtUser extends UkJpaEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "USER_ID")
    private String userId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 44)
    @Column(name = "PASSWORD")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "LOGIN_ID")
    private String loginId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONTRACT_CD")
    private long contractCd;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPIRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SPECIAL_USER")
    private short specialUser;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "MULTI_COM")
    private short multiCom;
    
    @Size(max = 80)
    @Column(name = "MAIL_ADD")
    private String mailAdd;
    
    @Size(max = 30)
    @Column(name = "USER_NAME")
    private String userName;
    
    @Size(max = 36)
    @Column(name = "ASSO_SID")
    private String assoSid;

    public SgwmtUser(String userId, int exclusVer, String password, String loginId, long contractCd, Date expirationDate, short specialUser, short multiCom) {
        this.userId = userId;
        this.password = password;
        this.loginId = loginId;
        this.contractCd = contractCd;
        this.expirationDate = expirationDate;
        this.specialUser = specialUser;
        this.multiCom = multiCom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgwmtUser)) {
            return false;
        }
        SgwmtUser other = (SgwmtUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

	@Override
	protected Object getKey() {
		return this.userId;
	}
}
