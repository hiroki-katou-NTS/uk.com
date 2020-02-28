package nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutPattern;
import nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout.SettingByCtgExportData;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaborInsuranceExportData {
    private List<Object[]> data;
    private String companyName;
}
