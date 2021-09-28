package nts.uk.ctx.sys.auth.dom.grant.roleindividual;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
@RunWith(JMockit.class)
public class GrantSystemAdminPrivilegesServiceTest {

	@Injectable
	private GrantSystemAdminPrivilegesService.Require require;
	
	/**
	 * target: grant
	 * pattern: user = empty
	 * excepted: Msg_2210
	 */
	@Test
	public void testGrant_user_empty() {
		val userId = "userId";
		val role = RoleIndividualGrantHelper.createRole( "cid", "roleId", RoleType.SYSTEM_MANAGER);
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 01, 01) , GeneralDate.ymd(2030, 12, 31) );
		
		new Expectations() {
			{
				require.getRoleByRoleType( RoleType.SYSTEM_MANAGER );
				result = role;
			
				require.getUser( (String) any );
			}
		};
		
		//Act
		NtsAssert.businessException( "Msg_2210", () -> GrantSystemAdminPrivilegesService.grant( require, userId, expirationPeriod ) );
	}
	
	/**
	 * target: grant
	 * pattern: default user
	 * excepted: RuntimeException
	 */
	@Test
	public void testGrant_user_default() {
		val userId = "userId";
		val role = RoleIndividualGrantHelper.createRole( "cid", "roleId", RoleType.SYSTEM_MANAGER);
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 01, 01) , GeneralDate.ymd(2030, 12, 31) );
		val user = RoleIndividualGrantHelper.createUser(true);// デフォルトユーザ
		new Expectations() {
			{
				require.getRoleByRoleType( RoleType.SYSTEM_MANAGER );
				result = role;
			
				require.getUser( (String) any );
				result = Optional.of( user );
			}
		};
		
		//Act
		NtsAssert.systemError( () -> GrantSystemAdminPrivilegesService.grant( require, userId, expirationPeriod ) );
		
	}
	
	/**
	 * target: grant
	 * pattern: エラーがない
	 * excepted: success
	 */
	@Test
	public void testGrant_success() {
		val userId = "userId";
		val role = RoleIndividualGrantHelper.createRole( "cid", "roleId", RoleType.SYSTEM_MANAGER);
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 01, 01) , GeneralDate.ymd(2030, 12, 31) );
		val grantRole = RoleIndividualGrantHelper.createSystemManangerOfGrantInfo( userId, expirationPeriod.start(), expirationPeriod.end() );
		val user = RoleIndividualGrantHelper.createUser( false );// デフォルトユーザではない
		
		new Expectations() {
			{
				require.getRoleByRoleType( RoleType.SYSTEM_MANAGER );
				result = role;
			
				require.getUser( (String) any );
				result = Optional.of( user );
			}
		};
		
		//Act
		NtsAssert.atomTask( () -> GrantSystemAdminPrivilegesService.grant( require, userId, expirationPeriod )
				,	any -> require.registerGrantInfo( grantRole ) );
	}
	
	/**
	 * target: updateExpirationPeriod
	 * pattern: user = empty
	 * excepted: RuntimeException
	 */
	@Test
	public void testUpdateExpirationPeriod_user_empty() {
		val userId = "userId";
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 01, 01) , GeneralDate.ymd(2030, 12, 31) );
		val grantRole = RoleIndividualGrantHelper.createSystemManangerOfGrantInfo( userId, expirationPeriod.start(), expirationPeriod.end() );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleTypeOfUser(userId, RoleType.SYSTEM_MANAGER );
				result = grantRole;
			
				require.getUser( (String) any );
			}
		};
		
		//Act
		NtsAssert.businessException( "Msg_2210", () -> GrantSystemAdminPrivilegesService.updateExpirationPeriod( require, userId, expirationPeriod ) );
		
	}
	
	/**
	 * target: updateExpirationPeriod
	 * pattern: default user
	 * excepted: RuntimeException
	 */
	@Test
	public void testUpdateExpirationPeriod_user_default() {
		val userId = "userId";
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 01, 01) , GeneralDate.ymd(2030, 12, 31) );
		val grantRole = RoleIndividualGrantHelper.createSystemManangerOfGrantInfo( userId, GeneralDate.ymd(2000, 01, 01), GeneralDate.ymd(2025, 01, 01) );
		val user = RoleIndividualGrantHelper.createUser(true);// デフォルトユーザ
		
		new Expectations() {
			{
				require.getGrantInfoByRoleTypeOfUser( userId, RoleType.SYSTEM_MANAGER );
				result = grantRole;
			
				require.getUser( (String) any );
				result = Optional.of( user );
			}
		};
		
		//Act
		NtsAssert.systemError( () -> GrantSystemAdminPrivilegesService.updateExpirationPeriod( require, userId, expirationPeriod ) );

	}
	
	/**
	 * target: updateExpirationPeriod
	 * pattern: システム日付からシステム管理者が存在しない期間がある
	 * システム日付~9999/12/31							<-------------------------------------------------->
	 * userID_1: 				<---------->
	 * userID_2: 												<-------------------------------------->
	 * 有効期限: 			<------------------------------>
	 * excepted: Msg_330
	 */
	@Test
	public void testUpdateExpirationPeriod_Msg_330() {
		val userId = "userID_5";
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 01, 01) , GeneralDate.ymd(2021, 12, 31) );
		val grantRole = RoleIndividualGrantHelper.createSystemManangerOfGrantInfo( userId, GeneralDate.ymd(2000, 01, 01), GeneralDate.ymd(2020, 01, 01) );
		val grantRoles = Arrays.asList(
				RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2020, 01, 01), GeneralDate.ymd(2021, 05, 31) )
			,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_2", GeneralDate.ymd(2025, 07, 01), GeneralDate.ymd(9999, 12, 31) )
			);
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		val userId_2 = RoleIndividualGrantHelper.createUser( "userID_2", GeneralDate.ymd(9999, 12, 31) );
		val userId_5 = RoleIndividualGrantHelper.createUser( userId, GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleTypeOfUser( userId, RoleType.SYSTEM_MANAGER );
				result = grantRole;
			
				require.getUser( userId );
				result = Optional.of( userId_5 );
				
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
				require.getUser( "userID_2" );
				result = Optional.of( userId_2 );
			}
		};
		
		//Act
		NtsAssert.businessException( "Msg_330", () -> GrantSystemAdminPrivilegesService.updateExpirationPeriod( require, userId, expirationPeriod ) );
	}
	
	/**
	 * target: updateExpirationPeriod
	 * pattern: システム日付からシステム管理者が存在しない期間がない
	 * システム日付~9999/12/31								<-------------------------------------------------->
	 * userID_1: 				<-------------->
	 * userID_2: 									<------------------------------------------------------>
	 * 有効期限: 			<------------------------------------>
	 * excepted: update success
	 */
	@Test
	public void testUpdateExpirationPeriod_update_success() {
		val userId = "userID_5";
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 01, 01) , GeneralDate.ymd(2025, 12, 31) );
		val grantRole = RoleIndividualGrantHelper.createSystemManangerOfGrantInfo( userId, GeneralDate.ymd(2000, 01, 01), GeneralDate.ymd(2030, 01, 01) );
		val grantRoles = Arrays.asList(
				RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2020, 01, 01), GeneralDate.ymd(2021, 05, 31) )
			,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_2", GeneralDate.ymd(2021, 07, 01), GeneralDate.ymd(9999, 12, 31) )
			);
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		val userId_2 = RoleIndividualGrantHelper.createUser( "userID_2", GeneralDate.ymd(9999, 12, 31) );
		val userId_5 = RoleIndividualGrantHelper.createUser( userId, GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleTypeOfUser( userId, RoleType.SYSTEM_MANAGER );
				result = grantRole;
			
				require.getUser( userId );
				result = Optional.of( userId_5 );
				
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
				require.getUser( "userID_2" );
				result = Optional.of( userId_2 );
				
			}
		};
		
		//Act
		NtsAssert.atomTask( () -> GrantSystemAdminPrivilegesService.updateExpirationPeriod( require, userId, expirationPeriod )
				,	any -> require.updateGrantInfo( grantRole ) );
				
	}
	
	/**
	 * target: deprive
	 * pattern: システム日付からシステム管理者が存在しない期間がある
	 * システム日付~9999/12/31							<-------------------------------------------------->
	 * userID_1: 				<---------->
	 * userID_2: 												<-------------------------------------->
	 * 有効期限: 			<------------------------------>
	 * excepted: Msg_331
	 */
	@Test
	public void testDeprive_Msg_331() {
		val userId = "userID_5";
		val grantRole = RoleIndividualGrantHelper.createSystemManangerOfGrantInfo( userId, GeneralDate.ymd(2000, 01, 01), GeneralDate.ymd(2020, 01, 01) );
		val grantRoles = Arrays.asList(
				RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2020, 01, 01), GeneralDate.ymd(2021, 05, 31) )
			,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_2", GeneralDate.ymd(2025, 07, 01), GeneralDate.ymd(9999, 12, 31) )
			);
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		val userId_2 = RoleIndividualGrantHelper.createUser( "userID_2", GeneralDate.ymd(9999, 12, 31) );
		val userId_5 = RoleIndividualGrantHelper.createUser( userId, GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleTypeOfUser( userId, RoleType.SYSTEM_MANAGER );
				result = grantRole;
			
				require.getUser( userId );
				result = Optional.of( userId_5 );
				
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
				require.getUser( "userID_2" );
				result = Optional.of( userId_2 );
			}
		};
		
		//Act
		NtsAssert.businessException( "Msg_331", () -> GrantSystemAdminPrivilegesService.deprive( require, userId ) );
		
	}
	
	/**
	 * target: updateExpirationPeriod
	 * pattern: システム日付からシステム管理者が存在しない期間がない
	 * システム日付~9999/12/31								<-------------------------------------------------->
	 * userID_1: 				<-------------->
	 * userID_2: 									<------------------------------------------------------>
	 * 有効期限: 			<------------------------------------>
	 * excepted: delete success
	 */
	@Test
	public void testDeprive_delete_success() {
		val userId = "userID_5";
		val grantRole = RoleIndividualGrantHelper.createSystemManangerOfGrantInfo( userId, GeneralDate.ymd(2000, 01, 01), GeneralDate.ymd(2030, 01, 01) );
		val grantRoles = Arrays.asList(
				RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2020, 01, 01), GeneralDate.ymd(2021, 05, 31) )
			,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_2", GeneralDate.ymd(2021, 07, 01), GeneralDate.ymd(9999, 12, 31) )
			);
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		val userId_2 = RoleIndividualGrantHelper.createUser( "userID_2", GeneralDate.ymd(9999, 12, 31) );
		val userId_5 = RoleIndividualGrantHelper.createUser( userId, GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleTypeOfUser( userId, RoleType.SYSTEM_MANAGER );
				result = grantRole;
			
				require.getUser( userId );
				result = Optional.of( userId_5 );
				
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
				require.getUser( "userID_2" );
				result = Optional.of( userId_2 );
				
			}
		};
		
		//Act
		NtsAssert.atomTask( () -> GrantSystemAdminPrivilegesService.deprive( require, userId )
				,	any -> require.deleteGrantInfo( grantRole.getUserId(), grantRole.getCompanyId(), grantRole.getRoleType()) );
				
	}
	
	/**
	 * target: isAlwaysASystemAdmin
	 * pattern: <チェック対象の期間>中に<システム管理者が存在しない期間>がある
	 * システム日付～9999/12/31 : 	2021/09/17~9999/12/31			<-------------------------------------------------------->
	 * userID_1				 <---------------->
	 * userID_2							<-------->
	 * userID_3									<---------------------->
	 * userID_4																					<------------------------>
	 * excepted: false
	 */
	@Test
	public void testIsAlwaysASystemAdmin_case1() {
		
		val grantRoles = Arrays.asList(
					RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2020, 01, 01), GeneralDate.ymd(2021, 05, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_2", GeneralDate.ymd(2021, 01, 01), GeneralDate.ymd(2021, 07, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_3", GeneralDate.ymd(2021, 07, 01), GeneralDate.ymd(2021, 12, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_4", GeneralDate.ymd(2022, 07, 01), GeneralDate.ymd(9999, 12, 31) )
				);
		
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		val userId_2 = RoleIndividualGrantHelper.createUser( "userID_2", GeneralDate.ymd(9999, 12, 31) );
		val userId_3 = RoleIndividualGrantHelper.createUser( "userID_3", GeneralDate.ymd(9999, 12, 31) );
		val userId_4 = RoleIndividualGrantHelper.createUser( "userID_4", GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
				require.getUser( "userID_2" );
				result = Optional.of( userId_2 );
				
				require.getUser( "userID_3" );
				result = Optional.of( userId_3 );
				
				require.getUser( "userID_4" );
				result = Optional.of( userId_4 );
				
			}
		};
		
		//Act
		boolean result = NtsAssert.Invoke.staticMethod(	GrantSystemAdminPrivilegesService.class, "isAlwaysASystemAdmin"
				,	require, "userID_5", Optional.empty());
		
		//Assert
		assertThat( result ).isFalse();
		
	}
	
	/**
	 * target: isAlwaysASystemAdmin
	 * pattern: <チェック対象の期間>中に<システム管理者が存在しない期間>がない
	 * システム日付～9999/12/31 : 	2021/09/17~9999/12/31			<-------------------------------------------------------->
	 * userID_1				 <---------------->
	 * userID_2							<-------->
	 * userID_3									<---------------------->
	 * userID_4																					<------------------------>
	 * userID_4															<---------------------------->
	 * excepted: true
	 */
	@Test
	public void testIsAlwaysASystemAdmin_case2() {
		
		val grantRoles = Arrays.asList(
					RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2000, 01, 01), GeneralDate.ymd(2021, 05, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_2", GeneralDate.ymd(2021, 01, 01), GeneralDate.ymd(2021, 07, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_3", GeneralDate.ymd(2021, 07, 01), GeneralDate.ymd(2021, 12, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_4", GeneralDate.ymd(2029, 07, 01), GeneralDate.ymd(9999, 12, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_6", GeneralDate.ymd(2022, 01, 01), GeneralDate.ymd(2030, 12, 31) )
				);
		
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		val userId_2 = RoleIndividualGrantHelper.createUser( "userID_2", GeneralDate.ymd(9999, 12, 31) );
		val userId_3 = RoleIndividualGrantHelper.createUser( "userID_3", GeneralDate.ymd(9999, 12, 31) );
		val userId_4 = RoleIndividualGrantHelper.createUser( "userID_4", GeneralDate.ymd(9999, 12, 31) );
		val userId_6 = RoleIndividualGrantHelper.createUser( "userID_6", GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
				require.getUser( "userID_2" );
				result = Optional.of( userId_2 );
				
				require.getUser( "userID_3" );
				result = Optional.of( userId_3 );
				
				require.getUser( "userID_4" );
				result = Optional.of( userId_4 );
				
				require.getUser( "userID_6" );
				result = Optional.of( userId_6 );
				
			}
		};
		
		//Act
		boolean result = NtsAssert.Invoke.staticMethod(	GrantSystemAdminPrivilegesService.class, "isAlwaysASystemAdmin"
				,	require, "userID_5", Optional.empty());
		
		//Assert
		assertThat( result ).isTrue();
		
	}
	
	/**
	 * target: isAlwaysASystemAdmin
	 * pattern: <チェック対象の期間>中に<システム管理者が存在しない期間>がない
	 * システム日付～9999/12/31 : 	2021/09/17~9999/12/31			<-------------------------------------------------------->
	 * userID_1				 <---------------->
	 * userID_2																		<------------------------------------>
	 * userID_3					<---------------->
	 * userID_4													<------------->
	 * userID_5																						<-------------------->
	 * userID_6																<------------>
	 * excepted: true
	 */
	@Test
	public void testIsAlwaysASystemAdmin_case3() {
		
		val grantRoles = Arrays.asList(
					RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2000, 1, 01), GeneralDate.ymd(2016, 5, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_2", GeneralDate.ymd(2030, 1, 01), GeneralDate.ymd(9999, 12, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_3", GeneralDate.ymd(2012, 7, 01), GeneralDate.ymd(2018, 12, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_4", GeneralDate.ymd(2021, 9, 27), GeneralDate.ymd(2024, 12, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_5", GeneralDate.ymd(2040, 1, 01), GeneralDate.ymd(9999, 12, 31) )
				,	RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_6", GeneralDate.ymd(2023, 1, 01), GeneralDate.ymd(2030, 12, 31) )
				);
		
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		val userId_2 = RoleIndividualGrantHelper.createUser( "userID_2", GeneralDate.ymd(9999, 12, 31) );
		val userId_3 = RoleIndividualGrantHelper.createUser( "userID_3", GeneralDate.ymd(9999, 12, 31) );
		val userId_4 = RoleIndividualGrantHelper.createUser( "userID_4", GeneralDate.ymd(9999, 12, 31) );
		val userId_5 = RoleIndividualGrantHelper.createUser( "userID_5", GeneralDate.ymd(9999, 12, 31) );
		val userId_6 = RoleIndividualGrantHelper.createUser( "userID_6", GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
				require.getUser( "userID_2" );
				result = Optional.of( userId_2 );
				
				require.getUser( "userID_3" );
				result = Optional.of( userId_3 );
				
				require.getUser( "userID_4" );
				result = Optional.of( userId_4 );
				
				require.getUser( "userID_5" );
				result = Optional.of( userId_5 );
				
				require.getUser( "userID_6" );
				result = Optional.of( userId_6 );
				
			}
		};
		
		//Act
		boolean result = NtsAssert.Invoke.staticMethod(	GrantSystemAdminPrivilegesService.class, "isAlwaysASystemAdmin"
				,	require, "userID_7", Optional.empty());
		
		//Assert
		assertThat( result ).isTrue();
		
	}
	
	/**
	 * target: isAlwaysASystemAdmin
	 * pattern: <チェック対象の期間>中に<システム管理者が存在しない期間>がない
	 * システム日付～9999/12/31 : 	2021/09/17~9999/12/31			<-------------------------------------------------------->
	 * userID_1								<---------------------------->
	 * 有効期間:																				<---------------------------->
	 * excepted: false
	 */
	@Test
	public void testIsAlwaysASystemAdmin_case4() {
		//有効期間
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2035, 01, 01), GeneralDate.ymd(9999, 12, 31) );
		
		val grantRoles = Arrays.asList(
					RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2000, 01, 01), GeneralDate.ymd(2030, 12, 31) )
				);
		
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
				
			}
		};
		
		//Act
		boolean result = NtsAssert.Invoke.staticMethod(	GrantSystemAdminPrivilegesService.class, "isAlwaysASystemAdmin"
				,	require, "userID_7", Optional.of(expirationPeriod) );
		
		//Assert
		assertThat( result ).isFalse();
		
	}
	
	
	/**
	 * target: isAlwaysASystemAdmin
	 * pattern: <チェック対象の期間>中に<システム管理者が存在しない期間>がない
	 * システム日付～9999/12/31 : 	2021/09/17~9999/12/31			<-------------------------------------------------------->
	 * userID_1																				<---------------------------->
	 * 有効期間:											<------------------->
	 * excepted: true
	 */
	@Test
	public void testIsAlwaysASystemAdmin_case5() {
		//有効期間
		val expirationPeriod = new DatePeriod( GeneralDate.ymd(2000, 12, 01), GeneralDate.ymd(2025, 12, 31) );
		
		val grantRoles = Arrays.asList(
					RoleIndividualGrantHelper.createSystemManangerOfGrantInfo("userID_1", GeneralDate.ymd(2030, 1, 01), GeneralDate.ymd(9999, 12, 31) )
				);
		
		val userId_1 = RoleIndividualGrantHelper.createUser( "userID_1", GeneralDate.ymd(9999, 12, 31) );
		
		new Expectations() {
			{
				require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER);
				result = grantRoles;
				
				require.getUser( "userID_1" );
				result = Optional.of( userId_1 );
			}
		};
		
		//Act
		boolean result = NtsAssert.Invoke.staticMethod(	GrantSystemAdminPrivilegesService.class, "isAlwaysASystemAdmin"
				,	require, "userID_7", Optional.of( expirationPeriod ));
		
		//Assert
		assertThat( result ).isFalse();
		
	}
	
}
