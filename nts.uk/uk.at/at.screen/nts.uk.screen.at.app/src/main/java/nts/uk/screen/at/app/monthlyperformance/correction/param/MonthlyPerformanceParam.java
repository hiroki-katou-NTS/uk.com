package nts.uk.screen.at.app.monthlyperformance.correction.param;

import java.util.List;
import java.util.Map;

import lombok.Data;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTime;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.CorrectionOfMonthlyPerformance;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceEmployeeDto;
@Data
public class MonthlyPerformanceParam {
	/**
	 * 抽出対象社員一覧：List＜社員ID＞
	 */
	private List<MonthlyPerformanceEmployeeDto> lstEmployees;
	/**
	 * 使用するフォーマット：月別実績のフォーマットコード
	 */
	private List<String> formatCodes;
	/**
	 * 表示する項目一覧
	 */
	private List<PSheet> sheets;
	/**
	 * 月別実績の修正
	 */
	private CorrectionOfMonthlyPerformance correctionOfMonthly;
	/**
	 * 起動モード
	 * 0: 通常モードで起動する
	 * 2: 承認モードで起動する
	 */
	private int initMenuMode;
	/**
	 * 画面モード
	 * 0: 修正モード　の場合
	 * 1: ロック解除モード　の場合
	 */
	private int initScreenMode;
	/**
	 * 選択されたエラーコード一覧
	 *//*
	public List<String> errorCodes;*/
	/**
	 * ロック状態一覧：List＜月の実績のロック状態＞
	 */
	private List<MonthlyPerformaceLockStatus> lstLockStatus;
	/**
	 * List item(in all sheet) to be display	
	 */
	private Map<Integer, PAttendanceItem> lstAtdItemUnique;
	
	private Integer yearMonth;
	
	private ActualTime actualTime;
	
	private Integer closureId;
}
