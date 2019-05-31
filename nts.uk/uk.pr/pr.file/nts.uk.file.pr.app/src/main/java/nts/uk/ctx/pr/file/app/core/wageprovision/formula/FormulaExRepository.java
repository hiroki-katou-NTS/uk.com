package nts.uk.ctx.pr.file.app.core.wageprovision.formula;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface FormulaExRepository {

    List<Object[]> getFormulaInfor(String cid, int startDate, GeneralDate baseDate);

    List<Object[]> getDetailFormula(String cid, int startDate);

    List<Object[]> getBaseAmountTargetItem(String cid, int startDate);

}
