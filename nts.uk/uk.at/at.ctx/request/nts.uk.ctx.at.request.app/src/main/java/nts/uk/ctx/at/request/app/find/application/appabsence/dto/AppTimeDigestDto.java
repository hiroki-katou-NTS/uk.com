package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.Data;

@Data
public class AppTimeDigestDto {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 60H超過時間
	 */
	private Integer sixtyHOvertime;
	/**
	 * 時間代休時間
	 */
	private Integer hoursOfSubHoliday;
	/**
	 * 時間年休時間
	 */
	private Integer hoursOfHoliday;
}
