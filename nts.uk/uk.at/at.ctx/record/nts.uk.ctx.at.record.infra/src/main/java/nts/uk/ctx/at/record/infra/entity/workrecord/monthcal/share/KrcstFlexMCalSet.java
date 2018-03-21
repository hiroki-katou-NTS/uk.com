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
 * The Class KrcstFlexMCalSet.
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KrcstFlexMCalSet extends UkJpaEntity {

	/** The insuffic set. */
	@Column(name = "INSUFFIC_SET")
	private short insufficSet;

	/** The include ot. */
	@Column(name = "INCLUDE_OT")
	private short includeOt;

	/** The aggr method. */
	@Column(name = "AGGR_METHOD")
	private short aggrMethod;

	/** The legal aggr set. */
	@Column(name = "LEGAL_AGGR_SET")
	private short legalAggrSet;

}
