/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.employment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JewtstEmploymentWtSet.
 */
@Entity
@Getter
@Setter
@Table(name = "KEWST_EMPLOYMENT_WT_SET")
public class KewstEmploymentWtSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jewtst employment wt set PK. */
	@EmbeddedId
	protected KewstEmploymentWtSetPK jewstEmploymentWtSetPK;

	/** The str week. */
	@Column(name = "STR_WEEK")
	private int strWeek;

	/** The daily time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "DAILY_TIME")
	private int dailyTime;

	/** The weekly time. */
	@Column(name = "WEEKLY_TIME")
	private int weeklyTime;

	/** The jan time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "JAN_TIME")
	private int janTime;

	/** The feb time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "FEB_TIME")
	private int febTime;

	/** The mar time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "MAR_TIME")
	private int marTime;

	/** The apr time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "APR_TIME")
	private int aprTime;

	/** The may time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "MAY_TIME")
	private int mayTime;

	/** The jun time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "JUN_TIME")
	private int junTime;

	/** The jul time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "JUL_TIME")
	private int julTime;

	/** The aug time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "AUG_TIME")
	private int augTime;

	/** The sep time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "SEP_TIME")
	private int sepTime;

	/** The oct time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "OCT_TIME")
	private int octTime;

	/** The nov time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "NOV_TIME")
	private int novTime;

	/** The dec time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "DEC_TIME")
	private int decTime;

	/**
	 * Instantiates a new jewtst employment wt set.
	 */
	public KewstEmploymentWtSet() {

	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.jewstEmploymentWtSetPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jewstEmploymentWtSetPK == null) ? 0 : jewstEmploymentWtSetPK.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		KewstEmploymentWtSet other = (KewstEmploymentWtSet) obj;
		if (jewstEmploymentWtSetPK == null) {
			if (other.jewstEmploymentWtSetPK != null)
				return false;
		} else if (!jewstEmploymentWtSetPK.equals(other.jewstEmploymentWtSetPK))
			return false;
		return true;
	}

}
