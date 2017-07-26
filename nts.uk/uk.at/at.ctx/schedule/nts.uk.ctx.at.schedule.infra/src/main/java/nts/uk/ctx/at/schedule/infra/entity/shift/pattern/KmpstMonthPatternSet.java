/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
    
    /** The sid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The m pattern cd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_PATTERN_CD")
    private String mPatternCd;

    public KmpstMonthPatternSet() {
    }

    public KmpstMonthPatternSet(String sid) {
        this.sid = sid;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.sid;
	}

   
}
