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
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSetPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPrescribedTimezoneSettingSetMemento.
 */
public class JpaPrescribedTimezoneSettingSetMemento implements PrescribedTimezoneSettingSetMemento {

	/** The entity. */
	private KshmtPredTimeSet entity;

	/**
	 * Instantiates a new jpa prescribed timezone setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaPrescribedTimezoneSettingSetMemento(KshmtPredTimeSet entity) {
		super();
		this.entity = entity;
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
	public void setLstTimezone(List<TimezoneUse> lstTimezone) {
		
		if(CollectionUtil.isEmpty(lstTimezone)){
			this.entity.setKshmtWorkTimeSheetSets(new ArrayList<>());
		}
		else {
			this.entity.setKshmtWorkTimeSheetSets(lstTimezone.stream().map(domain -> {
				KshmtWorkTimeSheetSet entity = new KshmtWorkTimeSheetSet(
						new KshmtWorkTimeSheetSetPK(this.entity.getKshmtPredTimeSetPK().getCid(),
								this.entity.getKshmtPredTimeSetPK().getWorktimeCd()));
				domain.saveToMemento(new JpaTimezoneSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}


		
	}

}
