/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class JcwtstCompanyWtSet.
 */
@Entity
@Getter
@Setter
@Table(name = "JCWTST_COMPANY_WT_SET")
public class JcwtstCompanyWtSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jcwtst company wt set PK. */
	@EmbeddedId
	protected JcwtstCompanyWtSetPK jcwtstCompanyWtSetPK;

	/** The str week. */
	@Column(name = "STR_WEEK")
	private int strWeek;

	/** The daily time. */
	@Column(name = "DAILY_TIME")
	private Long dailyTime;

	/** The weekly time. */
	@Column(name = "WEEKLY_TIME")
	private Long weeklyTime;

	/** The jan time. */
	@Column(name = "JAN_TIME")
	private Long janTime;

	/** The feb time. */
	@Column(name = "FEB_TIME")
	private Long febTime;

	/** The mar time. */
	@Column(name = "MAR_TIME")
	private Long marTime;

	/** The apr time. */
	@Column(name = "APR_TIME")
	private Long aprTime;

	/** The may time. */
	@Column(name = "MAY_TIME")
	private Long mayTime;

	/** The jun time. */
	@Column(name = "JUN_TIME")
	private Long junTime;

	/** The jul time. */
	@Column(name = "JUL_TIME")
	private Long julTime;

	/** The aug time. */
	@Column(name = "AUG_TIME")
	private Long augTime;

	/** The sep time. */
	@Column(name = "SEP_TIME")
	private Long sepTime;

	/** The oct time. */
	@Column(name = "OCT_TIME")
	private Long octTime;

	/** The nov time. */
	@Column(name = "NOV_TIME")
	private Long novTime;

	/** The dec time. */
	@Column(name = "DEC_TIME")
	private Long decTime;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.jcwtstCompanyWtSetPK;
	}
}
