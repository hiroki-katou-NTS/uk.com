/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimezoneRoundingSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTypeRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingGoOutTimeSheet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComGooutRound;

/**
 * The Class JpaGoOutTimezoneRoundingSetGetMemento.
 */
public class JpaGoOutTimezoneRoundingSetGetMemento implements GoOutTimezoneRoundingSetGetMemento {

	/** The entity map. */
	private Map<RoundingTimeType, GoOutTypeRoundingSet> entityMap;

	/**
	 * Instantiates a new jpa go out timezone rounding set get memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaGoOutTimezoneRoundingSetGetMemento(List<KshmtWtComGooutRound> entities) {
		super();
		this.entityMap = entities.stream().collect(Collectors.toMap(
				entity -> RoundingTimeType
						.valueOf(entity.getKshmtWtComGooutRoundPK().getRoundingTimeType()),
				entity -> new GoOutTypeRoundingSet(new DeductGoOutRoundingSet(
						new GoOutTimeRoundingSetting(
								RoundingGoOutTimeSheet.valueOf(entity.getPubDeductMethod()),
								new TimeRoundingSetting(entity.getPubDeductUnit(),
										entity.getPubDeductRounding())),
						new GoOutTimeRoundingSetting(
								RoundingGoOutTimeSheet.valueOf(entity.getPubRoundingMethod()),
								new TimeRoundingSetting(entity.getPubRoundingUnit(),
										entity.getPubRounding()))),
						new DeductGoOutRoundingSet(new GoOutTimeRoundingSetting(
								RoundingGoOutTimeSheet.valueOf(entity.getPersonalDeductMethod()),
								new TimeRoundingSetting(entity.getPersonalDeductUnit(),
										entity.getPersonalDeductRounding())),
								new GoOutTimeRoundingSetting(
										RoundingGoOutTimeSheet
												.valueOf(entity.getPersonalRoundingMethod()),
										new TimeRoundingSetting(entity.getPersonalRoundingUnit(),
												entity.getPersonalRounding()))))));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetGetMemento#getPubHolWorkTimezone()
	 */
	@Override
	public GoOutTypeRoundingSet getPubHolWorkTimezone() {
		return this.entityMap.get(RoundingTimeType.PUB_HOL_WORK_TIMEZONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetGetMemento#getWorkTimezone()
	 */
	@Override
	public GoOutTypeRoundingSet getWorkTimezone() {
		return this.entityMap.get(RoundingTimeType.WORK_TIMEZONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimezoneRoundingSetGetMemento#getOttimezone()
	 */
	@Override
	public GoOutTypeRoundingSet getOttimezone() {
		return this.entityMap.get(RoundingTimeType.OT_TIMEZONE);
	}

}
