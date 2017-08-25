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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtEstDaysPerSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_DAYS_PER_SET")
public class KscmtEstDaysPerSet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est days per set PK. */
    @EmbeddedId
    protected KscmtEstDaysPerSetPK kscmtEstDaysPerSetPK;
    
    /** The est condition 1 st days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_1ST_DAYS")
    private int estCondition1stDays;
    
    /** The est condition 2 nd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_2ND_DAYS")
    private int estCondition2ndDays;
    
    /** The est condition 3 rd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_3RD_DAYS")
    private int estCondition3rdDays;
    
    /** The est condition 4 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_4TH_DAYS")
    private int estCondition4thDays;
    
    /** The est condition 5 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_5TH_DAYS")
    private int estCondition5thDays;

    /**
     * Instantiates a new kscmt est days per set.
     */
    public KscmtEstDaysPerSet() {
    }

    /**
     * Instantiates a new kscmt est days per set.
     *
     * @param kscmtEstDaysPerSetPK the kscmt est days per set PK
     */
    public KscmtEstDaysPerSet(KscmtEstDaysPerSetPK kscmtEstDaysPerSetPK) {
        this.kscmtEstDaysPerSetPK = kscmtEstDaysPerSetPK;
    }


	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtEstDaysPerSetPK;
	}
	
    
}
