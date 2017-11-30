/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtWeeklyWorkSetPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtWeeklyWorkSetPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The day of week. */
    @Column(name = "DAY_OF_WEEK")
    private Integer dayOfWeek;

    /**
     * Instantiates a new kscmt weekly work set PK.
     */
    public KscmtWeeklyWorkSetPK() {
    	super();
    }

	/**
	 * Instantiates a new kscmt weekly work set PK.
	 *
	 * @param cid the cid
	 * @param dayOfWeek the day of week
	 */
	public KscmtWeeklyWorkSetPK(String cid, Integer dayOfWeek) {
		super();
		this.cid = cid;
		this.dayOfWeek = dayOfWeek;
	}
    
}
