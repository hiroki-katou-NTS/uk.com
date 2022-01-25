package nts.uk.screen.at.app.dailyperformance.correction.dto.primitive;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PrimitiveValueDaily {
	
	AttendanceTime(1, "勤怠時間", "AttendanceTime"),
	
	AttendanceTimeOfExistMinus(2, "勤怠時間（マイナス有り）", "AttendanceTimeOfExistMinus"),
	
	WorkTimes(3, "勤務回数", "WorkTimes"),
	
	WorkTypeCode(4, "勤務種類コード", "WorkTypeCode"),
	
	WorkTimeCode(5, "就業時間帯コード", "WorkTimeCode"),
	
	WorkLocationCD(6, "勤務場所コード", "WorkLocationCD"),
	
	EmploymentCode(7, "雇用区分コード", "EmploymentCode"),
	
	ClassificationCode(8, "分類コード", "ClassificationCode"),
	
	JobTitleCode(9, "職位コード", "JobTitleCode"),
	
	WorkplaceCode(10, "職場コード", "WorkplaceCode"),
	
	DivergenceReasonContent(11, "乖離理由", "DivergenceReasonContent"),
	
	BreakTimeGoOutTimes(12, "休憩外出回数", "BreakTimeGoOutTimes"),
	
	RecordRemarks(13, "実績の備考", "RecordRemarks"),
	
	DiverdenceReasonCode(14, "選択肢コード", "DiverdenceReasonCode"),
	
	TimeWithDayAttr(15, "時刻（日区分付き）", "TimeWithDayAttr"),
	
	AttendanceMonthlyTime(16, "勤怠月間時間", "AttendanceMonthlyTime"),

	AttendanceMonthlyTimeWithMinus(17, "勤怠月間時間（マイナス有り）", "AttendanceMonthlyTimeWithMinus"),
	
	AttendanceMonthlyDays(18, "勤怠月間日数", "AttendanceMonthlyDays"),
	
	AttendanceMonthlyNum(19, "勤怠月間回数", "AttendanceMonthlyNum"),
	
	Agreement36OneMonth(20, "３６協定1ヵ月時間", "Agreement36OneMonth"),
	
	Business(21, "勤務種別", "BusinessTypeCode"),
	
	CALCULATION_TIME(22, "計算時間", "CALCULATION_TIME"),
	
	MONTHLY_DAYS(23, "月間日数", "MONTHLY_DAYS"),
	
	TIME(24, "時間", "TIME"),
	
	DATE(25, "年月日", "DATE"),
	
	OUT_WORK_DATE(26, "出勤率", "OUT_WORK_DATE"),
	
	YEARLY_DAYS(27, "年間日数", "YEARLY_DAYS"),
	
	YEARLY_HOLIDAY_GRANT_DAYS(28, "年休付与日数", "YEARLY_HOLIDAY_GRANT_DAYS"),
	
	YEARLY_HOLIDAY_USE_DAYS(29, "年休使用日数", "YEARLY_HOLIDAY_USE_DAYS"),

	YEARLY_HOLIDAY_REMAINING_DAYS(30, "年休残日数", "YEARLY_HOLIDAY_REMAINING_DAYS"),
	
	YEARLY_HOLIDAY_REMAINING_TIME(31, "年休残時間", "YEARLY_HOLIDAY_REMAINING_TIME"),
	
	HALF_DAY_HOLIDAY_USE_NUM(32, "半日年休使用回数", "HALF_DAY_HOLIDAY_USE_NUM"),
	
	HALF_DAY_HOLIDAY_REMAINING_NUM(33, "半日年休残回数", "HALF_DAY_HOLIDAY_REMAINING_NUM"),
	
	HOUR_HOLIDAY_USE_NUM(34, "時間年休使用回数", "HOUR_HOLIDAY_USE_NUM"),
	
	HOUR_HOLIDAY_USE_TIME(35, "時間年休使用時間", "HOUR_HOLIDAY_USE_TIME"),
	
	HOUR_HOLIDAY_REMAINING_TIME(36, "時間年休残時間", "HOUR_HOLIDAY_REMAINING_TIME"),
	
	YEARLY_RESERVED_GRANT_DAYS(37, "積立年休付与日数", "YEARLY_RESERVED_GRANT_DAYS"),
	
	YEARLY_RESERVED_USE_DAYS(38, "積立年休使用日数", "YEARLY_RESERVED_USE_DAYS"),
	
	YEARLY_RESERVED_REMAINING_DAYS(39, "積立年休残日数", "YEARLY_RESERVED_REMAINING_DAYS"),
	
	SPECIAL_HOLIDAY_REMAINING_TIME(40, "特別休暇残時間", "SPECIAL_HOLIDAY_REMAINING_TIME"),
	
	SPECIAL_HOLIDAY_REMAINING_NUM(41, "特別休暇残数", "SPECIAL_HOLIDAY_REMAINING_NUM"),
	
	SPECIAL_HOLIDAY_REMAINING_GRANT_DAYS(42, "特別休暇残数用付与日数", "SPECIAL_HOLIDAY_REMAINING_GRANT_DAYS"),
	
	SPECIAL_HOLIDAY_USE_NUM(43, "特別休暇使用回数", "SPECIAL_HOLIDAY_USE_NUM"),
	
	SPECIAL_HOLIDAY_USE_TIME(44, "特別休暇使用時間", "SPECIAL_HOLIDAY_USE_TIME"),
	
	SPECIAL_HOLIDAY_USE_DAYS(45, "特別休暇使用日数", "SPECIAL_HOLIDAY_USE_DAYS"),
	
	SUBSTITUTE_HOLIDAY_DAYS(46, "代休日数", "SUBSTITUTE_HOLIDAY_DAYS"),
	
	SUBSTITUTE_HOLIDAY_REMAINING_DAYS(47, "代休残日数", "SUBSTITUTE_HOLIDAY_REMAINING_DAYS"),
	
	SUBSTITUTE_HOLIDAY_TIME(48, "代休時間", "SUBSTITUTE_HOLIDAY_TIME"),
	
	SUBSTITUTE_HOLIDAY_REMAINING_TIME(49, "代休残時間", "SUBSTITUTE_HOLIDAY_REMAINING_TIME"),
	
	PAUSE_REMAINING_DAYS(50, "振休残日数", "PAUSE_REMAINING_DAYS"),
	
	PAUSE_TOTAL_USE_DAYS(51, "振休日数", "PAUSE_TOTAL_USE_DAYS"),
	
    MoneyDay(54, "日次任意金額", "AnyItemAmount"),
	
	MoneyMonth(55, "月次任意金額", "AnyAmountMonth"),
	
	TimeDay(56, "日次任意時間", "AnyItemTime"),
	
	TimeMonth(57, "月次任意時間", "AnyTimeMonth"),
	
	CountDay(58, "日次任意回数", "AnyItemTimes"),
	
	CountMonth(59, "月次任意回数", "AnyTimesMonth"),
	
	ReasonCode(60, "乖離理由コード", "DiverdenceReasonCode"),
	
	APPLICATION_REASON(61, "申請理由", "APPLICATION_REASON"),
	
	APPLICATION_FIXED_REASON(62, "申請定型理由コード", "APPLICATION_FIXED_REASON"),
	
	WORD_CD(63, "作業コード", "WORD_CD"),
	
	COMPANY_ID(64, "会社ID", "COMPANY_ID"),
	
	MOUNTHLY_AMOUNT(65, "勤怠月間金額", "MOUNTHLY_AMOUNT"),
	
	RESERVATION_NUM(66, "注文数", "RESERVATION_NUM"),
	
	RESERVATION_AMOUNT(67, "注文金額", "RESERVATION_AMOUNT"),
	
	ADDITION_SETTING_CODE(68, "加給設定コード", "ADDITION_SETTING_CODE"),
	
	DAILY_AMOUNT(69, "勤怠日別金額", "DAILY_AMOUNT"),
	
	PRICE_UNIT(70, "単価", "PRICE_UNIT"),
	
	WORK_INPUT_REMARK(71, "【削除する】作業入力備考", "WORK_INPUT_REMARK"),
	
	CONTRACT_TIME(72, "労働契約時間", "CONTRACT_TIME"),
	
	SuppNumValue(73, "作業補足数値", "SuppNumValue"),
	
	WorkSuppComment(74, "作業補足コメント", "WorkSuppComment"),
	
	ChoiceCode(75, "作業補足情報の選択肢コード", "ChoiceCode");
	
	
	public final int value;
	
	public final String name;
	
	public final String primitive;
	
	public final static PrimitiveValueDaily[] values = PrimitiveValueDaily.values();
	
	public final static Map<Integer, String> mapValuePrimitive =  IntStream.range(0, values.length).boxed().collect(Collectors.toMap(x -> values[x].value, x -> values[x].primitive));
	
}
