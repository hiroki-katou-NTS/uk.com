/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CwpmtWorkplace.
 */
@Entity
@Table(name = "CWPMT_WORKPLACE")
@XmlRootElement

@Getter
@Setter
public class CwpmtWorkplace extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected CwpmtWorkplacePK cwpmtWorkplacePK;
	
	@NotNull
	@Column(name = "WKP_NAME")
	private String wkpname;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "WKPCD")
	private String wkpcd;
	
	public CwpmtWorkplace() {
	}

	public CwpmtWorkplace(CwpmtWorkplacePK cwpmtWorkplacePK) {
		this.cwpmtWorkplacePK = cwpmtWorkplacePK;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cwpmtWorkplacePK != null ? cwpmtWorkplacePK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof CwpmtWorkplace)) {
			return false;
		}
		CwpmtWorkplace other = (CwpmtWorkplace) object;
		if ((this.cwpmtWorkplacePK == null && other.cwpmtWorkplacePK != null)
				|| (this.cwpmtWorkplacePK != null && !this.cwpmtWorkplacePK.equals(other.cwpmtWorkplacePK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.cwpmtWorkplacePK;
	}

}
