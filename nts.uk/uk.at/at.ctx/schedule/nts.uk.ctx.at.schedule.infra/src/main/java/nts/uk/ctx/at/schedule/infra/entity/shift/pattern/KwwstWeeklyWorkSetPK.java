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
 * The Class KwwstWeeklyWorkSetPK.
 */
@Getter
@Setter
@Embeddable
public class KwwstWeeklyWorkSetPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The day of week. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "DAY_OF_WEEK")
    private Integer dayOfWeek;

    public KwwstWeeklyWorkSetPK() {
    }

	public KwwstWeeklyWorkSetPK(String cid, Integer dayOfWeek) {
		super();
		this.cid = cid;
		this.dayOfWeek = dayOfWeek;
	}
    
}
