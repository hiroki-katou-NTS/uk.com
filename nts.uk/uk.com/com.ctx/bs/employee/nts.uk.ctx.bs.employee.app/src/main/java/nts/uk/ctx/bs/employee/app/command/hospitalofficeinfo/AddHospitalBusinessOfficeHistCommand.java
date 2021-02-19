package nts.uk.ctx.bs.employee.app.command.hospitalofficeinfo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class AddHospitalBusinessOfficeHistCommand {
    private  String workplaceGroupId;
    // 履歴ID: .
    private  String historyId;
    // 夜勤運用ルール: 夜勤運用ルール.

    private int nightShiftOperationAtr;

    private String ClockHourMinuteStart; // HH:mm

    private String ClockHourMinuteEnd; // HH:mm

    private GeneralDate startDate;

    private GeneralDate endDate;
}
