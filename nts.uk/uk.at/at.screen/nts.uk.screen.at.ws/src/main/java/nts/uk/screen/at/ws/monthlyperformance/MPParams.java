package nts.uk.screen.at.ws.monthlyperformance;

import java.util.List;

import nts.uk.screen.at.app.dailyperformance.correction.dto.CorrectionOfDailyPerformance;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;

public class MPParams {
	/**
	 * 抽出対象社員一覧：List＜社員ID＞
	 */
	public List<DailyPerformanceEmployeeDto> lstEmployees;
	/**
	 * 使用するフォーマット：月別実績のフォーマットコード
	 */
	public List<String> formatCodes;
	/**
	 * 表示する項目一覧
	 */
	public List<String> dispItems;
	/**
	 * 月別実績の修正
	 */
	public CorrectionOfDailyPerformance correctionOfDaily;
	/**
	 * ロック状態一覧：List＜月の実績のロック状態＞
	 */
	
}
