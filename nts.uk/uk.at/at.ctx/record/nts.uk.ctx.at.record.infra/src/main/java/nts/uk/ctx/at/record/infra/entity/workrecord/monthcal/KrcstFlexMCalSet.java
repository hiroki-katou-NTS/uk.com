/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KrcstFlexMCalSet.
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KrcstFlexMCalSet extends UkJpaEntity {

	/** The insuffic set. */
	@Column(name = "INSUFFIC_SET")
	private int insufficSet;

	/** The include over time. */
	@Column(name = "INCLUDE_OT")
	private int includeOt;

	/** The include holiday work. */
	@Column(name = "INCLUDE_HDWK")
	private int includeHdwk;

	/** The aggr method. */
	@Column(name = "AGGR_METHOD")
	private int aggrMethod;

	/** The legal aggr set. */
	@Column(name = "LEGAL_AGGR_SET")
	private int legalAggrSet;

	/** The settlement period. */
	@Column(name = "SETTLE_PERIOD")
	private int settlePeriod;
	
	/** The start month. */
	@Column(name = "START_MONTH")
	private int startMonth;
	
	/** The settlement period months. */
	@Column(name = "SETTLE_PERIOD_MON")
	private int settlePeriodMon;
}
