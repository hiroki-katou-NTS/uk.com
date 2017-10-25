package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;
/**
 * 法定労働時間(フレックス)
 * @author keisuke_hoshina
 *
 */
import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.DailyTime;

@Value
public class DailyStatutoryTimeForFlex {
	private DailyTime predetermineTime;
	private DailyTime statutoryTime;
}
