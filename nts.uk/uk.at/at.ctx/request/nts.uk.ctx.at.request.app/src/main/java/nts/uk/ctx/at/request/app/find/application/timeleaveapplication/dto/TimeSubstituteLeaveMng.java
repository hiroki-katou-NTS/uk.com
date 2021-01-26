package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 時間代休管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeSubstituteLeaveMng {
    // 時間代休消化単位
    private Integer timeSubstituteLeaveUnit;

    // 時間代休管理区分
    private Boolean timeSubstituteLeaveMngAtr;
}
