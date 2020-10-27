/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outside.overtime.language;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtOutsideLangNamePK.
 */
@Getter
@Setter
@Embeddable
public class KshmtOutsideLangPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The over time no. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "OVER_TIME_NO")
    private int overTimeNo;
    
    /** The language id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "LANGUAGE_ID")
    private String languageId;

    /**
     * Instantiates a new kshst over time lang name PK.
     */
    public KshmtOutsideLangPK() {
    }

    /**
     * Instantiates a new kshst over time lang name PK.
     *
     * @param cid the cid
     * @param overTimeNo the over time no
     * @param languageId the language id
     */
    public KshmtOutsideLangPK(String cid, int overTimeNo, String languageId) {
        this.cid = cid;
        this.overTimeNo = overTimeNo;
        this.languageId = languageId;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) overTimeNo;
        hash += (languageId != null ? languageId.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtOutsideLangPK)) {
            return false;
        }
        KshmtOutsideLangPK other = (KshmtOutsideLangPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.overTimeNo != other.overTimeNo) {
            return false;
        }
        if ((this.languageId == null && other.languageId != null) || (this.languageId != null && !this.languageId.equals(other.languageId))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtOutsideLangNamePK[ cid=" + cid + ", overTimeNo=" + overTimeNo + ", languageId=" + languageId + " ]";
    }
    
}
