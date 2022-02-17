package nts.uk.screen.at.app.query.kdp.kdps01.a;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;

/**
 * 
 * @author sonnlb
 * 
 *         【output】
 * 
 *         ドメインモデル：スマホ打刻の打刻設定
 * 
 *         ドメインモデル：打刻後の実績表示
 *
 *         Temporary：抑制する打刻
 */
@NoArgsConstructor
@Data
public class SettingSmartPhoneDto {

	/**
	 * スマホ打刻の打刻設定
	 */

	private SettingsSmartphoneStampDto setting;

	/**
	 * 打刻後の実績表示
	 */
	private StampResultDisplay resulDisplay;

	/**
	 * 抑制する打刻
	 * 
	 */
	private StampToSuppress stampToSuppress;

	/**
	 * 社員別の打刻エリア制限設定
	 */
	private EmployeeStampingAreaRestrictionSettingDto employeeStampingAreaRestrictionSetting;

}
