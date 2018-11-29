package nts.uk.ctx.pereg.app.find.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.BloodType;
import nts.uk.ctx.bs.person.dom.person.info.GenderPerson;
import nts.uk.ctx.bs.person.dom.person.info.Person;

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

	/** The PersonNameGroup - 個人名グループ */
	private PersonNameGroupDto personNameGroup;


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

		return new PersonDto(person.getBirthDate(), blood, gender, person.getPersonId(),  nameGroup);
	}
}
