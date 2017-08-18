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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtEstDaysComSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_DAYS_COM_SET")
public class KscmtEstDaysComSet extends UkJpaEntity  implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KscmtEstDaysComSetPK kscmtEstDaysComSetPK;
    
    /** The m condition 1 st days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_1ST_DAYS")
    private int mCondition1stDays;
    
    /** The m condition 2 nd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_2ND_DAYS")
    private int mCondition2ndDays;
    
    /** The m condition 3 rd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_3RD_DAYS")
    private int mCondition3rdDays;
    
    /** The m condition 4 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_4TH_DAYS")
    private int mCondition4thDays;
    
    /** The m condition 5 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_5TH_DAYS")
    private int mCondition5thDays;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_1ST_DAYS")
    private int yCondition1stDays;
    
    /** The y condition 2 nd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_2ND_DAYS")
    private int yCondition2ndDays;
    
    /** The y condition 3 rd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_3RD_DAYS")
    private int yCondition3rdDays;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_4TH_DAYS")
    private int yCondition4thDays;
    
    /** The y condition 5 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_5TH_DAYS")
    private int yCondition5thDays;

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

  
}
