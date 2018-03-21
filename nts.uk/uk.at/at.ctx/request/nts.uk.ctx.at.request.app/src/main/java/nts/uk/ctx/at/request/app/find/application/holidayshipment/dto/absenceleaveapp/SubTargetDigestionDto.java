package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTargetDigestionDto {
	/**
	 * 振出申請ID
	 */
	private String recAppID;

	/**
	 * 振休申請ID
	 */
	private String absenceLeaveAppID;

	/**
	 * 使用時間数
	 */
	private GeneralDate hoursUsed;

	/**
	 * 休出管理データ
	 */
	private String leaveMngDataID;
	/**
	 * 休出発生日
	 */
	private GeneralDate breakOutDate;

	/**
	 * 休出状態
	 */
	private int restState;
}
