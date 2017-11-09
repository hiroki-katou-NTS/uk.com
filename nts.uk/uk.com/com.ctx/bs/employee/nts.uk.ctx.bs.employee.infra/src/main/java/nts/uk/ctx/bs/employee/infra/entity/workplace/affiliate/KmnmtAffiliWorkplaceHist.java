/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmnmtAffiliWorkplaceHist.
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "KMNMT_AFFI_WORKPLACE_HIST")
public class KmnmtAffiliWorkplaceHist extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kmnmt affili workplace hist PK. */
	@EmbeddedId
	protected KmnmtAffiliWorkplaceHistPK kmnmtAffiliWorkplaceHistPK;

	/** The end D. */
	@Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endD;

	/**
	 * Instantiates a new kmnmt affili workplace hist.
	 */
	public KmnmtAffiliWorkplaceHist() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kmnmtAffiliWorkplaceHistPK == null) ? 0
				: kmnmtAffiliWorkplaceHistPK.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KmnmtAffiliWorkplaceHist other = (KmnmtAffiliWorkplaceHist) obj;
		if (kmnmtAffiliWorkplaceHistPK == null) {
			if (other.kmnmtAffiliWorkplaceHistPK != null)
				return false;
		} else if (!kmnmtAffiliWorkplaceHistPK.equals(other.kmnmtAffiliWorkplaceHistPK))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.getKmnmtAffiliWorkplaceHistPK();
	}
}
