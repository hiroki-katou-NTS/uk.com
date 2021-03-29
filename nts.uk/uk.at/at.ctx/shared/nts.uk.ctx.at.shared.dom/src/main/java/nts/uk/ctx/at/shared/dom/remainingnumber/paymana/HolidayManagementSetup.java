package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

/**
 * The setup for holiday management
 * @author Administrator
 *
 */
@Data
@Builder
public class HolidayManagementSetup {
	
	/**
	 * 管理区分
	 */
	private ManageDistinct manageDistinct;
	
	/**
	 * 振休取得・使用方法
	 */
	private SubstVacationSetting setting;
	
}
