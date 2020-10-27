/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

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
 * The Class KscmtEstDaysEmp.
 */

/**
 * Gets the est condition 5 th days.
 *
 * @return the est condition 5 th days
 */
@Getter

/**
 * Sets the est condition 5 th days.
 *
 * @param estCondition5thDays the new est condition 5 th days
 */
@Setter
@Entity
@Table(name = "KSCMT_EST_DAYS_EMP")
public class KscmtEstDaysEmp extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est days emp set PK. */
    @EmbeddedId
    protected KscmtEstDaysEmpPK kscmtEstDaysEmpPK;
    
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
     * Instantiates a new kscmt est days emp set.
     */
    public KscmtEstDaysEmp() {
    }

    /**
     * Instantiates a new kscmt est days emp set.
     *
     * @param kscmtEstDaysEmpPK the kscmt est days emp set PK
     */
    public KscmtEstDaysEmp(KscmtEstDaysEmpPK kscmtEstDaysEmpPK) {
        this.kscmtEstDaysEmpPK = kscmtEstDaysEmpPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstDaysEmpPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kscmtEstDaysEmpPK == null) ? 0 : kscmtEstDaysEmpPK.hashCode());
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
		KscmtEstDaysEmp other = (KscmtEstDaysEmp) obj;
		if (kscmtEstDaysEmpPK == null) {
			if (other.kscmtEstDaysEmpPK != null)
				return false;
		} else if (!kscmtEstDaysEmpPK.equals(other.kscmtEstDaysEmpPK))
			return false;
		return true;
	}

    
}
