/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.Timezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPrescribedTimezoneSettingSetMemento.
 */
public class JpaPrescribedTimezoneSettingSetMemento implements PrescribedTimezoneSettingSetMemento {
	

	/** The entity. */
	private KshmtPredTimeSet entity;

	/** The lst entity time. */
	private List<KshmtWorkTimeSheetSet> lstEntityTime;
	
	/**
	 * Instantiates a new jpa prescribed timezone setting get memento.
	 *
	 * @param entity the entity
	 * @param lstEntityTime the lst entity time
	 */
	public JpaPrescribedTimezoneSettingSetMemento(KshmtPredTimeSet entity, List<KshmtWorkTimeSheetSet> lstEntityTime) {
		super();
		if(entity.getKshmtPredTimeSetPK() == null){
			entity.setKshmtPredTimeSetPK(new KshmtPredTimeSetPK());
		}
		this.entity = entity;
		this.lstEntityTime = lstEntityTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingSetMemento#setMorningEndTime(nts.uk.shr.com.time
	 * .TimeWithDayAttr)
	 */
	@Override
	public void setMorningEndTime(TimeWithDayAttr morningEndTime) {
		this.entity.setMorningEndTime(morningEndTime.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingSetMemento#setAfternoonStartTime(nts.uk.shr.com.
	 * time.TimeWithDayAttr)
	 */
	@Override
	public void setAfternoonStartTime(TimeWithDayAttr afternoonStartTime) {
		this.entity.setAfternoonStartTime(afternoonStartTime.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSettingSetMemento#setLstTimezone(java.util.List)
	 */
	@Override
	public void setLstTimezone(List<Timezone> lstTimezone) {
		if (CollectionUtil.isEmpty(lstTimezone)) {
			this.lstEntityTime = new ArrayList<>();
		} else {
			this.lstEntityTime = lstTimezone.stream().map(domain -> {
				KshmtWorkTimeSheetSet entity = new KshmtWorkTimeSheetSet();
				domain.saveToMemento(new JpaTimezoneSetMemento(entity));
				return entity;
			}).collect(Collectors.toList());
		}
	}


}
