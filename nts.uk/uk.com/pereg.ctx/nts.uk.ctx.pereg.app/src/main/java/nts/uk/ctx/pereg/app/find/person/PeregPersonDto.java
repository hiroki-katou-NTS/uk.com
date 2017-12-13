package nts.uk.ctx.pereg.app.find.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@NoArgsConstructor
public class PeregPersonDto extends PeregDomainDto {

	// 個人名グループ．個人名
	@PeregItem("IS00003")
	private String personName;

	// 個人名グループ．個人名カナ
	@PeregItem("IS00004")
	private String personNameKana;

	// 個人名グループ．個人名ローマ字．氏名
	@PeregItem("IS00005")
	private String personRomanjiFullName;

	// 個人名グループ．個人名ローマ字．氏名カナ
	@PeregItem("IS00006")
	private String personRomanjiFullNameKana;
	
	//個人名他言語
	@PeregItem("IS00007")///-------------
	private String personOtherLanguageName;
	
	//個人名他言語カナ
	@PeregItem("IS00008")///-------------
	private String personOtherLanguageNameKana;

	// 個人名グループ．ビジネスネーム
	@PeregItem("IS00009")
	private String businessName;
	
	//表示氏名(ビジネスネーム)カナ
	@PeregItem("IS00010")
	private String businessNameKana;

	// 個人名グループ．ビジネスネーム．英語
	@PeregItem("IS00011")
	private String businessEnglishName;

	// 個人名グループ．ビジネスネーム．その他
	@PeregItem("IS00012")
	private String businessOtherName;
	
	//個人旧姓
	@PeregItem("IS00013") // -------------------
	private String personMaidenName;
	
	//個人旧姓カナ
	@PeregItem("IS00014") // -------------------
	private String personMaidenNameKana;

	//個人届出名
	@PeregItem("IS00015")//------------
	private String personNotificationName;
	
	//個人届出名カナ
	@PeregItem("IS00016")//------------
	private String personNotificationNameKana;
	
	//生年月日
	@PeregItem("IS00017")
	private GeneralDate birthDate;
	
	// 性別
	@PeregItem("IS00018")
	private int gender;
	
	// 血液型
	@PeregItem("IS00019")
	private int bloodType;


	private PeregPersonDto(String recordId, String personId, String personName, String personNameKana,
			String personRomanjiFullName, String personRomanjiFullNameKana, String personOtherLanguageName,
			String personOtherLanguageNameKana, String businessName, String businessNameKana, String businessEnglishName,
			String businessOtherName, String personMaidenName, String personMaidenNameKana, String personNotificationName,
			String personNotificationNameKana, GeneralDate birthDate, int gender, int bloodType) {
		super(recordId, null, personId);
		this.personName = personName;
		this.personNameKana = personNameKana;
		this.personRomanjiFullName = personRomanjiFullName;
		this.personRomanjiFullNameKana = personRomanjiFullNameKana;
		this.personOtherLanguageName = personOtherLanguageName;
		this.personOtherLanguageNameKana = personOtherLanguageNameKana;
		this.businessName = businessName;
		this.businessNameKana = businessNameKana;
		this.businessEnglishName = businessEnglishName;
		this.businessOtherName = businessOtherName;
		this.personMaidenName = personMaidenName;
		this.personMaidenNameKana = personMaidenNameKana;
		this.personNotificationName = personNotificationName;
		this.personNotificationNameKana = personNotificationNameKana;
		this.birthDate = birthDate;
		this.gender = gender;
		this.bloodType = bloodType;
	}

	public static PeregPersonDto createFromDomain(Person person) {
		boolean hasPersonName = person.getPersonNameGroup().getPersonName() != null;
		boolean hasPersonRomanji = person.getPersonNameGroup().getPersonRomanji() != null;
		boolean hasBusinessName = person.getPersonNameGroup().getBusinessName() != null;
		boolean hasBusinessEnglishName = person.getPersonNameGroup().getBusinessEnglishName() != null;
		boolean hasBusinessOtherName = person.getPersonNameGroup().getBusinessOtherName() != null;
		boolean hasOldName = person.getPersonNameGroup().getOldName() != null;
		boolean hasTodokedeFullName = person.getPersonNameGroup().getTodokedeFullName() != null;
		return null;
//		return new PeregPersonDto(person.getPersonId(), person.getPersonId(),
//				hasPersonName ? person.getPersonNameGroup().getPersonName().getFullName().v() : "",
//				hasPersonName ? person.getPersonNameGroup().getPersonName().getFullNameKana().v() : "",
//				hasPersonRomanji ? person.getPersonNameGroup().getPersonRomanji().getFullName().v() : "",
//				hasPersonRomanji ? person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v() : "",
//				hasBusinessName ? person.getPersonNameGroup().getBusinessName().v() : "",
//				hasBusinessEnglishName ? person.getPersonNameGroup().getBusinessEnglishName().v() : "",
//				hasBusinessOtherName ? person.getPersonNameGroup().getBusinessOtherName().v() : "",
//				hasOldName ? person.getPersonNameGroup().getOldName().getFullName().v() : "",
//				hasOldName ? person.getPersonNameGroup().getOldName().getFullNameKana().v() : "",
//				hasTodokedeFullName ? person.getPersonNameGroup().getTodokedeFullName().getFullName().v() : "",
//				person.getGender().value);
	}

}
