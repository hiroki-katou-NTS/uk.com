package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.stamp.DestinationTimeAppShare;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppEnumShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;

/**
 * @author thanh_nx
 *
 *         時刻申請の取消
 */
public class CancelAppStamp {

	public static List<Integer> process(DailyRecordOfApplication dailyApp,
			List<DestinationTimeAppShare> listDestinationTimeApp) {

		// [input. 打刻取消(List)]でループ

		List<Integer> lstItemId = new ArrayList<>();
		listDestinationTimeApp.stream().forEach(data -> {

			if (data.getTimeStampAppEnum() == TimeStampAppEnumShare.ATTEENDENCE_OR_RETIREMENT) {
				// 該当の打刻枠NOがキーとなる[出退勤]の時刻をクリアする
				dailyApp.getAttendanceLeave().ifPresent(attLeav -> {
					attLeav.getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == data.getEngraveFrameNo().intValue()).forEach(item -> {
								if (data.getStartEndClassification() == StartEndClassificationShare.START) {
//									　 [出勤時刻1～2]、
//									　 [出勤場所コード1～2]
									item.getAttendanceStamp().ifPresent(x -> {
										removeTimeStamp(x);
										lstItemId.addAll(Arrays.asList(createItemId(31, item.getWorkNo().v(), 10),
												createItemId(30, item.getWorkNo().v(), 10)));
									});
								} else {
//									     [退勤時刻1～2]
//									     [退勤場所コード1～2]
									item.getLeaveStamp().ifPresent(x -> {
										lstItemId.addAll(Arrays.asList(createItemId(34, item.getWorkNo().v(), 10),
												createItemId(33, item.getWorkNo().v(), 10)));
										removeTimeStamp(x);
									});

								}

							});
				});
			} else if (data.getTimeStampAppEnum() == TimeStampAppEnumShare.EXTRAORDINARY) {
				// 該当の打刻枠NOがキーとなる[臨時出退勤]の時刻をクリアする
				dailyApp.getTempTime().ifPresent(temp -> {
					temp.getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == data.getEngraveFrameNo().intValue()).forEach(item -> {
								if (data.getStartEndClassification() == StartEndClassificationShare.START) {
//									[臨時出勤時刻1～3]、
//									[臨時出勤場所コード1～3]
									item.getAttendanceStamp().ifPresent(x -> {
										removeTimeStamp(x);
										lstItemId.addAll(Arrays.asList(createItemId(51, item.getWorkNo().v(), 8),
												createItemId(50, item.getWorkNo().v(), 8)));
									});
								} else {
//									[臨時退勤時刻1～3]
//									[臨時退勤場所コード1～3]
									item.getLeaveStamp().ifPresent(x -> {
										removeTimeStamp(x);
										lstItemId.addAll(Arrays.asList(createItemId(53, item.getWorkNo().v(), 8),
												createItemId(52, item.getWorkNo().v(), 8)));
									});
								}

							});
				});
			} else if (data.getTimeStampAppEnum() == TimeStampAppEnumShare.GOOUT_RETURNING) {
				// 該当の打刻枠NOがキーとなる[外出時間帯]の時刻をクリアする
				dailyApp.getOutingTime().ifPresent(temp -> {
					temp.getOutingTimeSheets().stream()
							.filter(x -> x.getOutingFrameNo().v() == data.getEngraveFrameNo().intValue())
							.forEach(item -> {
								if (data.getStartEndClassification() == StartEndClassificationShare.START) {
//									　　　[外出時刻1～10]、
//									　　　[外出場所コード1～10]
									item.getGoOut().ifPresent(x -> {
										removeTimeStamp(x);
										lstItemId.addAll(Arrays.asList(createItemId(88, item.getOutingFrameNo().v(), 7),
												createItemId(87, item.getOutingFrameNo().v(), 7)));
									});
								} else {
//									    [戻り時刻1～10]
//										[戻り場所コード1～10]
									item.getComeBack().ifPresent(x -> {
										removeTimeStamp(x);
										lstItemId.addAll(Arrays.asList(createItemId(91, item.getOutingFrameNo().v(), 7),
												createItemId(90, item.getOutingFrameNo().v(), 7)));
									});
								}

							});
				});
			}

		});

		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	private static void removeTimeStamp(TimeActualStamp stamp) {
		stamp.setStamp(Optional.of(new WorkStamp(
				new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null), null), Optional.empty())));
	}

	public static Integer createItemId(int itemMin, int no, int range) {
		return itemMin + (no - 1) * range;
	}
}
