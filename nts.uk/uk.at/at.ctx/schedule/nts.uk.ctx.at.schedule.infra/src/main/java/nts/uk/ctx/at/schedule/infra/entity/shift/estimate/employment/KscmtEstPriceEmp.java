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
 * The Class KscmtEstPriceEmp.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_PRICE_EMP")
public class KscmtEstPriceEmp extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est price emp set PK. */
    @EmbeddedId
    protected KscmtEstPriceEmpPK kscmtEstPriceEmpPK;
    
    /** The est condition 1 st mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_1ST_MNY")
    private int estCondition1stMny;
    
    /** The est condition 2 nd mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_2ND_MNY")
    private int estCondition2ndMny;
    
    /** The est condition 3 rd mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_3RD_MNY")
    private int estCondition3rdMny;
    
    /** The est condition 4 th mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_4TH_MNY")
    private int estCondition4thMny;
    
    /** The est condition 5 th mny. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_5TH_MNY")
    private int estCondition5thMny;

    /**
     * Instantiates a new kscmt est price emp set.
     */
    public KscmtEstPriceEmp() {
    }

    /**
     * Instantiates a new kscmt est price emp set.
     *
     * @param kscmtEstPriceEmpPK the kscmt est price emp set PK
     */
    public KscmtEstPriceEmp(KscmtEstPriceEmpPK kscmtEstPriceEmpPK) {
        this.kscmtEstPriceEmpPK = kscmtEstPriceEmpPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstPriceEmpPK;
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
				+ ((kscmtEstPriceEmpPK == null) ? 0 : kscmtEstPriceEmpPK.hashCode());
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
		KscmtEstPriceEmp other = (KscmtEstPriceEmp) obj;
		if (kscmtEstPriceEmpPK == null) {
			if (other.kscmtEstPriceEmpPK != null)
				return false;
		} else if (!kscmtEstPriceEmpPK.equals(other.kscmtEstPriceEmpPK))
			return false;
		return true;
	}

}
