/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtFlexHolSetPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtFlexHolSetPK implements Serializable {

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
    
    /** The worktime no. */
    @Basic(optional = false)
    @Column(name = "WORKTIME_NO")
    private int worktimeNo;

    /**
     * Instantiates a new kshmt flex hol set PK.
     */
    public KshmtFlexHolSetPK() {
    }

    /**
     * Instantiates a new kshmt flex hol set PK.
     *
     * @param cid the cid
     * @param worktimeCd the worktime cd
     * @param worktimeNo the worktime no
     */
    public KshmtFlexHolSetPK(String cid, String worktimeCd, int worktimeNo) {
        this.cid = cid;
        this.worktimeCd = worktimeCd;
        this.worktimeNo = worktimeNo;
    }

    /**
     * Gets the cid.
     *
     * @return the cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * Sets the cid.
     *
     * @param cid the new cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * Gets the worktime cd.
     *
     * @return the worktime cd
     */
    public String getWorktimeCd() {
        return worktimeCd;
    }

    /**
     * Sets the worktime cd.
     *
     * @param worktimeCd the new worktime cd
     */
    public void setWorktimeCd(String worktimeCd) {
        this.worktimeCd = worktimeCd;
    }

    /**
     * Gets the worktime no.
     *
     * @return the worktime no
     */
    public int getWorktimeNo() {
        return worktimeNo;
    }

    /**
     * Sets the worktime no.
     *
     * @param worktimeNo the new worktime no
     */
    public void setWorktimeNo(int worktimeNo) {
        this.worktimeNo = worktimeNo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (worktimeCd != null ? worktimeCd.hashCode() : 0);
        hash += (int) worktimeNo;
        return hash;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtFlexHolSetPK)) {
			return false;
		}
		KshmtFlexHolSetPK other = (KshmtFlexHolSetPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.worktimeNo != other.worktimeNo) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexHolSetPK[ cid=" + cid + ", worktimeCd=" + worktimeCd + ", worktimeNo=" + worktimeNo
				+ " ]";
	}

}
