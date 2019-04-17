package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth_healthinsur;


import lombok.Data;

@Data
public class SalaryHealthInsurExportQuery {
    private int targetStartYm;
    private String hisId;
    private String officeCode;
    private String socialInsuranceName;
    private String displayStart;
    private String displayEnd;
}
