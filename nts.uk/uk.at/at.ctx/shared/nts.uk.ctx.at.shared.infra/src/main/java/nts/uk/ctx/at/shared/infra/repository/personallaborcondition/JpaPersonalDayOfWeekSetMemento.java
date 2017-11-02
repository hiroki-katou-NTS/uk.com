/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalDayOfWeekSetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtSingleDaySche;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.PersonalWorkAtr;

/**
 * The Class JpaPersonalDayOfWeekSetMemento.
 */
public class JpaPersonalDayOfWeekSetMemento implements PersonalDayOfWeekSetMemento {
	
	/** The entitys. */
	private List<KshmtSingleDaySche> entitys;

	/**
	 * Instantiates a new jpa personal day of week set memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaPersonalDayOfWeekSetMemento(List<KshmtSingleDaySche> entitys) {
		if (CollectionUtil.isEmpty(entitys)) {
			this.entitys = new ArrayList<>();
		} else
			this.entitys = entitys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setSaturday(java.util.Optional)
	 */
	@Override
	public void setSaturday(Optional<SingleDaySchedule> saturday) {
		if(saturday.isPresent()){
			this.entitys.add(this.toEntity(saturday.get(), PersonalWorkAtr.SATURDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setSunday(java.util.Optional)
	 */
	@Override
	public void setSunday(Optional<SingleDaySchedule> sunday) {
		if (sunday.isPresent()) {
			this.entitys.add(this.toEntity(sunday.get(), PersonalWorkAtr.SUNDAY.value));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setMonday(java.util.Optional)
	 */
	@Override
	public void setMonday(Optional<SingleDaySchedule> monday) {
		if (monday.isPresent()) {
			this.entitys.add(this.toEntity(monday.get(), PersonalWorkAtr.MONDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setThursday(java.util.Optional)
	 */
	@Override
	public void setThursday(Optional<SingleDaySchedule> thursday) {
		if (thursday.isPresent()) {
			this.entitys.add(this.toEntity(thursday.get(), PersonalWorkAtr.THURSDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setWednesday(java.util.Optional)
	 */
	@Override
	public void setWednesday(Optional<SingleDaySchedule> wednesday) {
		if (wednesday.isPresent()) {
			this.entitys.add(this.toEntity(wednesday.get(), PersonalWorkAtr.WEDNESDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setTuesday(java.util.Optional)
	 */
	@Override
	public void setTuesday(Optional<SingleDaySchedule> tuesday) {
		if (tuesday.isPresent()) {
			this.entitys.add(this.toEntity(tuesday.get(), PersonalWorkAtr.TUESDAY.value));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * PersonalDayOfWeekSetMemento#setFriday(java.util.Optional)
	 */
	@Override
	public void setFriday(Optional<SingleDaySchedule> friday) {
		if (friday.isPresent()) {
			this.entitys.add(this.toEntity(friday.get(), PersonalWorkAtr.FRIDAY.value));
		}
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt single day sche
	 */
	private KshmtSingleDaySche toEntity(SingleDaySchedule domain, int personalWorkAtr) {
		KshmtSingleDaySche entity = new KshmtSingleDaySche();
		domain.saveToMemento(new JpaSingleDayScheduleSetMemento(entity));
		entity.getKshmtSingleDaySchePK().setPersWorkAtr(personalWorkAtr);
		return entity;
	}


}
