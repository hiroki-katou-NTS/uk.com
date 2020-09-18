package nts.uk.ctx.at.schedule.dom.displaysetting;

import java.util.Optional;

public interface DisplaySettingByDateForCmpRepository {

	/**
	 * get
	 * @param companyId
	 * @return
	 */
	Optional<DisplaySettingByDateForCmp> get (String companyId);
	
	/**
	 * insert(会社別スケジュール修正日付別の表示設定)
	 * @param dispSetcom
	 */
	void insert (String companyId, DisplaySettingByDateForCmp dispSetcom);
	
	/**
	 * update(会社別スケジュール修正日付別の表示設定)
	 * @param dispSetcom
	 */
	void update (String companyId, DisplaySettingByDateForCmp dispSetcom);
}
