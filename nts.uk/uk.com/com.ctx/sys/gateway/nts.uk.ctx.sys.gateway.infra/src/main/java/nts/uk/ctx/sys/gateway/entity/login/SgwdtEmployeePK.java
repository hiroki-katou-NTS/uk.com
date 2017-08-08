/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.entity.login;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class SgwdtEmployeePK.
 */
@Getter
@Setter
@Embeddable
public class SgwdtEmployeePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "SID")
    private String sid;

    public SgwdtEmployeePK() {
    }

    public SgwdtEmployeePK(String cid, String sid) {
        this.cid = cid;
        this.sid = sid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SgwdtEmployeePK)) {
            return false;
        }
        SgwdtEmployeePK other = (SgwdtEmployeePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }
}
