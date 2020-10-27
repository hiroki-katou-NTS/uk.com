/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.actuallock;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcdtActualLockHist.
 */
@Setter
@Getter
@Entity
@Table(name = "KRCDT_ACTUAL_LOCK_HIST")
public class KrcdtActualLockHist extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The krcmt actual lock hist PK. */
    @EmbeddedId
    protected KrcdtActualLockHistPK krcdtActualLockHistPK;
    
    /** The target month. */
    @Column(name = "TARGET_MONTH")
    private int targetMonth;
    
    /** The updator. */
    @Column(name = "UPDATOR")
    private String updator;
    
    /** The d lock state. */
    @Column(name = "D_LOCK_STATE")
    private int dLockState;
    
    /** The m lock state. */
    @Column(name = "M_LOCK_STATE")
    private int mLockState;

    /**
     * Instantiates a new krcmt actual lock hist.
     */
    public KrcdtActualLockHist() {
    	super();
    }
    
    /**
     * Gets the key.
     *
     * @return the key
     */
    @Override
	protected Object getKey() {
		return this.krcdtActualLockHistPK;
	}
    
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((krcdtActualLockHistPK == null) ? 0 : krcdtActualLockHistPK.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrcdtActualLockHist other = (KrcdtActualLockHist) obj;
		if (krcdtActualLockHistPK == null) {
			if (other.krcdtActualLockHistPK != null)
				return false;
		} else if (!krcdtActualLockHistPK.equals(other.krcdtActualLockHistPK))
			return false;
		return true;
	}

}
