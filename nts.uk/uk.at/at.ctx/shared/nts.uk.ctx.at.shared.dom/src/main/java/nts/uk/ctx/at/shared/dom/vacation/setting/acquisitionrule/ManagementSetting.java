package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * 管理設定
 * @author tutk
 *
 */
@Getter
@AllArgsConstructor
public class ManagementSetting {
	/**
	 * 代休の管理区分
	 */
	private ManageDistinct useSubHolidays;
	
	/**
	 * 振休の管理区分
	 */
	private ManageDistinct useHolidays;
}
