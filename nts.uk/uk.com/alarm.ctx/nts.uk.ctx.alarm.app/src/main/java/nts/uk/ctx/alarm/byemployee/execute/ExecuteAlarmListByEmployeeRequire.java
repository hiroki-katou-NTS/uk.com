package nts.uk.ctx.alarm.byemployee.execute;

import java.util.*;

import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.execute.ExecuteAlarmListByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternCode;
import nts.uk.ctx.at.aggregation.dom.adapter.dailyrecord.DailyRecordAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.workschedule.WorkScheduleAdapter;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.function.dom.adapter.remainnumber.yearholiday.checkexistholidaygrant.CheckExistHolidayGrantAdapter;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.*;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.AlarmListExtractResult;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeInfo;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
import nts.uk.ctx.at.record.app.find.anyperiod.AnyPeriodRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.app.find.dailyperform.ConvertFactory;
import nts.uk.ctx.at.record.app.find.monthly.MonthlyRecordToAttendanceItemConverterImpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlCategory;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriodRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ExtraResultMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeekly;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.ExtractionCondWeeklyRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.AttendRateAtNextHoliday;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RCAnnualHolidayManagement;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaCriterialDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
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
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagement;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagementRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxHistoryData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.*;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.*;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthlyGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.*;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.*;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.ApprovalRootStateStatusService;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.closure.ClosureMonth;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.stream.Collectors;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.standardtime.AgreementDomainService;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementUnitSettingRepository;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementYearSettingRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMngRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCount;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCountRepository;
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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.exceptsetting.AgreementYearSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSettingForCalc;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecuteAlarmListByEmployeeRequire {

    @Inject
    private WorkScheduleAdapter workScheduleAdapter;

    @Inject
    private WorkingConditionRepository workingConditionRepo;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;

    @Inject
    private WorkScheduleRepository workScheduleRepo;

    @Inject
    private DailyRecordAdapter dailyRecordAdapter;

    @Inject
    private IntegrationOfMonthlyGetter integrationOfMonthlyGetter;

    @Inject
    private ApplicationRepository applicatoinRepo;

    @Inject
    private ApprovalStatusAdapter approvalStatusAdapter;

    @Inject
    private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

    @Inject
    private AttendanceItemNameService attendanceItemNameService;

    @Inject
    private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;

    @Inject
    private YearHolidayRepository yearHolidayRepo;

    @Inject
    private AttendanceTimeOfAnyPeriodRepository attendanceTimeOfAnyPeriodRepo;

    @Inject
    private AnyAggrPeriodRepository anyAggrPeriodRepo;

    @Inject
    private OptionalItemRepository optionalItemRepo;

    @Inject
    private ExtractionCondWeeklyRepository extractionCondWeeklyRepository;

    @Inject
    private AttendanceTimeOfWeeklyRepository attendanceTimeOfWeeklyRepo;

    @Inject
    private ConvertFactory convertFactory;

    @Inject
    private IntegrationOfDailyGetter integrationOfDailyGetter;

    @Inject
    private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepo;

    @Inject
    private ErrorAlarmConditionRepository errorAlarmConditionRepo;

    @Inject
    private StampCardRepository stampCardRepo;

    @Inject
    private WorkplaceSpecificDateRepository workplaceSpecificDateRepo;

    @Inject
    private CompanySpecificDateRepository companySpecificDateRepo;

    @Inject
    private WorkplaceEventRepository workplaceEventRepo;

    @Inject
    private CompanyEventRepository companyEventRepo;

    @Inject
    private PublicHolidayRepository publicHolidayRepo;

    @Inject
    private SpecificDateItemRepository specificDateItemRepo;

    @Inject
    private EmpAffiliationInforAdapter empAffiliationInforAdapter;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private PredetemineTimeSettingRepository predetemineTimeSettingRepo;

    @Inject
    private FixedWorkSettingRepository fixedWorkSettingRepo;

    @Inject
    private FlexWorkSettingRepository flexWorkSettingRepo;

    @Inject
    private FlowWorkSettingRepository flowWorkSettingRepo;

    @Inject
    private AlarmListExtraProcessStatusRepository alarmListExtraProcessStatusRepo;

    @Inject
    private ExtraResultMonthlyRepository extraResultMonthlyRepo;

    @Inject
    private RecordDomRequireService requireService;

    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;

    @Inject
    private TempChildCareManagementRepository tempChildCareManagementRepo;

    @Inject
    private TempCareManagementRepository tempCareManagementRepo;

    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;

    @Inject
    private ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepo;

    @Inject
    private CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepo;

    @Inject
    private ChildCareUsedNumberRepository childCareUsedNumberRepo;

    @Inject
    private CareUsedNumberRepository careUsedNumberRepo;

    @Inject
    private ClosureStatusManagementRepository closureStatusManagementRepo;

    @Inject
    private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;

    @Inject
    private ClosureEmploymentRepository closureEmploymentRepo;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;

    @Inject
    private ClosureRepository closureRepo;

    @Inject
    private RCAnnualHolidayManagement annualHolidayManagement;

    @Inject
    private GrantYearHolidayRepository grantYearHolidayRepo;

    @Inject
    private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;

    @Inject
    private LengthServiceRepository lengthServiceRepo;

    @Inject
    private BPSettingRepository bPSettingRepo;

    @Inject
    private IdentificationRepository identificationRepo;

    @Inject
    private ApprovalRootStateStatusService approvalRootStateStatusService;

    @Inject
    private WorkingConditionItemRepository workingConditionItemRepo;

    @Inject
    private WorkLocationRepository workLocationRepo;

    @Inject
    private TaskingRepository taskingRepo;

    @Inject
    private CheckExistHolidayGrantAdapter checkExistHolidayGrantAdapter;

    @Inject
    private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepo;

    @Inject
    private CriterionAmountForCompanyRepository criterionAmountForCompanyRepo;

    @Inject
    private CriterionAmountForEmploymentRepository criterionAmountForEmploymentRepo;

    @Inject
    private HandlingOfCriterionAmountRepository handlingOfCriterionAmountRepo;

    @Inject
    private AgreementTimeOfManagePeriodRepository agreementTimeOfManagePeriodRepository;
    @Inject
    private AgreementYearSettingRepository agreementYearSettingRepository;
    @Inject
    private AgreementUnitSettingRepository agreementUnitSetRepo;
    
    @Inject
    private SyEmploymentAdapter syEmploymentAdapter;
    
    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;
    
    @Inject
    private AffClassificationAdapter affClassficationAdapter;
    
    @Inject
    private TaskFrameUsageSettingRepository taskFrameUsageSettingRepo;
    
    @Inject
    private ErrorAlarmAnyPeriodRepository errorAlarmAnyPeriodRepo;

    @Inject
    private AnnLeaveRemainNumberAdapter annLeaveRemainNumberAdapter;

    @Inject
    private ReserveLeaveManagerApdater reserveLeaveManagerApdater;

    @Inject
    private PayoutSubofHDManaRepository payoutSubofHDManaRepo;

    @Inject
    private InterimRecAbasMngRepository interimRecAbasMngRepo;

    @Inject
    private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;

    @Inject
    private PayoutManagementDataRepository payoutManagementDataRepo;

    @Inject
    private LeaveComDayOffManaRepository leaveComDayOffManaRepo;

    @Inject
    private EmpSubstVacationRepository empSubstVacationRepo;

    @Inject
    private ComSubstVacationRepository comSubstVacationRepo;

    @Inject
    private AgreementOperationSettingRepository agreementOperationSettingRepository;
    
    @Inject
    private Classification36AgreementTimeRepository classification36AgreementTimeRepository;
    
    @Inject
    private Workplace36AgreedHoursRepository workplace36AgreedHoursRepository;
    
    @Inject
    private Employment36HoursRepository employment36HoursRepository;

    @Inject
    private Company36AgreedHoursRepository company36AgreedHoursRepository;
    
    @Inject
    private VerticalTotalMethodOfMonthlyRepository verticalTotalMethodOfMonthlyRepository;
    
    @Inject
    private RetentionYearlySettingRepository retentionYearlySettingRepository;
    
    @Inject
    private WorkDaysNumberOnLeaveCountRepository workDaysNumberOnLeaveCountRepository;
    
    @Inject
    private HolidayAddtionRepository holidayAddtionRepository;
    
    @Inject
    private InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;

    @Inject
    private ComDayOffManaDataRepository comDayOffManaDataRepo;

    @Inject
    private LeaveManaDataRepository leaveManaDataRepo;

    @Inject
    private CompensLeaveEmSetRepository compensLeaveEmSetRepo;

    @Inject
    private CompensLeaveComSetRepository compensLeaveComSetRepo;

    @Inject
    private AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo;

    @Inject
    private AnnualLeaveMaxHistRepository annualLeaveMaxHistRepo;

    @Inject
    private AnnLeaMaxDataRepository annLeaMaxDataRepo;

    @Inject
    private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo;

    @Inject
    private TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepo;

    @Inject
    private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;

    @Inject
    private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;

    @Inject
    private PublicHolidaySettingRepository publicHolidaySettingRepo;

    @Inject
    private CompanyAdapter companyAdapter;

    @Inject
    private PublicHolidayManagementUsageUnitRepository publicHolidayManagementUsageUnitRepo;

    @Inject
    private SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;

    @Inject
    private CompanyMonthDaySettingRepository companyMonthDaySettingRepo;

    @Inject
    private EmployeeMonthDaySettingRepository employeeMonthDaySettingRepo;

    @Inject
    private EmploymentMonthDaySettingRepository employmentMonthDaySettingRepo;

    @Inject
    private PublicHolidayCarryForwardDataRepository publicHolidayCarryForwardDataRepo;

    @Inject
    private TempPublicHolidayManagementRepository tempPublicHolidayManagementRepo;

    @Inject
    private WorkplaceMonthDaySettingRepository workplaceMonthDaySettingRepo;

    @Inject
    private RervLeaGrantRemDataRepository rervLeaGrantRemDataRepository;
    
    @Inject
    private EmploymentSettingRepository employmentSettingRepository;
    
    @Inject
    private TmpResereLeaveMngRepository tmpResereLeaveMngRepository;
    
    @Inject
    private SysEmploymentHisAdapter sysEmploymentHisAdapter;
    
    public Require create() {
        return EmbedStopwatch.embed(new RequireImpl(
                AppContexts.user().contractCode(),
                AppContexts.user().companyId(),
                AppContexts.user().employeeId()));
    }

    public interface Require extends ExecuteAlarmListByEmployee.Require {

        void save(AlarmListExtractResult result);

        void save(ExtractEmployeeInfo employeeInfo);
    }

    @RequiredArgsConstructor
    public class RequireImpl implements Require, AgreementDomainService.RequireM5 {

        private final String companyId;
        private final String contractCode;
        private final String loginEmployeeId;

        //--- ログイン情報 ---//
        @Override
        public String getCompanyId() {
            return companyId;
        }

        @Override
        public String getLoginEmployeeId() {
            return loginEmployeeId;
        }

        //--- アラームリストの設定 ---//
        @Override
        public Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode) {
            return Optional.empty();
        }

        @Override
        public Optional<AlarmListCheckerByEmployee> getAlarmListChecker(AlarmListCategoryByEmployee category, AlarmListCheckerCode checkerCode) {
            return Optional.empty();
        }

        //--- アラームリストの抽出結果 ---//
        @Override
        public void save(AlarmListExtractResult result) {

        }

        @Override
        public void save(ExtractEmployeeInfo employeeInfo) {

        }

        @Override
        public void save(ExtractEmployeeErAlData alarm) {

        }

        //--- 個人情報系 ---//
        @Override
        public Optional<SyEmploymentImport> employment(String companyId, String employeeId, GeneralDate baseDate) {
            return syEmploymentAdapter.findByEmployeeId(companyId, employeeId, baseDate);
        }
        
        @Override
        public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyID, String employeeId, GeneralDate baseDate) {
            return Optional.empty();
        }

        @Override
        public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String s, List<String> list, GeneralDate generalDate) {
            return null;
        }

        @Override
        public List<String> getCanUseWorkplaceForEmp(String companyId, String employeeId, GeneralDate baseDate) {
            return affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(companyId, employeeId, baseDate);
        }
        
        @Override
        public Optional<AffClassificationSidImport> affEmployeeClassification(String companyId, String employeeId, GeneralDate baseDate) {
            return affClassficationAdapter.findByEmployeeId(companyId, employeeId, baseDate);
        }
        
        //--- 労働条件 ---//
        @Override
        public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
            return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
        }
        
        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate generalDate) {
            return workingConditionRepo.getWorkingConditionItemByEmpIDAndDate(this.companyId, generalDate, employeeId);
        }
        
        @Override
        public Optional<WorkingConditionItem> getWorkingConditionItem(String employeeId, GeneralDate date) {
            return this.workingConditionItem(employeeId, date);
        }

        @Override
        public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
            return workingConditionItemRepo.getByHistoryId(historyId);
        }

        @Override
        public List<WorkingConditionItem> workingConditionItemClones(List<String> employeeId, GeneralDate baseDate) {
            return workingConditionItemRepo.getByListSidAndStandardDate(employeeId, baseDate);
        }
        
        @Override
        public List<WorkingConditionItemWithPeriod> getWorkingConditions(String s, DatePeriod datePeriod) {
            return workingConditionRepo.getWorkingConditionItemWithPeriod(this.companyId, Arrays.asList(s), datePeriod);
        }
        
        @Override
        public List<WorkingConditionItemWithPeriod> getWorkingConditions(String employeeId, GeneralDate baseDate) {
            return this.getWorkingConditions(companyId, new DatePeriod(baseDate, baseDate));
        }
        
        //--- 勤務種類 ---//
        @Override
        public Optional<WorkType> getWorkType(String workTypeCode) {
            return this.workType(companyId, new WorkTypeCode(workTypeCode));
        }

        @Override
        public boolean existsWorkType(String workTypeCode) {
            return getWorkType(workTypeCode).isPresent();
        }

        @Override
        public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
            return getWorkType(workTypeCode.v());
        }

        @Override
        public Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfos(String s, DatePeriod datePeriod) {
            return null;
        }

        @Override
        public Optional<WorkType> workType(String cid, String workTypeCode) {
            return workTypeRepo.findByPK(cid, workTypeCode);
        }

        //--- 就業時間帯 ---//
        @Override
        public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
            return workTimeSettingRepo.findByCode(this.companyId, workTimeCode);
        }

        @Override
        public boolean existsWorkTime(String workTimeCode) {
            return getWorkTime(workTimeCode).isPresent();
        }

        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
        }

        @Override
        public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
            return predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
            return workTimeSettingRepo.findByCode(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
            return fixedWorkSettingRepo.findByKey(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
            return flexWorkSettingRepo.find(companyId, workTimeCode.toString());
        }

        @Override
        public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
            return flowWorkSettingRepo.find(companyId, workTimeCode.toString());
        }

        //--- 締め ---//
        @Override
        public List<Closure> closure(String companyId) {
            return closureRepo.findAll(companyId);
        }

        @Override
        public List<Closure> closureActive(String companyId, UseClassification useAtr) {
            return closureRepo.findAllActive(companyId, useAtr);
        }

        @Override
        public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
            return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
        }

        @Override
        public List<ClosureEmployment> employmentClosureClones(String s, List<String> list) {
            return null;
        }

        //--- 勤怠項目 ---//
        @Override
        public AttendanceItemConvertFactory getAttendanceItemConvertFactory() {
            // TODO
            return null;
        }

        @Override
        public String getDailyAttendanceItemName(Integer attendanceItemId) {
            return getAttendanceItemName(TypeOfItem.Daily, attendanceItemId);
        }

        @Override
        public String getMonthlyAttendanceItemName(Integer attendanceItemId) {
            return getAttendanceItemName(TypeOfItem.Monthly, attendanceItemId);
        }

        @Override
        public String getAttendanceItemName(TypeOfItem typeOfItem, int itemId) {
            return attendanceItemNameService.getNameOfAttendanceItem(Arrays.asList(itemId), typeOfItem)
                    .get(0).getAttendanceItemName();
        }

        //--- 勤務予定 ---//
        @Override
        public Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date) {
            return workScheduleRepo.get(employeeId, date);
        }

        @Override
        public boolean existsWorkSchedule(String employeeId, GeneralDate date) {
            return getWorkSchedule(employeeId, date).isPresent();
        }

        //--- 日別実績 ---//
        @Override
        public Optional<IntegrationOfDaily> getIntegrationOfDailyRecord(String employeeId, GeneralDate date) {
            return this.getIntegrationOfDailyRecords(employeeId, DatePeriod.oneDay(date))
                    .stream().findFirst();
        }

        @Override
        public Optional<Identification> getIdentification(String employeeId, GeneralDate date) {
            return identificationRepo.findByCode(employeeId, date);
        }

        //--- 見込み月次 ---//
        @Override
        public List<IntegrationOfDaily> getIntegrationOfDailyProspect(String employeeId, DatePeriod period) {
            return DailyAttendanceGettingService.get(
                    new DailyAttendanceGettingService.Require() {
                @Override
                public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> list, DatePeriod datePeriod) {
                    return workScheduleAdapter.getList(Arrays.asList(employeeId), period);
                }

                @Override
                public List<IntegrationOfDaily> getRecordList(List<EmployeeId> list, DatePeriod datePeriod) {
                    return dailyRecordAdapter.getDailyRecordByScheduleManagement(Arrays.asList(employeeId), period);
                }
            },
                    Arrays.asList(new EmployeeId(employeeId)),
                    period,
                    ScheRecGettingAtr.SCHEDULE_WITH_RECORD
            ).get(ScheRecGettingAtr.SCHEDULE_WITH_RECORD);
        }

        @Override
        public RecordDomRequireService requireService() {
            return requireService;
        }

        //--- 見込み年次 ---//
        @Override
        public List<IntegrationOfMonthly> getIntegrationOfMonthlyProspect(String employeeId, List<ClosureMonth> closureMonths) {
            return closureMonths.stream()
                .map(closureMonth -> integrationOfMonthlyGetter.get(
                        employeeId,
                        closureMonth.yearMonth(),
                        ClosureId.valueOf(closureMonth.closureId()),
                        closureMonth.closureDate()))
                .collect(Collectors.toList());
        }

        //--- 見込み共通 ---//
        @Override
        public Optional<CriterionAmountUsageSetting> getUsageSetting() {
            return criterionAmountUsageSettingRepo.get(companyId);
        }

        @Override
        public Optional<CriterionAmountForCompany> getCriterionAmountForCompany() {
            return criterionAmountForCompanyRepo.get(companyId);
        }

        @Override
        public Optional<CriterionAmountForEmployment> getCriterionAmountForEmployment(EmploymentCode employmentCode) {
            return criterionAmountForEmploymentRepo.get(companyId, employmentCode);
        }

        @Override
        public Optional<EmploymentPeriodImported> getEmploymentHistory(EmployeeId employeeId, GeneralDate generalDate) {
            return employmentHistory(new CacheCarrier(), companyId, employeeId.v(), generalDate).map(history ->
                    new EmploymentPeriodImported(
                            history.getEmployeeId(),
                            history.getPeriod(),
                            history.getEmploymentCode(),
                            Optional.empty())
            );
        }

        @Override
        public Optional<HandlingOfCriterionAmount> getHandling() {
            return handlingOfCriterionAmountRepo.get(companyId);
        }

        //--- 月別実績 ---//
        @Override
        public IntegrationOfMonthly getIntegrationOfMonthly(String employeeId, ClosureMonth closureMonth) {
            return integrationOfMonthlyGetter.get(employeeId, closureMonth.yearMonth(), ClosureId.valueOf(closureMonth.closureId()), closureMonth.closureDate());
        }

        @Override
        public List<IntegrationOfMonthly> getIntegrationOfMonthly(String employeeId, List<ClosureMonth> closureMonthes) {
            return closureMonthes.stream()
                    .map(closureMonth -> this.getIntegrationOfMonthly(employeeId, closureMonth))
                    .collect(Collectors.toList());
        }

        @Override
        public Optional<ConfirmationMonth> getConfirmationMonth(String employeeId, ClosureMonth closureMonth) {
            // TODO
            return Optional.empty();
        }

        @Override
        public ExtraResultMonthly getExtraResultMonthly(ErrorAlarmWorkRecordCode codes) {
            return null;
        }

        @Override
        public List<ExtraResultMonthly> getExtraResultMonthly(List<ErrorAlarmWorkRecordCode> codes) {
            return codes.stream()
                    .map(code -> this.getExtraResultMonthly(code))
                    .collect(Collectors.toList());
        }

        @Override
        public List<MonthlyRecordToAttendanceItemConverter> getMonthlyRecordToAttendanceItemConverter(List<IntegrationOfMonthly> monthlyRecords) {
            return monthlyRecords.stream()
                    .map(record -> MonthlyRecordToAttendanceItemConverterImpl.builder(optionalItemRepo).setData(record))
                    .collect(Collectors.toList());
        }

        @Override
        public List<ApproveRootStatusForEmpImport> getApprovalStateMonth(String employeeId, ClosureMonth closureMonth) {
            return approvalStatusAdapter.getApprovalByListEmplAndListApprovalRecordDateNew(
                    closureMonth.defaultPeriod().datesBetween(),
                    Arrays.asList(employeeId),
                    RecordRootType.CONFIRM_WORK_BY_MONTH.value);
        }

        //--- 週別実績 ---//
        @Override
        public List<ExtractionCondWeekly> getUsedExtractionCondWeekly(List<String> codes) {
            return extractionCondWeeklyRepository.getByCodes(companyId, ErAlCategory.WEEKLY.value, codes);
        }

        @Override
        public Iterable<AttendanceTimeOfWeekly> getAttendanceTimeOfWeekly(String employeeId, DatePeriod period) {
            return attendanceTimeOfWeeklyRepo.findMatchAnyOneDay(employeeId, period);
        }

        @Override
        public WeeklyRecordToAttendanceItemConverter createWeeklyConverter() {
            return convertFactory.createWeeklyConverter();
        }

        //--- 申請承認 ---//
        @Override
        public List<ApprovalRootStateStatus> getApprovalRootStateByPeriod(String employeeId, DatePeriod period) {
            return approvalRootStateStatusService.getStatusByEmpAndDate(employeeId, period.start(), period.end(), 1);
        }

        @Override
        public List<Application> getApplicationBy(String employeeId, GeneralDate targetDate, ReflectedState states) {
            return applicatoinRepo.getByListRefStatus(this.companyId, employeeId, targetDate, targetDate, Arrays.asList(states.value));
        }

        //--- エラーアラーム ---//
        @Override
        public Iterable<EmployeeDailyPerError> getEmployeeDailyPerErrors(String employeeId, DatePeriod period, List<ErrorAlarmWorkRecordCode> targetCodes) {
            return employeeDailyPerErrorRepo.findsByCodeLst(
                    Arrays.asList(employeeId),
                    period,
                    targetCodes.stream().map(t -> t.v()).collect(Collectors.toList()));
        }

        @Override
        public Optional<ErrorAlarmWorkRecord> getErrorAlarmWorkRecord(ErrorAlarmWorkRecordCode code) {
            // 会社ID渡してないからビビるけど、Repositoryの中でAppContextsから取得してる・・・
            return errorAlarmWorkRecordRepo.findByCode(code.v());
        }

        @Override
        public Optional<ErrorAlarmCondition> getErrorAlarmConditionById(String id) {
            return errorAlarmConditionRepo.findConditionByErrorAlamCheckId(id);
        }

        //--- 年休 ---//
        @Override
        public Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId) {
            return getBasicInfo(employeeId);
        }

        @Override
        public Optional<AnnualLeaveEmpBasicInfo> getBasicInfo(String employeeId) {
            return annLeaEmpBasicInfoRepo.get(employeeId);
        }

        @Override
        public Optional<GrantHdTblSet> getTable(String yearHolidayCode) {
            return grantHdTblSet(this.companyId, yearHolidayCode);
        }

        @Override
        public Optional<GrantHdTblSet> grantHdTblSet(String companyId, String yearHolidayCode) {
            return yearHolidayRepo.findByCode(companyId, yearHolidayCode);
        }

        @Override
        public Optional<LengthServiceTbl> lengthServiceTbl(String companyId, String yearHolidayCode) {
            return lengthServiceRepo.findByCode(companyId, yearHolidayCode);
        }

        @Override
        public Optional<GrantHdTbl> grantHdTbl(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
            return grantYearHolidayRepo.find(companyId, conditionNo, yearHolidayCode, grantNum);
        }

        @Override
        public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
            return annualPaidLeaveSettingRepo.findByCompanyId(companyId);
        }

        @Override
        public Optional<AttendRateAtNextHoliday> getDaysPerYear(String employeeId) {
            return annualHolidayManagement.getDaysPerYear(new CompanyId(this.companyId), new EmployeeId(employeeId));
        }

        @Override
        public Optional<AnnualLeaveGrantRemainingData> getLast(String employeeId){
            return annLeaGrantRemDataRepo.getLast(employeeId);
        }

        @Override
        public boolean checkExistHolidayGrantAdapter(String employeeId, GeneralDate date, Period period) {
            return checkExistHolidayGrantAdapter.checkExistHolidayGrantAdapter(employeeId, date, period);
        }

        @Override
        public List<AnnualLeaveGrantRemainingData> findAnnualLeaveGrantRemain(String employeeId, Optional<LeaveGrantNumber> grantDays) {
            if(grantDays.isPresent()){
                return annLeaGrantRemDataRepo.findMoreThan(employeeId, grantDays.get());
            }else {
                return annLeaGrantRemDataRepo.find(employeeId);
            }
        }

        //--- 年休残数 ---//

        @Override
        public List<AnnualLeaveRemainingHistory> annualLeaveRemainingHistory(String employeeId, YearMonth yearMonth) {
            return annualLeaveRemainHistRepo.getInfoBySidAndYM(employeeId, yearMonth);
        }

        @Override
        public Optional<AnnualLeaveMaxHistoryData> AnnualLeaveMaxHistoryData(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
            return annualLeaveMaxHistRepo.find(employeeId, yearMonth, closureId, closureDate);
        }

        @Override
        public Optional<AnnualLeaveMaxData> annualLeaveMaxData(String employeeId) {
            return annLeaMaxDataRepo.get(employeeId);
        }

        @Override
        public List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId) {
            return annLeaGrantRemDataRepo.find(employeeId);
        }

        @Override
        public Optional<OperationStartSetDailyPerform> dailyOperationStartSet(CompanyId companyId) {
            return operationStartSetDailyPerformRepo.findByCid(companyId);
        }

        @Override
        public List<TempAnnualLeaveMngs> tmpAnnualHolidayMng(String employeeId, DatePeriod datePeriod) {
            return tmpAnnualHolidayMngRepo.getBySidPeriod(employeeId, datePeriod);
        }

        @Override
        public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, DatePeriod datePeriod) {
            return attendanceTimeOfMonthlyRepo.findByPeriodIntoEndYmd(employeeId, datePeriod);
        }

        @Override
        public List<AnnLeaRemNumEachMonth> annLeaRemNumEachMonth(String employeeId, DatePeriod closurePeriod) {
            return annLeaRemNumEachMonthRepo.findByClosurePeriod(employeeId, closurePeriod);
        }

        @Override
        public Optional<Closure> closure(String companyId, int closureId) {
            return closureRepo.findById(companyId, closureId);
        }

        @Override
        public List<Closure> closureClones(String companyId, List<Integer> closureIds) {
            return closureRepo.findByListId(companyId, closureIds);
        }

        @Override
        public List<SharedSidPeriodDateEmploymentImport> employmentHistories(CacheCarrier cacheCarrier, List<String> employeeIds, DatePeriod datePeriod) {
            return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, employeeIds, datePeriod);
        }

        @Override
        public List<ClosureEmployment> employmentClosure(String companyId, List<String> employmentCDs) {
            return closureEmploymentRepo.findListEmployment(companyId, employmentCDs);
        }

        //--- 積立年休残数 ---//
        @Override
        public Optional<RsvLeaCriterialDate> getReserveLeaveRemain(String employeeId, GeneralDate date) {
            return reserveLeaveManagerApdater.getRsvLeaveManagerData(employeeId, date);
        }

        //--- 振休残数 ---//

        @Override
        public List<PayoutSubofHDManagement> getOccDigetByListSid(String employeeId, DatePeriod datePeriod) {
            return payoutSubofHDManaRepo.getOccDigetByListSid(employeeId, datePeriod);
        }

        @Override
        public List<InterimAbsMng> getAbsBySidDatePeriod(String employeeId, DatePeriod datePeriod) {
            return interimRecAbasMngRepo.getAbsBySidDatePeriod(employeeId, datePeriod);
        }

        @Override
        public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String employeeId) {
            return substitutionOfHDManaDataRepo.getBysiD(this.companyId, employeeId);
        }

        @Override
        public List<InterimRecMng> getRecBySidDatePeriod(String employeeId, DatePeriod datePeriod) {
            return interimRecAbasMngRepo.getRecBySidDatePeriod(employeeId, datePeriod);
        }

        @Override
        public List<PayoutManagementData> getPayoutMana(String employeeId) {
            return payoutManagementDataRepo.getSid(this.companyId, employeeId);
        }

        @Override
        public List<PayoutSubofHDManagement> getPayoutSubWithDateUse(String employeeId, GeneralDate dateOfUse, GeneralDate baseDate) {
            return payoutSubofHDManaRepo.getWithDateUse(employeeId, dateOfUse, baseDate);
        }

        @Override
        public List<PayoutSubofHDManagement> getPayoutSubWithOutbreakDay(String employeeId, GeneralDate outbreakDay, GeneralDate baseDate) {
            return payoutSubofHDManaRepo.getWithOutbreakDay(employeeId, outbreakDay, baseDate);
        }

        @Override
        public List<LeaveComDayOffManagement> getLeaveComWithDateUse(String employeeId, GeneralDate dateOfUse, GeneralDate baseDate) {
            return leaveComDayOffManaRepo.getLeaveComWithDateUse(employeeId, dateOfUse, baseDate);
        }

        @Override
        public List<LeaveComDayOffManagement> getLeaveComWithOutbreakDay(String employeeId, GeneralDate outbreakDay, GeneralDate baseDate) {
            return leaveComDayOffManaRepo.getLeaveComWithOutbreakDay(employeeId, outbreakDay, baseDate);
        }

        @Override
        public List<EmploymentHistShareImport> findByEmployeeIdOrderByStartDate(String employeeId) {
            return this.findByEmployeeIdOrderByStartDate(employeeId);
        }

        @Override
        public Optional<EmpSubstVacation> findEmpById(String companyId, String contractTypeCode) {
            return empSubstVacationRepo.findById(companyId, contractTypeCode);
        }

        @Override
        public Optional<ComSubstVacation> findComById(String companyId) {
            return comSubstVacationRepo.findById(companyId);
        }

        //--- 代休残数 ---//

        @Override
        public List<LeaveComDayOffManagement> getDigestOccByListComId(String employeeId, DatePeriod period) {
            return leaveComDayOffManaRepo.getDigestOccByListComId(employeeId, period);
        }

        @Override
        public List<InterimDayOffMng> getTempDayOffBySidPeriod(String employeeId, DatePeriod period) {
            return interimBreakDayOffMngRepo.getDayOffBySidPeriod(employeeId, period);
        }

        @Override
        public List<CompensatoryDayOffManaData> getFixByDayOffDatePeriod(String employeeId) {
            return comDayOffManaDataRepo.getBySid(this.companyId, employeeId);
        }

        @Override
        public List<InterimBreakMng> getTempBreakBySidPeriod(String employeeId, DatePeriod period) {
            return interimBreakDayOffMngRepo.getBySidPeriod(employeeId, period);
        }

        @Override
        public List<LeaveManagementData> getFixLeavByDayOffDatePeriod(String employeeId) {
            return leaveManaDataRepo.getBySid(this.companyId, employeeId);
        }

        @Override
        public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId, GeneralDate baseDate) {
            return shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, baseDate);
        }

        @Override
        public CompensatoryLeaveEmSetting findComLeavEmpSet(String companyId, String employmentCode) {
            return compensLeaveEmSetRepo.find(companyId, employmentCode);
        }

        @Override
        public CompensatoryLeaveComSetting findComLeavComSet(String companyId) {
            return compensLeaveComSetRepo.find(companyId);
        }

        //--- 公休残数 ---//

        @Override
        public Optional<PublicHolidaySetting> publicHolidaySetting(String companyId) {
            return publicHolidaySettingRepo.get(companyId);
        }

        @Override
        public Optional<YearMonthPeriod> getYearMonthPeriodByCalendarYearmonth(String companyId, YearMonth yearMonth) {
            return companyAdapter.getYearMonthPeriodByCalendarYearmonth(companyId, yearMonth);
        }

        @Override
        public Optional<PublicHolidayManagementUsageUnit> publicHolidayManagementUsageUnit(String companyId) {
            return publicHolidayManagementUsageUnitRepo.get(companyId);
        }

        @Override
        public Optional<PublicHolidayManagementUsageUnit> getPublicHolidayManagementUsageUnit(String companyId) {
            return this.publicHolidayManagementUsageUnit(companyId);
        }
        
        @Override
        public Optional<SharedAffWorkPlaceHisImport> getAffWorkPlaceHis(String employeeId, GeneralDate processingDate) {
            return sharedAffWorkPlaceHisAdapter.getAffWorkPlaceHis(employeeId, processingDate);
        }

        @Override
        public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId) {
            return affWorkplaceAdapter.getWorkplaceIdAndUpper(companyId, baseDate, workplaceId);
        }

        @Override
        public List<SharedSidPeriodDateEmploymentImport> getEmpHistBySidAndPeriod(List<String> employeeIds, DatePeriod datePeriod) {
            return shareEmploymentAdapter.getEmpHistBySidAndPeriod(employeeIds, datePeriod);
        }

        @Override
        public List<CompanyMonthDaySetting> getCompanyMonthDaySetting(String companyId, List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList) {
            return companyMonthDaySettingRepo.findByYears(new CompanyId(companyId), yearList);
        }

        @Override
        public List<EmployeeMonthDaySetting> getEmployeeMonthDaySetting(String companyId, String employeeId, List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList) {
            return employeeMonthDaySettingRepo.findByYears(new CompanyId(companyId), employeeId, yearList);
        }

        @Override
        public List<EmploymentMonthDaySetting> getEmploymentMonthDaySetting(String companyId, String employmentCode, List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList) {
            return employmentMonthDaySettingRepo.findByYears(new CompanyId(companyId), employmentCode, yearList);
        }

        @Override
        public Optional<PublicHolidayCarryForwardData> publicHolidayCarryForwardData(String employeeId) {
            return publicHolidayCarryForwardDataRepo.get(employeeId);
        }

        @Override
        public List<TempPublicHolidayManagement> tempPublicHolidayManagement(String employeeId, DatePeriod datePeriod) {
            return tempPublicHolidayManagementRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
        }

        @Override
        public List<WorkplaceMonthDaySetting> getWorkplaceMonthDaySetting(String companyId, String workplaceId, List<nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year> yearList) {
            return workplaceMonthDaySettingRepo.findByYears(new CompanyId(companyId), workplaceId, yearList);
        }

        @Override
        public List<InterimRecAbsMng> interimRecAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr) {
            return interimRecAbasMngRepo.getRecOrAbsMng(interimId, isRec, mngAtr);
        }

        //--- 子の看護介護休暇 ---//

        @Override
        public EmployeeImport findByEmpId(String employeeId) {
            return empEmployeeAdapter.findByEmpId(employeeId);
        }

        @Override
        public List<FamilyInfo> familyInfo(String employeeId) {
            //TODO 2021/03/22 時点では家族情報は取得できない
            return new ArrayList<>();
        }

        @Override
        public List<TempChildCareManagement> tempChildCareManagement(String employeeId, DatePeriod period) {
            return tempChildCareManagementRepo.findByPeriodOrderByYmd(employeeId, period);
        }

        @Override
        public List<TempCareManagement> tempCareManagement(String employeeId, DatePeriod period) {
            return tempCareManagementRepo.findByPeriodOrderByYmd(employeeId, period);
        }

        @Override
        public NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory) {
            return nursingLeaveSettingRepo.findByCompanyIdAndNursingCategory(companyId, nursingCategory.value);
        }

        @Override
        public Optional<NursingCareLeaveRemainingInfo> employeeInfo(String employeeId, NursingCategory nursingCategory) {
            if (nursingCategory.equals(NursingCategory.Nursing)) {
                return careLeaveEmployeeInfo(employeeId).map(mapper -> (NursingCareLeaveRemainingInfo) mapper);
            }
            if (nursingCategory.equals(NursingCategory.ChildNursing)) {
                return childCareLeaveEmployeeInfo(employeeId).map(mapper -> (NursingCareLeaveRemainingInfo) mapper);
            }
            return Optional.empty();
        }

        @Override
        public Optional<ChildCareLeaveRemainingInfo> childCareLeaveEmployeeInfo(String employeeId) {
            return childCareLeaveRemInfoRepo.getChildCareByEmpId(employeeId);
        }

        @Override
        public Optional<CareLeaveRemainingInfo> careLeaveEmployeeInfo(String employeeId) {
            return careLeaveRemainingInfoRepo.getCareByEmpId(employeeId);
        }

        @Override
        public Optional<ChildCareUsedNumberData> childCareUsedNumber(String employeeId) {
            return childCareUsedNumberRepo.find(employeeId);
        }

        @Override
        public Optional<CareUsedNumberData> careUsedNumber(String employeeId) {
            return careUsedNumberRepo.find(employeeId);
        }

        @Override
        public Optional<CareManagementDate> careData(String familyID) {
            //TODO 2021/03/22 時点では家族情報は取得できない
            return Optional.empty();
        }

        @Override
        public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
            return closureStatusManagementRepo.getLatestByEmpId(employeeId);
        }

        @Override
        public OptionLicense getOptionLicense() {
            return AppContexts.optionLicense();
        }

        @Override
        public List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier, List<String> employeeIds, DatePeriod datePeriod) {
            return this.employmentHistories(cacheCarrier, employeeIds, datePeriod);
        }

        @Override
        public EmployeeImport employee(CacheCarrier cacheCarrier, String employeeId) {
            return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, employeeId);
        }

        //--- 任意期間集計 ---//
        @Override
        public AttendanceTimeOfAnyPeriod getAttendanceTimeOfAnyPeriod(String employeeId, String anyPeriodFrameCode) {
            return attendanceTimeOfAnyPeriodRepo.find(employeeId, anyPeriodFrameCode).get();
        }

        @Override
        public List<IntegrationOfDaily> getIntegrationOfDailyRecords(String employeeId, DatePeriod period) {
            return integrationOfDailyGetter.getIntegrationOfDaily(employeeId, period);
        }

        @Override
        public AnyAggrPeriod getAnyAggrPeriod(AnyAggrFrameCode code) {
            return anyAggrPeriodRepo.findOneByCompanyIdAndFrameCode(companyId, code.v()).get();
        }

        @Override
        public List<ErrorAlarmAnyPeriod> getErrorAlarmAnyPeriod(List<ErrorAlarmWorkRecordCode> code) {
            return errorAlarmAnyPeriodRepo.gets(
            		this.companyId, 
            		ErAlCategory.ANY_PERIOD.value, 
            		code.stream().map(c -> c.v()).collect(Collectors.toList()));
        }

        @Override
        public AnyPeriodRecordToAttendanceItemConverter getAnyPeriodRecordToAttendanceItemConverter(
                String employeeId, AttendanceTimeOfAnyPeriod record) {
            return AnyPeriodRecordToAttendanceItemConverterImpl
                    .builder(optionalItemRepo)
                    .withBase(employeeId)
                    .withAttendanceTime(record)
                    .completed();
        }

        //--- 作業 ---//
        @Override
        public Optional<TaskAssignEmployee> getTaskAssign(String employeeId, TaskFrameNo frameNo) {
            return Optional.empty();
        }

        @Override
        public boolean existsTask(TaskFrameNo taskFrameNo, TaskCode code) {
            return false;
        }

        @Override
        public Optional<Task> getTask(TaskFrameNo taskFrameNo, WorkCode code) {
            return taskingRepo.getOptionalTask(companyId, taskFrameNo, new TaskCode(code.v()));
        }

        @Override
        public TaskFrameUsageSetting getTaskFrameUsageSetting() {
            return taskFrameUsageSettingRepo.getWorkFrameUsageSetting(companyId);
        }

        //--- 加給 ---//
        @Override
        public boolean existsBonusPay(BonusPaySettingCode code) {
            return bPSettingRepo.isExisted(companyId, code);
        }

        //================= 未分類の壁 =================//
        @Override
        public List<StampCard> getStampCard(String employeeId) {
            return stampCardRepo.getListStampCard(employeeId);
        }

        @Override
        public List<WorkLocation> getWorkLocation(String workLocationCode) {
            return null;
        }

        @Override
        public Optional<WorkplaceSpecificDateItem> getWorkplaceSpecByDate(String workplaceId, GeneralDate generalDate) {
            return workplaceSpecificDateRepo.get(workplaceId, generalDate);
        }

        @Override
        public Optional<CompanySpecificDateItem> getComSpecByDate(GeneralDate generalDate) {
            return companySpecificDateRepo.get(this.companyId, generalDate);
        }

        @Override
        public Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate generalDate) {
            return workplaceEventRepo.findByPK(workplaceId, generalDate);
        }

        @Override
        public Optional<CompanyEvent> findCompanyEventByPK(GeneralDate generalDate) {
            return companyEventRepo.findByPK(this.companyId, generalDate);
        }

        @Override
        public Optional<PublicHoliday> getHolidaysByDate(GeneralDate generalDate) {
            return publicHolidayRepo.getHolidaysByDate(this.companyId, generalDate);
        }

        @Override
        public List<SpecificDateItem> getSpecifiDateByListCode(List<SpecificDateItemNo> list) {
            return specificDateItemRepo.getSpecifiDateByListCode(this.companyId, list);
        }

        @Override
        public List<EmpOrganizationImport> getEmpOrganization(GeneralDate generalDate, List<String> lstEmpId) {
            return empAffiliationInforAdapter.getEmpOrganization(generalDate, lstEmpId);
        }

        @Override
        public boolean existWorkLocation(WorkLocationCD code) {
            return workLocationRepo.findByCode(contractCode, code.v()).isPresent();
        }

        @Override
        public Optional<AggregateMethodOfMonthly> getAggregateMethodOfMonthly(String cid) {
            return verticalTotalMethodOfMonthlyRepository.findByCid(cid);
        }

        @Override
        public Optional<RetentionYearlySetting> retentionYearlySetting(String companyId) {
            return retentionYearlySettingRepository.findByCompanyId(companyId);
        }

        @Override
        public WorkDaysNumberOnLeaveCount workDaysNumberOnLeaveCount(String cid) {
            return workDaysNumberOnLeaveCountRepository.findByCid(cid);
        }

        @Override
        public Optional<HolidayAddtionSet> holidayAddtionSet(String cid) {
            return holidayAddtionRepository.findByCId(cid);
        }

        @Override
        public Optional<AgreementTimeOfManagePeriod> getAgreementTimeOfManagePeriod(String employeeId, YearMonth yearMonth) {
            return agreementTimeOfManagePeriodRepository.find(employeeId, yearMonth);
        }

        @Override
        public Optional<AgreementOperationSetting> agreementOperationSetting(String cid) {
            return agreementOperationSettingRepository.find(cid);
        }

        @Override
        public BasicAgreementSettingForCalc basicAgreementSetting(String cid, String sid, GeneralDate baseDate, Year year) {
            return AgreementDomainService.getBasicSet(this, cid, sid, baseDate, year);
        }

        @Override
        public Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String sid, YearMonth ym) {
            return this.getAgreementTimeOfManagePeriod(sid, ym);
        }

        @Override
        public List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> sids, List<YearMonth> yearMonths) {
            return agreementTimeOfManagePeriodRepository.findBySidsAndYearMonths(sids, yearMonths);
        }

        @Override
        public BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate) {
            return AgreementDomainService.getBasicSet(this, cid, sid, baseDate);
        }

        @Override
        public Optional<AgreementYearSetting> agreementYearSetting(String sid, int year) {
            return agreementYearSettingRepository.findBySidAndYear(loginEmployeeId, year);
        }

        @Override
        public Optional<AgreementUnitSetting> agreementUnitSetting(String companyId) {
            return agreementUnitSetRepo.find(companyId);
        }


        @Override
        public Optional<AgreementTimeOfClassification> agreementTimeOfClassification(String companyId, LaborSystemtAtr laborSystemAtr, String classificationCode) {
            return classification36AgreementTimeRepository.getByCidAndClassificationCode(companyId, classificationCode, laborSystemAtr);
        }

        @Override
        public Optional<AgreementTimeOfWorkPlace> agreementTimeOfWorkPlace(String workplaceId, LaborSystemtAtr laborSystemAtr) {
            return workplace36AgreedHoursRepository.getByWorkplaceId(workplaceId, laborSystemAtr);
        }


        @Override
        public Optional<AgreementTimeOfEmployment> agreementTimeOfEmployment(String companyId, String employmentCategoryCode, LaborSystemtAtr laborSystemAtr) {
            return employment36HoursRepository.getByCidAndCd(companyId, employmentCategoryCode, laborSystemAtr);
        }

        @Override
        public Optional<AgreementTimeOfCompany> agreementTimeOfCompany(String companyId, LaborSystemtAtr laborSystemAtr) {
            return company36AgreedHoursRepository.getByCid(companyId, laborSystemAtr);
        }

        @Override
        public List<ReserveLeaveGrantRemainingData> reserveLeaveGrantRemainingData(String employeeId) {
            return rervLeaGrantRemDataRepository.find(employeeId);
        }

        @Override
        public Optional<EmptYearlyRetentionSetting> employmentYearlyRetentionSetting(String companyId, String employmentCode) {
            return employmentSettingRepository.find(companyId, employmentCode);
        }

        @Override
        public Optional<SEmpHistoryImport> employeeEmploymentHis(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate) {
            return sysEmploymentHisAdapter.findSEmpHistBySidRequire(cacheCarrier, companyId, employeeId, baseDate);
        }

        @Override
        public List<TmpResereLeaveMng> tmpResereLeaveMng(String sid, DatePeriod datePeriod) {
            return tmpResereLeaveMngRepository.findBySidPriod(sid, datePeriod);
        }
    }
}
