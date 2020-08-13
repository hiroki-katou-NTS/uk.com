/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmpmtMonthPatternPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtMonthPatternPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The month pattern cd. */
    @Column(name = "CD")
    private String mPatternCd;

    /**
     * Instantiates a new kmpmt month pattern PK.
     */
    public KscmtMonthPatternPK() {
    	super();
    }

	/**
	 * Instantiates a new kmpmt month pattern PK.
	 *
	 * @param cid the cid
	 * @param mPatternCd the m pattern cd
	 */
	public KscmtMonthPatternPK(String cid, String mPatternCd) {
		super();
		this.cid = cid;
		this.mPatternCd = mPatternCd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((mPatternCd == null) ? 0 : mPatternCd.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KscmtMonthPatternPK other = (KscmtMonthPatternPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (mPatternCd == null) {
			if (other.mPatternCd != null)
				return false;
		} else if (!mPatternCd.equals(other.mPatternCd))
			return false;
		return true;
	}

}
