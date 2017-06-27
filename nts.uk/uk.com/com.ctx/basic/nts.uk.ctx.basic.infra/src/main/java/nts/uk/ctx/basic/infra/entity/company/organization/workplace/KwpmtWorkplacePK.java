/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Embeddable
public class KwpmtWorkplacePK implements Serializable {
    
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
    @Size(min = 1, max = 5)
    @Column(name = "WPL_ID")
    private String wplid;
	
	@Column(name = "STR_D")
	@Temporal(TemporalType.TIMESTAMP)
	private GeneralDate strD;
	
	@Column(name = "END_D")
	@Temporal(TemporalType.TIMESTAMP)
	private GeneralDate endD;

    public KwpmtWorkplacePK() {
    }

    public KwpmtWorkplacePK(String cid, String wplid,GeneralDate strD,GeneralDate endD) {
        this.cid = cid;
        this.wplid = wplid;
        this.strD = strD;
        this.endD = endD;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((endD == null) ? 0 : endD.hashCode());
		result = prime * result + ((strD == null) ? 0 : strD.hashCode());
		result = prime * result + ((wplid == null) ? 0 : wplid.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KwpmtWorkplacePK))
			return false;
		KwpmtWorkplacePK other = (KwpmtWorkplacePK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (endD == null) {
			if (other.endD != null)
				return false;
		} else if (!endD.equals(other.endD))
			return false;
		if (strD == null) {
			if (other.strD != null)
				return false;
		} else if (!strD.equals(other.strD))
			return false;
		if (wplid == null) {
			if (other.wplid != null)
				return false;
		} else if (!wplid.equals(other.wplid))
			return false;
		return true;
	}

    
}
