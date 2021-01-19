package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;

/**
 * @author anhnm
 *
 */
@AllArgsConstructor
@Data
public class CheckBeforeRegisterHolidayParam {
    
    public ApplicationDto oldApplication;
    
    public ApplicationDto newApplication;

    // 休暇申請起動時の表示情報
    public AppAbsenceStartInfoDto appAbsenceStartInfoDto;
    
    // 元の休暇申請
    public ApplyForLeaveDto originApplyForLeave;
    
    // 新の休暇申請
    public ApplyForLeaveDto newApplyForLeave;
}
