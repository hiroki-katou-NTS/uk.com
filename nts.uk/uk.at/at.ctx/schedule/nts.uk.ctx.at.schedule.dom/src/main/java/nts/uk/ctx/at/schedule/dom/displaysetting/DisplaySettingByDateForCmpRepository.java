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
	 * @param dispSetcom
	 */
	void insert (String companyId, DisplaySettingByDateForCompany dispSetcom);
	
	/**
	 * update(会社別スケジュール修正日付別の表示設定)
	 * @param dispSetcom
	 */
	void update (String companyId, DisplaySettingByDateForCompany dispSetcom);
}
