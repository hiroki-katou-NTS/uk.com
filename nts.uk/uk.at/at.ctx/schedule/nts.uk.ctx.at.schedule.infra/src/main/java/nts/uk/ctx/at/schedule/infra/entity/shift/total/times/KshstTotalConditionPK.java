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
public class KshstTotalConditionPK implements Serializable {
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

    public KshstTotalConditionPK() {
    }

    public KshstTotalConditionPK(String cid, short totalTimesNo) {
        this.cid = cid;
        this.totalTimesNo = totalTimesNo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) totalTimesNo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstTotalConditionPK)) {
            return false;
        }
        KshstTotalConditionPK other = (KshstTotalConditionPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.totalTimesNo != other.totalTimesNo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KshstTotalConditionPK[ cid=" + cid + ", totalTimesNo=" + totalTimesNo + " ]";
    }
    
}
