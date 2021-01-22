package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author anhnm
 *
 */
@AllArgsConstructor
@Data
public class ChangeHolidayDatesParam {

    // 申請対象日リスト
    public List<String> holidayDates;
    // 休暇申請起動時の表示情報
    
    public AppAbsenceStartInfoDto appAbsenceStartInfoDto;
}
