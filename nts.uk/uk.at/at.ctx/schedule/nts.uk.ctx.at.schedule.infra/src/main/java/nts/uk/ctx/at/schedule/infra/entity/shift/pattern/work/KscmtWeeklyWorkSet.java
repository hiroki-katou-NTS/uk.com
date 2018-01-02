/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtWeeklyWorkSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_WEEKLY_WORK_SET")
public class KscmtWeeklyWorkSet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt weekly work set PK. */
    @EmbeddedId
    protected KscmtWeeklyWorkSetPK kscmtWeeklyWorkSetPK;
    
    /** The work day div. */
    @Column(name = "WORK_DAY_ATR")
    private int workDayAtr;

    public KscmtWeeklyWorkSet() {
    }

    public KscmtWeeklyWorkSet(KscmtWeeklyWorkSetPK kwwstWeeklyWorkSetPK) {
        this.kscmtWeeklyWorkSetPK = kwwstWeeklyWorkSetPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtWeeklyWorkSetPK;
	}
    
}
