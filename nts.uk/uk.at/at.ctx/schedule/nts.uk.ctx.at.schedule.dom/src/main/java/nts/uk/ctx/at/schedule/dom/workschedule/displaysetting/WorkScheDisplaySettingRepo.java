package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.Optional;

/**
 * 
 * @author HieuLt
 *
 */
public interface WorkScheDisplaySettingRepo {
	
	/**
	 * [1] get 指定した会社の表示設定を取得する
	 * @param companyID
	 * @return 	Optional<勤務予定の表示設定>
	 */
	Optional<WorkScheDisplaySetting> get(String companyID);
	
	/**
	 * [2] insert(勤務予定の表示設定)
	 * @param workScheDisplaySetting
	 */
	void insert(WorkScheDisplaySetting workScheDisplaySetting);
	
	/**
	 * [3] update(勤務予定の表示設定)
	 * @param workScheDisplaySetting
	 */
	void update(WorkScheDisplaySetting workScheDisplaySetting);
	
}
