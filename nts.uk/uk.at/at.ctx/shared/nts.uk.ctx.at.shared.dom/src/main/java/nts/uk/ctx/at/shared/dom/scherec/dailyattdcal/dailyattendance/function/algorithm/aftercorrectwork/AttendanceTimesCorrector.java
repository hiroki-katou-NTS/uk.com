package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/** 勤務回数の補正 */
public class AttendanceTimesCorrector {

	/** 勤務回数の補正 */
	public static void correct(Require require, IntegrationOfDaily dailyRecord) {
		
		/** 所定時間帯を取得する */
		val workTimeZones = dailyRecord.getWorkInformation().getRecordInfo().getWorkInfoAndTimeZone(require);

		workTimeZones.ifPresent(tz -> {
			
			/** 二回勤務使用するかを確認する */
			if (tz.getTimeZones().size() == 1) return;
			
			dailyRecord.getAttendanceLeave().flatMap(al -> al.getAttendanceLeavingWork(2)).ifPresent(al -> {
				
				al.getAttendanceStamp().ifPresent(a -> {
					val attendanceStampInfo = a.getStamp().map(c -> c.getTimeDay().getReasonTimeChange().getTimeChangeMeans())
															.orElse(null);

					/** 出勤2をクリアすべきかを確認する */
					if (attendanceStampInfo == TimeChangeMeans.AUTOMATIC_SET 
							|| attendanceStampInfo == TimeChangeMeans.DIRECT_BOUNCE_APPLICATION
					   		|| attendanceStampInfo == TimeChangeMeans.DIRECT_BOUNCE) 
						
						/** 出勤2をクリアすべきかを確認する */
						a.setStamp(Optional.empty());
				});
				
				al.getLeaveStamp().ifPresent(l -> {
					val leaveStampInfo = l.getStamp().map(c -> c.getTimeDay().getReasonTimeChange().getTimeChangeMeans())
															.orElse(null);

					/** 退勤2をクリアすべきかを確認する */
					if (leaveStampInfo == TimeChangeMeans.AUTOMATIC_SET 
							|| leaveStampInfo == TimeChangeMeans.DIRECT_BOUNCE_APPLICATION
					   		|| attendanceStampInfo == TimeChangeMeans.DIRECT_BOUNCE) 
						
						/** 退勤2をクリアする */
						l.setStamp(Optional.empty());
				});
			});
			 
		});
	}
	
	public static interface Require extends WorkInformation.Require {
		
	}
}
