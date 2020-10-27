/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtEstPriceSyaPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtEstPriceSyaPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The target year. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARGET_YEAR")
    private int targetYear;
    
    /** The target cls. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARGET_CLS")
    private int targetCls;

    /**
     * Instantiates a new kscmt est price per set PK.
     */
    public KscmtEstPriceSyaPK() {
    }

    /**
     * Instantiates a new kscmt est price per set PK.
     *
     * @param sid the sid
     * @param targetYear the target year
     * @param targetCls the target cls
     */
    public KscmtEstPriceSyaPK(String sid, int targetYear, int targetCls) {
        this.sid = sid;
        this.targetYear = targetYear;
        this.targetCls = targetCls;
    }
    
}
