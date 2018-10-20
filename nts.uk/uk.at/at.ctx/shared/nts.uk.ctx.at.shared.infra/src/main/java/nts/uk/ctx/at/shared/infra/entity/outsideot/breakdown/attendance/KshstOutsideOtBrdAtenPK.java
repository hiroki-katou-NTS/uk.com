/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstOutsideOtBrdAtenPK.
 */
@Getter
@Setter
@Embeddable
public class KshstOutsideOtBrdAtenPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Column(name = "CID")
    private String cid;
    
    /** The brd item no. */
    @Column(name = "BRD_ITEM_NO")
    private int brdItemNo;
    
    /** The attendance item id. */
    @Column(name = "ATTENDANCE_ITEM_ID")
    private int attendanceItemId;

    /**
     * Instantiates a new kshst outside ot brd aten PK.
     */
    public KshstOutsideOtBrdAtenPK() {
    	super();
    }

    /**
     * Instantiates a new kshst outside ot brd aten PK.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     * @param attendanceItemId the attendance item id
     */
    public KshstOutsideOtBrdAtenPK(String cid, int brdItemNo, int attendanceItemId) {
        this.cid = cid;
        this.brdItemNo = brdItemNo;
        this.attendanceItemId = attendanceItemId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) brdItemNo;
        hash += (int) attendanceItemId;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshstOutsideOtBrdAtenPK)) {
            return false;
        }
        KshstOutsideOtBrdAtenPK other = (KshstOutsideOtBrdAtenPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.brdItemNo != other.brdItemNo) {
            return false;
        }
        if (this.attendanceItemId != other.attendanceItemId) {
            return false;
        }
        return true;
    }

}
