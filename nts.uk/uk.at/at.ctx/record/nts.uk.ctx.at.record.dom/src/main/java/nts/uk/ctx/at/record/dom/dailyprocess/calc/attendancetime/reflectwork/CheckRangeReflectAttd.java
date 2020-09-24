package nts.uk.ctx.at.record.dom.dailyprocess.calc.attendancetime.reflectwork;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ProcessTimeOutput;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectTimezoneOutput;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 出勤打刻の反映範囲か確認する (new_2020)
 * @author tutk
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CheckRangeReflectAttd {
	
	public OutputCheckRangeReflectAttd checkRangeReflectAttd(Stamp stamp,StampReflectRangeOutput stampReflectRangeOutput,IntegrationOfDaily integrationOfDaily) {
		
		// 打刻．時刻を処理中年月日に対応する時刻に変換する (Chuyển đổi 打刻．時刻)
		TimeWithDayAttr timeWithDayAttr = TimeWithDayAttr.convertToTimeWithDayAttr(integrationOfDaily.getYmd(),
				stamp.getStampDateTime().toDate(), stamp.getStampDateTime().clockHourMinute().v());
				
		ProcessTimeOutput processTimeOutput = new ProcessTimeOutput();
		processTimeOutput.setTimeOfDay(timeWithDayAttr);
		
		// in or outrange
		List<StampReflectTimezoneOutput> lstStampReflectTimezone = stampReflectRangeOutput.getLstStampReflectTimezone();
		if (lstStampReflectTimezone == null) {
			return OutputCheckRangeReflectAttd.OUT_OF_RANGE;
		}

		int n = lstStampReflectTimezone.size();
		for (int i = 0; i < n; i++) {
			StampReflectTimezoneOutput stampReflectTimezone = lstStampReflectTimezone.get(i);
			if (stampReflectTimezone.getStartTime().v().intValue() <= processTimeOutput.getTimeOfDay().v().intValue()
					&& stampReflectTimezone.getEndTime().v().intValue() >= processTimeOutput.getTimeOfDay().v()
							.intValue()
					&& stampReflectTimezone.getClassification() == GoLeavingWorkAtr.GO_WORK) {
				if (stampReflectTimezone.getWorkNo().v().intValue() == 1) {
					return OutputCheckRangeReflectAttd.FIRST_TIME;
				}
				if (stampReflectTimezone.getWorkNo().v().intValue() == 2) {
					return OutputCheckRangeReflectAttd.SECOND_TIME;
				}
			}
		}
		return OutputCheckRangeReflectAttd.OUT_OF_RANGE;
	}

}
