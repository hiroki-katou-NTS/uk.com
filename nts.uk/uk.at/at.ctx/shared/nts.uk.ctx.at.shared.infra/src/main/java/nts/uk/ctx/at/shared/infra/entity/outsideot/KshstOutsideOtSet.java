/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstOutsideOtSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_OUTSIDE_OT_SET")
public class KshstOutsideOtSet implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The note. */
    @Column(name = "NOTE")
    private String note;
    
    /** The calculation method. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALCULATION_METHOD")
    private int calculationMethod;

    /**
     * Instantiates a new kshst over time set.
     */
    public KshstOutsideOtSet() {
    }

    /**
     * Instantiates a new kshst over time set.
     *
     * @param cid the cid
     */
    public KshstOutsideOtSet(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kshst over time set.
     *
     * @param cid the cid
     * @param calculationMethod the calculation method
     */
    public KshstOutsideOtSet(String cid, int calculationMethod) {
        this.cid = cid;
        this.calculationMethod = calculationMethod;
    }

    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstOutsideOtSet)) {
			return false;
		}
		KshstOutsideOtSet other = (KshstOutsideOtSet) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshstOverTimeSet[ cid=" + cid + " ]";
	}
    
}
