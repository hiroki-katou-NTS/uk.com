package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.GetSupportInfoOfEmployeeFromSupportableEmployee;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
		val workSchedule = Helper.createWorkSchedule( "sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	Helper.createAffiliationInforOfDailyAttd( "workplaceId", Optional.empty() )//職場
				,	Arrays.asList(
							Helper.createSupportScheduleDetailByTimeZone( recipient1 )//応援形式 = 時間帯
						,	Helper.createSupportScheduleDetailByTimeZone( recipient2 )
							)
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
		
		val dailyWork = Helper.createIntegrationOfDaily(
					"sid"//社員ID
				,	GeneralDate.ymd( 2022, 02, 25)//年月日
				,	Helper.createAffiliationInforOfDailyAttd( "affiliation", Optional.empty() )//職場
				,	Arrays.asList( ouenTime )
				,	Arrays.asList(
							Helper.createOuenWorkTimeSheetOfDailyAttendance( "recipient_1" )
						,	Helper.createOuenWorkTimeSheetOfDailyAttendance( "recipient_2" )
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
	
	public static class Helper{
		
		@Injectable
		private static TaskSchedule taskSchedule;
		
		@Injectable
		private static BreakTimeOfDailyAttd lstBreakTime;
		
		@Injectable
		private static WorkInfoOfDailyAttendance workInfo;
		
		@Injectable
		private static TimeSheetOfAttendanceEachOuenSheet timeSheet;
		
		/**
		 * 日別勤怠の所属情報を作る
		 * @param wplID 職場ID
		 * @param workplaceGroupId 職場グループID
		 * @return
		 */
		public static AffiliationInforOfDailyAttd createAffiliationInforOfDailyAttd( String wplID, Optional<String> workplaceGroupId ) {
			
			val domain = new AffiliationInforOfDailyAttd();
			
			domain.setWplID(wplID);
			domain.setWorkplaceGroupId(workplaceGroupId);
			
			return domain;
		}
		/**
		 * 時間帯で応援予定詳細を作る
		 * @param supportDestination 応援先
		 * @return
		 */
		public static SupportScheduleDetail createSupportScheduleDetailByTimeZone(
				TargetOrgIdenInfor supportDestination) {
			
			return new SupportScheduleDetail(	
						supportDestination
					,	SupportType.TIMEZONE
					,	Optional.of(	
							new TimeSpanForCalc(	TimeWithDayAttr.hourMinute(9, 0)
												,	TimeWithDayAttr.hourMinute(12, 0))
							)
						);
		}
		
		/**
		 * 終日で応援予定詳細を作る
		 * @param supportType 応援形式
		 * @param timeSpan 時間帯
		 * @return
		 */
		public static SupportScheduleDetail createSupportScheduleDetailByAllDay(
				TargetOrgIdenInfor supportDestination) {
			return new SupportScheduleDetail( supportDestination, SupportType.ALLDAY, Optional.empty() );
		}
		
		/**
		 * 時間帯で日別勤怠の応援作業時間帯を作る
		 * @param workplaceId 職場ID
		 * @return
		 */
		public static OuenWorkTimeSheetOfDailyAttendance createOuenWorkTimeSheetOfDailyAttendance(
				String workplaceId
				) {
			return OuenWorkTimeSheetOfDailyAttendance.create( new SupportFrameNo( 1 )
					,	WorkContent.create( 
								new WorkplaceOfWorkEachOuen( new WorkplaceId( workplaceId ), Optional.empty(), Optional.empty() )
							,	Optional.empty()
							,	Optional.empty() )
					,	timeSheet
					,	Optional.empty()
						);
		}
		
		/**
		 * 勤務予定を作る
		 * @param sid 社員ID
		 * @param ymd 年月日
		 * @param affInfo 確定区分
		 * @param details 詳細リスト
		 * @return
		 */
		public static WorkSchedule createWorkSchedule(
				String sid
			,	GeneralDate ymd
			,	AffiliationInforOfDailyAttd affInfo
			,	List<SupportScheduleDetail> details
			) {
			
			return new WorkSchedule(
						sid
					,	ymd
					,	ConfirmedATR.CONFIRMED
					,	workInfo
					,	affInfo
					,	lstBreakTime
					,	Collections.emptyList()
					,	taskSchedule
					,	new SupportSchedule( details )
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
					,	Optional.empty()
						);
		}
		
		/**
		 * 日別勤怠(Work)を作る
		 * @param sid 社員ID
		 * @param ymd 年月日
		 * @param affInfo 日別勤怠の所属情報
		 * @param ouenTime 応援時間
		 * @param ouenTimeSheet 応援時間帯
		 * @return
		 */
		public static IntegrationOfDaily createIntegrationOfDaily(
				String sid
			,	GeneralDate ymd
			,	AffiliationInforOfDailyAttd affInfo
			,	List<OuenWorkTimeOfDailyAttendance> ouenTime
			,	List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet
				) {
			return new IntegrationOfDaily(
						"sid"
					,	ymd
					,	workInfo
					,	CalAttrOfDailyAttd.createAllCalculate()
					,	affInfo
					,	Optional.empty()
					,	Collections.emptyList()
					,	Optional.empty()
					,	 new BreakTimeOfDailyAttd()
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
