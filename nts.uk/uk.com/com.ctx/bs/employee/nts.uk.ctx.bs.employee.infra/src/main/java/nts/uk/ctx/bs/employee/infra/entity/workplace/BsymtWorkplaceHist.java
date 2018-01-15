/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtWorkplaceHist.
 */
@Getter
@Setter
@Entity
@Table(name = "BSYMT_WORKPLACE_HIST")
public class BsymtWorkplaceHist extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bsymt workplace hist PK. */
	@EmbeddedId
	protected BsymtWorkplaceHistPK bsymtWorkplaceHistPK;

	/** The str D. */
	@Column(name = "STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate strD;

	/** The end D. */
	@Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate endD;

	/** The bsymt wkp config info. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bsymtWorkplaceHist", fetch = FetchType.LAZY)
	private List<BsymtWorkplaceInfo> lstBsymtWorkplaceInfo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (bsymtWorkplaceHistPK != null ? bsymtWorkplaceHistPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BsymtWorkplaceHist)) {
			return false;
		}
		BsymtWorkplaceHist other = (BsymtWorkplaceHist) object;
		if ((this.bsymtWorkplaceHistPK == null && other.bsymtWorkplaceHistPK != null)
				|| (this.bsymtWorkplaceHistPK != null
						&& !this.bsymtWorkplaceHistPK.equals(other.bsymtWorkplaceHistPK))) {
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
		return this.bsymtWorkplaceHistPK;
	}

}
