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
	
	AttendanceTimeMonth(16, "勤怠月間時間", "AttendanceTimeMonth"),

	AttendanceTimeMonthWithMinus(17, "勤怠月間時間（マイナス有り）", "AttendanceTimeMonthWithMinus"),
	
	AttendanceDaysMonth(18, "勤怠月間日数", "AttendanceDaysMonth"),
	
	AttendanceTimesMonth(19, "勤怠月間回数", "AttendanceTimesMonth"),
	
	Agree36OverTime(20, "３６協定1ヵ月時間", "OverTime"),
	
	Business(21, "勤務種別", "BusinessTypeCode"),
	
	MonthlyDays(23, "月間日数", "MonthlyDays"),
	
	AttendanceRate(26, "出勤率", "AttendanceRate"),
	
	YearlyDays(27, "年間日数", "YearlyDays"),
	
	LeaveGrantDayNumber(28, "年休付与日数", "LeaveGrantDayNumber"),
	
	AnnualLeaveUsedDayNumber(29, "年休使用日数", "AnnualLeaveUsedDayNumber"),

	AnnualLeaveRemainingDayNumber(30, "年休残日数", "AnnualLeaveRemainingDayNumber"),
	
	AnnualLeaveRemainingTime(31, "年休残時間", "AnnualLeaveRemainingTime"),
	
	HalfDayHolidayUsedTimes(32, "半日年休使用回数", "UsedTimes"),
	
	HalfDayHolidayRemainingTimes(33, "半日年休残回数", "RemainingTimes"),
	
	HourHolidayUsedTimes(34, "時間年休使用回数", "UsedTimes"),
	
	HourHolidayUsedMinutes(35, "時間年休使用時間", "UsedMinutes"),
	
	RemainingMinutes(36, "時間年休残時間", "RemainingMinutes"),
	
	LeaveGrantDayNumber3(37, "積立年休付与日数", "LeaveGrantDayNumber"),
	
	ReserveLeaveUsedDayNumber(38, "積立年休使用日数", "ReserveLeaveUsedDayNumber"),
	
	ReserveLeaveRemainingDayNumber(39, "積立年休残日数", "ReserveLeaveRemainingDayNumber"),
	
	SpecialTimeOfRemain(40, "特別休暇残時間", "TimeOfRemain"),
	
	SpecialDayNumberOfRemain(41, "特別休暇残数", "DayNumberOfRemain"),
	
	SpecialHolidayLeaveGrantDayNumber(42, "特別休暇残数用付与日数", "LeaveGrantDayNumber"),
	
	SpecialHolidayUseNum(43, "特別休暇使用回数", "UsedTimes"),// not found
	
	SpecialTimeOfUse(44, "特別休暇使用時間", "TimeOfUse"),
	
	SpecialDayNumberOfUse(45, "特別休暇使用日数", "DayNumberOfUse"),
	
	SubstituteHolidayDays(46, "代休日数", "ReserveLeaveUsedDayNumber"),// not found
	
	SubstituteHolidayRemainingDays(47, "代休残日数", "ReserveLeaveRemainingDayNumber"),// not found
	
	SubstituteHolidayTime(48, "代休時間", "UsedMinutes"),// not found
	
	SubstituteHolidayRemainingTime(49, "代休残時間", "RemainingMinutes"),// not found
	
	PasueRemainingDays(50, "振休残日数", "ReserveLeaveRemainingDayNumber"),// not found
	
	PauseTotalUseDays(51, "振休日数", "ReserveLeaveUsedDayNumber"),// not found
	
    MoneyDay(54, "日次任意金額", "AnyItemAmount"),
	
	MoneyMonth(55, "月次任意金額", "AnyAmountMonth"),
	
	TimeDay(56, "日次任意時間", "AnyItemTime"),
	
	TimeMonth(57, "月次任意時間", "AnyTimeMonth"),
	
	CountDay(58, "日次任意回数", "AnyItemTimes"),
	
	CountMonth(59, "月次任意回数", "AnyTimesMonth"),
	
	ReasonCode(60, "乖離理由コード", "DiverdenceReasonCode"),
	
	AppReason(61, "申請理由", "AppReason"),
	
	AppStandardReasonCode(62, "申請定型理由コード", "AppStandardReasonCode"),
	
	TaskCode(63, "作業コード", "TaskCode"),
	
	CompanyId(64, "会社ID", "CompanyId"),
	
	AttendanceAmountMonth(65, "勤怠月間金額", "AttendanceAmountMonth"),
	
	OrderNumberMonthly(66, "注文数", "OrderNumberMonthly"),
	
	OrderAmountMonthly(67, "注文金額", "OrderAmountMonthly"),
	
	BonusPaySettingCode(68, "加給設定コード", "BonusPaySettingCode"),
	
	AttendanceAmountDaily(69, "勤怠日別金額", "AttendanceAmountDaily"),
	
	WorkingHoursUnitPrice(70, "単価", "WorkingHoursUnitPrice"),
	
	LaborContractTime(72, "労働契約時間", "LaborContractTime"),
	
	SuppNumValue(73, "作業補足数値", "SuppNumValue"),
	
	WorkSuppComment(74, "作業補足コメント", "WorkSuppComment"),
	
	ChoiceCode(75, "作業補足情報の選択肢コード", "ChoiceCode");
	
	
	public final int value;
	
	public final String name;
	
	public final String primitive;
	
	public final static PrimitiveValueDaily[] values = PrimitiveValueDaily.values();
	
	public final static Map<Integer, String> mapValuePrimitive =  IntStream.range(0, values.length).boxed().collect(Collectors.toMap(x -> values[x].value, x -> values[x].primitive));
	
}
