package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.report.app.find.socinsurnoticreset.NameNotificationSetDto;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInforService;


@Value
@Data
@AllArgsConstructor
public class ReasonRomajiNameDto {
    private String empId;
    private String basicPenNumber;
    private EmpNameReportDto empNameReport;
}

