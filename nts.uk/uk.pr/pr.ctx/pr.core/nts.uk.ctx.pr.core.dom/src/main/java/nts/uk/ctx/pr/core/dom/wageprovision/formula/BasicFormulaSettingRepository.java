package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;

/**
* かんたん計算式設定
*/
public interface BasicFormulaSettingRepository {

    Optional<BasicFormulaSetting> getBasicFormulaSettingById(String historyID);

    void add(String formulaCode, BasicFormulaSetting domain);

    void upsert(String formulaCode, BasicFormulaSetting domain);

    void removeByHistory(String historyID);

}
