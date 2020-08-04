package nts.uk.ctx.at.record.dom.require;

import nts.uk.ctx.at.record.dom.byperiod.MonthlyCalculationByPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgeementTimeCommonSettingService;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreTimeByPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementPeriod;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.cancelactuallock.CancelActualLock;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.logprocess.MonthlyClosureUpdateLogProcess;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.monthlyupdatemgr.MonthlyUpdateMgr;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.ymupdate.ProcessYearMonthUpdate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.GetClosurePeriod;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationService;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AggregateSpecifiedDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.AggregateMonthlyRecordService;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CalcAnnLeaAttendanceRate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CreateTempAnnLeaMngProc;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.outsideot.service.OutsideOTSettingService;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.CalcNextAnnLeaGrantInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrantProcKdm002;

public interface RecordDomRequireService
		extends InterimRemainOffPeriodCreateData.RequireM4, BreakDayOffMngInPeriodQuery.RequireM10,
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

	public RecordDomRequireService createRequire();

}
