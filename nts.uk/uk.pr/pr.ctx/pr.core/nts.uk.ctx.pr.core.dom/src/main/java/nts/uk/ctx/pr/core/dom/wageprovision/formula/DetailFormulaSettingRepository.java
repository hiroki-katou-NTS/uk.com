package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;
import java.util.List;

/**
* 詳細計算式
*/
public interface DetailFormulaSettingRepository
{

    List<DetailFormulaSetting> getAllDetailFormulaSetting();

    Optional<DetailFormulaSetting> getDetailFormulaSettingById(String historyID);

    void add(DetailFormulaSetting domain);

    void update(DetailFormulaSetting domain);

    void upsert(DetailFormulaSetting domain);

    void remove(DetailFormulaSetting domain);

    void removeByHistory(String historyID);

    void removeByFormulaCode(String formulaCode);

}
