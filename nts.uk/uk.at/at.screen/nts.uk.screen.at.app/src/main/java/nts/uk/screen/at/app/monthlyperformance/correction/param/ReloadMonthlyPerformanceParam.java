package nts.uk.screen.at.app.monthlyperformance.correction.param;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceEmployeeDto;

@Data
@NoArgsConstructor
public class ReloadMonthlyPerformanceParam {
	
	/**
	 * 抽出対象社員一覧：List＜社員ID＞
	 */
	private List<MonthlyPerformanceEmployeeDto> lstEmployees;
	
	/**
	 * 処理年月
	 * YYYYMM
	 */
	private int processDate;
	
	/**
	 * 画面モード
	 * 0: 修正モード　の場合
	 * 1: ロック解除モード　の場合
	 */
	private int initScreenMode;
	
	/**
	 * 締めID
	 *  Hidden closureId
	 */
	private Integer closureId;
	
	/**
	 * List item(in all sheet) to be display	
	 */
	private Map<Integer, PAttendanceItem> lstAtdItemUnique;

}
