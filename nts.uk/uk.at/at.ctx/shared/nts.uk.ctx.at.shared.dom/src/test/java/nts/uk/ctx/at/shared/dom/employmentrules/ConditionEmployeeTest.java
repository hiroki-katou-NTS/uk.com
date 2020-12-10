package nts.uk.ctx.at.shared.dom.employmentrules;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.AllArgsConstructor;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.AffiliationPeriodAndWorkplace;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.ConditionEmployee;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeaveHolidayPeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeavePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.WorkPlaceHist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.HourlyPaymentAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeekGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.history.DateHistoryItem;

@RunWith(JMockit.class)
public class ConditionEmployeeTest {

	@Injectable
	private ConditionEmployee.Require require;


	@Test
	public void getters() {
		ConditionEmployee workInformation = new ConditionEmployee(true,true,true,true);
		NtsAssert.invokeGetters(workInformation);
	}

	@Test
	public void checkEmployeesAreEligible_FFTF_R1NotEmpty_True() {
		val conditionEmployee = new ConditionEmployee(false, false, true, false);
		new Expectations() {{
				// R1
				require.GetShortWorkHistory("eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()));

				List<DateHistoryItem> historyItems = new ArrayList<>();
				GeneralDate today = GeneralDate.today();
				DatePeriod period1 = DatePeriod.oneDay(today.addDays(-1));
				DatePeriod period2 = DatePeriod.oneDay(today);
				historyItems.add(DateHistoryItem.createNewHistory(period1));
				historyItems.add(DateHistoryItem.createNewHistory(period2));

				result = Optional.of(new ShortWorkTimeHistory("cid","sid", historyItems));
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
	}

	@Test
	public void checkEmployeesAreEligible_FFTF_R1Empty_False() {
		val conditionEmployee = new ConditionEmployee(false, false, true, false);
		new Expectations() {{
			// R1
			require.GetShortWorkHistory("eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = Optional.empty();
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
	}

	@Test
	public void checkEmployeesAreEligible_FFFT_R2Has2OrMore_True() {
		val conditionEmployee = new ConditionEmployee(false, false, false, true);
		new Expectations() {{
			// R2
			require.GetHistoryItemByPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<WorkingConditionItem>(){{
				WorkingConditionItemGetMemento memento = new WorkingConditionItemGetMementoImpl();
				WorkingConditionItem item1 = new WorkingConditionItem(memento);
				WorkingConditionItem item2 = new WorkingConditionItem(memento);
				add(item1);
				add(item2);
			}};
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
	}

	@Test
	public void checkEmployeesAreEligible_FFFT_R2HasLessThan2_False() {
		val conditionEmployee = new ConditionEmployee(false, false, false, true);
		new Expectations() {{
			// R2
			require.GetHistoryItemByPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<WorkingConditionItem>(){{
				val memento = new WorkingConditionItemGetMementoImpl();
				val item1 = new WorkingConditionItem(memento);
				add(item1);
			}};
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
	}

	@Test
	public void checkEmployeesAreEligible_FTFF_R3NotEmpty_R4NotEmpty_True() {
		val conditionEmployee = new ConditionEmployee(false, true, false, false);

		new Expectations() {{
			// R3
			require.GetLeavePeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<LeavePeriod>(){{
				val leavePeriod = new LeavePeriod(new DatePeriod(GeneralDate.min(), GeneralDate.max()),"sid");
				add(leavePeriod);
			}};

			// R4
			require.GetLeaveHolidayPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<LeaveHolidayPeriod>(){{
				val leaveHolidayPeriod = new LeaveHolidayPeriod(DatePeriod.oneDay(GeneralDate.today()), "sid", 1);
				add(leaveHolidayPeriod);
			}};
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
	}

	@Test
	public void checkEmployeesAreEligible_FTFF_R3NotEmpty_R4Empty_True() {
		val conditionEmployee = new ConditionEmployee(false, true, false, false);

		new Expectations() {{
			// R3
			require.GetLeavePeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<LeavePeriod>(){{
				val leavePeriod = new LeavePeriod(new DatePeriod(GeneralDate.min(), GeneralDate.max()), "sid");
				add(leavePeriod);
			}};

			// R4
			require.GetLeaveHolidayPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = Collections.emptyList();
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
	}

	@Test
	public void checkEmployeesAreEligible_FTFF_R3Empty_R4NotEmpty_True() {
		val conditionEmployee = new ConditionEmployee(false, true, false, false);
		new Expectations() {{
			// R3
			require.GetLeavePeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
		    result = Collections.emptyList();

		    // R4
			require.GetLeaveHolidayPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<LeaveHolidayPeriod>(){{
				val leaveHolidayPeriod = new LeaveHolidayPeriod(DatePeriod.oneDay(GeneralDate.today()), "sid", 1);
				add(leaveHolidayPeriod);
			}};
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
	}

	@Test
	public void checkEmployeesAreEligible_FTFF_R3Empty_R4Empty_False() {
		val conditionEmployee = new ConditionEmployee(false, true, false, false);
		new Expectations() {{
			// R3
			require.GetLeavePeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = Collections.emptyList();

			// R4
			require.GetLeaveHolidayPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = Collections.emptyList();
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
	}

	@Test
	public void checkEmployeesAreEligible_TFFF_R5Has2OrMore_True() {
		val conditionEmployee = new ConditionEmployee(true, false, false, false);

		val datePeriod = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		val datePeriod1 = new DatePeriod(GeneralDate.ymd(2020,1,1),GeneralDate.ymd(2020,2,2));
		val workPlaceId = IdentifierUtil.randomUniqueId();
		val datePeriod2 = new DatePeriod(GeneralDate.ymd(2020,2,3),GeneralDate.max());
		val workPlaceId2 = IdentifierUtil.randomUniqueId();

		new Expectations() {{
			// R5
			require.GetWorkHistory(Arrays.asList("eid"),datePeriod);
			result = new ArrayList<WorkPlaceHist>(){{
				val item1 = new AffiliationPeriodAndWorkplace(datePeriod1,workPlaceId);
				val item2 = new AffiliationPeriodAndWorkplace(datePeriod2,workPlaceId2);
				val workPlaceHist = new WorkPlaceHist("eid",new ArrayList<AffiliationPeriodAndWorkplace>(){{
					add(item1);
					add(item2);
				}});
				add(workPlaceHist);
			}};
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
	}

	@Test
	public void checkEmployeesAreEligible_TFFF_R5HasLessThan2_False() {
		val conditionEmployee = new ConditionEmployee(true, false, false, false);

		val datePeriod1 = new DatePeriod(GeneralDate.ymd(2020,2,3),GeneralDate.max());
		val workPlaceId1 = IdentifierUtil.randomUniqueId();

		new Expectations() {{
			// R5
			require.GetWorkHistory(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<WorkPlaceHist>(){{
				val item1 = new AffiliationPeriodAndWorkplace(datePeriod1,workPlaceId1);
				val workPlaceHist = new WorkPlaceHist("eid",new ArrayList<AffiliationPeriodAndWorkplace>(){{
					add(item1);
				}});
				add(workPlaceHist);
			}};
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
	}

	@Test
	public void checkEmployeesAreEligible_FFFF_False() {
		val conditionEmployee = new ConditionEmployee(false, false, false, false);

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
	}

	@Test
	public void checkEmployeesAreEligible_TTTT_R1R3R4EmptyR2R5LessThan2_False() {
		val conditionEmployee = new ConditionEmployee(true, true, true, true);
		
		new Expectations() {{
			// R1
			require.GetShortWorkHistory("eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = Optional.empty();

			// R2
			require.GetHistoryItemByPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<WorkingConditionItem>(){{
				val memento = new WorkingConditionItemGetMementoImpl();
				val item1 = new WorkingConditionItem(memento);
				add(item1);
			}};
			
			// R3
			require.GetLeavePeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
		    result = Collections.emptyList();
			
			// R4
			require.GetLeaveHolidayPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = Collections.emptyList();
			
			// R5
			require.GetWorkHistory(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
			result = new ArrayList<WorkPlaceHist>(){{
				val item1 = new AffiliationPeriodAndWorkplace();
				val workPlaceHist = new WorkPlaceHist("eid",new ArrayList<AffiliationPeriodAndWorkplace>(){{
					add(item1);
				}});
				add(workPlaceHist);
			}};
		}};

		assertThat(conditionEmployee.CheckEmployeesAreEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
	}

	@AllArgsConstructor
	class WorkingConditionItemGetMementoImpl implements WorkingConditionItemGetMemento {

		@Override
		public HourlyPaymentAtr getHourlyPaymentAtr() {
			return HourlyPaymentAtr.HOURLY_PAY;
		}

		@Override
		public ManageAtr getScheduleManagementAtr() {
			return ManageAtr.USE;
		}

		@Override
		public NotUseAtr getVacationAddedTimeAtr() {
			return NotUseAtr.USE;
		}

		@Override
		public WorkingSystem getLaborSystem() {
			return WorkingSystem.REGULAR_WORK;
		}

		@Override
		public PersonalWorkCategory getWorkCategory() {
			PersonalWorkCategoryGetMemento memento = new PersonalWorkCategoryGetMementoImpl();
			return new PersonalWorkCategory(memento);
		}

		@Override
		public LaborContractTime getContractTime() {
			return new LaborContractTime(0);
		}

		@Override
		public NotUseAtr getAutoIntervalSetAtr() {
			return NotUseAtr.USE;
		}

		@Override
		public String getHistoryId() {
			return "HistoryId";
		}

		@Override
		public PersonalDayOfWeek getWorkDayOfWeek() {
			PersonalDayOfWeekGetMemento memento = new PersonalDayOfWeekGetMementoImpl();
			return new PersonalDayOfWeek(memento);
		}

		@Override
		public String getEmployeeId() {
			return "EmployeeId";
		}

		@Override
		public NotUseAtr getAutoStampSetAtr() {
			return NotUseAtr.USE;
		}

		@Override
		public Optional<ScheduleMethod> getScheduleMethod() {
			return Optional.of(new ScheduleMethod(
					0,
					new WorkScheduleBusCal(
							WorkScheduleMasterReferenceAtr.CLASSIFICATION,
							WorkScheduleMasterReferenceAtr.WORK_PLACE,
							TimeZoneScheduledMasterAtr.FOLLOW_MASTER_REFERENCE),
					new MonthlyPatternWorkScheduleCre(1)));
		}

		@Override
		public Optional<BreakdownTimeDay> getHolidayAddTimeSet() {
			return Optional.of( new BreakdownTimeDay(
					new AttendanceTime(280),
					new AttendanceTime(240),
					new AttendanceTime(240)
			));
		}

		@Override
		public Optional<BonusPaySettingCode> getTimeApply() {
			return Optional.of(new BonusPaySettingCode("dummySingleDaySchedule"));
		}

		@Override
		public Optional<nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode> getMonthlyPattern() {
			return Optional.of( new MonthlyPatternCode("dummySingleDaySchedule"));
		}
	}

	private SingleDaySchedule dummySingleDaySchedule = new SingleDaySchedule(
			"workTypeCode",
			new ArrayList<TimeZone>(){{
				add(new TimeZone(NotUseAtr.USE, 1, 2, 3));
				add(new TimeZone(NotUseAtr.USE, 4, 5, 6));
			}},
			Optional.of("workTimeCode")
	);

	class PersonalWorkCategoryGetMementoImpl implements PersonalWorkCategoryGetMemento {

		@Override
		public SingleDaySchedule getHolidayWork() {
			return dummySingleDaySchedule;
		}

		@Override
		public SingleDaySchedule getHolidayTime() {
			return dummySingleDaySchedule;
		}

		@Override
		public SingleDaySchedule getWeekdayTime() {
			return dummySingleDaySchedule;
		}

		@Override
		public Optional<SingleDaySchedule> getPublicHolidayWork() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getInLawBreakTime() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getOutsideLawBreakTime() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getHolidayAttendanceTime() {
			return Optional.of(dummySingleDaySchedule);
		}
	}

	class PersonalDayOfWeekGetMementoImpl implements PersonalDayOfWeekGetMemento{

		@Override
		public Optional<SingleDaySchedule> getSaturday() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getSunday() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getMonday() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getThursday() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getWednesday() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getTuesday() {
			return Optional.of(dummySingleDaySchedule);
		}

		@Override
		public Optional<SingleDaySchedule> getFriday() {
			return Optional.of(dummySingleDaySchedule);
		}
	}
}
