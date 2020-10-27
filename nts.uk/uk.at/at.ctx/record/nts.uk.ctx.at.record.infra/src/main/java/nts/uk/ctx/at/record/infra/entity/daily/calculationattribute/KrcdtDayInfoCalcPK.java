/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.record.infra.entity.daily.calculationattribute;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Embeddable
public class KrcdtDayInfoCalcPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "SID")
    public String sid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    public GeneralDate ymd;

    public KrcdtDayInfoCalcPK() {
    }

    public KrcdtDayInfoCalcPK(String sid, GeneralDate ymd) {
        this.sid = sid;
        this.ymd = ymd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (ymd != null ? ymd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KrcdtDayInfoCalcPK)) {
            return false;
        }
        KrcdtDayInfoCalcPK other = (KrcdtDayInfoCalcPK) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        if ((this.ymd == null && other.ymd != null) || (this.ymd != null && !this.ymd.equals(other.ymd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.KrcdtDayInfoCalcPK[ sid=" + sid + ", ymd=" + ymd + " ]";
    }
    
}
