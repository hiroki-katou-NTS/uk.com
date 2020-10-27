/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company;

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
 * The Class KscmtEstDaysComSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_DAYS_COM_SET")
public class KscmtEstDaysComSet extends ContractUkJpaEntity  implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KscmtEstDaysComSetPK kscmtEstDaysComSetPK;
    
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
     * Instantiates a new kscmt est days com set.
     */
    public KscmtEstDaysComSet() {
    }

    /**
     * Instantiates a new kscmt est days com set.
     *
     * @param kscmtEstDaysComSetPK the kscmt est days com set PK
     */
    public KscmtEstDaysComSet(KscmtEstDaysComSetPK kscmtEstDaysComSetPK) {
        this.kscmtEstDaysComSetPK = kscmtEstDaysComSetPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstDaysComSetPK;
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
				+ ((kscmtEstDaysComSetPK == null) ? 0 : kscmtEstDaysComSetPK.hashCode());
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
		KscmtEstDaysComSet other = (KscmtEstDaysComSet) obj;
		if (kscmtEstDaysComSetPK == null) {
			if (other.kscmtEstDaysComSetPK != null)
				return false;
		} else if (!kscmtEstDaysComSetPK.equals(other.kscmtEstDaysComSetPK))
			return false;
		return true;
	}

}
