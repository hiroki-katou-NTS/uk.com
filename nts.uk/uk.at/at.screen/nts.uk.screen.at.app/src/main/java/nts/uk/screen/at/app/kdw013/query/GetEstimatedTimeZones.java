package nts.uk.screen.at.app.kdw013.query;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author sonnlb
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.工数入力目安時間帯を取得する.工数入力目安時間帯を取得する
 */
@Stateless
public class GetEstimatedTimeZones {
	
	@Inject
	private GetLastOverTimeApplication getLastOverTimeApplication;
	/**
	 * 
	 * @param inteDaiy 日別勤怠(Work)
	 * @return 入力目安時間帯
	 */
	public EstimatedTimeZone get(IntegrationOfDaily inteDaiy) {
		EstimatedTimeZone result = new EstimatedTimeZone();
		//入力目安時間帯．年月日 = 日別勤怠(Work)．年月日
		result.setYmd(inteDaiy.getYmd());
		
		//入力目安時間帯．休憩時間帯 = 日別勤怠(Work)．休憩時間帯．時間帯
		result.setBreakTimeSheets(inteDaiy.getBreakTime().getBreakTimeSheets());

		// 1. 日別勤怠(Work).出退勤.isPresent 出退勤の時間帯を返す() List<時間帯>
		
		inteDaiy.getAttendanceLeave().ifPresent(al -> {
			
			/**
			 * 入力目安時間帯．開始時刻 = 求めた「開始時刻」がなければ「時間帯(使用区分付き)．開始」をセットする
			 * 入力目安時間帯．終了時刻 = 求めた「終了時刻」がなければ「時間帯(使用区分付き)．終了」をセットする
			 * 取得した「時間帯(使用区分付き)」は「使用区分 = 使用する」の1個目を利用する
			 * 
			 */
			// get workNo 1
			al.getAttendanceLeavingWork(1).ifPresent(lw -> {
				result.setStartTime(lw.getAttendanceTime().map(x-> new TimeWithDayAttr(x.v())).orElse(null));
				result.setEndTime(lw.getLeaveTime().map(x-> new TimeWithDayAttr(x.v())).orElse(null));
			});			
		});
		// 2.取得する(社員ID, 年月日) 日別勤怠(Work).社員ID,日別勤怠(Work).年月日 Optional<残業申請>
		
		this.getLastOverTimeApplication.get(inteDaiy.getEmployeeId(), inteDaiy.getYmd()).ifPresent(oApp -> {
			
			/**
			 * 取得したList<時間帯>と残業申請から開始時刻と終了時刻を求める
			 * 
			 * 【比較対象】
			 * ・取得したList<時間帯>の1個目
			 * ・残業申請．勤務時間帯．時間帯
			 * 
			 * 【求め方】
			 * ・開始時刻：取得した「時間帯．開始時刻」と「残業申請．勤務時間帯．時間帯．開始時刻」の小さい方を開始時刻とする
			 * ・終了時刻：取得した「時間帯．終了時刻」と「残業申請．勤務時間帯．時間帯．終了時刻」の大きい方を終了時刻とする
			 */
			
			oApp.getWorkHoursOp().ifPresent(wh -> {
				wh.stream().mapToInt(x -> x.getTimeZone().getStartTime().v()).min().ifPresent(min -> {
					if (result.getStartTime() == null || (min < result.getStartTime().v())) {
						result.setStartTime(new TimeWithDayAttr(min));
					}

				});

				wh.stream().mapToInt(x -> x.getTimeZone().getEndTime().v()).max().ifPresent(max -> {
					if (result.getEndTime() == null || (max > result.getEndTime().v())) {
						result.setEndTime(new TimeWithDayAttr(max));
					}
				});
				//入力目安時間帯．残業時間帯 = 取得した「残業申請．勤務時間帯．時間帯」をセットする
				result.setOverTimeZones(wh.stream()
						.map(x -> x.getTimeZone())
						.collect(Collectors.toList()));
			});
			
			
		});
		
		
		//(求めた「開始時刻」.isEmpty OR 求めた「終了時刻」.isEmpty) AND 「日別勤怠(Work)．勤務情報．勤務情報．就業時間帯コード」.isPresent
		
		if (result.getStartTime() == null || result.getEndTime() == null) {
			//日別勤怠(Work)．勤務情報．始業終業時間帯
			//勤務NO = 1
			inteDaiy.getWorkInformation().getScheduleTimeSheets().stream().filter(x -> x.getWorkNo().v() == 1)
					.findFirst().ifPresent(ts -> {
						if (result.getStartTime() == null) {
							result.setStartTime(ts.getAttendance());
						}

						if (result.getEndTime() == null) {
							result.setEndTime(ts.getLeaveWork());
						}

					});
		}
		
		return result;
	}

}
