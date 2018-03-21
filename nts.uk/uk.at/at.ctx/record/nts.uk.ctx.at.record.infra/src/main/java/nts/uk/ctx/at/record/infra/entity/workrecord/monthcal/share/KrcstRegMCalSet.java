/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.share;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KrcstRegMCalSet.
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KrcstRegMCalSet extends UkJpaEntity {

	/** The include legal ot. */
	@Column(name = "INCLUDE_LEGAL_OT")
	private int includeLegalOt;

	/** The include holiday ot. */
	@Column(name = "INCLUDE_HOLIDAY_OT")
	private int includeHolidayOt;

	/** The include extra ot. */
	@Column(name = "INCLUDE_EXTRA_OT")
	private int includeExtraOt;

	/** The include legal aggr. */
	@Column(name = "INCLUDE_LEGAL_AGGR")
	private int includeLegalAggr;

	/** The include holiday aggr. */
	@Column(name = "INCLUDE_HOLIDAY_AGGR")
	private int includeHolidayAggr;

	/** The include extra aggr. */
	@Column(name = "INCLUDE_EXTRA_AGGR")
	private int includeExtraAggr;

}
