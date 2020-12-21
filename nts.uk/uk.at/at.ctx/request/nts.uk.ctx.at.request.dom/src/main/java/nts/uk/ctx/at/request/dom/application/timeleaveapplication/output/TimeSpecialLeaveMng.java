package nts.uk.ctx.at.request.dom.application.timeleaveapplication.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;

import java.util.List;

/**
 * 時間特別休暇管理
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeSpecialLeaveMng {
    // 時間特別休暇の消化単位
    private Integer timeSpecialLeaveUnit;

    // 時間特別休暇の管理区分
    private Boolean timeSpecialLeaveMngAtr;

    // 特別休暇枠一覧
    private List<SpecialHolidayFrame> listSpecialFrame;
}
