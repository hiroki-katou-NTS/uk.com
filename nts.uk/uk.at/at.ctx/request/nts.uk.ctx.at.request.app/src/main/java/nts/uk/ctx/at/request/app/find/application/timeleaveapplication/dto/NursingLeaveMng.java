package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 子看護介護管理
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NursingLeaveMng {
    // 時間介護消化単位
    private Integer timeCareLeaveUnit;

    // 時間介護管理区分
    private Boolean timeCareLeaveMngAtr;

    // 時間子看護消化単位
    private Integer timeChildCareLeaveUnit;

    // 時間子看護管理区分
    private Boolean timeChildCareLeaveMngAtr;
}
