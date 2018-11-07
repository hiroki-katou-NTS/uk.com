package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;

/**
* 詳細計算式
*/
public interface DetailFormulaSettingRepository
{

    List<DetailFormulaSetting> getAllDetailFormulaSetting();

    Optional<DetailFormulaSetting> getDetailFormulaSettingById();

    void add(DetailFormulaSetting domain);

    void update(DetailFormulaSetting domain);

    void remove();

}
