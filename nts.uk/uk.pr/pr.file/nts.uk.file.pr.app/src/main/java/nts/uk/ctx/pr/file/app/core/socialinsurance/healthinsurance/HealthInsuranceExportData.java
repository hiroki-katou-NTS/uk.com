package nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthInsuranceExportData {
    private List<Object[]> healthMonth;
    private List<Object[]> bonusHealth;
    private String companyName;
    private int startDate;
}
