package nts.uk.ctx.pr.file.app.core.socialinsurance.salaryhealth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto.ResponseWelfarePension;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryHealthExportData {
    ResponseWelfarePension responseWelfarePension;
    private String companyName;
}
