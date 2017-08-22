/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.person;

import nts.uk.ctx.basic.dom.person.PersonGetMemento;
import nts.uk.ctx.basic.dom.person.PersonId;
import nts.uk.ctx.basic.dom.person.PersonName;
import nts.uk.ctx.basic.infra.entity.person.CcgmtPerson;

/**
 * The Class JpaPersonGetMemento.
 */
public class JpaPersonGetMemento implements PersonGetMemento {

	/** The person. */
	private CcgmtPerson person;

	/**
	 * Instantiates a new jpa person get memento.
	 *
	 * @param person
	 *            the person
	 */
	public JpaPersonGetMemento(CcgmtPerson person) {
		this.person = person;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.person.PersonGetMemento#getPersonId()
	 */
	@Override
	public PersonId getPersonId() {
		return new PersonId(this.person.getPid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.person.PersonGetMemento#getPersonName()
	 */
	@Override
	public PersonName getPersonName() {
		return new PersonName(this.person.getPName());
	}

}
