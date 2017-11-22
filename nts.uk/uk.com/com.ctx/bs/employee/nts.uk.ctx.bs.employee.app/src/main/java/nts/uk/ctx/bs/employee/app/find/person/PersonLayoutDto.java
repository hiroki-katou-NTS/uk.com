package nts.uk.ctx.bs.employee.app.find.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonLayoutDto {
	
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
	
	@PeregItem("")
	private String businessNameKana;
	
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
	
	@PeregItem("IS00010")
	private String todokedeFullNameKana;
	
	@PeregItem("IS00010")
	private String PersonalNameMultilingual;
	
	@PeregItem("IS00010")
	private String PersonalNameMultilingualKana;
	
//	// 個人名グループ．個人届出名称．氏名カナ
//	@PeregItem("IS00011")
//	private String todokedeFullNameKana;
//	
//	// 個人名グループ．個人届出名称．氏名
//	@PeregItem("IS00012")
//	
//	data = person.getPersonNameGroup().getTodokedeFullName().getFullName().v();
//	break;
//	@PeregItem("IS00013")
//	// 個人名グループ．個人届出名称．氏名カナ
//	data = person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
//	break;
	
	// 性別
	@PeregItem("IS00014")
	private int gender;
	
	public static PersonLayoutDto createFromDomain(Person person){
		return new PersonLayoutDto(person.getPersonNameGroup().getPersonName().getFullName().v(), person.getPersonNameGroup().getPersonName().getFullNameKana().v(),
				person.getPersonNameGroup().getPersonRomanji().getFullName().v(), person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v(),
				person.getPersonNameGroup().getBusinessName().v(),person.getPersonNameGroup().getBusinessNameKana().v(),person.getPersonNameGroup().getBusinessEnglishName().v(),
				person.getPersonNameGroup().getBusinessOtherName().v(), person.getPersonNameGroup().getOldName().getFullName().v(),person.getPersonNameGroup().getOldName().getFullNameKana().v(),
				person.getPersonNameGroup().getTodokedeFullName().getFullName().v(), person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v(),
				person.getPersonNameGroup().getPersonalNameMultilingual().getFullName().v(), person.getPersonNameGroup().getPersonalNameMultilingual().getFullNameKana().v(),person.getGender().value);
	}
	private PersonPeregDto dto;
	private List<PersonInfoItemData> lstPersonInfoItemData;
}
