/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscmtEstDaysSya.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_DAYS_SYA")
public class KscmtEstDaysSya extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est days per set PK. */
    @EmbeddedId
    protected KscmtEstDaysSyaPK kscmtEstDaysSyaPK;
    
    /** The est condition 1 st days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_1ST_DAYS")
    private double estCondition1stDays;
    
    /** The est condition 2 nd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_2ND_DAYS")
    private double estCondition2ndDays;
    
    /** The est condition 3 rd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_3RD_DAYS")
    private double estCondition3rdDays;
    
    /** The est condition 4 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_4TH_DAYS")
    private double estCondition4thDays;
    
    /** The est condition 5 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_5TH_DAYS")
    private double estCondition5thDays;

    /**
     * Instantiates a new kscmt est days per set.
     */
    public KscmtEstDaysSya() {
    }

    /**
     * Instantiates a new kscmt est days per set.
     *
     * @param kscmtEstDaysSyaPK the kscmt est days per set PK
     */
    public KscmtEstDaysSya(KscmtEstDaysSyaPK kscmtEstDaysSyaPK) {
        this.kscmtEstDaysSyaPK = kscmtEstDaysSyaPK;
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstDaysSyaPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kscmtEstDaysSyaPK == null) ? 0 : kscmtEstDaysSyaPK.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KscmtEstDaysSya other = (KscmtEstDaysSya) obj;
		if (kscmtEstDaysSyaPK == null) {
			if (other.kscmtEstDaysSyaPK != null)
				return false;
		} else if (!kscmtEstDaysSyaPK.equals(other.kscmtEstDaysSyaPK))
			return false;
		return true;
	}

}
