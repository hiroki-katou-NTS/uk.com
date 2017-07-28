/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

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
 * The Class KwwstWeeklyWorkSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KWWST_WEEKLY_WORK_SET")
public class KwwstWeeklyWorkSet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kwwst weekly work set PK. */
    @EmbeddedId
    protected KwwstWeeklyWorkSetPK kwwstWeeklyWorkSetPK;
    
    /** The work day div. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORK_DAY_DIV")
    private Integer workDayDiv;

    public KwwstWeeklyWorkSet() {
    }

    public KwwstWeeklyWorkSet(KwwstWeeklyWorkSetPK kwwstWeeklyWorkSetPK) {
        this.kwwstWeeklyWorkSetPK = kwwstWeeklyWorkSetPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kwwstWeeklyWorkSetPK;
	}
    
}
