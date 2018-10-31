package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.time.YearMonth;

import java.util.List;

public interface FormulaHistRepository {

    List<FormulaHist> getFormulaHistByYearMonth(String cid, YearMonth yearMonth);

}
