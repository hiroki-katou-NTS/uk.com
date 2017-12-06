/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtFlexWorkTimeSetPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtFlexWorkTimeSetPK implements Serializable {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @Column(name = "CID")
    private String cid;
    
    /** The worktime cd. */
    @Basic(optional = false)
    @Column(name = "WORKTIME_CD")
    private String worktimeCd;
    
    /** The am pm class. */
    @Basic(optional = false)
    @Column(name = "AM_PM_CLASS")
    private int amPmClass;
    
    /** The time frame no. */
    @Basic(optional = false)
    @Column(name = "TIME_FRAME_NO")
    private int timeFrameNo;

    /**
     * Instantiates a new kshmt flex work time set PK.
     */
    public KshmtFlexWorkTimeSetPK() {
    }



    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (worktimeCd != null ? worktimeCd.hashCode() : 0);
        hash += (int) amPmClass;
        hash += (int) timeFrameNo;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexWorkTimeSetPK)) {
			return false;
		}
		KshmtFlexWorkTimeSetPK other = (KshmtFlexWorkTimeSetPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.amPmClass != other.amPmClass) {
			return false;
		}
		if (this.timeFrameNo != other.timeFrameNo) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
		return "entity.KshmtFlexWorkTimeSetPK[ cid=" + cid + ", worktimeCd=" + worktimeCd + ", amPmClass=" + amPmClass
				+ ", timeFrameNo=" + timeFrameNo + " ]";
	}
    
}
