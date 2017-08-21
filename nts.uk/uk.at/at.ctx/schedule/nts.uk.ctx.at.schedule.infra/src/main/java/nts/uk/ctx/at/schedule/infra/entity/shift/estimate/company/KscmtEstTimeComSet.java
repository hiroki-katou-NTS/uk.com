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
 * The Class KscmtEstTimeComSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_EST_TIME_COM_SET")
public class KscmtEstTimeComSet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt est time com set PK. */
    @EmbeddedId
    protected KscmtEstTimeComSetPK kscmtEstTimeComSetPK;
    
    /** The m condition 1 st time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_1ST_TIME")
    private int mCondition1stTime;
    
    /** The m condition 2 nd time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_2ND_TIME")
    private int mCondition2ndTime;
    
    /** The m condition 3 rd time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_3RD_TIME")
    private int mCondition3rdTime;
    
    /** The m condition 4 th time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_4TH_TIME")
    private int mCondition4thTime;
    
    /** The m condition 5 th time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_CONDITION_5TH_TIME")
    private int mCondition5thTime;
    
    /** The y condition 1 st time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_1ST_TIME")
    private int yCondition1stTime;
    
    /** The y condition 2 nd time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_2ND_TIME")
    private int yCondition2ndTime;
    
    /** The y condition 3 rd time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_3RD_TIME")
    private int yCondition3rdTime;
    
    /** The y condition 4 th time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_4TH_TIME")
    private int yCondition4thTime;
    
    /** The y condition 5 th time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "Y_CONDITION_5TH_TIME")
    private int yCondition5thTime;

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

}
