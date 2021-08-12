package nts.uk.ctx.at.record.dom.require;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
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
import nts.uk.ctx.at.record.dom.daily.export.AggregateSpecifiedDailys;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSettingService;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetYearAndMultiMonthAgreementTime;
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
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.AppRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
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
import nts.uk.ctx.at.shared.dom.scherec.byperiod.MonthlyCalculationByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UnitAtr;
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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordService;
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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
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
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.DailyStatutoryLaborTime;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CheckCareService;
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
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.CalcNextAnnLeaGrantInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrantProcKdm002;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

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
	private OvertimeWorkFrameRepository roleOvertimeWorkRepo;
	@Inject
	private HolidayAddtionRepository holidayAddtionRepo;
	@Inject
	private MonthlyAggrSetOfFlexRepository monthlyAggrSetOfFlexRepo;
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
	private Workplace36AgreedHoursRepository agreementTimeWorkPlaceRepo;
	@Inject
	private AffClassificationAdapter affClassficationAdapter;
	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;
	@Inject
	private Employment36HoursRepository agreementTimeOfEmploymentRepo;
	@Inject
	private Classification36AgreementTimeRepository agreementTimeOfClassificationRepo;
	@Inject
	private Company36AgreedHoursRepository agreementTimeCompanyRepo;
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
	private IntegrationOfDailyGetter integrationOfDailyGetter;
	@Inject
	private GetProcessingDate getProcessingDate;
	@Inject
	private DailySnapshotWorkAdapter snapshotAdapter;
	@Inject
	private SuperHD60HConMedRepository superHD60HConMedRepo;
	@Inject
	protected ElapseYearRepository elapseYearRepository;
	@Inject
	private SyCompanyRecordAdapter syCompanyRecordAdapter;
	@Inject
	private MonthlyAggregationRemainingNumber monthlyAggregationRemainingNumber;
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepo;
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepo;
	@Inject
	private CheckCareService checkChildCareService;
	@Inject
	private WorkingConditionItemService workingConditionItemService;

	public static interface Require extends RemainNumberTempRequireService.Require, GetAnnAndRsvRemNumWithinPeriod.RequireM2, CalcAnnLeaAttendanceRate.RequireM3,
		GetClosurePeriod.RequireM1, GetClosureStartForEmployee.RequireM1, CalcNextAnnLeaGrantInfo.RequireM1, GetNextAnnualLeaveGrantProcKdm002.RequireM1,
		GetYearAndMultiMonthAgreementTime.RequireM1, InterimRemainOffPeriodCreateData.RequireM2, DailyStatutoryLaborTime.RequireM1, AggregateMonthlyRecordService.RequireM1,
		MonAggrCompanySettings.RequireM6, WorkTimeIsFluidWork.RequireM2, MonAggrEmployeeSettings.RequireM2, MonthlyCalculationByPeriod.RequireM1, GetClosurePeriod.RequireM2,
		VerticalTotalOfMonthly.RequireM1, TotalCountByPeriod.RequireM1, GetAgreementTime.RequireM4, WorkingConditionService.RequireM1, MonthlyAggregationService.RequireM1,
		AgeementTimeCommonSettingService.RequireM1, CreateTempAnnLeaMngProc.RequireM3, AggregateSpecifiedDailys.RequireM1, ClosureService.RequireM6, ClosureService.RequireM5,
		MonthlyUpdateMgr.RequireM4, MonthlyClosureUpdateLogProcess.RequireM3, CancelActualLock.RequireM1, ProcessYearMonthUpdate.RequireM1, BreakDayOffMngInPeriodQuery.RequireM2,
		AgreementDomainService.RequireM5, AgreementDomainService.RequireM6, GetAgreementTime.RequireM5, VerticalTotalAggregateService.RequireM1, GetExcessTimesYear.RequireM2  {

		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);

	}

	public Require createRequire() {
		return new RequireImpl(comSubstVacationRepo, compensLeaveComSetRepo, specialLeaveGrantRepo, empEmployeeAdapter, grantDateTblRepo,
				annLeaEmpBasicInfoRepo, specialHolidayRepo, interimSpecialHolidayMngRepo, specialLeaveBasicInfoRepo, interimRecAbasMngRepo, empSubstVacationRepo,
				substitutionOfHDManaDataRepo, payoutManagementDataRepo, interimBreakDayOffMngRepo, comDayOffManaDataRepo, companyAdapter,
				shareEmploymentAdapter, leaveManaDataRepo, workingConditionItemRepo, workingConditionRepo, workTimeSettingRepo, fixedWorkSettingRepo,
				flowWorkSettingRepo, diffTimeWorkSettingRepo, flexWorkSettingRepo, predetemineTimeSettingRepo, closureRepo, closureEmploymentRepo,
				workTypeRepo, remainCreateInforByApplicationData, compensLeaveEmSetRepo, employmentSettingRepo, retentionYearlySettingRepo, annualPaidLeaveSettingRepo,
				outsideOTSettingRepo, workdayoffFrameRepo, yearHolidayRepo, tmpResereLeaveMngRepo, sysEmploymentHisAdapter, rervLeaGrantRemDataRepo, workInformationRepo,
				annLeaRemNumEachMonthRepo, lengthServiceRepository, grantYearHolidayRepo, tmpAnnualHolidayMngRepo, attendanceTimeOfMonthlyRepo, operationStartSetDailyPerformRepo,
				annualLeaveRemainHistRepo, closureStatusManagementRepo, annLeaMaxDataRepo, annLeaGrantRemDataRepo, employmentHistAdapter, remainCreateInforByScheData,
				remainCreateInforByRecordData, usageUnitSettingRepo, affWorkplaceAdapter, timeLeavingOfDailyPerformanceRepo,
				temporaryTimeOfDailyPerformanceRepo, specificDateAttrOfDailyPerforRepo, employeeDailyPerErrorRepo, anyItemValueOfDailyRepo, pcLogOnInfoOfDailyRepo,
				attendanceTimeRepo, payItemCountOfMonthlyRepo, optionalItemRepo, empConditionRepo, formulaRepo, formulaDispOrderRepo, actualLockRepo,
				legalTransferOrderSetOfAggrMonthlyRepo, roleOvertimeWorkRepo, holidayAddtionRepo, monthlyAggrSetOfFlexRepo, insufficientFlexHolidayMntRepo,
				flexShortageLimitRepo, roundingSetOfMonthlyRepo, totalTimesRepo, agreementOperationSettingRepo, parallel, checkBeforeCalcFlexChangeService,
				anyItemOfMonthlyRepo, empCalAndSumExeLogRepo, editStateOfMonthlyPerRepo, executorService, affiliationInforOfDailyPerforRepo,
				converterFactory, predWorkingDaysAdaptor, updateAllDomainMonthService, agreementUnitSetRepo, agreementTimeWorkPlaceRepo, affClassficationAdapter, syEmploymentAdapter,
				agreementTimeOfEmploymentRepo, agreementTimeOfClassificationRepo, agreementTimeCompanyRepo, remainMergeRepo, agreementYearSettingRepo, agreementMonthSettingRepo,
				agreementTimeOfManagePeriodRepo, targetPersonRepo, errMessageInfoRepo,
				annualLeaveTimeRemainHistRepo, annualLeaveMaxHistRepo, rsvLeaveGrantRemainHistRepo,
				rsvLeaveGrantTimeRemainHistRepo, interimRemainOffMonthProcess, monthlyClosureUpdateErrorInforRepo, monthlyClosureUpdateLogRepo,
				monthlyClosureUpdatePersonLogRepo, ouenWorkTimeSheetOfDailyRepo, ouenWorkTimeOfDailyRepo, ouenAggregateFrameSetOfMonthlyRepo, regularLaborTimeComRepo, deforLaborTimeComRepo,
				regularLaborTimeWkpRepo, deforLaborTimeWkpRepo, regularLaborTimeEmpRepo, deforLaborTimeEmpRepo, regularLaborTimeShaRepo, deforLaborTimeShaRepo, shaFlexMonthActCalSetRepo,
				comFlexMonthActCalSetRepo, empFlexMonthActCalSetRepo, wkpFlexMonthActCalSetRepo, empDeforLaborMonthActCalSetRepo, empRegulaMonthActCalSetRepo, comDeforLaborMonthActCalSetRepo,
				comRegulaMonthActCalSetRepo, shaDeforLaborMonthActCalSetRepo, shaRegulaMonthActCalSetRepo, wkpDeforLaborMonthActCalSetRepo, wkpRegulaMonthActCalSetRepo, monthlyWorkTimeSetRepo,
				verticalTotalMethodOfMonthlyRepo, stampCardRepo, bentoReservationRepo, bentoMenuRepo, integrationOfDailyGetter, weekRuleManagementRepo, sharedAffWorkPlaceHisAdapter, getProcessingDate,
				roleOfOpenPeriodRepo, elapseYearRepository, syCompanyRecordAdapter, snapshotAdapter, superHD60HConMedRepo, monthlyAggregationRemainingNumber,
				payoutSubofHDManaRepo, leaveComDayOffManaRepo , checkChildCareService, workingConditionItemService);
	}

	public  class RequireImpl extends nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RequireImp implements Require {
		private Optional<WeekRuleManagement> weekRuleManagementCache = Optional.empty();

		public RequireImpl(ComSubstVacationRepository comSubstVacationRepo, CompensLeaveComSetRepository compensLeaveComSetRepo, SpecialLeaveGrantRepository specialLeaveGrantRepo,
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
				EmpCalAndSumExeLogRepository empCalAndSumExeLogRepo, EditStateOfMonthlyPerRepository editStateOfMonthlyPerRepo, ManagedExecutorService executorService, AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepo,
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
				GetProcessingDate getProcessingDate, RoleOfOpenPeriodRepository roleOfOpenPeriodRepo, ElapseYearRepository elapseYearRepo,SyCompanyRecordAdapter syCompanyRecordAdapter, DailySnapshotWorkAdapter snapshotAdapter,
				SuperHD60HConMedRepository superHD60HConMedRepo, MonthlyAggregationRemainingNumber monthlyAggregationRemainingNumber, PayoutSubofHDManaRepository payoutSubofHDManaRepo,
				LeaveComDayOffManaRepository leaveComDayOffManaRepo,CheckCareService checkChildCareService,WorkingConditionItemService workingConditionItemService) {

			super(comSubstVacationRepo, compensLeaveComSetRepo, specialLeaveGrantRepo, empEmployeeAdapter, grantDateTblRepo, annLeaEmpBasicInfoRepo, specialHolidayRepo, interimSpecialHolidayMngRepo, specialLeaveBasicInfoRepo,
					interimRecAbasMngRepo, empSubstVacationRepo, substitutionOfHDManaDataRepo, payoutManagementDataRepo, interimBreakDayOffMngRepo, comDayOffManaDataRepo, companyAdapter, shareEmploymentAdapter,
					leaveManaDataRepo, workingConditionItemRepo, workingConditionRepo, workTimeSettingRepo, fixedWorkSettingRepo, flowWorkSettingRepo, diffTimeWorkSettingRepo, flexWorkSettingRepo, predetemineTimeSettingRepo, closureRepo,
					closureEmploymentRepo, workTypeRepo, remainCreateInforByApplicationData, compensLeaveEmSetRepo, employmentSettingRepo, retentionYearlySettingRepo, annualPaidLeaveSettingRepo, outsideOTSettingRepo, workdayoffFrameRepo,
					yearHolidayRepo, usageUnitSettingRepo, regularLaborTimeComRepo, deforLaborTimeComRepo, regularLaborTimeWkpRepo, deforLaborTimeWkpRepo, regularLaborTimeEmpRepo, deforLaborTimeEmpRepo, regularLaborTimeShaRepo,
					deforLaborTimeShaRepo, sharedAffWorkPlaceHisAdapter, lengthServiceRepository, grantYearHolidayRepo, payoutSubofHDManaRepo, leaveComDayOffManaRepo,checkChildCareService,workingConditionItemService,remainCreateInforByRecordData);

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
			this.executorService = executorService;
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
			this.roleOfOpenPeriodRepo = roleOfOpenPeriodRepo;
			this.snapshotAdapter = snapshotAdapter;
			this.superHD60HConMedRepo = superHD60HConMedRepo;
			this.syCompanyRecordAdapter = syCompanyRecordAdapter;
			this.monthlyAggregationRemainingNumber = monthlyAggregationRemainingNumber;
			this.elapseYearRepository = elapseYearRepo;
			this.leaveComDayOffManaRepo = leaveComDayOffManaRepo;
			this.payoutSubofHDManaRepo = payoutSubofHDManaRepo;
		}

		private SuperHD60HConMedRepository superHD60HConMedRepo;

		private DailySnapshotWorkAdapter snapshotAdapter;

		private GetProcessingDate getProcessingDate;

		private RoleOfOpenPeriodRepository roleOfOpenPeriodRepo;

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

		private ManagedExecutorService executorService;

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

		private SyCompanyRecordAdapter syCompanyRecordAdapter;

		private MonthlyAggregationRemainingNumber monthlyAggregationRemainingNumber;

		private ElapseYearRepository elapseYearRepository;

		private PayoutSubofHDManaRepository payoutSubofHDManaRepo;


		private LeaveComDayOffManaRepository leaveComDayOffManaRepo;

		HashMap<String,Optional<PredetemineTimeSetting>> predetemineTimeSetting = new HashMap<String, Optional<PredetemineTimeSetting>>();
		HashMap<String, Optional<RegularLaborTimeEmp>> regularLaborTimeEmpMap = new HashMap<String, Optional<RegularLaborTimeEmp>>();
		HashMap<String, Optional<DeforLaborTimeEmp>> deforLaborTimeEmpMap = new HashMap<String, Optional<DeforLaborTimeEmp>>();
		HashMap<String, Optional<RegularLaborTimeWkp>> regularLaborTimeWkpMap = new HashMap<String, Optional<RegularLaborTimeWkp>>();
		HashMap<String, Optional<DeforLaborTimeWkp>> deforLaborTimeWkpMap = new HashMap<String, Optional<DeforLaborTimeWkp>>();
		HashMap<String, Optional<MonthlyWorkTimeSetEmp>> monthlyWorkTimeSetEmpMap = new HashMap<String, Optional<MonthlyWorkTimeSetEmp>>();
		Optional<RegularLaborTimeCom> regularLaborTimeCom = Optional.empty();
		Optional<DeforLaborTimeCom> deforLaborTimeCom = Optional.empty();
		HashMap<String, Optional<MonthlyWorkTimeSetCom>> monthlyWorkTimeSetComMap = new HashMap<String, Optional<MonthlyWorkTimeSetCom>>();
		HashMap<String, Optional<MonthlyWorkTimeSetWkp>> monthlyWorkTimeSetWkpMap = new HashMap<String, Optional<MonthlyWorkTimeSetWkp>>();
		HashMap<String, Optional<WkpFlexMonthActCalSet>> wkpFlexMonthActCalSetMap = new HashMap<String, Optional<WkpFlexMonthActCalSet>>();
		HashMap<String, Optional<EmpFlexMonthActCalSet>> empFlexMonthActCalSetMap = new HashMap<String, Optional<EmpFlexMonthActCalSet>>();
		HashMap<String, Optional<WkpDeforLaborMonthActCalSet>> wkpDeforLaborMonthActCalSetMap = new HashMap<String, Optional<WkpDeforLaborMonthActCalSet>>();
		HashMap<String, Optional<EmpDeforLaborMonthActCalSet>> empDeforLaborMonthActCalSetMap = new HashMap<String, Optional<EmpDeforLaborMonthActCalSet>>();
		HashMap<String, Optional<WkpRegulaMonthActCalSet>> wkpRegulaMonthActCalSetMap = new HashMap<String, Optional<WkpRegulaMonthActCalSet>>();
		HashMap<String, Optional<EmpRegulaMonthActCalSet>> empRegulaMonthActCalSetMap = new HashMap<String, Optional<EmpRegulaMonthActCalSet>>();
		HashMap<String, YearMonth> yearMonthFromCalenderMap = new HashMap<String, YearMonth>();
		Optional<UsageUnitSetting> usageUnitSettingCache = Optional.empty();
		List<RoleOfOpenPeriod> roleOfOpenPeriodCache = new ArrayList<RoleOfOpenPeriod>();
		Optional<RoundingSetOfMonthly> roundingSetOfMonthlyCache = Optional.empty();
		Optional<AgreementOperationSetting> agreementOperationSettingCache = Optional.empty();
		List<ClosureEmployment> employmentClosureCache = new ArrayList<ClosureEmployment>();

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
		public List<ScheRemainCreateInfor> scheRemainCreateInfor(String sid,
				DatePeriod dateData) {
			return remainCreateInforByScheData.createRemainInforNew(sid, dateData.datesBetween());
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
			if(usageUnitSettingCache.isPresent()) {
				return usageUnitSettingCache;
			}
			usageUnitSettingCache = usageUnitSettingRepo.findByCompany(companyId);
			return usageUnitSettingCache;
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
		public List<EmptYearlyRetentionSetting> emptYearlyRetentionSet(String companyId) {
			return employmentSettingRepo.findAll(companyId);
		}

		@Override
		public Optional<LegalTransferOrderSetOfAggrMonthly> monthLegalTransferOrderCalcSet(String companyId) {
			return legalTransferOrderSetOfAggrMonthlyRepo.find(companyId);
		}

		@Override
		public List<OvertimeWorkFrame> roleOvertimeWorks(String companyId) {
			return roleOvertimeWorkRepo.getOvertimeWorkFrameByFrameByCom(companyId, NotUseAtr.USE.value);
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
		public Optional<InsufficientFlexHolidayMnt> insufficientFlexHolidayMnt(String cid) {
			return insufficientFlexHolidayMntRepo.findByCId(cid);
		}

		@Override
		public Optional<FlexShortageLimit> flexShortageLimit(String companyId) {
			return flexShortageLimitRepo.get(companyId);
		}

		@Override
		public Optional<RoundingSetOfMonthly> monthRoundingSet(String companyId) {
			if(roundingSetOfMonthlyCache.isPresent()) {
				return roundingSetOfMonthlyCache;
			}
			roundingSetOfMonthlyCache = roundingSetOfMonthlyRepo.find(companyId);
			return roundingSetOfMonthlyCache;
		}

		@Override
		public List<TotalTimes> totalTimes(String companyId) {
			return totalTimesRepo.getAllTotalTimes(companyId);
		}

		@Override
		public Optional<AgreementOperationSetting> agreementOperationSetting(String companyId) {
			if(agreementOperationSettingCache.isPresent()) {
				return agreementOperationSettingCache;
			}
			agreementOperationSettingCache = agreementOperationSettingRepo.find(companyId);
			return agreementOperationSettingCache;
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
			if(!employmentClosureCache.isEmpty()) {
				return employmentClosureCache;
			}
			employmentClosureCache = closureEmploymentRepo.findListEmployment(companyId, employmentCDs);
			return employmentClosureCache;
		}

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetByWorkTimeCode(String companyId,
				String workTimeCode) {
			if(predetemineTimeSetting.containsKey(workTimeCode)) {
				return predetemineTimeSetting.get(workTimeCode);
			}
			Optional<PredetemineTimeSetting> item = predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode);
			predetemineTimeSetting.put(workTimeCode, item);
			return item;
		}

		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			return converterFactory.createDailyConverter();
		}

		@Override
		public ManagedParallelWithContext parallelContext() {
			return parallel;
		}

//		@Override
//		public YearMonth yearMonthFromCalender(CacheCarrier cacheCarrier, String companyId, YearMonth yearMonth) {
//			String key = companyId + yearMonth.v();
//			if(yearMonthFromCalenderMap.containsKey(key)) {
//				return yearMonthFromCalenderMap.get(key);
//			}
//			YearMonth item = companyAdapter.getYearMonthFromCalenderYM(cacheCarrier, companyId, yearMonth);
//			yearMonthFromCalenderMap.put(key, item);
//			return item;
//		}

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

		@Override
		public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
			return workingConditionItemRepo.getBySidAndStandardDate(employeeId, baseDate);
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
			return agreementTimeOfClassificationRepo.getByCidAndClassificationCode(companyId, classificationCode,laborSystemAtr);
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
			return agreementTimeWorkPlaceRepo.getByWorkplaceId(workplaceId, laborSystemAtr);
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
			return agreementTimeOfEmploymentRepo.getByCidAndCd(companyId, employmentCategoryCode, laborSystemAtr);
		}

		@Override
		public List<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId) {
			return agreementTimeCompanyRepo.find(companyId);
		}

		@Override
		public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId,
				LaborSystemtAtr laborSystemAtr) {
			return agreementTimeCompanyRepo.getByCid(companyId, laborSystemAtr);
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
			return closureStatusManagementRepo.getLatestByEmpId(employeeId);
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
		public void addOrUpdateReserveLeaveGrantTimeRemainHistoryData(ReserveLeaveGrantTimeRemainHistoryData domain) {
			rsvLeaveGrantTimeRemainHistRepo.addOrUpdate(domain);
		}

		@Override
		public List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId) {
			return rervLeaGrantRemDataRepo.find(employeeId);
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
		public void deleteSubstitutionOfHDManagementDataAfter(String sid, boolean unknownDateFlag, GeneralDate target) {
			substitutionOfHDManaDataRepo.deleteAfter(sid, unknownDateFlag, target);
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
		public void deletePayoutManagementDataAfter(String sid, boolean unknownDateFlag, GeneralDate target) {
			payoutManagementDataRepo.deleteAfter(sid, unknownDateFlag, target);
		}

		@Override
		public Optional<PayoutManagementData> payoutManagementData(String Id) {
			return payoutManagementDataRepo.findByID(Id);
		}

		@Override
		public void deleteCompensatoryDayOffManaDataAfter(String sid, boolean unknownDateFlag, GeneralDate target) {
			comDayOffManaDataRepo.deleteAfter(sid, unknownDateFlag, target);
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
		public void deleteLeaveManagementDataAfter(String sid, boolean unknownDateFlag, GeneralDate target) {
			leaveManaDataRepo.deleteAfter(sid, unknownDateFlag, target);;
		}

		@Override
		public List<SpecialLeaveGrantRemainingData> specialLeaveGrantRemainingData(String employeeId, int specialCode) {
			return specialLeaveGrantRepo.getAll(employeeId, specialCode);
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
			return specialHolidayRepo.findByCompanyId(companyId);
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

			OuenWorkTimeOfDaily domain = ouenWorkTimeOfDailyRepo.find(empId, ymd);
			if(domain == null)
				return new ArrayList<>();
			
			return domain.getOuenTimes();
		}

		@Override
		public List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance(String empId,
				GeneralDate ymd) {
			OuenWorkTimeSheetOfDaily domain =  ouenWorkTimeSheetOfDailyRepo.find(empId, ymd);
			if(domain == null)
				return new ArrayList<>();
			
			return domain.getOuenTimeSheet();
		}

		@Override
		public boolean isUseWorkLayer(String companyId) {
			return false;
		}

		@Override
		public Optional<RegularLaborTimeCom> regularLaborTimeByCompany(String companyId) {
			if(regularLaborTimeCom.isPresent()) {
				return regularLaborTimeCom;
			}
			regularLaborTimeCom = regularLaborTimeComRepo.find(companyId);
			return regularLaborTimeCom;
		}

		@Override
		public Optional<DeforLaborTimeCom> deforLaborTimeByCompany(String companyId) {
			if(deforLaborTimeCom.isPresent()) {
				return deforLaborTimeCom;
			}
			deforLaborTimeCom = deforLaborTimeComRepo.find(companyId);
			return deforLaborTimeCom;
		}

		@Override
		public Optional<RegularLaborTimeWkp> regularLaborTimeByWorkplace(String cid, String wkpId) {
			if(regularLaborTimeWkpMap.containsKey(wkpId)) {
				return regularLaborTimeWkpMap.get(wkpId);
			}
			Optional<RegularLaborTimeWkp> item  = regularLaborTimeWkpRepo.find(cid, wkpId);
			regularLaborTimeWkpMap.put(wkpId, item);
			return item;
		}

		@Override
		public Optional<DeforLaborTimeWkp> deforLaborTimeByWorkplace(String cid, String wkpId) {
			if(deforLaborTimeWkpMap.containsKey(wkpId)) {
				return deforLaborTimeWkpMap.get(wkpId);
			}
			Optional<DeforLaborTimeWkp> item  = deforLaborTimeWkpRepo.find(cid, wkpId);
			deforLaborTimeWkpMap.put(wkpId, item);
			return item;
		}



		@Override
		public Optional<RegularLaborTimeEmp> regularLaborTimeByEmployment(String cid, String employmentCode) {
			if(regularLaborTimeEmpMap.containsKey(employmentCode)) {
				return regularLaborTimeEmpMap.get(employmentCode);
			}
			Optional<RegularLaborTimeEmp> item = regularLaborTimeEmpRepo.findById(cid, employmentCode);
			regularLaborTimeEmpMap.put(employmentCode, item);
			return item;
		}

		@Override
		public Optional<DeforLaborTimeEmp> deforLaborTimeByEmployment(String cid, String employmentCode) {
			if(deforLaborTimeEmpMap.containsKey(employmentCode)) {
				return deforLaborTimeEmpMap.get(employmentCode);
			}
			Optional<DeforLaborTimeEmp> item = deforLaborTimeEmpRepo.find(cid, employmentCode);
			deforLaborTimeEmpMap.put(employmentCode, item);
			return item;
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
			if(wkpRegulaMonthActCalSetMap.containsKey(wkpId)) {
				return wkpRegulaMonthActCalSetMap.get(wkpId);
			}
			Optional<WkpRegulaMonthActCalSet> item  = wkpRegulaMonthActCalSetRepo.find(cid, wkpId);
			wkpRegulaMonthActCalSetMap.put(wkpId, item);
			return item;
		}

		@Override
		public Optional<EmpRegulaMonthActCalSet> monthRegularCalcSetByEmployment(
				String cid, String empCode) {
			if(empRegulaMonthActCalSetMap.containsKey(empCode)) {
				return empRegulaMonthActCalSetMap.get(empCode);
			}
			Optional<EmpRegulaMonthActCalSet> item = empRegulaMonthActCalSetRepo.find(cid, empCode);
			empRegulaMonthActCalSetMap.put(empCode, item);
			return item;
		}

		@Override
		public Optional<WkpDeforLaborMonthActCalSet> monthDeforCalcSetByWorkplace(
				String cid, String wkpId) {
			if(wkpDeforLaborMonthActCalSetMap.containsKey(wkpId)) {
				return wkpDeforLaborMonthActCalSetMap.get(wkpId);
			}
			Optional<WkpDeforLaborMonthActCalSet> item = wkpDeforLaborMonthActCalSetRepo.find(cid, wkpId);
			wkpDeforLaborMonthActCalSetMap.put(wkpId, item);
			return item;
		}

		@Override
		public Optional<EmpDeforLaborMonthActCalSet> monthDeforCalcSetByEmployment(
				String cid, String empCode) {
			if(empDeforLaborMonthActCalSetMap.containsKey(empCode)) {
				return empDeforLaborMonthActCalSetMap.get(empCode);
			}
			Optional<EmpDeforLaborMonthActCalSet> item = empDeforLaborMonthActCalSetRepo.find(cid, empCode);
			empDeforLaborMonthActCalSetMap.put(empCode, item);
			return item;
		}

		@Override
		public Optional<WkpFlexMonthActCalSet> monthFlexCalcSetByWorkplace(
				String cid, String wkpId) {
			if(wkpFlexMonthActCalSetMap.containsKey(wkpId)) {
				return wkpFlexMonthActCalSetMap.get(wkpId);
			}
			Optional<WkpFlexMonthActCalSet> item = wkpFlexMonthActCalSetRepo.find(cid, wkpId);
			wkpFlexMonthActCalSetMap.put(wkpId, item);
			return item;
		}

		@Override
		public Optional<EmpFlexMonthActCalSet> monthFlexCalcSetByEmployment(
				String cid, String empCode) {
			if(empFlexMonthActCalSetMap.containsKey(empCode)) {
				return empFlexMonthActCalSetMap.get(empCode);
			}
			Optional<EmpFlexMonthActCalSet> item = empFlexMonthActCalSetRepo.find(cid, empCode);
			empFlexMonthActCalSetMap.put(empCode, item);
			return item;
		}

		@Override
		public Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			String key = workplaceId + laborAttr.value + ym.v();
			if(monthlyWorkTimeSetWkpMap.containsKey(key)) {
				return monthlyWorkTimeSetWkpMap.get(key);
			}
			Optional<MonthlyWorkTimeSetWkp> item = monthlyWorkTimeSetRepo.findWorkplace(cid, workplaceId, laborAttr, ym);
			monthlyWorkTimeSetWkpMap.put(key, item);
			return item;
		}

		@Override
		public Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			return monthlyWorkTimeSetRepo.findEmployee(cid, sid, laborAttr, ym);
		}

		@Override
		public Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmp(String cid, String empCode,
				LaborWorkTypeAttr laborAttr, YearMonth ym) {
			String key = empCode + laborAttr.value + ym.v();
			if(monthlyWorkTimeSetEmpMap.containsKey(key)) {
				return monthlyWorkTimeSetEmpMap.get(key);
			}
			Optional<MonthlyWorkTimeSetEmp> item = monthlyWorkTimeSetRepo.findEmployment(cid, empCode, laborAttr, ym);
			monthlyWorkTimeSetEmpMap.put(key, item);
			return item;
		}

		@Override
		public Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetCom(String cid, LaborWorkTypeAttr laborAttr,
				YearMonth ym) {
			String key = laborAttr.value + ym.v().toString();
			if(monthlyWorkTimeSetComMap.containsKey(key)) {
				return monthlyWorkTimeSetComMap.get(key);
			}
			Optional<MonthlyWorkTimeSetCom> item = monthlyWorkTimeSetRepo.findCompany(cid, laborAttr, ym);
			monthlyWorkTimeSetComMap.put(key, item);
			return item;
		}

		@Override
		public BasicAgreementSetting basicAgreementSetting(String companyId, String employeeId, GeneralDate criteriaDate) {
			return AgreementDomainService.getBasicSet(this, companyId, employeeId, criteriaDate);
		}


		public Optional<AggregateMethodOfMonthly> aggregateMethodOfMonthly(String cid) {
			return verticalTotalMethodOfMonthlyRepo.findByCid(cid);
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
		public BasicAgreementSettingForCalc basicAgreementSetting(String cid, String sid, YearMonth ym, GeneralDate baseDate) {

			return AgreementDomainService.getBasicSet(this, cid, sid, baseDate, ym);
		}

		@Override
		public List<RoleOfOpenPeriod> roleOfOpenPeriod(String cid) {
			if(!roleOfOpenPeriodCache.isEmpty()) {
				return roleOfOpenPeriodCache;
			}
			roleOfOpenPeriodCache = roleOfOpenPeriodRepo.findByCID(cid);
			return roleOfOpenPeriodCache;
		}

		@Override
		public List<IntegrationOfDaily> integrationOfDaily(String sid, DatePeriod period) {

			return integrationOfDailyGetter.getIntegrationOfDaily(sid, period);
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
			if(weekRuleManagementCache.isPresent()) {
				return weekRuleManagementCache;
			}
			weekRuleManagementCache = weekRuleManagementRepo.find(cid);
			return weekRuleManagementCache;
		}

		@Override
		public ReservationOfMonthly reservation(String sid, GeneralDate date, String companyID) {

			return VerticalTotalAggregateService.aggregate(this, sid, date, companyID);
		}

		@Override
		public List<StampCard> stampCard(String empId) {

			return stampCardRepo.getListStampCard(empId);
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
			return superHD60HConMedRepo.findById(cid);
		}

		@Override
		public List<DailyInterimRemainMngData> createDailyInterimRemainMngs(CacheCarrier cacheCarrier,
				String companyId, String employeeId, DatePeriod period, MonAggrCompanySettings comSetting,
				MonthlyCalculatingDailys dailys) {

			return monthlyAggregationRemainingNumber.createDailyInterimRemainMngs(cacheCarrier,
					companyId, employeeId, period, comSetting, dailys);
		}

		@Override
		public Optional<AffiliationInforOfDailyAttd> dailyAffiliationInfor(String employeeId, GeneralDate ymd) {
			return affiliationInforOfDailyPerforRepo.findByKey(employeeId, ymd).map(c -> c.getAffiliationInfor());
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
		public Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfo(String sid, int spLeaveCD, UnitAtr use) {

			return this.specialLeaveBasicInfoRepo.getBySidLeaveCdUser(sid, spLeaveCD, UseAtr.USE);
		}

		@Override
		public List<AffCompanyHistImport> listAffCompanyHistImport(List<String> listAppId, DatePeriod period) {
			return syCompanyRecordAdapter.getAffCompanyHistByEmployee(listAppId, period);
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
			return specialLeaveBasicInfoRepo.getBySidLeaveCdUser(sid, spLeaveCD, use);
		}

		@Override
		public Optional<SpecialHoliday> specialHoliday(String companyID, int specialHolidayCD) {

			return this.specialHolidayRepo.findByCode(companyID, specialHolidayCD);
		}

		@Override
		public List<AffCompanyHistSharedImport> employeeAffiliatedCompanyHistories(CacheCarrier cacheCarrier,
				List<String> sids, DatePeriod datePeriod) {

			return this.empEmployeeAdapter.getAffCompanyHistByEmployee(cacheCarrier, sids, datePeriod);
		}

		@Override
		public Optional<ElapseYear> elapseYear(String companyId, int specialHolidayCode) {

			return this.elapseYearRepository.findByCode(new CompanyId(companyId), new SpecialHolidayCode(specialHolidayCode));
		}

		@Override
		public List<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode) {

			return this.grantDateTblRepo.findBySphdCd(companyId, specialHolidayCode);
		}

		@Override
		public Optional<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode, String grantDateCode) {

			return this.grantDateTblRepo.findByCode(companyId, specialHolidayCode, grantDateCode);
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
			return annLeaGrantRemDataRepo.find(employeeId);
		}

		@Override
		public void addPayoutSubofHDManagement(PayoutSubofHDManagement domain) {
			payoutSubofHDManaRepo.add(domain);
		}

		@Override
		public void addLeaveComDayOffManagement(LeaveComDayOffManagement domain) {
			leaveComDayOffManaRepo.add(domain);
		}

		@Override
		public void deleteSpecialLeaveGrantRemainAfter(String sid, int specialCode, GeneralDate targetDate) {
				specialLeaveGrantRepo.deleteAfter(sid, specialCode, targetDate);
		}

		@Override
		public Optional<HolidayAddtionSet> holidayAddtionSet(String cid) {
			return holidayAddtionRepo.findByCId(cid);
		}
	}
}
