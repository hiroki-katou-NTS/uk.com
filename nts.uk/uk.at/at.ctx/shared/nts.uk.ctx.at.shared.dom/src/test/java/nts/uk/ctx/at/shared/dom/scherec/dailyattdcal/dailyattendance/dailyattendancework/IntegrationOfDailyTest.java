package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.GettingTimeVacactionService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimeVacation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttdHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.OuenWorkTimeSheetOfDailyAttendanceHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
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
		IntegrationOfDaily  target = IntegrationOfDailyHelper.createWithParams(
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
		
		val affiliationInfor = AffiliationInforOfDailyAttdHelper.createAffiliationInforOfDailyAttd("workplaceID", Optional.empty() );
		//応援しない社員
		val dailyRecord = IntegrationOfDailyHelper.createIntegrationOfDaily(	
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
		val affiliationInfor = AffiliationInforOfDailyAttdHelper.createAffiliationInforOfDailyAttd("workplaceID", Optional.empty() );
			
		val dailyWork = IntegrationOfDailyHelper.createIntegrationOfDaily(
					"sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	affiliationInfor//職場
				,	Arrays.asList( ouenTime )
				,	Arrays.asList(
						OuenWorkTimeSheetOfDailyAttendanceHelper.createOuenWorkTimeSheet("recipient_1", Optional.empty(), SupportType.ALLDAY )
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
		
		val affiliationInfor = AffiliationInforOfDailyAttdHelper.createAffiliationInforOfDailyAttd("workplaceID", Optional.empty() );
		
		val dailyWork = IntegrationOfDailyHelper.createIntegrationOfDaily(
					"sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	affiliationInfor//職場
				,	Arrays.asList( ouenTime )
				,	Arrays.asList(
							OuenWorkTimeSheetOfDailyAttendanceHelper.createOuenWorkTimeSheet("recipient_1", Optional.empty(), SupportType.TIMEZONE )
						,	OuenWorkTimeSheetOfDailyAttendanceHelper.createOuenWorkTimeSheet("recipient_2", Optional.empty(), SupportType.TIMEZONE )
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
	
}
