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
 * The Class KmpmtMonthPatternPK.
 */
@Getter
@Setter
@Embeddable
public class KmpmtMonthPatternPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The m pattern cd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "M_PATTERN_CD")
    private String mPatternCd;

    /**
     * Instantiates a new kmpmt month pattern PK.
     */
    public KmpmtMonthPatternPK() {
    }

	/**
	 * Instantiates a new kmpmt month pattern PK.
	 *
	 * @param cid the cid
	 * @param mPatternCd the m pattern cd
	 */
	public KmpmtMonthPatternPK(String cid, String mPatternCd) {
		super();
		this.cid = cid;
		this.mPatternCd = mPatternCd;
	}    
    
}
