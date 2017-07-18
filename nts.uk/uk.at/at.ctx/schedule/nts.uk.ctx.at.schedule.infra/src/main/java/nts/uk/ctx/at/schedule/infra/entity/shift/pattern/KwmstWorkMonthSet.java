/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KwmstWorkMonthSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KWMST_WORK_MONTH_SET")
public class KwmstWorkMonthSet extends JpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** The kwmst work month set PK. */
    @EmbeddedId
    protected KwmstWorkMonthSetPK kwmstWorkMonthSetPK;

    public KwmstWorkMonthSet() {
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kwmstWorkMonthSetPK;
	}

}
