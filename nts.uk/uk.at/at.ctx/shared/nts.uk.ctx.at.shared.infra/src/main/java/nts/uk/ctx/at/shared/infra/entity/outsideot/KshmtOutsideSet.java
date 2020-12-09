/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtOutsideSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_OUTSIDE_SET")
public class KshmtOutsideSet extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Id
    @Column(name = "CID")
    private String cid;
    
    /** The note. */
    @Column(name = "NOTE")
    private String note;
    
    /** The calculation method. */
    @Column(name = "CALCULATION_METHOD")
    private int calculationMethod;

    /**
     * Instantiates a new kshst over time set.
     */
    public KshmtOutsideSet() {
    	super();
    }

    /**
     * Instantiates a new kshst over time set.
     *
     * @param cid the cid
     */
    public KshmtOutsideSet(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kshst over time set.
     *
     * @param cid the cid
     * @param calculationMethod the calculation method
     */
    public KshmtOutsideSet(String cid, int calculationMethod) {
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
		if (!(object instanceof KshmtOutsideSet)) {
			return false;
		}
		KshmtOutsideSet other = (KshmtOutsideSet) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}

}
