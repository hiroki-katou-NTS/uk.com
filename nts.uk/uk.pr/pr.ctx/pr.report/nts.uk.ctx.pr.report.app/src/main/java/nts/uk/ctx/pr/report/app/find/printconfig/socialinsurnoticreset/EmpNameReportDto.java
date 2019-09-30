package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.report.app.find.socinsurnoticreset.NameNotificationSetDto;

@Value
@Data
@AllArgsConstructor
public class EmpNameReportDto {
    private NameNotificationSetDto personalSet;
    private NameNotificationSetDto spouse;

}
