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
 * The Class KmpstMonthPatternSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KMPST_MONTH_PATTERN_SET")
public class KmpstMonthPatternSet extends JpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /** The kmpst month pattern set PK. */
    @EmbeddedId
    protected KmpstMonthPatternSetPK kmpstMonthPatternSetPK;

    public KmpstMonthPatternSet() {
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kmpstMonthPatternSetPK;
	}

  
    
}
