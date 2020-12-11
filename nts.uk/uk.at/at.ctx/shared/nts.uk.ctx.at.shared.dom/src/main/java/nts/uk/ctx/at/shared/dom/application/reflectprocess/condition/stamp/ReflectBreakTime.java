package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppOtherShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;

/**
 * @author thanh_nx
 *
 *         休憩時間帯の反映
 */
public class ReflectBreakTime {

	public static List<Integer> reflect(DailyRecordOfApplication dailyApp,
			List<TimeStampAppOtherShare> listTimeStampAppOther) {

		List<Integer> lstItemId = new ArrayList<Integer>();

		listTimeStampAppOther.stream().forEach(data -> {

			if (!dailyApp.getBreakTime().isEmpty()) {

				Optional<BreakTimeOfDailyAttd> breakTimeOpt = dailyApp.getBreakTime().stream()
						.filter(x -> x.getBreakType() == BreakType.REFER_WORK_TIME).findFirst();

				if (breakTimeOpt.isPresent()) {
					Optional<BreakTimeSheet> brs = breakTimeOpt.get().getBreakTimeSheets().stream().filter(
							y -> y.getBreakFrameNo().v() == data.getDestinationTimeZoneApp().getEngraveFrameNo())
							.findFirst();
					if (brs.isPresent()) {
						brs.get().setStartTime(data.getTimeZone().getStartTime());
						brs.get().setEndTime(data.getTimeZone().getEndTime());
					} else {
						breakTimeOpt.get().getBreakTimeSheets().add(create(data));
					}
				} else {
					List<BreakTimeSheet> lstBreak = new ArrayList<>();
					lstBreak.add(create(data));
					dailyApp.getBreakTime().add(new BreakTimeOfDailyAttd(
							dailyApp.getClassification() == ScheduleRecordClassifi.SCHEDULE ? BreakType.REFER_SCHEDULE
									: BreakType.REFER_WORK_TIME,
							lstBreak));
				}

			} else {
				List<BreakTimeSheet> lstBreak = new ArrayList<>();
				lstBreak.add(create(data));
				List<BreakTimeOfDailyAttd> lstAttd = new ArrayList<>();
				lstAttd.add(new BreakTimeOfDailyAttd(
						dailyApp.getClassification() == ScheduleRecordClassifi.SCHEDULE ? BreakType.REFER_SCHEDULE
								: BreakType.REFER_WORK_TIME,
						lstBreak));
				dailyApp.setBreakTime(lstAttd);
			}
			lstItemId.addAll(createId(data.getDestinationTimeZoneApp().getEngraveFrameNo()));
		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	private static BreakTimeSheet create(TimeStampAppOtherShare data) {
		return new BreakTimeSheet(new BreakFrameNo(data.getDestinationTimeZoneApp().getEngraveFrameNo()),
				data.getTimeZone().getStartTime(), data.getTimeZone().getEndTime());
	}

	private static List<Integer> createId(int no) {
		return Arrays.asList(CancelAppStamp.createItemId(157, no, 6), CancelAppStamp.createItemId(159, no, 6));
	}
}
