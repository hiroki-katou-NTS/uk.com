package nts.uk.ctx.exio.dom.qmm.specificationitem;

import java.util.List;
import java.util.Optional;

/**
 * 明細書項目の表示設定
 */
public interface SpecificationItemDisplaySettingRepository {

	List<SpecificationItemDisplaySetting> getAllSpecItemDispSet();

	Optional<SpecificationItemDisplaySetting> getSpecItemDispSetById(String cid, String salaryItemId);

	void add(SpecificationItemDisplaySetting domain);

	void update(SpecificationItemDisplaySetting domain);

	void remove(String cid, String salaryItemId);

}