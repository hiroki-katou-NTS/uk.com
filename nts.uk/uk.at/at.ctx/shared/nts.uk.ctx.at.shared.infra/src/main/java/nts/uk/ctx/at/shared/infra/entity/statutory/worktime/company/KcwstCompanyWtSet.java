/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class JcwtstCompanyWtSet.
 */
@Entity
@Getter
@Setter
@Table(name = "KCWST_COMPANY_WT_SET")
public class KcwstCompanyWtSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jcwst company wt set PK. */
	@EmbeddedId
	protected KcwstCompanyWtSetPK jcwstCompanyWtSetPK;

	/** The str week. */
	@Column(name = "STR_WEEK")
	private int strWeek;

	/** The daily time. */
	@Column(name = "DAILY_TIME")
	private int dailyTime;

	/** The weekly time. */
	@Column(name = "WEEKLY_TIME")
	private int weeklyTime;

	/** The jan time. */
	@Column(name = "JAN_TIME")
	private int janTime;

	/** The feb time. */
	@Column(name = "FEB_TIME")
	private int febTime;

	/** The mar time. */
	@Column(name = "MAR_TIME")
	private int marTime;

	/** The apr time. */
	@Column(name = "APR_TIME")
	private int aprTime;

	/** The may time. */
	@Column(name = "MAY_TIME")
	private int mayTime;

	/** The jun time. */
	@Column(name = "JUN_TIME")
	private int junTime;

	/** The jul time. */
	@Column(name = "JUL_TIME")
	private int julTime;

	/** The aug time. */
	@Column(name = "AUG_TIME")
	private int augTime;

	/** The sep time. */
	@Column(name = "SEP_TIME")
	private int sepTime;

	/** The oct time. */
	@Column(name = "OCT_TIME")
	private int octTime;

	/** The nov time. */
	@Column(name = "NOV_TIME")
	private int novTime;

	/** The dec time. */
	@Column(name = "DEC_TIME")
	private int decTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.jcwstCompanyWtSetPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jcwstCompanyWtSetPK == null) ? 0 : jcwstCompanyWtSetPK.hashCode());
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
		KcwstCompanyWtSet other = (KcwstCompanyWtSet) obj;
		if (jcwstCompanyWtSetPK == null) {
			if (other.jcwstCompanyWtSetPK != null)
				return false;
		} else if (!jcwstCompanyWtSetPK.equals(other.jcwstCompanyWtSetPK))
			return false;
		return true;
	}

}
