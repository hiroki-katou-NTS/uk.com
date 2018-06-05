package nts.uk.ctx.at.shared.app.find.workingcondition;

import java.util.Optional;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class WorkingConditionDto extends PeregDomainDto {
	/**
	 * 期間
	 */
	@PeregItem("IS00118")
	private String period;

	/**
	 * 開始日
	 */
	@PeregItem("IS00119")
	private GeneralDate startDate;

	/**
	 * 終了日
	 */
	@PeregItem("IS00120")
	private GeneralDate endDate;

	/**
	 * スケ管理区分 予定管理区分
	 */
	@PeregItem("IS00121")
	private int scheduleManagementAtr;

	/**
	 * 勤務予定作成方法
	 */
	// @PeregItem("IS00122")

	/**
	 * 基本作成方法 予定作成方法.基本作成方法
	 */
	@PeregItem("IS00123")
	private int basicCreateMethod;

	/**
	 * 営業日カレンダーのマスタ参照先 予定作成方法.営業日カレンダーによる勤務予定作成.営業日カレンダーの参照先
	 */
	@PeregItem("IS00124")
	private Integer referenceBusinessDayCalendar;

	/**
	 * 基本勤務のマスタ参照先 予定作成方法.営業日カレンダーによる勤務予定作成.基本勤務の参照先
	 */
	@PeregItem("IS00125")
	private Integer referenceBasicWork;

	/**
	 * 就業時間帯の参照先 予定作成方法.月間パターンによる勤務予定作成.勤務種類と就業時間帯の参照先
	 */
	@PeregItem("IS00126")
	private Integer referenceType;

	/**
	 * 月間パターンCD
	 */
	@PeregItem("IS00127")
	private String monthlyPattern;

	/**
	 * 休日勤種CD 区分別勤務.休日時.勤務種類コード
	 */
	@PeregItem("IS00128")
	private String holidayWorkTypeCode;

	/**
	 * 平日時勤務設定
	 */
	// @PeregItem("IS00129")

	/**
	 * 平日勤種CD 区分別勤務.平日時.勤務種類コード
	 *
	 */
	@PeregItem("IS00130")
	private String weekdayWorkTypeCode;

	/**
	 * 平日就時CD 区分別勤務.平日時.就業時間帯コード
	 */
	@PeregItem("IS00131")
	private String weekdayWorkTimeCode;

	/**
	 * 平日時勤務時間1
	 * 
	 */
	@PeregItem("IS00132")
	private String weekDay1;

	/**
	 * 平日開始1 区分別勤務.平日時.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00133")
	private Integer weekDayStartTime1;

	/**
	 * 平日終了1 区分別勤務.平日時.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00134")
	private Integer weekDayEndTime1;

	/**
	 * 平日時勤務時間2
	 */
	@PeregItem("IS00135")
	private String weekDay2;

	/**
	 * 平日開始2 区分別勤務.平日時.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00136")
	private Integer weekDayStartTime2;

	/**
	 * 平日終了2 区分別勤務.平日時.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00137")
	private Integer weekDayEndTime2;

	/**
	 * 休出時勤務設定
	 */
	// @PeregItem("IS00138")

	/**
	 * 休出勤種CD 区分別勤務.休日出勤時.勤務種類コード
	 */
	@PeregItem("IS00139")
	private String workInHolidayWorkTypeCode;

	/**
	 * 休出就時CD 区分別勤務.休日出勤時.就業時間帯コード
	 */
	@PeregItem("IS00140")
	private String workInHolidayWorkTimeCode;

	/**
	 * 休出時勤務時間1
	 */
	@PeregItem("IS00141")
	private String workInHoliday1;

	/**
	 * 休出開始1 区分別勤務.休日出勤時.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00142")
	private Integer workInHolidayStartTime1;

	/**
	 * 休出終了1 区分別勤務.休日出勤時.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00143")
	private Integer workInHolidayEndTime1;

	/**
	 * 休出時勤務時間2
	 */
	@PeregItem("IS00144")
	private String workInHoliday2;

	/**
	 * 休出開始2 区分別勤務.休日出勤時.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00145")
	private Integer workInHolidayStartTime2;

	/**
	 * 休出終了2 区分別勤務.休日出勤時.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00146")
	private Integer workInHolidayEndTime2;

	/**
	 * 公休出勤時勤務設定
	 */
	// @PeregItem("IS00147")

	/**
	 * 公休出勤種CD 区分別勤務.公休出勤時.勤務種類コード
	 */
	@PeregItem("IS00148")
	private String workInPublicHolidayWorkTypeCode;

	/**
	 * 公休出就時CD 区分別勤務.公休出勤時.就業時間帯コード
	 */
	@PeregItem("IS00149")
	private String workInPublicHolidayWorkTimeCode;

	/**
	 * 公休時勤務時間1
	 */
	@PeregItem("IS00150")
	private String workInPubliclicHoliday1;

	/**
	 * 公休出開始1 区分別勤務.公休出勤時.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00151")
	private Integer workInPublicHolidayStartTime1;

	/**
	 * 公休出終了1 区分別勤務.公休出勤時.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00152")
	private Integer workInPublicHolidayEndTime1;

	/**
	 * 公休時勤務時間2
	 */
	@PeregItem("IS00153")
	private String workInPubliclicHoliday2;

	/**
	 * 公休出開始2 区分別勤務.公休出勤時.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00154")
	private Integer workInPublicHolidayStartTime2;

	/**
	 * 公休出終了2 区分別勤務.公休出勤時.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00155")
	private Integer workInPublicHolidayEndTime2;

	/**
	 * 法定休出時勤務設定
	 */
	// @PeregItem("IS00156")

	/**
	 * 法内休出勤種CD 区分別勤務.法内休出時.勤務種類コード
	 */
	@PeregItem("IS00157")
	private String inLawBreakTimeWorkTypeCode;

	/**
	 * 法内休出就時CD 区分別勤務.法内休出時.就業時間帯コード
	 */
	@PeregItem("IS00158")
	private String inLawBreakTimeWorkTimeCode;

	/**
	 * 法内休出時勤務時間1
	 */
	@PeregItem("IS00159")
	private String inLawBreakTime1;

	/**
	 * 法内休出開始1 区分別勤務.法内休出時.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00160")
	private Integer inLawBreakTimeStartTime1;

	/**
	 * 法内休出終了1 区分別勤務.法内休出時.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00161")
	private Integer inLawBreakTimeEndTime1;

	/**
	 * 法内休出時勤務時間2
	 */
	@PeregItem("IS00162")
	private String inLawBreakTime2;

	/**
	 * 法内休出開始2 区分別勤務.法内休出時.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00163")
	private Integer inLawBreakTimeStartTime2;

	/**
	 * 法内休出終了2 区分別勤務.法内休出時.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00164")
	private Integer inLawBreakTimeEndTime2;

	/**
	 * 法定外休出時勤務設定
	 */
	// @PeregItem("IS00165")

	/**
	 * 法外休出勤種CD 区分別勤務.法外休出時.勤務種類コード
	 */
	@PeregItem("IS00166")
	private String outsideLawBreakTimeWorkTypeCode;

	/**
	 * 法外休出就時CD 区分別勤務.法外休出時.就業時間帯コード
	 */
	@PeregItem("IS00167")
	private String outsideLawBreakTimeWorkTimeCode;

	/**
	 * 法外休出時勤務時間1
	 */
	@PeregItem("IS00168")
	private String outsideLawBreakTime1;

	/**
	 * 法外休出開始1 区分別勤務.法外休出時.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00169")
	private Integer outsideLawBreakTimeStartTime1;

	/**
	 * 法外休出終了1 区分別勤務.法外休出時.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00170")
	private Integer outsideLawBreakTimeEndTime1;

	/**
	 * 法外休出時勤務時間2
	 */
	@PeregItem("IS00171")
	private String outsideLawBreakTime2;

	/**
	 * 法外休出開始2 区分別勤務.法外休出時.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00172")
	private Integer outsideLawBreakTimeStartTime2;

	/**
	 * 法外休出終了2 区分別勤務.法外休出時.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00173")
	private Integer outsideLawBreakTimeEndTime2;

	/**
	 * 祝日時勤務設定
	 */
	// @PeregItem("IS00174")

	/**
	 * 法外祝日勤種CD 区分別勤務.祝日出勤時.勤務種類コード
	 */
	@PeregItem("IS00175")
	private String holidayAttendanceTimeWorkTypeCode;

	/**
	 * 法外祝日就時CD 区分別勤務.祝日出勤時.就業時間帯コード
	 */
	@PeregItem("IS00176")
	private String holidayAttendanceTimeWorkTimeCode;

	/**
	 * 法外祝日時勤務時間1
	 */
	@PeregItem("IS00177")
	private String holidayAttendanceTime1;

	/**
	 * 法外祝日開始1 区分別勤務.祝日出勤時.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00178")
	private Integer holidayAttendanceTimeStartTime1;

	/**
	 * 法外祝日終了1 区分別勤務.祝日出勤時.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00179")
	private Integer holidayAttendanceTimeEndTime1;

	/**
	 * 法外祝日時勤務時間2
	 */
	@PeregItem("IS00180")
	private String holidayAttendanceTime2;

	/**
	 * 法外祝日開始2 区分別勤務.祝日出勤時.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00181")
	private Integer holidayAttendanceTimeStartTime2;

	/**
	 * 法外祝日終了2 区分別勤務.祝日出勤時.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00182")
	private Integer holidayAttendanceTimeEndTime2;

	/**
	 * 日曜勤務設定
	 */
	// @PeregItem("IS00183")

	/**
	 * 日勤種CD 曜日別勤務.日曜日.勤務種類コード
	 */
	@PeregItem("IS00184")
	private String sundayWorkTypeCode;

	/**
	 * 日就時CD 曜日別勤務.日曜日.就業時間帯コード
	 */
	@PeregItem("IS00185")
	private String sundayWorkTimeCode;

	/**
	 * 日曜出勤時勤務時間1
	 */
	@PeregItem("IS00186")
	private String sunday1;

	/**
	 * 日開始1 曜日別勤務.日曜日.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00187")
	private Integer sundayStartTime1;

	/**
	 * 日終了1 曜日別勤務.日曜日.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00188")
	private Integer sundayEndTime1;

	/**
	 * 日曜出勤時勤務時間2
	 */
	@PeregItem("IS00189")
	private String sunday2;

	/**
	 * 日開始2 曜日別勤務.日曜日.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00190")
	private Integer sundayStartTime2;

	/**
	 * 日終了2 曜日別勤務.日曜日.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00191")
	private Integer sundayEndTime2;

	/**
	 * 月曜勤務設定
	 */
	// @PeregItem("IS00192")

	/**
	 * 月勤種CD 曜日別勤務.月曜日.勤務種類コード
	 */
	@PeregItem("IS00193")
	private String mondayWorkTypeCode;

	/**
	 * 月就時CD 曜日別勤務.月曜日.就業時間帯コード
	 */
	@PeregItem("IS00194")
	private String mondayWorkTimeCode;

	/**
	 * 月曜出勤時勤務時間1
	 */
	@PeregItem("IS00195")
	private String monday1;

	/**
	 * 月開始1 曜日別勤務.月曜日.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00196")
	private Integer mondayStartTime1;

	/**
	 * 月終了1 曜日別勤務.月曜日.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00197")
	private Integer mondayEndTime1;

	/**
	 * 月曜出勤時勤務時間2
	 */
	@PeregItem("IS00198")
	private String monday2;

	/**
	 * 月開始2 曜日別勤務.月曜日.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00199")
	private Integer mondayStartTime2;

	/**
	 * 月終了2 曜日別勤務.月曜日.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00200")
	private Integer mondayEndTime2;

	/**
	 * 火曜勤務設定
	 */
	// @PeregItem("IS00201")

	/**
	 * 火勤種CD 曜日別勤務.火曜日.勤務種類コード
	 */
	@PeregItem("IS00202")
	private String tuesdayWorkTypeCode;

	/**
	 * 火就時CD 曜日別勤務.火曜日.就業時間帯コード
	 */
	@PeregItem("IS00203")
	private String tuesdayWorkTimeCode;

	/**
	 * 火曜出勤時勤務時間1
	 */
	@PeregItem("IS00204")
	private String tuesday1;

	/**
	 * 火開始1 曜日別勤務.火曜日.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00205")
	private Integer tuesdayStartTime1;

	/**
	 * 火終了1 曜日別勤務.火曜日.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00206")
	private Integer tuesdayEndTime1;

	/**
	 * 火曜出勤時勤務時間2
	 */
	@PeregItem("IS00207")
	private String tuesday2;

	/**
	 * 火開始2 曜日別勤務.火曜日.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00208")
	private Integer tuesdayStartTime2;

	/**
	 * 火終了2 曜日別勤務.火曜日.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00209")
	private Integer tuesdayEndTime2;

	/**
	 * 水曜勤務設定
	 */
	// @PeregItem("IS00210")

	/**
	 * 水勤種CD 曜日別勤務.水曜日.勤務種類コード
	 */
	@PeregItem("IS00211")
	private String wednesdayWorkTypeCode;

	/**
	 * 水就時CD 曜日別勤務.水曜日.就業時間帯コード
	 */
	@PeregItem("IS00212")
	private String wednesdayWorkTimeCode;

	/**
	 * 水曜出勤時勤務時間1
	 */
	@PeregItem("IS00213")
	private String wednesday1;

	/**
	 * 水開始1 曜日別勤務.水曜日.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00214")
	private Integer wednesdayStartTime1;

	/**
	 * 水終了1 曜日別勤務.水曜日.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00215")
	private Integer wednesdayEndTime1;

	/**
	 * 水曜出勤時勤務時間2
	 */
	@PeregItem("IS00216")
	private String wednesday2;

	/**
	 * 水開始2 曜日別勤務.水曜日.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00217")
	private Integer wednesdayStartTime2;

	/**
	 * 水終了2 曜日別勤務.水曜日.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00218")
	private Integer wednesdayEndTime2;

	/**
	 * 木曜勤務設定
	 */
	// @PeregItem("IS00219")

	/**
	 * 木勤種CD 曜日別勤務.木曜日.勤務種類コード
	 */
	@PeregItem("IS00220")
	private String thursdayWorkTypeCode;

	/**
	 * 木就時CD 曜日別勤務.木曜日.就業時間帯コード
	 */
	@PeregItem("IS00221")
	private String thursdayWorkTimeCode;

	/**
	 * 木曜出勤時勤務時間1
	 */
	@PeregItem("IS00222")
	private String thursday1;

	/**
	 * 木開始1 曜日別勤務.木曜日.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00223")
	private Integer thursdayStartTime1;

	/**
	 * 木終了1 曜日別勤務.木曜日.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00224")
	private Integer thursdayEndTime1;

	/**
	 * 木曜出勤時勤務時間2
	 */
	@PeregItem("IS00225")
	private String thursday2;

	/**
	 * 木開始2 曜日別勤務.木曜日.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00226")
	private Integer thursdayStartTime2;

	/**
	 * 木終了2 曜日別勤務.木曜日.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00227")
	private Integer thursdayEndTime2;

	/**
	 * 金曜勤務設定
	 */
	// @PeregItem("IS00228")

	/**
	 * 金勤種CD 曜日別勤務.金曜日.勤務種類コード
	 */
	@PeregItem("IS00229")
	private String fridayWorkTypeCode;

	/**
	 * 金就時CD 曜日別勤務.金曜日.就業時間帯コード
	 */
	@PeregItem("IS00230")
	private String fridayWorkTimeCode;

	/**
	 * 金曜出勤時勤務時間1
	 *
	 */
	@PeregItem("IS00231")
	private String friday1;

	/**
	 * 金開始1 曜日別勤務.金曜日.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00232")
	private Integer fridayStartTime1;

	/**
	 * 金終了1 曜日別勤務.金曜日.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00233")
	private Integer fridayEndTime1;

	/**
	 * 金曜出勤時勤務時間2
	 */
	@PeregItem("IS00234")
	private String friday2;

	/**
	 * 金開始2 曜日別勤務.金曜日.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00235")
	private Integer fridayStartTime2;

	/**
	 * 金終了2 曜日別勤務.金曜日.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00236")
	private Integer fridayEndTime2;

	/**
	 * 土曜勤務設定
	 */
	// @PeregItem("IS00237")

	/**
	 * 土勤種CD 曜日別勤務.土曜日.勤務種類コード
	 */
	@PeregItem("IS00238")
	private String saturdayWorkTypeCode;

	/**
	 * 土就時CD 曜日別勤務.土曜日.就業時間帯コード
	 */
	@PeregItem("IS00239")
	private String saturdayWorkTimeCode;

	/**
	 * 土曜出勤時勤務時間1
	 */
	@PeregItem("IS00240")
	private String saturday1;

	/**
	 * 土開始1 曜日別勤務.土曜日.勤務時間帯.開始 ※回数=1
	 */
	@PeregItem("IS00241")
	private Integer saturdayStartTime1;

	/**
	 * 土終了1 曜日別勤務.土曜日.勤務時間帯.終了 ※回数=1
	 */
	@PeregItem("IS00242")
	private Integer saturdayEndTime1;

	/**
	 * 土曜出勤時勤務時間2
	 */
	@PeregItem("IS00243")
	private String saturday2;

	/**
	 * 土開始2 曜日別勤務.土曜日.勤務時間帯.開始 ※回数=2
	 */
	@PeregItem("IS00244")
	private Integer saturdayStartTime2;

	/**
	 * 土終了2 曜日別勤務.土曜日.勤務時間帯.終了 ※回数=2
	 */
	@PeregItem("IS00245")
	private Integer saturdayEndTime2;

	/**
	 * 加給CD
	 */
	// @PeregItem("IS00246")

	/**
	 * 年休就時設定 就業時間帯の自動設定区分
	 */
	@PeregItem("IS00247")
	private Integer autoIntervalSetAtr;

	/**
	 * 加算時間利用区分 休暇加算時間利用区分
	 */
	@PeregItem("IS00248")
	private Integer vacationAddedTimeAtr;

	/**
	 * 加算時間１日 休暇加算時間設定.１日
	 */
	@PeregItem("IS00249")
	private Integer oneDay;

	/**
	 * 加算時間ＡＭ 休暇加算時間設定.午前
	 */
	@PeregItem("IS00250")
	private Integer morning;

	/**
	 * 加算時間ＰＭ 休暇加算時間設定.午後
	 */
	@PeregItem("IS00251")
	private Integer afternoon;

	/**
	 * 就業区分 労働制
	 */
	@PeregItem("IS00252")
	private Integer laborSystem;

	/**
	 * 契約時間 契約時間
	 */
	@PeregItem("IS00253")
	private Integer contractTime;

	/** The auto stamp set atr. */
	// 自動打刻セット区分
	@PeregItem("IS00258")
	private int autoStampSetAtr;

	/* The hourly ppayment atr. */
	// 時給者区分
	@PeregItem("IS00259")
	private Integer hourlyPaymentAtr;

	/* The time apply. */
	// 加給時間帯
	@PeregItem("IS00246")
	private String timeApply;

	public WorkingConditionDto(String recordId) {
		super(recordId);
	}

	public static WorkingConditionDto createWorkingConditionDto(DateHistoryItem dateHistoryItem,
			WorkingConditionItem workingConditionItem) {
		WorkingConditionDto dto = new WorkingConditionDto(dateHistoryItem.identifier());

		dto.setRecordId(dateHistoryItem.identifier());
		dto.setStartDate(dateHistoryItem.start());
		dto.setEndDate(dateHistoryItem.end());

		if (workingConditionItem.getHourlyPaymentAtr() != null) {
			dto.setHourlyPaymentAtr(workingConditionItem.getHourlyPaymentAtr().value);
		}

		workingConditionItem.getTimeApply().ifPresent(wci -> {
			dto.setTimeApply(wci.v());
		});

		dto.setScheduleManagementAtr(workingConditionItem.getScheduleManagementAtr().value);

		// 予定作成方法
		workingConditionItem.getMonthlyPattern().ifPresent(mp -> {
			dto.setMonthlyPattern(mp.v());
		});

		setScheduleMethod(dto, workingConditionItem.getScheduleMethod().get());

		PersonalWorkCategory workCategory = workingConditionItem.getWorkCategory();

		// 休日出勤時
		setHolidayTime(dto, workCategory.getHolidayTime());

		// 平日時
		setWeekDay(dto, workCategory.getWeekdayTime());

		// 休日出勤時
		setWorkInHoliday(dto, workCategory.getHolidayWork());

		// 公休出勤時
		workCategory.getPublicHolidayWork().ifPresent(phw -> {
			setWorkInPublicHoliday(dto, phw);
		});

		// 法内休出時
		workCategory.getInLawBreakTime().ifPresent(ilbt -> {
			setInLawBreakTime(dto, ilbt);
		});

		// 法外休出時
		workCategory.getOutsideLawBreakTime().ifPresent(olbt -> {
			setOutsideLawBreakTime(dto, olbt);
		});

		// 祝日出勤時
		workCategory.getHolidayAttendanceTime().ifPresent(at -> {
			setHolidayAttendanceTime(dto, at);
		});

		PersonalDayOfWeek workDayOfWeek = workingConditionItem.getWorkDayOfWeek();

		// 日曜日
		workDayOfWeek.getSunday().ifPresent(sund -> setSunday(dto, sund));

		// 月曜日
		workDayOfWeek.getMonday().ifPresent(mond -> setMonday(dto, mond));

		// 火曜日
		workDayOfWeek.getTuesday().ifPresent(tue -> setTuesday(dto, tue));

		// 水曜日
		workDayOfWeek.getWednesday().ifPresent(wed -> setWednesday(dto, wed));

		// 木曜日
		workDayOfWeek.getThursday().ifPresent(thu -> setThursday(dto, thu));

		// 金曜日
		workDayOfWeek.getFriday().ifPresent(fri -> setFriday(dto, fri));

		// 土曜日
		workDayOfWeek.getSaturday().ifPresent(sat -> setSaturday(dto, sat));

		dto.setAutoIntervalSetAtr(workingConditionItem.getAutoIntervalSetAtr().value);
		dto.setVacationAddedTimeAtr(workingConditionItem.getVacationAddedTimeAtr().value);

		workingConditionItem.getHolidayAddTimeSet().ifPresent(hat -> {
			Optional.of(hat.getOneDay()).ifPresent(od -> dto.setOneDay(od.v()));

			Optional.of(hat.getMorning()).ifPresent(od -> dto.setMorning(od.v()));

			Optional.of(hat.getAfternoon()).ifPresent(od -> dto.setAfternoon(od.v()));
		});

		dto.setLaborSystem(workingConditionItem.getLaborSystem().value);
		dto.setContractTime(workingConditionItem.getContractTime().v());
		dto.setAutoStampSetAtr(workingConditionItem.getAutoStampSetAtr().value);

		return dto;
	}

	private static void setScheduleMethod(WorkingConditionDto dto, ScheduleMethod scheduleMethod) {
		dto.setBasicCreateMethod(scheduleMethod.getBasicCreateMethod().value);

		scheduleMethod.getWorkScheduleBusCal().ifPresent(wsb -> {
			dto.setReferenceBasicWork(wsb.getReferenceBasicWork().value);
			dto.setReferenceBusinessDayCalendar(wsb.getReferenceBusinessDayCalendar().value);
		});

		// cần xem lại thuật toán thực thi đoạn mã này
		switch (scheduleMethod.getBasicCreateMethod()) {
		case BUSINESS_DAY_CALENDAR:
			scheduleMethod.getWorkScheduleBusCal().ifPresent(wsb -> {
				dto.setReferenceType(wsb.getReferenceWorkingHours().value);
			});
			break;
		case MONTHLY_PATTERN:
			scheduleMethod.getMonthlyPatternWorkScheduleCre().ifPresent(mps -> {
				dto.setReferenceType(mps.getReferenceType().value);
			});
			break;
		default:
		case PERSONAL_DAY_OF_WEEK:
			break;
		}
	}

	private static void setHolidayTime(WorkingConditionDto dto, SingleDaySchedule holidayTime) {
		Optional.ofNullable(holidayTime).ifPresent(ht -> {
			dto.setHolidayWorkTypeCode(ht.getWorkTypeCode().v());
		});
	}

	private static void setWeekDay(WorkingConditionDto dto, SingleDaySchedule weekDay) {
		Optional.of(weekDay).ifPresent(wd -> {
			dto.setWeekdayWorkTypeCode(wd.getWorkTypeCode().v());

			wd.getWorkTimeCode().ifPresent(wt -> dto.setWeekdayWorkTimeCode(wt.v()));

			Optional<TimeZone> timeZone1 = wd.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
					.findFirst();
			Optional<TimeZone> timeZone2 = wd.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
					.findFirst();

			timeZone1.ifPresent(tz -> {
				dto.setWeekDayStartTime1(tz.getStart().v());
				dto.setWeekDayEndTime1(tz.getEnd().v());
			});

			timeZone2.ifPresent(tz -> {
				dto.setWeekDayStartTime2(tz.getStart().v());
				dto.setWeekDayEndTime2(tz.getEnd().v());
			});
		});
	}

	private static void setWorkInHoliday(WorkingConditionDto dto, SingleDaySchedule workInHoliday) {
		dto.setWorkInHolidayWorkTypeCode(workInHoliday.getWorkTypeCode().v());

		workInHoliday.getWorkTimeCode().ifPresent(wtc -> dto.setWorkInHolidayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = workInHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();

		Optional<TimeZone> timeZone2 = workInHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setWorkInHolidayStartTime1(tz.getStart().v());
			dto.setWorkInHolidayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setWorkInHolidayStartTime2(tz.getStart().v());
			dto.setWorkInHolidayEndTime2(tz.getEnd().v());
		});
	}

	private static void setWorkInPublicHoliday(WorkingConditionDto dto, SingleDaySchedule workInPublicHoliday) {
		dto.setWorkInPublicHolidayWorkTypeCode(workInPublicHoliday.getWorkTypeCode().v());

		workInPublicHoliday.getWorkTimeCode().ifPresent(wtc -> dto.setWorkInPublicHolidayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = workInPublicHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = workInPublicHoliday.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setWorkInPublicHolidayStartTime1(tz.getStart().v());
			dto.setWorkInPublicHolidayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setWorkInPublicHolidayStartTime2(tz.getStart().v());
			dto.setWorkInPublicHolidayEndTime2(tz.getEnd().v());
		});
	}

	private static void setInLawBreakTime(WorkingConditionDto dto, SingleDaySchedule inLawBreakTime) {
		dto.setInLawBreakTimeWorkTypeCode(inLawBreakTime.getWorkTypeCode().v());

		inLawBreakTime.getWorkTimeCode().ifPresent(wtc -> dto.setInLawBreakTimeWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = inLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = inLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setInLawBreakTimeStartTime1(tz.getStart().v());
			dto.setInLawBreakTimeEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setInLawBreakTimeStartTime2(tz.getStart().v());
			dto.setInLawBreakTimeEndTime2(tz.getEnd().v());
		});
	}

	private static void setOutsideLawBreakTime(WorkingConditionDto dto, SingleDaySchedule outsideLawBreakTime) {
		dto.setOutsideLawBreakTimeWorkTypeCode(outsideLawBreakTime.getWorkTypeCode().v());

		outsideLawBreakTime.getWorkTimeCode().ifPresent(wtc -> dto.setOutsideLawBreakTimeWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = outsideLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = outsideLawBreakTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setOutsideLawBreakTimeStartTime1(tz.getStart().v());
			dto.setOutsideLawBreakTimeEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setOutsideLawBreakTimeStartTime2(tz.getStart().v());
			dto.setOutsideLawBreakTimeEndTime2(tz.getEnd().v());
		});
	}

	private static void setHolidayAttendanceTime(WorkingConditionDto dto, SingleDaySchedule holidayAttendanceTime) {
		dto.setHolidayAttendanceTimeWorkTypeCode(holidayAttendanceTime.getWorkTypeCode().v());

		holidayAttendanceTime.getWorkTimeCode().ifPresent(wtc -> dto.setHolidayAttendanceTimeWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = holidayAttendanceTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 1).findFirst();
		Optional<TimeZone> timeZone2 = holidayAttendanceTime.getWorkingHours().stream()
				.filter(timeZone -> timeZone.getCnt() == 2).findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setHolidayAttendanceTimeStartTime1(tz.getStart().v());
			dto.setHolidayAttendanceTimeEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setHolidayAttendanceTimeStartTime2(tz.getStart().v());
			dto.setHolidayAttendanceTimeEndTime2(tz.getEnd().v());
		});
	}

	private static void setSunday(WorkingConditionDto dto, SingleDaySchedule sunday) {
		dto.setSundayWorkTypeCode(sunday.getWorkTypeCode().v());

		sunday.getWorkTimeCode().ifPresent(wtc -> dto.setSundayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = sunday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = sunday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setSundayStartTime1(tz.getStart().v());
			dto.setSundayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setSundayStartTime2(tz.getStart().v());
			dto.setSundayEndTime2(tz.getEnd().v());
		});
	}

	private static void setMonday(WorkingConditionDto dto, SingleDaySchedule monday) {
		dto.setMondayWorkTypeCode(monday.getWorkTypeCode().v());

		monday.getWorkTimeCode().ifPresent(wtc -> dto.setMondayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = monday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = monday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setMondayStartTime1(tz.getStart().v());
			dto.setMondayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setMondayStartTime2(tz.getStart().v());
			dto.setMondayEndTime2(tz.getEnd().v());
		});
	}

	private static void setTuesday(WorkingConditionDto dto, SingleDaySchedule tuesday) {
		dto.setTuesdayWorkTypeCode(tuesday.getWorkTypeCode().v());

		tuesday.getWorkTimeCode().ifPresent(wtc -> dto.setTuesdayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = tuesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = tuesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setTuesdayStartTime1(tz.getStart().v());
			dto.setTuesdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setTuesdayStartTime2(tz.getStart().v());
			dto.setTuesdayEndTime2(tz.getEnd().v());
		});
	}

	private static void setWednesday(WorkingConditionDto dto, SingleDaySchedule wednesday) {
		dto.setWednesdayWorkTypeCode(wednesday.getWorkTypeCode().v());

		wednesday.getWorkTimeCode().ifPresent(wtc -> dto.setWednesdayWorkTimeCode(wtc.v()));

		Optional<TimeZone> timeZone1 = wednesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = wednesday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setWednesdayStartTime1(tz.getStart().v());
			dto.setWednesdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setWednesdayStartTime2(tz.getStart().v());
			dto.setWednesdayEndTime2(tz.getEnd().v());
		});
	}

	private static void setThursday(WorkingConditionDto dto, SingleDaySchedule thursday) {
		dto.setThursdayWorkTypeCode(thursday.getWorkTypeCode().v());

		thursday.getWorkTimeCode().ifPresent(wtc -> {
			dto.setThursdayWorkTimeCode(wtc.v());
		});

		Optional<TimeZone> timeZone1 = thursday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = thursday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setThursdayStartTime1(tz.getStart().v());
			dto.setThursdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setThursdayStartTime2(tz.getStart().v());
			dto.setThursdayEndTime2(tz.getEnd().v());
		});
	}

	private static void setFriday(WorkingConditionDto dto, SingleDaySchedule friday) {
		dto.setFridayWorkTypeCode(friday.getWorkTypeCode().v());

		friday.getWorkTimeCode().ifPresent(wtc -> {
			dto.setFridayWorkTimeCode(wtc.v());
		});

		Optional<TimeZone> timeZone1 = friday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = friday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setFridayStartTime1(tz.getStart().v());
			dto.setFridayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setFridayStartTime2(tz.getStart().v());
			dto.setFridayEndTime2(tz.getEnd().v());
		});
	}

	private static void setSaturday(WorkingConditionDto dto, SingleDaySchedule saturday) {
		dto.setSaturdayWorkTypeCode(saturday.getWorkTypeCode().v());

		saturday.getWorkTimeCode().ifPresent(wtc -> {
			dto.setSaturdayWorkTimeCode(wtc.v());
		});

		Optional<TimeZone> timeZone1 = saturday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 1)
				.findFirst();
		Optional<TimeZone> timeZone2 = saturday.getWorkingHours().stream().filter(timeZone -> timeZone.getCnt() == 2)
				.findFirst();

		timeZone1.ifPresent(tz -> {
			dto.setSaturdayStartTime1(tz.getStart().v());
			dto.setSaturdayEndTime1(tz.getEnd().v());
		});

		timeZone2.ifPresent(tz -> {
			dto.setSaturdayStartTime2(tz.getStart().v());
			dto.setSaturdayEndTime2(tz.getEnd().v());
		});
	}

}
