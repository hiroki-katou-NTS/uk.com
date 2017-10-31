/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.dom.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.person.PersonGetMemento;
import nts.uk.ctx.basic.dom.person.PersonSetMemento;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;

/**
 * The Class Person.
 */
// 個人
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person extends AggregateRoot {

	/** The Birthday */
	// 生年月日
	private GeneralDate birthDate;

	/** The BloodType */
	// 血液型
	private BloodType bloodType;

	/** The Gender - 性別 */
	private GenderPerson gender;

	/** The person id - 個人ID */
	private String personId;

	/** The Person Mail Address - 個人メールアドレス */
	private PersonMailAddress mailAddress;

	/** The Person Mobile - 個人携帯*/
	private PersonMobile personMobile;

	/** The PersonNameGroup - 個人名グループ*/
	private PersonNameGroup personNameGroup;

	/** The Hobby - 趣味 */
	private Hobby hobBy;

	/** The countryId - 国籍    chua xac dinh duoc PrimitiveValue*/
	private Nationality countryId;

	/** The Taste - 嗜好 */
	private Taste taste;
	

	public static Person createFromJavaType(String pId, String personName) {
		return new Person(pId, new PersonNameGroup(new PersonName(personName)));
	}

	public Person(String personId, PersonNameGroup personNameGroup) {
		super();
		this.personId = personId;
		this.personNameGroup = personNameGroup;
	}

}
