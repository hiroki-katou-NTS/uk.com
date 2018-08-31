package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Value
public class RecAbsHistoryOutputDto {
	/**
	 * 年月日
	 */
	public CompensatoryDayoffDateDto ymdData;
	/**
	 * 使用日数
	 */
	public Double useDays;
	/**
	 * 振休履歴
	 */
	public AbsenceHistoryOutputParaDto absHisData;
	/**
	 * 振出履歴
	 */
	public RecruitmentHistoryOutParaDto recHisData;
}

@AllArgsConstructor
@Value
class CompensatoryDayoffDateDto {
	
	// 日付不明
	public boolean unknownDate;
	
	// 年月日
	public GeneralDate dayoffDate;
}

@AllArgsConstructor
@Value
class AbsenceHistoryOutputParaDto {
	/**	状態 */
	public int createAtr;
	/**	振休データID */
	public String absId;
	/**	振休日 */
	public CompensatoryDayoffDateDto absDate;
	/**	必要日数 */
	public Double requeiredDays;
	/**	未相殺日数 */
	public Double unOffsetDays;
}

@AllArgsConstructor
@Value
class RecruitmentHistoryOutParaDto {
	/**使用期限日	 */
	public GeneralDate expirationDate;
	/**消滅済	 */
	public boolean chkDisappeared;
	/**	状態 */
	public int dataAtr;
	/**	振出データID */
	public String recId;
	/**	振出日 */
	public CompensatoryDayoffDateDto recDate;
	/**発生日数	 */
	public Double occurrenceDays;
	/**法定内外区分	 */
	public int holidayAtr;
	/**	未使用日数 */
	public Double unUseDays;
}