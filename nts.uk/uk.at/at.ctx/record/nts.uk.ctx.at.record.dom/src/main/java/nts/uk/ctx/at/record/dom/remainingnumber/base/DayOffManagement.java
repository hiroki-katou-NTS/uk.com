package nts.uk.ctx.at.record.dom.remainingnumber.base;

import lombok.Data;

@Data
public class DayOffManagement {

	private String code;
	private String date;
	private Double useNumberDay;

	public DayOffManagement(String code, String date, Double useNumberDay) {
		super();
		this.code = code;
		this.date = date;
		this.useNumberDay = useNumberDay;
	}
}
