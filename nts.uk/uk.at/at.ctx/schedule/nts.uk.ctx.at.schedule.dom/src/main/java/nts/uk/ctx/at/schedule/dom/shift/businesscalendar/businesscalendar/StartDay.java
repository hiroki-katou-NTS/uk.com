package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.businesscalendar;

public enum StartDay {

	// 0:日曜日
	Day_0(0),

	// 1:月曜日
	Day_1(1),

	// 2:火曜日
	Day_2(2),

	// 3:水曜日
	Day_3(3),

	// 4:木曜日
	Day_4(4),

	// 5:金曜日
	Day_5(5),

	// 6:土曜日
	Day_6(6);
	
	public final int value;

	StartDay(int value) {
		this.value = value;
	}

}
