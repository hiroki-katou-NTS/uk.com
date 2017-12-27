package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * 0時跨ぎを計算するしない設定(平日)
 * @author keisuke_hoshina
 *
 */
@Value
public class OverDayEndCalcSetOfWeekDay {
	private UseAtr StatutoryHoliday;
	private UseAtr ExcessHoliday;
	private UseAtr ExcessSpecialHoliday;
}
