package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class UpdateWorkingCondition2Command {
	
	@PeregRecordId
	private String histId;
	
	@PeregEmployeeId
	private String employeeId;
	
	/**
	 * 期間
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
	 * 日就時CD 曜日別勤務.日曜日.就業時間帯コード
	 */
	@PeregItem("IS00185")
	private String sundayWorkTimeCode;

//	/**
//	 * 日曜出勤時勤務時間1
//	 */
//	// @PeregItem("IS00186")
//
//	/**
//	 * 日開始1 曜日別勤務.日曜日.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00187")
//	private BigDecimal sundayStartTime1;
//
//	/**
//	 * 日終了1 曜日別勤務.日曜日.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00188")
//	private BigDecimal sundayEndTime1;
//
//	/**
//	 * 日曜出勤時勤務時間2
//	 */
//	// @PeregItem("IS00189")
//
//	/**
//	 * 日開始2 曜日別勤務.日曜日.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00190")
//	private BigDecimal sundayStartTime2;
//
//	/**
//	 * 日終了2 曜日別勤務.日曜日.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00191")
//	private BigDecimal sundayEndTime2;

	/**
	 * 月曜勤務設定
	 */
	// @PeregItem("IS00192")

	/**
	 * 月就時CD 曜日別勤務.月曜日.就業時間帯コード
	 */
	@PeregItem("IS00194")
	private String mondayWorkTimeCode;

//	/**
//	 * 月曜出勤時勤務時間1
//	 */
//	// @PeregItem("IS00195")
//
//	/**
//	 * 月開始1 曜日別勤務.月曜日.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00196")
//	private BigDecimal mondayStartTime1;
//
//	/**
//	 * 月終了1 曜日別勤務.月曜日.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00197")
//	private BigDecimal mondayEndTime1;
//
//	/**
//	 * 月曜出勤時勤務時間2
//	 */
//	// @PeregItem("IS00198")
//
//	/**
//	 * 月開始2 曜日別勤務.月曜日.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00199")
//	private BigDecimal mondayStartTime2;
//
//	/**
//	 * 月終了2 曜日別勤務.月曜日.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00200")
//	private BigDecimal mondayEndTime2;

	/**
	 * 火曜勤務設定
	 */
	// @PeregItem("IS00201")

	/**
	 * 火就時CD 曜日別勤務.火曜日.就業時間帯コード
	 */
	@PeregItem("IS00203")
	private String tuesdayWorkTimeCode;

//	/**
//	 * 火曜出勤時勤務時間1
//	 */
//	// @PeregItem("IS00204")
//
//	/**
//	 * 火開始1 曜日別勤務.火曜日.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00205")
//	private BigDecimal tuesdayStartTime1;
//
//	/**
//	 * 火終了1 曜日別勤務.火曜日.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00206")
//	private BigDecimal tuesdayEndTime1;
//
//	/**
//	 * 火曜出勤時勤務時間2
//	 */
//	// @PeregItem("IS00207")
//
//	/**
//	 * 火開始2 曜日別勤務.火曜日.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00208")
//	private BigDecimal tuesdayStartTime2;
//
//	/**
//	 * 火終了2 曜日別勤務.火曜日.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00209")
//	private BigDecimal tuesdayEndTime2;

	/**
	 * 水曜勤務設定
	 */
	// @PeregItem("IS00210")

	/**
	 * 水就時CD 曜日別勤務.水曜日.就業時間帯コード
	 */
	@PeregItem("IS00212")
	private String wednesdayWorkTimeCode;

//	/**
//	 * 水曜出勤時勤務時間1
//	 */
//	// @PeregItem("IS00213")
//
//	/**
//	 * 水開始1 曜日別勤務.水曜日.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00214")
//	private BigDecimal wednesdayStartTime1;
//
//	/**
//	 * 水終了1 曜日別勤務.水曜日.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00215")
//	private BigDecimal wednesdayEndTime1;
//
//	/**
//	 * 水曜出勤時勤務時間2
//	 */
//	// @PeregItem("IS00216")
//
//	/**
//	 * 水開始2 曜日別勤務.水曜日.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00217")
//	private BigDecimal wednesdayStartTime2;
//
//	/**
//	 * 水終了2 曜日別勤務.水曜日.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00218")
//	private BigDecimal wednesdayEndTime2;

	/**
	 * 木曜勤務設定
	 */
	// @PeregItem("IS00219")

	/**
	 * 木就時CD 曜日別勤務.木曜日.就業時間帯コード
	 */
	@PeregItem("IS00221")
	private String thursdayWorkTimeCode;

//	/**
//	 * 木曜出勤時勤務時間1
//	 */
//	// @PeregItem("IS00222")
//
//	/**
//	 * 木開始1 曜日別勤務.木曜日.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00223")
//	private BigDecimal thursdayStartTime1;
//
//	/**
//	 * 木終了1 曜日別勤務.木曜日.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00224")
//	private BigDecimal thursdayEndTime1;
//
//	/**
//	 * 木曜出勤時勤務時間2
//	 */
//	// @PeregItem("IS00225")
//
//	/**
//	 * 木開始2 曜日別勤務.木曜日.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00226")
//	private BigDecimal thursdayStartTime2;
//
//	/**
//	 * 木終了2 曜日別勤務.木曜日.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00227")
//	private BigDecimal thursdayEndTime2;

	/**
	 * 金曜勤務設定
	 */
	// @PeregItem("IS00228")

	/**
	 * 金就時CD 曜日別勤務.金曜日.就業時間帯コード
	 */
	@PeregItem("IS00230")
	private String fridayWorkTimeCode;

//	/**
//	 * 金曜出勤時勤務時間1
//	 *
//	 */
//	// @PeregItem("IS00231")
//
//	/**
//	 * 金開始1 曜日別勤務.金曜日.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00232")
//	private BigDecimal fridayStartTime1;
//
//	/**
//	 * 金終了1 曜日別勤務.金曜日.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00233")
//	private BigDecimal fridayEndTime1;
//
//	/**
//	 * 金曜出勤時勤務時間2
//	 */
//	// @PeregItem("IS00234")
//
//	/**
//	 * 金開始2 曜日別勤務.金曜日.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00235")
//	private BigDecimal fridayStartTime2;
//
//	/**
//	 * 金終了2 曜日別勤務.金曜日.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00236")
//	private BigDecimal fridayEndTime2;

	/**
	 * 土曜勤務設定
	 */
	// @PeregItem("IS00237")

	/**
	 * 土就時CD 曜日別勤務.土曜日.就業時間帯コード
	 */
	@PeregItem("IS00239")
	private String saturdayWorkTimeCode;

//	/**
//	 * 土曜出勤時勤務時間1
//	 */
//	// @PeregItem("IS00240")
//
//	/**
//	 * 土開始1 曜日別勤務.土曜日.勤務時間帯.開始 ※回数=1
//	 */
//	@PeregItem("IS00241")
//	private BigDecimal saturdayStartTime1;
//
//	/**
//	 * 土終了1 曜日別勤務.土曜日.勤務時間帯.終了 ※回数=1
//	 */
//	@PeregItem("IS00242")
//	private BigDecimal saturdayEndTime1;
//
//	/**
//	 * 土曜出勤時勤務時間2
//	 */
//	// @PeregItem("IS00243")
//
//	/**
//	 * 土開始2 曜日別勤務.土曜日.勤務時間帯.開始 ※回数=2
//	 */
//	@PeregItem("IS00244")
//	private BigDecimal saturdayStartTime2;
//
//	/**
//	 * 土終了2 曜日別勤務.土曜日.勤務時間帯.終了 ※回数=2
//	 */
//	@PeregItem("IS00245")
//	private BigDecimal saturdayEndTime2;

}
