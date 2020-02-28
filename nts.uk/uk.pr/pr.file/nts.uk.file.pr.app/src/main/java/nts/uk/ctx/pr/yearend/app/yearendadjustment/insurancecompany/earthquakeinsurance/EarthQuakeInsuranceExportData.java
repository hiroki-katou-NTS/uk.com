package nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EarthQuakeInsuranceExportData {
    private List<Object[]> earthQuakeInsurances;
    private String companyName;
}
