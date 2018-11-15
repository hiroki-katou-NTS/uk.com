package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

/**
* 要素範囲設定
*/
public interface ElementRangeSettingRepository
{

    List<ElementRangeSetting> getAllElementRangeSetting();

    Optional<ElementRangeSetting> getElementRangeSettingById();

    void add(ElementRangeSetting domain);

    void update(ElementRangeSetting domain);

    void remove();

}
