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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletime.KscdtScheTime;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscdtScheFeeTime.
 */
@Getter
@Entity
@Table(name = "KSCDT_SCHE_FEE_TIME")
public class KscdtScheFeeTime extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscdt sche fee time PK. */
    @EmbeddedId
    protected KscdtScheFeeTimePK kscdtScheFeeTimePK;
    
    /** The person fee time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERSON_FEE_TIME")
    private int personFeeTime;
    
    @ManyToOne
	@JoinColumns({
			@JoinColumn(name = "SID", referencedColumnName = "KSCDT_SCHE_TIME.SID", insertable = false, updatable = false),
			@JoinColumn(name = "YMD", referencedColumnName = "KSCDT_SCHE_TIME.YMD", insertable = false, updatable = false) })
	public KscdtScheTime kscdtScheTime;

    /**
     * Instantiates a new kscmt ws person fee time.
     */
    public KscdtScheFeeTime() {
    }

    /**
     * Instantiates a new kscmt ws person fee time.
     *
     * @param kscmtWsPersonFeeTimePK the kscmt ws person fee time PK
     */
    public KscdtScheFeeTime(KscdtScheFeeTimePK kscmtWsPersonFeeTimePK) {
        this.kscdtScheFeeTimePK = kscmtWsPersonFeeTimePK;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kscdtScheFeeTimePK != null ? kscdtScheFeeTimePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscdtScheFeeTime)) {
			return false;
		}
		KscdtScheFeeTime other = (KscdtScheFeeTime) object;
		if ((this.kscdtScheFeeTimePK == null && other.kscdtScheFeeTimePK != null)
				|| (this.kscdtScheFeeTimePK != null
						&& !this.kscdtScheFeeTimePK.equals(other.kscdtScheFeeTimePK))) {
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
		return "entity.KscmtWsPersonFeeTime[ kscmtWsPersonFeeTimePK=" + kscdtScheFeeTimePK
				+ " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscdtScheFeeTimePK;
	}
    
}
