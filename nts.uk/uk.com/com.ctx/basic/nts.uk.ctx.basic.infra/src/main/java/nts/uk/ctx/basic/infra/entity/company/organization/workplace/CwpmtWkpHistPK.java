/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CwpmtWkpHistPK.
 */
@Getter
@Setter
@Embeddable
public class CwpmtWkpHistPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Basic(optional = false)
    @Column(name = "CID")
    private String cid;
	
	@NotNull
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    private String histId;

    public CwpmtWkpHistPK() {
    }

    public CwpmtWkpHistPK(String cid, String histId) {
        this.cid = cid;
        this.histId = histId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (histId != null ? histId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CwpmtWkpHistPK)) {
            return false;
        }
        CwpmtWkpHistPK other = (CwpmtWkpHistPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.histId == null && other.histId != null) || (this.histId != null && !this.histId.equals(other.histId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CwpmtWkpHistPK[ cid=" + cid + ", histId=" + histId + " ]";
    }
    
}
