package nts.uk.ctx.at.shared.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

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
		// return [2] エラー状態をチェックする(require) == 勤務情報のエラー状態.正常
		return checkErrorCondition(require) == ErrorStatusWorkInfo.NORMAL;
	}

	/**
	 * エラー状態をチェックする
	 * @param require require
	 */
	public ErrorStatusWorkInfo checkErrorCondition(Require require) {
		// $勤務種類 = require.勤務種類を取得する(@勤務種類コード) - CID sẽ dc truyền trên app
		Optional<WorkType> workType = require.getWorkType( this.workTypeCode.v() );

		// if $勤務種類.isEmpty
		if (!workType.isPresent()) {
			return ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE;
		}

		if(workType.get().getDeprecate() == DeprecateClassification.Deprecated) {
			return ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED;
		}

		// require.勤務種類を取得する(@勤務種類コード)
		SetupType setupType = require.checkNeededOfWorkTimeSetting(this.workTypeCode.v());

		switch (setupType) {
		case REQUIRED:// 必須
			// @就業時間帯コード ==null
			if (!this.workTimeCode.isPresent()) {
				return ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET;
			}
			break;
		case OPTIONAL:// 任意
			// @就業時間帯コード ==null
			if (!this.workTimeCode.isPresent() ) {
				return ErrorStatusWorkInfo.NORMAL;
			}
			break;
		default: // 不要
			// @就業時間帯コード.isPresent
			if (!this.workTimeCode.isPresent() ) {
				return ErrorStatusWorkInfo.NORMAL;
			}
			return ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY;

		}

		// require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) - CID sẽ dc truyền trên app
		Optional<WorkTimeSetting> workTimeSetting = this.workTimeCode.flatMap(c -> require.getWorkTime(c.v()));
		// if $就業時間帯.isEmpty
		if (!workTimeSetting.isPresent()) {
			return ErrorStatusWorkInfo.WORKTIME_WAS_DELETE;
		}
		if(workTimeSetting.get().getAbolishAtr() == AbolishAtr.ABOLISH ) {
			return ErrorStatusWorkInfo.WORKTIME_HAS_BEEN_ABOLISHED;
		}

		return ErrorStatusWorkInfo.NORMAL;
	}

	/**
	 * 出勤・休日系の判定
	 * @return WorkStyle 出勤休日区分
	 */
	public Optional<WorkStyle> getWorkStyle(Require require) {
		WorkStyle workStyle = require.checkWorkDay(this.workTypeCode == null ? null : this.workTypeCode.v());
		if (workStyle == null) {
			return Optional.empty();
		}
		return Optional.of(workStyle);
	}

	/**
	 * 勤務情報と補正済み所定時間帯を取得する
	 * @param require require
	 */
	public Optional<WorkInfoAndTimeZone> getWorkInfoAndTimeZone(Require require) {

		// $勤務種類 = require.勤務種類を取得する( @勤務種類コード )
		Optional<WorkType> workType = require.getWorkType( this.workTypeCode.v() );
		if (!workType.isPresent()) {
			return Optional.empty();
		}
		// @就業時間帯コード.isEmpty()
		if (!this.workTimeCode.isPresent()) {
			return Optional.of(WorkInfoAndTimeZone.createWithoutWorkTime( workType.get() ));
		}
		// $就業時間帯の設定 = require.就業時間帯を取得する(@就業時間帯コード )
		Optional<WorkTimeSetting> workTimeSetting = require.getWorkTime( this.workTimeCode.get().v() );
		if (!workTimeSetting.isPresent()) {
			return Optional.empty();
		}

		List<TimezoneUse> listTimezoneUse = new ArrayList<>();
		// $就業時間帯の設定.所定時間帯を取得する( $就業時間帯の設定.会社ID, @勤務種類コード, Optional.empty )
		listTimezoneUse = require.getPredeterminedTimezone(this.workTypeCode.v(), this.workTimeCode.get().v(), null)
				.getTimezones();
		// filter $.使用区分 == するしない区分．使用する
		// sort $.勤務NO ASC
		listTimezoneUse = listTimezoneUse.stream().filter(item -> item.isUsed()).sorted((x, y) -> x.getWorkNo() - y.getWorkNo())
				.collect(Collectors.toList());
		// map 時間帯#時間帯を作る( $.開始, $.終了 )
		List<TimeZone> listTimeZone = listTimezoneUse.stream().map(i -> new TimeZone(i.getStart(), i.getEnd()))
				.collect(Collectors.toList());

		return Optional.of( WorkInfoAndTimeZone.create( workType.get(), workTimeSetting.get(), listTimeZone ));
	}

	public static interface Require {

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

		/**
		 * 1日半日出勤・1日休日系の判定
		 *
		 * @param workTypeCode
		 * @return
		 */
		WorkStyle checkWorkDay(String workTypeCode);

	}

}
