package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 時間年休管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeAnnualLeaveMng {
    // 時間年休消化単位
    private Integer timeAnnualLeaveUnit;

    // 時間年休管理区分
    private Boolean timeAnnualLeaveMngAtr;
}
