package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.workschedulereflected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workschedule.BreakTimeOfDailyAttdImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.TimeLeavingWorkImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 勤務予定反映
 * @author tutk
 *
 */
@Stateless
public class WorkScheduleReflected {
	@Inject
	private WorkScheduleWorkInforAdapter workScheduleWorkInforAdapter;
	
	@Inject
	private WorkTypeRepository wkTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	public List<ErrorMessageInfo> workScheduleReflected(String companyId, String employeeId, GeneralDate ymd,
			WorkInfoOfDailyAttendance workInformation, List<BreakTimeOfDailyAttd> breakTime) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		//「勤務予定」ドメインを取得する
		Optional<WorkScheduleWorkInforImport> scheduleWorkInfor = workScheduleWorkInforAdapter.get(employeeId, ymd);
		if (!scheduleWorkInfor.isPresent() || scheduleWorkInfor.get().getWorkTyle() == null) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyId, employeeId, ymd, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("006"), new ErrMessageContent(TextResource.localize("Msg_431"))));
			return listErrorMessageInfo;
		}
		WorkInformation wi = new WorkInformation(scheduleWorkInfor.get().getWorkTime() ==null?null: new WorkTimeCode(scheduleWorkInfor.get().getWorkTime()), new WorkTypeCode(scheduleWorkInfor.get().getWorkTyle())); 
		//勤務情報をコピーする (Copy thông tin 勤務)
		workInformation.setRecordInfo(wi);
		workInformation.setScheduleInfo(wi);
		workInformation.setGoStraightAtr(EnumAdaptor.valueOf(scheduleWorkInfor.get().getGoStraightAtr(),NotUseAttribute.class));
		workInformation.setBackStraightAtr(EnumAdaptor.valueOf(scheduleWorkInfor.get().getBackStraightAtr(),NotUseAttribute.class));
		//計算状態を未計算にする
		workInformation.setCalculationState(CalculationState.No_Calculated);
		
		//ドメインモデル「勤務種類」を取得する-(Lấy domain 「WorkType」) // trả về list 1 phần tử or empty
		List<WorkType> wkTypeOpt = wkTypeRepo.findNotDeprecatedByListCode(companyId,
				Arrays.asList(workInformation.getRecordInfo().getWorkTypeCode().v()));
		if(wkTypeOpt.isEmpty()) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyId, employeeId, ymd, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("015"), new ErrMessageContent(TextResource.localize("Msg_590"))));
			return listErrorMessageInfo;
		}
		//1日半日出勤・1日休日系の判定
		WorkStyle workStyle =  wkTypeOpt.get(0).checkWorkDay();
		if(workStyle == WorkStyle.ONE_DAY_REST) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyId, employeeId, ymd, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("016"), new ErrMessageContent(TextResource.localize("Msg_591"))));
			return listErrorMessageInfo;
		}
		//ドメインモデル「就業時間帯の設定」を取得
		Optional<WorkTimeSetting> workTimeOpt = this.workTimeSettingRepository
				.findByCodeAndAbolishCondition(companyId, workInformation
						.getRecordInfo().getWorkTimeCode().v(), AbolishAtr.NOT_ABOLISH);
		//取得できなかった場合
		//勤務予定の出退勤を確認する
		if(!workTimeOpt.isPresent() || !scheduleWorkInfor.get().getTimeLeavingOfDailyAttd().isPresent() ) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyId, employeeId, ymd, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("016"), new ErrMessageContent(TextResource.localize("Msg_591"))));
			return listErrorMessageInfo;
		}
		// 予定時間帯をコピーする(Copy 予定時間帯)
		List<ScheduleTimeSheet> listScheduleTimeSheet = new ArrayList<>();
		for (TimeLeavingWorkImport timeLeavingWorkImport : scheduleWorkInfor.get().getTimeLeavingOfDailyAttd().get()
				.getTimeLeavingWorks()) {
			listScheduleTimeSheet.add(
				new ScheduleTimeSheet(
					timeLeavingWorkImport.getWorkNo(),
					timeLeavingWorkImport.getAttendanceStamp().get().getActualStamp().isPresent() 
					?timeLeavingWorkImport.getAttendanceStamp().get().getActualStamp().get().getTimeDay()
							.getTimeWithDay():0,
					timeLeavingWorkImport.getLeaveStamp().get().getActualStamp().isPresent()?
					timeLeavingWorkImport.getLeaveStamp().get().getActualStamp().get().getTimeDay().getTimeWithDay():0
				)
			);
		}
		workInformation.setScheduleTimeSheets(listScheduleTimeSheet);
		
		//予定休憩時間をコピーする
		List<BreakTimeOfDailyAttdImport> breakTimeWorkSchedule = scheduleWorkInfor.get()
				.getListBreakTimeOfDailyAttdImport().stream().filter(c -> c.getBreakType() == 1)
				.collect(Collectors.toList());
		breakTime = breakTimeWorkSchedule.stream()
				.map(c -> 
						new BreakTimeOfDailyAttd(
							EnumAdaptor.valueOf(c.getBreakType(), BreakType.class),
							c.getBreakTimeSheets().stream().map(
									x -> new BreakTimeSheet(
										new BreakFrameNo(x.getBreakFrameNo()), 
										new TimeWithDayAttr(x.getStartTime()),
										new TimeWithDayAttr(x.getEndTime())
									)
							).collect(Collectors.toList())
						)
					)
				.collect(Collectors.toList());

		return listErrorMessageInfo;
		
	}

}
