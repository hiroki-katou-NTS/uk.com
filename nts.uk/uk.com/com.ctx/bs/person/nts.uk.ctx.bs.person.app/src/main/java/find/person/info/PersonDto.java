package find.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@AllArgsConstructor
public class PersonDto extends PeregDomainDto{
	/** The Birthday */
	// 生年月日
	@PeregItem("IS00017")
	private GeneralDate birthDate;

	/** The BloodType */
	// 血液型
	@PeregItem("IS00019")
	private int bloodType;

	/** The Gender - 性別 */
	@PeregItem("IS00018")
	private int gender;

	/** The PersonNameGroup - 個人名グループ*/
	@PeregItem("IS00003")
	private String personName;

	@PeregItem("IS00004")
	private String personNameKana;

	@PeregItem("IS00009")
	private String businessName;
	
	@PeregItem("IS00010")
	private String businessNameKana;

	@PeregItem("IS00011")
	private String businessEnglishName;

	@PeregItem("IS00012")
	private String businessOtherName;

	@PeregItem("IS00005")
	private String personRomanji;
	
	@PeregItem("IS00006")
	private String personRomanjiKana;

	@PeregItem("IS00013")
	private String oldName;
	
	@PeregItem("IS00014")
	private String oldNameKana;

	@PeregItem("IS00015")
	private String todokedeFullName;
	
	@PeregItem("IS00016")
	private String todokedeFullNameKana;

	@PeregItem("IS00007")
	private String PersonalNameMultilingual;
	
	@PeregItem("IS00008")
	private String PersonalNameMultilingualKana;
	
	public static PersonDto createFromDomain(Person person){
		return new PersonDto(person.getBirthDate(), person.getBloodType().value, person.getGender().value, person.getPersonNameGroup().getPersonName().getFullName().v(),
				person.getPersonNameGroup().getPersonName().getFullNameKana().v(), person.getPersonNameGroup().getBusinessName().v(), person.getPersonNameGroup().getBusinessNameKana().v(), 
				person.getPersonNameGroup().getBusinessEnglishName().v(), person.getPersonNameGroup().getBusinessOtherName().v(), person.getPersonNameGroup().getPersonRomanji().getFullName().v(), 
				person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v(), person.getPersonNameGroup().getOldName().getFullName().v(), person.getPersonNameGroup().getOldName().getFullNameKana().v(), 
				person.getPersonNameGroup().getTodokedeFullName().getFullName().v(), person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v(), 
				person.getPersonNameGroup().getPersonalNameMultilingual().getFullName().v(), person.getPersonNameGroup().getPersonalNameMultilingual().getFullNameKana().v());
	}
	
}
