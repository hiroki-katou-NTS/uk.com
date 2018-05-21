package nts.uk.ctx.at.record.dom.remainingnumber.dayoffmanagement;

import lombok.Data;

@Data
public class DaysOffMana {

	private String code;
	private String date;
	private String useNumberDay;

	public DaysOffMana(String code, String date, String useNumberDay) {
		super();
		this.code = code;
		this.date = date;
		this.useNumberDay = useNumberDay;
	}
}
