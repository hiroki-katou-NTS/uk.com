package nts.uk.ctx.at.schedule.dom.displaysetting;

import java.util.Optional;

/**
 * 
 * @author HieuLt
 *
 */
public interface DisplaySettingByWorkplaceRepository {
	
	/**
	 * [1] get 指定した会社の表示設定を取得する
	 * @param companyID
	 * @return 	Optional<勤務予定の表示設定>
	 */
	Optional<DisplaySettingByWorkplace> get(String companyID);
	
	/**
	 * [2] insert(勤務予定の表示設定)
	 * @param workScheDisplaySetting
	 */
	void insert(DisplaySettingByWorkplace workScheDisplaySetting);
	
	/**
	 * [3] update(勤務予定の表示設定)
	 * @param workScheDisplaySetting
	 */
	void update(DisplaySettingByWorkplace workScheDisplaySetting);
	
}
