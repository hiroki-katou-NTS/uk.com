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
 * The Class KscmtEstTimeSya.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_TIME_SYA")
public class KscmtEstTimeSya extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est time per set PK. */
    @EmbeddedId
    protected KscmtEstTimeSyaPK kscmtEstTimeSyaPK;
    
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
     * Instantiates a new kscmt est time per set.
     */
    public KscmtEstTimeSya() {
    }

    /**
     * Instantiates a new kscmt est time per set.
     *
     * @param kscmtEstTimeSyaPK the kscmt est time per set PK
     */
    public KscmtEstTimeSya(KscmtEstTimeSyaPK kscmtEstTimeSyaPK) {
        this.kscmtEstTimeSyaPK = kscmtEstTimeSyaPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstTimeSyaPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kscmtEstTimeSyaPK == null) ? 0 : kscmtEstTimeSyaPK.hashCode());
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
		KscmtEstTimeSya other = (KscmtEstTimeSya) obj;
		if (kscmtEstTimeSyaPK == null) {
			if (other.kscmtEstTimeSyaPK != null)
				return false;
		} else if (!kscmtEstTimeSyaPK.equals(other.kscmtEstTimeSyaPK))
			return false;
		return true;
	}

}
