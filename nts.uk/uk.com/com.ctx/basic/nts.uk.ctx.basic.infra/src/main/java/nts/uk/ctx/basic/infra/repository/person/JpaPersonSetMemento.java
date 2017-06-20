/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.person;

import nts.uk.ctx.basic.dom.person.PersonId;
import nts.uk.ctx.basic.dom.person.PersonName;
import nts.uk.ctx.basic.dom.person.PersonSetMemento;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson;

/**
 * The Class JpaPersonSetMemento.
 */
public class JpaPersonSetMemento implements PersonSetMemento {
	
	/** The person. */
	private CcgmtPerson person;
	
	
	/**
	 * Instantiates a new jpa person set memento.
	 *
	 * @param person the person
	 */
	public JpaPersonSetMemento(CcgmtPerson person) {
		this.person = person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.person.PersonSetMemento#setPersonId(nts.uk.ctx.basic
	 * .dom.person.PersonId)
	 */
	@Override
	public void setPersonId(PersonId personId) {
		this.person.setPid(personId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.person.PersonSetMemento#setPersonName(nts.uk.ctx.
	 * basic.dom.person.PersonName)
	 */
	@Override
	public void setPersonName(PersonName personName) {
		this.person.setPName(personName.v());
	}

}
