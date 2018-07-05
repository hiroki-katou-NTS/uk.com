package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AttendanceTimeImport {

	private int hour;
	
	private int minute;
	
	public int getTime() {
		return hour*60 + minute;
	}
}
