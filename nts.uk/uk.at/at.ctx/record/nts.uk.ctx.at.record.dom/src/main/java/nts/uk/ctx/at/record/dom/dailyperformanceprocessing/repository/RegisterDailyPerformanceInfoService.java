package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.DailyRecordAdUpService;
import nts.uk.ctx.at.record.dom.daily.DailyRecordTransactionService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectStampOutput;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo.InsertWorkInfoOfDailyPerforService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class RegisterDailyPerformanceInfoService {

	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepository;

	@Inject
	private InsertWorkInfoOfDailyPerforService insertWorkInfoOfDailyPerforService;
	
	@Inject
	private DailyRecordTransactionService dailyTransaction;
	
	@Inject
	private DailyRecordAdUpService dailyRecordAdUpService;

	@Inject
	private StampDakokuRepository stampDakokuRepository;
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registerDailyPerformanceInfo(String companyId, String employeeID, GeneralDate day,
			ReflectStampOutput stampOutput, AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformanceUpdate,
			SpecificDateAttrOfDailyPerfor specificDateAttrOfDailyPerfor,
			CalAttrOfDailyPerformance calAttrOfDailyPerformance,
			BreakTimeOfDailyPerformance breakTimeOfDailyPerformance) {
    	
    	AffiliationInforOfDailyPerfor dailyPerfor = new AffiliationInforOfDailyPerfor(employeeID, day, affiliationInforOfDailyPerfor.getAffiliationInfor());
    	SpecificDateAttrOfDailyPerfor attrOfDailyPerfor = new SpecificDateAttrOfDailyPerfor(employeeID, day, specificDateAttrOfDailyPerfor.getSpecificDay());

		// 登録する - register - activity ⑤社員の日別実績を作成する
		// ドメインモデル「日別実績の勤務情報」を更新する - update
		// WorkInfoOfDailyPerformance - JDBC
		if (workInfoOfDailyPerformanceUpdate != null) {
			this.insertWorkInfoOfDailyPerforService.updateWorkInfoOfDailyPerforService(companyId, employeeID, day,
					workInfoOfDailyPerformanceUpdate);
			//dailyRecordAdUpService.adUpWorkInfo(workInfoOfDailyPerformanceUpdate);
		} else {
			dailyTransaction.updated(employeeID, day);
		}

		// ドメインモデル「日別実績の所属情報」を更新する - update
		// AffiliationInforOfDailyPerformance
		if (affiliationInforOfDailyPerfor != null) {
//			if (this.affiliationInforOfDailyPerforRepository.findByKey(employeeID, day).isPresent()) {
//				this.affiliationInforOfDailyPerforRepository.updateByKey(affiliationInforOfDailyPerfor);
//			} else {
//				this.affiliationInforOfDailyPerforRepository.add(affiliationInforOfDailyPerfor);
//			}
			dailyRecordAdUpService.adUpAffilicationInfo(dailyPerfor);
		}

		// ドメインモデル「日別実績の休憩時間帯」を更新する
		// BreakTimeOfDailyPerformance
		if (breakTimeOfDailyPerformance != null && !breakTimeOfDailyPerformance.getTimeZone().getBreakTimeSheets().isEmpty()) {
			if (this.breakTimeOfDailyPerformanceRepository.find(employeeID, day, 1).isPresent()) {
				this.breakTimeOfDailyPerformanceRepository.updateForEachOfType(breakTimeOfDailyPerformance);
			} else {
				this.breakTimeOfDailyPerformanceRepository.insert(breakTimeOfDailyPerformance);
				//dailyRecordAdUpService.adUpBreakTime(Arrays.asList(breakTimeOfDailyPerformance));
			}
		}

		// ドメインモデル「日別実績の特定日区分」を更新する (Update 「日別実績の特定日区分」)
		// specificDateAttrOfDailyPerfor - JDBC - new wave
		if (specificDateAttrOfDailyPerfor != null) {
//			if (this.specificDateAttrOfDailyPerforRepo.find(employeeID, day).isPresent()) {
//				this.specificDateAttrOfDailyPerforRepo.update(specificDateAttrOfDailyPerfor);
//			} else {
//				this.specificDateAttrOfDailyPerforRepo.add(specificDateAttrOfDailyPerfor);
//			}
			dailyRecordAdUpService.adUpSpecificDate(Optional.of(attrOfDailyPerfor));
		}

		// ドメインモデル「日別実績の計算区分」を更新する (Update 「日別実績の計算区分」)
		// calAttrOfDailyPerformance - JDBC newwave
		CalAttrOfDailyPerformance attrOfDailyPerformance = new CalAttrOfDailyPerformance(employeeID, day, calAttrOfDailyPerformance.getCalcategory());
		if (calAttrOfDailyPerformance != null) {
//			if (this.calAttrOfDailyPerformanceRepository.find(employeeID, day) != null) {
//				this.calAttrOfDailyPerformanceRepository.update(calAttrOfDailyPerformance);
//			} else {
//				this.calAttrOfDailyPerformanceRepository.add(calAttrOfDailyPerformance);
//			}
			dailyRecordAdUpService.adUpCalAttr(attrOfDailyPerformance);
		}
		
		//ドメインモデル「日別実績の休憩時間帯」を更新する
		if (stampOutput != null) {
			// breakTimeOfDailyPerformance - JDBC only insert
			if (stampOutput.getBreakTimeOfDailyPerformance() != null
					&& !stampOutput.getBreakTimeOfDailyPerformance().getTimeZone().getBreakTimeSheets().isEmpty()) {
				if (this.breakTimeOfDailyPerformanceRepository.find(employeeID, day, 0).isPresent()) {
					this.breakTimeOfDailyPerformanceRepository.updateForEachOfType(stampOutput.getBreakTimeOfDailyPerformance());
				} else {
					this.breakTimeOfDailyPerformanceRepository.insert(stampOutput.getBreakTimeOfDailyPerformance());
					//dailyRecordAdUpService.adUpBreakTime(Arrays.asList(stampOutput.getBreakTimeOfDailyPerformance()));
				}
				//dailyRecordAdUpService.adUpBreakTime(Arrays.asList(stampOutput.getBreakTimeOfDailyPerformance()));
			}

			// ドメインモデル「日別実績の外出時間帯」を更新する (Update 「日別実績の外出時間帯」) - JDBC only insert
			if (stampOutput.getOutingTimeOfDailyPerformance() != null) {
//				if (this.outingTimeOfDailyPerformanceRepository.findByEmployeeIdAndDate(employeeID, day).isPresent()) {
//					this.outingTimeOfDailyPerformanceRepository.update(stampOutput.getOutingTimeOfDailyPerformance());
//				} else {
//					this.outingTimeOfDailyPerformanceRepository.add(stampOutput.getOutingTimeOfDailyPerformance());
//				}
				dailyRecordAdUpService.adUpOutTime(Optional.of(stampOutput.getOutingTimeOfDailyPerformance()));
			}

			// ドメインモデル「日別実績の臨時出退勤」を更新する (Update 「日別実績の臨時出退勤」) - JDBC - tín
			if (stampOutput.getTemporaryTimeOfDailyPerformance() != null) {
//				if (this.temporaryTimeOfDailyPerformanceRepository.findByKey(employeeID, day).isPresent()) {
//					this.temporaryTimeOfDailyPerformanceRepository
//							.update(stampOutput.getTemporaryTimeOfDailyPerformance());
//				} else {
//					this.temporaryTimeOfDailyPerformanceRepository
//							.insert(stampOutput.getTemporaryTimeOfDailyPerformance());
//				}
				dailyRecordAdUpService.adUpTemporaryTime(Optional.of(stampOutput.getTemporaryTimeOfDailyPerformance()));
			}

			// ドメインモデル「日別実績の出退勤」を更新する (Update 「日別実績の出退勤」) - JDBC only insert - tín
			if (stampOutput.getTimeLeavingOfDailyPerformance() != null
					&& stampOutput.getTimeLeavingOfDailyPerformance().getAttendance().getTimeLeavingWorks() != null
					&& !stampOutput.getTimeLeavingOfDailyPerformance().getAttendance().getTimeLeavingWorks().isEmpty()) {
//				if (this.timeLeavingOfDailyPerformanceRepository.findByKey(employeeID, day).isPresent()) {
//					this.timeLeavingOfDailyPerformanceRepository.update(stampOutput.getTimeLeavingOfDailyPerformance());
//				} else {
//					this.timeLeavingOfDailyPerformanceRepository.insert(stampOutput.getTimeLeavingOfDailyPerformance());
//				}
				dailyRecordAdUpService.adUpTimeLeaving(Optional.of(stampOutput.getTimeLeavingOfDailyPerformance()));
			}
			
			// ドメインモデル「日別実績の短時間勤務時間帯」を更新する - JDBC - newwave
			if (stampOutput.getShortTimeOfDailyPerformance() != null) {
//				if (this.shortTimeOfDailyPerformanceRepository.find(employeeID, day).isPresent()) {
//					this.shortTimeOfDailyPerformanceRepository
//							.updateByKey(stampOutput.getShortTimeOfDailyPerformance());
//				} else {
//					this.shortTimeOfDailyPerformanceRepository.insert(stampOutput.getShortTimeOfDailyPerformance());
//				}
				dailyRecordAdUpService.adUpShortTime(Optional.of(stampOutput.getShortTimeOfDailyPerformance()));
			}

			// ドメインモデル「打刻」を更新する (Update 「打刻」) - JDBC
			if (stampOutput.getLstStamp() != null && !stampOutput.getLstStamp().isEmpty()) {
				stampOutput.getLstStamp().forEach(stampItem -> {
//					if(stampItem.getAttendanceTime().v() >= 1440) {
//						stampItem.setAttendanceTime(new AttendanceTime(stampItem.getAttendanceTime().v() - 1440));
//					}
//					this.stampRepository.updateStampItem(stampItem);
					this.stampDakokuRepository.update(stampItem);
					
				});
			}

			// ドメインモデル「日別実績の入退門」を更新する (Update 「日別実績の入退門」) - JDBC only insert - tín
			if (stampOutput.getAttendanceLeavingGateOfDaily() != null
					&& stampOutput.getAttendanceLeavingGateOfDaily().getTimeZone().getAttendanceLeavingGates() != null
					&& !stampOutput.getAttendanceLeavingGateOfDaily().getTimeZone().getAttendanceLeavingGates().isEmpty()) {
//				if (this.attendanceLeavingGateOfDailyRepo.find(employeeID, day).isPresent()) {
//					this.attendanceLeavingGateOfDailyRepo.update(stampOutput.getAttendanceLeavingGateOfDaily());
//				} else {
//					this.attendanceLeavingGateOfDailyRepo.add(stampOutput.getAttendanceLeavingGateOfDaily());
//				}
				dailyRecordAdUpService.adUpAttendanceLeavingGate(Optional.of(stampOutput.getAttendanceLeavingGateOfDaily()));
			}

			// ドメインモデル「日別実績のPCログオン情報」を更新する (Update 「日別実績のPCログオン情報」)) - JDBC only insert - tín
			if (stampOutput.getPcLogOnInfoOfDaily() != null
					&& stampOutput.getPcLogOnInfoOfDaily().getTimeZone().getLogOnInfo() != null
					&& !stampOutput.getPcLogOnInfoOfDaily().getTimeZone().getLogOnInfo().isEmpty()) {
//				if (this.pCLogOnInfoOfDailyRepo.find(employeeID, day).isPresent()) {
//					this.pCLogOnInfoOfDailyRepo.update(stampOutput.getPcLogOnInfoOfDaily());
//				} else {
//					this.pCLogOnInfoOfDailyRepo.add(stampOutput.getPcLogOnInfoOfDaily());
//				}
				dailyRecordAdUpService.adUpPCLogOn(Optional.of(stampOutput.getPcLogOnInfoOfDaily()));
			}
			
			//ドメインモデル「社員の日別実績エラー一覧」を更新する
			if (stampOutput.getEmployeeDailyPerErrorList() != null && !stampOutput.getEmployeeDailyPerErrorList().isEmpty()) {
				List<EmployeeDailyPerError> errors = stampOutput.getEmployeeDailyPerErrorList().stream()
						.filter(x -> x != null).collect(Collectors.toList());
				dailyRecordAdUpService.adUpEmpError(stampOutput.getEmployeeDailyPerErrorList(),
						errors.stream().map(x -> Pair.of(x.getEmployeeID(), x.getDate())).collect(Collectors.toList()),
						true);
//				for(EmployeeDailyPerError dailyPerError : stampOutput.getEmployeeDailyPerErrorList()){
//					if (dailyPerError != null) {
//						this.createEmployeeDailyPerError.createEmployeeError(dailyPerError);
//					}
//					
//				}
			}
		}
	}

}
