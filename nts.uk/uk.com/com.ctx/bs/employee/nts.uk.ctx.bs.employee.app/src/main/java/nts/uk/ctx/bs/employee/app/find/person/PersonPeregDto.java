package nts.uk.ctx.bs.employee.app.find.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.shr.pereg.app.PeregItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonPeregDto {
	
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
	
	// 個人携帯
	@PeregItem("IS00015")
	private String personMobile;
	
	// 個人メールアドレス
	@PeregItem("IS00016")
	private String mailAddress;
	
	// 趣味
	@PeregItem("IS00017")
	private String hobby;
	
	// 嗜好
	@PeregItem("IS00018")
	private String taste;

	// 国籍
	@PeregItem("IS00019")
	private String countryId; 
	
	public static PersonPeregDto createFromDomain(Person person){
		return new PersonPeregDto(person.getPersonNameGroup().getPersonName().v(), 
				person.getPersonNameGroup().getPersonNameKana() != null ? person.getPersonNameGroup().getPersonNameKana().v() : "", 
				person.getPersonNameGroup().getPersonRomanji() != null ?person.getPersonNameGroup().getPersonRomanji().getFullName().v() :"", 
				person.getPersonNameGroup().getPersonRomanji() != null ? person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v() : "",
				person.getPersonNameGroup().getBusinessName() != null ? person.getPersonNameGroup().getBusinessName().v() : "", 
				person.getPersonNameGroup().getBusinessEnglishName() != null ? person.getPersonNameGroup().getBusinessEnglishName().v() : "", 
				person.getPersonNameGroup().getBusinessOtherName() != null ? person.getPersonNameGroup().getBusinessOtherName().v() : "", 
				person.getPersonNameGroup().getOldName() != null ? person.getPersonNameGroup().getOldName().getFullName().v() : "",  
				person.getPersonNameGroup().getOldName()!= null ? person.getPersonNameGroup().getOldName().getFullNameKana().v() : "", 
				person.getPersonNameGroup().getTodokedeFullName()!= null ? person.getPersonNameGroup().getTodokedeFullName().getFullName().v() : "", 
				person.getGender().value, 
				person.getPersonMobile() != null ? person.getPersonMobile().v() : "", 
				person.getMailAddress().v(), 
				person.getHobBy() != null ? person.getHobBy().v() : "", 
				person.getTaste() != null ? person.getTaste().v() : "", 
				person.getCountryId() != null ? person.getCountryId().v() : "");
	}
}
