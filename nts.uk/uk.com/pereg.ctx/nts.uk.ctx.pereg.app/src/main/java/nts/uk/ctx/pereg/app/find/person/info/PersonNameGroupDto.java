package nts.uk.ctx.pereg.app.find.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessEnglishName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessNameKana;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessOtherName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;

@Getter
@AllArgsConstructor
public class PersonNameGroupDto {
	/** ビジネスネーム -BusinessName */
	private String businessName;
	
	/** ビジネスネームカナ  - BusinessName Kana */
	private String businessNameKana;
	
	/** ビジネスネームその他  - BusinessOtherName*/
	private String businessOtherName;
	
	/** ビジネスネーム英語  - BusinessEnglishName*/
	private String businessEnglishName;
	
	/** 個人名 - PersonName */
	private FullNameSetDto personName;
	
	/** 個人名多言語 - PersonalNameMultilingual*/
	private FullNameSetDto PersonalNameMultilingual;
	
	/** 個人名ローマ字  - PersonRomanji */
	private FullNameSetDto personRomanji;
	
	/** 個人届出名称  - TodokedeFullName */
	private FullNameSetDto todokedeFullName;
	
	/** 個人旧氏名 - OldName*/
	private FullNameSetDto oldName;

	public static PersonNameGroupDto fromDomain(PersonNameGroup domain) {
		if (domain == null) {
			return null;
		}
		FullNameSetDto personNameSet = FullNameSetDto.fromDomain(domain.getPersonName());
		FullNameSetDto personRomanjiSet = FullNameSetDto.fromDomain(domain.getPersonRomanji());
		FullNameSetDto todokedeFullNameSet = FullNameSetDto.fromDomain(domain.getTodokedeFullName());
		FullNameSetDto oldNameSet = FullNameSetDto.fromDomain(domain.getOldName());
		FullNameSetDto PersonalNameMultilingualSet = FullNameSetDto.fromDomain(domain.getPersonalNameMultilingual());

		String businessName = "", businessNameKana = "", businessOtherName = "", businessEnglishName = "";

		BusinessName bName = domain.getBusinessName();
		if (bName != null) {
			businessName = bName.v();
		}

		BusinessNameKana bNamKana = domain.getBusinessNameKana();
		if (bNamKana != null){
			businessNameKana = bNamKana.v();
		}
		BusinessOtherName boName = domain.getBusinessOtherName();
		if (boName != null) {
			businessOtherName = boName.v();
		}

		BusinessEnglishName beName = domain.getBusinessEnglishName();
		if (beName != null) {
			businessEnglishName = beName.v();
		}

		return new PersonNameGroupDto(businessName, businessNameKana, businessOtherName, 
				businessEnglishName, personNameSet,PersonalNameMultilingualSet,personRomanjiSet,
				todokedeFullNameSet, oldNameSet);
	}
}
