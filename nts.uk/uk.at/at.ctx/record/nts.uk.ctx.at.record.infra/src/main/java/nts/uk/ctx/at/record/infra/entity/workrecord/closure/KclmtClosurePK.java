/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.closure;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KclmtClosurePK.
 */
@Getter
@Setter
@Embeddable
public class KclmtClosurePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The closure id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLOSURE_ID")
    private Integer closureId;

    /**
     * Instantiates a new kclmt closure PK.
     */
    public KclmtClosurePK() {
    }

    /**
     * Instantiates a new kclmt closure PK.
     *
     * @param ccid the ccid
     * @param closureId the closure id
     */
    public KclmtClosurePK(String cid, int closureId) {
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
		if (!(object instanceof KclmtClosurePK)) {
			return false;
		}
		KclmtClosurePK other = (KclmtClosurePK) object;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KclmtClosurePK[ cid=" + cid + ", closureId=" + closureId + " ]";
    }
    
}
