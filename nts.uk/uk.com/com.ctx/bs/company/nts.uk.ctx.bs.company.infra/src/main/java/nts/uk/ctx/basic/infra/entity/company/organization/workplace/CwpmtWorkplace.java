/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CwpmtWorkplace.
 */
@Getter
@Setter
@Entity
@Table(name = "CWPMT_WORKPLACE")
public class CwpmtWorkplace extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -635296806934794023L;

	/** The cwpmt workplace PK. */
	@EmbeddedId
	protected CwpmtWorkplacePK cwpmtWorkplacePK;

	/** The wkpname. */
	@Column(name = "WKP_NAME")
	private String wkpname;

	/** The wkpcd. */
	@Column(name = "WKPCD")
	private String wkpcd;

	/**
	 * Instantiates a new cwpmt workplace.
	 */
	public CwpmtWorkplace() {
		super();
	}

	/**
	 * Instantiates a new cwpmt workplace.
	 *
	 * @param cwpmtWorkplacePK
	 *            the cwpmt workplace PK
	 */
	public CwpmtWorkplace(CwpmtWorkplacePK cwpmtWorkplacePK) {
		this.cwpmtWorkplacePK = cwpmtWorkplacePK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cwpmtWorkplacePK != null ? cwpmtWorkplacePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof CwpmtWorkplace)) {
			return false;
		}
		CwpmtWorkplace other = (CwpmtWorkplace) object;
		if ((this.cwpmtWorkplacePK == null && other.cwpmtWorkplacePK != null)
				|| (this.cwpmtWorkplacePK != null
						&& !this.cwpmtWorkplacePK.equals(other.cwpmtWorkplacePK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cwpmtWorkplacePK;
	}

}
