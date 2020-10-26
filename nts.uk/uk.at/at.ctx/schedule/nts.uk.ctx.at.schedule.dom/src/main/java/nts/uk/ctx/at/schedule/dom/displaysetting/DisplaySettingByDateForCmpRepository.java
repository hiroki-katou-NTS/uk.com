package nts.uk.ctx.at.schedule.dom.displaysetting;

import java.util.Optional;

public interface DisplaySettingByDateForCmpRepository {

	/**
	 * get
	 * @param companyId
	 * @return
	 */
	Optional<DisplaySettingByDateForCompany> get (String companyId);
	
	/**
	 * insert(会社別スケジュール修正日付別の表示設定)
	 * @param dispSetCom
	 */
	void insert (String companyId, DisplaySettingByDateForCompany dispSetCom);
	
	/**
	 * update(会社別スケジュール修正日付別の表示設定)
	 * @param dispSetCom
	 */
	void update (String companyId, DisplaySettingByDateForCompany dispSetCom);
}
