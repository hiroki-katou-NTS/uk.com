package nts.uk.ctx.at.request.dom.setting.company.displayname;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max= 7, min = 0)
public enum HdAppType {
	// 年次有休
	ANNUAL_HD(0),
	// 代休
	TEMP_HD(1),
	// 欠勤
	ABSENT(2),
	// 特別休暇
	SPECIAL_VACATION(3),
	// 積立年休
	YEARLY_RESERVED(4),
	// 休日
	HOLIDAY(5),
	// 時間消化
	TIME_DEGESTION(6),
	// 振休
	SHIFT(7);
	
	public final int value;
}
