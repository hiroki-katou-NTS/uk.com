package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;
import java.util.List;

/**
* 詳細計算式
*/
public interface DetailFormulaSettingRepository {

    Optional<DetailFormulaSetting> getDetailFormulaSettingById(String historyID);

    void upsert(DetailFormulaSetting domain);

    void removeByHistory(String historyID);

}
