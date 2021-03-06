package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.GetLegalWorkTimeOfEmployeeService.Require;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonAndWeekStatutoryTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyFlexStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternCode;
import nts.uk.ctx.at.shared.dom.workingcondition.MonthlyPatternWorkScheduleCre;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZoneScheduledMasterAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBusCal;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleMasterReferenceAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkTypeByIndividualWorkDay;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;	
/**
 * UnitTest: ??????????????????????????????????????????
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class GetLegalWorkTimeOfEmployeeServiceTest {
	@Injectable
	private Require require;
	
	/**
	 * ?????? = empty
	 * ?????? = empty
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_employement_empty() {		
		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
			}
		};
		
		val legalWorkTimeOfEmployee = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
				, new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(legalWorkTimeOfEmployee).isEmpty();
	}
	
	/**
	 * ?????????not empty,  ??????????????????????????? = empty
	 * ?????? = empty
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_empty() {
		val employeementHists = Helper.createEmployments();
		
		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
				result = employeementHists;
				
				require.getHistoryItemBySidAndBaseDate((String) any, (GeneralDate) any);
			}
		};
		
		val legalWorkTimeOfEmployee = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
				, new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(legalWorkTimeOfEmployee).isEmpty();
	}
	
	
	/**
	 * ?????????not empty,  ??????????????????????????? not empty
	 * ????????? == ?????????????????????
	 * ?????? = empty
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_empty_1() {
		val employeementHists = Helper.createEmployments();
		val itemHistory = Helper.createItemHistory(WorkingSystem.EXCLUDED_WORKING_CALCULATE);

		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
				result = employeementHists;
				
				require.getHistoryItemBySidAndBaseDate((String) any, (GeneralDate) any);
				result = Optional.of(itemHistory);
			}
		};
		
		val legalWorkTimeOfEmployee = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
				, new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(legalWorkTimeOfEmployee).isEmpty();
	}
	/**
	 * ?????????not empty,  ??????????????????????????? not empty
	 * ????????? == ??????????????????????????? FLEX_TIME_WORK
	 * ??????:
	 * ???????????? = ???????????????.?????????????????? 
	 * ???????????? = empty
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_flexStatutoryTime_statutorySetting() {
		val employeementHists = Helper.createEmployments();
		val itemHistory = Helper.createItemHistory(WorkingSystem.FLEX_TIME_WORK);
		val flexMonAndWeek = new MonthlyFlexStatutoryLaborTime(new MonthlyEstimateTime(4800)
				, new MonthlyEstimateTime(5000)
				, new MonthlyEstimateTime(5100));
		
		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
				result = employeementHists;
				
				require.getHistoryItemBySidAndBaseDate((String) any, (GeneralDate) any);
				result = Optional.of(itemHistory);
				
				require.flexMonAndWeekStatutoryTime((YearMonth) any, (String) any, (String) any, (GeneralDate) any);
				result = flexMonAndWeek;
			}
		};
		
		
		val actual = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
				   , new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(actual).isPresent();
		assertThat(actual.get().getSid()).isEqualTo("sid");
		assertThat(actual.get().getMonthlyEstimateTime()).isEqualTo(flexMonAndWeek.getStatutorySetting());
		assertThat(actual.get().getWeeklyEstimateTime()).isEmpty();
	}
	
	/**
	 * ?????????not empty,  ??????????????????????????? not empty
	 * ????????? == ????????????
	 * ???????????? monAndWeek= empty
	 * ??????: empty
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_regularWork_empty() {
		val employeementHists = Helper.createEmployments();
		val itemHistory = Helper.createItemHistory(WorkingSystem.REGULAR_WORK);
		
		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
				result = employeementHists;
				
				require.getHistoryItemBySidAndBaseDate((String) any, (GeneralDate) any);
				result = Optional.of(itemHistory);
				
				require.monAndWeekStatutoryTime( (YearMonth) any, (String) any, (String) any, (GeneralDate) any, (WorkingSystem) any);
			}
		};
		
		val actual = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
				    , new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(actual).isEmpty();
	}
	
	/**
	 * ?????????not empty,  ??????????????????????????? not empty
	 * ????????? == ????????????
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_regularWork_not_empty() {
		val employeementHists = Helper.createEmployments();
		val itemHistory = Helper.createItemHistory(WorkingSystem.REGULAR_WORK);
		val monAndWeek = new MonAndWeekStatutoryTime(new MonthlyEstimateTime(4000), new MonthlyEstimateTime(16000));
		
		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
				result = employeementHists;
				
				require.getHistoryItemBySidAndBaseDate((String) any, (GeneralDate) any);
				result = Optional.of(itemHistory);
				
				require.monAndWeekStatutoryTime((YearMonth) any, (String) any, (String) any
						, (GeneralDate) any, (WorkingSystem) any);
				result = Optional.of(monAndWeek);
			}
		};
		
		val actual = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
			    , new DatePeriod( GeneralDate.ymd(2018, 10, 10)
			    		        , GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(actual).isPresent();
		assertThat(actual.get().getSid()).isEqualTo("sid");
		assertThat(actual.get().getMonthlyEstimateTime()).isEqualTo(monAndWeek.getMonthlyEstimateTime());
		assertThat(actual.get().getWeeklyEstimateTime()).isPresent();
		assertThat(actual.get().getWeeklyEstimateTime().get()).isEqualTo(monAndWeek.getWeeklyEstimateTime());
	}
	
	/**
	 * ?????????not empty,  ??????????????????????????? not empty
	 * ????????? == ????????????????????????
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_variable_empty() {
		val employeementHists = Helper.createEmployments();
		val itemHistory = Helper.createItemHistory(WorkingSystem.VARIABLE_WORKING_TIME_WORK);
		
		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
				result = employeementHists;
				
				require.getHistoryItemBySidAndBaseDate((String) any, (GeneralDate) any);
				result = Optional.of(itemHistory);
				
				require.monAndWeekStatutoryTime((YearMonth) any, (String) any, (String) any
						, (GeneralDate) any, (WorkingSystem) any);
			}
		};
		
		val actual = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
				, new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(actual).isEmpty();
	}
	
	/**
	 * ?????????not empty,  ??????????????????????????? not empty
	 * ????????? == ????????????????????????
	 */
	@Test
	public void getLegalWorkTimeOfEmployee_variable_not_empty() {
		val employeementHists = Helper.createEmployments();
		val itemHistory = Helper.createItemHistory(WorkingSystem.VARIABLE_WORKING_TIME_WORK);
		val monAndWeek = new MonAndWeekStatutoryTime(new MonthlyEstimateTime(4000), new MonthlyEstimateTime(16000));
		
		new Expectations() {
			{
				require.getEmploymentHistories((String) any, (DatePeriod) any);
				result = employeementHists;
				
				require.getHistoryItemBySidAndBaseDate((String) any, (GeneralDate) any);
				result = Optional.of(itemHistory);
				
				require.monAndWeekStatutoryTime((YearMonth) any, (String) any, (String) any
						, (GeneralDate) any, (WorkingSystem) any);
				result = Optional.of(monAndWeek);
			}
		};
		
		val actual = GetLegalWorkTimeOfEmployeeService.get(require, "sid"
			        , new DatePeriod( GeneralDate.ymd(2018, 10, 10)
			    		            , GeneralDate.ymd(2019, 10, 10)));
		
		assertThat(actual.get().getSid()).isEqualTo("sid");
		assertThat(actual.get().getMonthlyEstimateTime()).isEqualTo(monAndWeek.getMonthlyEstimateTime());
		assertThat(actual.get().getWeeklyEstimateTime().get()).isEqualTo(monAndWeek.getWeeklyEstimateTime());
	}
	
	public static class Helper{
		
		@Injectable
		private static PersonalWorkCategory perCate;
		
		@Injectable
		private static PersonalDayOfWeek perDay;
		
		@Injectable
		private static BreakDownTimeDay holidayAddTimeSet;
		
		@Injectable
		private static ScheduleMethod scheduleMethod;
		
		@Injectable
		private static BonusPaySettingCode timeApply;
		
		@Injectable
		private static MonthlyPatternCode monthlyPattern;		
		/**
		 * create WorkingConditionItem
		 * @param workingSystem
		 * @return
		 */
		public  static WorkingConditionItem createItemHistory(WorkingSystem workingSystem) {
			val perDay = new PersonalDayOfWeek(Optional.empty(), Optional.empty(), Optional.empty()
					, Optional.empty(), Optional.empty(), Optional.empty()
					, Optional.empty());

			val perCate = new PersonalWorkCategory(
					new SingleDaySchedule(Collections.emptyList(), Optional.empty())
					, new SingleDaySchedule(Collections.emptyList(), Optional.empty())
					, perDay
					);
			val workTypeByIndividualWorkDay = new WorkTypeByIndividualWorkDay( new WorkTypeCode("001WC"), new WorkTypeCode("002WC"), new WorkTypeCode("003WC"), Optional.empty(), Optional.empty(), Optional.empty());
			val workByIndividualWorkDay =new nts.uk.ctx.at.shared.dom.workingcondition.WorkByIndividualWorkDay(perCate, workTypeByIndividualWorkDay);
			val holidayAddTimeSet = new BreakDownTimeDay(new AttendanceTime(120), new AttendanceTime(30), new AttendanceTime(30));
			val workScheduleBusCal = new WorkScheduleBusCal(
					WorkScheduleMasterReferenceAtr.WORK_PLACE
					,TimeZoneScheduledMasterAtr.PERSONAL_DAY_OF_WEEK);
			val monthlyPatter = new MonthlyPatternWorkScheduleCre(0);
			val scheduleMethod =  new ScheduleMethod(0, workScheduleBusCal, monthlyPatter);
			val timeApply = new BonusPaySettingCode("001");
			val monthlyPattern = new MonthlyPatternCode("001");
			return new WorkingConditionItem(
					"historyId", ManageAtr.USE, workByIndividualWorkDay
					, NotUseAtr.USE, NotUseAtr.USE, "sid", NotUseAtr.USE
					, new LaborContractTime(3200)
					, workingSystem
					, holidayAddTimeSet, scheduleMethod, new Integer(0)
					, timeApply, monthlyPattern
					);
		}
		
		public static List<EmploymentPeriodImported> createEmployments(){
			return Arrays.asList(
					  new EmploymentPeriodImported("sid"
							  , new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10))	
							  , "employment_code1"
							  , Optional.empty())
					, new EmploymentPeriodImported("sid"
						    , new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10))	
							, "employment_code2", Optional.empty())
					, new EmploymentPeriodImported("sid"
						    , new DatePeriod(GeneralDate.ymd(2018, 10, 10), GeneralDate.ymd(2019, 10, 10))	
							, "employment_code3"
							, Optional.empty())
					);
		}	
	}


}