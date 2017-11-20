package nts.uk.ctx.at.shared.dom.workrule.statutoryworkTime;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.DeformationLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.NormalSetting;

/**
 * 法定労働時間設定
 * @author keisuke_hoshina
 *
 */
@Value
public class StatutoryWorkTimeSet {
	private NormalSetting normalSetting;
	private FlexSetting flexSetting;
	private DeformationLaborSetting deformationLaborSetting;
}
