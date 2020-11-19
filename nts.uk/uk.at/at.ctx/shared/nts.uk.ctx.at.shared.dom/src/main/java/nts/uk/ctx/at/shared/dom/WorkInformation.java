package nts.uk.ctx.at.shared.dom;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ClockAreaAtr;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo.ContainsResult;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 勤務情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務情報
 * @author ken_takasu
 *
 */
public class WorkInformation {

	/** 勤務種類コード **/
	private WorkTypeCode workTypeCode;
	/** 就業時間帯コード **/
	private Optional<WorkTimeCode> workTimeCode;

	public WorkInformation(String workTypeCode, String workTimeCode) {
		this.setWorkTypeCode(workTypeCode);
		this.setWorkTimeCode(workTimeCode);
	}

	public WorkInformation(WorkTypeCode workTypeCode, WorkTimeCode workTimeCode) {
		this.setWorkTypeCode(workTypeCode);
		this.setWorkTimeCode(workTimeCode);
	}

	public WorkInformation clone() {
		return new WorkInformation(workTypeCode, workTimeCode.orElse(null));
	}

	/**
	 * 勤務種類コード(Get)
	 * @return 勤務種類コード
	 */
	public WorkTypeCode getWorkTypeCode() {
		return this.workTypeCode;
	}
	/**
	 * 勤務種類コード(Set)
	 * @param workTypeCode 就業時間帯
	 */
	public void setWorkTypeCode(String workTypeCode) {
		if (!StringUtils.isEmpty(workTypeCode)) {
			this.setWorkTypeCode(new WorkTypeCode(workTypeCode));
		}
	}
	/**
	 * 勤務種類コード(Set)
	 * @param workTypeCode 就業時間帯
	 */
	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	/**
	 * 就業時間帯コード(Get)
	 * @return 就業時間帯コード or null
	 */
	public WorkTimeCode getWorkTimeCode() {
		return this.workTimeCode.orElse(null);
	}
	/**
	 * 就業時間帯コード(Get)
	 * @return 就業時間帯コード
	 */
	public Optional<WorkTimeCode> getWorkTimeCodeNotNull() {
		return this.workTimeCode;
	}
	/**
	 * 就業時間帯コード(Set)
	 * @param workTimeCode 就業時間帯コード
	 */
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.workTimeCode = Optional.ofNullable(workTimeCode);
	}
	/**
	 * 就業時間帯コード(Set)
	 * @param workTimeCode 就業時間帯コード
	 */
	public void setWorkTimeCode(String workTimeCode) {
		if (StringUtils.isEmpty(workTimeCode)) {
			this.workTimeCode = Optional.empty();
		} else {
			this.workTimeCode = Optional.of(new WorkTimeCode(workTimeCode));
		}
	}

	public void removeWorkTimeInHolydayWorkType() {
		this.workTimeCode = Optional.empty();
	}

	public boolean isExamWorkTime() {

		return workTimeCode
				.map(m -> m.equals(new WorkTimeCode("102")) || m.equals(new WorkTimeCode("103")))
				.orElse(false);
	}


	/**
	 * 正常な状態か
	 * @param require require
	 * @return 勤務情報のエラー状態が"正常"であればtrue
	 */
	public boolean checkNormalCondition(Require require) {
		// 勤務情報のエラー状態が"正常"であればtrue
		return this.checkErrorCondition( require ) == ErrorStatusWorkInfo.NORMAL;
	}

	/**
	 * エラー状態をチェックする
	 * @param require require
	 */
	public ErrorStatusWorkInfo checkErrorCondition(Require require) {

		// 勤務種類を取得する
		val workType = require.getWorkType( this.workTypeCode.v() );
		if ( !workType.isPresent() ) {
			// 勤務種類が取得できない⇒勤務種類が削除された
			return ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE;
		}
		if( workType.get().isDeprecated() ) {
			// 廃止済⇒勤務種類が廃止された
			return ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED;
		}

		// 就業時間帯の必須チェック
		SetupType setupType = require.checkNeededOfWorkTimeSetting( this.workTypeCode.v() );
		switch (setupType) {
			case REQUIRED:	// 必須
				if ( !this.workTimeCode.isPresent() ) {
					// @就業時間帯コードが未設定→就業時間帯が必須なのに設定されていない
					return ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET;
				}
				break;
			case OPTIONAL:	// 任意
				if ( !this.workTimeCode.isPresent() ) {
					// @就業時間帯コードが未設定→正常
					return ErrorStatusWorkInfo.NORMAL;
				}
				break;
			default:		// 不要
				if ( this.workTimeCode.isPresent() ) {
					// @就業時間帯コードが設定されている→就業時間帯が不要なのに設定されている
					return ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY;
				} else {
					// @就業時間帯コードが未設定→正常
					return ErrorStatusWorkInfo.NORMAL;
				}
		}

		// 就業時間帯を取得する
		val workTimeSetting = require.getWorkTime( this.workTimeCode.get().v() );
		if ( !workTimeSetting.isPresent() ) {
			// 就業時間帯が取得できない→就業時間帯が削除された
			return ErrorStatusWorkInfo.WORKTIME_WAS_DELETE;
		}
		if( workTimeSetting.get().isAbolish() ) {
			// 廃止済⇒就業時間帯が廃止された
			return ErrorStatusWorkInfo.WORKTIME_HAS_BEEN_ABOLISHED;
		}

		// すべてOK→正常
		return ErrorStatusWorkInfo.NORMAL;

	}

	/**
	 * 出勤・休日系の判定
	 * @return WorkStyle 出勤休日区分
	 */
	public Optional<WorkStyle> getWorkStyle(Require require) {

		// 勤務種類を取得する
		val workType = require.getWorkType( this.workTypeCode.v() );
		if ( !workType.isPresent() ) {
			// 勤務種類が取得できない
			return Optional.empty();
		}

		return Optional.of( workType.get().checkWorkDay() );

	}

	/**
	 * 勤務情報と補正済み所定時間帯を取得する
	 * @param require require
	 */
	public Optional<WorkInfoAndTimeZone> getWorkInfoAndTimeZone(Require require) {

		// 勤務種類を取得する
		Optional<WorkType> workType = require.getWorkType(this.workTypeCode.v());
		if ( !workType.isPresent() ) {
			// 勤務種類が取得できない
			return Optional.empty();
		}

		// 就業時間帯コードの有無
		if ( !this.workTimeCode.isPresent() ) {
			// 就業時間帯コードなし
			return Optional.of(WorkInfoAndTimeZone.createWithoutWorkTime( workType.get() ));
		}

		// 就業時間帯を取得する
		val workTimeSetting = require.getWorkTime( this.workTimeCode.get().v() );
		if ( !workTimeSetting.isPresent() ) {
			// 就業時間帯が取得できない
			return Optional.empty();
		}

		// 補正済み所定時間帯を取得する
		val correctedTimezones = require.getPredeterminedTimezone( this.workTypeCode.v(), this.workTimeCode.get().v(), null )
									.getTimezones().stream()
									.filter( e -> e.isUsed() )
									.sorted(Comparator.comparing(TimezoneUse::getWorkNo))
									.map( e -> (TimeZone)e )
									.collect(Collectors.toList());

		return Optional.of( WorkInfoAndTimeZone.create( workType.get(), workTimeSetting.get(), correctedTimezones ));

	}


	/**
	 * 変更可能な勤務時間帯を取得する
	 * @param require require
	 * @return 変更可能な勤務時間帯リスト
	 */
	public List<ChangeableWorkingTimeZonePerNo> getChangeableWorkingTimezones(Require require) {

		// 勤務種類を取得する
		val workType = require.getWorkType( this.workTypeCode.v() );
		if ( !workType.isPresent() ) {
			// 勤務種類が取得できない
			return Collections.emptyList();
		}

		// 勤務設定を取得する
		val workSetting = this.getWorkSetting(require);
		if ( !workSetting.isPresent() ) {
			// 勤務設定が取得できない
			return Collections.emptyList();
		}

		// 出勤日区分を取得する
		val atdDayAtr = workType.get().chechAttendanceDay();
		// 出勤日区分による変更可能な時間帯を取得する
		return workSetting.get().getChangeableWorkingTimeZone(require).getByAtr( atdDayAtr );

	}

	/**
	 * 変更可能な勤務時間帯のチェック
	 * ※存在しない勤務NOを指定するとSystemErrorが発生する
	 * @param require require
	 * @param checkTarget 対象時刻区分
	 * @param workNo 勤務NO
	 * @param time 時刻
	 * @return 時間帯に含まれているか
	 */
	public ContainsResult containsOnChangeableWorkingTime(Require require, ClockAreaAtr checkTarget, WorkNo workNo, TimeWithDayAttr time) {

		// 勤務NOに対応する変更可能な時間帯を取得する
		val timezone = this.getChangeableWorkingTimezones(require).stream()
							.filter( e -> e.getWorkNo().v() == workNo.toAttendance().v() )
							.findFirst().get();

		// 時間帯に含まれているか
		return timezone.contains(time, checkTarget);

	}

	/**
	 * 休憩時間帯を取得する
	 * @param require require
	 * @return 休憩時間
	 */
	public Optional<BreakTimeZone> getBreakTimeZone(Require require) {

		// 勤務種類を取得する
		val workType = require.getWorkType( this.workTypeCode.v() );
		if ( !workType.isPresent() ) {
			// 勤務種類が取得できない
			return Optional.empty();
		}

		// 勤務設定を取得する
		val workSetting = this.getWorkSetting(require);
		if ( !workSetting.isPresent() ) {
			// 勤務設定が取得できない
			return Optional.empty();
		}

		// 出勤日区分を取得する
		val atdDayAtr = workType.get().chechAttendanceDay();
		if ( atdDayAtr.isHoliday() ) {
			// 出勤日区分が休日
			return Optional.empty();
		}

		return Optional.of( workSetting.get().getBreakTimeZone( atdDayAtr.isHolidayWork(), atdDayAtr.toAmPmAtr().get() ) );

	}


	/**
	 * 就業時間帯の勤務設定を取得する
	 * @param require require
	 * @return 勤務設定
	 */
	private Optional<WorkSetting> getWorkSetting(Require require) {

		// 就業時間帯コードの有無
		if ( !this.workTimeCode.isPresent() ) {
			// 就業時間帯コードなし
			return Optional.empty();
		}

		// 就業時間帯を取得する
		val workTimeSetting = require.getWorkTime( this.workTimeCode.get().v() );
		if ( !workTimeSetting.isPresent() ) {
			// 就業時間帯が取得できない
			return Optional.empty();
		}

		return Optional.of( workTimeSetting.get().getWorkSetting(require) );

	}



	public static interface Require
		extends	WorkTimeSetting.Require
			,	WorkSetting.Require
	{

		/**
		 * 勤務種類を取得する
		 * @param workTypeCd 就業時間帯コード
		 * @return
		 */
		Optional<WorkType> getWorkType(String workTypeCd);

		/**
		 * 就業時間帯を取得する
		 * @param workTimeCode 就業時間帯コード
		 * @return 就業時間帯の設定
		 */
		Optional<WorkTimeSetting> getWorkTime(String workTimeCode);

		/**
		 * 就業時間帯が必須か
		 * @param workTypeCode 勤務種類コード
		 * @return
		 */
		SetupType checkNeededOfWorkTimeSetting(String workTypeCode);

		/**
		 * 所定時間帯を取得する - WorkTimeSettingService
		 * @param workTypeCd 勤務種類コード
		 * @param workTimeCd 就業時間帯コード
		 * @param workNo 勤務NO
		 * @return 計算用所定時間設定
		 */
		PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo);

	}

}
