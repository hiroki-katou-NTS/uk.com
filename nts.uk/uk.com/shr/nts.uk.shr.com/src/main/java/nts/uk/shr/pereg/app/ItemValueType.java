package nts.uk.shr.pereg.app;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemValueType {

	STRING(1),
	NUMERIC(2),
	// 3:日付(Date)
	DATE(3),

	// 4:時間(Time)
	TIME(4),

	// 5:時刻(TimePoint)
	TIMEPOINT(5),

	// 6:選択(Selection)
	SELECTION(6);
	;
	public final int value; 
}
