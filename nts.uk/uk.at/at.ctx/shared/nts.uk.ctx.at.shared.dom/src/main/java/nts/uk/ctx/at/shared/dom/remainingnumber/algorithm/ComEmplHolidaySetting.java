package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.EmploymentHolidayMngSetting;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ComEmplHolidaySetting {
	/**
	 * 会社別休暇管理設定
	 */
	CompanyHolidayMngSetting comHolidaySetting;
	/**
	 * 雇用別休暇管理設定
	 */
	List<EmploymentHolidayMngSetting> lstEmplSetting;
}
