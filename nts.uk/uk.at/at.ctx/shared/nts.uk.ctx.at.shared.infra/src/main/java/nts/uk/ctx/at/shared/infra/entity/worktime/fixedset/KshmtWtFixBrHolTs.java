/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFixBrHolTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FIX_BR_HOL_TS")
public class KshmtWtFixBrHolTs extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt fixed hol rest set PK. */
    @EmbeddedId
    protected KshmtWtFixBrHolTsPK kshmtWtFixBrHolTsPK;
    
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The start time. */
    @Column(name = "START_TIME")
    private int startTime;
    
    
    /** The end time. */
    @Column(name = "END_TIME")
    private int endTime;

    /**
     * Instantiates a new kshmt fixed hol rest set.
     */
    public KshmtWtFixBrHolTs() {
    	super();
    }
    
    /**
     * Instantiates a new kshmt fixed hol rest set.
     *
     * @param kshmtWtFixBrHolTsPK the kshmt fixed hol rest set PK
     */
    public KshmtWtFixBrHolTs(KshmtWtFixBrHolTsPK kshmtWtFixBrHolTsPK) {
    	super();
    	this.kshmtWtFixBrHolTsPK = kshmtWtFixBrHolTsPK;
    }
    
    /**
     * Instantiates a new kshmt fixed hol rest set.
     *
     * @param cid the cid
     * @param worktimeCd the worktime cd
     * @param periodNo the period no
     */
    public KshmtWtFixBrHolTs(String cid, String worktimeCd,int periodNo) {
		this.kshmtWtFixBrHolTsPK = new KshmtWtFixBrHolTsPK(cid, worktimeCd,periodNo);
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtWtFixBrHolTsPK != null ? kshmtWtFixBrHolTsPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtWtFixBrHolTs)) {
            return false;
        }
        KshmtWtFixBrHolTs other = (KshmtWtFixBrHolTs) object;
        if ((this.kshmtWtFixBrHolTsPK == null && other.kshmtWtFixBrHolTsPK != null) || (this.kshmtWtFixBrHolTsPK != null && !this.kshmtWtFixBrHolTsPK.equals(other.kshmtWtFixBrHolTsPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtWtFixBrHolTsPK;
	}
    
}
