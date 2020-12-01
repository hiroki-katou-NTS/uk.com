package nts.uk.ctx.at.record.dom.require;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.shift.pattern.GetPredWorkingDaysAdaptor;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.WorkTypeOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.DailyCalculationEmployeeService;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSettingService;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetYearAndMultiMonthAgreementTime;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.dom.monthly.updatedomain.UpdateAllDomainMonthService;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalAggregateService;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInforRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLogRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.cancelactuallock.CancelActualLock;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess.MonthlyClosureUpdateLogProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.monthlyupdatemgr.MonthlyUpdateMgr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updateremainnum.RemainAnnualLeaveUpdating;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.ymupdate.ProcessYearMonthUpdate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationService;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CreateTempAnnLeaMngProc;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.DetermineActualResultLock;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.AppRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ScheRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveTimeRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.CalcAnnLeaAttendanceRate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantTimeRemainHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RsvLeaveGrantTimeRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByApplicationData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByScheData;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.MonthlyCalculationByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.wkp.WkpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.AggregateSpecifiedDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.getprocessingdate.GetProcessingDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.EditStateOfMonthlyPerRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.CalcFlexChangeDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.CheckBeforeCalcFlexChangeService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flex.ConditionCalcResult;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimitRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMntRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe.OuenAggregateFrameSetOfMonthlyRepo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff.MonthlyDayoffRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlexRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpConditionRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Formula;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrderRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.DailyStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.CalcNextAnnLeaGrantInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrantProcKdm002;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;

@Stateless
public class RecordDomRequireService {

	@Inject
	private TmpResereLeaveMngRepository tmpResereLeaveMngRepo;
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	@Inject
	private RervLeaGrantRemDataRepository rervLeaGrantRemDataRepo;
	@Inject
	private WorkInformationRepository workInformationRepo;
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;
	@Inject
	private LengthServiceRepository lengthServiceRepository;
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	@Inject
	private TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepo;
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo;
	@Inject
	private AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo;
	@Inject
	private ClosureStatusManagementRepository closureStatusManagementRepo;
	@Inject
	private AnnLeaMaxDataRepository annLeaMaxDataRepo;
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	@Inject
	private EmploymentHistAdapter employmentHistAdapter;
	@Inject
	private RemainCreateInforByScheData remainCreateInforByScheData;
	@Inject
	private RemainCreateInforByRecordData remainCreateInforByRecordData;
	@Inject
	private RemainCreateInforByApplicationData remainCreateInforByApplicationData;
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepo;
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepo;
	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepo;
	@Inject
	private SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDailyPerforRepo;
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;
	@Inject
	private AnyItemValueOfDailyRepo anyItemValueOfDailyRepo;
	@Inject
	private PCLogOnInfoOfDailyRepo pcLogOnInfoOfDailyRepo;
	@Inject
	private WorkTypeOfDailyPerforRepository workTypeOfDailyPerforRepo;
	@Inject
	private AttendanceTimeRepository attendanceTimeRepo;
	@Inject
	private PayItemCountOfMonthlyRepository payItemCountOfMonthlyRepo;
	@Inject
	private OptionalItemRepository optionalItemRepo;
	@Inject
	private EmpConditionRepository empConditionRepo;
	@Inject
	private FormulaRepository formulaRepo;
	@Inject
	private FormulaDispOrderRepository formulaDispOrderRepo;
	@Inject
	private ActualLockRepository actualLockRepo;
	@Inject
	private LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthlyRepo;
	@Inject
	private RoleOvertimeWorkRepository roleOvertimeWorkRepo;
	@Inject
	private HolidayAddtionRepository holidayAddtionRepo;
	@Inject
	private MonthlyAggrSetOfFlexRepository monthlyAggrSetOfFlexRepo;
	@Inject
	private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo;
	@Inject
	private InsufficientFlexHolidayMntRepository insufficientFlexHolidayMntRepo;
	@Inject
	private FlexShortageLimitRepository flexShortageLimitRepo;
	@Inject
	private RoundingSetOfMonthlyRepository roundingSetOfMonthlyRepo;
	@Inject
	private TotalTimesRepository totalTimesRepo;
	@Inject
	private AgreementOperationSettingRepository agreementOperationSettingRepo;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepo;
	@Inject
	private ManagedParallelWithContext parallel;
	@Inject
	private CheckBeforeCalcFlexChangeService checkBeforeCalcFlexChangeService;
	@Inject
	private CompanyAdapter companyAdapter;
	@Inject
	private AnyItemOfMonthlyRepository anyItemOfMonthlyRepo;
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo;
	@Inject
	private EditStateOfMonthlyPerRepository editStateOfMonthlyPerRepo;
	/** 並列処理用 */
	@Resource
	private ManagedExecutorService executorService;
	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepo;
	@Inject
	private SpecialHolidayRepository specialHolidayRepo;
	@Inject
	private AttendanceItemConvertFactory converterFactory;
	@Inject
	private GetPredWorkingDaysAdaptor predWorkingDaysAdaptor;
	@Inject
	private UpdateAllDomainMonthService updateAllDomainMonthService;
	@Inject
	private AgreementUnitSettingRepository agreementUnitSetRepo;
	@Inject
	private AgreementTimeOfWorkPlaceRepository agreementTimeWorkPlaceRepo;
	@Inject
	private AffClassificationAdapter affClassficationAdapter;
	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;
	@Inject
	private AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepo;
	@Inject
	private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepo;
	@Inject
	private BasicAgreementSettingRepository basicAgreementSettingRepo;
	@Inject
	private AgreementTimeCompanyRepository agreementTimeCompanyRepo;
	@Inject
	private RemainMergeRepository remainMergeRepo;
	@Inject
	private AgreementYearSettingRepository agreementYearSettingRepo;
	@Inject
	private AgreementMonthSettingRepository agreementMonthSettingRepo;
	@Inject
	private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;
	@Inject
	private TargetPersonRepository targetPersonRepo;
	@Inject
	private ErrMessageInfoRepository errMessageInfoRepo;
	@Inject
	private InterimRemainRepository interimRemainRepo;
	@Inject
	private BasicScheduleAdapter basicScheduleAdapter;
	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepo;
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepo;
	@Inject
	private LeaveManaDataRepository leaveManaDataRepo;
	@Inject
	private InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;
	@Inject
	private SpecialLeaveGrantRepository specialLeaveGrantRepo;
	@Inject
	private AnnualLeaveTimeRemainHistRepository annualLeaveTimeRemainHistRepo;
	@Inject
	private AnnualLeaveMaxHistRepository annualLeaveMaxHistRepo;
	@Inject
	private RsvLeaveGrantRemainHistRepository rsvLeaveGrantRemainHistRepo;
	@Inject
	private RsvLeaveGrantTimeRemainHistRepository rsvLeaveGrantTimeRemainHistRepo;
	@Inject
	private InterimRecAbasMngRepository interimRecAbasMngRepo;
	@Inject
	private InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo;
	@Inject
	private InterimRemainOffMonthProcess interimRemainOffMonthProcess;
	@Inject
	private MonthlyClosureUpdateErrorInforRepository monthlyClosureUpdateErrorInforRepo;
	@Inject
	private MonthlyClosureUpdateLogRepository monthlyClosureUpdateLogRepo;
	@Inject
	private MonthlyClosureUpdatePersonLogRepository monthlyClosureUpdatePersonLogRepo;
	@Inject
	private OuenWorkTimeSheetOfDailyRepo ouenWorkTimeSheetOfDailyRepo;
	@Inject
	private OuenWorkTimeOfDailyRepo ouenWorkTimeOfDailyRepo;
	@Inject
	private OuenAggregateFrameSetOfMonthlyRepo ouenAggregateFrameSetOfMonthlyRepo;
	@Inject
	private RegularLaborTimeComRepo regularLaborTimeComRepo;
	@Inject
	private DeforLaborTimeComRepo deforLaborTimeComRepo;
	@Inject
	private RegularLaborTimeWkpRepo regularLaborTimeWkpRepo;
	@Inject
	private DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;
	@Inject
	private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;
	@Inject
	private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;
	@Inject
	private RegularLaborTimeShaRepo regularLaborTimeShaRepo;
	@Inject
	private DeforLaborTimeShaRepo deforLaborTimeShaRepo;
	@Inject
	private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;
	@Inject
	private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;
	@Inject
	private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;
	@Inject
	private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;
	@Inject
	private EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;
	@Inject
	private EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;
	@Inject
	private ComDeforLaborMonthActCalSetRepo comDeforLaborMonthActCalSetRepo;
	@Inject
	private ComRegulaMonthActCalSetRepo comRegulaMonthActCalSetRepo;
	@Inject
	private ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo;
	@Inject
	private ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo;
	@Inject
	private WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo;
	@Inject
	private WkpRegulaMonthActCalSetRepo wkpRegulaMonthActCalSetRepo;
	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	@Inject
	private ExecutionLogRepository executionLogRepo;
	@Inject
	private DetermineActualResultLock lockStatusService;
	@Inject
	protected ComSubstVacationRepository comSubstVacationRepo;
	@Inject
	protected CompensLeaveComSetRepository compensLeaveComSetRepo;
	@Inject
	protected EmpEmployeeAdapter empEmployeeAdapter;
	@Inject
	protected GrantDateTblRepository grantDateTblRepo;
	@Inject
	protected AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	@Inject
	protected SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo;
	@Inject
	protected EmpSubstVacationRepository empSubstVacationRepo;
	@Inject
	protected ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	protected WorkingConditionItemRepository workingConditionItemRepo;
	@Inject
	protected WorkingConditionRepository workingConditionRepo;
	@Inject
	protected WorkTimeSettingRepository workTimeSettingRepo;
	@Inject
	protected FixedWorkSettingRepository fixedWorkSettingRepo;
	@Inject
	protected FlowWorkSettingRepository flowWorkSettingRepo;
	@Inject
	protected DiffTimeWorkSettingRepository diffTimeWorkSettingRepo;
	@Inject
	protected FlexWorkSettingRepository flexWorkSettingRepo;
	@Inject
	protected ClosureRepository closureRepo;
	@Inject
	protected ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	protected WorkTypeRepository workTypeRepo;
	@Inject
	protected CompensLeaveEmSetRepository compensLeaveEmSetRepo;
	@Inject
	protected EmploymentSettingRepository employmentSettingRepo;
	@Inject
	protected RetentionYearlySettingRepository retentionYearlySettingRepo;
	@Inject
	protected AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;
	@Inject
	protected OutsideOTSettingRepository outsideOTSettingRepo;
	@Inject
	protected WorkdayoffFrameRepository workdayoffFrameRepo;
	@Inject
	protected YearHolidayRepository yearHolidayRepo;
	@Inject
	protected SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;
	@Inject
	protected RoleOfOpenPeriodRepository roleOfOpenPeriodRepo;
	@Inject
	private VerticalTotalMethodOfMonthlyRepository verticalTotalMethodOfMonthlyRepo;
	@Inject
	private StampCardRepository stampCardRepo;
	@Inject
	private BentoReservationRepository bentoReservationRepo;
	@Inject
	private BentoMenuRepository bentoMenuRepo;
	@Inject
	private WeekRuleManagementRepo weekRuleManagementRepo;
	@Inject
	private DailyCalculationEmployeeService dailyCalculationEmployeeService;
	@Inject
	private GetProcessingDate getProcessingDate;

	// Require の不整合によるエラー
	public static interface Require extends RemainNumberTempRequireService.Require, GetAnnAndRsvRemNumWithinPeriod.RequireM2,
		CalcAnnLeaAttendanceRate.RequireM3, GetClosurePeriod.RequireM1, GetClosureStartForEmployee.RequireM1,
		CalcNextAnnLeaGrantInfo.RequireM1, GetNextAnnualLeaveGrantProcKdm002.RequireM1,
		GetYearAndMultiMonthAgreementTime.RequireM1,
		InterimRemainOffPeriodCreateData.RequireM2, DailyStatutoryLaborTime.RequireM1,
		AggregateMonthlyRecordService.RequireM1, MonAggrCompanySettings.RequireM6, WorkTimeIsFluidWork.RequireM2,
		MonAggrEmployeeSettings.RequireM2, MonthlyCalculationByPeriod.RequireM1, GetClosurePeriod.RequireM2,
		VerticalTotalOfMonthly.RequireM1, TotalCountByPeriod.RequireM1,
		GetAgreementTime.RequireM4, WorkingConditionService.RequireM1, MonthlyAggregationService.RequireM1,
		AgeementTimeCommonSettingService.RequireM1, CreateTempAnnLeaMngProc.RequireM3,
		AggregateSpecifiedDailys.RequireM1, ClosureService.RequireM6, ClosureService.RequireM5,
		MonthlyUpdateMgr.RequireM4, MonthlyClosureUpdateLogProcess.RequireM3, CancelActualLock.RequireM1,
		ProcessYearMonthUpdate.RequireM1, BreakDayOffMngInPeriodQuery.RequireM2, AgreementDomainService.RequireM5,
		AgreementDomainService.RequireM6, GetAgreementTime.RequireM5, VerticalTotalAggregateService.RequireM1,
		GetExcessTimesYear.RequireM2, SpecialLeaveManagementService.RequireM5,
		FlexTimeOfMonthly.RequireM3, AggregateMonthlyRecordServiceProc.RequireM2,
		MonthlyOldDatas.RequireM1, MonthlyAggregationService.RequireM2,
		RemainAnnualLeaveUpdating.RequireM3
		{

		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);

	}

	public Require createRequire() {
		return new RequireImpl(comSubstVacationRepo, compensLeaveComSetRepo,
				specialLeaveGrantRepo, empEmployeeAdapter, grantDateTblRepo,
				annLeaEmpBasicInfoRepo, specialHolidayRepo, interimSpecialHolidayMngRepo,
				specialLeaveBasicInfoRepo, interimRecAbasMngRepo, empSubstVacationRepo,
				interimRemainRepo, substitutionOfHDManaDataRepo, payoutManagementDataRepo,
				interimBreakDayOffMngRepo, comDayOffManaDataRepo, companyAdapter,
				shareEmploymentAdapter, leaveManaDataRepo, workingConditionItemRepo,
				workingConditionRepo, workTimeSettingRepo, fixedWorkSettingRepo,
				flowWorkSettingRepo, diffTimeWorkSettingRepo, flexWorkSettingRepo,
				predetemineTimeSettingRepo, closureRepo, closureEmploymentRepo,
				workTypeRepo, remainCreateInforByApplicationData,
				compensLeaveEmSetRepo, employmentSettingRepo,
				retentionYearlySettingRepo, annualPaidLeaveSettingRepo,
				outsideOTSettingRepo, workdayoffFrameRepo, yearHolidayRepo,
				tmpResereLeaveMngRepo, sysEmploymentHisAdapter,
				rervLeaGrantRemDataRepo, workInformationRepo,
				annLeaRemNumEachMonthRepo, lengthServiceRepository,
				grantYearHolidayRepo, tmpAnnualHolidayMngRepo,
				attendanceTimeOfMonthlyRepo, operationStartSetDailyPerformRepo,
				annualLeaveRemainHistRepo, closureStatusManagementRepo,
				annLeaMaxDataRepo, annLeaGrantRemDataRepo, employmentHistAdapter,
				remainCreateInforByScheData, remainCreateInforByRecordData,
				remainCreateInforByApplicationData, usageUnitSettingRepo,
				affWorkplaceAdapter, timeLeavingOfDailyPerformanceRepo,
				temporaryTimeOfDailyPerformanceRepo, specificDateAttrOfDailyPerforRepo,
				employeeDailyPerErrorRepo, anyItemValueOfDailyRepo,
				pcLogOnInfoOfDailyRepo, workTypeOfDailyPerforRepo,
				attendanceTimeRepo, payItemCountOfMonthlyRepo, optionalItemRepo,
				empConditionRepo, formulaRepo, formulaDispOrderRepo,
				actualLockRepo, legalTransferOrderSetOfAggrMonthlyRepo,
				roleOvertimeWorkRepo, holidayAddtionRepo, monthlyAggrSetOfFlexRepo,
				getFlexPredWorkTimeRepo, insufficientFlexHolidayMntRepo,
				flexShortageLimitRepo, roundingSetOfMonthlyRepo, totalTimesRepo,
				agreementOperationSettingRepo, predetemineTimeSettingRepo,
				parallel, checkBeforeCalcFlexChangeService, companyAdapter,
				anyItemOfMonthlyRepo, empCalAndSumExeLogRepo,
				editStateOfMonthlyPerRepo, executorService,
				affiliationInforOfDailyPerforRepo, specialHolidayRepo,
				converterFactory, predWorkingDaysAdaptor, updateAllDomainMonthService,
				agreementUnitSetRepo, agreementTimeWorkPlaceRepo,
				affClassficationAdapter, syEmploymentAdapter,
				agreementTimeOfEmploymentRepo, agreementTimeOfClassificationRepo,
				basicAgreementSettingRepo, agreementTimeCompanyRepo, remainMergeRepo,
				agreementYearSettingRepo, agreementMonthSettingRepo,
				agreementTimeOfManagePeriodRepo, targetPersonRepo, errMessageInfoRepo,
				interimRemainRepo, basicScheduleAdapter,
				substitutionOfHDManaDataRepo, payoutManagementDataRepo,
				comDayOffManaDataRepo, leaveManaDataRepo, interimBreakDayOffMngRepo,
				specialLeaveGrantRepo, annualLeaveTimeRemainHistRepo,
				annualLeaveMaxHistRepo, rsvLeaveGrantRemainHistRepo,
				rsvLeaveGrantTimeRemainHistRepo, interimRecAbasMngRepo,
				interimSpecialHolidayMngRepo, interimRemainOffMonthProcess,
				monthlyClosureUpdateErrorInforRepo, monthlyClosureUpdateLogRepo,
				monthlyClosureUpdatePersonLogRepo, ouenWorkTimeSheetOfDailyRepo,
				ouenWorkTimeOfDailyRepo, ouenAggregateFrameSetOfMonthlyRepo,
				regularLaborTimeComRepo, deforLaborTimeComRepo,
				regularLaborTimeWkpRepo, deforLaborTimeWkpRepo,
				regularLaborTimeEmpRepo, deforLaborTimeEmpRepo,
				regularLaborTimeShaRepo, deforLaborTimeShaRepo,
				shaFlexMonthActCalSetRepo, comFlexMonthActCalSetRepo,
				empFlexMonthActCalSetRepo, wkpFlexMonthActCalSetRepo,
				empDeforLaborMonthActCalSetRepo, empRegulaMonthActCalSetRepo,
				comDeforLaborMonthActCalSetRepo, comRegulaMonthActCalSetRepo,
				shaDeforLaborMonthActCalSetRepo, shaRegulaMonthActCalSetRepo,
				wkpDeforLaborMonthActCalSetRepo, wkpRegulaMonthActCalSetRepo,
				monthlyWorkTimeSetRepo, executionLogRepo,
				lockStatusService, verticalTotalMethodOfMonthlyRepo, stampCardRepo,
				bentoReservationRepo, bentoMenuRepo, dailyCalculationEmployeeService,
				weekRuleManagementRepo, sharedAffWorkPlaceHisAdapter, getProcessingDate);
	}

	public static class RequireImpl extends RemainNumberTempRequireService.RequireImp implements Require {

		public RequireImpl(ComSubstVacationRepository comSubstVacationRepo,
				CompensLeaveComSetRepository compensLeaveComSetRepo, SpecialLeaveGrantRepository specialLeaveGrantRepo,
				EmpEmployeeAdapter empEmployeeAdapter, GrantDateTblRepository grantDateTblRepo,
				AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo, SpecialHolidayRepository specialHolidayRepo,
				InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo,
				SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo,
				InterimRecAbasMngRepository interimRecAbasMngRepo, EmpSubstVacationRepository empSubstVacationRepo,
				InterimRemainRepository interimRemainRepo,
				SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo,
				PayoutManagementDataRepository payoutManagementDataRepo,
				InterimBreakDayOffMngRepository interimBreakDayOffMngRepo,
				ComDayOffManaDataRepository comDayOffManaDataRepo, CompanyAdapter companyAdapter,
				ShareEmploymentAdapter shareEmploymentAdapter, LeaveManaDataRepository leaveManaDataRepo,
				WorkingConditionItemRepository workingConditionItemRepo,
				WorkingConditionRepository workingConditionRepo, WorkTimeSettingRepository workTimeSettingRepo,
				FixedWorkSettingRepository fixedWorkSettingRepo, FlowWorkSettingRepository flowWorkSettingRepo,
				DiffTimeWorkSettingRepository diffTimeWorkSettingRepo, FlexWorkSettingRepository flexWorkSettingRepo,
				PredetemineTimeSettingRepository predetemineTimeSettingRepo, ClosureRepository closureRepo,
				ClosureEmploymentRepository closureEmploymentRepo, WorkTypeRepository workTypeRepo,
				RemainCreateInforByApplicationData remainCreateInforByApplicationData,
				CompensLeaveEmSetRepository compensLeaveEmSetRepo, EmploymentSettingRepository employmentSettingRepo,
				RetentionYearlySettingRepository retentionYearlySettingRepo,
				AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo,
				OutsideOTSettingRepository outsideOTSettingRepo, WorkdayoffFrameRepository workdayoffFrameRepo,
				YearHolidayRepository yearHolidayRepo, TmpResereLeaveMngRepository tmpResereLeaveMngRepo,
				SysEmploymentHisAdapter sysEmploymentHisAdapter, RervLeaGrantRemDataRepository rervLeaGrantRemDataRepo,
				WorkInformationRepository workInformationRepo,
				AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo,
				LengthServiceRepository lengthServiceRepository, GrantYearHolidayRepository grantYearHolidayRepo,
				TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepo,
				AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo,
				OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo,
				AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo,
				ClosureStatusManagementRepository closureStatusManagementRepo,
				AnnLeaMaxDataRepository annLeaMaxDataRepo, AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo,
				EmploymentHistAdapter employmentHistAdapter, RemainCreateInforByScheData remainCreateInforByScheData,
				RemainCreateInforByRecordData remainCreateInforByRecordData,
				RemainCreateInforByApplicationData remainCreateInforByApplicationData2,
				UsageUnitSettingRepository usageUnitSettingRepo, AffWorkplaceAdapter affWorkplaceAdapter,
				TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepo,
				TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepo,
				SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDailyPerforRepo,
				EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo,
				AnyItemValueOfDailyRepo anyItemValueOfDailyRepo, PCLogOnInfoOfDailyRepo pcLogOnInfoOfDailyRepo,
				WorkTypeOfDailyPerforRepository workTypeOfDailyPerforRepo, AttendanceTimeRepository attendanceTimeRepo,
				PayItemCountOfMonthlyRepository payItemCountOfMonthlyRepo, OptionalItemRepository optionalItemRepo,
				EmpConditionRepository empConditionRepo, FormulaRepository formulaRepo,
				FormulaDispOrderRepository formulaDispOrderRepo, ActualLockRepository actualLockRepo,
				LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthlyRepo,
				RoleOvertimeWorkRepository roleOvertimeWorkRepo, HolidayAddtionRepository holidayAddtionRepo,
				MonthlyAggrSetOfFlexRepository monthlyAggrSetOfFlexRepo,
				GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo,
				InsufficientFlexHolidayMntRepository insufficientFlexHolidayMntRepo,
				FlexShortageLimitRepository flexShortageLimitRepo,
				RoundingSetOfMonthlyRepository roundingSetOfMonthlyRepo, TotalTimesRepository totalTimesRepo,
				AgreementOperationSettingRepository agreementOperationSettingRepo,
				PredetemineTimeSettingRepository predetemineTimeSettingRepo2, ManagedParallelWithContext parallel,
				CheckBeforeCalcFlexChangeService checkBeforeCalcFlexChangeService, CompanyAdapter companyAdapter2,
				AnyItemOfMonthlyRepository anyItemOfMonthlyRepo, EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo,
				EditStateOfMonthlyPerRepository editStateOfMonthlyPerRepo, ManagedExecutorService executorService,
				AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepo,
				SpecialHolidayRepository specialHolidayRepo2, AttendanceItemConvertFactory converterFactory,
				GetPredWorkingDaysAdaptor predWorkingDaysAdaptor,
				UpdateAllDomainMonthService updateAllDomainMonthService,
				AgreementUnitSettingRepository agreementUnitSetRepo,
				AgreementTimeOfWorkPlaceRepository agreementTimeWorkPlaceRepo,
				AffClassificationAdapter affClassficationAdapter, SyEmploymentAdapter syEmploymentAdapter,
				AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepo,
				AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepo,
				BasicAgreementSettingRepository basicAgreementSettingRepo,
				AgreementTimeCompanyRepository agreementTimeCompanyRepo, RemainMergeRepository remainMergeRepo,
				AgreementYearSettingRepository agreementYearSettingRepo,
				AgreementMonthSettingRepository agreementMonthSettingRepo,
				AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo,
				TargetPersonRepository targetPersonRepo, ErrMessageInfoRepository errMessageInfoRepo,
				InterimRemainRepository interimRemainRepo2, BasicScheduleAdapter basicScheduleAdapter,
				SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo2,
				PayoutManagementDataRepository payoutManagementDataRepo2,
				ComDayOffManaDataRepository comDayOffManaDataRepo2, LeaveManaDataRepository leaveManaDataRepo2,
				InterimBreakDayOffMngRepository interimBreakDayOffMngRepo2,
				SpecialLeaveGrantRepository specialLeaveGrantRepo2,
				AnnualLeaveTimeRemainHistRepository annualLeaveTimeRemainHistRepo,
				AnnualLeaveMaxHistRepository annualLeaveMaxHistRepo,
				RsvLeaveGrantRemainHistRepository rsvLeaveGrantRemainHistRepo,
				RsvLeaveGrantTimeRemainHistRepository rsvLeaveGrantTimeRemainHistRepo,
				InterimRecAbasMngRepository interimRecAbasMngRepo2,
				InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo2,
				InterimRemainOffMonthProcess interimRemainOffMonthProcess,
				MonthlyClosureUpdateErrorInforRepository monthlyClosureUpdateErrorInforRepo,
				MonthlyClosureUpdateLogRepository monthlyClosureUpdateLogRepo,
				MonthlyClosureUpdatePersonLogRepository monthlyClosureUpdatePersonLogRepo,
				OuenWorkTimeSheetOfDailyRepo ouenWorkTimeSheetOfDailyRepo,
				OuenWorkTimeOfDailyRepo ouenWorkTimeOfDailyRepo,
				OuenAggregateFrameSetOfMonthlyRepo ouenAggregateFrameSetOfMonthlyRepo,
				RegularLaborTimeComRepo regularLaborTimeComRepo, DeforLaborTimeComRepo deforLaborTimeComRepo,
				RegularLaborTimeWkpRepo regularLaborTimeWkpRepo, DeforLaborTimeWkpRepo deforLaborTimeWkpRepo,
				RegularLaborTimeEmpRepo regularLaborTimeEmpRepo, DeforLaborTimeEmpRepo deforLaborTimeEmpRepo,
				RegularLaborTimeShaRepo regularLaborTimeShaRepo, DeforLaborTimeShaRepo deforLaborTimeShaRepo,
				ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo,
				ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo,
				EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo,
				WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo,
				EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo,
				EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo,
				ComDeforLaborMonthActCalSetRepo comDeforLaborMonthActCalSetRepo,
				ComRegulaMonthActCalSetRepo comRegulaMonthActCalSetRepo,
				ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo,
				ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo,
				WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo,
				WkpRegulaMonthActCalSetRepo wkpRegulaMonthActCalSetRepo, MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo,
				ExecutionLogRepository executionLogRepo,
				DetermineActualResultLock lockStatusService,
				VerticalTotalMethodOfMonthlyRepository verticalTotalMethodOfMonthlyRepo,
				StampCardRepository stampCardRepo,
				BentoReservationRepository bentoReservationRepo,
				BentoMenuRepository bentoMenuRepo, DailyCalculationEmployeeService dailyCalculationEmployeeService,
				WeekRuleManagementRepo weekRuleManagementRepo, SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter,
				GetProcessingDate getProcessingDate) {

			super(comSubstVacationRepo, compensLeaveComSetRepo, specialLeaveGrantRepo, empEmployeeAdapter,
					grantDateTblRepo, annLeaEmpBasicInfoRepo, specialHolidayRepo, interimSpecialHolidayMngRepo,
					specialLeaveBasicInfoRepo, interimRecAbasMngRepo, empSubstVacationRepo, interimRemainRepo,
					substitutionOfHDManaDataRepo, payoutManagementDataRepo, interimBreakDayOffMngRepo,
					comDayOffManaDataRepo, companyAdapter, shareEmploymentAdapter, leaveManaDataRepo,
					workingConditionItemRepo, workingConditionRepo, workTimeSettingRepo, fixedWorkSettingRepo,
					flowWorkSettingRepo, diffTimeWorkSettingRepo, flexWorkSettingRepo, predetemineTimeSettingRepo,
					closureRepo, closureEmploymentRepo, workTypeRepo, remainCreateInforByApplicationData,
					compensLeaveEmSetRepo, employmentSettingRepo, retentionYearlySettingRepo,
					annualPaidLeaveSettingRepo, outsideOTSettingRepo, workdayoffFrameRepo,
					yearHolidayRepo, usageUnitSettingRepo, regularLaborTimeComRepo,
					deforLaborTimeComRepo, regularLaborTimeWkpRepo, deforLaborTimeWkpRepo,
					regularLaborTimeEmpRepo, deforLaborTimeEmpRepo, regularLaborTimeShaRepo,
					deforLaborTimeShaRepo, sharedAffWorkPlaceHisAdapter);

			this.tmpResereLeaveMngRepo = tmpResereLeaveMngRepo;
			this.sysEmploymentHisAdapter = sysEmploymentHisAdapter;
			this.rervLeaGrantRemDataRepo = rervLeaGrantRemDataRepo;
			this.workInformationRepo = workInformationRepo;
			this.annLeaRemNumEachMonthRepo = annLeaRemNumEachMonthRepo;
			this.lengthServiceRepository = lengthServiceRepository;
			this.grantYearHolidayRepo = grantYearHolidayRepo;
			this.tmpAnnualHolidayMngRepo = tmpAnnualHolidayMngRepo;
			this.attendanceTimeOfMonthlyRepo = attendanceTimeOfMonthlyRepo;
			this.operationStartSetDailyPerformRepo = operationStartSetDailyPerformRepo;
			this.annualLeaveRemainHistRepo = annualLeaveRemainHistRepo;
			this.closureStatusManagementRepo = closureStatusManagementRepo;
			this.annLeaMaxDataRepo = annLeaMaxDataRepo;
			this.annLeaGrantRemDataRepo = annLeaGrantRemDataRepo;
			this.employmentHistAdapter = employmentHistAdapter;
			this.remainCreateInforByScheData = remainCreateInforByScheData;
			this.remainCreateInforByRecordData = remainCreateInforByRecordData;
			this.remainCreateInforByApplicationData = remainCreateInforByApplicationData2;
			this.usageUnitSettingRepo = usageUnitSettingRepo;
			this.affWorkplaceAdapter = affWorkplaceAdapter;
			this.timeLeavingOfDailyPerformanceRepo = timeLeavingOfDailyPerformanceRepo;
			this.temporaryTimeOfDailyPerformanceRepo = temporaryTimeOfDailyPerformanceRepo;
			this.specificDateAttrOfDailyPerforRepo = specificDateAttrOfDailyPerforRepo;
			this.employeeDailyPerErrorRepo = employeeDailyPerErrorRepo;
			this.anyItemValueOfDailyRepo = anyItemValueOfDailyRepo;
			this.pcLogOnInfoOfDailyRepo = pcLogOnInfoOfDailyRepo;
			this.workTypeOfDailyPerforRepo = workTypeOfDailyPerforRepo;
			this.attendanceTimeRepo = attendanceTimeRepo;
			this.payItemCountOfMonthlyRepo = payItemCountOfMonthlyRepo;
			this.optionalItemRepo = optionalItemRepo;
			this.empConditionRepo = empConditionRepo;
			this.formulaRepo = formulaRepo;
			this.formulaDispOrderRepo = formulaDispOrderRepo;
			this.actualLockRepo = actualLockRepo;
			this.legalTransferOrderSetOfAggrMonthlyRepo = legalTransferOrderSetOfAggrMonthlyRepo;
			this.roleOvertimeWorkRepo = roleOvertimeWorkRepo;
			this.holidayAddtionRepo = holidayAddtionRepo;
			this.monthlyAggrSetOfFlexRepo = monthlyAggrSetOfFlexRepo;
			this.getFlexPredWorkTimeRepo = getFlexPredWorkTimeRepo;
			this.insufficientFlexHolidayMntRepo = insufficientFlexHolidayMntRepo;
			this.flexShortageLimitRepo = flexShortageLimitRepo;
			this.roundingSetOfMonthlyRepo = roundingSetOfMonthlyRepo;
			this.totalTimesRepo = totalTimesRepo;
			this.agreementOperationSettingRepo = agreementOperationSettingRepo;
			this.predetemineTimeSettingRepo = predetemineTimeSettingRepo2;
			this.parallel = parallel;
			this.checkBeforeCalcFlexChangeService = checkBeforeCalcFlexChangeService;
			this.companyAdapter = companyAdapter2;
			this.anyItemOfMonthlyRepo = anyItemOfMonthlyRepo;
			this.empCalAndSumExeLogRepo = empCalAndSumExeLogRepo;
			this.editStateOfMonthlyPerRepo = editStateOfMonthlyPerRepo;
			this.executorService = executorService;
			this.affiliationInforOfDailyPerforRepo = affiliationInforOfDailyPerforRepo;
			this.specialHolidayRepo = specialHolidayRepo2;
			this.converterFactory = converterFactory;
			this.predWorkingDaysAdaptor = predWorkingDaysAdaptor;
			this.updateAllDomainMonthService = updateAllDomainMonthService;
			this.agreementUnitSetRepo = agreementUnitSetRepo;
			this.agreementTimeWorkPlaceRepo = agreementTimeWorkPlaceRepo;
			this.affClassficationAdapter = affClassficationAdapter;
			this.syEmploymentAdapter = syEmploymentAdapter;
			this.agreementTimeOfEmploymentRepo = agreementTimeOfEmploymentRepo;
			this.agreementTimeOfClassificationRepo = agreementTimeOfClassificationRepo;
			this.basicAgreementSettingRepo = basicAgreementSettingRepo;
			this.agreementTimeCompanyRepo = agreementTimeCompanyRepo;
			this.remainMergeRepo = remainMergeRepo;
			this.agreementYearSettingRepo = agreementYearSettingRepo;
			this.agreementMonthSettingRepo = agreementMonthSettingRepo;
			this.agreementTimeOfManagePeriodRepo = agreementTimeOfManagePeriodRepo;
			this.targetPersonRepo = targetPersonRepo;
			this.errMessageInfoRepo = errMessageInfoRepo;
			this.interimRemainRepo = interimRemainRepo2;
			this.basicScheduleAdapter = basicScheduleAdapter;
			this.substitutionOfHDManaDataRepo = substitutionOfHDManaDataRepo2;
			this.payoutManagementDataRepo = payoutManagementDataRepo2;
			this.comDayOffManaDataRepo = comDayOffManaDataRepo2;
			this.leaveManaDataRepo = leaveManaDataRepo2;
			this.interimBreakDayOffMngRepo = interimBreakDayOffMngRepo2;
			this.specialLeaveGrantRepo = specialLeaveGrantRepo2;
			this.annualLeaveTimeRemainHistRepo = annualLeaveTimeRemainHistRepo;
			this.annualLeaveMaxHistRepo = annualLeaveMaxHistRepo;
			this.rsvLeaveGrantRemainHistRepo = rsvLeaveGrantRemainHistRepo;
			this.rsvLeaveGrantTimeRemainHistRepo = rsvLeaveGrantTimeRemainHistRepo;
			this.interimRecAbasMngRepo = interimRecAbasMngRepo2;
			this.interimSpecialHolidayMngRepo = interimSpecialHolidayMngRepo2;
			this.interimRemainOffMonthProcess = interimRemainOffMonthProcess;
			this.monthlyClosureUpdateErrorInforRepo = monthlyClosureUpdateErrorInforRepo;
			this.monthlyClosureUpdateLogRepo = monthlyClosureUpdateLogRepo;
			this.monthlyClosureUpdatePersonLogRepo = monthlyClosureUpdatePersonLogRepo;
			this.ouenWorkTimeSheetOfDailyRepo = ouenWorkTimeSheetOfDailyRepo;
			this.ouenWorkTimeOfDailyRepo = ouenWorkTimeOfDailyRepo;
			this.ouenAggregateFrameSetOfMonthlyRepo = ouenAggregateFrameSetOfMonthlyRepo;
			this.regularLaborTimeComRepo = regularLaborTimeComRepo;
			this.deforLaborTimeComRepo = deforLaborTimeComRepo;
			this.regularLaborTimeWkpRepo = regularLaborTimeWkpRepo;
			this.deforLaborTimeWkpRepo = deforLaborTimeWkpRepo;
			this.regularLaborTimeEmpRepo = regularLaborTimeEmpRepo;
			this.deforLaborTimeEmpRepo = deforLaborTimeEmpRepo;
			this.regularLaborTimeShaRepo = regularLaborTimeShaRepo;
			this.deforLaborTimeShaRepo = deforLaborTimeShaRepo;
			this.shaFlexMonthActCalSetRepo = shaFlexMonthActCalSetRepo;
			this.comFlexMonthActCalSetRepo = comFlexMonthActCalSetRepo;
			this.empFlexMonthActCalSetRepo = empFlexMonthActCalSetRepo;
			this.wkpFlexMonthActCalSetRepo = wkpFlexMonthActCalSetRepo;
			this.empDeforLaborMonthActCalSetRepo = empDeforLaborMonthActCalSetRepo;
			this.empRegulaMonthActCalSetRepo = empRegulaMonthActCalSetRepo;
			this.comDeforLaborMonthActCalSetRepo = comDeforLaborMonthActCalSetRepo;
			this.comRegulaMonthActCalSetRepo = comRegulaMonthActCalSetRepo;
			this.shaDeforLaborMonthActCalSetRepo = shaDeforLaborMonthActCalSetRepo;
			this.shaRegulaMonthActCalSetRepo = shaRegulaMonthActCalSetRepo;
			this.wkpDeforLaborMonthActCalSetRepo = wkpDeforLaborMonthActCalSetRepo;
			this.wkpRegulaMonthActCalSetRepo = wkpRegulaMonthActCalSetRepo;
			this.monthlyWorkTimeSetRepo = monthlyWorkTimeSetRepo;
			this.executionLogRepo = executionLogRepo;
			this.lockStatusService = lockStatusService;
			this.verticalTotalMethodOfMonthlyRepo = verticalTotalMethodOfMonthlyRepo;
			this.stampCardRepo = stampCardRepo;
			this.bentoReservationRepo = bentoReservationRepo;
			this.bentoMenuRepo = bentoMenuRepo;
			this.weekRuleManagementRepo = weekRuleManagementRepo;
			this.dailyCalculationEmployeeService = dailyCalculationEmployeeService;
			this.getProcessingDate = getProcessingDate;
		}
		private GetProcessingDate getProcessingDate;

		private RoleOfOpenPeriodRepository roleOfOpenPeriodRepo;

		private TmpResereLeaveMngRepository tmpResereLeaveMngRepo;

		private SysEmploymentHisAdapter sysEmploymentHisAdapter;

		private RervLeaGrantRemDataRepository rervLeaGrantRemDataRepo;

		private WorkInformationRepository workInformationRepo;

		private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;

		private LengthServiceRepository lengthServiceRepository;

		private GrantYearHolidayRepository grantYearHolidayRepo;

		private TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepo;

		private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;

		private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo;

		private AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo;

		private ClosureStatusManagementRepository closureStatusManagementRepo;

		private AnnLeaMaxDataRepository annLeaMaxDataRepo;

		private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;

		private EmploymentHistAdapter employmentHistAdapter;

		private RemainCreateInforByScheData remainCreateInforByScheData;

		private RemainCreateInforByRecordData remainCreateInforByRecordData;

		private RemainCreateInforByApplicationData remainCreateInforByApplicationData;

		private UsageUnitSettingRepository usageUnitSettingRepo;

		private AffWorkplaceAdapter affWorkplaceAdapter;

		private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepo;

		private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepo;

		private SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDailyPerforRepo;

		private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

		private AnyItemValueOfDailyRepo anyItemValueOfDailyRepo;

		private PCLogOnInfoOfDailyRepo pcLogOnInfoOfDailyRepo;

		private WorkTypeOfDailyPerforRepository workTypeOfDailyPerforRepo;

		private AttendanceTimeRepository attendanceTimeRepo;

		private PayItemCountOfMonthlyRepository payItemCountOfMonthlyRepo;

		private OptionalItemRepository optionalItemRepo;

		private EmpConditionRepository empConditionRepo;

		private FormulaRepository formulaRepo;

		private FormulaDispOrderRepository formulaDispOrderRepo;

		private ActualLockRepository actualLockRepo;

		private LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthlyRepo;

		private RoleOvertimeWorkRepository roleOvertimeWorkRepo;

		private HolidayAddtionRepository holidayAddtionRepo;

		private MonthlyAggrSetOfFlexRepository monthlyAggrSetOfFlexRepo;

		private GetFlexPredWorkTimeRepository getFlexPredWorkTimeRepo;

		private InsufficientFlexHolidayMntRepository insufficientFlexHolidayMntRepo;

		private FlexShortageLimitRepository flexShortageLimitRepo;

		private RoundingSetOfMonthlyRepository roundingSetOfMonthlyRepo;

		private TotalTimesRepository totalTimesRepo;

		private AgreementOperationSettingRepository agreementOperationSettingRepo;

		private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

		private ManagedParallelWithContext parallel;

		private CheckBeforeCalcFlexChangeService checkBeforeCalcFlexChangeService;

		private CompanyAdapter companyAdapter;

		private AnyItemOfMonthlyRepository anyItemOfMonthlyRepo;

		private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo;

		private EditStateOfMonthlyPerRepository editStateOfMonthlyPerRepo;

		private ManagedExecutorService executorService;

		private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepo;

		private SpecialHolidayRepository specialHolidayRepo;

		private AttendanceItemConvertFactory converterFactory;

		private GetPredWorkingDaysAdaptor predWorkingDaysAdaptor;

		private UpdateAllDomainMonthService updateAllDomainMonthService;

		private AgreementUnitSettingRepository agreementUnitSetRepo;

		private AgreementTimeOfWorkPlaceRepository agreementTimeWorkPlaceRepo;

		private AffClassificationAdapter affClassficationAdapter;

		private SyEmploymentAdapter syEmploymentAdapter;

		private AgreementTimeOfEmploymentRepostitory agreementTimeOfEmploymentRepo;

		private AgreementTimeOfClassificationRepository agreementTimeOfClassificationRepo;

		private BasicAgreementSettingRepository basicAgreementSettingRepo;

		private AgreementTimeCompanyRepository agreementTimeCompanyRepo;

		private RemainMergeRepository remainMergeRepo;

		private AgreementYearSettingRepository agreementYearSettingRepo;

		private AgreementMonthSettingRepository agreementMonthSettingRepo;

		private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;

		private TargetPersonRepository targetPersonRepo;

		private ErrMessageInfoRepository errMessageInfoRepo;

		private InterimRemainRepository interimRemainRepo;

		private BasicScheduleAdapter basicScheduleAdapter;

		private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;

		private PayoutManagementDataRepository payoutManagementDataRepo;

		private ComDayOffManaDataRepository comDayOffManaDataRepo;

		private LeaveManaDataRepository leaveManaDataRepo;

		private InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;

		private SpecialLeaveGrantRepository specialLeaveGrantRepo;

		private AnnualLeaveTimeRemainHistRepository annualLeaveTimeRemainHistRepo;

		private AnnualLeaveMaxHistRepository annualLeaveMaxHistRepo;

		private RsvLeaveGrantRemainHistRepository rsvLeaveGrantRemainHistRepo;

		private RsvLeaveGrantTimeRemainHistRepository rsvLeaveGrantTimeRemainHistRepo;

		private InterimRecAbasMngRepository interimRecAbasMngRepo;

		private InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo;

		private InterimRemainOffMonthProcess interimRemainOffMonthProcess;

		private MonthlyClosureUpdateErrorInforRepository monthlyClosureUpdateErrorInforRepo;

		private MonthlyClosureUpdateLogRepository monthlyClosureUpdateLogRepo;

		private MonthlyClosureUpdatePersonLogRepository monthlyClosureUpdatePersonLogRepo;

		private OuenWorkTimeSheetOfDailyRepo ouenWorkTimeSheetOfDailyRepo;

		private OuenWorkTimeOfDailyRepo ouenWorkTimeOfDailyRepo;

		private OuenAggregateFrameSetOfMonthlyRepo ouenAggregateFrameSetOfMonthlyRepo;

		private RegularLaborTimeComRepo regularLaborTimeComRepo;

		private DeforLaborTimeComRepo deforLaborTimeComRepo;

		private RegularLaborTimeWkpRepo regularLaborTimeWkpRepo;

		private DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;

		private RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;

		private DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;

		private RegularLaborTimeShaRepo regularLaborTimeShaRepo;

		private DeforLaborTimeShaRepo deforLaborTimeShaRepo;

		private ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo;

		private ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo;

		private EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo;

		private WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo;

		private EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo;

		private EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo;

		private ComDeforLaborMonthActCalSetRepo comDeforLaborMonthActCalSetRepo;

		private ComRegulaMonthActCalSetRepo comRegulaMonthActCalSetRepo;

		private ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo;

		private ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo;

		private WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo;

		private WkpRegulaMonthActCalSetRepo wkpRegulaMonthActCalSetRepo;

		private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;

		private ExecutionLogRepository executionLogRepo;

		private DetermineActualResultLock lockStatusService;

		private VerticalTotalMethodOfMonthlyRepository verticalTotalMethodOfMonthlyRepo;

		private StampCardRepository stampCardRepo;

		private BentoReservationRepository bentoReservationRepo;

		private BentoMenuRepository bentoMenuRepo;

		private WeekRuleManagementRepo weekRuleManagementRepo;

		private DailyCalculationEmployeeService dailyCalculationEmployeeService;

		@Override
		public Optional<SEmpHistoryImport> employeeEmploymentHis(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate baseDate) {
			return sysEmploymentHisAdapter.findSEmpHistBySidRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

		@Override
		public Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfos(String employeeId, DatePeriod datePeriod) {
			return workInformationRepo.findByPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getWorkInformation()));
		}

		@Override
		public Optional<GrantHdTbl> grantHdTbl(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
			return grantYearHolidayRepo.find(companyId, conditionNo, yearHolidayCode, grantNum);
		}

		@Override
		public Optional<OperationStartSetDailyPerform> dailyOperationStartSet(CompanyId companyId) {
			return operationStartSetDailyPerformRepo.findByCid(companyId);
		}

		@Override
		public List<EmploymentHistImport> employmentHistories(String employeeId) {
			return employmentHistAdapter.findByEmployeeIdOrderByStartDate(employeeId);
		}

		@Override
		public List<GrantHdTblSet> grantHdTblSets(String companyId) {
			return yearHolidayRepo.findAll(companyId);
		}

		@Override
		public Optional<GrantHdTblSet> grantHdTblSet(String companyId, String yearHolidayCode) {
			return yearHolidayRepo.findByCode(companyId, yearHolidayCode);
		}

		@Override
		public List<ScheRemainCreateInfor> scheRemainCreateInfor(CacheCarrier cacheCarrier, String cid, String sid,
				DatePeriod dateData) {
			return remainCreateInforByScheData.createRemainInfor(cacheCarrier, cid, sid, dateData);
		}

		@Override
		public List<RecordRemainCreateInfor> recordRemainCreateInfor(CacheCarrier cacheCarrier, String cid, String sid,
				DatePeriod dateData) {
			return remainCreateInforByRecordData.lstRecordRemainData(cacheCarrier, cid, sid, dateData);
		}

		@Override
		public List<AppRemainCreateInfor> appRemainCreateInfor(CacheCarrier cacheCarrier, String cid, String sid,
				DatePeriod dateData) {
			return remainCreateInforByApplicationData.lstRemainDataFromApp(cacheCarrier, cid, sid, dateData);
		}

		@Override
		public Optional<UsageUnitSetting> usageUnitSetting(String companyId) {
			return usageUnitSettingRepo.findByCompany(companyId);
		}

		@Override
		public Map<GeneralDate, TimeLeavingOfDailyAttd> dailyTimeLeavings(String employeeId, DatePeriod datePeriod) {
			return timeLeavingOfDailyPerformanceRepo.findbyPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAttendance()));
		}

		@Override
		public Map<GeneralDate, TemporaryTimeOfDailyAttd> dailyTemporaryTimes(String employeeId, DatePeriod datePeriod) {
			return temporaryTimeOfDailyPerformanceRepo.findbyPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAttendance()));
		}

		@Override
		public Map<GeneralDate, SpecificDateAttrOfDailyAttd> dailySpecificDates(String employeeId, DatePeriod datePeriod) {
			return specificDateAttrOfDailyPerforRepo.findByPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getSpecificDay()));
		}

		@Override
		public List<EmployeeDailyPerError> dailyEmpErrors(String employeeId, DatePeriod datePeriod) {
			return employeeDailyPerErrorRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
		}

		@Override
		public Map<GeneralDate, AnyItemValueOfDailyAttd> dailyAnyItems(List<String> employeeId, DatePeriod baseDate) {
			return anyItemValueOfDailyRepo.finds(employeeId, baseDate)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAnyItem()));
		}

		@Override
		public Map<GeneralDate, PCLogOnInfoOfDailyAttd> dailyPcLogons(List<String> employeeId, DatePeriod baseDate) {
			return pcLogOnInfoOfDailyRepo.finds(employeeId, baseDate)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTimeZone()));
		}

		@Override
		public Optional<WorkTypeOfDailyPerformance> dailyWorkType(String employeeId, GeneralDate ymd) {
			return workTypeOfDailyPerforRepo.findByKey(employeeId, ymd);
		}

		@Override
		public Map<GeneralDate, AttendanceTimeOfDailyAttendance> dailyAttendanceTimes(String employeeId, DatePeriod datePeriod) {
			return attendanceTimeRepo.findByPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTime()));
		}

		@Override
		public Optional<PayItemCountOfMonthly> monthPayItemCount(String companyId) {
			return payItemCountOfMonthlyRepo.find(companyId);
		}

		@Override
		public List<OptionalItem> optionalItems(String companyId) {
			return optionalItemRepo.findAll(companyId);
		}

		@Override
		public List<EmpCondition> employmentConditions(String companyId, List<Integer> optionalItemNoList) {
			return empConditionRepo.findAll(companyId, optionalItemNoList);
		}

		@Override
		public List<Formula> formulas(String companyId) {
			return formulaRepo.find(companyId);
		}

		@Override
		public List<FormulaDispOrder> formulaDispOrder(String companyId) {
			return formulaDispOrderRepo.findAll(companyId);
		}

		@Override
		public Map<String, List<LengthServiceTbl>> lengthServiceTbl(String companyId, List<String> yearHolidayCode) {
			return lengthServiceRepository.findByCode(companyId, yearHolidayCode);
		}

		@Override
		public List<LengthServiceTbl> lengthServiceTbl(String companyId, String yearHolidayCode) {
			return lengthServiceRepository.findByCode(companyId, yearHolidayCode);
		}

		@Override
		public List<EmptYearlyRetentionSetting> emptYearlyRetentionSet(String companyId) {
			return employmentSettingRepo.findAll(companyId);
		}

		@Override
		public Optional<LegalTransferOrderSetOfAggrMonthly> monthLegalTransferOrderCalcSet(String companyId) {
			return legalTransferOrderSetOfAggrMonthlyRepo.find(companyId);
		}

		@Override
		public List<RoleOvertimeWork> roleOvertimeWorks(String companyId) {
			return roleOvertimeWorkRepo.findByCID(companyId);
		}

		@Override
		public Map<String, AggregateRoot> holidayAddtionSets(String companyId) {
			return holidayAddtionRepo.findByCompanyId(companyId);
		}

		@Override
		public Optional<MonthlyAggrSetOfFlex> monthFlexAggrSet(String companyId) {
			return monthlyAggrSetOfFlexRepo.find(companyId);
		}

		@Override
		public Optional<GetFlexPredWorkTime> flexPredWorkTime(String companyId) {
			return getFlexPredWorkTimeRepo.find(companyId);
		}

		@Override
		public Optional<InsufficientFlexHolidayMnt> insufficientFlexHolidayMnt(String cid) {
			return insufficientFlexHolidayMntRepo.findByCId(cid);
		}

		@Override
		public Optional<FlexShortageLimit> flexShortageLimit(String companyId) {
			return flexShortageLimitRepo.get(companyId);
		}

		@Override
		public Optional<RoundingSetOfMonthly> monthRoundingSet(String companyId) {
			return roundingSetOfMonthlyRepo.find(companyId);
		}

		@Override
		public List<TotalTimes> totalTimes(String companyId) {
			return totalTimesRepo.getAllTotalTimes(companyId);
		}

		@Override
		public Optional<AgreementOperationSetting> agreementOperationSetting(String companyId) {
			return agreementOperationSettingRepo.find(companyId);
		}

		@Override
		public Optional<SharedAffWorkPlaceHisImport> affWorkPlace(String employeeId, GeneralDate baseDate) {
			return sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, baseDate);
		}

		@Override
		public Optional<WorkingCondition> workingCondition(String historyId) {
			return workingConditionRepo.getByHistoryId(historyId);
		}

		@Override
		public List<SharedSidPeriodDateEmploymentImport> employmentHistories(CacheCarrier cacheCarrier,
				List<String> sids, DatePeriod datePeriod) {
			return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
		}

		@Override
		public List<ClosureEmployment> employmentClosure(String companyId, List<String> employmentCDs) {
			return closureEmploymentRepo.findListEmployment(companyId, employmentCDs);
		}

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetByWorkTimeCode(String companyId,
				String workTimeCode) {
			return predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode);
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter(Map<Integer, OptionalItem> optionalItems) {
			return converterFactory.createDailyConverter(optionalItems);
		}

		@Override
		public ManagedParallelWithContext parallelContext() {
			return parallel;
		}

		@Override
		public YearMonth yearMonthFromCalender(CacheCarrier cacheCarrier, String companyId, YearMonth yearMonth) {
			return companyAdapter.getYearMonthFromCalenderYM(cacheCarrier, companyId, yearMonth);
		}

		@Override
		public ConditionCalcResult flexConditionCalcResult(CacheCarrier cacheCarrier, String companyId,
				CalcFlexChangeDto calc) {
			return checkBeforeCalcFlexChangeService.getConditionCalcFlexRequire(cacheCarrier, companyId, calc);
		}

		@Override
		public Optional<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate) {
			return attendanceTimeOfMonthlyRepo.find(employeeId, yearMonth, closureId, closureDate);
		}

		@Override
		public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId,
				YearMonth yearMonth) {
			return attendanceTimeOfMonthlyRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
		}

		@Override
		public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, DatePeriod period) {
			return attendanceTimeOfMonthlyRepo.findByPeriodIntoEndYmd(employeeId, period);
		}

		@Override
		public List<AnyItemOfMonthly> anyItemOfMonthly(String employeeId, YearMonth yearMonth, ClosureId closureId,
				ClosureDate closureDate) {
			return anyItemOfMonthlyRepo.findByMonthlyAndClosure(employeeId, yearMonth, closureId, closureDate);
		}

		@Override
		public List<AnnLeaRemNumEachMonth> annLeaRemNumEachMonth(String employeeId, DatePeriod closurePeriod) {
			return annLeaRemNumEachMonthRepo.findByClosurePeriod(employeeId, closurePeriod);
		}

		@Override
		public Optional<AnnLeaRemNumEachMonth> annLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate) {
			return remainMergeRepo.find(employeeId, yearMonth, closureId, closureDate)
					.map(c -> c.getAnnLeaRemNumEachMonth());
		}

		@Override
		public Optional<RsvLeaRemNumEachMonth> rsvLeaRemNumEachMonth(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate) {
			return remainMergeRepo.find(employeeId, yearMonth, closureId, closureDate)
					.map(c -> c.getRsvLeaRemNumEachMonth());
		}

		@Override
		public Optional<AbsenceLeaveRemainData> absenceLeaveRemainData(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate) {
			return remainMergeRepo.find(employeeId, yearMonth, closureId, closureDate)
					.map(c -> c.getAbsenceLeaveRemainData());
		}

		@Override
		public Optional<MonthlyDayoffRemainData> monthlyDayoffRemainData(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate) {
			return remainMergeRepo.find(employeeId, yearMonth, closureId, closureDate)
					.map(c -> c.getMonthlyDayoffRemainData());
		}

		@Override
		public List<SpecialHolidayRemainData> specialHolidayRemainData(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate) {
			return remainMergeRepo.find(employeeId, yearMonth, closureId, closureDate)
					.map(c -> c.getSpecialHolidayRemainData()).orElse(Collections.emptyList());
		}

		@Override
		public Optional<AgreementYearSetting> agreementYearSetting(String employeeId, int yearMonth) {
			return agreementYearSettingRepo.findByKey(employeeId, yearMonth);
		}

		@Override
		public Optional<AgreementMonthSetting> agreementMonthSetting(String employeeId, YearMonth yearMonth) {
			return agreementMonthSettingRepo.findByKey(employeeId, yearMonth);
		}

		@Override
		public Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String employeeId,
				YearMonth yearMonth) {
			return agreementTimeOfManagePeriodRepo.find(employeeId, yearMonth);
		}

		@Override
		public List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> employeeIds,
				List<YearMonth> yearMonths) {
			return agreementTimeOfManagePeriodRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		}

		@Override
		public void updateLogInfo(String empCalAndSumExecLogID, int executionContent, int processStatus) {
			empCalAndSumExeLogRepo.updateLogInfo(empCalAndSumExecLogID, executionContent, processStatus);
		}

		@Override
		public void updateLogWithContent(String employeeID, String empCalAndSumExecLogId, int executionContent,
				int state) {
			targetPersonRepo.updateWithContent(employeeID, empCalAndSumExecLogId, executionContent, state);
		}

		@Override
		public void add(ErrMessageInfo errMessageInfo) {
			errMessageInfoRepo.add(errMessageInfo);
		}

		@Override
		public Optional<EmpCalAndSumExeLog> calAndSumExeLog(String empCalAndSumExecLogID) {
			return empCalAndSumExeLogRepo.getByEmpCalAndSumExecLogID(empCalAndSumExecLogID);
		}

		@Override
		public void removeMonthEditState(String employeeId, YearMonth yearMonth, ClosureId closureId,
				ClosureDate closureDate) {
			editStateOfMonthlyPerRepo.remove(employeeId, yearMonth, closureId, closureDate);
		}

		@Override
		public List<EditStateOfMonthlyPerformance> monthEditStates(String employeeId, YearMonth yearMonth,
				ClosureId closureId, ClosureDate closureDate) {
			return editStateOfMonthlyPerRepo.findByClosure(employeeId, yearMonth, closureId, closureDate);
		}

		@Override
		public ManagedExecutorService getExecutorService() {
			return executorService;
		}

		@Override
		public Optional<AffiliationInforOfDailyAttd> dailyAffiliationInfor(String employeeId, GeneralDate ymd) {
			return affiliationInforOfDailyPerforRepo.findByKey(employeeId, ymd).map(c -> c.getAffiliationInfor());
		}

		@Override
		public Map<GeneralDate, AffiliationInforOfDailyAttd> dailyAffiliationInfors(List<String> employeeId, DatePeriod ymd) {
			return affiliationInforOfDailyPerforRepo.finds(employeeId, ymd)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAffiliationInfor()));
		}

		@Override
		public MonthlyRecordToAttendanceItemConverter createMonthlyConverter() {
			return converterFactory.createMonthlyConverter();
		}

		@Override
		public AttendanceDaysMonth monthAttendanceDays(CacheCarrier cacheCarrier, DatePeriod period,
				Map<String, WorkType> workTypeMap) {
			return predWorkingDaysAdaptor.byPeriod(cacheCarrier, period, workTypeMap);
		}

		@Override
		public void merge(List<IntegrationOfMonthly> domains, GeneralDate targetDate) {
			updateAllDomainMonthService.merge(domains, targetDate);
		}

//		@Override
//		public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
//			return workingConditionItemRepo.getBySidAndStandardDate(employeeId, baseDate);
//		}

		@Override
		public List<WorkingConditionItem> workingConditionItem(String employeeId, DatePeriod datePeriod) {
			return workingConditionItemRepo.getBySidAndPeriodOrderByStrD(employeeId, datePeriod);
		}

		@Override
		public Map<String, Map<GeneralDate, WorkingConditionItem>> workingConditionItem(
				Map<String, Set<GeneralDate>> params) {
			return workingConditionItemRepo.getBySidAndPeriod(params);
		}

		@Override
		public Optional<AgreementUnitSetting> agreementUnitSetting(String companyId) {
			return agreementUnitSetRepo.find(companyId);
		}

		@Override
		public Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId,
				GeneralDate baseDate) {
			return affClassficationAdapter.findByEmployeeId(companyId, employeeId, baseDate);
		}

		@Override
		public List<AffClassificationSidImport> affEmployeeClassification(String companyId, List<String> employeeId,
				DatePeriod baseDate) {
			return affClassficationAdapter.finds(companyId, employeeId, baseDate);
		}

		@Override
		public Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId,
				LaborSystemtAtr laborSystemAtr, String classificationCode) {
			return agreementTimeOfClassificationRepo.find(companyId, laborSystemAtr, classificationCode);
		}

		@Override
		public List<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId,
				List<String> classificationCode) {
			return agreementTimeOfClassificationRepo.find(companyId, classificationCode);
		}

		@Override
		public List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId,
				GeneralDate baseDate) {
			return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

		@Override
		public Map<GeneralDate, Map<String, List<String>>> getCanUseWorkplaceForEmp(String companyId,
				List<String> employeeId, DatePeriod baseDate) {
			return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(companyId, employeeId, baseDate);
		}

		@Override
		public List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate) {
			return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(companyId, employeeId, baseDate);
		}

		@Override
		public Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId,
				LaborSystemtAtr laborSystemAtr) {
			return agreementTimeWorkPlaceRepo.findAgreementTimeOfWorkPlace(workplaceId, laborSystemAtr);
		}

		@Override
		public List<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(List<String> workplaceId) {
			return agreementTimeWorkPlaceRepo.findWorkPlaceSetting(workplaceId);
		}

		@Override
		public Map<String, List<SyEmploymentImport>> employment(List<String> employeeId, DatePeriod baseDate) {
			return syEmploymentAdapter.finds(employeeId, baseDate);
		}

		@Override
		public Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate) {
			return syEmploymentAdapter.findByEmployeeId(companyId, employeeId, baseDate);
		}

		@Override
		public List<AgreementTimeOfEmployment> agreementTimeOfEmployment(String comId,
				List<String> employmentCategoryCode) {
			return agreementTimeOfEmploymentRepo.findEmploymentSetting(comId, employmentCategoryCode);
		}

		@Override
		public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId,
				String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
			return agreementTimeOfEmploymentRepo.find(companyId, employmentCategoryCode, laborSystemAtr);
		}

		@Override
		public List<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId) {
			return agreementTimeCompanyRepo.find(companyId);
		}

		@Override
		public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId,
				LaborSystemtAtr laborSystemAtr) {
			return agreementTimeCompanyRepo.find(companyId, laborSystemAtr);
		}

		@Override
		public List<BasicAgreementSetting> basicAgreementSetting(List<String> basicSettingId) {
			return basicAgreementSettingRepo.find(basicSettingId);
		}

		@Override
		public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, GeneralDate criteriaDate) {
			return attendanceTimeOfMonthlyRepo.findByDate(employeeId, criteriaDate);
		}

		@Override
		public Optional<BasicScheduleSidDto> basicScheduleSid(String employeeId, GeneralDate baseDate) {
			return basicScheduleAdapter.findAllBasicSchedule(employeeId, baseDate);
		}

		@Override
		public Optional<ClosureHistory> closureHistoryByYm(String companyId, int closureId, int yearMonth) {
			return closureRepo.findBySelectedYearMonth(companyId, closureId, yearMonth);
		}

		@Override
		public void addClosureStatusManagement(ClosureStatusManagement domain) {
			closureStatusManagementRepo.add(domain);
		}

		@Override
		public List<ClosureStatusManagement> employeeClosureStatusManagements(List<String> employeeIds,
				DatePeriod span) {
			return closureStatusManagementRepo.getByIdListAndDatePeriod(employeeIds, span);
		}

		@Override
		public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
			return closureStatusManagementRepo.getLatestByEmpId(employeeId);
		}

//		@Override
//		public void deleteTmpAnnualHolidayMng(String mngId) {
//			tmpAnnualHolidayMngRepo.deleteById(mngId);
//		}

		@Override
		public Optional<TmpAnnualHolidayMng> tmpAnnualHolidayMng(String mngId) {
			return tmpAnnualHolidayMngRepo.getById(mngId);
		}

//		@Override
//		public void deleteTmpResereLeaveMng(String mngId) {
//			tmpResereLeaveMngRepo.deleteById(mngId);
//		}

		@Override
		public Optional<TmpResereLeaveMng> tmpResereLeaveMng(String resereMngId) {
			return tmpResereLeaveMngRepo.getById(resereMngId);
		}

		@Override
		public void addOrUpdateAnnualLeaveRemainingHistory(AnnualLeaveRemainingHistory domain) {
			annualLeaveRemainHistRepo.addOrUpdate(domain);
		}

		@Override
		public List<AnnualLeaveRemainingHistory> annualLeaveRemainingHistory(String sid, YearMonth ym) {
			return annualLeaveRemainHistRepo.getInfoBySidAndYM(sid, ym);
		}

		@Override
		public void addAnnualLeaveGrantRemainingData(AnnualLeaveGrantRemainingData data) {
			annLeaGrantRemDataRepo.add(data);
		}

		@Override
		public void updateAnnualLeaveGrantRemainingData(AnnualLeaveGrantRemainingData data) {
			annLeaGrantRemDataRepo.update(data);
		}

		@Override
		public List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId,
				GeneralDate grantDate) {
			return annLeaGrantRemDataRepo.find(employeeId, grantDate);
		}

		@Override
		public List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId) {
			return annLeaGrantRemDataRepo.find(employeeId);
		}

		@Override
		public void addOrUpdateAnnualLeaveTimeRemainingHistory(AnnualLeaveTimeRemainingHistory domain) {
			annualLeaveTimeRemainHistRepo.addOrUpdate(domain);
		}

		@Override
		public void addOrUpdateAnnualLeaveMaxHistoryData(AnnualLeaveMaxHistoryData domain) {
			annualLeaveMaxHistRepo.addOrUpdate(domain);
		}

		@Override
		public Optional<AnnualLeaveMaxData> annualLeaveMaxData(String employeeId) {
			return annLeaMaxDataRepo.get(employeeId);
		}

		@Override
		public void updateAnnualLeaveMaxData(AnnualLeaveMaxData maxData) {
			annLeaMaxDataRepo.update(maxData);
		}

		@Override
		public void addAnnualLeaveMaxData(AnnualLeaveMaxData maxData) {
			annLeaMaxDataRepo.add(maxData);
		}

		@Override
		public void deleteReserveLeaveGrantRemainHistoryData(String employeeId, YearMonth ym, ClosureId closureId,
				ClosureDate closureDate) {
			rsvLeaveGrantRemainHistRepo.delete(employeeId, ym, closureId, closureDate);
		}

		@Override
		public void addOrUpdateReserveLeaveGrantRemainHistoryData(ReserveLeaveGrantRemainHistoryData domain,
				String cid) {
			rsvLeaveGrantRemainHistRepo.addOrUpdate(domain, cid);
		}

		@Override
		public void deleteReserveLeaveGrantTimeRemainHistoryData(String employeeId, GeneralDate date) {
			rsvLeaveGrantTimeRemainHistRepo.deleteAfterDate(employeeId, date);
		}

		@Override
		public void addOrUpdateReserveLeaveGrantTimeRemainHistoryData(ReserveLeaveGrantTimeRemainHistoryData domain,
				String cid) {
			rsvLeaveGrantTimeRemainHistRepo.addOrUpdate(domain, cid);
		}

		@Override
		public List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId, String cId) {
			return rervLeaGrantRemDataRepo.find(employeeId, cId);
		}

		@Override
		public List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId,
				GeneralDate grantDate) {
			return rervLeaGrantRemDataRepo.find(employeeId, grantDate);
		}

		@Override
		public void deleteReserveLeaveGrantRemainingData(String employeeId, GeneralDate date) {
			rervLeaGrantRemDataRepo.deleteAfterDate(employeeId, date);
		}

		@Override
		public void updateReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data) {
			rervLeaGrantRemDataRepo.update(data);
		}

		@Override
		public void addReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data, String cId) {
			rervLeaGrantRemDataRepo.add(data, cId);
		}

		@Override
		public void deleteInterimAbsMng(List<String> listAbsMngId) {
			interimRecAbasMngRepo.deleteInterimAbsMng(listAbsMngId);
		}

		@Override
		public void deleteInterimRecMng(List<String> listRecId) {
			interimRecAbasMngRepo.deleteInterimRecMng(listRecId);
		}


		@Override
		public void updateSubstitutionOfHDManagementData(SubstitutionOfHDManagementData domain) {
			substitutionOfHDManaDataRepo.update(domain);
		}

		@Override
		public void createSubstitutionOfHDManagementData(SubstitutionOfHDManagementData domain) {
			substitutionOfHDManaDataRepo.create(domain);
		}

		@Override
		public void deleteSubstitutionOfHDManagementData(List<String> subOfHDID) {
			substitutionOfHDManaDataRepo.deleteById(subOfHDID);
		}

		@Override
		public List<SubstitutionOfHDManagementData> substitutionOfHDManagementData(String sid, Boolean unknownDate,
				DatePeriod dayoffDate) {
			return substitutionOfHDManaDataRepo.getByHoliday(sid, unknownDate, dayoffDate);
		}

		@Override
		public Optional<SubstitutionOfHDManagementData> substitutionOfHDManagementData(String Id) {
			return substitutionOfHDManaDataRepo.findByID(Id);
		}

		@Override
		public void updatePayoutManagementData(PayoutManagementData domain) {
			payoutManagementDataRepo.update(domain);
		}

		@Override
		public void createPayoutManagementData(PayoutManagementData domain) {
			payoutManagementDataRepo.create(domain);
		}

		@Override
		public void deletePayoutManagementData(List<String> payoutId) {
			payoutManagementDataRepo.deleteById(payoutId);
		}

		@Override
		public Optional<PayoutManagementData> payoutManagementData(String Id) {
			return payoutManagementDataRepo.findByID(Id);
		}

		@Override
		public List<PayoutManagementData> payoutManagementData(String sid, Boolean unknownDate, DatePeriod dayoffDate) {
			return payoutManagementDataRepo.getByHoliday(sid, unknownDate, dayoffDate);
		}

		@Override
		public void deleteCompensatoryDayOffManaData(List<String> dayOffId) {
			comDayOffManaDataRepo.deleteById(dayOffId);
		}

		@Override
		public void updateCompensatoryDayOffManaData(CompensatoryDayOffManaData domain) {
			comDayOffManaDataRepo.update(domain);
		}

		@Override
		public void createCompensatoryDayOffManaData(CompensatoryDayOffManaData domain) {
			comDayOffManaDataRepo.create(domain);
		}

		@Override
		public List<CompensatoryDayOffManaData> compensatoryDayOffManaData(String sid, Boolean unknownDate,
				DatePeriod dayOff) {
			return comDayOffManaDataRepo.getByHoliday(sid, unknownDate, dayOff);
		}

		@Override
		public Optional<CompensatoryDayOffManaData> compensatoryDayOffManaData(String comDayOffId) {
			return comDayOffManaDataRepo.getBycomdayOffId(comDayOffId);
		}

		@Override
		public Optional<LeaveManagementData> leaveManagementData(String comDayOffId) {
			return leaveManaDataRepo.getByLeaveId(comDayOffId);
		}

		@Override
		public void updateLeaveManagementData(LeaveManagementData leaveMng) {
			leaveManaDataRepo.update(leaveMng);
		}

		@Override
		public void createLeaveManagementData(LeaveManagementData leaveMng) {
			leaveManaDataRepo.create(leaveMng);
		}

		@Override
		public List<LeaveManagementData> leaveManagementData(String sid, Boolean unknownDate, DatePeriod dayOff) {
			return leaveManaDataRepo.getByHoliday(sid, unknownDate, dayOff);
		}

		@Override
		public void deleteLeaveManagementData(List<String> leaveManaId) {
			leaveManaDataRepo.deleteById(leaveManaId);
		}

		@Override
		public void deleteInterimBreakMng(List<String> listBreakId) {
			interimBreakDayOffMngRepo.deleteInterimBreakMng(listBreakId);
		}

		@Override
		public void deleteInterimDayOffMng(List<String> mngIds) {
			interimBreakDayOffMngRepo.deleteInterimDayOffMng(mngIds);
		}

		@Override
		public List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String employeeId, int specialCode) {
			return specialLeaveGrantRepo.getAll(employeeId, specialCode);
		}

		@Override
		public void deleteSpecialLeaveGrantRemainingData(String specialid) {
			specialLeaveGrantRepo.delete(specialid);
		}

		@Override
		public void updateSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data) {
			specialLeaveGrantRepo.update(data);
		}

		@Override
		public void addSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data) {
			specialLeaveGrantRepo.add(data);
		}

		@Override
		public void deleteSpecialHolidayInterim(String specialId) {
			interimSpecialHolidayMngRepo.deleteSpecialHoliday(specialId);
		}

		@Override
		public List<SpecialHoliday> specialHoliday(String companyId) {
			return specialHolidayRepo.findByCompanyId(companyId);
		}

		@Override
		public Map<GeneralDate, DailyInterimRemainMngData> monthInterimRemainData(CacheCarrier cacheCarrier, String cid,
				String sid, DatePeriod dateData) {
			return interimRemainOffMonthProcess.monthInterimRemainData(cacheCarrier, cid, sid, dateData);
		}

		@Override
		public void deleteInterimRemain(String employeeId, DatePeriod dateData, RemainType remainType) {
			interimRemainRepo.deleteBySidPeriodType(employeeId, dateData, remainType);
		}

		@Override
		public List<InterimRemain> interimRemain(String employeeId, DatePeriod dateData, RemainType remainType) {
			return interimRemainRepo.getRemainBySidPriod(employeeId, dateData, remainType);
		}

//		@Override
//		public List<InterimRemain> interimRemain(String sId) {
//			return interimRemainRepo.findByEmployeeID(sId);
//		}

		@Override
		public void persistAndUpdateInterimRemain(InterimRemain domain) {
			interimRemainRepo.persistAndUpdateInterimRemain(domain);
		}

		@Override
		public void removeInterimRemain(String sId, DatePeriod period) {
			interimRemainRepo.deleteBySidPeriod(sId, period);
		}

		@Override
		public void addMonthlyClosureUpdateErrorInfor(MonthlyClosureUpdateErrorInfor domain) {
			monthlyClosureUpdateErrorInforRepo.add(domain);
		}

		@Override
		public List<MonthlyClosureUpdateErrorInfor> monthlyClosureUpdateErrorInfor(String monthlyClosureUpdateLogId,
				String employeeId) {
			return monthlyClosureUpdateErrorInforRepo.getByLogIdAndEmpId(monthlyClosureUpdateLogId, employeeId);
		}

		@Override
		public Optional<MonthlyClosureUpdateLog> monthlyClosureUpdateLog(String id) {
			return monthlyClosureUpdateLogRepo.getLogById(id);
		}

		@Override
		public void updateMonthlyClosureUpdateLog(MonthlyClosureUpdateLog domain) {
			monthlyClosureUpdateLogRepo.updateStatus(domain);
		}

		@Override
		public List<MonthlyClosureUpdatePersonLog> monthlyClosureUpdatePersonLog(String monthlyClosureUpdateLogId) {
			return monthlyClosureUpdatePersonLogRepo.getAll(monthlyClosureUpdateLogId);
		}

		@Override
		public void addMonthlyClosureUpdatePersonLog(MonthlyClosureUpdatePersonLog domain) {
			monthlyClosureUpdatePersonLogRepo.add(domain);
		}

		@Override
		public void deleteMonthlyClosureUpdatePersonLog(String monthlyLogId, String empId) {
			monthlyClosureUpdatePersonLogRepo.delete(monthlyLogId, empId);
		}


		@Override
		public Optional<ActualLock> actualLock(String companyId, int closureId) {
			return actualLockRepo.findById(companyId, closureId);
		}

		@Override
		public void updateActualLock(ActualLock actualLock) {
			actualLockRepo.update(actualLock);
		}

		@Override
		public void updateClosure(Closure closure) {
			closureRepo.update(closure);
		}

		@Override
		public Optional<OuenAggregateFrameSetOfMonthly> ouenAggregateFrameSetOfMonthly(String companyId) {

			return ouenAggregateFrameSetOfMonthlyRepo.find(companyId);
		}

		@Override
		public List<OuenWorkTimeOfDailyAttendance> ouenWorkTimeOfDailyAttendance(String empId, GeneralDate ymd) {

			return ouenWorkTimeOfDailyRepo.find(empId, ymd)
					.stream().map(c -> c.getOuenTime()).collect(Collectors.toList());
		}

		@Override
		public List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance(String empId,
				GeneralDate ymd) {

			return ouenWorkTimeSheetOfDailyRepo.find(empId, ymd)
					.stream().map(c -> c.getOuenTimeSheet()).collect(Collectors.toList());
		}

		@Override
		public boolean isUseWorkLayer(String companyId) {
			return false;
		}

		@Override
		public Optional<RegularLaborTimeCom> regularLaborTimeByCompany(String companyId) {
			return regularLaborTimeComRepo.find(companyId);
		}

		@Override
		public Optional<DeforLaborTimeCom> deforLaborTimeByCompany(String companyId) {
			return deforLaborTimeComRepo.find(companyId);
		}

		@Override
		public Optional<RegularLaborTimeWkp> regularLaborTimeByWorkplace(String cid, String wkpId) {
			return regularLaborTimeWkpRepo.find(cid, wkpId);
		}

		@Override
		public Optional<DeforLaborTimeWkp> deforLaborTimeByWorkplace(String cid, String wkpId) {
			return deforLaborTimeWkpRepo.find(cid, wkpId);
		}

		@Override
		public Optional<RegularLaborTimeEmp> regularLaborTimeByEmployment(String cid, String employmentCode) {
			return regularLaborTimeEmpRepo.findById(cid, employmentCode);
		}

		@Override
		public Optional<DeforLaborTimeEmp> deforLaborTimeByEmployment(String cid, String employmentCode) {
			return deforLaborTimeEmpRepo.find(cid, employmentCode);
		}

		@Override
		public Optional<RegularLaborTimeSha> regularLaborTimeByEmployee(String Cid, String EmpId) {
			return regularLaborTimeShaRepo.find(Cid, EmpId);
		}

		@Override
		public Optional<DeforLaborTimeSha> deforLaborTimeByEmployee(String cid, String empId) {
			return deforLaborTimeShaRepo.find(cid, empId);
		}

		@Override
		public Optional<ShaFlexMonthActCalSet> monthFlexCalcSetbyEmployee(
				String cid, String sId) {
			return shaFlexMonthActCalSetRepo.find(cid, sId);
		}

		@Override
		public Optional<ShaDeforLaborMonthActCalSet> monthDeforLaborCalcSetByEmployee(
				String cId, String sId) {
			return shaDeforLaborMonthActCalSetRepo.find(cId, sId);
		}

		@Override
		public Optional<ShaRegulaMonthActCalSet> monthRegulaCalcSetByEmployee(
				String cid, String sId) {
			return shaRegulaMonthActCalSetRepo.find(cid, sId);
		}

		@Override
		public Optional<ComRegulaMonthActCalSet> monthRegulaCalSetByCompany(
				String companyId) {
			return comRegulaMonthActCalSetRepo.find(companyId);
		}

		@Override
		public Optional<ComDeforLaborMonthActCalSet> monthDeforLaborCalSetByCompany(
				String companyId) {
			return comDeforLaborMonthActCalSetRepo.find(companyId);
		}

		@Override
		public Optional<ComFlexMonthActCalSet> monthFlexCalSetByCompany(
				String companyId) {
			return comFlexMonthActCalSetRepo.find(companyId);
		}

		@Override
		public Optional<WkpRegulaMonthActCalSet> monthRegularCalcSetByWorkplace(
				String cid, String wkpId) {
			return wkpRegulaMonthActCalSetRepo.find(cid, wkpId);
		}

		@Override
		public Optional<EmpRegulaMonthActCalSet> monthRegularCalcSetByEmployment(
				String cid, String empCode) {
			return empRegulaMonthActCalSetRepo.find(cid, empCode);
		}

		@Override
		public Optional<WkpDeforLaborMonthActCalSet> monthDeforCalcSetByWorkplace(
				String cid, String wkpId) {
			return wkpDeforLaborMonthActCalSetRepo.find(cid, wkpId);
		}

		@Override
		public Optional<EmpDeforLaborMonthActCalSet> monthDeforCalcSetByEmployment(
				String cid, String empCode) {
			return empDeforLaborMonthActCalSetRepo.find(cid, empCode);
		}

		@Override
		public Optional<WkpFlexMonthActCalSet> monthFlexCalcSetByWorkplace(
				String cid, String wkpId) {
			return wkpFlexMonthActCalSetRepo.find(cid, wkpId);
		}

		@Override
		public Optional<EmpFlexMonthActCalSet> monthFlexCalcSetByEmployment(
				String cid, String empCode) {
			return empFlexMonthActCalSetRepo.find(cid, empCode);
		}

		@Override
		public Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSetRepo.findWorkplace(cid, workplaceId, laborAttr, ym);
		}

		@Override
		public Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSetRepo.findEmployee(cid, sid, laborAttr, ym);
		}

		@Override
		public Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmp(String cid, String empCode,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSetRepo.findEmployment(cid, empCode, laborAttr, ym);
		}

		@Override
		public Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetCom(String cid, LaborWorkTypeAttr laborAttr,
				YearMonth ym) {
			return monthlyWorkTimeSetRepo.findCompany(cid, laborAttr, ym);
		}

		@Override
		public BasicAgreementSetting basicAgreementSetting(String companyId, String employeeId, GeneralDate criteriaDate) {
			return AgreementDomainService.getBasicSet(this, companyId, employeeId, criteriaDate);
		}


		public Optional<VerticalTotalMethodOfMonthly> verticalTotalMethodOfMonthly(String cid) {
			return verticalTotalMethodOfMonthlyRepo.findByCid(cid);
		}

		@Override
		public BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate, Year year) {

			return AgreementDomainService.getBasicSet(this, cid, sid, baseDate, year);
		}

		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String cid, GeneralDate ymd, String sid) {

			return workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(cid, ymd, sid);
		}

		@Override
		public BasicAgreementSetting basicAgreementSetting(String cid, String sid, YearMonth ym, GeneralDate baseDate) {

			return AgreementDomainService.getBasicSet(this, cid, sid, baseDate, ym);
		}

		@Override
		public List<RoleOfOpenPeriod> roleOfOpenPeriod(String cid) {

			return roleOfOpenPeriodRepo.findByCID(cid);
		}

		@Override
		public List<IntegrationOfDaily> integrationOfDaily(String sid, DatePeriod period) {

			return dailyCalculationEmployeeService.getIntegrationOfDaily(sid, period);
		}

		@Override
		public MonAggrCompanySettings monAggrCompanySettings(String cid) {

			return MonAggrCompanySettings.loadSettings(this, cid);
		}

		@Override
		public MonAggrEmployeeSettings monAggrEmployeeSettings(CacheCarrier cacheCarrier, String companyId,
				String employeeId, DatePeriod period) {

			return MonAggrEmployeeSettings.loadSettings(this, cacheCarrier, companyId, employeeId, period);
		}

		@Override
		public Optional<WeekRuleManagement> weekRuleManagement(String cid) {

			return weekRuleManagementRepo.find(cid);
		}

		@Override
		public ReservationOfMonthly reservation(String sid, GeneralDate date) {

			return VerticalTotalAggregateService.aggregate(this, sid, date);
		}

		@Override
		public List<StampCard> stampCard(String empId) {

			return stampCardRepo.getListStampCard(empId);
		}

		@Override
		public List<BentoReservation> bentoReservation(List<ReservationRegisterInfo> inforLst, GeneralDate date,
				boolean ordered) {

			return bentoReservationRepo.findByOrderedPeriodEmpLst(inforLst, new DatePeriod(date, date), ordered);
		}

		@Override
		public Bento bento(String companyID, GeneralDate date, int frameNo) {

			return bentoMenuRepo.getBento(companyID, date, frameNo);
		}

		@Override
		public Optional<GeneralDate> getProcessingDate(String employeeId, GeneralDate date) {

			return getProcessingDate.getProcessingDate(employeeId, date);
		}

		@Override
		public List<AffCompanyHistImport> listAffCompanyHistImport(List listAppId, DatePeriod period) {
			// TODO Auto-generated method stub
			return null;
		}

//		@Override
//		public EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId) {
//			// TODO Auto-generated method stub
//			return null;
//		}

		@Override
		public Optional<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String specialId) {
			return specialLeaveGrantRepo.getBySpecialId(specialId);
		}

		@Override
		public List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String sid, int speCode,
				DatePeriod datePriod, GeneralDate startDate, LeaveExpirationStatus expirationStatus) {

			return specialLeaveGrantRepo.getByNextDate(sid, speCode, datePriod, startDate, expirationStatus);
		}

		@Override
		public List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String sid, int specialLeaveCode,
				LeaveExpirationStatus expirationStatus, GeneralDate grantDate, GeneralDate deadlineDate) {

			return specialLeaveGrantRepo.getByPeriodStatus(sid, specialLeaveCode, expirationStatus, grantDate,
					deadlineDate);
		}

		@Override
		public List<InterimSpecialHolidayMng> interimSpecialHolidayMng(String mngId) {
			return interimSpecialHolidayMngRepo.findById(mngId);
		}
	}
}