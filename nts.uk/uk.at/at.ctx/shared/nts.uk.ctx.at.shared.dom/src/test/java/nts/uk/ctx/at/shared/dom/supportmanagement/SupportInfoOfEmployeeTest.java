package nts.uk.ctx.at.shared.dom.supportmanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 社員の応援情報のUTコード
 * @author lan_lt
 *
 */
public class SupportInfoOfEmployeeTest {
	
	@Test
	public void testGetter(@Injectable TargetOrgIdenInfor affiliationOrg) {
		
		//act
		val result = SupportInfoOfEmployee.createWithoutSupport(	new EmployeeId("sid")
				,	GeneralDate.ymd(2022, 1, 1)
				,	affiliationOrg);
		//assert
		NtsAssert.invokeGetters( result );
		
	}
	/**
	 * target: createWithoutSupport
	 */
	@Test
	public void testCreateWithoutSupport( @Injectable TargetOrgIdenInfor affiliationOrg ) {
		
		//act
		val result = SupportInfoOfEmployee.createWithoutSupport(	new EmployeeId("sid")
																,	GeneralDate.ymd(2022, 2, 22)
																,	affiliationOrg );
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd(2022, 2, 22) );
		assertThat( result.getAffiliationOrg() ).isEqualTo( affiliationOrg );
		assertThat( result.getSupportType() ).isEmpty();
		assertThat( result.getRecipientList() ).isEmpty();
		
	}
	
	/**
	 * target: createWithAllDaySupport
	 */
	@Test
	public void testCreateWithAllDaySupport(	@Injectable TargetOrgIdenInfor affiliationOrg
											,	@Injectable TargetOrgIdenInfor recipient) {
		
		//act
		val result = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId("sid")
																,	GeneralDate.ymd(2022, 2, 22)
																,	affiliationOrg
																,	recipient );
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd(2022, 2, 22) );
		assertThat( result.getAffiliationOrg() ).isEqualTo( affiliationOrg );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.ALLDAY );
		assertThat( result.getRecipientList() ).containsExactly( recipient);
	}
	
	/**
	 * target: createWithTimezoneSupport
	 */
	@Test
	public void testCreateWithTimezoneSupport(	@Injectable TargetOrgIdenInfor affiliationOrg
											,	@Injectable TargetOrgIdenInfor recipient_1
											,	@Injectable TargetOrgIdenInfor recipient_2) {
		//act
		val result = SupportInfoOfEmployee.createWithTimezoneSupport(	new EmployeeId("sid")
																	,	GeneralDate.ymd(2022, 2, 22)
																	,	affiliationOrg
																	,	Arrays.asList( recipient_1, recipient_2 ) );
		//assert
		assertThat( result.getEmployeeId().v() ).isEqualTo( "sid" );
		assertThat( result.getDate() ).isEqualTo( GeneralDate.ymd(2022, 2, 22) );
		assertThat( result.getAffiliationOrg() ).isEqualTo( affiliationOrg );
		assertThat( result.getSupportType().get() ).isEqualTo( SupportType.TIMEZONE );
		assertThat( result.getRecipientList() ).containsExactly( recipient_1, recipient_2 );
		
	}
	
	/**
	 * target: doTheySupport
	 */
	@Test
	public void testDoTheySupport(	@Injectable TargetOrgIdenInfor affiliationOrg
								,	@Injectable TargetOrgIdenInfor recipient) {
		
		/** 応援しない **/
		{
			val target = SupportInfoOfEmployee.createWithoutSupport(	new EmployeeId("sid")
																	,	GeneralDate.ymd(2022, 2, 22)
																	,	affiliationOrg);
			//act
			val result = target.doTheySupport();
			
			//assert
			assertThat( result ).isFalse();
		}
		
		/** 応援する **/
		{
			val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId("sid")
																		,	GeneralDate.ymd(2022, 2, 22)
																		,	affiliationOrg
																		,	recipient );
			//act
			val result = target.doTheySupport();
			
			//assert
			assertThat( result ).isTrue();
		}
	}
	
	/**
	 * target: isAffiliatePerson
	 * pattern: 職場
	 */
	@Test
	public void testIsAffiliatePerson_case_workplace( @Injectable TargetOrgIdenInfor recipient) {
		//所属組織
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
		
		val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId("sid")
				,	GeneralDate.ymd(2022, 2, 22)
				,	affiliationOrg
				,	recipient );
		
		//同じ
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1");
			
			//act
			val result = target.isAffiliatePerson( baseOrg );
			
			//assert
			assertThat( result ).isTrue();
		}
		
		//違う
		{
			
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_2");
			//act
			val result = target.isAffiliatePerson( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
			
		}
		
		
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( "workplaceGroupId_1" );
			
			//act
			val result = target.isAffiliatePerson( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
		}
		
	}
	
	/**
	 * target: isAffiliatePerson
	 * pattern: 職場グループ
	 */
	@Test
	public void testIsAffiliatePerson_case_workplaceGroup( @Injectable TargetOrgIdenInfor recipient ) {
		//所属組織
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( "workplaceGroupId_1" );
		
		val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId("sid")
				,	GeneralDate.ymd(2022, 2, 22)
				,	affiliationOrg
				,	recipient );
		
		//同じ
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( "workplaceGroupId_1" );
			
			//act
			val result = target.isAffiliatePerson( baseOrg );
			
			//assert
			assertThat( result ).isTrue();
		}
		
		//違う
		{
			
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup( "workplaceGroupId_2" );
			//act
			val result = target.isAffiliatePerson( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
		}
		
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			
			//act
			val result = target.isAffiliatePerson( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
		}
		
	}
	
	/**
	 * target: doTheyGoToSupport
	 * pattern: 応援に行かない
	 */
	@Test
	public void testDoTheyGoToSupport_case_dont_go( @Injectable TargetOrgIdenInfor recipient ) {
		//所属組織
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
		
		//所属者じゃない
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_2" );
			
			val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )//dummy
																	,	GeneralDate.ymd(2022, 2, 22)//dummy
																	,	affiliationOrg
																	,	recipient );//dummy
			
			//act
			val result = target.doTheyGoToSupport( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
			
		}
		
		//応援しない
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			
			// 応援しない社員
			val target = SupportInfoOfEmployee.createWithoutSupport(	new EmployeeId( "sid" )//dummy
																	,	GeneralDate.ymd(2022, 2, 22)//dummy
																	,	affiliationOrg );
			//act
			val result = target.doTheyGoToSupport( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
			
		}
		
		//終日応援する場合は 応援に行かない
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId("sid")
																	,	GeneralDate.ymd(2022, 2, 22)
																	,	affiliationOrg
																	,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" )
																		);
			//act
			val result = target.doTheyGoToSupport(baseOrg);
			
			//assert
			assertThat( result ).isFalse();
			
		}
		
		//時間帯で応援する場合は 応援に行かない
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			val target = SupportInfoOfEmployee.createWithTimezoneSupport(	new EmployeeId("sid")
																		,	GeneralDate.ymd(2022, 2, 22)
																		,	affiliationOrg
																		,	Arrays.asList( 
																				TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" )
																			));
			//act
			val result = target.doTheyGoToSupport(baseOrg);
			
			//assert
			assertThat( result ).isFalse();
			
		}
	}
	
	/**
	 * 
	 * target: doTheyGoToSupport
	 * pattern: 応援に行く
	 */
	@Test
	public void testDoTheyGoToSupport_case_go( @Injectable TargetOrgIdenInfor recipient ) {
		//所属組織
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
		
		//終日応援する場合は 応援に行く
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )
																	,	GeneralDate.ymd( 2022, 2, 22 )
																	,	affiliationOrg
																	,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_other" )
																		);
			//act
			val result = target.doTheyGoToSupport( baseOrg );
			
			//assert
			assertThat( result ).isTrue();
			
		}
		
		//時間帯で応援する場合は 応援に行く
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			val target = SupportInfoOfEmployee.createWithTimezoneSupport(	new EmployeeId( "sid" )
																		,	GeneralDate.ymd(2022, 2, 22)
																		,	affiliationOrg
																		,	Arrays.asList( 
																				TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" )
																			,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_other" )
																			));
			//act
			val result = target.doTheyGoToSupport(baseOrg);
			
			//assert
			assertThat( result ).isTrue();
			
		}
	}
	
	/**
	 * target: doTheyCometoSupport
	 * pattern: 応援に来ない
	 */
	@Test
	public void testDoTheyCometoSupport_case_no_come( @Injectable TargetOrgIdenInfor recipient ) {
		//所属組織
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
		
		//所属者
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			
			val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )//dummy
																	,	GeneralDate.ymd(2022, 2, 22)//dummy
																	,	affiliationOrg
																	,	recipient );//dummy
			
			//act
			val result = target.doTheyCometoSupport( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
		}
		
		//応援しない
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_2" );
			
			// 応援しない社員
			val target = SupportInfoOfEmployee.createWithoutSupport(	new EmployeeId( "sid" )//dummy
																	,	GeneralDate.ymd(2022, 2, 22)//dummy
																	,	affiliationOrg );
			
			//act
			val result = target.doTheyCometoSupport( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
		}
		
		//終日応援する場合は 応援に来ない
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_2" );
			val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )
																	,	GeneralDate.ymd( 2022, 2, 22 )
																	,	affiliationOrg
																	,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_3" )
																		);
			//act
			val result = target.doTheyCometoSupport( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
			
		}
		
		//時間帯で応援する場合は 応援に来ない
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_2" );
			val target = SupportInfoOfEmployee.createWithTimezoneSupport(	new EmployeeId( "sid" )
																		,	GeneralDate.ymd(2022, 2, 22)
																		,	affiliationOrg
																		,	Arrays.asList( 
																				TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_other_1" )
																			,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_other_2" )
																			));
			//act
			val result = target.doTheyCometoSupport( baseOrg );
			
			//assert
			assertThat( result ).isFalse();
			
		}
	}
	
	/**
	 * target: doTheyCometoSupport
	 * pattern: 応援に来る
	 */
	@Test
	public void testDoTheyCometoSupport_case_come( @Injectable TargetOrgIdenInfor recipient ) {
		//所属組織
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" );
		
		//終日応援する場合は 応援に来る
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			val target = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )
																	,	GeneralDate.ymd( 2022, 2, 22 )
																	,	affiliationOrg
																	,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" )
																		);
			//act
			val result = target.doTheyCometoSupport( baseOrg );
			
			//assert
			assertThat( result ).isTrue();
			
		}
		
		//時間帯で応援する場合は 応援に来る
		{
			val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );
			val target = SupportInfoOfEmployee.createWithTimezoneSupport(	new EmployeeId( "sid" )
																		,	GeneralDate.ymd(2022, 2, 22)
																		,	affiliationOrg
																		,	Arrays.asList( 
																				TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" )
																			,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_other" )
																			));
			//act
			val result = target.doTheyCometoSupport( baseOrg );
			
			//assert
			assertThat( result ).isTrue();
			
		}
	}
	
	/**
	 * target: getSupportStatus
	 * pattern: 応援に行く、応援に行かない
	 */
	@Test
	public void testGetSupportStatus_case_go(@Injectable TargetOrgIdenInfor recipient ) {
		
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" );//所属組織
		
		val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" );//基準となる組織
		
		Map<SupportInfoOfEmployee, SupportStatus> expectedValues = new HashMap< SupportInfoOfEmployee, SupportStatus >() {
			
			private static final long serialVersionUID = 1L;

			{
				//終日応援する場合は 応援に行く
				val goAllDay_emp = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )
																				,	GeneralDate.ymd( 2022, 2, 22 )
																				,	affiliationOrg
																				,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_other" )
																					);
				put( goAllDay_emp, SupportStatus.GO_ALLDAY);
				
				//時間帯で応援する場合は 応援に行く
				val goTimeZone_emp = SupportInfoOfEmployee.createWithTimezoneSupport(	new EmployeeId( "sid" )
																					,	GeneralDate.ymd(2022, 2, 22)
																					,	affiliationOrg
																					,	Arrays.asList( 
																							TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" )
																						,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplace_other" )
																						));
				put( goTimeZone_emp, SupportStatus.GO_TIMEZONE );
				
				//応援に行かない
				val donnotgo_emp = SupportInfoOfEmployee.createWithoutSupport(	new EmployeeId( "sid" )
																				,	GeneralDate.ymd( 2022, 2, 22 )
																				,	affiliationOrg
																					);
				put( donnotgo_emp, SupportStatus.DO_NOT_GO);
				
			}
		};
		
		expectedValues.entrySet().forEach( supporter ->{
			//act
			val result = supporter.getKey().getSupportStatus( baseOrg );
			
			//assert
			assertThat( result ).isEqualTo( supporter.getValue() );
			
		});
	}
	
	/**
	 * target: getSupportStatus
	 * pattern: 応援に来る、応援に来ない
	 */
	@Test
	public void testGetSupportStatus_case_come(@Injectable TargetOrgIdenInfor recipient ) {
		
		val affiliationOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" );//所属組織
		
		val baseOrg = TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" );//基準となる組織
		
		Map<SupportInfoOfEmployee, SupportStatus> expectedValues = new HashMap< SupportInfoOfEmployee, SupportStatus >() {
			
			private static final long serialVersionUID = 1L;

			{
				//終日応援する場合は 応援に来る
				val comeAllDay_emp = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )
																				,	GeneralDate.ymd( 2022, 2, 22 )
																				,	affiliationOrg
																				,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" )
																					);
				put( comeAllDay_emp, SupportStatus.COME_ALLDAY);
				
				//時間帯で応援する場合は 応援に来る
				val comeTimeZone_emp = SupportInfoOfEmployee.createWithTimezoneSupport(	new EmployeeId( "sid" )
																					,	GeneralDate.ymd(2022, 2, 22)
																					,	affiliationOrg
																					,	Arrays.asList( 
																							TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_1" )
																						,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_2" )
																						));
				put( comeTimeZone_emp, SupportStatus.COME_TIMEZONE );
				
				//応援に来ない
				val donnotcome_emp = SupportInfoOfEmployee.createWithAllDaySupport(	new EmployeeId( "sid" )
																				,	GeneralDate.ymd( 2022, 2, 22 )
																				,	affiliationOrg
																				,	TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId_2" )
																					);
				put( donnotcome_emp, SupportStatus.DO_NOT_COME);
				
			}
		};
		
		expectedValues.entrySet().forEach( supporter ->{
			//act
			val result = supporter.getKey().getSupportStatus( baseOrg );
			
			//assert
			assertThat( result ).isEqualTo( supporter.getValue() );
			
		});
	}
	
}
