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
 * The Class KrcstDeforMCalSet.
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KrcstDeforMCalSet extends UkJpaEntity {

	/** The include legal ot. */
	@Column(name = "INCLUDE_LEGAL_OT")
	private short includeLegalOt;

	/** The include holiday ot. */
	@Column(name = "INCLUDE_HOLIDAY_OT")
	private short includeHolidayOt;

	/** The include extra ot. */
	@Column(name = "INCLUDE_EXTRA_OT")
	private short includeExtraOt;

	/** The include legal aggr. */
	@Column(name = "INCLUDE_LEGAL_AGGR")
	private short includeLegalAggr;

	/** The include holiday aggr. */
	@Column(name = "INCLUDE_HOLIDAY_AGGR")
	private short includeHolidayAggr;

	/** The include extra aggr. */
	@Column(name = "INCLUDE_EXTRA_AGGR")
	private short includeExtraAggr;

	/** The is ot irg. */
	@Column(name = "IS_OT_IRG")
	private short isOtIrg;

	/** The period. */
	@Column(name = "PERIOD")
	private short period;

	/** The repeat atr. */
	@Column(name = "REPEAT_ATR")
	private short repeatAtr;

	/** The str month. */
	@Column(name = "STR_MONTH")
	private short strMonth;

}
