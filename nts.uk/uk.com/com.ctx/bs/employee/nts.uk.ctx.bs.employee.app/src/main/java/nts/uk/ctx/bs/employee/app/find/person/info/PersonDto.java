package nts.uk.ctx.bs.employee.app.find.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.BloodType;
import nts.uk.ctx.bs.person.dom.person.info.GenderPerson;
import nts.uk.ctx.bs.person.dom.person.info.Hobby;
import nts.uk.ctx.bs.person.dom.person.info.Nationality;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonMailAddress;
import nts.uk.ctx.bs.person.dom.person.info.PersonMobile;

@Getter
@AllArgsConstructor
public class PersonDto {
	/** The Birthday */
	// 生年月日
	private GeneralDate birthDate;

	/** The BloodType */
	// 血液型
	private int bloodType;

	/** The Gender - 性別 */
	private int gender;

	/** The person id - 個人ID */
	private String personId;

	/** The Person Mail Address - 個人メールアドレス */
	private String mailAddress;

	/** The Person Mobile - 個人携帯 */
	private String personMobile;

	/** The PersonNameGroup - 個人名グループ */
	private PersonNameGroupDto personNameGroup;

	/** The Hobby - 趣味 */
	private String hobBy;

	/** The countryId - 国籍 chua xac dinh duoc PrimitiveValue */
	private String countryId;

	static PersonDto fromDomain(Person person) {
		if (person == null) {
			return null;
		}

		PersonNameGroupDto nameGroup = PersonNameGroupDto.fromDomain(person.getPersonNameGroup());

		int blood = 0;
		BloodType bloodType = person.getBloodType();

		if (bloodType != null) {
			blood = bloodType.value;
		}

		int gender = 1;
		GenderPerson genderType = person.getGender();
		if (genderType != null) {
			gender = genderType.value;
		}

		String mail = "";
		PersonMailAddress mailAddress = person.getMailAddress();
		if (mailAddress != null) {
			mail = mailAddress.v();
		}

		String mobile = "";
		PersonMobile personMobile = person.getPersonMobile();
		if (personMobile != null) {
			mobile = personMobile.v();
		}

		String hobBy = "";
		Hobby hobby = person.getHobBy();
		if (hobby != null) {
			hobBy = hobby.v();
		}

		String countryId = "";
		Nationality country = person.getCountryId();
		if (country != null) {
			countryId = country.v();
		}

		return new PersonDto(person.getBirthDate(), blood, gender, person.getPersonId(), mail, mobile, nameGroup, hobBy,
				countryId);
	}
}
