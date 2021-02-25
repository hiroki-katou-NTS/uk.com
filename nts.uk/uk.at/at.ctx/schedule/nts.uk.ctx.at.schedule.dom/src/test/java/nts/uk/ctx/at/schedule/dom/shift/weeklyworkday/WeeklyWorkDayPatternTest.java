package nts.uk.ctx.at.schedule.dom.shift.weeklyworkday;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WorkdayPatternItem;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class WeeklyWorkDayPatternTest {

	@Test
	public void create_over7day() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));

		NtsAssert.systemError(() -> {
			WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);
		});
	}

	@Test
	public void create_overlap() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.WORKINGDAYS));
		NtsAssert.systemError(() -> {
			WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);
		});
	}
	@Test
	public void create_success() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val result = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		assertThat(result.getCompanyId()).isEqualByComparingTo(companyId);
		assertThat(result.getListWorkdayPatternItem().size()).isEqualTo(7);
		assertThat(result.getListWorkdayPatternItem().get(0)).isEqualTo(listWorkdayPatternItem.get(0));
		assertThat(result.getListWorkdayPatternItem().get(1)).isEqualTo(listWorkdayPatternItem.get(1));
		assertThat(result.getListWorkdayPatternItem().get(2)).isEqualTo(listWorkdayPatternItem.get(2));
		assertThat(result.getListWorkdayPatternItem().get(3)).isEqualTo(listWorkdayPatternItem.get(3));
		assertThat(result.getListWorkdayPatternItem().get(4)).isEqualTo(listWorkdayPatternItem.get(4));
		assertThat(result.getListWorkdayPatternItem().get(5)).isEqualTo(listWorkdayPatternItem.get(5));
		assertThat(result.getListWorkdayPatternItem().get(6)).isEqualTo(listWorkdayPatternItem.get(6));

	}

	@Test
	public void test_getWorkingDayCtgOfTagertDay_01() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val weeklyWorkDayPattern = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		val result = weeklyWorkDayPattern.getWorkingDayCtgOfTagertDay(GeneralDate.ymd(2020,8,22));

		Assertions.assertThat(result).isEqualTo(WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL);
	}

	@Test
	public void test_getWorkingDayCtgOfTagertDay_02() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val weeklyWorkDayPattern = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		val result = weeklyWorkDayPattern.getWorkingDayCtgOfTagertDay(GeneralDate.ymd(2020,8,23));

		Assertions.assertThat(result).isEqualTo(WorkdayDivision.NON_WORKINGDAY_INLAW);
	}

	@Test
	public void test_getWorkingDayCtgOfTagertDay_03() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val weeklyWorkDayPattern = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		val result = weeklyWorkDayPattern.getWorkingDayCtgOfTagertDay(GeneralDate.ymd(2020,8,24));

		Assertions.assertThat(result).isEqualTo(WorkdayDivision.WORKINGDAYS);
	}

	@Test
	public void test_getWorkingDayCtgOfTagertDay_04() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val weeklyWorkDayPattern = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		val result = weeklyWorkDayPattern.getWorkingDayCtgOfTagertDay(GeneralDate.ymd(2020,8,25));

		Assertions.assertThat(result).isEqualTo(WorkdayDivision.WORKINGDAYS);
	}

	@Test
	public void test_getWorkingDayCtgOfTagertDay_05() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val weeklyWorkDayPattern = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		val result = weeklyWorkDayPattern.getWorkingDayCtgOfTagertDay(GeneralDate.ymd(2020,8,26));

		Assertions.assertThat(result).isEqualTo(WorkdayDivision.WORKINGDAYS);
	}

	@Test
	public void test_getWorkingDayCtgOfTagertDay_06() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val weeklyWorkDayPattern = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		val result = weeklyWorkDayPattern.getWorkingDayCtgOfTagertDay(GeneralDate.ymd(2020,8,27));

		Assertions.assertThat(result).isEqualTo(WorkdayDivision.WORKINGDAYS);
	}

	@Test
	public void test_getWorkingDayCtgOfTagertDay_07() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val weeklyWorkDayPattern = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		val result = weeklyWorkDayPattern.getWorkingDayCtgOfTagertDay(GeneralDate.ymd(2020,8,28));

		Assertions.assertThat(result).isEqualTo(WorkdayDivision.WORKINGDAYS);
	}

	@Test
	public void getters() {
		CompanyId companyId = new CompanyId("CID");
		List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
		listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
		val result = WeeklyWorkDayPattern.weeklyWorkDayPattern(companyId, listWorkdayPatternItem);

		WeeklyWorkDayPattern target = WeeklyWorkDayPattern.weeklyWorkDayPattern(
				companyId,listWorkdayPatternItem);

		NtsAssert.invokeGetters(target);
	}
}