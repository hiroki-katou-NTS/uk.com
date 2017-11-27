package nts.uk.ctx.bs.employee.app.find.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@NoArgsConstructor
public class PeregPersonDto extends PeregDomainDto {

	// 個人名グループ．個人名
	@PeregItem("IS00001")
	private String personName;

	// 個人名グループ．個人名カナ
	@PeregItem("IS00002")
	private String personNameKana;

	// 個人名グループ．個人名ローマ字．氏名
	@PeregItem("IS00003")
	private String personRomanjiFullName;

	// 個人名グループ．個人名ローマ字．氏名カナ
	@PeregItem("IS00004")
	private String personRomanjiFullNameKana;

	// 個人名グループ．ビジネスネーム
	@PeregItem("IS00005")
	private String businessName;

	// 個人名グループ．ビジネスネーム．英語
	@PeregItem("IS00006")
	private String businessEnglishName;

	// 個人名グループ．ビジネスネーム．その他
	@PeregItem("IS00007")
	private String businessOtherName;

	// 個人名グループ．個人旧氏名．氏名
	@PeregItem("IS00008")
	private String oldFullName;

	// 個人名グループ．個人旧氏名．氏名カナ
	@PeregItem("IS00009")
	private String oldFullNameKana;

	// 個人名グループ．個人届出名称．氏名
	@PeregItem("IS00010")
	private String todokedeFullName;

	// // 個人名グループ．個人届出名称．氏名カナ
	// @PeregItem("IS00011")
	// private String todokedeFullNameKana;
	//
	// // 個人名グループ．個人届出名称．氏名
	// @PeregItem("IS00012")
	//
	// data =
	// person.getPersonNameGroup().getTodokedeFullName().getFullName().v();
	// break;
	// @PeregItem("IS00013")
	// // 個人名グループ．個人届出名称．氏名カナ
	// data =
	// person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
	// break;

	// 性別
	@PeregItem("IS00014")
	private int gender;

	private PeregPersonDto(String recordId, String personId, String personName, String personNameKana,
			String personRomanjiFullName, String personRomanjiFullNameKana, String businessName,
			String businessEnglishName, String businessOtherName, String oldFullName, String oldFullNameKana,
			String todokedeFullName, int gender) {
		super(recordId, null, personId);
		this.personName = personName;
		this.personNameKana = personNameKana;
		this.personRomanjiFullName = personRomanjiFullName;
		this.personRomanjiFullName = personRomanjiFullNameKana;
		this.businessName = businessName;
		this.businessEnglishName = businessEnglishName;
		this.businessOtherName = businessOtherName;
		this.oldFullName = oldFullName;
		this.oldFullNameKana = oldFullNameKana;
		this.todokedeFullName = todokedeFullName;
		this.gender = gender;
	}

	public static PeregPersonDto createFromDomain(Person person) {
		boolean hasPersonName = person.getPersonNameGroup().getPersonName() != null;
		boolean hasPersonRomanji = person.getPersonNameGroup().getPersonRomanji() != null;
		boolean hasBusinessName = person.getPersonNameGroup().getBusinessName() != null;
		boolean hasBusinessEnglishName = person.getPersonNameGroup().getBusinessEnglishName() != null;
		boolean hasBusinessOtherName = person.getPersonNameGroup().getBusinessOtherName() != null;
		boolean hasOldName = person.getPersonNameGroup().getOldName() != null;
		boolean hasTodokedeFullName = person.getPersonNameGroup().getTodokedeFullName() != null;

		return new PeregPersonDto(person.getPersonId(), person.getPersonId(),
				hasPersonName ? person.getPersonNameGroup().getPersonName().getFullName().v() : "",
				hasPersonName ? person.getPersonNameGroup().getPersonName().getFullNameKana().v() : "",
				hasPersonRomanji ? person.getPersonNameGroup().getPersonRomanji().getFullName().v() : "",
				hasPersonRomanji ? person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v() : "",
				hasBusinessName ? person.getPersonNameGroup().getBusinessName().v() : "",
				hasBusinessEnglishName ? person.getPersonNameGroup().getBusinessEnglishName().v() : "",
				hasBusinessOtherName ? person.getPersonNameGroup().getBusinessOtherName().v() : "",
				hasOldName ? person.getPersonNameGroup().getOldName().getFullName().v() : "",
				hasOldName ? person.getPersonNameGroup().getOldName().getFullNameKana().v() : "",
				hasTodokedeFullName ? person.getPersonNameGroup().getTodokedeFullName().getFullName().v() : "",
				person.getGender().value);
	}

}
