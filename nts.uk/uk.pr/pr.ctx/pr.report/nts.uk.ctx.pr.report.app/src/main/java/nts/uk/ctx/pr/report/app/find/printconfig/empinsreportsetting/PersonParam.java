package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class PersonParam {
    List<String> employeeIds;
}
