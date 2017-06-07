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
 * The Class KwpmtWorkHierarchyPK.
 */
@Getter
@Setter
@Embeddable
public class KwpmtWorkHierarchyPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CCID")
    private String ccid;
    
    /** The wkpid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKPID")
    private String wkpid;
    
    /** The wkpcd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WKPCD")
    private String wkpcd;
    
    /** The his id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIS_ID")
    private String hisId;

    /**
     * Instantiates a new kwpmt work hierarchy PK.
     */
    public KwpmtWorkHierarchyPK() {
    }

    /**
     * Instantiates a new kwpmt work hierarchy PK.
     *
     * @param ccid the ccid
     * @param wkpid the wkpid
     * @param wkpcd the wkpcd
     * @param hisId the his id
     */
    public KwpmtWorkHierarchyPK(String ccid, String wkpid, String wkpcd, String hisId) {
        this.ccid = ccid;
        this.wkpid = wkpid;
        this.wkpcd = wkpcd;
        this.hisId = hisId;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccid != null ? ccid.hashCode() : 0);
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        hash += (wkpcd != null ? wkpcd.hashCode() : 0);
        hash += (hisId != null ? hisId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KwpmtWorkHierarchyPK)) {
            return false;
        }
        KwpmtWorkHierarchyPK other = (KwpmtWorkHierarchyPK) object;
        if ((this.ccid == null && other.ccid != null) || (this.ccid != null && !this.ccid.equals(other.ccid))) {
            return false;
        }
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        if ((this.wkpcd == null && other.wkpcd != null) || (this.wkpcd != null && !this.wkpcd.equals(other.wkpcd))) {
            return false;
        }
        if ((this.hisId == null && other.hisId != null) || (this.hisId != null && !this.hisId.equals(other.hisId))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KwpmtWorkHierarchyPK[ ccid=" + ccid + ", wkpid=" + wkpid + ", wkpcd=" + wkpcd + ", hisId=" + hisId + " ]";
    }
    
}
