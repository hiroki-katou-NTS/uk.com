package nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LifeInsuranceExportData {
    private List<Object[]> lifeInsurance;
    private String companyName;
}
