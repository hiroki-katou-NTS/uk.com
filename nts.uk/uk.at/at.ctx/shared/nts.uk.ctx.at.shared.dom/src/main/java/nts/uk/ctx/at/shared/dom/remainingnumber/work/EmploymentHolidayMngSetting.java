package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;

/**
 * 雇用別休暇管理設定
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmploymentHolidayMngSetting {
	/**
	 * 雇用コード
	 */
	private String employmentCode;
	/**
	 * 雇用振休管理設定
	 */
	private Optional<EmpSubstVacation> absSetting;
	/**
	 * 雇用の代休管理設定
	 */
	private CompensatoryLeaveEmSetting dayOffSetting;
}
