/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Class KscmtWsPersonFeeTime.
 */
@Entity
@Table(name = "KSCMT_WS_PERSON_FEE_TIME")
public class KscmtWsPersonFeeTime implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt ws person fee time PK. */
    @EmbeddedId
    protected KscmtWsPersonFeeTimePK kscmtWsPersonFeeTimePK;
    
    /** The person fee time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERSON_FEE_TIME")
    private int personFeeTime;

    /**
     * Instantiates a new kscmt ws person fee time.
     */
    public KscmtWsPersonFeeTime() {
    }

    /**
     * Instantiates a new kscmt ws person fee time.
     *
     * @param kscmtWsPersonFeeTimePK the kscmt ws person fee time PK
     */
    public KscmtWsPersonFeeTime(KscmtWsPersonFeeTimePK kscmtWsPersonFeeTimePK) {
        this.kscmtWsPersonFeeTimePK = kscmtWsPersonFeeTimePK;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kscmtWsPersonFeeTimePK != null ? kscmtWsPersonFeeTimePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscmtWsPersonFeeTime)) {
			return false;
		}
		KscmtWsPersonFeeTime other = (KscmtWsPersonFeeTime) object;
		if ((this.kscmtWsPersonFeeTimePK == null && other.kscmtWsPersonFeeTimePK != null)
				|| (this.kscmtWsPersonFeeTimePK != null
						&& !this.kscmtWsPersonFeeTimePK.equals(other.kscmtWsPersonFeeTimePK))) {
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
		return "entity.KscmtWsPersonFeeTime[ kscmtWsPersonFeeTimePK=" + kscmtWsPersonFeeTimePK
				+ " ]";
	}
    
}
