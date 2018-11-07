package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;

/**
* かんたん計算式設定
*/
public interface BasicFormulaSettingRepository
{

    List<BasicFormulaSetting> getAllBasicFormulaSetting();

    Optional<BasicFormulaSetting> getBasicFormulaSettingById();

    void add(BasicFormulaSetting domain);

    void update(BasicFormulaSetting domain);

    void remove();

}
