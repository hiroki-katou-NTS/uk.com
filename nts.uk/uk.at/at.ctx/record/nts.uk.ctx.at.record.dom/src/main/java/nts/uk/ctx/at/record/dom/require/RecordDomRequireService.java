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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleAdapter;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.shift.pattern.GetPredWorkingDaysAdaptor;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkPlaceSidImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.WorkTypeOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.byperiod.MonthlyCalculationByPeriod;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriodRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSettingService;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreTimeByPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementPeriod;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.flex.CalcFlexChangeDto;
import nts.uk.ctx.at.record.dom.monthly.flex.CheckBeforeCalcFlexChangeService;
import nts.uk.ctx.at.record.dom.monthly.flex.ConditionCalcResult;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerRepository;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.record.dom.monthly.updatedomain.UpdateAllDomainMonthService;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.record.dom.monthly.workform.flex.MonthlyAggrSetOfFlexRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthlyRepository;
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
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationService;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AggregateSpecifiedDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.getprocessingdate.GetProcessingDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordService;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrderRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CalcAnnLeaAttendanceRate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CreateTempAnnLeaMngProc;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfClassification;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfEmployment;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.record.dom.standardtime.AgreementUnitSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementMonthSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeCompanyRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfClassificationRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfEmploymentRepostitory;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.BasicAgreementSettingRepository;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryLaborTime;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.DetermineActualResultLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.PerformanceType;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.TargetPersonRepository;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexShortageLimitRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.InsufficientFlexHolidayMntRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.ouen.aggframe.OuenAggregateFrameSetOfMonthly;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.ouen.aggframe.OuenAggregateFrameSetOfMonthlyRepo;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.outsideot.service.OutsideOTSettingService;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.AppRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByApplicationData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByScheData;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemCustom;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.com.ComFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.com.ComFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.com.ComDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.com.ComDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.com.ComRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.com.ComRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.wkp.WkpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.wkp.WkpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.wkp.WkpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.workrecord.monthcal.calcmethod.other.wkp.WkpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
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

@Stateless
public class RecordDomRequireService
		implements InterimRemainOffPeriodCreateData.RequireM4, BreakDayOffMngInPeriodQuery.RequireM10,
		AbsenceReruitmentMngInPeriodQuery.RequireM10, SpecialLeaveManagementService.RequireM5,
		GetClosureStartForEmployee.RequireM1, ClosureService.RequireM3, OutsideOTSettingService.RequireM2,
		OutsideOTSettingService.RequireM1, AbsenceTenProcess.RequireM1, AbsenceTenProcess.RequireM2,
		AbsenceTenProcess.RequireM4, AbsenceTenProcess.RequireM3, AbsenceReruitmentMngInPeriodQuery.RequireM2,
		WorkingConditionService.RequireM1, GetAnnAndRsvRemNumWithinPeriod.RequireM2, CalcAnnLeaAttendanceRate.RequireM3,
		GetClosurePeriod.RequireM1, CalcNextAnnLeaGrantInfo.RequireM1,
		GetNextAnnualLeaveGrantProcKdm002.RequireM1, InterimRemainOffPeriodCreateData.RequireM2,
		DailyStatutoryLaborTime.RequireM1, AggregateMonthlyRecordService.RequireM1, MonAggrCompanySettings.RequireM6,
		WorkTimeIsFluidWork.RequireM2, MonAggrEmployeeSettings.RequireM2, MonthlyCalculationByPeriod.RequireM1,
		GetClosurePeriod.RequireM2, VerticalTotalOfMonthly.RequireM1, TotalCountByPeriod.RequireM1,
		GetAgreementTime.RequireM5, GetAgreementTime.RequireM3, GetAgreementTime.RequireM4,
		GetAgreementPeriod.RequireM2, GetAgreTimeByPeriod.RequireM8, GetAgreTimeByPeriod.RequireM7,
		GetAgreTimeByPeriod.RequireM5, GetAgreTimeByPeriod.RequireM3,
		MonthlyAggregationService.RequireM1, AgeementTimeCommonSettingService.RequireM1,
		CreateTempAnnLeaMngProc.RequireM3, AggregateSpecifiedDailys.RequireM1, ClosureService.RequireM6,
		ClosureService.RequireM5, MonthlyUpdateMgr.RequireM4, MonthlyClosureUpdateLogProcess.RequireM3,
		CancelActualLock.RequireM1, ProcessYearMonthUpdate.RequireM1, BreakDayOffMngInPeriodQuery.RequireM2 {

	public RecordDomRequireService createRequire() {
		return this;
	}

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
	private AnnualLeaveTimeRemainHistRepository annualLeaveTimeRemainHistRepo;
	@Inject
	private AnnualLeaveMaxHistRepository annualLeaveMaxHistRepo;
	@Inject
	private RsvLeaveGrantRemainHistRepository rsvLeaveGrantRemainHistRepo;
	@Inject
	private RsvLeaveGrantTimeRemainHistRepository rsvLeaveGrantTimeRemainHistRepo;
	@Inject
	private InterimRemainOffMonthProcess interimRemainOffMonthProcess;
	@Inject
	private MonthlyClosureUpdateErrorInforRepository monthlyClosureUpdateErrorInforRepo;
	@Inject
	private MonthlyClosureUpdateLogRepository monthlyClosureUpdateLogRepo;
	@Inject
	private MonthlyClosureUpdatePersonLogRepository monthlyClosureUpdatePersonLogRepo;
	@Inject
	protected ComSubstVacationRepository comSubstVacationRepo;
	@Inject
	protected CompensLeaveComSetRepository compensLeaveComSetRepo;
	@Inject
	protected SpecialLeaveGrantRepository specialLeaveGrantRepo;
	@Inject
	protected EmpEmployeeAdapter empEmployeeAdapter;
	@Inject
	protected GrantDateTblRepository grantDateTblRepo;
	@Inject
	protected AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	@Inject
	protected InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo;
	@Inject
	protected SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo;
	@Inject
	protected InterimRecAbasMngRepository interimRecAbasMngRepo;
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

	@Override
	public Optional<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode) {
		return grantDateTblRepo.findByCodeAndIsSpecified(companyId, specialHolidayCode);
	}

	@Override
	public List<ElapseYear> elapseYear(String companyId, int specialHolidayCode, String grantDateCode) {
		return grantDateTblRepo.findElapseByGrantDateCd(companyId, specialHolidayCode, grantDateCode);
	}

	@Override
	public Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId) {
		return annLeaEmpBasicInfoRepo.get(employeeId);
	}

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

	@Override
	public Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfo(String sid, int spLeaveCD, UseAtr use) {
		return specialLeaveBasicInfoRepo.getBySidLeaveCdUser(sid, spLeaveCD, use);
	}

	@Override
	public Optional<ComSubstVacation> comSubstVacation(String companyId) {
		return comSubstVacationRepo.findById(companyId);
	}

	@Override
	public Optional<InterimAbsMng> interimAbsMng(String absId) {
		return interimRecAbasMngRepo.getAbsById(absId);
	}

	@Override
	public List<InterimRecAbsMng> interimRecAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr) {
		return interimRecAbasMngRepo.getRecOrAbsMng(interimId, isRec, mngAtr);
	}

	@Override
	public Optional<InterimRecMng> interimRecMng(String recId) {
		return interimRecAbasMngRepo.getReruitmentById(recId);
	}

	@Override
	public List<SubstitutionOfHDManagementData> substitutionOfHDManagementData(String cid, String sid, GeneralDate ymd,
			double unOffseDays) {
		return substitutionOfHDManaDataRepo.getByYmdUnOffset(cid, sid, ymd, unOffseDays);
	}

	@Override
	public List<PayoutManagementData> payoutManagementData(String cid, String sid, GeneralDate ymd, double unUse,
			DigestionAtr state) {
		return payoutManagementDataRepo.getByUnUseState(cid, sid, ymd, unUse, state);
	}

	@Override
	public List<InterimRemain> interimRemains(String employeeId, DatePeriod dateData, RemainType remainType) {
		return interimRemainRepo.getRemainBySidPriod(employeeId, dateData, remainType);
	}

	@Override
	public Optional<InterimDayOffMng> interimDayOffMng(String dayOffManaId) {
		return interimBreakDayOffMngRepo.getDayoffById(dayOffManaId);
	}

	@Override
	public Optional<InterimBreakMng> interimBreakMng(String breakManaId) {
		return interimBreakDayOffMngRepo.getBreakManaBybreakMngId(breakManaId);
	}

	@Override
	public List<CompensatoryDayOffManaData> compensatoryDayOffManaData(String cid, String sid, GeneralDate ymd) {
		return comDayOffManaDataRepo.getBySidDate(cid, sid, ymd);
	}

	@Override
	public List<InterimBreakDayOffMng> interimBreakDayOffMng(String mngId, boolean breakDay, DataManagementAtr mngAtr) {
		return interimBreakDayOffMngRepo.getBreakDayOffMng(mngId, breakDay, mngAtr);
	}

	@Override
	public CompanyDto firstMonth(CacheCarrier cacheCarrier, String companyId) {
		return companyAdapter.getFirstMonthRequire(cacheCarrier, companyId);
	}

	@Override
	public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
			String employeeId, GeneralDate baseDate) {
		return shareEmploymentAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
	}

	@Override
	public List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier, List<String> sids,
			DatePeriod datePeriod) {
		return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
	}

	@Override
	public List<LeaveManagementData> leaveManagementData(String cid, String sid, GeneralDate ymd, DigestionAtr state) {
		return leaveManaDataRepo.getBySidYmd(cid, sid, ymd, state);
	}

	@Override
	public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
		return workingConditionItemRepo.getByHistoryId(historyId);
	}

	@Override
	public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
		return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
	}

	@Override
	public Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode) {
		return flowWorkSettingRepo.find(companyId, workTimeCode);
	}

	@Override
	public Optional<FlexWorkSetting> flexWorkSetting(String companyId, String workTimeCode) {
		return flexWorkSettingRepo.find(companyId, workTimeCode);
	}

	@Override
	public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, String workTimeCode) {
		return fixedWorkSettingRepo.findByKey(companyId, workTimeCode);
	}

	@Override
	public Optional<DiffTimeWorkSetting> diffTimeWorkSetting(String companyId, String workTimeCode) {
		return diffTimeWorkSettingRepo.find(companyId, workTimeCode);
	}

	@Override
	public Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode) {
		return workTimeSettingRepo.findByCode(companyId, workTimeCode);
	}

	@Override
	public CompensatoryLeaveComSetting compensatoryLeaveComSetting(String companyId) {
		return compensLeaveComSetRepo.find(companyId);
	}

	@Override
	public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, String workTimeCode) {
		return predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode);
	}

	@Override
	public Optional<SpecialHoliday> specialHoliday(String companyID, int specialHolidayCD) {
		return specialHolidayRepo.findByCode(companyID, specialHolidayCD);
	}

	@Override
	public List<Integer> getSpecialHolidayNumber(String cid, int sphdSpecLeaveNo) {
		return specialHolidayRepo.findBySphdSpecLeave(cid, sphdSpecLeaveNo);
	}

	@Override
	public Optional<Closure> closure(String companyId, int closureId) {
		return closureRepo.findById(companyId, closureId);
	}

	@Override
	public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
		return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
	}

	@Override
	public Optional<WorkType> workType(String companyId, String workTypeCd) {
		return workTypeRepo.findByPK(companyId, workTypeCd);
	}

	@Override
	public Integer excludeHolidayAtr(CacheCarrier cacheCarrier, String cid, String appID) {

		return remainCreateInforByApplicationData.excludeHolidayAtr(cacheCarrier, cid, appID);
	}

	@Override
	public Optional<EmpSubstVacation> empSubstVacation(String companyId, String contractTypeCode) {
		return empSubstVacationRepo.findById(companyId, contractTypeCode);
	}

	@Override
	public CompensatoryLeaveEmSetting compensatoryLeaveEmSetting(String companyId, String employmentCode) {
		return compensLeaveEmSetRepo.find(companyId, employmentCode);
	}

	@Override
	public List<Closure> closure(String companyId) {
		return closureRepo.findAll(companyId);
	}

	@Override
	public EmployeeImport employee(CacheCarrier cacheCarrier, String empId) {
		return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
	}

	@Override
	public EmployeeRecordImport employeeFullInfo(CacheCarrier cacheCarrier, String empId) {
		return empEmployeeAdapter.findByAllInforEmpId(cacheCarrier, empId);
	}

	@Override
	public List<SClsHistImport> employeeClassificationHistoires(CacheCarrier cacheCarrier, String companyId,
			List<String> employeeIds, DatePeriod datePeriod) {
		return empEmployeeAdapter.lstClassByEmployeeId(cacheCarrier, companyId, employeeIds, datePeriod);
	}

	@Override
	public List<AffCompanyHistSharedImport> employeeAffiliatedCompanyHistories(CacheCarrier cacheCarrier,
			List<String> sids, DatePeriod datePeriod) {
		return empEmployeeAdapter.getAffCompanyHistByEmployee(cacheCarrier, sids, datePeriod);
	}

	@Override
	public Optional<OutsideOTSetting> outsideOTSetting(String companyId) {
		return outsideOTSettingRepo.findById(companyId);
	}

	@Override
	public List<WorkdayoffFrame> workdayoffFrames(String companyId) {
		return workdayoffFrameRepo.getAllWorkdayoffFrame(companyId);
	}

	@Override
	public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
		return annualPaidLeaveSettingRepo.findByCompanyId(companyId);
	}

	@Override
	public Optional<EmptYearlyRetentionSetting> employmentYearlyRetentionSetting(String companyId,
			String employmentCode) {
		return employmentSettingRepo.find(companyId, employmentCode);
	}

	@Override
	public Optional<RetentionYearlySetting> retentionYearlySetting(String companyId) {
		return retentionYearlySettingRepo.findByCompanyId(companyId);
	}

	@Override
	public Optional<SEmpHistoryImport> employeeEmploymentHis(CacheCarrier cacheCarrier, String companyId,
			String employeeId, GeneralDate baseDate) {
		return sysEmploymentHisAdapter.findSEmpHistBySidRequire(cacheCarrier, companyId, employeeId, baseDate);
	}

	@Override
	public List<WorkInfoOfDailyPerformance> dailyWorkInfos(String employeeId, DatePeriod datePeriod) {
		return workInformationRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
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
	public List<TimeLeavingOfDailyPerformance> dailyTimeLeavings(String employeeId, DatePeriod datePeriod) {
		return timeLeavingOfDailyPerformanceRepo.findbyPeriodOrderByYmd(employeeId, datePeriod);
	}

	@Override
	public List<TemporaryTimeOfDailyPerformance> dailyTemporaryTimes(String employeeId, DatePeriod datePeriod) {
		return temporaryTimeOfDailyPerformanceRepo.findbyPeriodOrderByYmd(employeeId, datePeriod);
	}

	@Override
	public List<SpecificDateAttrOfDailyPerfor> dailySpecificDates(String employeeId, DatePeriod datePeriod) {
		return specificDateAttrOfDailyPerforRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
	}

	@Override
	public List<EmployeeDailyPerError> dailyEmpErrors(String employeeId, DatePeriod datePeriod) {
		return employeeDailyPerErrorRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
	}

	@Override
	public List<AnyItemValueOfDaily> dailyAnyItems(List<String> employeeId, DatePeriod baseDate) {
		return anyItemValueOfDailyRepo.finds(employeeId, baseDate);
	}

	@Override
	public List<PCLogOnInfoOfDaily> dailyPcLogons(List<String> employeeId, DatePeriod baseDate) {
		return pcLogOnInfoOfDailyRepo.finds(employeeId, baseDate);
	}

	@Override
	public List<WorkTypeOfDailyPerformance> dailyWorkTypes(List<String> employeeId, DatePeriod baseDate) {
		return workTypeOfDailyPerforRepo.finds(employeeId, baseDate);
	}

	@Override
	public Optional<WorkTypeOfDailyPerformance> dailyWorkType(String employeeId, GeneralDate ymd) {
		return workTypeOfDailyPerforRepo.findByKey(employeeId, ymd);
	}

	@Override
	public List<AttendanceTimeOfDailyPerformance> dailyAttendanceTimes(String employeeId, DatePeriod datePeriod) {
		return attendanceTimeRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
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
	public Optional<AffWorkPlaceSidImport> affWorkPlace(String employeeId, GeneralDate baseDate) {
		return affWorkplaceAdapter.findBySidAndDate(employeeId, baseDate);
	}

	@Override
	public Optional<WorkingCondition> workingCondition(String historyId) {
		return workingConditionRepo.getByHistoryId(historyId);
	}

	@Override
	public List<SharedSidPeriodDateEmploymentImport> employmentHistories(CacheCarrier cacheCarrier, List<String> sids,
			DatePeriod datePeriod) {
		return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
	}

	@Override
	public List<ClosureEmployment> employmentClosure(String companyId, List<String> employmentCDs) {
		return closureEmploymentRepo.findListEmployment(companyId, employmentCDs);
	}

	@Override
	public Optional<PredetemineTimeSetting> predetemineTimeSetByWorkTimeCode(String companyId, String workTimeCode) {
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
	public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth) {
		return attendanceTimeOfMonthlyRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
	}

	@Override
	public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		return attendanceTimeOfMonthlyRepo.findByYMAndClosureIdOrderByStartYmd(employeeId, yearMonth, closureId);
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
				.map(c -> c.getSpecialHolidayRemainList()).orElse(Collections.emptyList());
	}

	@Override
	public List<AgreementYearSetting> agreementYearSetting(List<String> employeeIds, int yearMonth) {
		return agreementYearSettingRepo.findByKey(employeeIds, yearMonth);
	}

	@Override
	public Optional<AgreementYearSetting> agreementYearSetting(String employeeId, int yearMonth) {
		return agreementYearSettingRepo.findByKey(employeeId, yearMonth);
	}

	@Override
	public List<AgreementMonthSetting> agreementMonthSetting(List<String> employeeIds, List<YearMonth> yearMonths) {
		return agreementMonthSettingRepo.findByKey(employeeIds, yearMonths);
	}

	@Override
	public Optional<AgreementMonthSetting> agreementMonthSetting(String employeeId, YearMonth yearMonth) {
		return agreementMonthSettingRepo.findByKey(employeeId, yearMonth);
	}

	@Override
	public Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String employeeId, YearMonth yearMonth) {
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
	public void updateLogWithContent(String employeeID, String empCalAndSumExecLogId, int executionContent, int state) {
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
	public Optional<AffiliationInforOfDailyPerfor> dailyAffiliationInfor(String employeeId, GeneralDate ymd) {
		return affiliationInforOfDailyPerforRepo.findByKey(employeeId, ymd);
	}

	@Override
	public List<AffiliationInforOfDailyPerfor> dailyAffiliationInfors(List<String> employeeId, DatePeriod ymd) {
		return affiliationInforOfDailyPerforRepo.finds(employeeId, ymd);
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
	public List<WorkingConditionItemCustom> workingConditionItemCustom(List<String> employeeIds, GeneralDate baseDate) {
		return workingConditionItemRepo.getBySidsAndStandardDate(employeeIds, baseDate);
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
		return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(cacheCarrier, companyId, employeeId,
				baseDate);
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
	public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr) {
		return agreementTimeCompanyRepo.find(companyId, laborSystemAtr);
	}

	@Override
	public Optional<BasicAgreementSetting> basicAgreementSetting(String basicSettingId) {
		return basicAgreementSettingRepo.find(basicSettingId);
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
	public List<ClosureStatusManagement> employeeClosureStatusManagements(List<String> employeeIds, DatePeriod span) {
		return closureStatusManagementRepo.getByIdListAndDatePeriod(employeeIds, span);
	}

	@Override
	public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
		return closureStatusManagementRepo.getLatestByEmpId(employeeId);
	}

	// @Override
	// public void deleteTmpAnnualHolidayMng(String mngId) {
	// tmpAnnualHolidayMngRepo.deleteById(mngId);
	// }

	@Override
	public Optional<TmpAnnualHolidayMng> tmpAnnualHolidayMng(String mngId) {
		return tmpAnnualHolidayMngRepo.getById(mngId);
	}

	// @Override
	// public void deleteTmpResereLeaveMng(String mngId) {
	// tmpResereLeaveMngRepo.deleteById(mngId);
	// }

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
	public List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId, GeneralDate grantDate) {
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
	public void addOrUpdateReserveLeaveGrantRemainHistoryData(ReserveLeaveGrantRemainHistoryData domain, String cid) {
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

	// @Override
	// public List<InterimRemain> interimRemain(String sId) {
	// return interimRemainRepo.findByEmployeeID(sId);
	// }

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
	public List<ActualLock> actualLocks(String companyId) {
		return actualLockRepo.findAll(companyId);
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

		return ouenWorkTimeOfDailyRepo.find(empId, ymd).stream().map(c -> c.getOuenTime()).collect(Collectors.toList());
	}

	@Override
	public List<OuenWorkTimeSheetOfDailyAttendance> ouenWorkTimeSheetOfDailyAttendance(String empId, GeneralDate ymd) {

		return ouenWorkTimeSheetOfDailyRepo.find(empId, ymd).stream().map(c -> c.getOuenTimeSheet())
				.collect(Collectors.toList());
	}

	@Override
	public boolean isUseWorkLayer(String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

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
	public Optional<ShaFlexMonthActCalSet> monthFlexCalcSetbyEmployee(String cid, String sId) {
		return shaFlexMonthActCalSetRepo.find(cid, sId);
	}

	@Override
	public Optional<ShaDeforLaborMonthActCalSet> monthDeforLaborCalcSetByEmployee(String cId, String sId) {
		return shaDeforLaborMonthActCalSetRepo.find(cId, sId);
	}

	@Override
	public Optional<ShaRegulaMonthActCalSet> monthRegulaCalcSetByEmployee(String cid, String sId) {
		return shaRegulaMonthActCalSetRepo.find(cid, sId);
	}

	@Override
	public Optional<ComRegulaMonthActCalSet> monthRegulaCalSetByCompany(String companyId) {
		return comRegulaMonthActCalSetRepo.find(companyId);
	}

	@Override
	public Optional<ComDeforLaborMonthActCalSet> monthDeforLaborCalSetByCompany(String companyId) {
		return comDeforLaborMonthActCalSetRepo.find(companyId);
	}

	@Override
	public Optional<ComFlexMonthActCalSet> monthFlexCalSetByCompany(String companyId) {
		return comFlexMonthActCalSetRepo.find(companyId);
	}

	@Override
	public Optional<WkpRegulaMonthActCalSet> monthRegularCalcSetByWorkplace(String cid, String wkpId) {
		return wkpRegulaMonthActCalSetRepo.find(cid, wkpId);
	}

	@Override
	public Optional<EmpRegulaMonthActCalSet> monthRegularCalcSetByEmployment(String cid, String empCode) {
		return empRegulaMonthActCalSetRepo.find(cid, empCode);
	}

	@Override
	public Optional<WkpDeforLaborMonthActCalSet> monthDeforCalcSetByWorkplace(String cid, String wkpId) {
		return wkpDeforLaborMonthActCalSetRepo.find(cid, wkpId);
	}

	@Override
	public Optional<EmpDeforLaborMonthActCalSet> monthDeforCalcSetByEmployment(String cid, String empCode) {
		return empDeforLaborMonthActCalSetRepo.find(cid, empCode);
	}

	@Override
	public Optional<WkpFlexMonthActCalSet> monthFlexCalcSetByWorkplace(String cid, String wkpId) {
		return wkpFlexMonthActCalSetRepo.find(cid, wkpId);
	}

	@Override
	public Optional<EmpFlexMonthActCalSet> monthFlexCalcSetByEmployment(String cid, String empCode) {
		return empFlexMonthActCalSetRepo.find(cid, empCode);
	}

	@Override
	public Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId,
			LaborWorkTypeAttr laborAttr, YearMonth ym) {
		return monthlyWorkTimeSetRepo.findWorkplace(cid, workplaceId, laborAttr, ym);
	}

	@Override
	public Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid, LaborWorkTypeAttr laborAttr,
			YearMonth ym) {
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
	public Optional<GeneralDate> getProcessingDate(String employeeId, GeneralDate date) {
		return getProcessingDate.getProcessingDate(employeeId, date);
	}

	@Inject
	private GetProcessingDate getProcessingDate;
	@Inject
	private ExecutionLogRepository executionLogRepo;
	@Inject
	private DetermineActualResultLock lockStatusService;

	@Override
	public Optional<ExecutionLog> executionLog(String empCalAndSumExecLogID, int executionContent) {
		return executionLogRepo.getByExecutionContent(empCalAndSumExecLogID, executionContent);
	}

	@Override
	public LockStatus lockStatus(String companyId, GeneralDate baseDate, Integer closureId, PerformanceType type) {
		return lockStatusService.getDetermineActualLocked(companyId, baseDate, closureId, type);
	}
}
