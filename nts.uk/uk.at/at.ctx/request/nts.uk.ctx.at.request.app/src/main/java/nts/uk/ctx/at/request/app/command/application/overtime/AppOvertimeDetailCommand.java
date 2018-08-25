package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

/**
 * 時間外時間の詳細
 */
@Data
@AllArgsConstructor
public class AppOvertimeDetailCommand {
	/**
	 * 申請時間
	 */
	private int applicationTime;

	/**
	 * 年月
	 */
	private int yearMonth;

	/**
	 * 実績時間
	 */
	private int actualTime;

	/**
	 * 36時間
	 */
	private int time36;

	/**
	 * 36年間超過月
	 */
	private List<Integer> year36OverMonth;

	/**
	 * 36年間超過回数
	 */
	private int numOfYear36Over;

	public AppOvertimeDetail toDomain(String cid, String appId) {
		return new AppOvertimeDetail(cid, appId, this.applicationTime, this.yearMonth, this.actualTime, this.time36,
				this.year36OverMonth, this.numOfYear36Over);
	}
}
