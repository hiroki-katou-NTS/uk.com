package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.GettingTimeVacactionService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttdTest;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
@RunWith(JMockit.class)
public class IntegrationOfDailyTest {

	@Injectable
	TimeLeavingOfDailyAttd timeLeaving;

	@Injectable
	AttendanceTimeOfDailyAttendance attendanceTime;

	@Injectable
	OutingTimeOfDailyAttd outingTime;

	@SuppressWarnings("unchecked")
	@Test
	public void testGetTimeVacation_success(
			@Injectable Map<TimezoneToUseHourlyHoliday, TimeVacation> timeVacations) {
		
		new Expectations( GettingTimeVacactionService.class ) {
			{
				GettingTimeVacactionService.get(( Optional<TimeLeavingOfDailyAttd> ) any
						, (Optional<AttendanceTimeOfDailyAttendance>) any
						, (Optional<OutingTimeOfDailyAttd>) any);
				result = timeVacations;
				
			}
		};

		// Arrange
		IntegrationOfDaily  target = Helper.createWithParams(
				Optional.of(timeLeaving),
				Optional.of(attendanceTime),
				Optional.of(outingTime));

		// Action
		Map<TimezoneToUseHourlyHoliday, TimeVacation> result = target.getTimeVacation();

		// Assert
		assertThat(result).isEqualTo(timeVacations);
	}
	
	/**
	 * target: getSupportInfoOfEmployee
	 * pattern: 応援しない社員
	 */
	@Test
	public void testGetSupportInfoOfEmployee_not_support() {
		
		val affiliationInfor = AffiliationInforOfDailyAttdTest.Helper.createAffiliationInforOfDailyAttd("workplaceID", Optional.empty() );
		//応援しない社員
		val dailyRecord = Helper.createIntegrationOfDaily(	
															"sid"
														,	GeneralDate.ymd( 2022, 2, 24 )
														,	affiliationInfor
														,	Collections.emptyList()//応援時間がない
														,	Collections.emptyList()//dummy
															);
		//act
		val result = dailyRecord.getSupportInfoOfEmployee();
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 2, 24 ) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceID" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType() ).isEmpty();
		assertThat( result.getRecipientList() ).isEmpty();
	}
	
	/**
	 * target: getSupportInfoOfEmployee
	 * pattern: 終日で応援する社員
	 */
	@Test
	public void testGetSupportInfoOfEmployee_support_all_day(
			@Injectable OuenWorkTimeOfDailyAttendance ouenTime
			) {
		val affiliationInfor = AffiliationInforOfDailyAttdTest.Helper.createAffiliationInforOfDailyAttd("workplaceID", Optional.empty() );
			
		val dailyWork = Helper.createIntegrationOfDaily(
					"sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	affiliationInfor//職場
				,	Arrays.asList( ouenTime )
				,	Arrays.asList(
							Helper.createOuenWorkTimeSheet("recipient_1", Optional.empty(), SupportType.ALLDAY )
							)
					);
		
		//act
		val result = dailyWork.getSupportInfoOfEmployee();
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 2, 25 ) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceID" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.ALLDAY );
		assertThat( result.getRecipientList() )
		.extracting(	r -> r.getUnit()
					,	r -> r.getTargetId())
		.containsExactly( 
						tuple(	TargetOrganizationUnit.WORKPLACE, "recipient_1" )
				);
	}

	/**
	 * target: getSupportInfoOfEmployee
	 * pattern: 時間帯で応援する社員
	 */
	@Test
	public void testGetSupportInfoOfEmployee_support_time_zone(
			@Injectable OuenWorkTimeOfDailyAttendance ouenTime
			) {
		
		val affiliationInfor = AffiliationInforOfDailyAttdTest.Helper.createAffiliationInforOfDailyAttd("workplaceID", Optional.empty() );
		
		val dailyWork = Helper.createIntegrationOfDaily(
					"sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	affiliationInfor//職場
				,	Arrays.asList( ouenTime )
				,	Arrays.asList(
							Helper.createOuenWorkTimeSheet("recipient_1", Optional.empty(), SupportType.TIMEZONE )
						,	Helper.createOuenWorkTimeSheet("recipient_2", Optional.empty(), SupportType.TIMEZONE )
							)
					);
		
		//act
		val result = dailyWork.getSupportInfoOfEmployee();
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 2, 25 ) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceID" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.TIMEZONE );
		assertThat( result.getRecipientList() )
		.extracting(	r -> r.getUnit()
					,	r -> r.getTargetId())
		.containsExactly( 
						tuple(	TargetOrganizationUnit.WORKPLACE, "recipient_1" )
					,	tuple(	TargetOrganizationUnit.WORKPLACE, "recipient_2" )
				);
	}
	
	public static class Helper{
		
		@Injectable
		private static TimeSheetOfAttendanceEachOuenSheet timeSheet;

		private static WorkInfoOfDailyAttendance defaultWorkInfo = new WorkInfoOfDailyAttendance(
				new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001")),
				CalculationState.No_Calculated,
				NotUseAttribute.Not_use,
				NotUseAttribute.Not_use,
				DayOfWeek.MONDAY,
				Collections.emptyList(),
				Optional.empty());

		private static AffiliationInforOfDailyAttd defaultAffInfo = new AffiliationInforOfDailyAttd(
				new EmploymentCode("EmpCode-001"),
				"JobTitle-Id-001",
				"Wpl-Id-001",
				new ClassificationCode("class-001"),
				Optional.empty(),
				Optional.empty()
				, Optional.empty()
				, Optional.empty()
				, Optional.empty()
				);

		public static IntegrationOfDaily createWithParams(
				Optional<TimeLeavingOfDailyAttd> optTimeLeaving,
				Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime,
				Optional<OutingTimeOfDailyAttd> outingTime) {
			return new IntegrationOfDaily(
					  "sid"
					, GeneralDate.today()
					, defaultWorkInfo
					, CalAttrOfDailyAttd.createAllCalculate()
					, defaultAffInfo
					, Optional.empty()
					, Collections.emptyList()
					, outingTime
					, new BreakTimeOfDailyAttd()
					, optAttendanceTime
					, optTimeLeaving
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, Collections.emptyList()
					, Collections.emptyList()
					, Collections.emptyList()
					, Optional.empty());
		}
		/**
		 * 応援別勤務の勤務先を作る
		 * @param workplaceId　職場ID
		 * @param workplaceGroupId 職場グループID
		 * @param supportType 応援形式
		 * @return
		 */
		public static OuenWorkTimeSheetOfDailyAttendance createOuenWorkTimeSheet(
				String workplaceId,
				Optional<String> workplaceGroupId,
				SupportType supportType ) {
				return new OuenWorkTimeSheetOfDailyAttendance( new SupportFrameNo( 1 )
						,	supportType
						,	WorkContent.create( 
									new WorkplaceOfWorkEachOuen( new WorkplaceId( workplaceId ), Optional.empty(), workplaceGroupId )
								,	Optional.empty()
								,	Optional.empty() )
						,	timeSheet
						,	Optional.empty()
							);
		}
		/**
		 * 日別勤怠(Work)を作る
		 * @param ouenTime 応援時間
		 * @param ouenTimeSheet 応援時間帯
		 * @return
		 */
		public static IntegrationOfDaily createIntegrationOfDaily(
					String sid
				,	GeneralDate ymd
				,	AffiliationInforOfDailyAttd affiliationInfor
				,	List<OuenWorkTimeOfDailyAttendance> ouenTime
				,	List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet ) {
			
			return new IntegrationOfDaily(
						sid
					,	ymd
					,	defaultWorkInfo
					,	CalAttrOfDailyAttd.createAllCalculate()
					,	affiliationInfor
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	new BreakTimeOfDailyAttd()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	Collections.emptyList()
					,	ouenTime
					,	ouenTimeSheet
					,	Optional.empty());
		}

	}
}
