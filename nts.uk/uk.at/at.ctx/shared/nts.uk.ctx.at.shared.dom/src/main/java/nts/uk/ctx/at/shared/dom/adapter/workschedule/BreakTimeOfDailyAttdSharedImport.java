package nts.uk.ctx.at.shared.dom.adapter.workschedule;

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
public class BreakTimeOfDailyAttdSharedImport implements DomainObject {
	//休憩種類 BreakType  /* 0 就業時間帯から参照 */ /* 1 スケジュールから参照 */
	//時間帯
	private List<BreakTimeSheetSharedImport> breakTimeSheets;
	
	public BreakTimeOfDailyAttdSharedImport(List<BreakTimeSheetSharedImport> breakTimeSheets) {
		super();
		this.breakTimeSheets = breakTimeSheets;
	}	
	
	
}
