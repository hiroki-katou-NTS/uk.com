/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.dailypattern;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The Class KdpstDailyPatternSetPK.
 */
@Getter
@Setter
@Embeddable
public class KdpstDailyPatternSetPK implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
    @Column(name = "CID")
	public String cid;
	
    /** The cid. */
    @Column(name = "CD")
    public String patternCd;
	
    /**
     * Instantiates a new kcsmt work type PK.
     */
    public KdpstDailyPatternSetPK(){
    	super();
    }

	/**
	 * Instantiates a new kcsmt work type PK.
	 *
	 * @param cid the cid
	 * @param patternCd the pattern cd
	 */
	public KdpstDailyPatternSetPK(String cid, String patternCd) {
		this.cid = cid;
		this.patternCd = patternCd;
	}
	
	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (patternCd != null ? patternCd.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KdpstDailyPatternSetPK)) {
            return false;
        }
        KdpstDailyPatternSetPK other = (KdpstDailyPatternSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.patternCd == null && other.patternCd != null) || (this.patternCd != null
                && !this.patternCd.equals(other.patternCd))) {
            return false;
        }
        return true;
    }
}
