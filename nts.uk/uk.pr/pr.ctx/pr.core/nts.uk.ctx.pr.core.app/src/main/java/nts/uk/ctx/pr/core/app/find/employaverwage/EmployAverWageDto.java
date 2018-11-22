package nts.uk.ctx.pr.core.app.find.employaverwage;

import lombok.Data;

import java.util.List;

@Data
public class EmployAverWageDto {

    private String date;
    private List<String> employeeIds;
    private List<String>  averageWages;

}
