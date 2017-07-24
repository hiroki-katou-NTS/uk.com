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
 * The Class KbldtExtBudgetLogPK.
 */
@Embeddable
@Setter
@Getter
public class KbldtExtBudgetLogPK implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;

    /** The exe id. */
    @Basic(optional = false)
    @Column(name = "EXE_ID")
    private String exeId;

    /**
     * Instantiates a new kbldt ext budget log PK.
     */
    public KbldtExtBudgetLogPK() {
    }

    /**
     * Instantiates a new kbldt ext budget log PK.
     *
     * @param cid
     *            the cid
     * @param exeId
     *            the exe id
     */
    public KbldtExtBudgetLogPK(String cid, String exeId) {
        this.cid = cid;
        this.exeId = exeId;
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
        hash += (exeId != null ? exeId.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KbldtExtBudgetLogPK)) {
            return false;
        }
        KbldtExtBudgetLogPK other = (KbldtExtBudgetLogPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.exeId == null && other.exeId != null) || (this.exeId != null && !this.exeId.equals(other.exeId))) {
            return false;
        }
        return true;
    }
}
