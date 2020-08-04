package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require;

import nts.uk.ctx.at.shared.dom.outsideot.service.OutsideOTSettingService;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

public interface RemainNumberTempRequireService extends InterimRemainOffPeriodCreateData.RequireM4,
		BreakDayOffMngInPeriodQuery.RequireM10, AbsenceReruitmentMngInPeriodQuery.RequireM10,
		SpecialLeaveManagementService.RequireM5, GetClosureStartForEmployee.RequireM1, ClosureService.RequireM3,
		OutsideOTSettingService.RequireM2, OutsideOTSettingService.RequireM1, AbsenceTenProcess.RequireM1,
		AbsenceTenProcess.RequireM2, AbsenceTenProcess.RequireM4, AbsenceTenProcess.RequireM3,
		AbsenceReruitmentMngInPeriodQuery.RequireM2, WorkingConditionService.RequireM1 {

	public RemainNumberTempRequireService createRequire();
}
