package nts.uk.ctx.at.request.app.find.application.holidayshipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;

/**
 * @author sonnlb 消化対象代休管理Dto
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTargetDigestionDto {
	/**
	 * 申請ID
	 */
	private String appID;

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

	/**
	 * 日付不明
	 */
	private int unknownDate;

	public static SubTargetDigestionDto createFromDomain(SubTargetDigestion domain) {
		return new SubTargetDigestionDto(domain.getAppID(), domain.getHoursUsed(), domain.getLeaveMngDataID(),
				domain.getBreakOutDate(), domain.getRestState().value, domain.getUnknownDate());
	}
}
