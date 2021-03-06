package nts.uk.screen.at.app.kdl036;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Kdl036OutputData {
    private String employeeId;

    // 対象選択区分
    private int targetSelectionAtr;

    // 代休日リスト
    private List<GeneralDate> substituteHolidayList;

    // 休日出勤情報一覧
    private List<HolidayWorkData> holidayWorkInfoList;

    // 日数単位
    private double daysUnit;
}
