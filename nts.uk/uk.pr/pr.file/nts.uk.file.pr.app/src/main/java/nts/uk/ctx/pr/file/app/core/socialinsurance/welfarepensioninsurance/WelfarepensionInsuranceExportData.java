package nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WelfarepensionInsuranceExportData {
    private List<Object[]> welfarepensionInsuranceEmp;
    private List<Object[]> welfarepensionInsuranceBonus;
    private String companyName;
    private int startDate;
}
