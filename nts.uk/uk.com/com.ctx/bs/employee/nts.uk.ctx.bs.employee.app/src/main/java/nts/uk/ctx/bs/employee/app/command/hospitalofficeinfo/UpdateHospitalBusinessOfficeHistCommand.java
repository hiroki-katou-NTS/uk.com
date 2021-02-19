package nts.uk.ctx.bs.employee.app.command.hospitalofficeinfo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class UpdateHospitalBusinessOfficeHistCommand {
    private GeneralDate startDate;
    private String workplaceGroupId;
    private String historyId;}
