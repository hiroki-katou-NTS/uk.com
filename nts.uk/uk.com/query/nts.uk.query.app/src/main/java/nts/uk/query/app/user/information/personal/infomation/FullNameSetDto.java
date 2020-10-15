package nts.uk.query.app.user.information.personal.infomation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.person.dom.person.info.fullnameset.FullNameSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullNameSetDto {
    /**
     * 氏名
     */
    private String fullName;

    /**
     * 氏名カナ
     */
    private String fullNameKana;

    public static FullNameSetDto toDto(FullNameSet domain){
        return new FullNameSetDto(
                domain.getFullName().v(),
                domain.getFullNameKana().v()
        );
    }
}
