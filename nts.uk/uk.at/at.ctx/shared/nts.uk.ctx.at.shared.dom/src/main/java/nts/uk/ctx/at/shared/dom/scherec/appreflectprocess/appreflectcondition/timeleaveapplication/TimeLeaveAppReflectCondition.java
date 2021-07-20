package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationDetailShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.DailyAfterAppReflectResult;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 時間休暇の申請反映条件
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveAppReflectCondition extends DomainObject {
	/**
	 * 60H超休
	 */
	private NotUseAtr superHoliday60H;

	/**
	 * 介護
	 */
	private NotUseAtr nursing;

	/**
	 * 子看護
	 */
	private NotUseAtr childNursing;

	/**
	 * 時間代休
	 */
	private NotUseAtr substituteLeaveTime;

	/**
	 * 時間年休
	 */
	private NotUseAtr annualVacationTime;

	/**
	 * 時間特別休暇
	 */
	private NotUseAtr specialVacationTime;

	/**
	 * @author thanh_nx
	 *
	 *         時間消化休暇の内訳を反映
	 */
	public DailyRecordOfApplication process(TimeDigestApplicationShare timeDigestApp,
			DailyRecordOfApplication dailyApp) {

		// 時間休暇の申請反映条件チェック
		TimeDigestApplicationShare timeDigestCheck = this.check(timeDigestApp);

		// 時間消化休暇の内訳を反映
		this.processTimeDigest(timeDigestCheck, dailyApp);
		return dailyApp;

	}

	/**
	 * @author thanh_nx
	 *
	 *         休暇申請の時間消化の反映
	 */
	public DailyRecordOfApplication processTimeDigest(TimeDigestApplicationShare timeDigestApp,
			DailyRecordOfApplication dailyApp) {

		// [input.時間消化申請(work）.時間年休]を日別勤怠(work）へセット
		if (this.getAnnualVacationTime() == NotUseAtr.USE) {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getAnnual()
						.setDigestionUseTime(timeDigestApp.getTimeAnnualLeave());
			});
		}
		if (this.getSubstituteLeaveTime() == NotUseAtr.USE) {
			// [input.時間消化申請(work）.時間代休]を日別勤怠(work）へセット
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getSubstitute()
						.setDigestionUseTime(timeDigestApp.getTimeOff());
			});
		}

		if (this.getSpecialVacationTime() == NotUseAtr.USE) {
			// [input.時間消化申請(work）.時間特別休暇]を日別勤怠(work）へセット
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getSpecialHoliday()
						.setDigestionUseTime(timeDigestApp.getTimeSpecialVacation());
			});
		}

		if (this.getSuperHoliday60H() == NotUseAtr.USE) {
			// [input.時間消化申請(work）.60H超休]を日別勤怠(work）へセット
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getOverSalary()
						.setDigestionUseTime(timeDigestApp.getOvertime60H());
			});
		}
		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(540, 542, 546));

		return dailyApp;
	}
		
	/**
	 * @author thanh_nx
	 *
	 *         控除時間と相殺する時間休暇を反映
	 */

	public DailyAfterAppReflectResult process(List<TimeLeaveApplicationDetailShare> appTimeLeavDetail,
			DailyRecordOfApplication dailyApp, NotUseAtr reflectActualTimeZone) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// input.時間休暇申請詳細（List）でループ
		for (TimeLeaveApplicationDetailShare detail : appTimeLeavDetail) {

			// 時間休暇の申請反映条件チェック
			TimeDigestApplicationShare timeDigest = check(detail.getTimeDigestApplication());

			// 時間休暇の時間消化の反映
			lstItemId.addAll(process(dailyApp, detail.getAppTimeType(), timeDigest).getLstItemId());

			// [日別勤怠(work）.予定実績区分]をチェックする
			if (dailyApp.getClassification() == ScheduleRecordClassifi.RECORD
					&& reflectActualTimeZone == NotUseAtr.NOT_USE) {
				continue;
			}

			// 時間休暇時間帯の反映
			lstItemId.addAll(processVacationTimeZone(detail.getAppTimeType(),
					detail.getTimeZoneWithWorkNoLst(), dailyApp).getLstItemId());

		}

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	/**
	 * @author thanh_nx
	 *
	 *         時間休暇の申請反映条件チェック
	 */

	public TimeDigestApplicationShare check(TimeDigestApplicationShare timeDigestApplication) {

		// 反映する時間消化申請（work）の初期化
		TimeDigestApplicationShare timeDigestInit = new TimeDigestApplicationShare(new AttendanceTime(0),
				new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0),
				new AttendanceTime(0), Optional.empty());

		// [時間年休]をチェック
		if (this.getAnnualVacationTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeAnnualLeave(timeDigestApplication.getTimeAnnualLeave());
		}

		// [時間代休]をチェック
		if (this.getSubstituteLeaveTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeOff(timeDigestApplication.getTimeOff());
		}

		// [時間特別休暇]をチェック
		if (this.getSpecialVacationTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeSpecialVacation(timeDigestApplication.getTimeSpecialVacation());
			timeDigestInit.setSpecialVacationFrameNO(timeDigestApplication.getSpecialVacationFrameNO());
		}

		// [60H超休]をチェック
		if (this.getSuperHoliday60H() == NotUseAtr.USE) {
			timeDigestInit.setOvertime60H(timeDigestApplication.getOvertime60H());
		}

		// [子看護]をチェック
		if (this.getChildNursing() == NotUseAtr.USE) {
			timeDigestInit.setChildTime(timeDigestApplication.getChildTime());
		}

		// [介護]をチェック
		if (this.getNursing() == NotUseAtr.USE) {
			timeDigestInit.setNursingTime(timeDigestApplication.getNursingTime());
		}

		return timeDigestInit;
	}

	/**
	 * @author thanh_nx
	 *
	 *         時間休暇の時間消化の反映
	 */

	public DailyAfterAppReflectResult process(DailyRecordOfApplication dailyApp, AppTimeType appTimeType,
			TimeDigestApplicationShare timeDigest) {

		// [input. 時間休種類]をチェック
		List<Integer> lstItemId = new ArrayList<>();
		if (appTimeType == AppTimeType.ATWORK || appTimeType == AppTimeType.ATWORK2) {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				// [input. 時間消化申請(work)]を 日別勤怠(work）の遅刻時間へセット
				for (int i = 1; i <= 2; i++) {
					int z = i;
					LateTimeOfDaily data = x.getLateTimeOfDaily().stream().filter(y -> y.getWorkNo().v() == z)
							.findFirst().orElse(null);
					if (data == null) {
						data = LateTimeOfDaily.createDefaultWithNo(i);
						x.getLateTimeOfDaily().add(data);
					}
					if (data.getWorkNo().v() == 1 && appTimeType == AppTimeType.ATWORK) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(595, 596, 597, 1123, 1124, 1125, 1126));
					}

					if (data.getWorkNo().v() == 2 && appTimeType == AppTimeType.ATWORK2) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(601, 602, 603, 1127, 1128, 1129, 1130));
					}
				}
			});
		} else if (appTimeType == AppTimeType.OFFWORK || appTimeType == AppTimeType.OFFWORK2) {
			// [input. 時間消化申請(work)]を 日別勤怠(work）の早退時間へセット
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				for (int i = 1; i <= 2; i++) {
					int z = i;
					LeaveEarlyTimeOfDaily data = x.getLeaveEarlyTimeOfDaily().stream()
							.filter(y -> y.getWorkNo().v() == z).findFirst().orElse(null);
					if (data == null) {
						data = LeaveEarlyTimeOfDaily.createDefaultWithNo(i);
						x.getLeaveEarlyTimeOfDaily().add(data);
					}
					if (data.getWorkNo().v() == 1 && appTimeType == AppTimeType.OFFWORK) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(607, 608, 609, 1131, 1132, 1133, 1134));
					}

					if (data.getWorkNo().v() == 2 && appTimeType == AppTimeType.OFFWORK2) {
						updateVacationTime(data.getTimePaidUseTime(), timeDigest);
						lstItemId.addAll(Arrays.asList(613, 614, 615, 1135, 1136, 1137, 1138));
					}
				}
			});
		} else {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				// [input.時間消化申請(work）を日別勤怠(work）の外出時間]へセット
				if (x.getOutingTimeOfDaily().isEmpty()) {
					x.getOutingTimeOfDaily().add(OutingTimeOfDaily.createDefaultWithReason(
							appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION));
				}

				for (OutingTimeOfDaily data : x.getOutingTimeOfDaily()) {
					if (appTimeType == AppTimeType.PRIVATE) {
						data.setReason(GoingOutReason.PRIVATE);
						updateVacationTime(data.getTimeVacationUseOfDaily(), timeDigest);
						lstItemId.addAll(Arrays.asList(502, 503, 504, 1145, 505, 1140, 1141));
					}

					if (appTimeType == AppTimeType.UNION) {
						data.setReason(GoingOutReason.UNION);
						updateVacationTime(data.getTimeVacationUseOfDaily(), timeDigest);
						lstItemId.addAll(Arrays.asList(514, 515, 516, 1146, 517, 1142, 1143));
					}
				}
			});
		}

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	// 日別勤怠の時間休暇使用時間を更新する
	private void updateVacationTime(TimevacationUseTimeOfDaily data, TimeDigestApplicationShare timeDigest) {
		data.setTimeAnnualLeaveUseTime(timeDigest.getTimeAnnualLeave());
		data.setTimeCompensatoryLeaveUseTime(timeDigest.getTimeOff());
		data.setTimeSpecialHolidayUseTime(timeDigest.getTimeSpecialVacation());
		data.setSpecialHolidayFrameNo(timeDigest.getSpecialVacationFrameNO().map(y -> new SpecialHdFrameNo(y)));
		data.setSixtyHourExcessHolidayUseTime(timeDigest.getOvertime60H());
		data.setTimeChildCareHolidayUseTime(timeDigest.getChildTime());
		data.setTimeCareHolidayUseTime(timeDigest.getNursingTime());
	}

	/**
	 * @author thanh_nx
	 *
	 *         時間休暇時間帯の反映
	 */

	public DailyAfterAppReflectResult processVacationTimeZone(AppTimeType appTimeType, List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstItemId = new ArrayList<Integer>();

		// [input.時間休暇時間帯（List）]でループ
		for (TimeZoneWithWorkNo workNo : timeZoneWithWorkNoLst) {

			if (appTimeType == AppTimeType.PRIVATE || appTimeType == AppTimeType.UNION) {
				// 日別勤怠(work）の外出時間帯をチェック
				if (!dailyApp.getOutingTime().isPresent()) {
					dailyApp.setOutingTime(Optional.of(new OutingTimeOfDailyAttd(new ArrayList<>())));
				}

				if (!dailyApp.getOutingTime().get().getOutingTimeSheets().stream()
						.filter(x -> x.getOutingFrameNo().v() == workNo.getWorkNo().v()).findFirst().isPresent()) {
					dailyApp.getOutingTime().get().getOutingTimeSheets().add(OutingTimeSheet.createDefaultWithNo(
							workNo.getWorkNo().v(),
							appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION));
				}

				// 時間帯を日別勤怠(work）の外出時間帯にセットする
				dailyApp.getOutingTime().get().getOutingTimeSheets().stream()
						.filter(x -> x.getOutingFrameNo().v() == workNo.getWorkNo().v()).map(x -> {
							x.getGoOut().ifPresent(y -> {
								y.getTimeDay().setTimeWithDay(Optional.of(workNo.getTimeZone().getStartTime()));
								y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
								lstItemId.add(CancelAppStamp.createItemId(88, workNo.getWorkNo().v(), 7));
							});

							x.getComeBack().ifPresent(y -> {
								y.getTimeDay().setTimeWithDay(Optional.of(workNo.getTimeZone().getEndTime()));
								y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
								lstItemId.add(CancelAppStamp.createItemId(91, workNo.getWorkNo().v(), 7));
							});
							x.setReasonForGoOut(
									appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION);
							lstItemId.add(CancelAppStamp.createItemId(86, workNo.getWorkNo().v(), 7));
							return x;
						}).collect(Collectors.toList());

			} else {

				// 日別勤怠(work）の出退勤をチェック
				if (!dailyApp.getAttendanceLeave().isPresent()) {
					dailyApp.setAttendanceLeave(
							Optional.of(new TimeLeavingOfDailyAttd(new ArrayList<>(), new WorkTimes(0))));
				}

				// 処理中の時間帯.勤務NOをもとに[出退勤]を作成する
				if (!dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(workNo.getWorkNo().v()).isPresent()
						&& (checkRespectiveAttNo(workNo.getWorkNo().v(), appTimeType)
								|| checkRespectiveOffNo(workNo.getWorkNo().v(), appTimeType))) {
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().add(
							TimeLeavingWork.createDefaultWithNo(workNo.getWorkNo().v(), TimeChangeMeans.APPLICATION));
				}

				if (dailyApp.getClassification() == ScheduleRecordClassifi.SCHEDULE) {
					// 時間帯を日別勤怠(work）の時間休暇時間帯にセットする
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == workNo.getWorkNo().v()).map(x -> {
								if (checkRespectiveAttNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getAttendanceStamp().ifPresent(y -> {
										y.setTimeVacation(
												Optional.of(new TimeSpanForCalc(workNo.getTimeZone().getStartTime(),
														workNo.getTimeZone().getEndTime())));
										lstItemId.add(appTimeType == AppTimeType.ATWORK ? 1288 : 1290);
									});
								} else if (checkRespectiveOffNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getLeaveStamp().ifPresent(y -> {
										y.setTimeVacation(
												Optional.of(new TimeSpanForCalc(workNo.getTimeZone().getStartTime(),
														workNo.getTimeZone().getEndTime())));
										lstItemId.add(appTimeType == AppTimeType.OFFWORK ? 1289 : 1291);
									});
								}
								return x;
							}).collect(Collectors.toList());
				} else {
					// 時間帯を日別勤怠(work）の出退勤にセットする
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().stream()
							.filter(x -> x.getWorkNo().v() == workNo.getWorkNo().v()).map(x -> {
								if (checkRespectiveAttNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getAttendanceStamp().ifPresent(y -> y.getStamp().map(st -> {

										st.getTimeDay()
												.setTimeWithDay(Optional.ofNullable(workNo.getTimeZone().getEndTime()));
										st.getTimeDay().getReasonTimeChange()
												.setTimeChangeMeans(TimeChangeMeans.APPLICATION);
										lstItemId.add(appTimeType == AppTimeType.ATWORK ? 31 : 41);
										return st;
									}));
								} else if (checkRespectiveOffNo(workNo.getWorkNo().v(), appTimeType)) {
									x.getLeaveStamp().ifPresent(y -> y.getStamp().map(st -> {
										st.getTimeDay().setTimeWithDay(
												Optional.ofNullable(workNo.getTimeZone().getStartTime()));
										st.getTimeDay().getReasonTimeChange()
												.setTimeChangeMeans(TimeChangeMeans.APPLICATION);
										lstItemId.add(appTimeType == AppTimeType.OFFWORK ? 34 : 44);
										return st;
									}));
								}
								return x;
							}).collect(Collectors.toList());
				}

			}
		}

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	private boolean checkRespectiveAttNo(int no, AppTimeType appType) {
		if (no == 1 && appType == AppTimeType.ATWORK) {
			return true;
		}
		if (no == 2 && appType == AppTimeType.ATWORK2) {
			return true;
		}
		return false;
	}

	private boolean checkRespectiveOffNo(int no, AppTimeType appType) {
		if (no == 1 && appType == AppTimeType.OFFWORK) {
			return true;
		}
		if (no == 2 && appType == AppTimeType.OFFWORK2) {
			return true;
		}
		return false;
	}
}
