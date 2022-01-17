package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.appabsence.ApplyForLeaveDto;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;

/**
 * @author anhnm
 *
 */
@Data
@AllArgsConstructor
public class RegisterHolidayDatesParam {
    
    public ApplicationDto oldApplication;
    
    public ApplicationDto newApplication;

    // 新の休暇申請
    public ApplyForLeaveDto newApplyForLeave;
    
    // 元の休暇申請
    public ApplyForLeaveDto originApplyForLeave;
    
    // 休日の申請日<List>
    public List<String> holidayDates;
    
    // 休暇申請起動時の表示情報
    public AppAbsenceStartInfoDto appAbsenceStartInfoDto;
    
    // 申請する勤務種類が休日か
    public boolean holidayFlg;
}
