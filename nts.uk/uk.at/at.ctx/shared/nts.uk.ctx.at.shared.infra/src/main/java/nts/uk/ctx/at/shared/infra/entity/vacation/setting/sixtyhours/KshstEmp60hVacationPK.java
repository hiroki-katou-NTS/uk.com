/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstEmp60hVacationPK.
 *
 * @author NWS_THANHNC_PC
 */

@Getter
@Setter
@Embeddable
public class KshstEmp60hVacationPK implements Serializable {
    
    /** The cid. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    /** The contract type cd. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "CONTRACT_TYPE_CD")
    private String contractTypeCd;

    /**
     * Instantiates a new kshst emp 60 h vacation PK.
     */
    public KshstEmp60hVacationPK() {
    }

    /**
     * Instantiates a new kshst emp 60 h vacation PK.
     *
     * @param cid the cid
     * @param contractTypeCd the contract type cd
     */
    public KshstEmp60hVacationPK(String cid, String contractTypeCd) {
        this.cid = cid;
        this.contractTypeCd = contractTypeCd;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (contractTypeCd != null ? contractTypeCd.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshstEmp60hVacationPK)) {
            return false;
        }
        KshstEmp60hVacationPK other = (KshstEmp60hVacationPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.contractTypeCd == null && other.contractTypeCd != null) || (this.contractTypeCd != null && !this.contractTypeCd.equals(other.contractTypeCd))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshstEmp60hVacationPK[ cid=" + cid + ", contractTypeCd=" + contractTypeCd + " ]";
    }
    
}
