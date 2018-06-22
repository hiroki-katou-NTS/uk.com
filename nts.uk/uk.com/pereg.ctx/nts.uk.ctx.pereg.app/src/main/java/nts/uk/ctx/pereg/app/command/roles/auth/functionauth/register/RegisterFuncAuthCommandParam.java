package nts.uk.ctx.pereg.app.command.roles.auth.functionauth.register;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.permit.RestoreAvailabilityPermission;

@AllArgsConstructor
@NoArgsConstructor
public class RegisterFuncAuthCommandParam implements RestoreAvailabilityPermission {

	private String companyId;
	
	private String roleId;
	
	private int functionNo;

	private boolean available;

	@Override
	public String companyId() {
		return companyId;
	}

	@Override
	public String roleId() {
		return roleId;
	}

	@Override
	public int functionNo() {
		return functionNo;
	}
	
	@Override
	public boolean isAvailable() {
		return this.available;
	}
	
}
