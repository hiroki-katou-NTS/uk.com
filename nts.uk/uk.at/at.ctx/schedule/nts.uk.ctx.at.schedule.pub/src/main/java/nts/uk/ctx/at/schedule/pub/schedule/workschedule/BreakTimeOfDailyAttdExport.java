package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class BreakTimeOfDailyAttdExport implements DomainObject {
	//休憩種類 BreakType  /* 0 就業時間帯から参照 */ /* 1 スケジュールから参照 */
	private int breakType;
	//時間帯
	private List<BreakTimeSheetExport> breakTimeSheets;
	
	public BreakTimeOfDailyAttdExport(int breakType, List<BreakTimeSheetExport> breakTimeSheets) {
		super();
		this.breakType = breakType;
		this.breakTimeSheets = breakTimeSheets;
	}	
	
	
}
