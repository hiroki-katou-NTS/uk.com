/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * The Class KmnmtEmpWorkplacePK.
 */
@Embeddable
public class KmnmtEmpWorkplacePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The scd. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "SCD")
    private String scd;
    
    /** The wpl id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WPL_ID")
    private String wplId;

    public KmnmtEmpWorkplacePK() {
    }

    public KmnmtEmpWorkplacePK(String scd, String wplId) {
        this.scd = scd;
        this.wplId = wplId;
    }

    public String getScd() {
        return scd;
    }

    public void setScd(String scd) {
        this.scd = scd;
    }

    public String getWplId() {
        return wplId;
    }

    public void setWplId(String wplId) {
        this.wplId = wplId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scd != null ? scd.hashCode() : 0);
        hash += (wplId != null ? wplId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KmnmtEmpWorkplacePK)) {
            return false;
        }
        KmnmtEmpWorkplacePK other = (KmnmtEmpWorkplacePK) object;
        if ((this.scd == null && other.scd != null) || (this.scd != null && !this.scd.equals(other.scd))) {
            return false;
        }
        if ((this.wplId == null && other.wplId != null) || (this.wplId != null && !this.wplId.equals(other.wplId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.KmnmtEmpWorkplacePK[ scd=" + scd + ", wplId=" + wplId + " ]";
    }
    
}
