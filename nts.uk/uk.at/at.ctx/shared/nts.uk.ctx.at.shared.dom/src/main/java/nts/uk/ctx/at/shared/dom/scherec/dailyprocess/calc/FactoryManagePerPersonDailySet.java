package nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.RefDesForAdditionalTakeLeave;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.FlexWithinWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.export.GetFlexAggrSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.DailyStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CheckDateForManageCmpLeaveService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * ファクトリー：個人設定管理
 * @author kazuki_watanabe
 */
public interface FactoryManagePerPersonDailySet {
	
	Optional<ManagePerPersonDailySet> create(String companyId, ManagePerCompanySet companySetting, IntegrationOfDaily daily, WorkingConditionItem conditionItem);
	
	Optional<ManagePerPersonDailySet> create(String companyId, String sid, GeneralDate ymd, IntegrationOfDaily daily);
	
	static interface Require extends
		DailyStatutoryLaborTime.RequireM1,
		RefDesForAdditionalTakeLeave.Require,
		CheckDateForManageCmpLeaveService.Require,
		OverTimeSheet.Require,
		FlexWithinWorkTimeSheet.Require,
		WorkInformation.RequireM1,
		GetFlexAggrSet.RequireM1 { }
}
