/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.attendance;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

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
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The brd item no. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "BRD_ITEM_NO")
    private int brdItemNo;
    
    /** The attendance item id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ATTENDANCE_ITEM_ID")
    private int attendanceItemId;

    public KshstOutsideOtBrdAtenPK() {
    }

    public KshstOutsideOtBrdAtenPK(String cid, int brdItemNo, int attendanceItemId) {
        this.cid = cid;
        this.brdItemNo = brdItemNo;
        this.attendanceItemId = attendanceItemId;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) brdItemNo;
        hash += (int) attendanceItemId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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

    @Override
    public String toString() {
        return "entity.KshstOverTimeBrdAtenPK[ cid=" + cid + ", brdItemNo=" + brdItemNo + ", attendanceItemId=" + attendanceItemId + " ]";
    }
    
}
