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
 * The Class KshmtFlexStampReflectPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtFlexStampReflectPK implements Serializable {

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
    
    /** The work no. */
    @Basic(optional = false)
    @Column(name = "WORK_NO")
    private short workNo;

    /**
     * Instantiates a new kshmt flex stamp reflect PK.
     */
    public KshmtFlexStampReflectPK() {
    }

    /**
     * Instantiates a new kshmt flex stamp reflect PK.
     *
     * @param cid the cid
     * @param worktimeCd the worktime cd
     * @param workNo the work no
     */
    public KshmtFlexStampReflectPK(String cid, String worktimeCd, short workNo) {
        this.cid = cid;
        this.worktimeCd = worktimeCd;
        this.workNo = workNo;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (worktimeCd != null ? worktimeCd.hashCode() : 0);
        hash += (int) workNo;
        return hash;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexStampReflectPK)) {
			return false;
		}
		KshmtFlexStampReflectPK other = (KshmtFlexStampReflectPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.workNo != other.workNo) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtFlexStampReflectPK[ cid=" + cid + ", worktimeCd=" + worktimeCd + ", workNo=" + workNo
				+ " ]";
	}
	
    
}
