package nts.uk.ctx.at.shared.dom.workrule.weekmanage;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.DayOfWeek;

/** 週の管理 */
@Getter
public class WeekRuleManagement {

	/** 会社ID */
	private String cid;
	
	/** 週開始 */
	private DayOfWeek dayOfWeek;
	
	/** 週割増時間を締め開始日から計算する */
	private boolean calcWeekPremFromClosureStart;
	
	private WeekRuleManagement(String cid, DayOfWeek weekStart, boolean calcWeekPremFromClosureStart) {
		this.cid = cid;
		this.dayOfWeek = weekStart;
	}
	
	public static WeekRuleManagement of (String cid, DayOfWeek weekStart, boolean calcWeekPremFromClosureStart) {
		
		return new WeekRuleManagement(cid, weekStart, calcWeekPremFromClosureStart);
	}
	
	public static WeekRuleManagement of (String cid, int weekStart, boolean calcWeekPremFromClosureStart) {
		
		return new WeekRuleManagement(cid,
									EnumAdaptor.valueOf(weekStart, DayOfWeek.class),
									calcWeekPremFromClosureStart);
	}
	
	/** 週割増時間を計算する週開始を取得する */
	public WeekStart getWeekStart() {
		
		if (this.calcWeekPremFromClosureStart) {
			return WeekStart.TighteningStartDate;
		}
		
		switch (dayOfWeek) {
		case MONDAY:
			return WeekStart.Monday;
		case TUESDAY:
			return WeekStart.Tuesday;
		case WEDNESDAY:
			return WeekStart.Wednesday;
		case THURSDAY:
			return WeekStart.Thursday;
		case FRIDAY:
			return WeekStart.Friday;
		case SATURDAY:
			return WeekStart.Saturday;
		case SUNDAY:
			return WeekStart.Sunday;
		default:
			throw new RuntimeException("週開始: " + dayOfWeek);
		}
	}
}
