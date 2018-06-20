package nts.uk.shr.com.permit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public abstract class AvailabilityPermissionBase extends AggregateRoot {

	private final String companyId;
	
	private final String roleId;
	
	private final int functionNo;
	
	private boolean isAvailable;
	
	public AvailabilityPermissionBase(RestoreAvailabilityPermission restore, int version) {
		this.companyId = restore.companyId();
		this.roleId = restore.roleId();
		this.functionNo = restore.functionNo();
		this.isAvailable = restore.isAvailable();
	}
		
	public AvailabilityPermissionBase(RestoreAvailabilityPermission restore) {
		this(restore, 0);
	}
	
	public void changeAvailablity(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}
