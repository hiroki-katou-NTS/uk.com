package nts.uk.ctx.at.request.dom.application.applist.service.content;

import lombok.Getter;
import lombok.Setter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter
public class OvertimeHolidayWorkActual {
	
	/**
	 * 残業申請データ
	 */
	private AppOverTimeData appOverTimeData;
	
	/**
	 * 休出時間申請データ
	 */
	private AppHolidayWorkData appHolidayWorkData;
	
	/**
	 * 事後申請の実績データ
	 */
	private PostAppData postAppData;
	
	/**
	 * 実績状態
	 */
	private boolean actualStatusCheckResult;
	
	/**
	 * 背景色
	 */
	private Integer backgroundColor;

}
