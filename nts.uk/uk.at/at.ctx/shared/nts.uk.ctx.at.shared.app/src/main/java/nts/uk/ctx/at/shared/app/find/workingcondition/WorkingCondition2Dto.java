package nts.uk.ctx.at.shared.app.find.workingcondition;

import java.util.Optional;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Setter
public class WorkingCondition2Dto extends PeregDomainDto {	
	
	/**
	 * 期間 - 
	 */
	@PeregItem("IS00780")
	private String period;

	/**
	 * 開始日
	 */
	@PeregItem("IS00781")
	private GeneralDate startDate;

	/**
	 * 終了日
	 */
	@PeregItem("IS00782")
	private GeneralDate endDate;
	

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

	
	public WorkingCondition2Dto(String recordId) {
		super(recordId);
		// TODO Auto-generated constructor stub
	}
	
	public static WorkingCondition2Dto createWorkingConditionDto(DateHistoryItem dateHistoryItem,
			WorkingConditionItem workingConditionItem) {
		WorkingCondition2Dto dto = new WorkingCondition2Dto(dateHistoryItem.identifier());

		dto.setRecordId(dateHistoryItem.identifier());
		dto.setStartDate(dateHistoryItem.start());
		dto.setEndDate(dateHistoryItem.end());

		// 休日出勤時

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


		return dto;
	}




	private static void setSunday(WorkingCondition2Dto dto, SingleDaySchedule sunday) {
		dto.setSundayWorkTypeCode(sunday.getWorkTypeCode().map(i->i.v()).orElse(null));

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

	private static void setMonday(WorkingCondition2Dto dto, SingleDaySchedule monday) {
		dto.setMondayWorkTypeCode(monday.getWorkTypeCode().map(i->i.v()).orElse(null));

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

	private static void setTuesday(WorkingCondition2Dto dto, SingleDaySchedule tuesday) {
		dto.setTuesdayWorkTypeCode(tuesday.getWorkTypeCode().map(i->i.v()).orElse(null));

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

	private static void setWednesday(WorkingCondition2Dto dto, SingleDaySchedule wednesday) {
		dto.setWednesdayWorkTypeCode(wednesday.getWorkTypeCode().map(i->i.v()).orElse(null));

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

	private static void setThursday(WorkingCondition2Dto dto, SingleDaySchedule thursday) {
		dto.setThursdayWorkTypeCode(thursday.getWorkTypeCode().map(i->i.v()).orElse(null));

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

	private static void setFriday(WorkingCondition2Dto dto, SingleDaySchedule friday) {
		dto.setFridayWorkTypeCode(friday.getWorkTypeCode().map(i->i.v()).orElse(null));

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

	private static void setSaturday(WorkingCondition2Dto dto, SingleDaySchedule saturday) {
		dto.setSaturdayWorkTypeCode(saturday.getWorkTypeCode().map(i->i.v()).orElse(null));

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
