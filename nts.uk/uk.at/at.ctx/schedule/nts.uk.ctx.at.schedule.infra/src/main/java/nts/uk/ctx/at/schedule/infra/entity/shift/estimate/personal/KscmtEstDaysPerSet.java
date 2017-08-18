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
    
    /** The m condition 1 st days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_1ST_DAYS")
    private short mCondition1stDays;
    
    /** The m condition 2 nd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_2ND_DAYS")
    private short mCondition2ndDays;
    
    /** The m condition 3 rd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_3RD_DAYS")
    private short mCondition3rdDays;
    
    /** The m condition 4 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_4TH_DAYS")
    private short mCondition4thDays;
    
    /** The m condition 5 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_5TH_DAYS")
    private short mCondition5thDays;
    
    /** The y condition 1 st days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_1ST_DAYS")
    private short yCondition1stDays;
    
    /** The y condition 2 nd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_2ND_DAYS")
    private short yCondition2ndDays;
    
    /** The y condition 3 rd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_3RD_DAYS")
    private short yCondition3rdDays;
    
    /** The y condition 4 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_4TH_DAYS")
    private short yCondition4thDays;
    
    /** The y condition 5 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_5TH_DAYS")
    private short yCondition5thDays;

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
