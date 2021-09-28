package nts.uk.ctx.sys.auth.dom.grant.roleindividual;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleAtr;
import nts.uk.ctx.sys.auth.dom.role.RoleCode;
import nts.uk.ctx.sys.auth.dom.role.RoleName;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.shared.dom.user.ContractCode;
import nts.uk.ctx.sys.shared.dom.user.DisabledSegment;
import nts.uk.ctx.sys.shared.dom.user.LoginID;
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.password.HashPassword;
import nts.uk.ctx.sys.shared.dom.user.password.PassStatus;

public class RoleIndividualGrantHelper {
	/**
	 * ロールを作る
	 * @param cid 会社ID
	 * @param roleId ロールID
	 * @param roleType ロール種類
	 * @return
	 */
	public static Role createRole(String cid, String roleId, RoleType  roleType) {
		
		return new Role ( roleId
				,	new nts.uk.ctx.sys.auth.dom.role.ContractCode("contractCode")
				,	cid
				,	new RoleCode( "code" )
				,	new RoleName( "name" )
				,	roleType
				,	RoleAtr.GENERAL
				,	EmployeeReferenceRange.DEPARTMENT_ONLY
				,	Optional.empty());
	}
	
	/**
	 * ロール個人別付与を作る
	 * @param userId ユーザID
	 * @param roleType ロール種類
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return
	 */
	public static RoleIndividualGrant createRoleIndividualGrant(
				String userId
			,	RoleType roleType
			,	DatePeriod validPeriod ) {
		
		return new RoleIndividualGrant( userId, "companyId", roleType, "roleId", validPeriod );
		
	}
	
	/**
	 * ユーザを作る
	 * @param defaultUser デフォルトユーザ
	 * @return
	 */
	public static User createUser( boolean defaultUser ) {
		
		return new User( "userID"
				,	defaultUser
				,	new HashPassword("hashPassword")
				,	new LoginID("loginID")
				,	new ContractCode( "contractCode" )
				,	GeneralDate.ymd(9999, 12, 21)
				,	DisabledSegment.True
				,	DisabledSegment.True
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
				,	PassStatus.InitPassword );
	}
	
	/**
	 * ユーザを作る
	 * @param userId ユーザID
	 * @param expirationDate 有効期限
	 * @return
	 */
	public static User createUser( String userId, GeneralDate expirationDate) {
		
		return new User( userId
				,	false
				,	new HashPassword("hashPassword")
				,	new LoginID("loginID")
				,	new ContractCode( "contractCode" )
				,	expirationDate
				,	DisabledSegment.True
				,	DisabledSegment.True
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
				,	PassStatus.InitPassword );
	}
	
	/**
	 * システム管理者ロールの付与情報を作成する
	 * @param userId ユーザID
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return
	 */
	public static RoleIndividualGrant createSystemManangerOfGrantInfo(
				String userId
			,	GeneralDate startDate
			,	GeneralDate endDate) {
		
		return new RoleIndividualGrant(
					userId
				,	"companyId" //DUMMY
				,	RoleType.SYSTEM_MANAGER //DUMMY
				,	"roleId" //DUMMY
				,	new DatePeriod( startDate, endDate) );
	}	

}
