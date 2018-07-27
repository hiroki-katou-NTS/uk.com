package nts.uk.ctx.at.shared.dom.statutory.worktime;

import java.util.Optional;

import org.junit.Test;

import junit.framework.Assert;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.shared.WeekStart;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public class UsageUnitSettingTest {

	@Test
	public void test_getDailyUnit() {

//		CompanyId cID = new CompanyId("");
//
//		EmployeeId eID = new EmployeeId("");
//
//		WorkplaceId wId = new WorkplaceId("");
//
//		EmploymentCode eCode = new EmploymentCode("");
//
//		WeeklyUnit weeklyUnit = new WeeklyUnit(new WeeklyTime(0), WeekStart.Saturday);
//
//		/* 労働時間と日数の設定の利用単位の設定 */
//		UsageUnitSetting usageUnitSetting = new UsageUnitSetting(cID, false, false, false);
//
//		/* 社員別通常勤務労働時間 */
//		ShainRegularLaborTime shainRegularLaborTime = new ShainRegularLaborTime(cID, eID,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(480))));
//		/* 社員別変形労働労働時間 */
//		ShainTransLaborTime shainTransLaborTime = new ShainTransLaborTime(cID, eID,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(485))));
//		/* 職場別通常勤務労働時間 */
//		WkpRegularLaborTime wkpRegularLaborTime = new WkpRegularLaborTime(cID, wId,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(490))));
//		/* 職場別変形労働労働時間 */
//		WkpTransLaborTime wkpTransLaborTime = new WkpTransLaborTime(cID, wId,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(495))));
//		/* 雇用別通常勤務労働時間 */
//		EmpRegularLaborTime empRegularLaborTime = new EmpRegularLaborTime(cID, eCode,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(500))));
//		/* 雇用別変形労働労働時間 */
//		EmpTransLaborTime empTransLaborTime = new EmpTransLaborTime(cID, eCode,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(505))));
//		/* 会社別通常勤務労働時間 */
//		ComRegularLaborTime comRegularLaborTime = new ComRegularLaborTime(cID,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(510))));
//		/* 会社別変形労働労働時間 */
//		ComTransLaborTime comTransLaborTime = new ComTransLaborTime(cID,
//				new WorkingTimeSetting(weeklyUnit, new DailyUnit(new TimeOfDay(515))));
//
//		DailyUnit dailyUnit = usageUnitSetting.getDailyUnit(WorkingSystem.REGULAR_WORK,
//															Optional.of(shainRegularLaborTime),
//															//Optional.empty(),
//															Optional.of(shainTransLaborTime),
//															//Optional.empty(),
//															Optional.of(wkpRegularLaborTime),
//															Optional.of(wkpTransLaborTime),
//															Optional.of(empRegularLaborTime),
//															Optional.of(empTransLaborTime),
//															Optional.of(comRegularLaborTime),
//															Optional.of(comTransLaborTime));
//		
//		DailyUnit dailyUnit = usageUnitSetting.getDailyUnit(WorkingSystem.REGULAR_WORK,
//															Optional.empty(),
//															Optional.empty(),
//															Optional.empty(),
//															Optional.empty(),
//															Optional.empty(),
//															Optional.empty(),
//															Optional.empty(),
//															Optional.empty());
//		
//
//		Assert.assertEquals(dailyUnit.getDailyTime().valueAsMinutes(), 510);

	}

}
