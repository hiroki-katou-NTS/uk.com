package nts.uk.ctx.sys.gateway.dom.outage.helper;

import lombok.RequiredArgsConstructor;
import lombok.val;
import mockit.Mock;
import mockit.MockUp;
import nts.gul.util.value.MutableValue;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@RequiredArgsConstructor
public class RoleExpectation {
	private final RoleType role;
	
	/**
	 * ロールのモックをセットアップ
	 */
	public static LoginUserRoles setup(RoleType role) {
		return new RoleExpectation(role).setup();
	}
	
	
	private LoginUserRoles setup() {
		switch (role) {
		case TENANT_ADMIN:
			return expectation(true, false, false);
		case COMPANY_ADMIN:
			return expectation(false, true, false);
		case PERSON_IN_CHARGE:
			return expectation(false, false, true);
		default:
			return expectation(false, false, false);
		}
	}
	
	public static enum RoleType {
		TENANT_ADMIN,
		COMPANY_ADMIN,
		PERSON_IN_CHARGE,
		EMPLOYEE,
	}

	private LoginUserRoles expectation(boolean systemAdmin, boolean companyAdmin, boolean isInCharge) {
		
		MutableValue<LoginUserRoles.HavingRole> havingRole = new MutableValue<>();
		
		val roles = new MockUp<LoginUserRoles>() {
			
			@Mock
			public LoginUserRoles.HavingRole have() {
				return havingRole.get();
			}
			
			@Mock
			public boolean isInChargeAny() {
				return isInCharge;
			}
			
			@Mock
			public String forSystemAdmin() {
				return systemAdmin ? "role" : null;
			}
			
			@Mock
			public String forCompanyAdmin() {
				return companyAdmin ? "role" : null;
			}
			
		}.getMockInstance();
		
		havingRole.set(new LoginUserRoles.HavingRole(roles));
		
		return roles;
	}
}
