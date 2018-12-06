package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

/**
 * 要素範囲設定
 */
public interface ElementRangeSettingRepository {

	public List<ElementRangeSetting> getAllElementRangeSetting(List<String> historyIds);

	public Optional<ElementRangeSetting> getElementRangeSettingById(String historyId);

	public void add(ElementRangeSetting domain);

	public void update(ElementRangeSetting domain);

	public void remove(String historyId);

}
