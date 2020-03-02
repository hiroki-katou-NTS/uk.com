package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth;

import lombok.Data;

@Data
public class SalaryHealthExportQuery {
    private String historyId;
    private int date;
    private String socialInsuranceCode;
    private String socialInsuranceName;
}
