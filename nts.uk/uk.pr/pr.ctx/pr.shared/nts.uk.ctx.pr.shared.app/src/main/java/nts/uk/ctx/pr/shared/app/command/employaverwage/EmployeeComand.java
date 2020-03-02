package nts.uk.ctx.pr.shared.app.command.employaverwage;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeComand {

    private String giveCurrTreatYear;
    private String baseDate;
    private List<String> employeeIds;
}