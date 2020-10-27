/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmfmtWorkTypePK.
 */
@Setter
@Getter
@Embeddable
public class KshmtHdnursingLeavePK implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;
    
    /** The nursing ctr. */
    @Basic(optional = false)
    @Column(name = "NURSING_TYPE")
    private Integer nursingCtr;
    
    /**
     * Instantiates a new kmfmt work type PK.
     */
    public KshmtHdnursingLeavePK() {
    }
    
    /**
     * Instantiates a new kmfmt work type PK.
     *
     * @param cid the cid
     * @param nursingCtr the nursing ctr
     * @param orderNumber the order number
     */
    public KshmtHdnursingLeavePK(String cid, Integer nursingCtr) {
        this.cid = cid;
        this.nursingCtr = nursingCtr;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (nursingCtr != null ? nursingCtr.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdnursingLeavePK)) {
            return false;
        }
        KshmtHdnursingLeavePK other = (KshmtHdnursingLeavePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.nursingCtr == null && other.nursingCtr != null) || (this.nursingCtr != null
                && !this.nursingCtr.equals(other.nursingCtr))) {
            return false;
        }
        return true;
    }
}

