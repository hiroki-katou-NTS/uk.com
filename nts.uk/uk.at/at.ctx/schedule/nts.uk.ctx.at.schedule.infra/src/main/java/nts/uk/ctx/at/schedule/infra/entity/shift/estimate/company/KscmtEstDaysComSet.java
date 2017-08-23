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
    
    /** The est condition 1 st days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_1ST_DAYS")
    private short estCondition1stDays;
    
    /** The est condition 2 nd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_2ND_DAYS")
    private short estCondition2ndDays;
    
    /** The est condition 3 rd days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_3RD_DAYS")
    private short estCondition3rdDays;
    
    /** The est condition 4 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_4TH_DAYS")
    private short estCondition4thDays;
    
    /** The est condition 5 th days. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EST_CONDITION_5TH_DAYS")
    private short estCondition5thDays;
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
