package nts.uk.ctx.pr.core.app.command.employaverwage;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class EmployeeComand {

    private String giveCurrTreatYear;
    private String baseDate;
    private List<String> employeeIds;
}