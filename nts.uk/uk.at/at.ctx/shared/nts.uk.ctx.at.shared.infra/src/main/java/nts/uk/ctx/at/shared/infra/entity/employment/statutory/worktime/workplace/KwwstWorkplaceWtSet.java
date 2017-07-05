/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class JwpwtstWorkplaceWtSet.
 */
@Entity
@Getter
@Setter
@Table(name = "KWWST_WORKPLACE_WT_SET")
public class KwwstWorkplaceWtSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The jwwst workplace wt set PK. */
	@EmbeddedId
	protected KwwstWorkplaceWtSetPK jwwstWorkplaceWtSetPK;

	/** The str week. */
	@Column(name = "STR_WEEK")
	private int strWeek;

	/** The daily time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "DAILY_TIME")
	private Long dailyTime;

	/** The weekly time. */
	@Column(name = "WEEKLY_TIME")
	private Long weeklyTime;

	/** The jan time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "JAN_TIME")
	private Long janTime;

	/** The feb time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "FEB_TIME")
	private Long febTime;

	/** The mar time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "MAR_TIME")
	private Long marTime;

	/** The apr time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "APR_TIME")
	private Long aprTime;

	/** The may time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "MAY_TIME")
	private Long mayTime;

	/** The jun time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "JUN_TIME")
	private Long junTime;

	/** The jul time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "JUL_TIME")
	private Long julTime;

	/** The aug time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "AUG_TIME")
	private Long augTime;

	/** The sep time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "SEP_TIME")
	private Long sepTime;

	/** The oct time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "OCT_TIME")
	private Long octTime;

	/** The nov time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "NOV_TIME")
	private Long novTime;

	/** The dec time. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "DEC_TIME")
	private Long decTime;

	/**
	 * Instantiates a new jwpwtst workplace wt set.
	 */
	public KwwstWorkplaceWtSet() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.jwwstWorkplaceWtSetPK;
	}
}
