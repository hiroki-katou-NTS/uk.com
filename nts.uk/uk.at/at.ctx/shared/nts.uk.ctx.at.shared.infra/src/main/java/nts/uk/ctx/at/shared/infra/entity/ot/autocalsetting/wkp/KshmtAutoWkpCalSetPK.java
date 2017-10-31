/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Getter
@Setter
@Embeddable
public class KshmtAutoWkpCalSetPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Column(name = "CID")
    private String cid;

    @Column(name = "WKPID")
    private String wkpid;

    public KshmtAutoWkpCalSetPK() {
    	super();
    }

    public KshmtAutoWkpCalSetPK(String cid, String wkpid) {
        this.cid = cid;
        this.wkpid = wkpid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtAutoWkpCalSetPK)) {
            return false;
        }
        KshmtAutoWkpCalSetPK other = (KshmtAutoWkpCalSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.wkpid != other.wkpid) {
            return false;
        }
        return true;
    }

}
