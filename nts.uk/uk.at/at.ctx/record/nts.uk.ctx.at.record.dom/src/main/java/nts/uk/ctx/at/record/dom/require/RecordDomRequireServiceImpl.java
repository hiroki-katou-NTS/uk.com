package nts.uk.ctx.at.record.dom.require;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.shift.pattern.GetPredWorkingDaysAdaptor;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.snapshot.DailySnapshotWorkAdapter;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.CreatingDailyResultsCondition;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess.CreatingDailyResultsConditionRepository;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculateDailyRecordServiceCenter;
import nts.uk.ctx.at.record.dom.monthly.updatedomain.UpdateAllDomainMonthService;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalAggregateService;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInforRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLogRepository;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetPeriodFromPreviousToNextGrantDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GrantPeriodDto;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayManagementUsageUnitRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardData;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardDataRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardHistory;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardHistoryRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagement;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagementRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.AppRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ScheRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.MonthlyDayoffRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByApplicationData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByScheData;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService.RequireM1;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationRemainingNumber;
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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.getprocessingdate.GetProcessingDate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCount;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCountRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Classification36AgreementTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Company36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Employment36HoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementMonthSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedRemainDataForMonthlyAgg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.InterimRemainOffMonthProcess;
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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMergeRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.AbsenceLeaveRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlexRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.SuperHD60HConMedRepository;
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
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
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
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CheckCareService;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService.RequireM3;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployeeProc;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

public  class RecordDomRequireServiceImpl extends nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RequireImp implements RecordDomRequireService.Require {

		public RecordDomRequireServiceImpl(ComSubstVacationRepository comSubstVacationRepo, CompensLeaveComSetRepository compensLeaveComSetRepo, SpecialLeaveGrantRepository specialLeaveGrantRepo,
				EmpEmployeeAdapter empEmployeeAdapter, GrantDateTblRepository grantDateTblRepo, AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo, SpecialHolidayRepository specialHolidayRepo,
				InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo, SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo, InterimRecAbasMngRepository interimRecAbasMngRepo,
				EmpSubstVacationRepository empSubstVacationRepo, SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo,
				PayoutManagementDataRepository payoutManagementDataRepo, InterimBreakDayOffMngRepository interimBreakDayOffMngRepo, ComDayOffManaDataRepository comDayOffManaDataRepo,
				CompanyAdapter companyAdapter, ShareEmploymentAdapter shareEmploymentAdapter, LeaveManaDataRepository leaveManaDataRepo, WorkingConditionItemRepository workingConditionItemRepo,
				WorkingConditionRepository workingConditionRepo, WorkTimeSettingRepository workTimeSettingRepo, FixedWorkSettingRepository fixedWorkSettingRepo, FlowWorkSettingRepository flowWorkSettingRepo,
				DiffTimeWorkSettingRepository diffTimeWorkSettingRepo, FlexWorkSettingRepository flexWorkSettingRepo, PredetemineTimeSettingRepository predetemineTimeSettingRepo, ClosureRepository closureRepo,
				ClosureEmploymentRepository closureEmploymentRepo, WorkTypeRepository workTypeRepo, RemainCreateInforByApplicationData remainCreateInforByApplicationData, CompensLeaveEmSetRepository compensLeaveEmSetRepo,

				EmploymentSettingRepository employmentSettingRepo, RetentionYearlySettingRepository retentionYearlySettingRepo, AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo,
				OutsideOTSettingRepository outsideOTSettingRepo, WorkdayoffFrameRepository workdayoffFrameRepo, YearHolidayRepository yearHolidayRepo, TmpResereLeaveMngRepository tmpResereLeaveMngRepo,
				SysEmploymentHisAdapter sysEmploymentHisAdapter, RervLeaGrantRemDataRepository rervLeaGrantRemDataRepo, WorkInformationRepository workInformationRepo, AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo,
				LengthServiceRepository lengthServiceRepository, GrantYearHolidayRepository grantYearHolidayRepo, TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepo, AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo,
				OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo, AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo, ClosureStatusManagementRepository closureStatusManagementRepo,
				AnnLeaMaxDataRepository annLeaMaxDataRepo, AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo, EmploymentHistAdapter employmentHistAdapter, RemainCreateInforByScheData remainCreateInforByScheData,
				RemainCreateInforByRecordData remainCreateInforByRecordData, UsageUnitSettingRepository usageUnitSettingRepo, AffWorkplaceAdapter affWorkplaceAdapter,
				TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepo, TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepo, SpecificDateAttrOfDailyPerforRepo specificDateAttrOfDailyPerforRepo,
				EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo, AnyItemValueOfDailyRepo anyItemValueOfDailyRepo, PCLogOnInfoOfDailyRepo pcLogOnInfoOfDailyRepo, AttendanceTimeRepository attendanceTimeRepo,
				PayItemCountOfMonthlyRepository payItemCountOfMonthlyRepo, OptionalItemRepository optionalItemRepo, EmpConditionRepository empConditionRepo, FormulaRepository formulaRepo, FormulaDispOrderRepository formulaDispOrderRepo,
				ActualLockRepository actualLockRepo, LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthlyRepo, OvertimeWorkFrameRepository roleOvertimeWorkRepo, HolidayAddtionRepository holidayAddtionRepo,
				MonthlyAggrSetOfFlexRepository monthlyAggrSetOfFlexRepo, InsufficientFlexHolidayMntRepository insufficientFlexHolidayMntRepo, FlexShortageLimitRepository flexShortageLimitRepo,
				RoundingSetOfMonthlyRepository roundingSetOfMonthlyRepo, TotalTimesRepository totalTimesRepo, AgreementOperationSettingRepository agreementOperationSettingRepo,
				ManagedParallelWithContext parallel, CheckBeforeCalcFlexChangeService checkBeforeCalcFlexChangeService, AnyItemOfMonthlyRepository anyItemOfMonthlyRepo,
				EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo, EditStateOfMonthlyPerRepository editStateOfMonthlyPerRepo, AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepo,
				AttendanceItemConvertFactory converterFactory, GetPredWorkingDaysAdaptor predWorkingDaysAdaptor, UpdateAllDomainMonthService updateAllDomainMonthService,
				AgreementUnitSettingRepository agreementUnitSetRepo, Workplace36AgreedHoursRepository agreementTimeWorkPlaceRepo, AffClassificationAdapter affClassficationAdapter, SyEmploymentAdapter syEmploymentAdapter,
				Employment36HoursRepository agreementTimeOfEmploymentRepo, Classification36AgreementTimeRepository agreementTimeOfClassificationRepo, Company36AgreedHoursRepository agreementTimeCompanyRepo, RemainMergeRepository remainMergeRepo,
				AgreementYearSettingRepository agreementYearSettingRepo, AgreementMonthSettingRepository agreementMonthSettingRepo, AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo, TargetPersonRepository targetPersonRepo,
				ErrMessageInfoRepository errMessageInfoRepo, AnnualLeaveTimeRemainHistRepository annualLeaveTimeRemainHistRepo, AnnualLeaveMaxHistRepository annualLeaveMaxHistRepo,
				RsvLeaveGrantRemainHistRepository rsvLeaveGrantRemainHistRepo, RsvLeaveGrantTimeRemainHistRepository rsvLeaveGrantTimeRemainHistRepo, InterimRemainOffMonthProcess interimRemainOffMonthProcess,
				MonthlyClosureUpdateErrorInforRepository monthlyClosureUpdateErrorInforRepo, MonthlyClosureUpdateLogRepository monthlyClosureUpdateLogRepo, MonthlyClosureUpdatePersonLogRepository monthlyClosureUpdatePersonLogRepo,
				OuenWorkTimeSheetOfDailyRepo ouenWorkTimeSheetOfDailyRepo, OuenWorkTimeOfDailyRepo ouenWorkTimeOfDailyRepo, OuenAggregateFrameSetOfMonthlyRepo ouenAggregateFrameSetOfMonthlyRepo, RegularLaborTimeComRepo regularLaborTimeComRepo,
				DeforLaborTimeComRepo deforLaborTimeComRepo, RegularLaborTimeWkpRepo regularLaborTimeWkpRepo, DeforLaborTimeWkpRepo deforLaborTimeWkpRepo, RegularLaborTimeEmpRepo regularLaborTimeEmpRepo, DeforLaborTimeEmpRepo deforLaborTimeEmpRepo,
				RegularLaborTimeShaRepo regularLaborTimeShaRepo, DeforLaborTimeShaRepo deforLaborTimeShaRepo, ShaFlexMonthActCalSetRepo shaFlexMonthActCalSetRepo, ComFlexMonthActCalSetRepo comFlexMonthActCalSetRepo, EmpFlexMonthActCalSetRepo empFlexMonthActCalSetRepo,
				WkpFlexMonthActCalSetRepo wkpFlexMonthActCalSetRepo, EmpDeforLaborMonthActCalSetRepo empDeforLaborMonthActCalSetRepo, EmpRegulaMonthActCalSetRepo empRegulaMonthActCalSetRepo, ComDeforLaborMonthActCalSetRepo comDeforLaborMonthActCalSetRepo,
				ComRegulaMonthActCalSetRepo comRegulaMonthActCalSetRepo, ShaDeforLaborMonthActCalSetRepo shaDeforLaborMonthActCalSetRepo, ShaRegulaMonthActCalSetRepo shaRegulaMonthActCalSetRepo, WkpDeforLaborMonthActCalSetRepo wkpDeforLaborMonthActCalSetRepo,
				WkpRegulaMonthActCalSetRepo wkpRegulaMonthActCalSetRepo, MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo, VerticalTotalMethodOfMonthlyRepository verticalTotalMethodOfMonthlyRepo, StampCardRepository stampCardRepo,
				BentoReservationRepository bentoReservationRepo, BentoMenuRepository bentoMenuRepo, IntegrationOfDailyGetter integrationOfDailyGetter, WeekRuleManagementRepo weekRuleManagementRepo, SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter,
				GetProcessingDate getProcessingDate, ElapseYearRepository elapseYearRepo,SyCompanyRecordAdapter syCompanyRecordAdapter, DailySnapshotWorkAdapter snapshotAdapter,
				SuperHD60HConMedRepository superHD60HConMedRepo, MonthlyAggregationRemainingNumber monthlyAggregationRemainingNumber, PayoutSubofHDManaRepository payoutSubofHDManaRepo,
				LeaveComDayOffManaRepository leaveComDayOffManaRepo,CheckCareService checkChildCareService,WorkingConditionItemService workingConditionItemService, PublicHolidaySettingRepository publicHolidaySettingRepo, 
				PublicHolidayManagementUsageUnitRepository publicHolidayManagementUsageUnitRepo, CompanyMonthDaySettingRepository companyMonthDaySettingRepo, TempPublicHolidayManagementRepository tempPublicHolidayManagementRepo,
				PublicHolidayCarryForwardDataRepository publicHolidayCarryForwardDataRepo, EmploymentMonthDaySettingRepository employmentMonthDaySettingRepo, WorkplaceMonthDaySettingRepository workplaceMonthDaySettingRepo,
				EmployeeMonthDaySettingRepository employeeMonthDaySettingRepo, PublicHolidayCarryForwardHistoryRepository publicHolidayCarryForwardHistoryRepo,ChildCareUsedNumberRepository childCareUsedNumberRepo,
				CareUsedNumberRepository careUsedNumberRepo, ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepo, CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepo, TempChildCareManagementRepository tempChildCareManagementRepo,
				TempCareManagementRepository tempCareManagementRepo, NursingLeaveSettingRepository nursingLeaveSettingRepo,ExecutionLogRepository executionLogRepo,WorkingConditionRepository workingConditionRepository, TransactionService transaction,
				EmploymentAdapter employmentAdapter, CreatingDailyResultsConditionRepository creatingDailyResultsConditionRepo, GetPeriodFromPreviousToNextGrantDate getPeriodFromPreviousToNextGrantDate, WorkDaysNumberOnLeaveCountRepository workDaysNumberOnLeaveCountRepo,
				CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter, EmpComHisAdapter empComHisAdapter) {
			
		super(comSubstVacationRepo, compensLeaveComSetRepo, specialLeaveGrantRepo, empEmployeeAdapter, grantDateTblRepo,
				annLeaEmpBasicInfoRepo, specialHolidayRepo, interimSpecialHolidayMngRepo, specialLeaveBasicInfoRepo,
				interimRecAbasMngRepo, empSubstVacationRepo, substitutionOfHDManaDataRepo, payoutManagementDataRepo,
				interimBreakDayOffMngRepo, comDayOffManaDataRepo, companyAdapter, shareEmploymentAdapter,
				leaveManaDataRepo, workingConditionItemRepo, workingConditionRepository, workTimeSettingRepo,
				fixedWorkSettingRepo, flowWorkSettingRepo, diffTimeWorkSettingRepo, flexWorkSettingRepo,
				predetemineTimeSettingRepo, closureRepo, closureEmploymentRepo, workTypeRepo,
				remainCreateInforByApplicationData, compensLeaveEmSetRepo, employmentSettingRepo,
				retentionYearlySettingRepo, annualPaidLeaveSettingRepo, outsideOTSettingRepo, workdayoffFrameRepo,
				yearHolidayRepo, usageUnitSettingRepo, regularLaborTimeComRepo, deforLaborTimeComRepo,
				regularLaborTimeWkpRepo, deforLaborTimeWkpRepo, regularLaborTimeEmpRepo, deforLaborTimeEmpRepo,
				regularLaborTimeShaRepo, deforLaborTimeShaRepo, sharedAffWorkPlaceHisAdapter, lengthServiceRepository,
				grantYearHolidayRepo, payoutSubofHDManaRepo, leaveComDayOffManaRepo, checkChildCareService,
				workingConditionItemService, remainCreateInforByRecordData, sysEmploymentHisAdapter, elapseYearRepo,
				empComHisAdapter, closureStatusManagementRepo);

			this.tmpResereLeaveMngRepo = tmpResereLeaveMngRepo;
			this.sysEmploymentHisAdapter = sysEmploymentHisAdapter;
			this.rervLeaGrantRemDataRepo = rervLeaGrantRemDataRepo;
			this.workInformationRepo = workInformationRepo;
			this.annLeaRemNumEachMonthRepo = annLeaRemNumEachMonthRepo;
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
			this.remainCreateInforByApplicationData = remainCreateInforByApplicationData;
			this.usageUnitSettingRepo = usageUnitSettingRepo;
			this.affWorkplaceAdapter = affWorkplaceAdapter;
			this.timeLeavingOfDailyPerformanceRepo = timeLeavingOfDailyPerformanceRepo;
			this.temporaryTimeOfDailyPerformanceRepo = temporaryTimeOfDailyPerformanceRepo;
			this.specificDateAttrOfDailyPerforRepo = specificDateAttrOfDailyPerforRepo;
			this.employeeDailyPerErrorRepo = employeeDailyPerErrorRepo;
			this.anyItemValueOfDailyRepo = anyItemValueOfDailyRepo;
			this.pcLogOnInfoOfDailyRepo = pcLogOnInfoOfDailyRepo;
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
			this.insufficientFlexHolidayMntRepo = insufficientFlexHolidayMntRepo;
			this.flexShortageLimitRepo = flexShortageLimitRepo;
			this.roundingSetOfMonthlyRepo = roundingSetOfMonthlyRepo;
			this.totalTimesRepo = totalTimesRepo;
			this.agreementOperationSettingRepo = agreementOperationSettingRepo;
			this.predetemineTimeSettingRepo = predetemineTimeSettingRepo;
			this.parallel = parallel;
			this.checkBeforeCalcFlexChangeService = checkBeforeCalcFlexChangeService;
			this.companyAdapter = companyAdapter;
			this.anyItemOfMonthlyRepo = anyItemOfMonthlyRepo;
			this.empCalAndSumExeLogRepo = empCalAndSumExeLogRepo;
			this.editStateOfMonthlyPerRepo = editStateOfMonthlyPerRepo;
			this.affiliationInforOfDailyPerforRepo = affiliationInforOfDailyPerforRepo;
			this.specialHolidayRepo = specialHolidayRepo;
			this.converterFactory = converterFactory;
			this.predWorkingDaysAdaptor = predWorkingDaysAdaptor;
			this.updateAllDomainMonthService = updateAllDomainMonthService;
			this.agreementUnitSetRepo = agreementUnitSetRepo;
			this.agreementTimeWorkPlaceRepo = agreementTimeWorkPlaceRepo;
			this.affClassficationAdapter = affClassficationAdapter;
			this.syEmploymentAdapter = syEmploymentAdapter;
			this.agreementTimeOfEmploymentRepo = agreementTimeOfEmploymentRepo;
			this.agreementTimeOfClassificationRepo = agreementTimeOfClassificationRepo;
			this.agreementTimeCompanyRepo = agreementTimeCompanyRepo;
			this.agreementYearSettingRepo = agreementYearSettingRepo;
			this.agreementMonthSettingRepo = agreementMonthSettingRepo;
			this.agreementTimeOfManagePeriodRepo = agreementTimeOfManagePeriodRepo;
			this.targetPersonRepo = targetPersonRepo;
			this.errMessageInfoRepo = errMessageInfoRepo;
			this.substitutionOfHDManaDataRepo = substitutionOfHDManaDataRepo;
			this.payoutManagementDataRepo = payoutManagementDataRepo;
			this.comDayOffManaDataRepo = comDayOffManaDataRepo;
			this.leaveManaDataRepo = leaveManaDataRepo;
			this.interimBreakDayOffMngRepo = interimBreakDayOffMngRepo;
			this.specialLeaveGrantRepo = specialLeaveGrantRepo;
			this.annualLeaveTimeRemainHistRepo = annualLeaveTimeRemainHistRepo;
			this.annualLeaveMaxHistRepo = annualLeaveMaxHistRepo;
			this.rsvLeaveGrantRemainHistRepo = rsvLeaveGrantRemainHistRepo;
			this.rsvLeaveGrantTimeRemainHistRepo = rsvLeaveGrantTimeRemainHistRepo;
			this.interimRecAbasMngRepo = interimRecAbasMngRepo;
			this.interimSpecialHolidayMngRepo = interimSpecialHolidayMngRepo;
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
			this.verticalTotalMethodOfMonthlyRepo = verticalTotalMethodOfMonthlyRepo;
			this.stampCardRepo = stampCardRepo;
			this.bentoReservationRepo = bentoReservationRepo;
			this.bentoMenuRepo = bentoMenuRepo;
			this.weekRuleManagementRepo = weekRuleManagementRepo;
			this.integrationOfDailyGetter = integrationOfDailyGetter;
			this.getProcessingDate = getProcessingDate;
			this.snapshotAdapter = snapshotAdapter;
			this.superHD60HConMedRepo = superHD60HConMedRepo;
			this.monthlyAggregationRemainingNumber = monthlyAggregationRemainingNumber;
			this.elapseYearRepository = elapseYearRepo;
			this.leaveComDayOffManaRepo = leaveComDayOffManaRepo;
			this.payoutSubofHDManaRepo = payoutSubofHDManaRepo;
			this.publicHolidaySettingRepo = publicHolidaySettingRepo;
			this.publicHolidayManagementUsageUnitRepo = publicHolidayManagementUsageUnitRepo;
			this.companyMonthDaySettingRepo = companyMonthDaySettingRepo;
			this.tempPublicHolidayManagementRepo = tempPublicHolidayManagementRepo;
			this.publicHolidayCarryForwardDataRepo = publicHolidayCarryForwardDataRepo;
			this.employmentMonthDaySettingRepo = employmentMonthDaySettingRepo;
			this.workplaceMonthDaySettingRepo = workplaceMonthDaySettingRepo;
			this.employeeMonthDaySettingRepo = employeeMonthDaySettingRepo;
			this.publicHolidayCarryForwardHistoryRepo = publicHolidayCarryForwardHistoryRepo;
			this.childCareUsedNumberRepo = childCareUsedNumberRepo;
			this.careUsedNumberRepo = careUsedNumberRepo;
			this.childCareLeaveRemInfoRepo = childCareLeaveRemInfoRepo;
			this.careLeaveRemainingInfoRepo = careLeaveRemainingInfoRepo;
			this.tempChildCareManagementRepo = tempChildCareManagementRepo;
			this.tempCareManagementRepo = tempCareManagementRepo;
			this.nursingLeaveSettingRepo = nursingLeaveSettingRepo;
			this.executionLogRepo = executionLogRepo;
			this.workingConditionRepository = workingConditionRepository;
			this.workDaysNumberOnLeaveCountRepo = workDaysNumberOnLeaveCountRepo;
			this.getPeriodFromPreviousToNextGrantDate = getPeriodFromPreviousToNextGrantDate;
			this.transaction = transaction;
			this.employmentAdapter = employmentAdapter;
			this.creatingDailyResultsConditionRepo = creatingDailyResultsConditionRepo;
			this.calculateDailyRecordServiceCenter = calculateDailyRecordServiceCenter;
			this.remainMergeRepo = remainMergeRepo;
		}
		protected EmploymentAdapter employmentAdapter;
		protected CreatingDailyResultsConditionRepository creatingDailyResultsConditionRepo;
		private WorkDaysNumberOnLeaveCountRepository workDaysNumberOnLeaveCountRepo;

		protected TransactionService transaction;
		
		private SuperHD60HConMedRepository superHD60HConMedRepo;

		private DailySnapshotWorkAdapter snapshotAdapter;

		private GetProcessingDate getProcessingDate;

		private TmpResereLeaveMngRepository tmpResereLeaveMngRepo;

		private SysEmploymentHisAdapter sysEmploymentHisAdapter;

		private RervLeaGrantRemDataRepository rervLeaGrantRemDataRepo;

		private WorkInformationRepository workInformationRepo;

		private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;

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

		private AttendanceTimeRepository attendanceTimeRepo;

		private PayItemCountOfMonthlyRepository payItemCountOfMonthlyRepo;

		private OptionalItemRepository optionalItemRepo;

		private EmpConditionRepository empConditionRepo;

		private FormulaRepository formulaRepo;

		private FormulaDispOrderRepository formulaDispOrderRepo;

		private ActualLockRepository actualLockRepo;

		private LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthlyRepo;

		private OvertimeWorkFrameRepository roleOvertimeWorkRepo;

		private HolidayAddtionRepository holidayAddtionRepo;

		private MonthlyAggrSetOfFlexRepository monthlyAggrSetOfFlexRepo;

		private InsufficientFlexHolidayMntRepository insufficientFlexHolidayMntRepo;

		private FlexShortageLimitRepository flexShortageLimitRepo;

		private RoundingSetOfMonthlyRepository roundingSetOfMonthlyRepo;

		private TotalTimesRepository totalTimesRepo;

		private AgreementOperationSettingRepository agreementOperationSettingRepo;

		private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

		private ManagedParallelWithContext parallel;

		private CheckBeforeCalcFlexChangeService checkBeforeCalcFlexChangeService;

		private AnyItemOfMonthlyRepository anyItemOfMonthlyRepo;

		private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo;

		private EditStateOfMonthlyPerRepository editStateOfMonthlyPerRepo;

//		private ManagedExecutorService executorService;

		private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepo;

		private SpecialHolidayRepository specialHolidayRepo;

		private AttendanceItemConvertFactory converterFactory;

		private GetPredWorkingDaysAdaptor predWorkingDaysAdaptor;

		private UpdateAllDomainMonthService updateAllDomainMonthService;

		private AgreementUnitSettingRepository agreementUnitSetRepo;

		private Workplace36AgreedHoursRepository agreementTimeWorkPlaceRepo;

		private AffClassificationAdapter affClassficationAdapter;

		private SyEmploymentAdapter syEmploymentAdapter;

		private Employment36HoursRepository agreementTimeOfEmploymentRepo;

		private Classification36AgreementTimeRepository agreementTimeOfClassificationRepo;

		private Company36AgreedHoursRepository agreementTimeCompanyRepo;



		private AgreementYearSettingRepository agreementYearSettingRepo;

		private AgreementMonthSettingRepository agreementMonthSettingRepo;

		private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepo;

		private TargetPersonRepository targetPersonRepo;

		private ErrMessageInfoRepository errMessageInfoRepo;

		private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;

		private PayoutManagementDataRepository payoutManagementDataRepo;

		private ComDayOffManaDataRepository comDayOffManaDataRepo;

		private LeaveManaDataRepository leaveManaDataRepo;

		private SpecialLeaveGrantRepository specialLeaveGrantRepo;

		private AnnualLeaveTimeRemainHistRepository annualLeaveTimeRemainHistRepo;

		private AnnualLeaveMaxHistRepository annualLeaveMaxHistRepo;

		private RsvLeaveGrantRemainHistRepository rsvLeaveGrantRemainHistRepo;

		private RsvLeaveGrantTimeRemainHistRepository rsvLeaveGrantTimeRemainHistRepo;

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

		private VerticalTotalMethodOfMonthlyRepository verticalTotalMethodOfMonthlyRepo;

		private StampCardRepository stampCardRepo;

		private BentoReservationRepository bentoReservationRepo;

		private BentoMenuRepository bentoMenuRepo;

		private WeekRuleManagementRepo weekRuleManagementRepo;

		private IntegrationOfDailyGetter integrationOfDailyGetter;

		private MonthlyAggregationRemainingNumber monthlyAggregationRemainingNumber;

		private ElapseYearRepository elapseYearRepository;

		private PayoutSubofHDManaRepository payoutSubofHDManaRepo;


		private LeaveComDayOffManaRepository leaveComDayOffManaRepo;
		
		private PublicHolidaySettingRepository publicHolidaySettingRepo;
		
		private PublicHolidayManagementUsageUnitRepository publicHolidayManagementUsageUnitRepo;
		
		private CompanyMonthDaySettingRepository companyMonthDaySettingRepo;
		
		private TempPublicHolidayManagementRepository tempPublicHolidayManagementRepo;
		
		private PublicHolidayCarryForwardDataRepository publicHolidayCarryForwardDataRepo;
		
		private EmploymentMonthDaySettingRepository employmentMonthDaySettingRepo;
		
		private WorkplaceMonthDaySettingRepository workplaceMonthDaySettingRepo;
		
		private EmployeeMonthDaySettingRepository employeeMonthDaySettingRepo;
		
		private PublicHolidayCarryForwardHistoryRepository publicHolidayCarryForwardHistoryRepo;
		
		private ChildCareUsedNumberRepository childCareUsedNumberRepo;
		
		private CareUsedNumberRepository careUsedNumberRepo;
		
		private ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepo;
		
		private CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepo;
		
		private TempChildCareManagementRepository tempChildCareManagementRepo;
		
		private TempCareManagementRepository tempCareManagementRepo;
		
		private NursingLeaveSettingRepository nursingLeaveSettingRepo;
		
		private ExecutionLogRepository executionLogRepo;
		
		private WorkingConditionRepository workingConditionRepository;

		private GetPeriodFromPreviousToNextGrantDate getPeriodFromPreviousToNextGrantDate; 

		private RemainMergeRepository remainMergeRepo;
		
		private  CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter;

		RecordDomRequireServiceCache cache = new RecordDomRequireServiceCache();

		@Override
		public Optional<SEmpHistoryImport> employeeEmploymentHis(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate baseDate) {
			return sysEmploymentHisAdapter.findSEmpHistBySidRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

		@Override
		public Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfos(String employeeId, DatePeriod datePeriod) {
			Map<GeneralDate, WorkInfoOfDailyAttendance> dataForResult = new ConcurrentHashMap<GeneralDate, WorkInfoOfDailyAttendance>();
			Map<GeneralDate, WorkInfoOfDailyAttendance> dataByRepo = new ConcurrentHashMap<GeneralDate, WorkInfoOfDailyAttendance>();
			
			for(GeneralDate date : datePeriod.datesBetween()){
				String keyForGet = employeeId + "-" + date.toString();	
				if(cache.getWorkInfoOfDailyAttendanceMap().containsKey(keyForGet)){
					dataForResult.put(date,cache.getWorkInfoOfDailyAttendanceMap().get(keyForGet));
				}
				else{
					dataByRepo =workInformationRepo.findByPeriodOrderByYmd(employeeId, new DatePeriod(date,datePeriod.end()))
							.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getWorkInformation()));
					
					dataByRepo.forEach((k, v) ->{
						String keyForPut = employeeId + "-" + k.toString();
						if(!cache.getWorkInfoOfDailyAttendanceMap().containsKey(keyForPut)){
							cache.getWorkInfoOfDailyAttendanceMap().put(keyForPut, v);
						}
						dataForResult.put(k, v);
					});
					
					break;
				}
			};

			return dataForResult;
		}

		@Override
		public Optional<OperationStartSetDailyPerform> dailyOperationStartSet(CompanyId companyId) {
			if(!cache.getOperationStartSetDailyPerformCache().isPresent()){
				cache.setOperationStartSetDailyPerformCache(operationStartSetDailyPerformRepo.findByCid(companyId));
			}
			return cache.getOperationStartSetDailyPerformCache();
		}

		@Override
		public List<EmploymentHistImport> employmentHistories(String employeeId) {
			if(!cache.getEmploymentHistImportMap().containsKey(employeeId)){
				cache.getEmploymentHistImportMap().put(employeeId, employmentHistAdapter.findByEmployeeIdOrderByStartDate(employeeId));
			}
			return cache.getEmploymentHistImportMap().get(employeeId);
		}

		@Override
		public List<GrantHdTblSet> grantHdTblSets(String companyId) {
			if(cache.getGrantHdTblSetCache().isEmpty()){
				cache.setGrantHdTblSetCache(yearHolidayRepo.findAll(companyId));
			}
			return cache.getGrantHdTblSetCache();
		}

		@Override
		public List<ScheRemainCreateInfor> scheRemainCreateInfor(String cid, String sid,
				DatePeriod dateData) {
			
			return remainCreateInforByScheData.createRemainInforNew(cid, sid, dateData.datesBetween());

		}

		@Override
		public List<RecordRemainCreateInfor> recordRemainCreateInfor(CacheCarrier cacheCarrier, String cid, String sid,
				DatePeriod dateData) {
			return remainCreateInforByRecordData.lstRecordRemainData(cacheCarrier, cid, sid, dateData.datesBetween());
		}

		@Override
		public List<AppRemainCreateInfor> appRemainCreateInfor(CacheCarrier cacheCarrier, String cid, String sid,
				DatePeriod dateData) {
			return remainCreateInforByApplicationData.lstRemainDataFromApp(cacheCarrier, cid, sid, dateData);
		}

		@Override
		public Optional<UsageUnitSetting> usageUnitSetting(String companyId) {
			if(!cache.getUsageUnitSettingCache().isPresent()) {
				cache.setUsageUnitSettingCache(usageUnitSettingRepo.findByCompany(companyId));
			}
			return cache.getUsageUnitSettingCache();
		}

		@Override
		public Map<GeneralDate, TimeLeavingOfDailyAttd> dailyTimeLeavings(String employeeId, DatePeriod datePeriod) {
			
			Map<GeneralDate, TimeLeavingOfDailyAttd> dataForResult = new ConcurrentHashMap<GeneralDate, TimeLeavingOfDailyAttd>();
			Map<GeneralDate, TimeLeavingOfDailyAttd> dataByRepo = new ConcurrentHashMap<GeneralDate, TimeLeavingOfDailyAttd>();
			
			for(GeneralDate date : datePeriod.datesBetween()){
				String keyForGet = employeeId + "-" + date.toString();	
				if(cache.getTimeLeavingOfDailyAttdMap().containsKey(keyForGet)){
					dataForResult.put(date,cache.getTimeLeavingOfDailyAttdMap().get(keyForGet));
				}
				else{
					dataByRepo =timeLeavingOfDailyPerformanceRepo.findbyPeriodOrderByYmd(employeeId, new DatePeriod(date, datePeriod.end()))
							.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAttendance()));
					
					dataByRepo.forEach((k, v) ->{
						String keyForPut = employeeId + "-" + k.toString();
						if(!cache.getTimeLeavingOfDailyAttdMap().containsKey(keyForPut)){
							cache.getTimeLeavingOfDailyAttdMap().put(keyForPut, v);
						}
						dataForResult.put(k, v);
					});
					
					break;
				}
			};

			return dataForResult;
		}

		@Override
		public Map<GeneralDate, TemporaryTimeOfDailyAttd> dailyTemporaryTimes(String employeeId, DatePeriod datePeriod) {
			
			Map<GeneralDate, TemporaryTimeOfDailyAttd> dataForResult = new ConcurrentHashMap<GeneralDate, TemporaryTimeOfDailyAttd>();
			Map<GeneralDate, TemporaryTimeOfDailyAttd> dataByRepo = new ConcurrentHashMap<GeneralDate, TemporaryTimeOfDailyAttd>();
			
			for(GeneralDate date : datePeriod.datesBetween()){
				String keyForGet = employeeId + "-" + date.toString();	
				if(cache.getTemporaryTimeOfDailyAttdMap().containsKey(keyForGet)){
					dataForResult.put(date,cache.getTemporaryTimeOfDailyAttdMap().get(keyForGet));
				}
				else{
					dataByRepo =temporaryTimeOfDailyPerformanceRepo.findbyPeriodOrderByYmd(employeeId,  new DatePeriod(date, datePeriod.end()))
							.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAttendance()));
					
					dataByRepo.forEach((k, v) ->{
						String keyForPut = employeeId + "-" + k.toString();
						if(!cache.getTemporaryTimeOfDailyAttdMap().containsKey(keyForPut)){
							cache.getTemporaryTimeOfDailyAttdMap().put(keyForPut, v);
						}
						dataForResult.put(k, v);
					});
					
					break;
				}
			};

			return dataForResult;
		}

		@Override
		public Map<GeneralDate, SpecificDateAttrOfDailyAttd> dailySpecificDates(String employeeId, DatePeriod datePeriod) {
			
			Map<GeneralDate, SpecificDateAttrOfDailyAttd> dataForResult = new ConcurrentHashMap<GeneralDate, SpecificDateAttrOfDailyAttd>();
			Map<GeneralDate, SpecificDateAttrOfDailyAttd> dataByRepo = new ConcurrentHashMap<GeneralDate, SpecificDateAttrOfDailyAttd>();
			
			for(GeneralDate date : datePeriod.datesBetween()){
				String keyForGet = employeeId + "-" + date.toString();	
				if(cache.getSpecificDateAttrOfDailyAttdMap().containsKey(keyForGet)){
					dataForResult.put(date,cache.getSpecificDateAttrOfDailyAttdMap().get(keyForGet));
				}
				else{
					dataByRepo =specificDateAttrOfDailyPerforRepo.findByPeriodOrderByYmd(employeeId,  new DatePeriod(date, datePeriod.end()))
							.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getSpecificDay()));
					
					dataByRepo.forEach((k, v) ->{
						String keyForPut = employeeId + "-" + k.toString();
						if(!cache.getSpecificDateAttrOfDailyAttdMap().containsKey(keyForPut)){
							cache.getSpecificDateAttrOfDailyAttdMap().put(keyForPut, v);
						}
						dataForResult.put(k, v);
					});
					
					break;
				}
			};

			return dataForResult;
			
		}

		@Override
		public List<EmployeeDailyPerError> dailyEmpErrors(String employeeId, DatePeriod datePeriod) {
			
			List<EmployeeDailyPerError> dataForResult = new ArrayList<>();
			List<EmployeeDailyPerError> dataByRepo =  new ArrayList<>();
			
			for(GeneralDate date : datePeriod.datesBetween()){
				String keyForGet = employeeId + "-" + date.toString();	
				if(cache.getEmployeeDailyPerErrorMap().containsKey(keyForGet)){
					dataForResult.add(cache.getEmployeeDailyPerErrorMap().get(keyForGet));
				}
				else{
					dataByRepo =employeeDailyPerErrorRepo.findByPeriodOrderByYmd(employeeId,  new DatePeriod(date, datePeriod.end()));
					
					dataByRepo.forEach((v) ->{
						String keyForPut = employeeId + "-" + v.getDate().toString();
						if(!cache.getEmployeeDailyPerErrorMap().containsKey(keyForPut)){
							cache.getEmployeeDailyPerErrorMap().put(keyForPut, v);
						}
						dataForResult.add(v);
					});
					
					break;
				}
			};

			return dataForResult;
		}

		@Override
		public Map<GeneralDate, AnyItemValueOfDailyAttd> dailyAnyItems(List<String> employeeIds, DatePeriod baseDate) {
			
			
			Map<GeneralDate, AnyItemValueOfDailyAttd> dataForResult = new ConcurrentHashMap<GeneralDate, AnyItemValueOfDailyAttd>();
			Map<GeneralDate, AnyItemValueOfDailyAttd> dataByRepo = new ConcurrentHashMap<GeneralDate, AnyItemValueOfDailyAttd>();
		
			for(String employeeId : employeeIds){
				for(GeneralDate date : baseDate.datesBetween()){
					String keyForGet = employeeId + "-" + date.toString();	
					if(cache.getAnyItemValueOfDailyAttdMap().containsKey(keyForGet)){
						dataForResult.put(date,cache.getAnyItemValueOfDailyAttdMap().get(keyForGet));
					}
					else{
						dataByRepo =anyItemValueOfDailyRepo.finds(Arrays.asList(employeeId), new DatePeriod(date, baseDate.end()))
								.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAnyItem()));
						
						dataByRepo.forEach((k, v) ->{
							String keyForPut = employeeId + "-" + k.toString();
							if(!cache.getAnyItemValueOfDailyAttdMap().containsKey(keyForPut)){
								cache.getAnyItemValueOfDailyAttdMap().put(keyForPut, v);
							}
							dataForResult.put(k, v);
						});
						
						break;
					}
				};
			}
			return dataForResult;
		}

		@Override
		public Map<GeneralDate, PCLogOnInfoOfDailyAttd> dailyPcLogons(List<String> employeeIds, DatePeriod baseDate) {
			
			Map<GeneralDate, PCLogOnInfoOfDailyAttd> dataForResult = new ConcurrentHashMap<GeneralDate, PCLogOnInfoOfDailyAttd>();
			Map<GeneralDate, PCLogOnInfoOfDailyAttd> dataByRepo = new ConcurrentHashMap<GeneralDate, PCLogOnInfoOfDailyAttd>();
		
			for(String employeeId : employeeIds){
				for(GeneralDate date : baseDate.datesBetween()){
					String keyForGet = employeeId + "-" + date.toString();	
					if(cache.getPCLogOnInfoOfDailyAttdMap().containsKey(keyForGet)){
						dataForResult.put(date,cache.getPCLogOnInfoOfDailyAttdMap().get(keyForGet));
					}
					else{
						dataByRepo =pcLogOnInfoOfDailyRepo.finds(Arrays.asList(employeeId), new DatePeriod(date, baseDate.end()))
								.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTimeZone()));
						
						dataByRepo.forEach((k, v) ->{
							String keyForPut = employeeId + "-" + k.toString();
							if(!cache.getPCLogOnInfoOfDailyAttdMap().containsKey(keyForPut)){
								cache.getPCLogOnInfoOfDailyAttdMap().put(keyForPut, v);
							}
							dataForResult.put(k, v);
						});
						
						break;
					}
				};
			}
			return dataForResult;
		}

		@Override
		public Map<GeneralDate, AttendanceTimeOfDailyAttendance> dailyAttendanceTimes(String employeeId, DatePeriod datePeriod) {
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> dataForResult = new ConcurrentHashMap<GeneralDate, AttendanceTimeOfDailyAttendance>();
			Map<GeneralDate, AttendanceTimeOfDailyAttendance> dataByRepo = new ConcurrentHashMap<GeneralDate, AttendanceTimeOfDailyAttendance>();
			
			for(GeneralDate date : datePeriod.datesBetween()){
				String keyForGet = employeeId + "-" + date.toString();	
				if(cache.getAttendanceTimeOfDailyAttendanceMap().containsKey(keyForGet)){
					dataForResult.put(date,cache.getAttendanceTimeOfDailyAttendanceMap().get(keyForGet));
				}
				else{
					dataByRepo =attendanceTimeRepo.findByPeriodOrderByYmd(employeeId, new DatePeriod(date, datePeriod.end()))
							.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTime()));
					
					dataByRepo.forEach((k, v) ->{
						String keyForPut = employeeId + "-" + k.toString();
						if(!cache.getAttendanceTimeOfDailyAttendanceMap().containsKey(keyForPut)){
							cache.getAttendanceTimeOfDailyAttendanceMap().put(keyForPut, v);
						}
						dataForResult.put(k, v);
					});
					
					break;
				}
			};

			return dataForResult;
		}

		@Override
		public Optional<PayItemCountOfMonthly> monthPayItemCount(String companyId) {
			if(!cache.getPayItemCountOfMonthlyCache().isPresent()){
				cache.setPayItemCountOfMonthlyCache(payItemCountOfMonthlyRepo.find(companyId));
			}
			return cache.getPayItemCountOfMonthlyCache();
		}

		@Override
		public List<OptionalItem> optionalItems(String companyId) {
			if(cache.getOptionalItemCache().isEmpty()){
				cache.setOptionalItemCache(optionalItemRepo.findAll(companyId));
			}
			return cache.getOptionalItemCache();
		}

		@Override
		public List<EmpCondition> employmentConditions(String companyId, List<Integer> optionalItemNoList) {
			return empConditionRepo.findAll(companyId, optionalItemNoList);
		}

		@Override
		public List<Formula> formulas(String companyId) {
			if(cache.getFormulaCache().isEmpty()){
				cache.setFormulaCache(formulaRepo.find(companyId));
			}
			return cache.getFormulaCache();
		}

		@Override
		public List<FormulaDispOrder> formulaDispOrder(String companyId) {
			if(cache.getFormulaDispOrderCache().isEmpty()){
				cache.setFormulaDispOrderCache(formulaDispOrderRepo.findAll(companyId));
			}
			return cache.getFormulaDispOrderCache();
		}

		@Override
		public Map<String, List<LengthServiceTbl>> lengthServiceTbl(String companyId, List<String> yearHolidayCode) {
			return lengthServiceRepo.findByCode(companyId, yearHolidayCode);
		}

		@Override
		public List<EmptYearlyRetentionSetting> emptYearlyRetentionSet(String companyId) {
			if(cache.getEmptYearlyRetentionSettingCache().isEmpty()){
				cache.setEmptYearlyRetentionSettingCache(employmentSettingRepo.findAll(companyId));
			}
			return cache.getEmptYearlyRetentionSettingCache();
		}

		@Override
		public Optional<LegalTransferOrderSetOfAggrMonthly> monthLegalTransferOrderCalcSet(String companyId) {
			if(!cache.getLegalTransferOrderSetOfAggrMonthlyCache().isPresent()){
				cache.setLegalTransferOrderSetOfAggrMonthlyCache(legalTransferOrderSetOfAggrMonthlyRepo.find(companyId));
			}
			return cache.getLegalTransferOrderSetOfAggrMonthlyCache();
		}

		@Override
		public List<OvertimeWorkFrame> roleOvertimeWorks(String companyId) {
			if(cache.getOvertimeWorkFrameCache().isEmpty()){
				cache.setOvertimeWorkFrameCache(roleOvertimeWorkRepo.getOvertimeWorkFrameByFrameByCom(companyId, NotUseAtr.USE.value));
			}
			return cache.getOvertimeWorkFrameCache();
		}

		@Override
		public Map<String, AggregateRoot> holidayAddtionSets(String companyId) {
			return holidayAddtionRepo.findByCompanyId(companyId);
		}

		@Override
		public Optional<MonthlyAggrSetOfFlex> monthFlexAggrSet(String companyId) {
			if(!cache.getMonthlyAggrSetOfFlexCache().isPresent()){
				cache.setMonthlyAggrSetOfFlexCache(monthlyAggrSetOfFlexRepo.find(companyId));
			}
			return cache.getMonthlyAggrSetOfFlexCache();
		}

		@Override
		public Optional<InsufficientFlexHolidayMnt> insufficientFlexHolidayMnt(String cid) {
			if(!cache.getInsufficientFlexHolidayMntCache().isPresent()){
				cache.setInsufficientFlexHolidayMntCache(insufficientFlexHolidayMntRepo.findByCId(cid));
			}
			return cache.getInsufficientFlexHolidayMntCache();
		}

		@Override
		public Optional<FlexShortageLimit> flexShortageLimit(String companyId) {
			if(!cache.getFlexShortageLimitCache().isPresent()){
				cache.setFlexShortageLimitCache(flexShortageLimitRepo.get(companyId));
			}
			return cache.getFlexShortageLimitCache();
		}

		@Override
		public Optional<RoundingSetOfMonthly> monthRoundingSet(String companyId) {
			if(!cache.getRoundingSetOfMonthlyCache().isPresent()) {
				cache.setRoundingSetOfMonthlyCache(roundingSetOfMonthlyRepo.find(companyId));
			}
			return cache.getRoundingSetOfMonthlyCache();
		}

		@Override
		public List<TotalTimes> totalTimes(String companyId) {
			if(cache.getTotalTimesCache().isEmpty()){
				cache.setTotalTimesCache(totalTimesRepo.getAllTotalTimes(companyId));
			}
			return cache.getTotalTimesCache();
		}

		@Override
		public Optional<AgreementOperationSetting> agreementOperationSetting(String companyId) {
			if(!cache.getAgreementOperationSettingCache().isPresent()) {
				cache.setAgreementOperationSettingCache(agreementOperationSettingRepo.find(companyId));
			}
			return cache.getAgreementOperationSettingCache();
		}

		@Override
		public Optional<SharedAffWorkPlaceHisImport> affWorkPlace(String employeeId, GeneralDate baseDate) {
			
			List<SharedAffWorkPlaceHisImport> byCache = new ArrayList<>();
			cache.getSharedAffWorkPlaceHisImportMapMap().forEach((k,v)->{
				if(k.getKey().equals(employeeId)){
					byCache.add(v);	
				}
			});
			
			Optional<SharedAffWorkPlaceHisImport> dataForResult = byCache.stream().filter(c->c.getDateRange().contains(baseDate)).findFirst();
			if(dataForResult.isPresent())
				return dataForResult;
			
			dataForResult = sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, baseDate);
			if(dataForResult.isPresent() && !cache.getSharedAffWorkPlaceHisImportMapMap().containsKey(Pair.of(employeeId, dataForResult.get().getDateRange()))){
				cache.getSharedAffWorkPlaceHisImportMapMap().put(Pair.of(employeeId, dataForResult.get().getDateRange()), dataForResult.get());
			}
				
			return dataForResult;
		}
		
		@Override
		public Map<GeneralDate, Map<String, Optional<SharedAffWorkPlaceHisImport>>> affWorkPlace(String companyId, List<String> employeeId, DatePeriod baseDate) {
			return sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHisClones(companyId, employeeId, baseDate);
		}

		@Override
		public Optional<WorkingCondition> workingCondition(String historyId) {
			if(!cache.getWorkingConditionMap().containsKey(historyId)){
				cache.getWorkingConditionMap().put(historyId, workingConditionRepo.getByHistoryId(historyId));
			}
			return cache.getWorkingConditionMap().get(historyId);
		}

		@Override
		public List<SharedSidPeriodDateEmploymentImport> employmentHistories(CacheCarrier cacheCarrier,
				List<String> sids, DatePeriod datePeriod) {
			return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
		}

		@Override
		public List<ClosureEmployment> employmentClosure(String companyId, List<String> employmentCDs) {
			if(!cache.getEmploymentClosureCache().containsKey(companyId)) {
				cache.getEmploymentClosureCache().put(companyId, closureEmploymentRepo.findAllByCid(companyId));
			}
			return cache.getEmploymentClosureCache().get(companyId).stream()
					.filter(c -> employmentCDs.contains(c.getEmploymentCD())).collect(Collectors.toList());
		}

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetByWorkTimeCode(String companyId,
				String workTimeCode) {
			if(!cache.getPredetemineTimeSettingMap().containsKey(workTimeCode)) {
				cache.getPredetemineTimeSettingMap().put(workTimeCode, predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode));
			}
			return cache.getPredetemineTimeSettingMap().get(workTimeCode);
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			return converterFactory.createDailyConverter();
		}

		@Override
		public ManagedParallelWithContext parallelContext() {
			return parallel;
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
		public List<AgreementMonthSetting> agreementMonthSettingClones(List<String> employeeId, YearMonth yearMonth) {
			List<YearMonth> yearMonths = new ArrayList<YearMonth>();
			yearMonths.add(yearMonth);
			return agreementMonthSettingRepo.findByKey(employeeId, yearMonths);
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
			if(!cache.getEmpCalAndSumExeLogMap().containsKey(empCalAndSumExecLogID)){
				cache.getEmpCalAndSumExeLogMap().put(empCalAndSumExecLogID,
					empCalAndSumExeLogRepo.getByEmpCalAndSumExecLogID(empCalAndSumExecLogID));
			}
			return cache.getEmpCalAndSumExeLogMap().get(empCalAndSumExecLogID);
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

//		@Override
//		public ManagedExecutorService getExecutorService() {
//			return executorService;
//		} 

		@Override
		public Map<GeneralDate, AffiliationInforOfDailyAttd> dailyAffiliationInfors(List<String> employeeIds, DatePeriod ymd) {
			
			Map<GeneralDate, AffiliationInforOfDailyAttd> dataForResult = new ConcurrentHashMap<GeneralDate, AffiliationInforOfDailyAttd>();
			Map<GeneralDate, AffiliationInforOfDailyAttd> dataByRepo = new ConcurrentHashMap<GeneralDate, AffiliationInforOfDailyAttd>();
		
			for(String employeeId : employeeIds){
				for(GeneralDate date : ymd.datesBetween()){
					String keyForGet = employeeId + "-" + date.toString();	
					if(cache.getAffiliationInforOfDailyAttdMap().containsKey(keyForGet)){
						dataForResult.put(date,cache.getAffiliationInforOfDailyAttdMap().get(keyForGet));
					}
					else{
						dataByRepo =affiliationInforOfDailyPerforRepo.finds(Arrays.asList(employeeId), new DatePeriod(date, ymd.end()))
								.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAffiliationInfor()));
						
						dataByRepo.forEach((k, v) ->{
							String keyForPut = employeeId + "-" + k.toString();
							if(!cache.getAffiliationInforOfDailyAttdMap().containsKey(keyForPut)){
								cache.getAffiliationInforOfDailyAttdMap().put(keyForPut, v);
							}
							dataForResult.put(k, v);
						});
						
						break;
					}
				};
			}
			return dataForResult;
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

		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
			return workingConditionItemRepo.getBySidAndStandardDate(employeeId, baseDate);
		}
		
		@Override
		public List<WorkingConditionItem> workingConditionItemClones(List<String> employeeId, GeneralDate baseDate) {
			return workingConditionItemRepo.getByListSidAndStandardDate(employeeId, baseDate);
		}

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
			if(!cache.getAgreementUnitSettingCache().isPresent()){
				cache.setAgreementUnitSettingCache(agreementUnitSetRepo.find(companyId));
			}
			return cache.getAgreementUnitSettingCache();
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
			
			String key = companyId + "-" + laborSystemAtr + "-" + classificationCode;
			if(!cache.getAgreementTimeOfClassificationMap().containsKey(key)){
				cache.getAgreementTimeOfClassificationMap().put(key, agreementTimeOfClassificationRepo
					.getByCidAndClassificationCode(companyId, classificationCode, laborSystemAtr));
			}
			return cache.getAgreementTimeOfClassificationMap().get(key);
		}

		@Override
		public List<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId,
				List<String> classificationCode) {
			return agreementTimeOfClassificationRepo.findCidAndLstCd(companyId, classificationCode);
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
			String key = workplaceId + "-" + laborSystemAtr;
			if(!cache.getAgreementTimeOfWorkPlaceMap().containsKey(key)){
				cache.getAgreementTimeOfWorkPlaceMap().put(key, agreementTimeWorkPlaceRepo.getByWorkplaceId(workplaceId, laborSystemAtr));
			}
			return cache.getAgreementTimeOfWorkPlaceMap().get(key);
		}

		@Override
		public List<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(List<String> workplaceId) {
			return agreementTimeWorkPlaceRepo.getByListWorkplaceId(workplaceId);
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
			return agreementTimeOfEmploymentRepo.findByCidAndListCd(comId, employmentCategoryCode);
		}

		@Override
		public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId,
				String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
			String key = companyId + "-" + employmentCategoryCode + "-" + laborSystemAtr;
			if(!cache.getAgreementTimeOfEmploymentMap().containsKey(key)){
				cache.getAgreementTimeOfEmploymentMap().put(key, 
						agreementTimeOfEmploymentRepo.getByCidAndCd(companyId, employmentCategoryCode, laborSystemAtr));
			}
			return cache.getAgreementTimeOfEmploymentMap().get(key);
		}

		@Override
		public List<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId) {
			if(cache.getAgreementTimeOfCompanyCache().isEmpty()){
				cache.setAgreementTimeOfCompanyCache(agreementTimeCompanyRepo.find(companyId));
			}
			return cache.getAgreementTimeOfCompanyCache();
		}

		@Override
		public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId,
				LaborSystemtAtr laborSystemAtr) {
			if(!cache.getAgreementTimeOfCompanyMap().containsKey(laborSystemAtr)){
				cache.getAgreementTimeOfCompanyMap().put(laborSystemAtr, agreementTimeCompanyRepo.getByCid(companyId, laborSystemAtr));
			}
			return cache.getAgreementTimeOfCompanyMap().get(laborSystemAtr);
		}

		@Override
		public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, GeneralDate criteriaDate) {
			return attendanceTimeOfMonthlyRepo.findByDate(employeeId, criteriaDate);
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
			if(!cache.getClosureStatusManagementOptMap().containsKey(employeeId)){
				cache.getClosureStatusManagementOptMap().put(employeeId, closureStatusManagementRepo.getLatestByEmpId(employeeId));
			}
			return cache.getClosureStatusManagementOptMap().get(employeeId);
		}

//		@Override
//		public void deleteTmpAnnualHolidayMng(String mngId) {
//			tmpAnnualHolidayMngRepo.deleteById(mngId);
//		}

		@Override
		public List<TempAnnualLeaveMngs> tmpAnnualHolidayMng(String sid, DatePeriod dateData) {
			return tmpAnnualHolidayMngRepo.getBySidPeriod(sid, dateData);
		}

//		@Override
//		public void deleteTmpResereLeaveMng(String mngId) {
//			tmpResereLeaveMngRepo.deleteById(mngId);
//		}

		@Override
		public List<TmpResereLeaveMng> tmpResereLeaveMng(String sid, DatePeriod period) {
			return tmpResereLeaveMngRepo.findBySidPriod(sid, period);
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
			annLeaGrantRemDataRepo.add(AppContexts.user().companyId(), data);
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
		public void addOrUpdateAnnualLeaveTimeRemainingHistory(AnnualLeaveTimeRemainingHistory domain) {
			annualLeaveTimeRemainHistRepo.addOrUpdate(domain);
		}

		@Override
		public void addOrUpdateAnnualLeaveMaxHistoryData(AnnualLeaveMaxHistoryData domain) {
			annualLeaveMaxHistRepo.addOrUpdate(domain);
		}

		@Override
		public Optional<AnnualLeaveMaxData> annualLeaveMaxData(String employeeId) {
			if(!cache.getAnnualLeaveMaxDataMap().containsKey(employeeId)){
				cache.getAnnualLeaveMaxDataMap().put(employeeId, annLeaMaxDataRepo.get(employeeId));
			}
			return cache.getAnnualLeaveMaxDataMap().get(employeeId);
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
		public void addOrUpdateReserveLeaveGrantRemainHistoryData(ReserveLeaveGrantRemainHistoryData domain,
				String cid) {
			rsvLeaveGrantRemainHistRepo.addOrUpdate(domain, cid);
		}
		
		@Override
		public void addOrUpdateReserveLeaveGrantTimeRemainHistoryData(ReserveLeaveGrantTimeRemainHistoryData domain) {
			rsvLeaveGrantTimeRemainHistRepo.addOrUpdate(domain);
		}

		@Override
		public List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId) {
			if(!cache.getReserveLeaveGrantRemainingDataMap().containsKey(employeeId)){
				cache.getReserveLeaveGrantRemainingDataMap().put(employeeId, rervLeaGrantRemDataRepo.find(employeeId));
			}
			return cache.getReserveLeaveGrantRemainingDataMap().get(employeeId);
		}

		@Override
		public List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId,
				GeneralDate grantDate) {
			String key = employeeId + "-" + grantDate;
			if(!cache.getReserveLeaveGrantRemainingDatabyGrantDateMap().containsKey(key)){
				cache.getReserveLeaveGrantRemainingDatabyGrantDateMap().put(key, rervLeaGrantRemDataRepo.find(employeeId, grantDate));
			}
			return cache.getReserveLeaveGrantRemainingDatabyGrantDateMap().get(key);
		}

		@Override
		public void updateReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data) {
			rervLeaGrantRemDataRepo.update(data);
		}

		@Override
		public void addReserveLeaveGrantRemainingData(ReserveLeaveGrantRemainingData data) {
			rervLeaGrantRemDataRepo.add(data);
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
		public Optional<PayoutManagementData> payoutManagementData(String Id) {
			return payoutManagementDataRepo.findByID(Id);
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
		public List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String employeeId, int specialCode) {
			String key = employeeId + "-" + specialCode ;
			if(!cache.getSpecialLeaveGrantRemainingDataMap().containsKey(key)){
				cache.getSpecialLeaveGrantRemainingDataMap().put(key, specialLeaveGrantRepo.getAll(employeeId, specialCode));
			}
			return cache.getSpecialLeaveGrantRemainingDataMap().get(key);
		}

		@Override
		public void updateSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data) {
			specialLeaveGrantRepo.update(data);
		}

		@Override
		public void addSpecialLeaveGrantRemainingData(SpecialLeaveGrantRemainingData data) {
			specialLeaveGrantRepo.add(AppContexts.user().companyId(), data);
		}

		@Override
		public List<SpecialHoliday> specialHoliday(String companyId) {
			if(cache.getSpecialHolidayCache().isEmpty()){
				cache.setSpecialHolidayCache(specialHolidayRepo.findByCompanyId(companyId));
			}
			return cache.getSpecialHolidayCache();
		}

		@Override
		public FixedRemainDataForMonthlyAgg monthInterimRemainData(CacheCarrier cacheCarrier, String cid,
				String sid, DatePeriod dateData, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
			return interimRemainOffMonthProcess.monthInterimRemainData(cacheCarrier, cid, sid, dateData, yearMonth, closureId, closureDate);
		}

		@Override
		public void addMonthlyClosureUpdateErrorInfor(MonthlyClosureUpdateErrorInfor domain) {
			monthlyClosureUpdateErrorInforRepo.add(domain);
		}

		@Override
		public List<MonthlyClosureUpdateErrorInfor> monthlyClosureUpdateErrorInfor(String monthlyClosureUpdateLogId,
				String employeeId) {
			String key = monthlyClosureUpdateLogId + "-" + employeeId;
			if(!cache.getMonthlyClosureUpdateErrorInforMap().containsKey(key)){
				cache.getMonthlyClosureUpdateErrorInforMap().put(key,
					monthlyClosureUpdateErrorInforRepo.getByLogIdAndEmpId(monthlyClosureUpdateLogId, employeeId));
			}
			return cache.getMonthlyClosureUpdateErrorInforMap().get(key);
		}

		@Override
		public Optional<MonthlyClosureUpdateLog> monthlyClosureUpdateLog(String id) {
			if(!cache.getMonthlyClosureUpdateLogMap().containsKey(id)){
				cache.getMonthlyClosureUpdateLogMap().put(id, monthlyClosureUpdateLogRepo.getLogById(id));
			}
			return cache.getMonthlyClosureUpdateLogMap().get(id);
		}

		@Override
		public void updateMonthlyClosureUpdateLog(MonthlyClosureUpdateLog domain) {
			monthlyClosureUpdateLogRepo.updateStatus(domain);
		}

		@Override
		public List<MonthlyClosureUpdatePersonLog> monthlyClosureUpdatePersonLog(String monthlyClosureUpdateLogId) {
			if(!cache.getMonthlyClosureUpdatePersonLogMap().containsKey(monthlyClosureUpdateLogId)){
				cache.getMonthlyClosureUpdatePersonLogMap().put(monthlyClosureUpdateLogId,
					monthlyClosureUpdatePersonLogRepo.getAll(monthlyClosureUpdateLogId));
			}
			return cache.getMonthlyClosureUpdatePersonLogMap().get(monthlyClosureUpdateLogId);
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
			if(!cache.getActualLockMap().containsKey(closureId)){
				cache.getActualLockMap().put(closureId, actualLockRepo.findById(companyId, closureId));
			}
			return cache.getActualLockMap().get(closureId);
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
			if(!cache.getOuenAggregateFrameSetOfMonthlyCache().isPresent()){
				cache.setOuenAggregateFrameSetOfMonthlyCache(ouenAggregateFrameSetOfMonthlyRepo.find(companyId));
			}
			return cache.getOuenAggregateFrameSetOfMonthlyCache();
		}

		@Override
		public List<OuenWorkTimeOfDailyAttendance> ouenWorkTimeOfDailyAttendance(String empId, GeneralDate ymd) {
//			Optional<OuenWorkTimeOfDaily> daily = ouenWorkTimeOfDailyRepo.find(empId, ymd);
//			if(!daily.isPresent()) {
//				return new ArrayList<>();
//			}
//			return daily.get().getOuenTimes();
			return new ArrayList<>();
		}

		@Override
		public List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance(String empId,
				GeneralDate ymd) {
//			OuenWorkTimeSheetOfDaily domain =  ouenWorkTimeSheetOfDailyRepo.find(empId, ymd);
//			if(domain == null)
//				return new ArrayList<>();
//			
//			return domain.getOuenTimeSheet();
			return new ArrayList<>();
		}

		@Override
		public boolean isUseWorkLayer(String companyId) {
			return false;
		}

		@Override
		public Optional<RegularLaborTimeCom> regularLaborTimeByCompany(String companyId) {
			if(!cache.getRegularLaborTimeComCache().isPresent()) {
				cache.setRegularLaborTimeComCache(regularLaborTimeComRepo.find(companyId));
			}
			return cache.getRegularLaborTimeComCache();
		}

		@Override
		public Optional<DeforLaborTimeCom> deforLaborTimeByCompany(String companyId) {
			if(!cache.getDeforLaborTimeComCache().isPresent()) {
				cache.setDeforLaborTimeComCache(deforLaborTimeComRepo.find(companyId));
			}
			return cache.getDeforLaborTimeComCache();
		}

		@Override
		public Optional<RegularLaborTimeWkp> regularLaborTimeByWorkplace(String cid, String wkpId) {
			if(!cache.getRegularLaborTimeWkpMap().containsKey(wkpId)) {
				cache.getRegularLaborTimeWkpMap().put(wkpId, regularLaborTimeWkpRepo.find(cid, wkpId));
			}
			return cache.getRegularLaborTimeWkpMap().get(wkpId);
		}

		@Override
		public Optional<DeforLaborTimeWkp> deforLaborTimeByWorkplace(String cid, String wkpId) {
			if(!cache.getDeforLaborTimeWkpMap().containsKey(wkpId)) {
				cache.getDeforLaborTimeWkpMap().put(wkpId, deforLaborTimeWkpRepo.find(cid, wkpId));
			}
			
			return cache.getDeforLaborTimeWkpMap().get(wkpId);
		}



		@Override
		public Optional<RegularLaborTimeEmp> regularLaborTimeByEmployment(String cid, String employmentCode) {
			if(!cache.getRegularLaborTimeEmpMap().containsKey(employmentCode)) {
				cache.getRegularLaborTimeEmpMap().put(employmentCode, regularLaborTimeEmpRepo.findById(cid, employmentCode));
			}
			return cache.getRegularLaborTimeEmpMap().get(employmentCode);
		}

		@Override
		public Optional<DeforLaborTimeEmp> deforLaborTimeByEmployment(String cid, String employmentCode) {
			if(!cache.getDeforLaborTimeEmpMap().containsKey(employmentCode)) {
				cache.getDeforLaborTimeEmpMap().put(employmentCode, deforLaborTimeEmpRepo.find(cid, employmentCode));
			}
			return cache.getDeforLaborTimeEmpMap().get(employmentCode);
		}

		@Override
		public Optional<RegularLaborTimeSha> regularLaborTimeByEmployee(String Cid, String EmpId) {
			if(!cache.getRegularLaborTimeShaMap().containsKey(EmpId)){
				cache.getRegularLaborTimeShaMap().put(EmpId, regularLaborTimeShaRepo.find(Cid, EmpId));
			}
			return cache.getRegularLaborTimeShaMap().get(EmpId);
		}

		@Override
		public Optional<DeforLaborTimeSha> deforLaborTimeByEmployee(String cid, String empId) {
			if(!cache.getDeforLaborTimeShaMap().containsKey(empId)){
				cache.getDeforLaborTimeShaMap().put(empId, deforLaborTimeShaRepo.find(cid, empId));
			}
			return cache.getDeforLaborTimeShaMap().get(empId);
		}

		@Override
		public Optional<ShaFlexMonthActCalSet> monthFlexCalcSetbyEmployee(
				String cid, String sId) {
			if(!cache.getShaFlexMonthActCalSetMap().containsKey(sId)){
				cache.getShaFlexMonthActCalSetMap().put(sId, shaFlexMonthActCalSetRepo.find(cid, sId));
			}
			return cache.getShaFlexMonthActCalSetMap().get(sId);
		}

		@Override
		public Optional<ShaDeforLaborMonthActCalSet> monthDeforLaborCalcSetByEmployee(
				String cId, String sId) {
			if(!cache.getShaDeforLaborMonthActCalSetMap().containsKey(sId)){
				cache.getShaDeforLaborMonthActCalSetMap().put(sId, shaDeforLaborMonthActCalSetRepo.find(cId, sId));
			}
			return cache.getShaDeforLaborMonthActCalSetMap().get(sId);
		}

		@Override
		public Optional<ShaRegulaMonthActCalSet> monthRegulaCalcSetByEmployee(
				String cid, String sId) {
			if(!cache.getShaRegulaMonthActCalSetMap().containsKey(sId)){
				cache.getShaRegulaMonthActCalSetMap().put(sId, shaRegulaMonthActCalSetRepo.find(cid, sId));
			}
			return cache.getShaRegulaMonthActCalSetMap().get(sId);
		}

		@Override
		public Optional<ComRegulaMonthActCalSet> monthRegulaCalSetByCompany(
				String companyId) {
			if(!cache.getComRegulaMonthActCalSetCache().isPresent()){
				cache.setComRegulaMonthActCalSetCache(comRegulaMonthActCalSetRepo.find(companyId));
			}
			return cache.getComRegulaMonthActCalSetCache();
		}

		@Override
		public Optional<ComDeforLaborMonthActCalSet> monthDeforLaborCalSetByCompany(
				String companyId) {
			if(!cache.getComDeforLaborMonthActCalSetCache().isPresent()){
				cache.setComDeforLaborMonthActCalSetCache(comDeforLaborMonthActCalSetRepo.find(companyId));
			}
			return cache.getComDeforLaborMonthActCalSetCache();
		}

		@Override
		public Optional<ComFlexMonthActCalSet> monthFlexCalSetByCompany(
				String companyId) {
			if(!cache.getComFlexMonthActCalSetCache().isPresent()){
				cache.setComFlexMonthActCalSetCache(comFlexMonthActCalSetRepo.find(companyId));
			}
			return cache.getComFlexMonthActCalSetCache();
		}

		@Override
		public Optional<WkpRegulaMonthActCalSet> monthRegularCalcSetByWorkplace(
				String cid, String wkpId) {
			if(!cache.getWkpRegulaMonthActCalSetMap().containsKey(wkpId)) {
				cache.getWkpRegulaMonthActCalSetMap().put(wkpId, wkpRegulaMonthActCalSetRepo.find(cid, wkpId));
			}
			return cache.getWkpRegulaMonthActCalSetMap().get(wkpId);
		}

		@Override
		public Optional<EmpRegulaMonthActCalSet> monthRegularCalcSetByEmployment(
				String cid, String empCode) {
			if(!cache.getEmpRegulaMonthActCalSetMap().containsKey(empCode)) {
				cache.getEmpRegulaMonthActCalSetMap().put(empCode, empRegulaMonthActCalSetRepo.find(cid, empCode));
			}
			return cache.getEmpRegulaMonthActCalSetMap().get(empCode);
		}

		@Override
		public Optional<WkpDeforLaborMonthActCalSet> monthDeforCalcSetByWorkplace(
				String cid, String wkpId) {
			if(!cache.getWkpDeforLaborMonthActCalSetMap().containsKey(wkpId)) {
				cache.getWkpDeforLaborMonthActCalSetMap().put(wkpId, wkpDeforLaborMonthActCalSetRepo.find(cid, wkpId));
			}
			return cache.getWkpDeforLaborMonthActCalSetMap().get(wkpId);
		}

		@Override
		public Optional<EmpDeforLaborMonthActCalSet> monthDeforCalcSetByEmployment(
				String cid, String empCode) {
			if(!cache.getEmpDeforLaborMonthActCalSetMap().containsKey(empCode)) {
				cache.getEmpDeforLaborMonthActCalSetMap().put(empCode, empDeforLaborMonthActCalSetRepo.find(cid, empCode));
			}
			return cache.getEmpDeforLaborMonthActCalSetMap().get(empCode);
		}

		@Override
		public Optional<WkpFlexMonthActCalSet> monthFlexCalcSetByWorkplace(
				String cid, String wkpId) {
			if(!cache.getWkpFlexMonthActCalSetMap().containsKey(wkpId)) {
				cache.getWkpFlexMonthActCalSetMap().put(wkpId, wkpFlexMonthActCalSetRepo.find(cid, wkpId));
			}
			return cache.getWkpFlexMonthActCalSetMap().get(wkpId);
		}

		@Override
		public Optional<EmpFlexMonthActCalSet> monthFlexCalcSetByEmployment(
				String cid, String empCode) {
			if(!cache.getEmpFlexMonthActCalSetMap().containsKey(empCode)) {
				cache.getEmpFlexMonthActCalSetMap().put(empCode, empFlexMonthActCalSetRepo.find(cid, empCode));
			}
			return cache.getEmpFlexMonthActCalSetMap().get(empCode);
		}

		@Override
		public Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			String key = workplaceId + "-" + laborAttr.value + "-" + ym.v();
			if(!cache.getMonthlyWorkTimeSetWkpMap().containsKey(key)) {
				cache.getMonthlyWorkTimeSetWkpMap().put(key, monthlyWorkTimeSetRepo.findWorkplace(cid, workplaceId, laborAttr, ym));
			}
			return cache.getMonthlyWorkTimeSetWkpMap().get(key);
		}

		@Override
		public Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			String key = sid + "-" + laborAttr.value + "-" + ym.v();
			if(!cache.getMonthlyWorkTimeSetShaMap().containsKey(key)){
				cache.getMonthlyWorkTimeSetShaMap().put(key, monthlyWorkTimeSetRepo.findEmployee(cid, sid, laborAttr, ym));
			}

			return cache.getMonthlyWorkTimeSetShaMap().get(key);
		}

		@Override
		public Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmp(String cid, String empCode,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			String key = empCode + "-" + laborAttr.value + "-" + ym.v();
			if(!cache.getMonthlyWorkTimeSetEmpMap().containsKey(key)) {
				cache.getMonthlyWorkTimeSetEmpMap().put(key, monthlyWorkTimeSetRepo.findEmployment(cid, empCode, laborAttr, ym));
			}
			return cache.getMonthlyWorkTimeSetEmpMap().get(key);
		}

		@Override
		public Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetCom(String cid, LaborWorkTypeAttr laborAttr,
				YearMonth ym) {
			String key = laborAttr.value + "-" + ym.v().toString();
			if(!cache.getMonthlyWorkTimeSetComMap().containsKey(key)) {
				cache.getMonthlyWorkTimeSetComMap().put(key, monthlyWorkTimeSetRepo.findCompany(cid, laborAttr, ym));
			}
			return cache.getMonthlyWorkTimeSetComMap().get(key);
		}

		@Override
		public BasicAgreementSetting basicAgreementSetting(String companyId, String employeeId, GeneralDate criteriaDate) {
			return AgreementDomainService.getBasicSet(this, companyId, employeeId, criteriaDate);
		}


		public Optional<AggregateMethodOfMonthly> aggregateMethodOfMonthly(String cid) {
			if(!cache.getAggregateMethodOfMonthlyCache().isPresent()){
				cache.setAggregateMethodOfMonthlyCache(verticalTotalMethodOfMonthlyRepo.findByCid(cid));
			}
			return cache.getAggregateMethodOfMonthlyCache();
		}

		@Override
		public BasicAgreementSettingForCalc basicAgreementSetting(String cid, String sid, GeneralDate baseDate, Year year) {

			return AgreementDomainService.getBasicSet(this, cid, sid, baseDate, year);
		}

		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String cid, GeneralDate ymd, String sid) {

			return workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(cid, ymd, sid);
		}
		
		@Override
		public List<WorkingConditionItem> workingConditionItem(String cid, GeneralDate ymd, List<String> sid) {
			return workingConditionRepo.getWorkingConditionItemByLstEmpIDAndDate(cid, ymd, sid);
		}

		@Override
		public BasicAgreementSettingForCalc basicAgreementSetting(String cid, String sid, YearMonth ym, GeneralDate baseDate) {

			return AgreementDomainService.getBasicSet(this, cid, sid, baseDate, ym);
		}
		
		@Override
		public Map<String, BasicAgreementSettingForCalc> basicAgreementSettingClones(String cid, List<String> sid, YearMonth ym, GeneralDate baseDate) {

			return AgreementDomainService.getBasicSetClones(this, cid, sid, baseDate, ym);
		}

		@Override
		public List<IntegrationOfDaily> integrationOfDaily(String sid, DatePeriod period) {

			List<IntegrationOfDaily> dataForResult = new ArrayList<>();
			List<IntegrationOfDaily> dataByRepo =  new ArrayList<>();
			
			for(GeneralDate date : period.datesBetween()){
				String keyForGet = sid + "-" + date.toString();	
				if(cache.getIntegrationOfDailyMap().containsKey(keyForGet)){
					dataForResult.add(cache.getIntegrationOfDailyMap().get(keyForGet));
				}
				else{
					dataByRepo =integrationOfDailyGetter.getIntegrationOfDaily(sid,  new DatePeriod(date, period.end()));
					
					dataByRepo.forEach((v) ->{
						String keyForPut = sid + "-" + v.getYmd().toString();
						if(!cache.getIntegrationOfDailyMap().containsKey(keyForPut)){
							cache.getIntegrationOfDailyMap().put(keyForPut, v);
						}
						dataForResult.add(v);
					});
					
					break;
				}
			};

			return dataForResult;
		}
		
		@Override
		public List<IntegrationOfDaily> integrationOfDailyClones(List<String> sids, DatePeriod period) {

			List<IntegrationOfDaily> dataForResult = new ArrayList<>();
			List<IntegrationOfDaily> dataByRepo =  new ArrayList<>();
			
			for(String sid : sids){
				for(GeneralDate date : period.datesBetween()){
					String keyForGet = sid + "-" + date.toString();	
					if(cache.getIntegrationOfDailySIDListMap().containsKey(keyForGet)){
						dataForResult.add(cache.getIntegrationOfDailySIDListMap().get(keyForGet));
					}
					else{
						dataByRepo =integrationOfDailyGetter.getIntegrationOfDailyClones(Arrays.asList(sid),  new DatePeriod(date, period.end()));
						
						dataByRepo.forEach((v) ->{
							String keyForPut = sid + "-" + v.getYmd().toString();
							if(!cache.getIntegrationOfDailySIDListMap().containsKey(keyForPut)){
								cache.getIntegrationOfDailySIDListMap().put(keyForPut, v);
							}
							dataForResult.add(v);
						});
						
						break;
					}
				};
			}
			return dataForResult;
		}

		@Override
		public MonAggrCompanySettings monAggrCompanySettings(String cid) {
			if(cache.getMonAggrCompanySettingsCache() == null){
				cache.setMonAggrCompanySettingsCache(MonAggrCompanySettings.loadSettings(this, cid));
			}
			return cache.getMonAggrCompanySettingsCache();
		}

		@Override
		public MonAggrEmployeeSettings monAggrEmployeeSettings(CacheCarrier cacheCarrier, String companyId,
				String employeeId, DatePeriod period) {

			return MonAggrEmployeeSettings.loadSettings(this, cacheCarrier, companyId, employeeId, period);
		}
		
		@Override
		public List<MonAggrEmployeeSettings> monAggrEmployeeSettingsClones(CacheCarrier cacheCarrier, String companyId,
				List<String> employeeId, DatePeriod period) {

			return MonAggrEmployeeSettings.loadSettingsClones(this, cacheCarrier, companyId, employeeId, period);
		}

		@Override
		public Optional<WeekRuleManagement> weekRuleManagement(String cid) {
			if(!cache.getWeekRuleManagementCache().isPresent()) {
				cache.setWeekRuleManagementCache(weekRuleManagementRepo.find(cid));
			}
			return cache.getWeekRuleManagementCache();
		}

		@Override
		public ReservationOfMonthly reservation(String sid, GeneralDate date, String companyID) {

			return VerticalTotalAggregateService.aggregate(this, sid, date, companyID);
		}

		@Override
		public List<StampCard> stampCard(String empId) {
			if(!cache.getStampCardMap().containsKey(empId)){
				cache.getStampCardMap().put(empId, stampCardRepo.getListStampCard(empId));
			}
			return cache.getStampCardMap().get(empId);
		}

		@Override
		public List<BentoReservation> bentoReservation(List<ReservationRegisterInfo> inforLst, GeneralDate date,
				boolean ordered, String companyID) {

			return bentoReservationRepo.findByOrderedPeriodEmpLst(inforLst, new DatePeriod(date, date), ordered, companyID);
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
		public Map<GeneralDate, SnapShot> snapshot(String employeeId, DatePeriod datePeriod) {

			return snapshotAdapter.find(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getSnapshot().toDomain()));
		}

		@Override
		public Optional<SuperHD60HConMed> superHD60HConMed(String cid) {
			if(!cache.getSuperHD60HConMedCache().isPresent()){
				cache.setSuperHD60HConMedCache(superHD60HConMedRepo.findById(cid));
			}
			return cache.getSuperHD60HConMedCache();
		}

		@Override
		public List<DailyInterimRemainMngData> createDailyInterimRemainMngs(CacheCarrier cacheCarrier,
				String companyId, String employeeId, DatePeriod period, MonAggrCompanySettings comSetting,
				MonthlyCalculatingDailys dailys) {

			return monthlyAggregationRemainingNumber.createDailyInterimRemainMngs(cacheCarrier,
					companyId, employeeId, period, comSetting, dailys);
		}

		@Override
		public AggregateMonthlyRecordValue aggregation(CacheCarrier cacheCarrier, DatePeriod period,
				String companyId, String employeeId, YearMonth yearMonth, ClosureId closureId,   ClosureDate closureDate,
				MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
				MonthlyCalculatingDailys monthlyCalculatingDailys,
				InterimRemainMngMode interimRemainMngMode, boolean isCalcAttendanceRate) {

			return monthlyAggregationRemainingNumber.aggregation(cacheCarrier, period, companyId, employeeId, yearMonth,
					closureId, closureDate, companySets, employeeSets, monthlyCalculatingDailys, interimRemainMngMode,
					isCalcAttendanceRate);
		}


		@Override
		public Optional<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String specialId) {

			return this.specialLeaveGrantRepo.getBySpecialId(specialId);
		}

		@Override
		public List<InterimSpecialHolidayMng> interimSpecialHolidayMng(String mngId, DatePeriod datePeriod) {
			return interimSpecialHolidayMngRepo.findSpecialHolidayBySidAndPeriod(mngId, datePeriod);
		}

		@Override
		public List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String sid, int specialLeaveCode,
				LeaveExpirationStatus expirationStatus, GeneralDate grantDate, GeneralDate deadlineDate) {

			return specialLeaveGrantRepo.getByPeriodStatus(
					sid, specialLeaveCode, expirationStatus, grantDate, deadlineDate);
		}

		@Override
		public List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String sid, int speCode,
				DatePeriod datePeriod, LeaveExpirationStatus expirationStatus) {

			return specialLeaveGrantRepo.getByPeriodStatus(
					sid, speCode, expirationStatus, datePeriod.end(), datePeriod.start());
		}

		@Override
		public Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfo(String sid, int spLeaveCD,
				UseAtr use) {
			String key = sid + "-" + spLeaveCD + "-" + use.value;
			if(!cache.getSpecialLeaveBasicInfoMap().containsKey(key)){
				cache.getSpecialLeaveBasicInfoMap().put(key,
						specialLeaveBasicInfoRepo.getBySidLeaveCdUser(sid, spLeaveCD, use));
			}			
			return cache.getSpecialLeaveBasicInfoMap().get(key);
		}

		@Override
		public Optional<SpecialHoliday> specialHoliday(String companyID, int specialHolidayCD) {
			if(!cache.getSpecialHolidayMap().containsKey(specialHolidayCD)){
				cache.getSpecialHolidayMap().put(specialHolidayCD, this.specialHolidayRepo.findBySingleCD(companyID, specialHolidayCD));
			}
			return cache.getSpecialHolidayMap().get(specialHolidayCD);
		}


		@Override
		public Optional<ElapseYear> elapseYear(String companyId, int specialHolidayCode) {
			if(!cache.getElapseYearMap().containsKey(specialHolidayCode)){
			cache.getElapseYearMap().put(specialHolidayCode, this.elapseYearRepository
					.findByCode(new CompanyId(companyId), new SpecialHolidayCode(specialHolidayCode)));
			}
			return cache.getElapseYearMap().get(specialHolidayCode);
		}

		@Override
		public List<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode) {
			if(!cache.getGrantDateTblMap().containsKey(specialHolidayCode)){
				cache.getGrantDateTblMap().put(specialHolidayCode, this.grantDateTblRepo.findBySphdCd(companyId, specialHolidayCode));
			}
			return cache.getGrantDateTblMap().get(specialHolidayCode);
		}

		@Override
		public Optional<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode, String grantDateCode) {
			String key = companyId + "-" + specialHolidayCode + "-" + grantDateCode;
			if(!cache.getGrantDateTblOptMap().containsKey(key)){
				cache.getGrantDateTblOptMap().put(key, this.grantDateTblRepo.findByCode(companyId, specialHolidayCode, grantDateCode));
			}
			return cache.getGrantDateTblOptMap().get(key);
		}

		@Override
		public EmployeeRecordImport employeeFullInfo(CacheCarrier cacheCarrier, String empId) {

			return this.empEmployeeAdapter.findByAllInforEmpId(cacheCarrier, empId);
		}

		@Override
		public EmployeeImport employeeInfo(CacheCarrier cacheCarrier, String empId) {

			return this.empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
		}

		@Override
		public List<SClsHistImport> employeeClassificationHistoires(CacheCarrier cacheCarrier, String companyId,
				List<String> employeeIds, DatePeriod datePeriod) {

			return this.empEmployeeAdapter.lstClassByEmployeeId(cacheCarrier, companyId, employeeIds, datePeriod);
		}

		@Override
		public Optional<AnnualLeaveMaxHistoryData> AnnualLeaveMaxHistoryData(
				String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {

			return this.annualLeaveMaxHistRepo.find(employeeId, yearMonth, closureId, closureDate);
		}

		public List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId) {
			if(!cache.getAnnualLeaveGrantRemainingDataMap().containsKey(employeeId)){
				cache.getAnnualLeaveGrantRemainingDataMap().put(employeeId, annLeaGrantRemDataRepo.find(employeeId));
			}
			return cache.getAnnualLeaveGrantRemainingDataMap().get(employeeId);
		}

		@Override
		public void addPayoutSubofHDManagement(PayoutSubofHDManagement domain) {
			payoutSubofHDManaRepo.updateOrInsert(domain);
		}

		@Override
		public void addLeaveComDayOffManagement(LeaveComDayOffManagement domain) {
			leaveComDayOffManaRepo.updateOrInsert(domain);
		}

		@Override
		public void deleteSpecialLeaveGrantRemainAfter(String sid, int specialCode, GeneralDate targetDate) {
			specialLeaveGrantRepo.deleteAfter(sid, specialCode, targetDate);
		}

		@Override
		public List<WorkingConditionItemWithPeriod> workingCondition(String employeeId, DatePeriod datePeriod) {
			return this.workingConditionRepo.getWorkingConditionItemWithPeriod(AppContexts.user().companyId(), Arrays.asList(employeeId), datePeriod)
					.stream().filter(c -> c.getWorkingConditionItem().getEmployeeId().equals(employeeId))
					.collect(Collectors.toList());
		}

		@Override
		public List<ClosureStatusManagement> getAllByEmpId(String employeeId) {
			if(!cache.getClosureStatusManagementMap().containsKey(employeeId)){
				cache.getClosureStatusManagementMap().put(employeeId, closureStatusManagementRepo.getAllByEmpId(employeeId));
			}
			
			return cache.getClosureStatusManagementMap().get(employeeId);
		}

		@Override
		public Optional<ClosureEmployment> findByEmploymentCD(String employmentCode) {
			String companyId = AppContexts.user().companyId();
			if(!cache.getClosureEmploymentMap().containsKey(employmentCode)){
				cache.getClosureEmploymentMap().put(employmentCode, closureEmploymentRepo.findByEmploymentCD(companyId, employmentCode));
			}
			return cache.getClosureEmploymentMap().get(employmentCode);
		}

		@Override
		public Optional<ActualLock> findById(int closureId) {
			String companyId = AppContexts.user().companyId();
			return this.actualLock(companyId, closureId);
		}

		@Override
		public DatePeriod getClosurePeriod(int closureId, YearMonth processYm) {
			DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(
					ClosureService.createRequireM1(closureRepo,closureEmploymentRepo), closureId, processYm);
			return datePeriodClosure;
		}

		@Override
		public Optional<ExecutionLog> getByExecutionContent(String empCalAndSumExecLogID, int executionContent) {
			String key = empCalAndSumExecLogID + "-" + executionContent;
			if(!cache.getExecutionLogMap().containsKey(key)){
				cache.getExecutionLogMap().put(key, executionLogRepo.getByExecutionContent(empCalAndSumExecLogID, executionContent));
			}
			return cache.getExecutionLogMap().get(key);
		}

		@Override
		public Optional<Closure> findClosureById(int closureId) {
			String companyId = AppContexts.user().companyId();
			if(!cache.getClosureMap().containsKey(closureId)){
				cache.getClosureMap().put(closureId, closureRepo.findById(companyId, closureId));
			}
			return cache.getClosureMap().get(closureId);
		}

		@Override
		public Optional<HolidayAddtionSet> holidayAddtionSet(String cid) {
			if(!cache.getHolidayAddtionSetCache().isPresent()){
				cache.setHolidayAddtionSetCache(holidayAddtionRepo.findByCId(cid));
			}
			return cache.getHolidayAddtionSetCache();
		}

//		@Override
//		public Optional<HolidayAddtionSet> holidayAddtionSet(String cid) {
//			return holidayAddtionRepo.findByCId(cid);
//		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String cid, String workTimeCode) {
			if(!cache.getWorkTimeSettingMap().containsKey(workTimeCode)){
				cache.getWorkTimeSettingMap().put(workTimeCode, this.workTimeSetting(cid, workTimeCode));
			}
			return cache.getWorkTimeSettingMap().get(workTimeCode);
		}

		@Override
		public CompensatoryLeaveComSetting findCompensatoryLeaveComSet(String companyId) {
			if(cache.getCompensatoryLeaveComSettingCache() == null){
				cache.setCompensatoryLeaveComSettingCache(this.compensatoryLeaveComSetting(companyId));
			}
			return cache.getCompensatoryLeaveComSettingCache();
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			return this.fixedWorkSetting(AppContexts.user().companyId(), code.v()).get();
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			return this.flowWorkSetting(AppContexts.user().companyId(), code.v()).get();
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			return this.flexWorkSetting(AppContexts.user().companyId(), code.v()).get();
		}
		
		@Override
		public Optional<PublicHolidaySetting> publicHolidaySetting(String companyID){
			if(!cache.getPublicHolidaySettingCache().isPresent()){
				cache.setPublicHolidaySettingCache(this.publicHolidaySettingRepo.get(companyID));
			}
			return cache.getPublicHolidaySettingCache();
		}
		
		@Override
		public Optional<PublicHolidayManagementUsageUnit> publicHolidayManagementUsageUnit(String companyID){
			if(!cache.getPublicHolidayManagementUsageUnitCache().isPresent()){
				cache.setPublicHolidayManagementUsageUnitCache(this.publicHolidayManagementUsageUnitRepo.get(companyID));
			}
			return cache.getPublicHolidayManagementUsageUnitCache();
		}
		
		@Override
		public Optional<YearMonthPeriod> getYearMonthPeriodByCalendarYearmonth(String companyID, YearMonth yearMonth){
			return companyAdapter.getYearMonthPeriodByCalendarYearmonth(companyID, yearMonth);
		}
		
		
		public Optional<SharedAffWorkPlaceHisImport> getAffWorkPlaceHis(String employeeId, GeneralDate processingDate){
			return sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, processingDate);
		}
		
		public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId){
			return sharedAffWorkPlaceHisAdapter.getWorkplaceIdAndUpper(companyId, baseDate, workplaceId);
		}
		
		public List<SharedSidPeriodDateEmploymentImport> getEmpHistBySidAndPeriod(List<String> employeeID, DatePeriod Period){
			return shareEmploymentAdapter.getEmpHistBySidAndPeriod(employeeID, Period);
		}
		
		public List<TempPublicHolidayManagement> tempPublicHolidayManagement(String employeeId, DatePeriod Period){
			return this.tempPublicHolidayManagementRepo.findByPeriodOrderByYmd(employeeId, Period);
		}
		
		public Optional<PublicHolidayCarryForwardData> publicHolidayCarryForwardData(String employeeId){
			if(!cache.getPublicHolidayCarryForwardDataMap().containsKey(employeeId)){
				cache.getPublicHolidayCarryForwardDataMap().put(employeeId, this.publicHolidayCarryForwardDataRepo.get(employeeId));
			}
			return cache.getPublicHolidayCarryForwardDataMap().get(employeeId);
		}
		
		public List<EmploymentMonthDaySetting> getEmploymentMonthDaySetting(String companyID, 
				String employmentCode,List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList){
			return this.employmentMonthDaySettingRepo.findByYears(new CompanyId(companyID), employmentCode, yearList);
		}
		
		public List<WorkplaceMonthDaySetting>  getWorkplaceMonthDaySetting(String companyID,
				String workplaceId,List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList){
			return  this.workplaceMonthDaySettingRepo.findByYears(new CompanyId(companyID), workplaceId, yearList);
		}
		
		public List<CompanyMonthDaySetting>  getCompanyMonthDaySetting(	String companyID ,
				List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList){
			return this.companyMonthDaySettingRepo.findByYears(new CompanyId(companyID), yearList);
		}
		
		public List<EmployeeMonthDaySetting>  getEmployeeMonthDaySetting(
				String companyID, String employeeId,List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList){
			return this.employeeMonthDaySettingRepo.findByYears(new CompanyId(companyID), employeeId, yearList);
		}
		
		public void deleteTempPublicHolidayByPeriod(String employeeId, DatePeriod period){
			this.tempPublicHolidayManagementRepo.deleteByPeriod(employeeId, period);
		}
		
		public void persistAndUpdate(PublicHolidayCarryForwardData carryForwardData){
			this.publicHolidayCarryForwardDataRepo.persistAndUpdate(carryForwardData);
		}
		
		public void deletePublicHolidayCarryForwardData(String employeeId){
			this.publicHolidayCarryForwardDataRepo.delete(employeeId);
		}
		
		public void persistAndUpdateCarryForwardHistory(PublicHolidayCarryForwardHistory hist){
			this.publicHolidayCarryForwardHistoryRepo.persistAndUpdate(hist);
		}
		
		public void persistAndUpdateUseChildCare(String employeeId, ChildCareUsedNumberData domain){
			this.childCareUsedNumberRepo.persistAndUpdate(employeeId, domain);
		}
		
		public void updateChildCareMaxDay(String sid, ChildCareNurseUpperLimit ThisFiscalYear){
			this.childCareLeaveRemInfoRepo.updateMaxDay(sid, ThisFiscalYear);
		}
		
		public void deleteTempAnnualSidPeriod(String sid, DatePeriod period){
			this.tmpAnnualHolidayMngRepo.deleteSidPeriod(sid, period);
		}
		
		public void deleteTempResereSidPeriod(String sid, DatePeriod period){
			this.tmpResereLeaveMngRepo.deleteSidPeriod(sid, period);
		}
		
		public void deleteInterimAbsMngBySidDatePeriod(String sId, DatePeriod period){
			this.interimRecAbasMngRepo.deleteAbsMngWithPeriod(sId, period);
		}
		public void deleteInterimRecMngBySidDatePeriod(String sId, DatePeriod period){
			this.interimRecAbasMngRepo.deleteRecMngWithPeriod(sId, period);
		}
		public void deleteTempChildCareByPeriod(String sid, DatePeriod period){
			this.tempChildCareManagementRepo.deleteByPeriod(sid, period);
		}
		
		
		@Override
		public EmployeeImport findByEmpId(String empId) {
			if(!cache.getEmployeeImportMap().containsKey(empId)){
				cache.getEmployeeImportMap().put(empId, Optional.ofNullable(this.empEmployeeAdapter.findByEmpId(empId)));
			}
			return cache.getEmployeeImportMap().get(empId).orElse(null);
		}
		
		@Override
		public List<FamilyInfo> familyInfo(String employeeId) {
			// 2021/03/22 時点では家族情報は取得できない
			return new ArrayList<>();
		}
		
		@Override
		public List<TempChildCareManagement> tempChildCareManagement(
				String employeeId, DatePeriod ymd) {
				return this.tempChildCareManagementRepo.findByPeriodOrderByYmd(employeeId, ymd);
		}

		@Override
		public List<TempCareManagement> tempCareManagement(
				String employeeId, DatePeriod ymd) {
				return this.tempCareManagementRepo.findByPeriodOrderByYmd(employeeId, ymd);
		}
		
		
		@Override
		public NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory) {
			if(!cache.getNursingLeaveSettingMap().containsKey(nursingCategory)){
				cache.getNursingLeaveSettingMap().put(nursingCategory,
					Optional.ofNullable(this.nursingLeaveSettingRepo.findByCompanyIdAndNursingCategory(companyId, nursingCategory.value)));
			}
			return cache.getNursingLeaveSettingMap().get(nursingCategory).orElse(null);
		}
		

		@Override
		public Optional<ChildCareUsedNumberData> childCareUsedNumber(String employeeId) {
			if(!cache.getChildCareUsedNumberDataMap().containsKey(employeeId)){
				cache.getChildCareUsedNumberDataMap().put(employeeId, this.childCareUsedNumberRepo.find(employeeId));
			}
			return cache.getChildCareUsedNumberDataMap().get(employeeId);
		}
		
		@Override
		public Optional<CareUsedNumberData> careUsedNumber(String employeeId) {
			if(!cache.getCareUsedNumberDataMap().containsKey(employeeId)){
				cache.getCareUsedNumberDataMap().put(employeeId, this.careUsedNumberRepo.find(employeeId));
			}
			return cache.getCareUsedNumberDataMap().get(employeeId);
		}
		
		@Override
		public Optional<CareManagementDate> careData(String familyID) {
			// 2021/03/22 時点では家族情報は取得できない
			return Optional.empty();
		}
		
		@Override
		public Optional<ChildCareLeaveRemainingInfo> childCareLeaveEmployeeInfo(String employeeId) {
			if(!cache.getChildCareLeaveRemainingInfoMap().containsKey(employeeId)){
				cache.getChildCareLeaveRemainingInfoMap().put(employeeId, this.childCareLeaveRemInfoRepo.getChildCareByEmpId(employeeId));
			}
			return cache.getChildCareLeaveRemainingInfoMap().get(employeeId);
		}
		
		@Override
		public Optional<CareLeaveRemainingInfo> careLeaveEmployeeInfo(String employeeId) {
			if(!cache.getCareLeaveRemainingInfo().containsKey(employeeId)){
				cache.getCareLeaveRemainingInfo().put(employeeId, this.careLeaveRemainingInfoRepo.getCareByEmpId(employeeId));
			}
			return cache.getCareLeaveRemainingInfo().get(employeeId);
		}
		
		
		@Override
		public Optional<NursingCareLeaveRemainingInfo> employeeInfo(String employeeId, NursingCategory nursingCategory) {
			String key = employeeId + "-" + nursingCategory;
			if(!cache.getNursingCareLeaveRemainingInfoMap().containsKey(key)){
				if(nursingCategory.equals(NursingCategory.Nursing)){
					cache.getNursingCareLeaveRemainingInfoMap().put(key, this.careLeaveRemainingInfoRepo
							.getCareByEmpId(employeeId).map(mapper -> (NursingCareLeaveRemainingInfo) mapper));
				}
				if(nursingCategory.equals(NursingCategory.ChildNursing)){
					cache.getNursingCareLeaveRemainingInfoMap().put(key, this.childCareLeaveRemInfoRepo
							.getChildCareByEmpId(employeeId).map(mapper->(NursingCareLeaveRemainingInfo)mapper));
				}
			}
			return cache.getNursingCareLeaveRemainingInfoMap().getOrDefault(key, Optional.empty());
			
		}
		
		public void persistAndUpdateUseCare(String employeeId, CareUsedNumberData domain){
			this.careUsedNumberRepo.persistAndUpdate(employeeId, domain);
		}
		
		public void updateCareMaxDay(String sid, ChildCareNurseUpperLimit ThisFiscalYear){
			this.careLeaveRemainingInfoRepo.updateMaxDay(sid, ThisFiscalYear);
		}
		
		public void deleteTempCareByPeriod(String sid, DatePeriod period){
			this.tempCareManagementRepo.deleteBySidDatePeriod(sid, period);	
		}
		
		public	void deleteInterimDayOffMngBySidDatePeriod(String sid, DatePeriod period){
			this.interimBreakDayOffMngRepo.deleteDayoffWithPeriod(sid, period);
		}
		
		public void deleteInterimBreakMngBySidDatePeriod(String sid, DatePeriod period){
			this.interimBreakDayOffMngRepo.deleteBreakoffWithPeriod(sid, period);
		}
		
		public void deleteTempSpecialSidPeriod(String sid, int specialCode, DatePeriod period){
			this.interimSpecialHolidayMngRepo.deleteBySidAndPeriod(sid, specialCode, period);
		}
		
		@Override
		public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
			if(cache.getAnnualPaidLeaveSettingCache() == null){
				cache.setAnnualPaidLeaveSettingCache(annualPaidLeaveSettingRepo.findByCompanyId(companyId));
			}
			return cache.getAnnualPaidLeaveSettingCache();
		}

		@Override
		public List<DailyInterimRemainMngData> mapInterimRemainData(RequireM1 require, CacheCarrier cacheCarrier,
				String cid, String sid, DatePeriod datePeriod) {
			return AggregateMonthlyRecordService.mapInterimRemainData(require, cacheCarrier, cid, sid, datePeriod);
		}

		@Override
		public Optional<GeneralDate> algorithm(GetClosureStartForEmployee.RequireM1 require, CacheCarrier cacheCarrier, String employeeId) {
			return GetClosureStartForEmployeeProc.algorithm(require, cacheCarrier, employeeId);
		}

		@Override	
		public Optional<GrantPeriodDto> getPeriodYMDGrant(String cid, String sid, GeneralDate ymd, Integer periodOutput,
				Optional<DatePeriod> fromTo) {
			return getPeriodFromPreviousToNextGrantDate.getPeriodYMDGrant(cid, sid, ymd, periodOutput, fromTo);
		}

		@Override
		public List<AnnLeaRemNumEachMonth> findBySidsAndYearMonths(List<String> employeeIds,
				List<YearMonth> yearMonths) {
			return annLeaRemNumEachMonthRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		}

		@Override
		public DatePeriod findClosurePeriod(RequireM3 require, CacheCarrier cacheCarrier, String employeeId,
				GeneralDate criteriaDate) {
			return ClosureService.findClosurePeriod(require, cacheCarrier, employeeId, criteriaDate);
		}

		@Override
		public List<YearMonth> GetYearMonthClosurePeriod(RequireM3 require, CacheCarrier cacheCarrier,
				String employeeId, GeneralDate criteriaDate, DatePeriod period) {
			return ClosureService.GetYearMonthClosurePeriod(require, cacheCarrier, 
					employeeId, criteriaDate, period);
		}

		@Override
		public void transaction(AtomTask task) {
			this.transaction.execute(task);
		}

		public List<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String cId, List<String> employeeId) {
			return annLeaEmpBasicInfoRepo.getAll(cId, employeeId);
		}

		@Override
		public List<ShaFlexMonthActCalSet> monthFlexCalcSetbyEmployee(String cid, List<String> sId) {
			return shaFlexMonthActCalSetRepo.findAllShaByCid(cid).stream().filter(c -> sId.contains(c.getEmpId())).collect(Collectors.toList());
		}

		@Override
		public List<ShaDeforLaborMonthActCalSet> monthDeforLaborCalcSetByEmployee(String cId, List<String> sId) {
			return shaDeforLaborMonthActCalSetRepo.findByCid(cId).stream().filter(c -> sId.contains(c.getEmployeeId())).collect(Collectors.toList());
		}

		@Override
		public List<ShaRegulaMonthActCalSet> monthRegulaCalcSetByEmployee(String cid, List<String> sId) {
			List<ShaRegulaMonthActCalSet> list = shaRegulaMonthActCalSetRepo.findRegulaMonthActCalSetByCid(cid);
			return list.stream().filter(c -> sId.contains(c.getEmployeeId())).collect(Collectors.toList());
		}

		@Override
		public List<DeforLaborTimeSha> deforLaborTimeByEmployee(String cid, List<String> empId) {
			return deforLaborTimeShaRepo.findList(cid, empId);
		}

		@Override
		public List<RegularLaborTimeSha> regularLaborTimeByEmployee(String Cid, List<String> EmpId) {
			return regularLaborTimeShaRepo.findList(Cid, EmpId);
		}

		@Override
		public List<EmployeeImport> employee(List<String> empId) {
			return empEmployeeAdapter.findByEmpId(empId);
		}

		@Override
		public List<WorkingConditionItem> workingConditionItem(List<String> sId, DatePeriod datePeriod) {
			return workingConditionItemRepo.getBySidsAndDatePeriodNew(sId, datePeriod);
		}

		@Override
		public Map<String, Map<GeneralDate, AttendanceTimeOfDailyAttendance>> dailyAttendanceTimesclones(
				List<String> employeeId, DatePeriod datePeriod) {
			Map<String, Map<GeneralDate, AttendanceTimeOfDailyAttendance>> result = new HashMap<String, Map<GeneralDate,AttendanceTimeOfDailyAttendance>>();

			for (String id : employeeId) {
				Map<GeneralDate, AttendanceTimeOfDailyAttendance> dataForResult = new ConcurrentHashMap<GeneralDate, AttendanceTimeOfDailyAttendance>();
				Map<GeneralDate, AttendanceTimeOfDailyAttendance> dataByRepo = new ConcurrentHashMap<GeneralDate, AttendanceTimeOfDailyAttendance>();
				for(GeneralDate date : datePeriod.datesBetween()){
					String keyForGet = employeeId + "-" + date.toString();	
					if(cache.getAttendanceTimeOfDailyAttendanceListMap().containsKey(keyForGet)){
						dataForResult.put(date,cache.getAttendanceTimeOfDailyAttendanceListMap().get(keyForGet));
					}
					else{
						dataByRepo =attendanceTimeRepo.finds(Arrays.asList(id), new DatePeriod(date,datePeriod.end()))
								.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTime()));
						
						dataByRepo.forEach((k, v) ->{
							String keyForPut = employeeId + "-" + k.toString();
							if(!cache.getAttendanceTimeOfDailyAttendanceListMap().containsKey(keyForPut)){
								cache.getAttendanceTimeOfDailyAttendanceListMap().put(keyForPut, v);
							}
							dataForResult.put(k, v);
						});
						
						break;
					}
				};
				result.put(id,dataForResult);
			}

			return result;
		}

		@Override
		public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
			return closureEmploymentRepo.findListEmployment(companyID, employmentCD);
		}

		@Override
		public List<Closure> closureClones(String companyId, List<Integer> closureId) {
			return closureRepo.findByListId(companyId, closureId);
		}

		@Override
		public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String companyId, List<String> employeeId,
				GeneralDate baseDate) {
			return shareEmploymentAdapter.findEmpHistoryVer2(companyId, employeeId, baseDate);
		}

		@Override
		public List<WorkingConditionItemWithPeriod> getWorkingConditionItemWithPeriod(String companyID,
				List<String> lstEmpID, DatePeriod datePeriod) {
			return workingConditionRepository.getWorkingConditionItemWithPeriod(companyID, lstEmpID, datePeriod);
		}
		
		@Override
		public List<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId) {
			if(!cache.getEmploymentHistoryImportedMap().containsKey(employeeId)){
				cache.getEmploymentHistoryImportedMap().put(employeeId, employmentAdapter.getEmpHistBySid(companyId, employeeId));
			}
			return cache.getEmploymentHistoryImportedMap().get(employeeId);
		}

		@Override
		public Optional<CreatingDailyResultsCondition> creatingDailyResultsCondition(String cid) {
			if(!cache.getCreatingDailyResultsConditionCache().isPresent()){
				cache.setCreatingDailyResultsConditionCache(creatingDailyResultsConditionRepo.findByCid(cid));
			}
			return cache.getCreatingDailyResultsConditionCache();
		}

		@Override
		public List<InterimDayOffMng> getDayOffBySidPeriod(String sid, DatePeriod period) {
			return interimBreakDayOffMngRepo.getDayOffBySidPeriod(sid, period);
		}

		@Override
		public List<InterimBreakMng> getBreakBySidPeriod(String sid, DatePeriod period) {
			return interimBreakDayOffMngRepo.getBySidPeriod(sid, period);
		}

		@Override
		public List<TempChildCareManagement> findChildCareByPeriodOrderByYmd(String employeeId, DatePeriod period) {
			return tempChildCareManagementRepo.findByPeriodOrderByYmd(employeeId, period);
		}

		@Override
		public List<TempCareManagement> findCareByPeriodOrderByYmd(String employeeId, DatePeriod period) {
			return tempCareManagementRepo.findByPeriodOrderByYmd(employeeId, period);
		}
		
		@Override
		public List<InterimRecMng> getRecBySidDatePeriod(String sid, DatePeriod period){
			return interimRecAbasMngRepo.getRecBySidDatePeriod(sid, period);
		}
		
		@Override
		public List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period){
			return interimRecAbasMngRepo.getAbsBySidDatePeriod(sid, period);
		}
		
		@Override
		public WorkDaysNumberOnLeaveCount workDaysNumberOnLeaveCount(String cid) {
			if(cache.getWorkDaysNumberOnLeaveCountCache() == null){
				cache.setWorkDaysNumberOnLeaveCountCache(workDaysNumberOnLeaveCountRepo.findByCid(cid));
			}
			return cache.getWorkDaysNumberOnLeaveCountCache();
		}
		
		@Override
		public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode) {
			return this.predetemineTimeSetByWorkTimeCode(companyId, workTimeCode);
		}

		@Override
		public List<IntegrationOfDaily> calculateForRecord(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet,
				ExecutionType reCalcAtr) {
			return calculateDailyRecordServiceCenter.calculatePassCompanySetting(calcOption, integrationOfDaily, companySet, reCalcAtr);
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			if(!cache.getWorkTypeMap().containsKey(workTypeCd)){
				cache.getWorkTypeMap().put(workTypeCd, workTypeRepo.findByPK( AppContexts.user().companyId(), workTypeCd));
			}
			return cache.getWorkTypeMap().get(workTypeCd);
		}

		@Override
		public List<PayoutSubofHDManagement> getOccDigetByListSid(String sid, DatePeriod date) {
			return payoutSubofHDManaRepo.getOccDigetByListSid(sid, date);
		}

		@Override
		public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String sid) {
			return substitutionOfHDManaDataRepo.getBysiD(AppContexts.user().companyId(), sid);
		}

		@Override
		public List<PayoutManagementData> getPayoutMana(String sid) {
			return payoutManagementDataRepo.getSid(AppContexts.user().companyId(), sid);
		}

		@Override
		public List<PayoutSubofHDManagement> getByListDate(String sid, List<GeneralDate> lstDate) {
			return payoutSubofHDManaRepo.getByListDate(sid, lstDate);
		}

		@Override
		public List<PayoutSubofHDManagement> getByListOccDate(String sid, List<GeneralDate> lstDate) {
			return payoutSubofHDManaRepo.getByListOccDate(sid, lstDate);
		}

		@Override
		public List<InterimAbsMng> getAbsBySidDateList(String sid, List<GeneralDate> lstDate) {
			return interimRecAbasMngRepo.getAbsBySidDateList(sid, lstDate);
		}

		@Override
		public List<InterimRecMng> getRecBySidDateList(String sid, List<GeneralDate> lstDate) {
			return interimRecAbasMngRepo.getRecBySidDateList(sid, lstDate);
		}

		@Override
		public List<LeaveComDayOffManagement> getLeavByListDate(String sid, List<GeneralDate> lstDate) {
			return leaveComDayOffManaRepo.getByListDate(sid, lstDate);
		}

		@Override
		public List<LeaveComDayOffManagement> getLeavByListOccDate(String sid, List<GeneralDate> lstDate) {
			return leaveComDayOffManaRepo.getLeavByListOccDate(sid, lstDate);
		}

		@Override
		public List<InterimBreakMng> getBreakBySidDateList(String sid, List<GeneralDate> lstDate) {
			return interimBreakDayOffMngRepo.getBreakBySidDateList(sid, lstDate);
		}

		@Override
		public List<InterimDayOffMng> getDayOffDateList(String sid, List<GeneralDate> lstDate) {
			return interimBreakDayOffMngRepo.getDayOffDateList(sid, lstDate);
		}
	}