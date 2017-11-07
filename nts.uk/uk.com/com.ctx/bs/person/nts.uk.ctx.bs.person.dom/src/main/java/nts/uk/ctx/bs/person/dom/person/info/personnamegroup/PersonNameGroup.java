package nts.uk.ctx.bs.person.dom.person.info.personnamegroup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.person.dom.person.info.fullnameset.FullNameSet;

@Getter
@Setter
@AllArgsConstructor
public class PersonNameGroup {

	/** ビジネスネーム -BusinessName */
	private BusinessName businessName;
	
	/** 個人名 - PersonName */
	private PersonName personName;
	
	/** ビジネスネームその他  - BusinessOtherName*/
	private BusinessOtherName businessOtherName;
	
	/** ビジネスネーム英語  - BusinessEnglishName*/
	private BusinessEnglishName businessEnglishName;
	
	/** 個人名カナ - PersonNameKana */
	private PersonNameKana personNameKana;
	
	/** 個人名ローマ字  - PersonRomanji */
	private FullNameSet personRomanji;
	
	/** 個人届出名称  - TodokedeFullName */
	private FullNameSet todokedeFullName;
	
	/** 個人旧氏名 - OldName*/
	private FullNameSet oldName;
	
	/** 旧姓届出名称 - TodokedeOldFullName*/
	private FullNameSet todokedeOldFullName;


	public PersonNameGroup(PersonName personName) {
		super();
		this.personName = personName;
	}
	
	public PersonNameGroup(PersonName personName, BusinessName businessName) {
		super();
		this.personName = personName;
		this.businessName = businessName;
	}
	
	
	

}
