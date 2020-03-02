package nts.uk.ctx.at.shared.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 勤務情報
 * 
 * @author ken_takasu
 *
 */
public class WorkInformation {

	private WorkTypeCode workTypeCode;
	private WorkTimeCode workTimeCode;

	public WorkInformation(String workTimeCode, String workTypeCode) {

		this.workTimeCode = StringUtils.isEmpty(workTimeCode) ? null : new WorkTimeCode(workTimeCode);
		this.workTypeCode = workTypeCode == null ? null : new WorkTypeCode(workTypeCode);
	}

	public WorkInformation(WorkTimeCode workTimeCode, WorkTypeCode workTypeCode) {
		this.workTimeCode = workTimeCode;
		this.workTypeCode = workTypeCode;
	}

	public WorkTimeCode getWorkTimeCode() {
		return this.workTimeCode;
	}

	public WorkTypeCode getWorkTypeCode() {
		return this.workTypeCode;
	}

	public void removeWorkTimeInHolydayWorkType() {
		this.workTimeCode = null;
	}

	public void setSiftCode(WorkTimeCode siftCode) {
		this.workTimeCode = siftCode;
	}

	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	/**
	 * [1] 正常な状態か
	 * 
	 * @param require
	 * @return
	 */
	public boolean checkNormalCondition(Require require) {
		//return [2] エラー状態をチェックする(require) == 勤務情報のエラー状態.正常	
		return checkErrorCondition(require) == ErrorStatusWorkInfo.NORMAL;
	}

	/**
	 * [2] エラー状態をチェックする
	 * 
	 * @param require
	 */
	public ErrorStatusWorkInfo checkErrorCondition(Require require) {
		//	$勤務種類 = require.勤務種類を取得する(@勤務種類コード)  - CID sẽ dc truyền trên app
		Optional<WorkType> workType = require.findByPK(this.workTypeCode.v()); 

		// if $勤務種類.isEmpty
		if (!workType.isPresent()) {
			return ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE;
		}

		// require.勤務種類を取得する(@勤務種類コード)
		SetupType setupType = require.checkNeededOfWorkTimeSetting(this.workTypeCode.v());

		switch (setupType) {
		case REQUIRED:// 必須
			// @就業時間帯コード ==null
			if (this.getWorkTimeCode() == null) {
				return ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET;
			}
			break;
		case OPTIONAL:
			// @就業時間帯コード ==null
			if (this.getWorkTimeCode() == null) {
				return ErrorStatusWorkInfo.NORMAL;
			}
			break;
		default:
			// @就業時間帯コード ==null
			if (this.getWorkTimeCode() == null) {
				return ErrorStatusWorkInfo.NORMAL;
			}
			// @就業時間帯コード.isPresent
			return ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY;

		}

		// require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) - CID sẽ dc truyền trên app
		Optional<WorkTimeSetting> workTimeSetting = require
				.findByCode(this.workTimeCode == null ? null : this.workTimeCode.v());
		// if $就業時間帯.isEmpty
		if (!workTimeSetting.isPresent()) {
			return ErrorStatusWorkInfo.WORKTIME_WAS_DELETE;
		}

		return ErrorStatusWorkInfo.NORMAL;
	}
	
	/**
	 * [3] 出勤・休日系の判定
	 * 
	 * @return AttendanceHolidayAttr 出勤休日区分
	 */
	public AttendanceHolidayAttr getAttendanceHolidayAttr(Require require) {
		Optional<WorkType> workType = require.findByPK(this.workTypeCode.v());
		return workType.get().getAttendanceHolidayAttr();
	}
	
	/**
	 * [4] 勤務情報と補正済み所定時間帯を取得する
	 * 
	 * @param require
	 */
	public Optional<WorkInfoAndTimeZone> getWorkInfoAndTimeZone(Require require) {
		
		//	$勤務種類 = require.勤務種類を取得する( @勤務種類コード )
		Optional<WorkType> workType = require.findByPK(this.workTypeCode.v());
		if (!workType.isPresent()) {
			return Optional.empty();
		}
		//@就業時間帯コード.isEmpty()
		if (this.getWorkTimeCode() == null) {
			return Optional.of(new WorkInfoAndTimeZone(workType.get()));
		}
		//$就業時間帯の設定 = require.就業時間帯を取得する(@就業時間帯コード )
		Optional<WorkTimeSetting> workTimeSetting = require
				.findByCode(this.workTimeCode.v());
		if (!workTimeSetting.isPresent()) {
			return Optional.empty();
		}
		
		List<TimezoneUse> listTimezoneUse = new ArrayList<>();
		//	$就業時間帯の設定.所定時間帯を取得する( $就業時間帯の設定.会社ID, @勤務種類コード, Optional.empty )		
		listTimezoneUse = require.getPredeterminedTimezone(this.workTimeCode.v(), this.workTypeCode.v(), null).getTimezones();
		//filter $.使用区分 == するしない区分．使用する
		//	sort $.勤務NO ASC	
		listTimezoneUse.stream().filter(item -> item.isUsed()).sorted((x, y) -> x.getWorkNo() - y.getWorkNo()).collect(Collectors.toList());
		//map 時間帯#時間帯を作る( $.開始, $.終了 )
		//List<TimeZone> listTimeZone =  listTimezoneUse.stream().map(i->new TimeZone(i.getStart(),i.getEnd())).collect(Collectors.toList());
				
		return Optional.of(new WorkInfoAndTimeZone(workType.get(),workTimeSetting.get(),listTimezoneUse));
	}
	
	

	public static interface Require {

		/**
		 * [R-1] 勤務種類を取得する
		 * 
		 * @param workTypeCd
		 * @return
		 */
		Optional<WorkType> findByPK(String workTypeCd);

		/**
		 * [R-2] 就業時間帯を取得する ( get 就業時間帯の設定)
		 * 
		 * @param companyId
		 * @param workTimeCode
		 * @return
		 */
		Optional<WorkTimeSetting> findByCode(String workTimeCode);

		/**
		 * [R-3] 就業時間帯が必須か 就業時間帯の必須チェック
		 * 
		 * @param workTypeCode
		 * @return
		 */
		SetupType checkNeededOfWorkTimeSetting(String workTypeCode);
		
		/**
		 * 所定時間帯を取得する   - WorkTimeSettingService
		 * @param companyId
		 * @param workTimeCd
		 * @param workTypeCd
		 * @param workNo
		 * @return 
		 */
		PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
				Integer workNo);
	}

}
