package nts.uk.ctx.pr.file.app.core.wageprovision.formula;

import java.util.List;

public interface FormulaExRepository {

    List<Object[]> getFormulaInfor(String cid, int startDate);

    List<Object[]> getDetailFormula(String cid);

    List<Object[]> getBaseAmountTargetItem(String cid);

}
