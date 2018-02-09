package nts.uk.ctx.at.request.dom.setting.company.displayname;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max= 7, min = 0)
public enum HdAppType {
	// 年次有休
	ANNUAL_HD(0, "年次有休"),
	// 代休
	TEMP_HD(1, "代休"),
	// 欠勤
	ABSENT(2, "欠勤"),
	// 特別休暇
	SPECIAL_VACATION(3, "特別休暇"),
	// 積立年休
	YEARLY_RESERVED(4, "積立年休"),
	// 休日
	HOLIDAY(5, "休日"),
	// 時間消化
	TIME_DEGESTION(6, "時間消化"),
	// 振休
	SHIFT(7, "振休");
	public Integer value;
	public String nameId;
}
