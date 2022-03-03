package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;
/**
 * 応援可能な社員から社員の応援情報を取得するのUTコード
 * @author lan_lt
 *
 */

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class GetSupportInfoOfEmployeeFromSupportableEmployeeTest {
	
	@Injectable 
	GetSupportInfoOfEmployeeFromSupportableEmployee.Require require;
	
	/**
	 * target: get
	 * pattern: 応援しない社員
	 */
	@Test
	public void testGet_case_do_not_support( @Injectable TargetOrgIdenInfor affiliationOrg ) {
		
		new Expectations( GetTargetIdentifiInforService.class ) {
			{
				
				GetTargetIdentifiInforService.get( require, ( GeneralDate ) any, ( String ) any );
				result = affiliationOrg;//所属組織
				
				require.getSupportableEmployee( ( EmployeeId ) any, (GeneralDate) any );
				//応援可能な社員がない
			}
		};
		
		//act
		val result = GetSupportInfoOfEmployeeFromSupportableEmployee.get(require, new EmployeeId( "sid" ), GeneralDate.ymd( 2022,2,22 ) );
		
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd( 2022,2,22 ) );
		assertThat( result.getAffiliationOrg() ).isEqualTo( affiliationOrg );
		assertThat( result.getSupportType() ).isEmpty();
		assertThat( result.getRecipientList() ).isEmpty();
		
	}
	
	/**
	 * target: get
	 * pattern: 終日で応援者
	 */
	@Test
	public void testGet_case_support_all_Day(	@Injectable TargetOrgIdenInfor affiliationOrg
											,	@Injectable TargetOrgIdenInfor recipient
			) {
		
		//終日で応援者
		val supportableEmp = SupportableEmployee.createAsAllday(	new EmployeeId( "sid" )
																,	recipient
																,	new DatePeriod( GeneralDate.ymd( 2022, 2, 22 ), GeneralDate.ymd( 2022, 2, 22 ) ) );
		
		new Expectations( GetTargetIdentifiInforService.class ) {
			{
				GetTargetIdentifiInforService.get( require, ( GeneralDate ) any, ( String ) any );
				result = affiliationOrg;//所属組織
				
				require.getSupportableEmployee( ( EmployeeId ) any, ( GeneralDate ) any );
				result = Arrays.asList( supportableEmp );//応援可能な社員リスト
			}
		};
		
		//act
		val result = GetSupportInfoOfEmployeeFromSupportableEmployee.get( require, new EmployeeId( "sid" ), GeneralDate.ymd( 2022,2,22 ) );
		
		//assert
		assertThat( result ).extracting(
				info -> info.getEmployeeId().v()
			,	info -> info.getDate()
			,	info -> info.getAffiliationOrg()
			,	info -> info.getSupportType().get()
		).containsOnly(
				"sid"
			,	GeneralDate.ymd( 2022,2,22 )
			,	affiliationOrg
			,	SupportType.ALLDAY 
		);
		
		assertThat( result.getRecipientList() ).containsExactly( recipient );
		
	}
	
	/**
	 * target: get
	 * pattern: 時間帯で応援者
	 */
	@Test
	public void testGet_case_support_time_zone(	@Injectable TargetOrgIdenInfor affiliationOrg
											,	@Injectable TargetOrgIdenInfor recipient_1
											,	@Injectable TargetOrgIdenInfor recipient_2 ) {
		
		//時間帯で応援者
		val supportableEmp_1 = SupportableEmployee.createAsTimezone(	new EmployeeId( "sid" )
																	,	recipient_1
																	,	GeneralDate.ymd( 2022, 2, 22 )
																	,	new TimeSpanForCalc(
																				TimeWithDayAttr.hourMinute( 9, 0 )
																			,	TimeWithDayAttr.hourMinute( 11, 00 )
																		));
		
		val supportableEmp_2 = SupportableEmployee.createAsTimezone(	new EmployeeId( "sid" )
																	,	recipient_2
																	,	GeneralDate.ymd( 2022, 2, 22 )
																	,	new TimeSpanForCalc(
																				TimeWithDayAttr.hourMinute( 15, 0 )
																			,	TimeWithDayAttr.hourMinute( 17, 30 )
																		));
		
		new Expectations( GetTargetIdentifiInforService.class ) {
			{
				GetTargetIdentifiInforService.get( require, ( GeneralDate ) any, ( String ) any );
				result = affiliationOrg;//所属組織
				
				require.getSupportableEmployee( ( EmployeeId ) any, ( GeneralDate ) any );
				result = Arrays.asList( supportableEmp_1, supportableEmp_2 );//応援可能な社員リスト
			}
		};
		
		//act
		val result = GetSupportInfoOfEmployeeFromSupportableEmployee.get( require, new EmployeeId( "sid" ), GeneralDate.ymd( 2022,2,22 ) );
		
		//assert
		assertThat( result ).extracting(
				info -> info.getEmployeeId().v()
			,	info -> info.getDate()
			,	info -> info.getAffiliationOrg()
			,	info -> info.getSupportType().get()
		).containsOnly(
				"sid"
			,	GeneralDate.ymd( 2022,2,22 )
			,	affiliationOrg
			,	SupportType.TIMEZONE
		);
		
		assertThat( result.getRecipientList() ).containsExactly( recipient_1, recipient_2 );
	}
	
}
