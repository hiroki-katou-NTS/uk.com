/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshstComNormalSet.
 */
@Setter
@Getter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KshstNormalSet extends UkJpaEntity {

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

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

}
