package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AttendanceItemType {
	/* 人件費*/
	Cost(0),
	/* 乖離時間*/
	DivergenceTime(1);
	public final int value;
}
