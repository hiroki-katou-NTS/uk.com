package nts.uk.screen.at.ws.monthlyperformance;

import java.util.List;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.CorrectionOfDailyPerformance;

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
	 * 起動モード
	 */
	public int initMode;
	/**
	 * 選択されたエラーコード一覧
	 */
	public List<String> errorCodes;
	/**
	 * ロック状態一覧：List＜月の実績のロック状態＞
	 */
	public List<MPLockStatus> lstLockStatus;
}
