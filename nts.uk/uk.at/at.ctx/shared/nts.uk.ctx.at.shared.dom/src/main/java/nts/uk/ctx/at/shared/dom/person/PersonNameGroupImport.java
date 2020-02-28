package nts.uk.ctx.at.shared.dom.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
public class PersonNameGroupImport {
	/** ビジネスネーム -BusinessName */
	private String businessName;

	/** ビジネスネームカナ - BusinessName Kana */
	private String businessNameKana;

	/** ビジネスネームその他 - BusinessOtherName */
	private String businessOtherName;

	/** ビジネスネーム英語 - BusinessEnglishName */
	private String businessEnglishName;

	/** 個人名 - PersonName */
	private FullNameSetImport personName;

	/** 個人名多言語 - PersonalNameMultilingual */
	private FullNameSetImport PersonalNameMultilingual;

	/** 個人名ローマ字 - PersonRomanji */
	private FullNameSetImport personRomanji;

	/** 個人届出名称 - TodokedeFullName */
	private FullNameSetImport todokedeFullName;

	/** 個人旧氏名 - OldName */
	private FullNameSetImport oldName;
}
