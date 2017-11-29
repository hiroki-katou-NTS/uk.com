package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * 0時跨ぎ計算するしない設定(法定外休日)
 * @author keisuke_hoshina
 *
 */
@Value
public class OverDayEndCalcSetOfExcessHoliday {
	private UseAtr WeekDay;
	private UseAtr StatutoryHoliday;
	private UseAtr ExcessSpecialHoliday;
}
