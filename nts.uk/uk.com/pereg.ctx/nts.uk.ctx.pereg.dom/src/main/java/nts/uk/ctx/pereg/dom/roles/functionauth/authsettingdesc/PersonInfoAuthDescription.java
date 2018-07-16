package nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc;

import lombok.Getter;
import nts.uk.shr.com.permit.DescriptionOfAvailabilityPermissionBase;
import nts.uk.shr.com.permit.RestoreDescriptionOfAvailabilityPermission;

/**
 * @author danpv
 * domain name: 個人情報の機能
 */
@Getter
public class PersonInfoAuthDescription extends DescriptionOfAvailabilityPermissionBase {
	
	public PersonInfoAuthDescription(RestoreDescriptionOfAvailabilityPermission restore) {
		super(restore, 0);
	}

	public PersonInfoAuthDescription(int functionNo, String name, String explanation, int displayOrder,
			boolean defaultValue) {
		super(functionNo, name, explanation, displayOrder, defaultValue);
	}

}
