package nts.uk.screen.at.app.ksu001.processcommon;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.employeeworkway.EmployeeWorkingStatus;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkTypeWorkTimeUseDailyAttendanceRecord;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportStatus;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.screen.at.app.ksu001.displayinworkinformation.EditStateOfDailyAttdDto;
import nts.uk.screen.at.app.ksu001.start.SupportCategory;

/**
 * @author laitv
 * 勤務予定（勤務情報）dto
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkScheduleWorkInforDto {
	
	// 社員ID
	public String employeeId;
	// 年月日
	public GeneralDate date;
	// データがあるか
	public boolean haveData;
	// 実績か
	public boolean achievements;
	// 確定済みか
	public boolean confirmed;
	// 勤務予定が必要か
	public boolean needToWork;
	// 応援か
	public Integer supportCategory; // tu 1-> 5
	
	//Khu vực Optional
	// 勤務種類コード
	public String workTypeCode;
	// 勤務種類名 tên viết tắt
	public String workTypeName;
	// 勤務種類編集状態
	public EditStateOfDailyAttdDto workTypeEditStatus;
	// 就業時間帯コード
	public String workTimeCode;
	// 就業時間帯名
	public String workTimeName;
	// 就業時間帯編集状態
	public EditStateOfDailyAttdDto workTimeEditStatus;
	// 開始時刻
	public Integer startTime;
	// 開始時刻編集状態
	public EditStateOfDailyAttdDto startTimeEditState;
	// 終了時刻
	public Integer endTime;
	// 終了時刻編集状態
	public EditStateOfDailyAttdDto endTimeEditState;
	// 出勤休日区分
	public Integer workHolidayCls;
	// dùng cho màn ksu001
	public boolean workTypeIsNotExit;
	// dùng cho màn ksu001
	public boolean workTimeIsNotExit;
	
	// dùng cho màn ksu002
	public String workTypeNameKsu002;
	
	// dùng cho màn ksu002
	public String workTimeNameKsu002;
	
	// chưa bao gồm điều kiện 対象の日 < A画面パラメータ. 修正可能開始日　の場合 => check dưới UI
	public boolean conditionAbc1; 
	public boolean conditionAbc2;
	
	/**固定勤務
	FIXED(0, "固定勤務"),
	FLEX(1, "フレックス勤務"),
	FLOW(2,"流動勤務"), */
	
	//就業時間帯の勤務形態 
	public Integer workTimeForm;
	/**
	DO_NOT_GO( 0 ), 応援に行かない 
	DO_NOT_COME( 1 ), 応援に来ない 
	GO_ALLDAY( 2 ), 応援に行く(終日) 
	GO_TIMEZONE( 3 ), 応援に行く(時間帯)
	COME_ALLDAY( 4 ), 応援に来る(終日)
	COME_TIMEZONE( 5 ), 応援に来る(時間帯)
	 */
	public Integer supportStatus;

	// Create work schedule
	// <<constructor>> 勤務予定で作成する (
	// 社員の就業状態: 社員の就業状態 : EmployeeWorkingStatus
	// 勤務予定: 勤務予定 : WorkSchedule 
	// 勤務種類: 勤務種類:  List<WorkTypeInfor> lstWorkTypeInfor
	// 就業時間帯の設定: 就業時間帯の設定: List<WorkTimeSetting> lstWorkTimeSetting
	// 対象組織: 対象組織識別情報:  TargetOrgIdenInfor targetOrg
	// 日別勤怠の実績で利用する勤務種類と就業時間帯: 日別勤怠の実績で利用する勤務種類と就業時間帯: WorkTypeWorkTimeUseDailyAttendanceRecord
	// ): 勤務予定（勤務情報）dto
	public WorkScheduleWorkInforDto(
			EmployeeWorkingStatus empWorkingStatus,
			Optional<WorkSchedule> workScheduleInput,
			Optional<WorkTypeInfor> workTypeInfor,
			Optional<WorkTimeSetting> workTimeSetting,
			TargetOrgIdenInfor targetOrg,
			WorkTypeWorkTimeUseDailyAttendanceRecord wTypeWTimeUseDailyAttendRecord,
			WorkInformation.Require require) {
		super();
		// step 1 : 勤務予定が必要か()
		boolean needCreateWorkSchedule = empWorkingStatus.getWorkingStatus().needCreateWorkSchedule();
		if (!needCreateWorkSchedule) {
			// step 2 : 勤務予定が必要か() == false
			this.employeeId = empWorkingStatus.getEmployeeID();
			this.date = empWorkingStatus.getDate();
			this.haveData = false;
			this.achievements = false;
			this.confirmed = false;
			this.needToWork = false;
			this.supportCategory = SupportCategory.NotCheering.value;
			this.workTypeCode = null;
			this.workTypeName = null;
			this.workTypeEditStatus = null;
			this.workTimeCode = null;
			this.workTimeName = null;
			this.workTimeEditStatus = null;
			this.startTime = null;
			this.startTimeEditState = null;
			this.endTime = null;
			this.endTimeEditState = null;
			this.workHolidayCls = null;
			this.workTimeForm = null;
			this.conditionAbc1 = true;
			this.conditionAbc2 = true;
			this.supportStatus = SupportStatus.DO_NOT_COME.getValue();

			/*※Abc1
			勤務予定（勤務情報）dto．実績か == true	Achievement						×	
			勤務予定（勤務情報）dto．確定済みか == true Confirmed						×	
			勤務予定（勤務情報）dto．勤務予定が必要か == false need a work				×	
			勤務予定（勤務情報）dto．応援状況 == 応援に来る(時間帯)	 supportStatus		× 
			対象の日 < A画面パラメータ. 修正可能開始日　の場合 Target date				× => check ở dưới UI	
			上記以外																○	
			*/
			if (this.achievements == true 
					|| this.confirmed == true 
					|| this.needToWork == false
					|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue()) {
				this.conditionAbc1 = false;
			}

			/* ※Abc2
			 勤務予定（勤務情報）dto．実績か == true	Achievement						           ×	
			勤務予定（勤務情報）dto．勤務予定が必要か == false	need a work				           ×	
			勤務予定（勤務情報）dto．応援状況 == 応援に来る(時間帯)　or　応援に行く(終日)	 supportStatus ×	
			対象の日 < A画面パラメータ. 修正可能開始日　の場合 Target date				           × => check ở dưới UI
			上記以外																           ○	
			 */
			if (this.achievements == true 
					|| this.needToWork == false
					|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue()
					|| this.supportStatus == SupportStatus.GO_ALLDAY.getValue()) {
				this.conditionAbc2 = false;
			}
			
		} else {
			WorkSchedule workSchedule = workScheduleInput.get();
			WorkInformation workInformation = workSchedule.getWorkInfo().getRecordInfo();

			// step 3.1: 出勤・休日系の判定(@Require)
			Optional<WorkStyle> workStyle = Optional.empty();
			if (workInformation.getWorkTypeCode() != null) {
				workStyle = workInformation.getWorkStyle(require); // workHolidayCls
			}

			String workTypeCode = workInformation.getWorkTypeCode() == null ? null : workInformation.getWorkTypeCode().toString();
			String workTypeName = null;
			boolean workTypeIsNotExit  = false;

			if (workTypeInfor.isPresent()) {
				workTypeName = workTypeInfor.get().getAbbreviationName();
			} else if (!workTypeInfor.isPresent() && workTypeCode != null){
				workTypeIsNotExit = true;
			}

			String workTimeCode = workInformation.getWorkTimeCode() == null  ? null : workInformation.getWorkTimeCode().toString();
			String workTimeName = null;
			boolean workTimeIsNotExit  = false;
			if (workTimeSetting.isPresent()) {
				if (workTimeSetting.get().getWorkTimeDisplayName() != null && workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName() != null ) {
					workTimeName = workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName().toString();
				}
			} else  if (!workTimeSetting.isPresent() && workTimeCode != null){
				workTimeIsNotExit = true;
			}

			Integer startTime = null;
			Integer endtTime = null;

			if (workTimeCode != null) {
				if (workSchedule.getOptTimeLeaving().isPresent()) {
					Optional<TimeLeavingWork> timeLeavingWork = workSchedule.getOptTimeLeaving().get().getTimeLeavingWorks().stream().filter(i -> i.getWorkNo().v() == 1).findFirst();
					if (timeLeavingWork.isPresent()) {
						if(timeLeavingWork.get().getAttendanceStamp().isPresent()){
							if(timeLeavingWork.get().getAttendanceStamp().get().getStamp().isPresent()){
								if(timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay() != null){
									if(timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()){
										startTime = timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v();
									}
								}
							}
						}
					}
				}

				if (workSchedule.getOptTimeLeaving().isPresent()) {
					Optional<TimeLeavingWork> timeLeavingWork = workSchedule.getOptTimeLeaving().get().getTimeLeavingWorks().stream().filter(i -> i.getWorkNo().v() == 1).findFirst();
					if (timeLeavingWork.isPresent()) {
						if(timeLeavingWork.get().getLeaveStamp().isPresent()){
							if(timeLeavingWork.get().getLeaveStamp().get().getStamp().isPresent()){
								if(timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay() != null){
									if(timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()){
										endtTime = timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v();
									}
								}
							}
						}
					}
				}
			}

			Optional<EditStateOfDailyAttd> workTypeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 28).findFirst();
			Optional<EditStateOfDailyAttd> workTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 29).findFirst();
			Optional<EditStateOfDailyAttd> startTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 31).findFirst();
			Optional<EditStateOfDailyAttd> endTimeEditStatus = workSchedule.getLstEditState().stream().filter(i -> i.getAttendanceItemId() == 34).findFirst();

					this.employeeId = empWorkingStatus.getEmployeeID();
					this.date = empWorkingStatus.getDate();
					this.haveData = true;
					this.achievements = false;
					this.confirmed = workSchedule.getConfirmedATR().value == ConfirmedATR.CONFIRMED.value;
					this.needToWork = true;
					this.supportCategory = SupportCategory.NotCheering.value;
					this.workTypeCode = workTypeCode;
					this.workTypeName = workTypeName;
					this.workTypeEditStatus = workTypeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(workTypeEditStatus.get().getAttendanceItemId(), workTypeEditStatus.get().getEditStateSetting().value) : null;
					this.workTimeCode = workTimeCode;
					this.workTimeName = workTimeName;
					this.workTimeEditStatus = workTimeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(workTimeEditStatus.get().getAttendanceItemId(), workTimeEditStatus.get().getEditStateSetting().value) : null;
					this.startTime = startTime;
					this.startTimeEditState = startTimeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(startTimeEditStatus.get().getAttendanceItemId(), startTimeEditStatus.get().getEditStateSetting().value) : null;
					this.endTime = endtTime;
					this.endTimeEditState = endTimeEditStatus.isPresent() ? new EditStateOfDailyAttdDto(endTimeEditStatus.get().getAttendanceItemId(), endTimeEditStatus.get().getEditStateSetting().value) : null;
					this.workHolidayCls = workStyle.isPresent() ? workStyle.get().value : null;
					this.workTypeIsNotExit = workTypeIsNotExit;
					this.workTimeIsNotExit = workTimeIsNotExit;
					this.workTypeNameKsu002 = workTypeInfor.map(m -> m.getAbbreviationName()).orElse(workTypeCode == null ? null : workTypeCode + "{#KSU002_31}");
					this.workTimeNameKsu002 = workTimeSetting.map(m -> m.getWorkTimeDisplayName().getWorkTimeAbName().v()).orElse(workTimeCode == null ? null : workTimeCode + "{#KSU002_31}");
					this.workTimeForm = workTimeSetting.map(m -> m.getWorkTimeDivision().getWorkTimeForm().value).orElse(null);
					this.conditionAbc1 = true;
					this.conditionAbc2 = true;
					this.supportStatus = SupportStatus.DO_NOT_GO.getValue();

			/*※Abc1
			勤務予定（勤務情報）dto．実績か == true	Achievement						×	
			勤務予定（勤務情報）dto．確定済みか == true Confirmed					    ×	
			勤務予定（勤務情報）dto．勤務予定が必要か == false need a work				×	
			勤務予定（勤務情報）dto．応援か == 時間帯応援先	 supportStatus				× 
			対象の日 < A画面パラメータ. 修正可能開始日　の場合 Target date				×	=> check ở dưới UI
			上記以外															    ○	
			*/
			if (this.achievements == true 
					|| this.confirmed == true 
					|| this.needToWork == false
					|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue()) {
				this.conditionAbc1 = false;
			}

			/* ※Abc2
			 勤務予定（勤務情報）dto．実績か == true	Achievement						           ×	
			勤務予定（勤務情報）dto．勤務予定が必要か == false	need a work				           ×	
			勤務予定（勤務情報）dto．応援状況 == 応援に来る(時間帯)　or　応援に行く(終日)	 supportStatus ×	
			対象の日 < A画面パラメータ. 修正可能開始日　の場合 Target date				           × => check ở dưới UI
			上記以外																           ○	
			 */
			if (this.achievements == true 
					|| this.needToWork == false
					|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue()
					|| this.supportStatus == SupportStatus.GO_ALLDAY.getValue()) {
				this.conditionAbc2 = false;
			}
		}
	}
	
	// create WorkRecord
	//  <<constructor>> 勤務実績で作成する (
	// 社員の就業状態: 社員の就業状態, 
	// 日別実績: 日別勤怠, 
	// 勤務種類: 勤務種類, 
	// 就業時間帯の設定: 就業時間帯の設定, 
	// 対象組織: 対象組織識別情報, 
	// 日別勤怠の実績で利用する勤務種類と就業時間帯: 日別勤怠の実績で利用する勤務種類と就業時間帯
	//): 勤務予定（勤務情報）dto
	public WorkScheduleWorkInforDto(
			Optional<IntegrationOfDaily> workRecord,
			EmployeeWorkingStatus empWorkingStatus,
			Optional<WorkTypeInfor> workTypeInfor,
			Optional<WorkTimeSetting> workTimeSetting,
			TargetOrgIdenInfor targetOrg,
			WorkTypeWorkTimeUseDailyAttendanceRecord wTypeWTimeUseDailyAttendRecord) {
		super();
		// step 1 check 社員の就業状態.就業状態
		boolean needCreateWorkSchedule = empWorkingStatus.getWorkingStatus().needCreateWorkSchedule();
		if (needCreateWorkSchedule) {
			// step 4.2
			IntegrationOfDaily daily = workRecord.get();
			if (daily.getWorkInformation() != null) {
				WorkInformation workInformation = daily.getWorkInformation().getRecordInfo();

				String workTypeCode = workInformation.getWorkTypeCode() == null ? null : workInformation.getWorkTypeCode().toString();
				String workTypeName = null;
				boolean workTypeIsNotExit  = false;

				if (workTypeInfor.isPresent()) {
					workTypeName = workTypeInfor.get().getAbbreviationName();
				} else if (!workTypeInfor.isPresent() && workTypeCode != null){
					workTypeIsNotExit = true;
				}

				String workTimeCode = workInformation.getWorkTimeCode() == null ? null: workInformation.getWorkTimeCode().toString();
				String workTimeName = null;
				boolean workTimeIsNotExit  = false;

				if (workTimeSetting.isPresent()) {
					if (workTimeSetting.get().getWorkTimeDisplayName() != null && workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName() != null) {
						workTimeName = workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeAbName().toString();
					}
				} else if (!workTimeSetting.isPresent() && workTimeCode != null){
					workTimeIsNotExit = true;
				}

				Integer startTime = null;
				if (daily.getAttendanceLeave().isPresent()) {
					Optional<TimeLeavingWork> timeLeavingWork = daily.getAttendanceLeave().get().getTimeLeavingWorks().stream().filter(i -> i.getWorkNo().v() == 1).findFirst();
					if (timeLeavingWork.isPresent()) {
						if (timeLeavingWork.get().getAttendanceStamp().isPresent()) {
							if (timeLeavingWork.get().getAttendanceStamp().get().getStamp().isPresent()) {
								if (timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay() != null) {
									if (timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
										startTime = timeLeavingWork.get().getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v();
									}
								}
							}
						}
					}
				}

				Integer endtTime = null;
				if (daily.getAttendanceLeave().isPresent()) {
					Optional<TimeLeavingWork> timeLeavingWork = daily.getAttendanceLeave().get().getTimeLeavingWorks().stream().filter(i -> i.getWorkNo().v() == 1).findFirst();
					if (timeLeavingWork.isPresent()) {
						if (timeLeavingWork.get().getLeaveStamp().isPresent()) {
							if (timeLeavingWork.get().getLeaveStamp().get().getStamp().isPresent()) {
								if (timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay() != null) {
									if (timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().isPresent()) {
										endtTime = timeLeavingWork.get().getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v();
									}
								}
							}
						}
					}
				}

				this.employeeId = empWorkingStatus.getEmployeeID();
				this.date = empWorkingStatus.getDate();
				this.haveData = true;
				this.achievements = true;
				this.confirmed = true;
				this.needToWork = needCreateWorkSchedule;
				this.supportCategory = SupportCategory.NotCheering.value;
				this.workTypeCode = workTypeCode;
				this.workTypeName = workTypeName;
				this.workTypeEditStatus = null;
				this.workTimeCode = workTimeCode;
				this.workTimeName = workTimeName;
				this.workTimeEditStatus = null;
				this.startTime = startTime;
				this.startTimeEditState = null;
				this.endTime = endtTime;
				this.endTimeEditState = null;
				this.workHolidayCls = null;
				this.workTypeIsNotExit = workTypeIsNotExit;
				this.workTimeIsNotExit = workTimeIsNotExit;
				this.workTypeNameKsu002 = workTypeInfor.map(m -> m.getAbbreviationName()).orElse(workTypeCode == null ? null : workTypeCode + "{#KSU002_31}");
				this.workTimeNameKsu002 = workTimeSetting.map(m -> m.getWorkTimeDisplayName().getWorkTimeAbName().v()).orElse(workTimeCode == null ? null : workTimeCode + "{#KSU002_31}");
				this.workTimeForm = !workTimeSetting.isPresent() ? null : workTimeSetting.get().getWorkTimeDivision().getWorkTimeForm().value;
				this.conditionAbc1 = false;
				this.conditionAbc2 = false;
				this.supportStatus = SupportStatus.DO_NOT_GO.getValue();
			}
		}
	}
}
