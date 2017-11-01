package nts.uk.ctx.bs.employee.app.find.person.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessEnglishName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessOtherName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameKana;

@Getter
@AllArgsConstructor
public class PersonNameGroupDto {
	/** ビジネスネーム -BusinessName */
	private String businessName;

	/** 個人名 - PersonName */
	private String personName;

	/** ビジネスネームその他 - BusinessOtherName */
	private String businessOtherName;

	/** ビジネスネーム英語 - BusinessEnglishName */
	private String businessEnglishName;

	/** 個人名カナ - PersonNameKana */
	private String personNameKana;

	/** 個人名ローマ字 - PersonRomanji */
	private FullNameSetDto personRomanji;

	/** 個人届出名称 - TodokedeFullName */
	private FullNameSetDto todokedeFullName;

	/** 個人旧氏名 - OldName */
	private FullNameSetDto oldName;

	/** 旧姓届出名称 - TodokedeOldFullName */
	private FullNameSetDto todokedeOldFullName;

	public static PersonNameGroupDto fromDomain(PersonNameGroup domain) {
		if (domain == null) {
			return null;
		}

		FullNameSetDto personRomanji = FullNameSetDto.fromDomain(domain.getPersonRomanji());
		FullNameSetDto todokedeFullName = FullNameSetDto.fromDomain(domain.getTodokedeFullName());
		FullNameSetDto oldName = FullNameSetDto.fromDomain(domain.getOldName());
		FullNameSetDto todokedeOldFullName = FullNameSetDto.fromDomain(domain.getTodokedeOldFullName());

		String businessName = "", personName = "", businessOtherName = "", businessEnglishName = "",
				personNameKana = "";

		BusinessName bName = domain.getBusinessName();
		if (bName != null) {
			businessName = bName.v();
		}

		PersonName pName = domain.getPersonName();
		if (pName != null) {
			personName = pName.v();
		}

		BusinessOtherName boName = domain.getBusinessOtherName();
		if (boName != null) {
			businessOtherName = boName.v();
		}

		BusinessEnglishName beName = domain.getBusinessEnglishName();
		if (beName != null) {
			businessEnglishName = beName.v();
		}

		PersonNameKana bnName = domain.getPersonNameKana();
		if (bnName != null) {
			personNameKana = bnName.v();
		}

		return new PersonNameGroupDto(businessName, personName, businessOtherName, businessEnglishName, personNameKana,
				personRomanji, todokedeFullName, oldName, todokedeOldFullName);
	}
}
