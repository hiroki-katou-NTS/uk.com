/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

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
public class KscmtWeeklyWorkSetPK implements Serializable {
    
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

    public KscmtWeeklyWorkSetPK() {
    }

	public KscmtWeeklyWorkSetPK(String cid, Integer dayOfWeek) {
		super();
		this.cid = cid;
		this.dayOfWeek = dayOfWeek;
	}
    
}
