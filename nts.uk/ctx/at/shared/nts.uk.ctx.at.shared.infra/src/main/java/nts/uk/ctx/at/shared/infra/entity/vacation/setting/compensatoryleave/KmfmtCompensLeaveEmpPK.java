/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * The Class KmfmtCompensLeaveEmpPK.
 */
@Data
@Embeddable
public class KmfmtCompensLeaveEmpPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "EMP_CD")
    private String empCd;

    public KmfmtCompensLeaveEmpPK() {
    }

    public KmfmtCompensLeaveEmpPK(String cid, String empCd) {
        this.cid = cid;
        this.empCd = empCd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (empCd != null ? empCd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KmfmtCompensLeaveEmpPK)) {
            return false;
        }
        KmfmtCompensLeaveEmpPK other = (KmfmtCompensLeaveEmpPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.empCd == null && other.empCd != null) || (this.empCd != null && !this.empCd.equals(other.empCd))) {
            return false;
        }
        return true;
    }
}
