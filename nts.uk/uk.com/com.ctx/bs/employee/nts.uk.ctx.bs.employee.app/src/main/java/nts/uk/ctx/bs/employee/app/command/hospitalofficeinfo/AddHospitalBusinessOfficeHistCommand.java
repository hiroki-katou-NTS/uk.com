package nts.uk.ctx.bs.employee.app.command.hospitalofficeinfo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class AddHospitalBusinessOfficeHistCommand {
    private  String workplaceGroupId;

    private int nightShiftOperationAtr;

    private String ClockHourMinuteStart; // HH:mm

    private String ClockHourMinuteEnd; // HH:mm

    private String startDate;

    public GeneralDate toDate() {
        return GeneralDate.fromString(startDate, "yyyy/MM/dd");
    }

}
