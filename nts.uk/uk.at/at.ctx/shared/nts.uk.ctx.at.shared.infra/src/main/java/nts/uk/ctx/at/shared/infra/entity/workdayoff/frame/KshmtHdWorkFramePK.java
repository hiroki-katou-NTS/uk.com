/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workdayoff.frame;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class KshmtHdWorkFramePK implements Serializable {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 663719554552931046L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
   
    /** The wdo fr no. */
    @Column(name = "WDO_FR_NO")
    private short wdoFrNo;

    /**
     * Instantiates a new kshst workdayoff frame PK.
     */
    public KshmtHdWorkFramePK() {
    	super();
    }

    /**
     * Instantiates a new kshst workdayoff frame PK.
     *
     * @param cid the cid
     * @param wdoFrNo the wdo fr no
     */
    public KshmtHdWorkFramePK(String cid, short wdoFrNo) {
        this.cid = cid;
        this.wdoFrNo = wdoFrNo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) wdoFrNo;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdWorkFramePK)) {
            return false;
        }
        KshmtHdWorkFramePK other = (KshmtHdWorkFramePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.wdoFrNo != other.wdoFrNo) {
            return false;
        }
        return true;
    }
}
