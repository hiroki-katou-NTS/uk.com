/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.total.times;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Embeddable
public class KshstTotalSubjectsPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_TIMES_NO")
    private short totalTimesNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORK_TYPE_ATR")
    private short workTypeAtr;

    public KshstTotalSubjectsPK() {
    }

    public KshstTotalSubjectsPK(String cid, short totalTimesNo, short workTypeAtr) {
        this.cid = cid;
        this.totalTimesNo = totalTimesNo;
        this.workTypeAtr = workTypeAtr;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public short getTotalTimesNo() {
        return totalTimesNo;
    }

    public void setTotalTimesNo(short totalTimesNo) {
        this.totalTimesNo = totalTimesNo;
    }

    public short getWorkTypeAtr() {
        return workTypeAtr;
    }

    public void setWorkTypeAtr(short workTypeAtr) {
        this.workTypeAtr = workTypeAtr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) totalTimesNo;
        hash += (int) workTypeAtr;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstTotalSubjectsPK)) {
            return false;
        }
        KshstTotalSubjectsPK other = (KshstTotalSubjectsPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.totalTimesNo != other.totalTimesNo) {
            return false;
        }
        if (this.workTypeAtr != other.workTypeAtr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KshstTotalSubjectsPK[ cid=" + cid + ", totalTimesNo=" + totalTimesNo + ", workTypeAtr=" + workTypeAtr + " ]";
    }
    
}
