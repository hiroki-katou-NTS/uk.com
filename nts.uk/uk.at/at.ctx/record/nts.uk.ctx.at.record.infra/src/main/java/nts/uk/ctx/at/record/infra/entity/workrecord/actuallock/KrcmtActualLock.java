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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KrcmtActualLock.
 */
@Setter
@Getter
@Entity
@Table(name = "KRCMT_ACTUAL_LOCK")

public class KrcmtActualLock extends UkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The krcmt actual lock PK. */
    @EmbeddedId
    protected KrcmtActualLockPK krcmtActualLockPK;
    
    /** The d lock state. */
    @Column(name = "D_LOCK_STATE")
    private short dLockState;
    
    /** The m lock state. */
    @Column(name = "M_LOCK_STATE")
    private short mLockState;

    /**
     * Instantiates a new krcmt actual lock.
     */
    public KrcmtActualLock() {
    	super();
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.krcmtActualLockPK;
	}


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((krcmtActualLockPK == null) ? 0 : krcmtActualLockPK.hashCode());
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
		KrcmtActualLock other = (KrcmtActualLock) obj;
		if (krcmtActualLockPK == null) {
			if (other.krcmtActualLockPK != null)
				return false;
		} else if (!krcmtActualLockPK.equals(other.krcmtActualLockPK))
			return false;
		return true;
	}

	

}
