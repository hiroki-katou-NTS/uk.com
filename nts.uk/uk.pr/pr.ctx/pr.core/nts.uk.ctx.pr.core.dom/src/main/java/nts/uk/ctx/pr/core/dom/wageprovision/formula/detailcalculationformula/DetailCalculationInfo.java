package nts.uk.ctx.pr.core.dom.wageprovision.formula.detailcalculationformula;

import lombok.AllArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.formula.DetailFormulaSetting;
import java.util.Optional;

@AllArgsConstructor
public class DetailCalculationInfo {
    private int yearMonth;
    private Optional<DetailFormulaSetting> detailFormulaSetting;
}
