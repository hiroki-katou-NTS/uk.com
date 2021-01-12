package nts.uk.ctx.at.request.dom.application.applist.service.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
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
	 * true: 実績状態＝実績あり
	 * false: 実績状態＝実績なし
	 */
	private boolean actualStatus;
	
	/**
	 * 背景色
	 */
	private String backgroundColor;

}
