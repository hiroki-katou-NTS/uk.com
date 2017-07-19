/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmpstMonthPatternSetPK.
 */
@Getter
@Setter
@Embeddable
public class KmpstMonthPatternSetPK implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The month pattern cd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "MONTH_PATTERN_CD")
    private String monthPatternCd;
    
    /** The sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;

    public KmpstMonthPatternSetPK() {
    }
    
}
