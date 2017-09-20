package nts.uk.ctx.bs.person.dom.person.setting.init;

import java.util.List;

public interface PerInfoInitValueSettingRepository {
	/**
	 * get All Init Value Setting
	 * 
	 * @param companyId
	 * @return List<PerInfoInitValueSetting>
	 */
	List<PerInfoInitValueSetting> getAllInitValueSetting(String companyId);

	PerInfoInitValueSetting getDetailInitValSetting(String initValueSettingId);

}
