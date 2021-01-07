package nts.uk.ctx.at.request.app.command.application.appabsence;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;

/**
 * @author anhnm
 * 休暇申請
 *
 */
@Data
@AllArgsConstructor
public class ApplyForLeaveDto {

    // 休暇申請反映情報
    private ReflectFreeTimeAppDto reflectFreeTimeApp;
    
    // 休暇申請画面描画情報
    private VacationRequestInfoDto vacationInfo;
    
    public ApplyForLeave toDomain() {
        return new ApplyForLeave(
                reflectFreeTimeApp.toDomain(),
                vacationInfo.toDomain());
    }
}
