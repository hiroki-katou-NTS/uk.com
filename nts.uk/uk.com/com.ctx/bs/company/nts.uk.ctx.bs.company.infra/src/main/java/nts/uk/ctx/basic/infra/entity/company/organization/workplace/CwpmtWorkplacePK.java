/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * The Class CwpmtWorkplacePK.
 */
@Getter
@Setter
@Embeddable
public class CwpmtWorkplacePK implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
	@Basic(optional = false)
    @NotNull
    @Column(name = "WKPID")
    private String wkpid;
	
	@NotNull
	@Column(name = "STR_D")
	@Temporal(TemporalType.TIMESTAMP)
	private GeneralDate strD;
	
	@NotNull
	@Column(name = "END_D")
	@Temporal(TemporalType.TIMESTAMP)
	private GeneralDate endD;

    public CwpmtWorkplacePK() {
    }

    public CwpmtWorkplacePK(String cid, String wkpid,GeneralDate strD,GeneralDate endD) {
        this.cid = cid;
        this.wkpid = wkpid;
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
		result = prime * result + ((wkpid == null) ? 0 : wkpid.hashCode());
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
		if (!(obj instanceof CwpmtWorkplacePK))
			return false;
		CwpmtWorkplacePK other = (CwpmtWorkplacePK) obj;
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
		if (wkpid == null) {
			if (other.wkpid != null)
				return false;
		} else if (!wkpid.equals(other.wkpid))
			return false;
		return true;
	}

    
}
