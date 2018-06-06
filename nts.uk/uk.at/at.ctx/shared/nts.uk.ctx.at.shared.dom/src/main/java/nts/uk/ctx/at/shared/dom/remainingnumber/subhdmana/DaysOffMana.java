package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import lombok.Data;

@Data
public class DaysOffMana {

	private String comDayOffID;
	private String dayOff;
	private String remainDays;

	public DaysOffMana(String comDayOffID, String dayOff, String remainDays) {
		super();
		this.comDayOffID = comDayOffID;
		this.dayOff = dayOff;
		this.remainDays = remainDays;
	}
}
