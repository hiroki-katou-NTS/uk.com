package nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave.nexttime;

import lombok.Data;

@Data
public class NextTimeEventDto {
	
	/**
	 * 次回年休付与日
	 */
	private String nextTimeGrantDate;
	
	/**
	 * 次回年休付与日数
	 */
	private String nextTimeGrantDays;
	
	/**
	 * 次回時間年休付与上限
	 */
	private String nextTimeMaxTime;

	public NextTimeEventDto(String nextTimeGrantDate, String nextTimeGrantDays, String nextTimeMaxTime) {
		super();
		this.nextTimeGrantDate = nextTimeGrantDate;
		this.nextTimeGrantDays = nextTimeGrantDays;
		this.nextTimeMaxTime = nextTimeMaxTime;
	}

}
