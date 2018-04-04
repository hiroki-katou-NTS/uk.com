package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author sonnlb 消化対象振休管理Dto
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubDigestionDto {
	/**
	 * 振休申請ID
	 */
	private String absenceLeaveAppID;

	/**
	 * 使用日数
	 */
	private int daysUsedNo;

	/**
	 * 振出管理データ
	 */
	private String payoutMngDataID;

	/**
	 * 振出状態
	 */
	private int pickUpState;

	/**
	 * 振休発生日
	 */
	private GeneralDate occurrenceDate;
	/**
	 * 日付不明
	 */
	private int unknownDate;
}
