package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum ClosingDateAtr {
	
	/*
	 * ３６協定締め日区分
	 */
	// 0: 勤怠の締め日と同じ
	SAME_AS_CLOSING_DATE(0),
	// 1: 締め日を指定
	DESIGNATE_CLOSING_DATE(1);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "勤怠の締め日と同じ";
			break;
		case 1:
			name = "締め日を指定";
			break;
		default:
			name = "勤怠の締め日と同じ";
			break;
		}
		return name;
	}
}
