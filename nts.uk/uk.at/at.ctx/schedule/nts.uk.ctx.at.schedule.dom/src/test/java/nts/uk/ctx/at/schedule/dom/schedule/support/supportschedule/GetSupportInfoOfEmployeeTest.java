package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.share.AffiliationInforOfDailyAttdHelperInSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.share.IntegrationOfDailyHelperInSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.share.OuenWorkTimeSheetOfDailyAttendanceHelperInSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleHelper;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.GetSupportInfoOfEmployeeFromSupportableEmployee;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * 社員の応援情報を取得するのUTコード
 * @author lan_lt
 */
public class GetSupportInfoOfEmployeeTest {
	
	@Injectable
	GetSupportInfoOfEmployee.Require require;
	
	/**
	 * target: getScheduleInfo
	 * pattern: 予定がある
	 */
	@Test
	public void testGetScheduleInfo_case_have_schedule() {
		
		val recipient1 = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_1" );
		val recipient2 = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_2" );
		val workSchedule = WorkScheduleHelper.createWorkSchedule( "sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	AffiliationInforOfDailyAttdHelperInSchedule.createAffiliationInforOfDailyAttd( "workplaceId", Optional.empty() )//職場
				,	new SupportSchedule (
						Arrays.asList(
							SupportScheduleDetailHelper.createSupportScheduleDetailByTimeZone( recipient1 )//応援形式 = 時間帯
						,	SupportScheduleDetailHelper.createSupportScheduleDetailByTimeZone( recipient2 )
							))
					);
		
		new Expectations() {
			{
				require.getWorkSchedule( (String) any, (GeneralDate) any );
				result = Optional.of( workSchedule );
			}
		};
		
		//act
		val result = GetSupportInfoOfEmployee.getScheduleInfo(require, new EmployeeId( "sid" ), GeneralDate.ymd( 2022, 02, 25) );

		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 02, 25) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceId" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.TIMEZONE );
		assertThat( result.getRecipientList() )
			.extracting(	r -> r.getUnit()
						,	r -> r.getTargetId())
			.containsExactly( 
							tuple(	TargetOrganizationUnit.WORKPLACE, "workplace_1" )
						,	tuple(	TargetOrganizationUnit.WORKPLACE, "workplace_2" )
				);
	}
	
	/**
	 * target: getScheduleInfo
	 * pattern: 予定がない
	 */
	@Test
	public void testGetScheduleInfo_case_havent_schedule() {
		
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" );
		val recipient = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( "workplaceGroupId" );
		
		//終日で社員応援
		val supportInfoOfEmp = SupportInfoOfEmployee.createWithAllDaySupport(
					new EmployeeId("sid")
				,	GeneralDate.ymd( 2022, 02, 25)
				,	affiliationOrg
				,	recipient);
		
		new Expectations( GetSupportInfoOfEmployeeFromSupportableEmployee.class ) {
			{
				require.getWorkSchedule( (String) any, (GeneralDate) any );
				
				GetSupportInfoOfEmployeeFromSupportableEmployee.get(require, (EmployeeId) any , (GeneralDate) any );
				result = supportInfoOfEmp;
			}
		};
		
		//act
		val result = GetSupportInfoOfEmployee.getScheduleInfo(require, new EmployeeId( "sid" ), GeneralDate.ymd( 2022, 02, 25) );
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 02, 25) );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "workplaceId" );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.ALLDAY );
		assertThat( result.getRecipientList() )
				.extracting(	r -> r.getUnit()
							,	r -> r.getTargetId())
				.containsExactly( tuple( TargetOrganizationUnit.WORKPLACE_GROUP, "workplaceGroupId" ) );
	}
	
	/**
	 * target: getRecordInfo
	 */
	@Test
	public void testGetRecordInfo_have_record(
			@Injectable OuenWorkTimeOfDailyAttendance ouenTime
			) {
		
		val dailyWork = IntegrationOfDailyHelperInSchedule.createIntegrationOfDaily(
					"sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	AffiliationInforOfDailyAttdHelperInSchedule.createAffiliationInforOfDailyAttd( "affiliation", Optional.empty() )//職場
				,	Arrays.asList( ouenTime )
				,	Arrays.asList(
							OuenWorkTimeSheetOfDailyAttendanceHelperInSchedule.createOuenWorkTimeSheetOfDailyAttendance( "recipient_1" )
						,	OuenWorkTimeSheetOfDailyAttendanceHelperInSchedule.createOuenWorkTimeSheetOfDailyAttendance( "recipient_2" )
							)
					);
		
		new Expectations() {
			{
				require.getRecord( (String) any, (GeneralDate) any );
				result = Optional.of( dailyWork );
			}
		};
		
		//act
		val result = GetSupportInfoOfEmployee.getRecordInfo(require, new EmployeeId( "sid" ), GeneralDate.ymd( 2022, 02, 25) );

		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 02, 25) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "affiliation" );
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
	
	/**
	 * target: getRecordInfo
	 */
	@Test
	public void testGetRecordInfo_havent_record() {
		
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "affiliation" );
		
		new Expectations( GetTargetIdentifiInforService.class ) {
			{
				require.getRecord( (String) any, (GeneralDate) any );
				
				GetTargetIdentifiInforService.get( require, (GeneralDate) any , (String) any );
				result = affiliationOrg;
			}
		};
		
		//act
		val result = GetSupportInfoOfEmployee.getRecordInfo(require, new EmployeeId( "sid" ), GeneralDate.ymd( 2022, 02, 25) );

		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022, 02, 25) );
		assertThat( result.getAffiliationOrg().getTargetId() ).isEqualTo( "affiliation" );
		assertThat( result.getAffiliationOrg().getUnit() ).isEqualTo( TargetOrganizationUnit.WORKPLACE );
		assertThat( result.getSupportType() ).isEmpty();
		assertThat( result.getRecipientList() ).isEmpty();
	}
	
}
