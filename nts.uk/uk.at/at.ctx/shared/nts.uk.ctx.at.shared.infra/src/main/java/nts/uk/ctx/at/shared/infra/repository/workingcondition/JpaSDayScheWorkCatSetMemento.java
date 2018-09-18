/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
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
	
	/** The map kshmt work cat time zone. */
	private Map<Integer, KshmtWorkCatTimeZone> mapKshmtWorkCatTimeZone;

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
		
		this.mapKshmtWorkCatTimeZone = new HashMap<>();
		if (!CollectionUtil.isEmpty(entity.getKshmtWorkCatTimeZones())) {
			this.mapKshmtWorkCatTimeZone = entity.getKshmtWorkCatTimeZones().stream()
					.collect(Collectors.toMap(item -> item.getKshmtWorkCatTimeZonePK().getCnt(), Function.identity()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento#
	 * setWorkTypeCode(nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(Optional<WorkTypeCode> workTypeCode) {
		if (workTypeCode != null && workTypeCode.isPresent()){
			this.entity.setWorkTypeCode(workTypeCode.get().v());
		}  else {
			this.entity.setWorkTypeCode(null);
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
			KshmtWorkCatTimeZone kshmtWorkCatTimeZone = this.mapKshmtWorkCatTimeZone.getOrDefault(Integer.valueOf(item.getCnt()), new KshmtWorkCatTimeZone());
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
		if (workTimeCode != null && workTimeCode.isPresent()){
			this.entity.setWorkTimeCode(workTimeCode.get().v());
		}  else {
			this.entity.setWorkTimeCode(null);
		}
	}

}
