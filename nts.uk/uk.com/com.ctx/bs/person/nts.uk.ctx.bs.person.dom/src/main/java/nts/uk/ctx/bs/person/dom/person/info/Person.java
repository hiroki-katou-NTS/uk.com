/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.dom.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class Person.
 */
// 個人
@Getter
@AllArgsConstructor
public class Person extends AggregateRoot {

	/** The person id. */
	//個人ID
	private PersonId personId;
	
	/** The person name. */
	//個人旧名
	private PersonName personName;
	

	public static Person createFromJavaStyle(String personId, String personName) {
		return new Person(new PersonId(personId), new PersonName(personName));
	}
	
}
