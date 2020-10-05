package nts.uk.ctx.at.shared.dom.workrule.weekmanage;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/** 週の管理 */
@Getter
public class WeekRuleManagement {

	/** 会社ID */
	private String cid;
	
	/** 週開始 */
	private WeekStart weekStart;
	
	private WeekRuleManagement(String cid, WeekStart weekStart) {
		this.cid = cid;
		this.weekStart = weekStart;
	}
	
	public static WeekRuleManagement of (String cid, WeekStart weekStart) {
		
		return new WeekRuleManagement(cid, weekStart);
	}
	
	public static WeekRuleManagement of (String cid, int weekStart) {
		
		return new WeekRuleManagement(cid,
									EnumAdaptor.valueOf(weekStart, WeekStart.class));
	}
}
