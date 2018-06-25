package nts.uk.ctx.pereg.dom.roles.functionauth.authsetting;

import nts.uk.shr.com.permit.AvailabilityPermissionBase;
import nts.uk.shr.com.permit.RestoreAvailabilityPermission;

/**
 * @author danpv
 * domain name: 個人情報の権限
 */
public class PersonInfoAuthority extends AvailabilityPermissionBase {

	public PersonInfoAuthority(RestoreAvailabilityPermission restore) {
		super(restore);
	}

}
