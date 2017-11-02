package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;
import nts.uk.ctx.bs.employee.app.find.person.info.FullNameSetDto;

/**
 * The Class Person.
 */
// 個人

@Getter
public class ItemPersonDto extends CtgItemFixDto{
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
	
	private ItemPersonDto(String businessName, String personName, String businessOtherName, String businessEnglishName,
			String personNameKana, FullNameSetDto personRomanji, FullNameSetDto todokedeFullName, FullNameSetDto oldName, FullNameSetDto todokedeOldFullName){
		super();
		this.ctgItemType = CtgItemType.PERSON;
		this.businessName = businessName;
		this.personName = personName;
		this.businessOtherName = businessOtherName;
		this.businessEnglishName = businessEnglishName;
		this.personNameKana = personNameKana;
		this.personRomanji = personRomanji;
		this.todokedeFullName = todokedeFullName;
		this.oldName = oldName;
		this.todokedeOldFullName = todokedeOldFullName;
	}
	
	public static ItemPersonDto createFromJavaType(String businessName, String personName, String businessOtherName, String businessEnglishName,
			String personNameKana, FullNameSetDto personRomanji, FullNameSetDto todokedeFullName, FullNameSetDto oldName, FullNameSetDto todokedeOldFullName){
		return new ItemPersonDto(businessName, personName, businessOtherName, businessEnglishName, personNameKana, personRomanji, todokedeFullName, oldName, todokedeOldFullName);
	}
	
}
