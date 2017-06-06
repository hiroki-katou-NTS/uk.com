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
    @Column(name = "CCID")
    private String ccid;
    
    /** The closure id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLOSURE_ID")
    private String closureId;

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
    public KclmtClosurePK(String ccid, String closureId) {
        this.ccid = ccid;
        this.closureId = closureId;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccid != null ? ccid.hashCode() : 0);
        hash += (closureId != null ? closureId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KclmtClosurePK)) {
            return false;
        }
        KclmtClosurePK other = (KclmtClosurePK) object;
        if ((this.ccid == null && other.ccid != null) || (this.ccid != null && !this.ccid.equals(other.ccid))) {
            return false;
        }
        if ((this.closureId == null && other.closureId != null) || (this.closureId != null && !this.closureId.equals(other.closureId))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KclmtClosurePK[ ccid=" + ccid + ", closureId=" + closureId + " ]";
    }
    
}
