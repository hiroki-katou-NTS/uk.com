/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrule.closure;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtClosurePK.
 */
@Getter
@Setter
@Embeddable
public class KshmtClosurePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The closure id. */
    @Column(name = "CLOSURE_ID")
    private Integer closureId;

    /**
     * Instantiates a new kclmt closure PK.
     */
    public KshmtClosurePK() {
    	super();
    }

    /**
     * Instantiates a new kclmt closure PK.
     *
     * @param ccid the ccid
     * @param closureId the closure id
     */
    public KshmtClosurePK(String cid, int closureId) {
        this.cid = cid;
        this.closureId = closureId;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (closureId != null ? closureId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtClosurePK)) {
			return false;
		}
		KshmtClosurePK other = (KshmtClosurePK) object;
		if ((this.cid == null && other.cid != null)
			|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.closureId == null && other.closureId != null)
			|| (this.closureId != null && !this.closureId.equals(other.closureId))) {
			return false;
		}
		return true;
	}
}
