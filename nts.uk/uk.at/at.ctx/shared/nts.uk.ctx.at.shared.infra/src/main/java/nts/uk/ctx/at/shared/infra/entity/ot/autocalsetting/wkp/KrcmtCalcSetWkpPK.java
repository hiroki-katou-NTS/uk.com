/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtCalcSetWkpPK.
 */
@Getter
@Setter
@Embeddable
public class KrcmtCalcSetWkpPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;

    /** The wkpid. */
    @Column(name = "WKPID")
    private String wkpid;

    /**
     * Instantiates a new kshmt auto wkp cal set PK.
     */
    public KrcmtCalcSetWkpPK() {
    	super();
    }

    /**
     * Instantiates a new kshmt auto wkp cal set PK.
     *
     * @param cid the cid
     * @param wkpid the wkpid
     */
    public KrcmtCalcSetWkpPK(String cid, String wkpid) {
        this.cid = cid;
        this.wkpid = wkpid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcmtCalcSetWkpPK)) {
            return false;
        }
        KrcmtCalcSetWkpPK other = (KrcmtCalcSetWkpPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.wkpid != other.wkpid) {
            return false;
        }
        return true;
    }

}
