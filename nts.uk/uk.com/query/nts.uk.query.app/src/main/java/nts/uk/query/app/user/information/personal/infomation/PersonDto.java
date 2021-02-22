package nts.uk.query.app.user.information.personal.infomation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.Person;

/**
 * Dto 個人
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    /**
     * 個人ID
     */
    private String personId;

    /**
     * 生年月日
     */

    private GeneralDate birthDate;

    /**
     * 血液型
     */
    private Integer bloodType;

    /**
     * 性別
     */
    private Integer gender;

    /**
     * 個人名グループ
     */
    private PersonNameGroupDto personNameGroup;

    public static PersonDto toDto(Person domain) {
        return new PersonDto(
                domain.getPersonId(),
                domain.getBirthDate(),
                domain.getBloodType() == null ? null : domain.getBloodType().value,
                domain.getGender() == null ? null : domain.getGender().value,
                PersonNameGroupDto.toDto(domain.getPersonNameGroup())
        );
    }
}
