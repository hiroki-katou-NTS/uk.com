package nts.uk.shr.com.permit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AvailabilityPermissionBase {

	private final String companyId;
	
	private final String roleId;
	
	private final int functionNo;
	
	private boolean isAvailable;
	
	public AvailabilityPermissionBase(RestoreAvailabilityPermission restore) {
		this.companyId = restore.companyId();
		this.roleId = restore.roleId();
		this.functionNo = restore.functionNo();
		this.isAvailable = restore.isAvailable();
	}
	
	public void changeAvailablity(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
