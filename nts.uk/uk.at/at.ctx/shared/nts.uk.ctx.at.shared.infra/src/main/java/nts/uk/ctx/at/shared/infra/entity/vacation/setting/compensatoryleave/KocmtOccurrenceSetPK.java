/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KocmtOccurrenceSetPK.
 */
@Setter
@Getter
@Embeddable
public class KocmtOccurrenceSetPK implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;

    /** The occurr type. */
    @Basic(optional = false)
    @Column(name = "OCCURR_TYPE")
    private Integer occurrType;

    /**
     * Instantiates a new kocmt occurrence set PK.
     */
    public KocmtOccurrenceSetPK() {
    }

    /**
     * Instantiates a new kocmt occurrence set PK.
     *
     * @param cid
     *            the cid
     * @param occurrType
     *            the occurr type
     */
    public KocmtOccurrenceSetPK(String cid, Integer occurrType) {
        this.cid = cid;
        this.occurrType = occurrType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) occurrType;
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KocmtOccurrenceSetPK)) {
            return false;
        }
        KocmtOccurrenceSetPK other = (KocmtOccurrenceSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.occurrType != other.occurrType) {
            return false;
        }
        return true;
    }
}
