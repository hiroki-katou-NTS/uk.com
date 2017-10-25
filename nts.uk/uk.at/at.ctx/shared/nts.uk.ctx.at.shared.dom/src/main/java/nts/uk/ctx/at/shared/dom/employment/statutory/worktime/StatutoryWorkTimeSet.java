package nts.uk.ctx.at.shared.dom.employment.statutory.worktime;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.shared.NormalSetting;

/**
 * 法定労働時間設定
 * @author keisuke_hoshina
 *
 */
@Value
public class StatutoryWorkTimeSet {
	private NormalSetting fixedSet;
	private FlexSetting flexSet;
	private DeformationLaborSetting fluidSet;
}
