/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.person;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class Person.
 */
// 個人
@Getter
public class Person extends AggregateRoot {

	/** The person id. */
	//個人ID
	private PersonId personId;
	
	/** The person name. */
	//個人旧名
	private PersonName personName;
	
	
	/**
	 * Instantiates a new person.
	 *
	 * @param memento the memento
	 */
	public Person(PersonGetMemento memento){
		this.personId = memento.getPersonId();
		this.personName = memento.getPersonName();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PersonSetMemento memento){
		memento.setPersonId(this.personId);
		memento.setPersonName(this.personName);
	}
}
