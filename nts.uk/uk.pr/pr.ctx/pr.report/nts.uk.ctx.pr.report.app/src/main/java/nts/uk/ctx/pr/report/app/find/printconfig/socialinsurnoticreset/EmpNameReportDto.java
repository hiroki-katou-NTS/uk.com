package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Value
@Data
@AllArgsConstructor
public class EmpNameReportDto {
    private ReasonRomajiNameDto.NameNotificationSetDto personalSet;
    private ReasonRomajiNameDto.NameNotificationSetDto spouse;

}
