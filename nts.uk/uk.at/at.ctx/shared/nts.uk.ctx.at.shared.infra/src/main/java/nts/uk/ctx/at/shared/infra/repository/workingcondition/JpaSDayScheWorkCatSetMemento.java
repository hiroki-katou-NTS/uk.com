/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCatPK;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkCatTimeZone;

/**
 * The Class JpaSingleDayScheduleSetMemento.
 */
public class JpaSDayScheWorkCatSetMemento implements SingleDayScheduleSetMemento {

	/** The entity. */
	private KshmtPerWorkCat entity;

	/**
	 * Instantiates a new jpa single day schedule set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSDayScheWorkCatSetMemento(String historyId, int workCategoryAtr,
			KshmtPerWorkCat entity) {
		if (entity.getKshmtPerWorkCatPK() == null) {
			KshmtPerWorkCatPK kshmtPerWorkCatPK = new KshmtPerWorkCatPK();
			kshmtPerWorkCatPK.setHistoryId(historyId);
			kshmtPerWorkCatPK.setPerWorkCatAtr(workCategoryAtr);
			entity.setKshmtPerWorkCatPK(kshmtPerWorkCatPK);
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkTypeCode(nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		if (workTypeCode != null && !StringUtil.isNullOrEmpty(workTypeCode.v(), true)) {
			this.entity.setWorkTypeCode(workTypeCode.v());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkingHours(java.util.List)
	 */
	@Override
	public void setWorkingHours(List<TimeZone> workingHours) {
		this.entity.setKshmtWorkCatTimeZones(workingHours.stream().map(item -> {
			KshmtWorkCatTimeZone kshmtWorkCatTimeZone = new KshmtWorkCatTimeZone();
			item.saveToMemento(new JpaTimezoneSetMemento<KshmtWorkCatTimeZone>(
					this.entity.getKshmtPerWorkCatPK().getHistoryId(),
					this.entity.getKshmtPerWorkCatPK().getPerWorkCatAtr(), kshmtWorkCatTimeZone));
			return kshmtWorkCatTimeZone;
		}).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkTimeCode(java.util.Optional)
	 */
	@Override
	public void setWorkTimeCode(Optional<WorkTimeCode> workTimeCode) {
		if (workTimeCode.isPresent() && !StringUtil.isNullOrEmpty(workTimeCode.get().v(), true)) {
			this.entity.setWorkTimeCode(workTimeCode.get().v());
		}
	}

}
