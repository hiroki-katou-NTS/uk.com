package nts.uk.ctx.at.schedule.app.command.executionlog.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.command.executionlog.ScheduleCreatorExecutionCommand;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScEmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ImplementAtr;
import nts.uk.ctx.at.schedule.dom.executionlog.ReCreateAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;

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
	
	/**
	 * 月間パターンで勤務予定を作成する
	 * @param command
	 * @param workingConditionItem
	 */
	public void createScheduleWithMonthlyPattern(ScheduleCreatorExecutionCommand command, WorkingConditionItem workingConditionItem) {
		//ドメインモデル「スケジュール作成エラーログ」を登録する
		if (!workingConditionItem.getMonthlyPattern().isPresent()) {
			//log Msg_603
			scheCreExeErrorLogHandler.addError(command.toBaseCommand(), command.getEmployeeId(), "Msg_603");
			return;
		}
		
		//ドメインモデル「月間勤務就業設定」を取得する
		Optional<WorkMonthlySetting> workMonthlySetOpt = this.workMonthlySettingRepo.findById(command.getCompanyId(), workingConditionItem.getMonthlyPattern().get().v(), command.getToDate());
		
		//
		//対象日の「月間勤務就業設定」があるかチェックする
		//
		
		//存在しない場合
		//ドメインモデル「スケジュール作成エラーログ」を登録する
		if (!workMonthlySetOpt.isPresent()) {
			//log Msg_604
			scheCreExeErrorLogHandler.addError(command.toBaseCommand(), command.getEmployeeId(), "Msg_604");
			return;
		}
		
		//
		//存在する場合
		//
		
		//在職状態を判断
		EmploymentStatusDto employmentStatus = this.scEmploymentStatusAdapter.getStatusEmployment(command.getEmployeeId(), command.getToDate());
		
		//退職、取得できない(退職 OR không lấy được)
		if (employmentStatus == null) {
			return;
		}
		
		//入社前
		
		
		//在職、休職、休業
		//ドメインモデル「勤務予定基本情報」を取得する
		Optional<BasicSchedule> basicScheOpt = basicScheduleRepo.find(command.getEmployeeId(), command.getToDate());
		
		if (ImplementAtr.RECREATE == command.getContent().getImplementAtr()) { // 再作成
			if (basicScheOpt.isPresent()) { //「勤務予定基本情報」 データあり
				BasicSchedule basicSche = basicScheOpt.get();
				
				//入力パラメータ「再作成区分」を判断
				if (command.getContent().getReCreateContent().getReCreateAtr() == ReCreateAtr.ONLY_UNCONFIRM) { // 未確定データのみ
					//取得したドメインモデル「勤務予定基本情報」の「予定確定区分」を判断
					ConfirmedAtr confirmedAtr = basicSche.getConfirmedAtr();
					if (confirmedAtr == ConfirmedAtr.UNSETTLED) {//未確定
						//対象外 : アルゴリズム「スケジュール作成判定処理」を実行する
						//対象外 : 手修正を保護するか判定する
						command.setIsDeleteBeforInsert(true);
						
					} else { //確定済み
						return;
					}
				} else { // 全件
					//対象外 : 手修正を保護するか判定する
					command.setIsDeleteBeforInsert(true);
				}
			} else { //「勤務予定基本情報」 データなし
				//no something
			}
			
			//在職状態に対応する「勤務種類コード」を取得する
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
			
			
			Optional<WorktypeDto> workTypeOpt = this.scheCreExeWorkTypeHandler.getWorktype(commandWorktypeGetter);
			if (workTypeOpt.isPresent()) {//取得エラーなし
				//在職状態に対応する「就業時間帯コード」を取得する
				WorkTimeGetterCommand commandWorkTimeGetter = commandWorktypeGetter.toWorkTime();
				commandWorkTimeGetter.setWorkTypeCode(workTypeOpt.get().getWorktypeCode());	
				Optional<String> workTimeOpt =  this.scheCreExeWorkTimeHandler.getWorktime(commandWorkTimeGetter);
				if (workTimeOpt.isPresent()) {//取得エラーなし
					//勤務予定時間帯を取得する
					WorkTimeSetGetterCommand workTimeSetGetterCommand = new WorkTimeSetGetterCommand();
					workTimeSetGetterCommand.setWorktypeCode(workTypeOpt.get().getWorktypeCode());
					workTimeSetGetterCommand.setCompanyId(command.getCompanyId());
					workTimeSetGetterCommand.setWorkingCode(workTimeOpt.get());
					Optional<PrescribedTimezoneSetting> scheduleWorkHour = this.scheCreExeWorkTimeHandler.getScheduleWorkHour(workTimeSetGetterCommand);
					
					//TODO 休憩予定時間帯を取得する
					
					//TODO アルゴリズム「社員の短時間勤務を取得」を実行し、短時間勤務を取得する
					
					//TODO 勤務予定マスタ情報を取得する
					
					//TODO 取得した情報をもとに「勤務予定基本情報」を作成する (create basic schedule)
					
					//TODO 予定確定区分を取得し、「勤務予定基本情報. 確定区分」に設定する
					
				}
			}			
		} else { // 通常作成
			return;
		}
	}
}
