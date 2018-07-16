package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Value
public class BreakDayOffHistoryDto {
	/**
	 * 発生消化年月日
	 */
	public ComDayoffDateDto hisDate;
	/**
	 * 休出履歴
	 */
	public BreakHistoryDataDto breakHis;
	/**
	 * 代休履歴
	 */
	public DayOffHistoryDataDto dayOffHis;
	/**
	 * 使用日数
	 */
	public Double useDays;
	/**
	 * 「雇用の代休管理設定.管理区分」
	 */
	public int isManaged;
}

@AllArgsConstructor
@Value
class ComDayoffDateDto {
	
	// 日付不明
	public boolean unknownDate;
	
	// 年月日
	public GeneralDate dayoffDate;
}

@AllArgsConstructor
@Value
class BreakHistoryDataDto {
	/**	休出データID */
	public String breakMngId;
	/**
	 * 休出日
	 */
	public ComDayoffDateDto breakDate;
	/**
	 * 使用期限日
	 */
	public GeneralDate expirationDate;
	/**
	 * 消滅済
	 */
	public boolean chkDisappeared;
	/**
	 *  管理データ状態区分
	 */
	public int mngAtr;
	/**
	 * 発生日数
	 */
	public Double occurrenceDays;
	/**
	 * 未使用日数
	 */
	public Double unUseDays;
}

@AllArgsConstructor
@Value
class DayOffHistoryDataDto {
	/**	状態 */
	public int createAtr;
	/**
	 * 代休データID
	 */
	public String dayOffId;
	/**
	 * 代休日
	 */
	public ComDayoffDateDto dayOffDate;
	/**
	 * 必要日数
	 */
	public Double requeiredDays;
	/**
	 * 未相殺日数
	 */
	public Double unOffsetDays;
}