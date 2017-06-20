/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.person;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.basic.dom.person.PersonId;
import nts.uk.ctx.basic.dom.person.PersonName;
import nts.uk.ctx.basic.dom.person.PersonSetMemento;

/**
 * The Class PersonDto.
 */
@Getter
@Setter
public class PersonDto implements PersonSetMemento{

	/** The person id. */
	private String personId;
	
	/** The person name. */
	private String personName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.person.PersonSetMemento#setPersonId(nts.uk.ctx.basic
	 * .dom.person.PersonId)
	 */
	@Override
	public void setPersonId(PersonId personId) {
		this.personId = personId.v();
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
		this.personName = personName.v();
	}

}
