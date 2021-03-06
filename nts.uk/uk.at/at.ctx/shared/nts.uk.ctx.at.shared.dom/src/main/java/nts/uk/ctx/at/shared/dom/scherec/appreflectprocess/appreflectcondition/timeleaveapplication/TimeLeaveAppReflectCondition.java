package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.timeleaveapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
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
 * ?????????????????????????????????
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveAppReflectCondition extends DomainObject {
	/**
	 * 60H??????
	 */
	private NotUseAtr superHoliday60H;

	/**
	 * ??????
	 */
	private NotUseAtr nursing;

	/**
	 * ?????????
	 */
	private NotUseAtr childNursing;

	/**
	 * ????????????
	 */
	private NotUseAtr substituteLeaveTime;

	/**
	 * ????????????
	 */
	private NotUseAtr annualVacationTime;

	/**
	 * ??????????????????
	 */
	private NotUseAtr specialVacationTime;

	/**
	 * @author thanh_nx
	 *
	 *         ????????????????????????????????????
	 */
	public DailyRecordOfApplication process(TimeDigestApplicationShare timeDigestApp,
			DailyRecordOfApplication dailyApp) {

		// ?????????????????????????????????????????????
		TimeDigestApplicationShare timeDigestCheck = this.check(timeDigestApp);

		// ????????????????????????????????????
		this.processTimeDigest(timeDigestCheck, dailyApp);
		return dailyApp;

	}

	/**
	 * @author thanh_nx
	 *
	 *         ????????????????????????????????????
	 */
	public DailyRecordOfApplication processTimeDigest(TimeDigestApplicationShare timeDigestApp,
			DailyRecordOfApplication dailyApp) {

		// [input.??????????????????(work???.????????????]???????????????(work???????????????
		if (this.getAnnualVacationTime() == NotUseAtr.USE) {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getAnnual()
						.setDigestionUseTime(timeDigestApp.getTimeAnnualLeave());
			});
		}
		if (this.getSubstituteLeaveTime() == NotUseAtr.USE) {
			// [input.??????????????????(work???.????????????]???????????????(work???????????????
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getSubstitute()
						.setDigestionUseTime(timeDigestApp.getTimeOff());
			});
		}

		if (this.getSpecialVacationTime() == NotUseAtr.USE) {
			// [input.??????????????????(work???.??????????????????]???????????????(work???????????????
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getSpecialHoliday()
						.setDigestionUseTime(timeDigestApp.getTimeSpecialVacation());
			});
		}

		if (this.getSuperHoliday60H() == NotUseAtr.USE) {
			// [input.??????????????????(work???.60H??????]???????????????(work???????????????
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				x.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily().getOverSalary()
						.setDigestionUseTime(timeDigestApp.getOvertime60H());
			});
		}
		// ???????????????????????????
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, Arrays.asList(540, 542, 546));

		return dailyApp;
	}
		
	/**
	 * @author thanh_nx
	 *
	 *         ????????????????????????????????????????????????
	 */

	public DailyAfterAppReflectResult process(List<TimeLeaveApplicationDetailShare> appTimeLeavDetail,
			DailyRecordOfApplication dailyApp, NotUseAtr reflectActualTimeZone) {

		List<Integer> lstItemId = new ArrayList<Integer>();
		// input.???????????????????????????List???????????????
		for (TimeLeaveApplicationDetailShare detail : appTimeLeavDetail) {

			// ?????????????????????????????????????????????
			TimeDigestApplicationShare timeDigest = check(detail.getTimeDigestApplication());

			// ????????????????????????????????????
			lstItemId.addAll(process(dailyApp, detail.getAppTimeType(), timeDigest).getLstItemId());

			// [????????????(work???.??????????????????]?????????????????????
			if (dailyApp.getClassification() == ScheduleRecordClassifi.RECORD
					&& reflectActualTimeZone == NotUseAtr.NOT_USE) {
				continue;
			}

			// ??????????????????????????????
			lstItemId.addAll(processVacationTimeZone(detail.getAppTimeType(),
					detail.getTimeZoneWithWorkNoLst(), dailyApp).getLstItemId());

		}

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	/**
	 * @author thanh_nx
	 *
	 *         ?????????????????????????????????????????????
	 */

	public TimeDigestApplicationShare check(TimeDigestApplicationShare timeDigestApplication) {

		// ?????????????????????????????????work???????????????
		TimeDigestApplicationShare timeDigestInit = new TimeDigestApplicationShare(new AttendanceTime(0),
				new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0),
				new AttendanceTime(0), Optional.empty());

		// [????????????]???????????????
		if (this.getAnnualVacationTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeAnnualLeave(timeDigestApplication.getTimeAnnualLeave());
		}

		// [????????????]???????????????
		if (this.getSubstituteLeaveTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeOff(timeDigestApplication.getTimeOff());
		}

		// [??????????????????]???????????????
		if (this.getSpecialVacationTime() == NotUseAtr.USE) {
			timeDigestInit.setTimeSpecialVacation(timeDigestApplication.getTimeSpecialVacation());
			timeDigestInit.setSpecialVacationFrameNO(timeDigestApplication.getSpecialVacationFrameNO());
		}

		// [60H??????]???????????????
		if (this.getSuperHoliday60H() == NotUseAtr.USE) {
			timeDigestInit.setOvertime60H(timeDigestApplication.getOvertime60H());
		}

		// [?????????]???????????????
		if (this.getChildNursing() == NotUseAtr.USE) {
			timeDigestInit.setChildTime(timeDigestApplication.getChildTime());
		}

		// [??????]???????????????
		if (this.getNursing() == NotUseAtr.USE) {
			timeDigestInit.setNursingTime(timeDigestApplication.getNursingTime());
		}

		return timeDigestInit;
	}

	/**
	 * @author thanh_nx
	 *
	 *         ????????????????????????????????????
	 */

	public DailyAfterAppReflectResult process(DailyRecordOfApplication dailyApp, AppTimeType appTimeType,
			TimeDigestApplicationShare timeDigest) {

		// [input. ???????????????]???????????????
		List<Integer> lstItemId = new ArrayList<>();
		if (appTimeType == AppTimeType.ATWORK || appTimeType == AppTimeType.ATWORK2) {
			dailyApp.getAttendanceTimeOfDailyPerformance().ifPresent(x -> {
				// [input. ??????????????????(work)]??? ????????????(work??????????????????????????????
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
			// [input. ??????????????????(work)]??? ????????????(work??????????????????????????????
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
				// [input.??????????????????(work??????????????????(work??????????????????]????????????
				val goutReason = appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION;
				val outingEdit = x.getOutingTimeOfDaily().stream().filter(y -> y.getReason() == goutReason).collect(Collectors.toList());
				if (outingEdit.isEmpty()) {
					outingEdit.add(OutingTimeOfDaily.createDefaultWithReason(goutReason));
					x.getOutingTimeOfDaily().addAll(outingEdit);
				}
				for (OutingTimeOfDaily data : outingEdit) {
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

		// ???????????????????????????
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);

		return new DailyAfterAppReflectResult(dailyApp, lstItemId);
	}

	// ??????????????????????????????????????????????????????
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
	 *         ??????????????????????????????
	 */

	public DailyAfterAppReflectResult processVacationTimeZone(AppTimeType appTimeType, List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst,
			DailyRecordOfApplication dailyApp) {
		List<Integer> lstItemId = new ArrayList<Integer>();

		// [input.????????????????????????List???]????????????
		for (TimeZoneWithWorkNo workNo : timeZoneWithWorkNoLst) {

			if (appTimeType == AppTimeType.PRIVATE || appTimeType == AppTimeType.UNION) {
				// ????????????(work????????????????????????????????????
				if (!dailyApp.getOutingTime().isPresent()) {
					dailyApp.setOutingTime(Optional.of(new OutingTimeOfDailyAttd(new ArrayList<>())));
				}

				if (!dailyApp.getOutingTime().get().getOutingTimeSheets().stream()
						.filter(x -> x.getOutingFrameNo().v() == workNo.getWorkNo().v()).findFirst().isPresent()) {
					dailyApp.getOutingTime().get().getOutingTimeSheets().add(OutingTimeSheet.createDefaultWithNo(
							workNo.getWorkNo().v(),
							appTimeType == AppTimeType.PRIVATE ? GoingOutReason.PRIVATE : GoingOutReason.UNION));
				}

				// ????????????????????????(work???????????????????????????????????????
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

				// ????????????(work??????????????????????????????
				if (!dailyApp.getAttendanceLeave().isPresent()) {
					dailyApp.setAttendanceLeave(
							Optional.of(new TimeLeavingOfDailyAttd(new ArrayList<>(), new WorkTimes(0))));
				}

				// ?????????????????????.??????NO????????????[?????????]???????????????
				if (!dailyApp.getAttendanceLeave().get().getAttendanceLeavingWork(workNo.getWorkNo().v()).isPresent()
						&& (checkRespectiveAttNo(workNo.getWorkNo().v(), appTimeType)
								|| checkRespectiveOffNo(workNo.getWorkNo().v(), appTimeType))) {
					dailyApp.getAttendanceLeave().get().getTimeLeavingWorks().add(
							TimeLeavingWork.createDefaultWithNo(workNo.getWorkNo().v(), TimeChangeMeans.APPLICATION));
				}

				if (dailyApp.getClassification() == ScheduleRecordClassifi.SCHEDULE) {
					// ????????????????????????(work?????????????????????????????????????????????
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
					// ????????????????????????(work?????????????????????????????????
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

		// ???????????????????????????
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
