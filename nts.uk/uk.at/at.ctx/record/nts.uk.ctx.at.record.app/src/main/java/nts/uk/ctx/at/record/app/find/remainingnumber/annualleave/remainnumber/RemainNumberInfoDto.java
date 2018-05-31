package nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.remainnumber;

import lombok.Data;

@Data
public class RemainNumberInfoDto {
	
	/**
	 * 年休残数
	 */
	private String annualLeaveNumber;
	
	/**
	 * 前回年休付与日
	 */
	private String lastGrantDate;

}
