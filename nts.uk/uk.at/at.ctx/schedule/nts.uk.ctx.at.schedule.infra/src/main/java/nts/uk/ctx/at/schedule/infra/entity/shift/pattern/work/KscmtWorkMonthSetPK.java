/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtWorkMonthSetPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtWorkMonthSetPK implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The m pattern cd. */
    @Column(name = "M_PATTERN_CD")
    private String mPatternCd;
    
    /** The ymd K. */
    @Column(name = "YMD_K")
    private BigDecimal ymdK;

    /**
     * Instantiates a new kwmmt work month set PK.
     */
    public KscmtWorkMonthSetPK() {
    	super();
    }

	/**
	 * Instantiates a new kwmmt work month set PK.
	 *
	 * @param cid the cid
	 * @param mPatternCd the m pattern cd
	 * @param ymdK the ymd K
	 */
	public KscmtWorkMonthSetPK(String cid, String mPatternCd, BigDecimal ymdK) {
		super();
		this.cid = cid;
		this.mPatternCd = mPatternCd;
		this.ymdK = ymdK;
	}
    
}
