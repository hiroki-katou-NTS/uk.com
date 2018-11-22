package nts.uk.ctx.pr.core.app.command.employaverwage;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeAverWageComand {
    private String giveCurrTreatYear;
    List<EmployeeDto> employeeDtoList;

}
