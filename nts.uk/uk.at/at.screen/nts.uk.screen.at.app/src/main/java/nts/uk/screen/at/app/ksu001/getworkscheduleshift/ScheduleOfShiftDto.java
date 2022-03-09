package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.GetSupportInfoOfEmployee;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.DeterEditStatusShiftService;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.ShiftEditState;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.employeeworkway.EmployeeWorkingStatus;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportStatus;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;


/**
 * @author laitv 
 * 勤務予定（シフト）dto
 */
@Data
@Builder
@AllArgsConstructor
public class ScheduleOfShiftDto {

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
	// シフトコード
	public String shiftCode;
	// シフト名称
	public String shiftName;
	// シフトの編集状態
	public ShiftEditStateDto shiftEditState;
	// 出勤休日区分
	public Integer workHolidayCls;

	// để check điều kiện ※Aa1
	public boolean isEdit;

	// để check điều kiện ※Aa2
	public boolean isActive;

	// chưa bao gồm điều kiện 対象の日 < A画面パラメータ. 修正可能開始日 の場合 => check dưới UI
	public boolean conditionAa1;
	public boolean conditionAa2;
	/**
	DO_NOT_GO( 0 ), 応援に行かない 
	DO_NOT_COME( 1 ), 応援に来ない 
	GO_ALLDAY( 2 ), 応援に行く(終日) 
	GO_TIMEZONE( 3 ), 応援に行く(時間帯)
	COME_ALLDAY( 4 ), 応援に来る(終日)
	COME_TIMEZONE( 5 ), 応援に来る(時間帯)
	 */
	public Integer supportStatus;

	// <<constructor>> 応援予定で作成する (
	// 社員の就業状態: 社員の就業状態,
	// 勤務予定: 勤務予定,
	// 対象組織: 対象組織識別情報): 勤務予定（シフト）dto
	public ScheduleOfShiftDto(EmployeeWorkingStatus employeeWorkingStatus, 
			Optional<WorkSchedule> workScheduleInput,
			List<ShiftMasterMapWithWorkStyle> listShiftMaster,
			TargetOrgIdenInfor targetOrg, 
			WorkInformation.Require require,
			GetSupportInfoOfEmployee.Require requireGetSupportInfo) {
		super();
		// step 1 勤務予定が必要か()
		boolean needCreateWorkSchedule = employeeWorkingStatus.getWorkingStatus().needCreateWorkSchedule();
		if (needCreateWorkSchedule && workScheduleInput.isPresent()) {
			// step3.1 勤務予定.勤務情報
			WorkSchedule workSchedule = workScheduleInput.get();
			WorkInformation workInformation = workSchedule.getWorkInfo().getRecordInfo();
			
			Optional<WorkStyle> workStyle = Optional.empty();
			if (workInformation.getWorkTypeCode() != null) {
				workStyle = workInformation.getWorkStyle(require);
			}

			// step 3.2  シフトの編集状態を判断する
			ShiftEditState shiftEditState = DeterEditStatusShiftService.toDecide(workSchedule);
			ShiftEditStateDto shiftEditStateDto = ShiftEditStateDto.toDto(shiftEditState);

			String workTypeCode = workInformation.getWorkTypeCode() == null ? null: workInformation.getWorkTypeCode().toString().toString();
			String workTimeCode = workInformation.getWorkTimeCode() == null ? null: workInformation.getWorkTimeCode().toString().toString();

			Optional<ShiftMasterMapWithWorkStyle> shiftMaster = listShiftMaster.stream().filter(x -> {
				boolean s = Objects.equals(x.workTypeCode, workTypeCode);
				boolean y = Objects.equals(x.workTimeCode, workTimeCode);
				return s && y;
			}).findFirst();

			if (!shiftMaster.isPresent()) {
				System.out.println("WorkType - workTime chưa được đăng ký: " + workTypeCode + " - " + workTimeCode);
			}
			
			// step3.3 call DomainService 社員の応援情報を取得する
			// 実績の情報を取得する(Require, 社員ID, 年月日)
			SupportInfoOfEmployee supportInfoOfEmp = GetSupportInfoOfEmployee.getScheduleInfo(requireGetSupportInfo, new EmployeeId(employeeId), date);
			
			// step3.3.1 応援状況を取得する(対象組織識別情報)
			this.supportStatus = supportInfoOfEmp.getSupportStatus(targetOrg).getValue();

			// step 3.4  create
			this.employeeId = employeeWorkingStatus.getEmployeeID();
			this.date = employeeWorkingStatus.getDate();
			this.haveData = true;
			this.achievements = false;
			this.confirmed = workSchedule.getConfirmedATR().value == ConfirmedATR.CONFIRMED.value;
			this.needToWork = needCreateWorkSchedule;
			this.shiftCode = shiftMaster.isPresent() ? shiftMaster.get().shiftMasterCode : null;
			this.shiftName = shiftMaster.isPresent() ? shiftMaster.get().shiftMasterName : null;
			this.shiftEditState = shiftEditStateDto;
			this.workHolidayCls = workStyle.isPresent() ? workStyle.get().value : null;
			this.isEdit = true;
			this.isActive = true;
			this.conditionAa1 = true;
			this.conditionAa2 = true;
			
			/**※Aa1
			勤務予定（シフト）dto．実績か == true	           achievements	            ×	
			勤務予定（シフト）dto．確定済みか == true          confirmed		            ×	
			勤務予定（シフト）dto．勤務予定が必要か == false	   needToWork		        ×	
			勤務予定（シフト）dto．応援状況 == 応援に来ない　or　応援に来る(時間帯)supportStatus	×	
			対象の日 < A画面パラメータ. 修正可能開始日　の場合    targetDate		            ×	=> check dưới UI
			上記以外									   other                    ○	
			*/
			if (       this.achievements == true 
					|| this.confirmed == true 
					|| this.needToWork == false 
					|| this.supportStatus == SupportStatus.DO_NOT_COME.getValue() 
					|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue() ) 
			{	
				this.setEdit(false);
				this.setConditionAa1(false);
			}

			/**
			 * ※Aa2			シフト確定																					
			勤務予定（シフト）dto．実績か == true                                    achievements	×	
			勤務予定（シフト）dto．勤務予定が必要か == false                           needToWork	 	×	
			勤務予定（勤務情報）dto．応援状況 == 応援に来る(時間帯)　or　応援に行く(終日)  supportStatus		×	
			対象の日 < A画面パラメータ. 修正可能開始日　の場合	                      targetDate		×	=> check dưới UI
			上記以外										                      other			○	
			 */
			if (  this.achievements == true || this.needToWork == false
				|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue()
				|| this.supportStatus == SupportStatus.GO_ALLDAY.getValue()) 
			{	this.setActive(false);
				this.setConditionAa2(false);
			}
			
		} else {
			// step2 create
			this.employeeId = employeeWorkingStatus.getEmployeeID();
			this.date = employeeWorkingStatus.getDate();
			this.haveData = false;
			this.achievements = false;
			this.confirmed = false;
			this.needToWork = false;
			this.shiftCode = null;
			this.shiftName = null;
			this.shiftEditState = null;
			this.workHolidayCls = null;
			this.isEdit = true;
			this.isActive = true;
			this.conditionAa1 = true;
			this.conditionAa2 = true;
			this.supportStatus = SupportStatus.DO_NOT_COME.getValue();
			
			// ※Aa1
			if (this.achievements == true 
					|| this.confirmed == true 
					|| this.needToWork == false
					|| this.supportStatus == SupportStatus.DO_NOT_COME.getValue() 
					|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue()) {
				this.setEdit(false);
				this.setConditionAa1(false);
			}
			// ※Aa2
			if (      this.achievements == true 
					|| this.needToWork == false
					|| this.supportStatus == SupportStatus.COME_TIMEZONE.getValue()
					|| this.supportStatus == SupportStatus.GO_ALLDAY.getValue()) 
			{
				this.setActive(false);
				this.setConditionAa2(false);
			}
		}
	}

	// <<constructor>> 応援実績で作成する (
	// 社員の就業状態: 社員の就業状態,
	// Optional<日別勤怠>: 日別勤怠(Work),
	// 対象組織: 対象組織識別情報): 勤務予定（シフト）dto
	public ScheduleOfShiftDto(
			Optional<IntegrationOfDaily> workRecord, 
			EmployeeWorkingStatus employeeWorkingStatus,
			List<ShiftMasterMapWithWorkStyle> listShiftMaster, 
			TargetOrgIdenInfor targetOrg,
			GetSupportInfoOfEmployee.Require requireGetSupportInfo) {
		super();
		// step1 勤務予定が必要か()
		boolean needCreateWorkSchedule = employeeWorkingStatus.getWorkingStatus().needCreateWorkSchedule();
		// step3 勤務予定が必要か == true && 日別勤怠.isPresent
		if (needCreateWorkSchedule && workRecord.isPresent()) {
			if (workRecord.get().getWorkInformation() != null) {
				WorkInformation workInformation = workRecord.get().getWorkInformation().getRecordInfo();

				String workTypeCode = workInformation.getWorkTypeCode() == null ? null: workInformation.getWorkTypeCode().toString().toString();
				String workTimeCode = workInformation.getWorkTimeCode() == null ? null: workInformation.getWorkTimeCode().toString().toString();

				Optional<ShiftMasterMapWithWorkStyle> shiftMaster = listShiftMaster.stream().filter(x -> {
					boolean s = Objects.equals(x.workTypeCode, workTypeCode);
					boolean y = Objects.equals(x.workTimeCode, workTimeCode);
					return s && y;
				}).findFirst();

				if (!shiftMaster.isPresent()) {
					System.out.println("WorkType - workTime chưa được đăng ký: " + workTypeCode + " - " + workTimeCode);
				}
				
				// step2 call DomainService 社員の応援情報を取得する
				// 実績の情報を取得する(Require, 社員ID, 年月日)
				SupportInfoOfEmployee supportInfoOfEmp = GetSupportInfoOfEmployee.getRecordInfo(requireGetSupportInfo, new EmployeeId(employeeId), date);
				
				// step2.1 応援状況を取得する(対象組織識別情報)
				this.supportStatus = supportInfoOfEmp.getSupportStatus(targetOrg).getValue();

				this.employeeId = employeeWorkingStatus.getEmployeeID();
				this.date = employeeWorkingStatus.getDate();
				this.haveData = true;
				this.achievements = true;
				this.confirmed = true;
				this.needToWork = true;
				this.shiftCode = shiftMaster.isPresent() ? shiftMaster.get().shiftMasterCode : null;
				this.shiftName = shiftMaster.isPresent() ? shiftMaster.get().shiftMasterName : null;
				this.shiftEditState = null;
				this.workHolidayCls = null;
				this.isEdit = false;
				this.isActive = false;
				this.conditionAa1 = false;
				this.conditionAa2 = false;
			}
		}
	}
}
