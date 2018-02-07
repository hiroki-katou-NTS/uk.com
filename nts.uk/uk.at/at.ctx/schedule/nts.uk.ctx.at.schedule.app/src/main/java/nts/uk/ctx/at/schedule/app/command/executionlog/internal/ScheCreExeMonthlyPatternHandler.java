package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScEmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.commonalgorithm.ScheduleMasterInformationRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * 月間パターンで勤務予定を作成する
 * @author chinhbv
 *
 */
@Stateless
public class ScheCreExeMonthlyPatternHandler {
	@Inject
	private ScheCreExeErrorLogHandler scheCreExeErrorLogHandler;
	@Inject
	private ScheCreExeWorkTimeHandler scheCreExeWorkTimeHandler;
	@Inject
	private ScheCreExeWorkTypeHandler scheCreExeWorkTypeHandler;
	@Inject
	private WorkMonthlySettingRepository workMonthlySettingRepo;
	@Inject
	private BasicScheduleRepository basicScheduleRepo;
	@Inject
	private ScEmploymentStatusAdapter scEmploymentStatusAdapter;
	@Inject
	private ScheCreExeBasicScheduleHandler scheCreExeBasicScheduleHandler;
	
	/**
	 * 月間パターンで勤務予定を作成する
	 * @param command
	 * @param workingConditionItem
	 */
	public void createScheduleWithMonthlyPattern(ScheduleCreatorExecutionCommand command, WorkingConditionItem workingConditionItem) {
		//ドメインモデル「月間勤務就業設定」を取得する
		Optional<WorkMonthlySetting> workMonthlySetOpt = this.workMonthlySettingRepo.findById(command.getCompanyId(), workingConditionItem.getMonthlyPattern().get().v(), command.getToDate());
		
		// パラメータ．月間パターンをチェックする, 対象日の「月間勤務就業設定」があるかチェックする
		if (!checkMonthlyPattern(command, workingConditionItem, workMonthlySetOpt)) {
			return;
		}
				
		//在職状態を判断
		if (!checkEmploymentStatus(command)) {
			return;
		}
				
		//在職、休職、休業
		//ドメインモデル「勤務予定基本情報」を取得する
		Optional<BasicSchedule> basicScheOpt = basicScheduleRepo.find(command.getEmployeeId(), command.getToDate());
		
		// 再作成
		if (basicScheOpt.isPresent()) { //「勤務予定基本情報」 データあり
			BasicSchedule basicSche = basicScheOpt.get();
			
			if (ImplementAtr.GENERALLY_CREATED == command.getContent().getImplementAtr()) { // 通常作成 
				return;
			}
			
			//入力パラメータ「再作成区分」を判断
			if (command.getContent().getReCreateContent().getReCreateAtr() == ReCreateAtr.ONLY_UNCONFIRM) { // 未確定データのみ
				//取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
				ConfirmedAtr confirmedAtr = basicSche.getConfirmedAtr();
				if (confirmedAtr == ConfirmedAtr.CONFIRMED) { //確定済み
					return;
				}
				
				//未確定
				//対象外 : アルゴリズム「スケジュール作成判定処理」を実行する
				//対象外 : 手修正を保護するか判定する
				command.setIsDeleteBeforInsert(true);
			} else { // 全件
				//対象外 : 手修正を保護するか判定する
				command.setIsDeleteBeforInsert(true);
			}
		} else { //「勤務予定基本情報」 データなし
			//no something
			command.setIsDeleteBeforInsert(false); 
		}
		
		//月間勤務就業設定
		WorkMonthlySetting workMonthlySet = workMonthlySetOpt.get();
		
		//在職状態に対応する「勤務種類コード」を取得する
		WorkTypeGetterCommand commandWorktypeGetter = getWorkTypeGetter(command, workingConditionItem);
		Optional<WorktypeDto> workTypeOpt = this.getWorkTypeByEmploymentStatus(workMonthlySet, commandWorktypeGetter);
		if (workTypeOpt.isPresent()) {//取得エラーなし
			//在職状態に対応する「就業時間帯コード」を取得する
			Optional<String> workTimeOpt =  this.getWorkingTimeZoneCode(workMonthlySet, commandWorktypeGetter);
			if (workTimeOpt == null || workTimeOpt.isPresent()) {//取得エラーなし
				//休憩予定時間帯を取得する
				//勤務予定マスタ情報を取得する
				//勤務予定時間帯を取得する		
				//アルゴリズム「社員の短時間勤務を取得」を実行し、短時間勤務を取得する // request list #72
				//取得した情報をもとに「勤務予定基本情報」を作成する (create basic schedule)
				//予定確定区分を取得し、「勤務予定基本情報. 確定区分」に設定する
				scheCreExeBasicScheduleHandler.updateAllDataToCommandSave(command, workingConditionItem.getEmployeeId(), workTypeOpt.get(), workTimeOpt != null ? workTimeOpt.get() : null);
			}
		}			
		
	}

	/**
	 * Get work type getter
	 * @param command
	 * @param workingConditionItem
	 * @return
	 */
	private WorkTypeGetterCommand getWorkTypeGetter(ScheduleCreatorExecutionCommand command,
			WorkingConditionItem workingConditionItem) {
		WorkTypeGetterCommand commandWorktypeGetter = new WorkTypeGetterCommand();
		commandWorktypeGetter.setBaseGetter(command.toBaseCommand());
		commandWorktypeGetter.setEmployeeId(workingConditionItem.getEmployeeId());
		if (workingConditionItem.getScheduleMethod().isPresent() && workingConditionItem
				.getScheduleMethod().get().getWorkScheduleBusCal().isPresent()) {
			commandWorktypeGetter.setReferenceBasicWork(workingConditionItem.getScheduleMethod()
					.get().getWorkScheduleBusCal().get().getReferenceBasicWork().value);
		}
		if (workingConditionItem.getScheduleMethod().isPresent() && workingConditionItem
				.getScheduleMethod().get().getWorkScheduleBusCal().isPresent()) {
			commandWorktypeGetter
					.setReferenceBusinessDayCalendar(workingConditionItem.getScheduleMethod().get()
							.getWorkScheduleBusCal().get().getReferenceBusinessDayCalendar().value);
			commandWorktypeGetter.setReferenceWorkingHours(
					workingConditionItem.getScheduleMethod().get().getWorkScheduleBusCal().get().getReferenceWorkingHours().value);
		}
		return commandWorktypeGetter;
	}
		
	/**
	 * 在職状態に対応する「勤務種類コード」を取得する
	 * 
	 * @return
	 */
	private Optional<WorktypeDto> getWorkTypeByEmploymentStatus(WorkMonthlySetting workMonthlySet, WorkTypeGetterCommand commandWorktypeGetter) {						
		// setup command employment status getter
		WorkTypeByEmpStatusGetterCommand commandWorkTypeEmploymentStatus = commandWorktypeGetter.toWorkTypeEmploymentStatus();
		
		// set working code to command
		commandWorkTypeEmploymentStatus.setWorkingCode(workMonthlySet.getWorkingCode() == null ? null : workMonthlySet.getWorkingCode().v());
		
		// set work type code to command
		commandWorkTypeEmploymentStatus.setWorkTypeCode(workMonthlySet.getWorkTypeCode().v());
		
		return this.scheCreExeWorkTypeHandler.getWorkTypeByEmploymentStatus(commandWorkTypeEmploymentStatus);
	}
	
	/**
	 * 在職状態に対応する「就業時間帯コード」を取得する
	 * @param workMonthlySet
	 * @param command
	 * @return
	 */
	public Optional<String> getWorkingTimeZoneCode(WorkMonthlySetting workMonthlySet, WorkTypeGetterCommand commandWorktypeGetter) {
		WorkTimeGetterCommand workTimeGetterCommand = commandWorktypeGetter.toWorkTime();
		WorkTimeZoneGetterCommand commandGetter = workTimeGetterCommand.toWorkTimeZone();
		commandGetter.setWorkTypeCode(workMonthlySet.getWorkTypeCode().v());
		commandGetter.setWorkingCode(workMonthlySet.getWorkingCode() == null ? null : workMonthlySet.getWorkingCode().v());
		return this.scheCreExeWorkTimeHandler.getWorkingTimeZoneCode(commandGetter);
	}

	/**
	 * check employment status (在職状態を判断)
	 * 
	 * @param command
	 */
	private boolean checkEmploymentStatus(ScheduleCreatorExecutionCommand command) {
		EmploymentStatusDto employmentStatus = this.scEmploymentStatusAdapter.getStatusEmployment(command.getEmployeeId(), command.getToDate());
		
		//退職、取得できない(退職 OR không lấy được)
		if (employmentStatus == null || employmentStatus.getStatusOfEmployment() == 6) {//RETIREMENT
			return false;
		}
		
		//入社前
		if (employmentStatus.getStatusOfEmployment() == 4) { //BEFORE_JOINING
			return false;
		}
		
		return true;
	}

	/**
	 * check monthly pattern
	 * @param command
	 * @param workingConditionItem
	 */
	private boolean checkMonthlyPattern(ScheduleCreatorExecutionCommand command,
			WorkingConditionItem workingConditionItem, Optional<WorkMonthlySetting> workMonthlySetOpt) {
		//ドメインモデル「スケジュール作成エラーログ」を登録する
		if (!workingConditionItem.getMonthlyPattern().isPresent() || StringUtil.isNullOrEmpty(workingConditionItem.getMonthlyPattern().get().v(), true)) {
			//log Msg_603
			scheCreExeErrorLogHandler.addError(command.toBaseCommand(), command.getEmployeeId(), "Msg_603");
			return false; 
		}
				
		//
		//対象日の「月間勤務就業設定」があるかチェックする
		//
		
		//存在しない場合
		//ドメインモデル「スケジュール作成エラーログ」を登録する
		if (!workMonthlySetOpt.isPresent()) {
			//log Msg_604
			scheCreExeErrorLogHandler.addError(command.toBaseCommand(), command.getEmployeeId(), "Msg_604");
			return false;
		}
		
		return true;
	}
	
}
