/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezoneGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixWorkTs;

/**
 * The Class JpaFixHalfDayWorkTimezoneGetMemento.
 */
public class JpaFixHalfDayWorkTimezoneGetMemento implements FixHalfDayWorkTimezoneGetMemento {

	/** The entity. */
	private KshmtWtFix entity;

	/** The type. */
	private AmPmAtr type;

	/**
	 * Instantiates a new jpa fix half day work timezone get memento.
	 *
	 * @param entity
	 *            the entity
	 * @param type
	 *            the type
	 */
	public JpaFixHalfDayWorkTimezoneGetMemento(KshmtWtFix entity, AmPmAtr type) {
		super();
		if (entity.getKshmtWtFixPK() == null) {
			entity.setKshmtWtFixPK(new KshmtWtFixPK());
		}
		this.entity = entity;
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneGetMemento#getRestTimezone()
	 */
	@Override
	public FixRestTimezoneSet getRestTimezone() {
		// KSHMT_WT_FIX_BR_WEK_TS
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFixBrWekTss())) {
			this.entity.setKshmtWtFixBrWekTss(new ArrayList<>());
		}
		List<KshmtWtFixBrWekTs> kshmtWtFixBrWekTss = this.entity.getKshmtWtFixBrWekTss().stream()
				.sorted((item1, item2) -> item1.getStartTime() - item2.getEndTime())
				.filter(entity -> entity.getKshmtWtFixBrWekTsPK().getAmPmAtr() == this.type.value)
				.collect(Collectors.toList());
		return new FixRestTimezoneSet(new JpaFixRestHalfdayTzGetMemento(kshmtWtFixBrWekTss));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneGetMemento#getWorkTimezone()
	 */
	@Override
	public FixedWorkTimezoneSet getWorkTimezone() {
		// KSHMT_WT_FIX_WORK_TS 就業時間の時間帯設定(固定)
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFixWorkTss())) {
			this.entity.setKshmtWtFixWorkTss(new ArrayList<>());
		}
		List<KshmtWtFixWorkTs> kshmtWtFixWorkTss = this.entity.getKshmtWtFixWorkTss().stream()
				.sorted((item1, item2) -> item1.getTimeStr() - item2.getTimeStr())
				.filter(entity -> entity.getKshmtWtFixWorkTsPK().getAmPmAtr() == this.type.value)
				.collect(Collectors.toList());
		// KSHMT_WT_FIX_OVER_TS 残業時間の時間帯設定
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFixOverTss())) {
			this.entity.setKshmtWtFixOverTss(new ArrayList<>());
		}
		List<KshmtWtFixOverTs> kshmtWtFixOverTss = this.entity.getKshmtWtFixOverTss().stream()
				.sorted((item1, item2) -> item1.getTimeStr() - item2.getTimeStr())
				.filter(entity -> entity.getKshmtWtFixOverTsPK().getAmPmAtr() == this.type.value)
				.collect(Collectors.toList());
		return new FixedWorkTimezoneSet(new JpaFixedWorkTimezoneSetGetMemento(kshmtWtFixWorkTss, kshmtWtFixOverTss));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezoneGetMemento#getDayAtr()
	 */
	@Override
	public AmPmAtr getDayAtr() {
		return this.type;
	}

}
