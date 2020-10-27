/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.ArrayList;
import java.util.List;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTsPK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPrescribedTimezoneSettingSetMemento.
 */
public class JpaPrescribedTimezoneSettingSetMemento implements PrescribedTimezoneSettingSetMemento {

	/** The entity. */
	private KshmtWtComPredTime entity;

	/**
	 * Instantiates a new jpa prescribed timezone setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaPrescribedTimezoneSettingSetMemento(KshmtWtComPredTime entity) {
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
			this.entity.setKshmtWtComPredTss(new ArrayList<>());
			return;
		}
		String companyId = this.entity.getKshmtWtComPredTimePK().getCid();
		String workTimeCd = this.entity.getKshmtWtComPredTimePK().getWorktimeCd();

		List<KshmtWtComPredTs> lstEnttiy = this.entity.getKshmtWtComPredTss();
		if(CollectionUtil.isEmpty(lstEnttiy)){
			lstEnttiy = new ArrayList<>();
		}
		
		List<KshmtWtComPredTs> newListEntity = new ArrayList<>();
		
		for(TimezoneUse domain : lstTimezone) {
			// newPK
			KshmtWtComPredTsPK pk = new KshmtWtComPredTsPK(companyId, workTimeCd, domain.getWorkNo());
			
			// find entity if existed, else new entity
			KshmtWtComPredTs entity = lstEnttiy.stream()
					.filter(item -> item.getKshmtWtComPredTsPK().equals(pk))
					.findFirst()
					.orElse(new KshmtWtComPredTs(pk));
			
			// save to memento
			domain.saveToMemento(new JpaTimezoneSetMemento(entity));
			
			// add entity
			newListEntity.add(entity);
		}
		
		// set list KshmtWtComPredTss
		this.entity.setKshmtWtComPredTss(newListEntity);
	}

}
