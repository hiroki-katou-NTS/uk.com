package nts.uk.ctx.at.shared.dom.employmentrules;

import lombok.AllArgsConstructor;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.*;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.workingcondition.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
	public void Check_isShortTimeWork_isTrue() {
		ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);
		new Expectations() {
			{
				require.GetShortWorkHistory("eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()));
				List<DateHistoryItem> historyItems = new ArrayList<>();
				GeneralDate today = GeneralDate.today();
				DatePeriod period1 = DatePeriod.oneDay(today.addDays(-1));
				DatePeriod period2 = DatePeriod.oneDay(today);
				historyItems.add(DateHistoryItem.createNewHistory(period1));
				historyItems.add(DateHistoryItem.createNewHistory(period2));
				result = Optional.of(new ShortWorkTimeHistory("cid","sid", historyItems));
			}
		};
		assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isTrue();
	}

	@Test
	public void Check_isConditionChanger_isTrue(@Mocked final AppContexts tr) {
		ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);
		WorkingConditionItemGetMemento memento = new WorkingConditionItemGetMementoImpl();
		WorkingConditionItem item1 = new WorkingConditionItem(memento);
		WorkingConditionItem item2 = new WorkingConditionItem(memento);

		new Expectations() {
			{
				require.GetHistoryItemByPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
				result = new ArrayList<WorkingConditionItem>(){{
					add(item1);
					add(item2);
				}};
			}
		};
		assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
	}

	@Test
	public void Check_isLeave_isTrue(@Mocked final AppContexts tr) {
		ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);

		LeavePeriod leavePeriod = new LeavePeriod();

		new Expectations() {
			{
				require.GetLeavePeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
				result = new ArrayList<LeavePeriod>(){{
					add(leavePeriod);
				}};
			}
		};
		assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
	}

	@Test
	public void Check_isLeave_isTrue_V1(@Mocked final AppContexts tr) {
		ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);

		LeaveHolidayPeriod leaveHolidayPeriod = new LeaveHolidayPeriod();

		new Expectations() {
			{
				require.GetLeaveHolidayPeriod(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
				result = new ArrayList<LeaveHolidayPeriod>(){{
					add(leaveHolidayPeriod);
				}};
			}
		};
		assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
	}

	@Test
	public void Check_isTransferPerson_isTrue(@Mocked final AppContexts tr) {
		ConditionEmployee conditionEmployee = new ConditionEmployee(true,true,true,true);
		AffiliationPeriodAndWorkplace item1 = new AffiliationPeriodAndWorkplace();
		AffiliationPeriodAndWorkplace item2 = new AffiliationPeriodAndWorkplace();
		WorkPlaceHist workPlaceHist = new WorkPlaceHist("eid",new ArrayList<AffiliationPeriodAndWorkplace>(){{
			add(item1);
			add(item2);
		}});

		new Expectations() {
			{
				require.GetWorkHistory(Arrays.asList("eid"),new DatePeriod(GeneralDate.min(), GeneralDate.max()));
				result = new ArrayList<WorkPlaceHist>(){{
					add(workPlaceHist);
				}};
			}
		};
		assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max())));
	}

	@Test
	public void Check_employeesIsEligible_isFalse() {
		ConditionEmployee conditionEmployee = new ConditionEmployee(false,false,false,false);
		assertThat(conditionEmployee.CheckEmployeesIsEligible(require,"eid",new DatePeriod(GeneralDate.min(), GeneralDate.max()))).isFalse();
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
			return Optional.empty();
		}

		@Override
		public Optional<BreakdownTimeDay> getHolidayAddTimeSet() {
			return Optional.empty();
		}

		@Override
		public Optional<BonusPaySettingCode> getTimeApply() {
			return Optional.empty();
		}

		@Override
		public Optional<nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode> getMonthlyPattern() {
			return Optional.empty();
		}
	}

	class PersonalWorkCategoryGetMementoImpl implements PersonalWorkCategoryGetMemento {

		@Override
		public SingleDaySchedule getHolidayWork() {
			return null;
		}

		@Override
		public SingleDaySchedule getHolidayTime() {
			return null;
		}

		@Override
		public SingleDaySchedule getWeekdayTime() {
			return null;
		}

		@Override
		public Optional<SingleDaySchedule> getPublicHolidayWork() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getInLawBreakTime() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getOutsideLawBreakTime() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getHolidayAttendanceTime() {
			return Optional.empty();
		}
	}

	class PersonalDayOfWeekGetMementoImpl implements PersonalDayOfWeekGetMemento{

		@Override
		public Optional<SingleDaySchedule> getSaturday() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getSunday() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getMonday() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getThursday() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getWednesday() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getTuesday() {
			return Optional.empty();
		}

		@Override
		public Optional<SingleDaySchedule> getFriday() {
			return Optional.empty();
		}
	}
}
