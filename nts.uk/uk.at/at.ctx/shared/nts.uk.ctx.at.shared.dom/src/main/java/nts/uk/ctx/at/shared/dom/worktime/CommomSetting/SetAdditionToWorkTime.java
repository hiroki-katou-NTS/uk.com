package nts.uk.ctx.at.shared.dom.worktime.CommomSetting;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.BreakdownTimeDay;

/**
 * 就業時間への加算設定
 * @author keisuke_hoshina
 *
 */
@Value
public class SetAdditionToWorkTime {
	private BreakdownTimeDay time;
}
