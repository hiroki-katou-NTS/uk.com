/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class KwpmtWplHistPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "CID")
    private String cid;
	
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    private String histId;

    public KwpmtWplHistPK() {
    }

    public KwpmtWplHistPK(String cid, String histId) {
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
        if (!(object instanceof KwpmtWplHistPK)) {
            return false;
        }
        KwpmtWplHistPK other = (KwpmtWplHistPK) object;
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
        return "entity.KwpmtWplHistPK[ cid=" + cid + ", histId=" + histId + " ]";
    }
    
}
