package nts.uk.shr.com.context.loginuser;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.context.LoginUserContext;

@RequiredArgsConstructor
public class DefaultLoginUserContext implements LoginUserContext {
	
	private final String userId;
	
	private final boolean isEmployee;
	
	@Setter
	private String loginCode;
	
	@Setter
	private String personId;
	
	@Setter
	private String contractCode;
	
	@Setter
	private String companyId;
	
	@Setter
	private String companyCode;
	
	@Setter
	private String employeeId;
	
	@Setter
	private String employeeCode;

	@Override
	public boolean hasLoggedIn() {
		return true;
	}
	
	@Override
	public boolean isEmployee() {
		return this.isEmployee;
	}
	
	@Override
	public String userId() {
		return this.userId;
	}
	
	@Override
	public String loginCode() {
		return this.loginCode;
	}
	
	@Override
	public String personId() {
		return this.personId;
	}
	@Override
	public String contractCode() {
		return this.contractCode;
	}
	@Override
	public String companyId() {
		return this.companyId;
	}
	@Override
	public String companyCode() {
		return this.companyCode;
	}
	@Override
	public String employeeId() {
		return this.employeeId;
	}
	@Override
	public String employeeCode() {
		return this.employeeCode;
	}
	
}
