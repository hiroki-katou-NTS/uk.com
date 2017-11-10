/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.person.dom.person.info;

import javax.persistence.EnumType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.fullnameset.FullNameSet;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessEnglishName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessOtherName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameKana;

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
	
	//for required field
	public static Person createFromJavaType(String personId, GeneralDate birthDate, int bloodType, int gender, String personMobile, String mailAddress, String businessName, String personName) {
		return new Person(personId, birthDate, EnumAdaptor.valueOf(bloodType, BloodType.class),
				EnumAdaptor.valueOf(gender, GenderPerson.class), new PersonMobile(personMobile), 
				new PersonMailAddress(mailAddress), new BusinessName(businessName), new PersonName(personName));
	}
	public Person(String personId, PersonNameGroup personNameGroup) {
		super();
		this.personId = personId;
		this.personNameGroup = personNameGroup;
	}
	
	//constructor for required field
	public Person(String personId, GeneralDate birthDate, BloodType bloodType, GenderPerson gender, PersonMobile personMobile, PersonMailAddress mailAddress, BusinessName businessName, PersonName personName){
		super();
		this.personId = personId;
		this.birthDate = birthDate;
		this.bloodType = bloodType;
		this.gender = gender;
		this.personMobile = personMobile;
		this.mailAddress = mailAddress;
		this.personNameGroup = new PersonNameGroup(personName, businessName);
	}
	
	// sonnlb code start

		public static Person createFromJavaType(GeneralDate birthDate, int bloodType, int gender, String personId,
				String mailAddress, String personMobile, String businessName, String personName, String businessOtherName,
				String businessEnglishName, String personNameKana, String personRomanji, String personRomanjiKana,
				String todokedeFullName, String todokedeFullNameKana, String oldName, String oldNameKana,
				String todokedeOldFullName, String todokedeOldFullNameKana, String hobBy, String countryId, String taste) {

			FullNameSet personRomanjiSet = new FullNameSet(personRomanji, personRomanjiKana);
			FullNameSet todokedeFullNameSet = new FullNameSet(todokedeFullName, todokedeFullNameKana);
			FullNameSet oldNameSet = new FullNameSet(oldName, oldNameKana);
			FullNameSet todokedeOldFullNameSet = new FullNameSet(todokedeOldFullName, todokedeOldFullNameKana);

			PersonNameGroup personNameGroup = new PersonNameGroup(new BusinessName(businessName),
					new PersonName(personName), new BusinessOtherName(businessOtherName),
					new BusinessEnglishName(businessEnglishName), new PersonNameKana(personNameKana), personRomanjiSet,
					todokedeFullNameSet, oldNameSet, todokedeOldFullNameSet);

			return new Person(birthDate, EnumAdaptor.valueOf(bloodType, BloodType.class),
					EnumAdaptor.valueOf(gender, GenderPerson.class), personId, new PersonMailAddress(mailAddress),
					new PersonMobile(personMobile), personNameGroup, new Hobby(hobBy), new Nationality(countryId),
					new Taste(taste)

			);
		}

		// sonnlb code end

}
