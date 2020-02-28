package nts.uk.ctx.pr.core.dom.adapter.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonNameGroupExport {
    /**
     * ビジネスネーム -BusinessName
     */
    private String businessName;

    /**
     * ビジネスネームカナ  - BusinessName Kana
     */
    private String businessNameKana;

    /**
     * ビジネスネームその他  - BusinessOtherName
     */
    private String businessOtherName;

    /**
     * ビジネスネーム英語  - BusinessEnglishName
     */
    private String businessEnglishName;

    /**
     * 個人名 - PersonName
     */
    private FullNameSetExport personName;

    /**
     * 個人名多言語 - PersonalNameMultilingual
     */
    private FullNameSetExport PersonalNameMultilingual;

    /**
     * 個人名ローマ字  - PersonRomanji
     */
    private FullNameSetExport personRomanji;

    /**
     * 個人届出名称  - TodokedeFullName
     */
    private FullNameSetExport todokedeFullName;

    /**
     * 個人旧氏名 - OldName
     */
    private FullNameSetExport oldName;

}

