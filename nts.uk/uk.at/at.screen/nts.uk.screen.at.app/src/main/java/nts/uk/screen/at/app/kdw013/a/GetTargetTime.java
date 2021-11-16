package nts.uk.screen.at.app.kdw013.a;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.screen.at.app.kdw013.query.GetApplicationData;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.残業申請・休出時間申請の対象時間を取得する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetTargetTime {

	@Inject
	private GetApplicationData applicationData;
	
	@Inject
	private ClosureEmploymentService closureEmploymentService;
	
	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;

	/**
	 * 
	 * @param sid                    対象社員
	 * @param inputDates             対象日リスト
	 * @return List<残業休出時間>
	 */
	public List<OvertimeLeaveTimeDto> get(String sid, List<GeneralDate> inputDates) {
		
		 List<OvertimeLeaveTimeDto> result = new ArrayList<>();

		// 1.社員に対応する締め期間を取得する
		DatePeriod period = this.closureEmploymentService.findClosurePeriod(sid, GeneralDate.today());
		
		//年月日リスト = 対象日リスト：filter $ >= 取得した「期間．開始日」
		List<GeneralDate> dates = inputDates.stream().filter(x -> x.afterOrEquals(period.start()))
				.collect(Collectors.toList());
		
		//2 .get (対象社員,年月日リスト)
		
		List<AttendanceTimeOfDailyPerformance> atts =  this.attendanceTimeRepository.find(sid, dates);
		
		atts.forEach(att -> {
			
			//日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間．残業時間.isPresent
			att.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().ifPresent(ot->{
				//残業合計時間 = 「日別勤怠の残業時間．残業枠時間．振替時間．計算時間」 + 「日別勤怠の残業時間．残業枠時間．残業時間．計算時間」	
						Integer totalOverTime = 
								ot.getOverTimeWorkFrameTime().stream().mapToInt(ft -> ft.getTransferTime().getCalcTime().v()).sum() 
								+ ot.getOverTimeWorkFrameTime().stream().mapToInt(ft -> ft.getOverTimeWork().getCalcTime().v()).sum();
						
						if (totalOverTime > 0) {
							//3 . 残業合計時間 > 0
							//取得する(社員ID, 申請種類, 年月日, 事前事後区分)
							//申請者, 申請種類, 申請日, 事前事後区分
							//対象者,申請種類.残業申請,日別勤怠(Work).年月日,事前事後区分.事後
							List<Application> lstApplication = applicationData.get(sid,
									ApplicationType.OVER_TIME_APPLICATION.value, att.getYmd(),
									PrePostAtr.POSTERIOR.value);
							// 3.1 List<申請> == isEmpty 日別勤怠(Work).年月日,残業休出区分.残業申請,残業合計時間 
							if (lstApplication.isEmpty()) {
								result.add(new OvertimeLeaveTimeDto(att.getYmd(), totalOverTime,
										OverTimeLeaveType.OVER_TIME_APPLICATION.value));
							}
						}
			});
			//日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間．休出時間.isPresent
			att.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().ifPresent(ht->{
				//休出合計時間 = 「日別勤怠の休出時間．休出枠時間．振替時間．計算時間」 + 「日別勤怠の休出時間．休出枠時間．休出時間．計算時間」	
						Integer totalBreakTime = 
								ht.getHolidayWorkFrameTime().stream().mapToInt(ft -> ft.getTransferTime().get().getCalcTime().v()).sum() 
								+ ht.getHolidayWorkFrameTime().stream().mapToInt(ft -> ft.getHolidayWorkTime().get().getCalcTime().v()).sum();
						
						if (totalBreakTime > 0) {
							// 休出合計時間 > 0
							// 取得する(社員ID, 申請種類, 年月日, 事前事後区分)
							// 申請者, 申請種類, 申請日, 事前事後区分
							// 対象者,申請種類.休出時間申請,日別勤怠(Work).年月日,事前事後区分.事後
							List<Application> lstApplication = applicationData.get(sid,
									ApplicationType.HOLIDAY_WORK_APPLICATION.value, att.getYmd(),
									PrePostAtr.POSTERIOR.value);
							// 4.1 List<申請> == isEmpty
							// 日別勤怠(Work).年月日,残業休出区分.休日出勤申請,休出合計時間
							if (lstApplication.isEmpty()) {
								result.add(new OvertimeLeaveTimeDto(att.getYmd(), totalBreakTime,
										OverTimeLeaveType.HOLIDAY_WORK_APPLICATION.value));
							}
						}
			});			

		});

		return result;
	}

}
