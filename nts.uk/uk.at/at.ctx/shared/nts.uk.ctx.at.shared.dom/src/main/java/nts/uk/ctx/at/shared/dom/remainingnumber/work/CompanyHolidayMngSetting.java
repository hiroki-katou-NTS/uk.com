package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
/**
 * 会社別休暇管理設定
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyHolidayMngSetting {
	/**
	 * 会社ID
	 */
	private String cid;
	/**
	 * 振休管理設定
	 */
	private Optional<ComSubstVacation> absSetting;
	/**
	 * 代休管理設定
	 */
	private CompensatoryLeaveComSetting dayOffSetting;
}
