/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KbtdtExtBudgetTimePK.
 */
@Embeddable
@Setter
@Getter
public class KbtdtExtBudgetTimePK implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;

    /** The wkpid. */
    @Basic(optional = false)
    @Column(name = "WKPID")
    private String wkpid;

    /**
     * Instantiates a new kbtdt ext budget time PK.
     */
    public KbtdtExtBudgetTimePK() {
    }

    /**
     * Instantiates a new kbtdt ext budget time PK.
     *
     * @param cid
     *            the cid
     * @param wkpid
     *            the wkpid
     */
    public KbtdtExtBudgetTimePK(String cid, String wkpid) {
        this.cid = cid;
        this.wkpid = wkpid;
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
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KbtdtExtBudgetTimePK)) {
            return false;
        }
        KbtdtExtBudgetTimePK other = (KbtdtExtBudgetTimePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        return true;
    }

}
