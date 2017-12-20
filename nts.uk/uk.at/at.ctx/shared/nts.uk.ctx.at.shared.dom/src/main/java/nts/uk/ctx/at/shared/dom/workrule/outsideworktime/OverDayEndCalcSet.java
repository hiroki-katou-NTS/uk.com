package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * 0時跨ぎ計算設定
 * @author keisuke_hoshina
 *
 */
@Value
public class OverDayEndCalcSet {
	private String companyId;
	private UseAtr calcOverDayEnd;
	private OverDayEndAggregateFrameSet overDayEndAggregateFrameSet;
	private OverDayEndCalcSetOfStatutoryHoliday isCalcSetOfStatutoryHoliday;
	private OverDayEndCalcSetOfExcessHoliday isCalcSetOfExcessHoliday;
	private OverDayEndCalcSetOfExcessSpecialHoliday isCalcSetOfExcessSpecialHoliday;
	private OverDayEndCalcSetOfWeekDay isCalcSetOfWeekDay;
}
