/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkSetPK;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixHalfDayWorkTimezoneGetMemento implements FixHalfDayWorkTimezoneGetMemento {
	/** The entity. */
	private KshmtFlexWorkSet entity;

	/**
	 * Instantiates a new jpa core time setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFixHalfDayWorkTimezoneGetMemento(KshmtFlexWorkSet entity) {
		super();
		if (entity.getKshmtFlexWorkSetPK() == null) {
			entity.setKshmtFlexWorkSetPK(new KshmtFlexWorkSetPK());
		}
		this.entity = entity;
	}

	@Override
	public FixRestTimezoneSet getRestTimezone() {
		// KSHMT_FIXED_HALF_REST_SET 固定休憩時間の時間帯設定(平日)
		return null;
	}

	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		// KSHMT_FIXED_WORK_TIME_SET 就業時間の時間帯設定(固定)
		// KSHMT_FIXED_OT_TIME_SET 残業時間の時間帯設定

		// TODO Auto-generated method stub
		return new FixedWorkTimezoneSet(new JpaFixedWorkTimezoneSetGetMemento(entity));
	}

	@Override
	public AmPmAtr getDayAtr() {
		// TODO Auto-generated method stub
		return null;
	}

}
