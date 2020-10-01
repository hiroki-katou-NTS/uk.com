package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;

/**
 * @author thanh_nx
 * 
 *         出退勤の反映（打刻）
 */
public class ReflectAttendanceLeav {

	public static List<Integer> reflect(DailyRecordOfApplication dailyApp, List<TimeStampAppShare> listTimeStampApp) {
		List<Integer> lstItemId = new ArrayList<>();
		// input. 出退勤（List）でループする
		listTimeStampApp.stream().forEach(data -> {

			if (dailyApp.getAttendanceLeave().isPresent()) {
				// 日別勤怠(work）の[出退勤]をチェック
				Optional<TimeLeavingWork> timeLeavOpt = dailyApp.getAttendanceLeave().get().getTimeLeavingWorks()
						.stream()
						.filter(x -> x.getWorkNo().v() == data.getDestinationTimeApp().getEngraveFrameNo().intValue())
						.findFirst();
				if (!timeLeavOpt.isPresent()) {
					// 該当の打刻枠NOをキーに[出退勤]を作成する
					Pair<TimeLeavingWork, List<Integer>> result = createTimeLeav(data);
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().add(result.getLeft());
					dailyApp.getAttendanceLeave().get().setCountWorkTime();
					lstItemId.addAll(result.getRight());
				} else {
					lstItemId.addAll(updateTimeLeav(timeLeavOpt.get(), data));
				}
			} else {
				List<TimeLeavingWork> lst = new ArrayList<>();
				Pair<TimeLeavingWork, List<Integer>> result = createTimeLeav(data);
				lst.add(result.getLeft());
				dailyApp.setAttendanceLeave(Optional.of(new TimeLeavingOfDailyAttd(lst, new WorkTimes(0))));
				dailyApp.getAttendanceLeave().get().setCountWorkTime();
				lstItemId.addAll(result.getRight());
			}

		});

		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	public static TimeActualStamp createTimeActualStamp(TimeStampAppShare data) {
		return new TimeActualStamp(null, new WorkStamp(data.getTimeOfDay(),
				new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null), data.getTimeOfDay()),
				data.getWorkLocationCd()), 0);
	}

	public static Pair<TimeLeavingWork, List<Integer>> createTimeLeav(TimeStampAppShare data) {
		List<Integer> itemIds = new ArrayList<Integer>();
		boolean start = (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START);
		if (start) {
			itemIds.addAll(Arrays.asList(
					CancelAppStamp.createItemId(31, data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10),
					CancelAppStamp.createItemId(30, data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10)));
		} else {
			itemIds.addAll(Arrays.asList(
					CancelAppStamp.createItemId(34, data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10),
					CancelAppStamp.createItemId(33, data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10)));
		}
		return Pair.of(
				new TimeLeavingWork(new WorkNo(data.getDestinationTimeApp().getEngraveFrameNo().intValue()),
						start ? createTimeActualStamp(data) : null, //
						start ? null : createTimeActualStamp(data)), //
				itemIds);//
	}

	public static List<Integer> updateTimeLeav(TimeLeavingWork timeLeav, TimeStampAppShare data) {

		List<Integer> lstItemId = new ArrayList<>();
		boolean start = (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START);
		if (start) {
			timeLeav.getAttendanceStamp().ifPresent(x -> {
				x.getStamp().ifPresent(y -> {
					if (data.getWorkLocationCd().isPresent()) {
						lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(30,
								data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10)));
						y.setLocationCode(data.getWorkLocationCd());
					}
					y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
					y.getTimeDay().setTimeWithDay(Optional.ofNullable(data.getTimeOfDay()));
					lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(31,
							data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10)));
				});
			});
		} else {
			timeLeav.getLeaveStamp().ifPresent(x -> {
				x.getStamp().ifPresent(y -> {
					if (data.getWorkLocationCd().isPresent()) {
						lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(33,
								data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10)));
						y.setLocationCode(data.getWorkLocationCd());
					}
					y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
					y.getTimeDay().setTimeWithDay(Optional.ofNullable(data.getTimeOfDay()));
					lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(34,
							data.getDestinationTimeApp().getEngraveFrameNo().intValue(), 10)));
				});
			});
		}
		return lstItemId;
	}

}
