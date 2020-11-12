package nts.uk.ctx.at.record.dom.adapter.workschedule;

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
public class BreakTimeOfDailyAttdImport implements DomainObject {
	//休憩種類 BreakType  /* 0 就業時間帯から参照 */ /* 1 スケジュールから参照 */
	//時間帯
	private List<BreakTimeSheetImport> breakTimeSheets;
	
	public BreakTimeOfDailyAttdImport(List<BreakTimeSheetImport> breakTimeSheets) {
		super();
		this.breakTimeSheets = breakTimeSheets;
	}	
	
	
}
