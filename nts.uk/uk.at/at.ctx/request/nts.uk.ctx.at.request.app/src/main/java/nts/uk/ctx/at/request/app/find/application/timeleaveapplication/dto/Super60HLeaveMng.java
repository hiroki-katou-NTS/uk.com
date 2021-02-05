package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 60H超休管理
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Super60HLeaveMng {
    // 60H超休消化単位
    private Integer super60HLeaveUnit;

    // 60H超休管理区分
    private Boolean super60HLeaveMngAtr;
}
