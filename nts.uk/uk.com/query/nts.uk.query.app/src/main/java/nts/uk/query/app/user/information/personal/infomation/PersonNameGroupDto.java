package nts.uk.query.app.user.information.personal.infomation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;

/**
 * Dto 個人名グループ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonNameGroupDto {
    /**
     * ビジネスネーム
     */
    private String businessName;

    /**
     * ビジネスネームカナ
     */
    private String businessNameKana;

    /**
     * ビジネスネームその他
     */
    private String businessOtherName;

    /**
     * ビジネスネーム英語
     */
    private String businessEnglishName;

    /**
     * 個人名
     */
    private FullNameSetDto personName;

    /**
     * 個人名多言語
     */
    private FullNameSetDto PersonalNameMultilingual;

    /**
     * 個人名ローマ字
     */
    private FullNameSetDto personRomanji;

    /**
     * 個人届出名称
     */
    private FullNameSetDto todokedeFullName;

    /**
     * 個人旧氏名
     */
    private FullNameSetDto oldName;

    public static PersonNameGroupDto toDto(PersonNameGroup domain) {
        return new PersonNameGroupDto(
                domain.getBusinessName().v(),
                domain.getBusinessNameKana().v(),
                domain.getBusinessOtherName().v(),
                domain.getBusinessEnglishName().v(),
                FullNameSetDto.toDto(domain.getPersonName()),
                FullNameSetDto.toDto(domain.getPersonalNameMultilingual()),
                FullNameSetDto.toDto(domain.getPersonRomanji()),
                FullNameSetDto.toDto(domain.getTodokedeFullName()),
                FullNameSetDto.toDto(domain.getOldName())
        );
    }
}
