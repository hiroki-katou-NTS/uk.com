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
 * The Class KscmtEstTimeComSet.
 */

@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_TIME_COM_SET")
public class KscmtEstTimeComSet extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est time com set PK. */
    @EmbeddedId
    protected KscmtEstTimeComSetPK kscmtEstTimeComSetPK;
    
    /** The est condition 1 st time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_1ST_TIME")
    private int estCondition1stTime;
    
    /** The est condition 2 nd time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_2ND_TIME")
    private int estCondition2ndTime;
    
    /** The est condition 3 rd time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_3RD_TIME")
    private int estCondition3rdTime;
    
    /** The est condition 4 th time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_4TH_TIME")
    private int estCondition4thTime;
    
    /** The est condition 5 th time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_5TH_TIME")
    private int estCondition5thTime;


    /**
     * Instantiates a new kscmt est time com set.
     */
    public KscmtEstTimeComSet() {
    }

    /**
     * Instantiates a new kscmt est time com set.
     *
     * @param kscmtEstTimeComSetPK the kscmt est time com set PK
     */
    public KscmtEstTimeComSet(KscmtEstTimeComSetPK kscmtEstTimeComSetPK) {
        this.kscmtEstTimeComSetPK = kscmtEstTimeComSetPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstTimeComSetPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kscmtEstTimeComSetPK == null) ? 0 : kscmtEstTimeComSetPK.hashCode());
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
		KscmtEstTimeComSet other = (KscmtEstTimeComSet) obj;
		if (kscmtEstTimeComSetPK == null) {
			if (other.kscmtEstTimeComSetPK != null)
				return false;
		} else if (!kscmtEstTimeComSetPK.equals(other.kscmtEstTimeComSetPK))
			return false;
		return true;
	}
	
	

}
