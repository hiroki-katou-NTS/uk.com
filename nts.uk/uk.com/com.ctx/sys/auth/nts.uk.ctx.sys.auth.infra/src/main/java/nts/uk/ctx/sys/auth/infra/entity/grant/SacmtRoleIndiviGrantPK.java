/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.entity.grant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class SacmtRoleIndiviGrantPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
    private String cid;

	@Column(name = "USER_ID")
    private String userId;

	@Column(name = "ROLE_TYPE")
    private Integer roleType;

    public SacmtRoleIndiviGrantPK() {
    }

    public SacmtRoleIndiviGrantPK(String cid, String userId, Integer roleType) {
        this.cid = cid;
        this.userId = userId;
        this.roleType = roleType;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (userId != null ? userId.hashCode() : 0);
        hash += (int) roleType;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SacmtRoleIndiviGrantPK)) {
            return false;
        }
        SacmtRoleIndiviGrantPK other = (SacmtRoleIndiviGrantPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        if (this.roleType != other.roleType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SacmtRoleIndiviGrantPK[ cid=" + cid + ", userId=" + userId + ", roleType=" + roleType + " ]";
    }
    
}
