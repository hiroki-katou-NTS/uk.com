package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.calculationsetting.enums.GoBackOutCorrectionClass;
import nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.algorithm.CreateEmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.CorrectionResult;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;

/*
 * 外出系打刻漏れをチェックする
 */
@Stateless
public class GoingOutStampLeakageChecking {

	@Inject
	private OutingTimeOfDailyPerformanceRepository outingTimeOfDailyPerformanceRepository;

	@Inject
	private StampReflectionManagementRepository stampReflectionManagementRepository;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepository;

	@Inject
	private CreateEmployeeDailyPerError createEmployeeDailyPerError;

	public void goingOutStampLeakageChecking(String companID, String employeeID, GeneralDate processingDate) {

		Optional<OutingTimeOfDailyPerformance> outingTimeOfDailyPerformance = this.outingTimeOfDailyPerformanceRepository
				.findByEmployeeIdAndDate(employeeID, processingDate);

		if (outingTimeOfDailyPerformance.isPresent()) {

			List<OutingTimeSheet> outingTimeSheets = outingTimeOfDailyPerformance.get().getOutingTimeSheets();

			for (OutingTimeSheet outingTimeSheet : outingTimeSheets) {
				if (outingTimeSheet.getGoOut().getStamp() == null) {

					List<Integer> attendanceItemIDList = new ArrayList<>();

					if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((1)))) {
						attendanceItemIDList.add(88);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((2)))) {
						attendanceItemIDList.add(95);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((3)))) {
						attendanceItemIDList.add(102);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((4)))) {
						attendanceItemIDList.add(109);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((5)))) {
						attendanceItemIDList.add(116);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((6)))) {
						attendanceItemIDList.add(123);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((7)))) {
						attendanceItemIDList.add(130);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((8)))) {
						attendanceItemIDList.add(137);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((9)))) {
						attendanceItemIDList.add(144);
					} else if (outingTimeSheet.getOutingFrameNo().equals(new WorkNo((10)))) {
						attendanceItemIDList.add(151);
					}

					// 外出に打刻漏れ補正する
					CorrectionResult correctionResult = stampLeakageCorrect(companID, employeeID, processingDate,
							outingTimeSheet);
					if (correctionResult == CorrectionResult.FAILURE) {
						createEmployeeDailyPerError.createEmployeeDailyPerError(companID, employeeID, processingDate,
								new ErrorAlarmWorkRecordCode("S001"), attendanceItemIDList);
					}
				}

				if (outingTimeSheet.getComeBack().getStamp() == null) {

					List<Integer> newAttendanceItemIDList = new ArrayList<>();

					if (outingTimeSheet.getComeBack().equals(new WorkNo((1)))) {
						newAttendanceItemIDList.add(91);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((2)))) {
						newAttendanceItemIDList.add(98);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((3)))) {
						newAttendanceItemIDList.add(105);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((4)))) {
						newAttendanceItemIDList.add(112);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((5)))) {
						newAttendanceItemIDList.add(119);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((6)))) {
						newAttendanceItemIDList.add(126);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((7)))) {
						newAttendanceItemIDList.add(133);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((8)))) {
						newAttendanceItemIDList.add(140);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((9)))) {
						newAttendanceItemIDList.add(147);
					} else if (outingTimeSheet.getComeBack().equals(new WorkNo((10)))) {
						newAttendanceItemIDList.add(154);
					}

					// 戻りに打刻漏れ補正する
					CorrectionResult returnCorrectionResult = returnStampLeakageCorrect(companID, employeeID,
							processingDate, outingTimeSheet);
					if (returnCorrectionResult == CorrectionResult.FAILURE) {
						createEmployeeDailyPerError.createEmployeeDailyPerError(companID, employeeID, processingDate,
								new ErrorAlarmWorkRecordCode("S001"), newAttendanceItemIDList);
					}
				}

			}
		}

	}

	private CorrectionResult stampLeakageCorrect(String companyId, String employeeID, GeneralDate processingDate,
			OutingTimeSheet outingTimeSheet) {
		CorrectionResult correctionResult = CorrectionResult.SUCCESS;

		Optional<StampReflectionManagement> stampReflectionManagement = stampReflectionManagementRepository
				.findByCid(companyId);

		if (stampReflectionManagement.isPresent()) {
			GoBackOutCorrectionClass goBackOutCorrectionClass = stampReflectionManagement.get()
					.getGoBackOutCorrectionClass();
			if (goBackOutCorrectionClass == GoBackOutCorrectionClass.DO_NOT_CORRECT) {
				correctionResult = CorrectionResult.FAILURE;
			} else {
				// 漏れ補正に使う退勤を確認する
				WorkStamp stamp = leakageCorrection(companyId, employeeID, processingDate,
						outingTimeSheet.getComeBack());
				if (stamp != null) {
					correctionResult = CorrectionResult.SUCCESS;
				} else {
					correctionResult = CorrectionResult.FAILURE;
				}
			}
		}

		return correctionResult;
	}

	private WorkStamp leakageCorrection(String companyId, String employeeID, GeneralDate processingDate,
			TimeActualStamp comeBack) {
		// 漏れ補正打刻をクリア
		WorkStamp stamp = null;

		Optional<TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformance = this.timeLeavingOfDailyPerformanceRepository
				.findByKey(employeeID, processingDate);

		if (timeLeavingOfDailyPerformance.isPresent()) {
			List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.get().getTimeLeavingWorks();

			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks) {
				if ((timeLeavingWork.getAttendanceStamp().getStamp() != null)
						&& (timeLeavingWork.getLeaveStamp().getStamp() != null)) {
					if (timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay()
							.lessThanOrEqualTo(comeBack.getStamp().get().getTimeWithDay())
							&& timeLeavingWork.getLeaveStamp().getStamp().get().getTimeWithDay()
									.greaterThanOrEqualTo(comeBack.getStamp().get().getTimeWithDay())) {
						if (timeLeavingWork.getAttendanceStamp().getStamp().get()
								.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT
								|| timeLeavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION
								|| timeLeavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION_BUTTON) {							
							stamp = new WorkStamp(timeLeavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime(),
									timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay(),
									timeLeavingWork.getAttendanceStamp().getStamp().get().getLocationCode(),
									StampSourceInfo.STAMP_LEAKAGE_CORRECTION);
						}
					}

				}
			}
		}

		Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOfDailyPerformance = this.temporaryTimeOfDailyPerformanceRepository
				.findByKey(employeeID, processingDate);

		if (temporaryTimeOfDailyPerformance.isPresent()) {
			List<TimeLeavingWork> leavingWorks = temporaryTimeOfDailyPerformance.get().getTimeLeavingWorks();

			for (TimeLeavingWork leavingWork : leavingWorks) {
				if (leavingWork.getAttendanceStamp().getStamp() != null
						&& leavingWork.getLeaveStamp().getStamp() != null) {
					if (leavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay()
							.lessThanOrEqualTo(comeBack.getStamp().get().getTimeWithDay())
							&& leavingWork.getLeaveStamp().getStamp().get().getTimeWithDay()
									.greaterThanOrEqualTo(comeBack.getStamp().get().getTimeWithDay())) {
						if (leavingWork.getAttendanceStamp().getStamp().get()
								.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT
								|| leavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION
								|| leavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION_BUTTON) {
							stamp = new WorkStamp(leavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime(), 
									leavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay(), 
									leavingWork.getAttendanceStamp().getStamp().get().getLocationCode(), 
									StampSourceInfo.STAMP_LEAKAGE_CORRECTION);
						}
					}
				}

			}
		}
		return stamp;
	}

	private CorrectionResult returnStampLeakageCorrect(String companyId, String employeeID, GeneralDate processingDate,
			OutingTimeSheet outingTimeSheet) {
		CorrectionResult correctionResult = CorrectionResult.SUCCESS;

		Optional<StampReflectionManagement> stampReflectionManagement = stampReflectionManagementRepository
				.findByCid(companyId);

		if (stampReflectionManagement.isPresent()) {
			GoBackOutCorrectionClass goBackOutCorrectionClass = stampReflectionManagement.get()
					.getGoBackOutCorrectionClass();
			if (goBackOutCorrectionClass == GoBackOutCorrectionClass.DO_NOT_CORRECT) {
				correctionResult = CorrectionResult.FAILURE;
			} else {
				// 漏れ補正に使う退勤を確認する
				WorkStamp stamp = leaveWorkLeakageCorrection(companyId, employeeID, processingDate,
						outingTimeSheet.getGoOut());
				if (stamp != null) {
					correctionResult = CorrectionResult.SUCCESS;
				} else {
					correctionResult = CorrectionResult.FAILURE;
				}
			}
		}

		return correctionResult;
	}

	private WorkStamp leaveWorkLeakageCorrection(String companyId, String employeeID, GeneralDate processingDate,
			TimeActualStamp goOut) {
		// 漏れ補正打刻をクリア
		WorkStamp stamp = null;

		Optional<TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformance = this.timeLeavingOfDailyPerformanceRepository
				.findByKey(employeeID, processingDate);
		if (timeLeavingOfDailyPerformance.isPresent()) {
			List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.get().getTimeLeavingWorks();

			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks) {
				if ((timeLeavingWork.getAttendanceStamp().getStamp() != null)
						&& (timeLeavingWork.getLeaveStamp().getStamp() != null)) {
					if (timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay()
							.lessThanOrEqualTo(goOut.getStamp().get().getTimeWithDay())
							&& timeLeavingWork.getLeaveStamp().getStamp().get().getTimeWithDay()
									.greaterThanOrEqualTo(goOut.getStamp().get().getTimeWithDay())) {
						if (timeLeavingWork.getAttendanceStamp().getStamp().get()
								.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT
								|| timeLeavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION
								|| timeLeavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION_BUTTON) {							
							stamp = new WorkStamp(timeLeavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime(), 
									timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay(), 
									timeLeavingWork.getAttendanceStamp().getStamp().get().getLocationCode(), 
									StampSourceInfo.STAMP_LEAKAGE_CORRECTION);
						}
					}
				}

			}
		}

		Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOfDailyPerformance = this.temporaryTimeOfDailyPerformanceRepository
				.findByKey(employeeID, processingDate);

		if (temporaryTimeOfDailyPerformance.isPresent()) {
			List<TimeLeavingWork> leavingWorks = temporaryTimeOfDailyPerformance.get().getTimeLeavingWorks();

			for (TimeLeavingWork leavingWork : leavingWorks) {
				if (leavingWork.getAttendanceStamp().getStamp() != null
						&& leavingWork.getLeaveStamp().getStamp() != null) {
					if (leavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay()
							.lessThanOrEqualTo(goOut.getStamp().get().getTimeWithDay())
							&& leavingWork.getLeaveStamp().getStamp().get().getTimeWithDay()
									.greaterThanOrEqualTo(goOut.getStamp().get().getTimeWithDay())) {
						if (leavingWork.getAttendanceStamp().getStamp().get()
								.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT
								|| leavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION
								|| leavingWork.getAttendanceStamp().getStamp().get()
										.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION_BUTTON) {							
							stamp = new WorkStamp(leavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime(), 
									leavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay(), 
									leavingWork.getAttendanceStamp().getStamp().get().getLocationCode(), 
									StampSourceInfo.STAMP_LEAKAGE_CORRECTION);
						}
					}
				}

			}
		}

		return stamp;
	}
}
