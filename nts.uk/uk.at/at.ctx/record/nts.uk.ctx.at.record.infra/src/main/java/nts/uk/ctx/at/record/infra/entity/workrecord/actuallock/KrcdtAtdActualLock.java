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
 * The Class KrcdtAtdActualLock.
 */
@Setter
@Getter
@Entity
@Table(name = "KRCDT_ATD_ACTUAL_LOCK")

public class KrcdtAtdActualLock extends ContractUkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The krcmt actual lock PK. */
    @EmbeddedId
    protected KrcdtAtdActualLockPK krcdtAtdActualLockPK;
    
    /** The d lock state. */
    @Column(name = "D_LOCK_STATE")
    private int dLockState;
    
    /** The m lock state. */
    @Column(name = "M_LOCK_STATE")
    private int mLockState;

    /**
     * Instantiates a new krcmt actual lock.
     */
    public KrcdtAtdActualLock() {
    	super();
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.krcdtAtdActualLockPK;
	}


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((krcdtAtdActualLockPK == null) ? 0 : krcdtAtdActualLockPK.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KrcdtAtdActualLock other = (KrcdtAtdActualLock) obj;
		if (krcdtAtdActualLockPK == null) {
			if (other.krcdtAtdActualLockPK != null)
				return false;
		} else if (!krcdtAtdActualLockPK.equals(other.krcdtAtdActualLockPK))
			return false;
		return true;
	}

	

}
