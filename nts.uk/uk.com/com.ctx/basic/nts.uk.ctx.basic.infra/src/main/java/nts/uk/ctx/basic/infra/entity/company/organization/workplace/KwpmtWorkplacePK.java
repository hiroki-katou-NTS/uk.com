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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

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
	

	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 120)
	@Column(name = "WPL_CD")
	private String wplcd;

    public KwpmtWorkplacePK() {
    }

    public KwpmtWorkplacePK(String cid, String wplid,String wplcd) {
        this.cid = cid;
        this.wplid = wplid;
        this.wplcd = wplcd;
    }

   

    @Override
    public String toString() {
        return "entity.KwpmtWorkplacePK[ cid=" + cid + ", wkpid=" + wplid + ", wplcd=" +wplcd+ " ]";
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((wplcd == null) ? 0 : wplcd.hashCode());
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
		if (wplcd == null) {
			if (other.wplcd != null)
				return false;
		} else if (!wplcd.equals(other.wplcd))
			return false;
		if (wplid == null) {
			if (other.wplid != null)
				return false;
		} else if (!wplid.equals(other.wplid))
			return false;
		return true;
	}
    
}
