package nts.uk.ctx.pereg.infra.entity.roles.functionauth;

import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.shr.com.permit.RestoreAvailabilityPermission;
import nts.uk.shr.infra.permit.data.JpaEntityOfAvailabilityPermissionBase;

@Table(name = "PPEMT_ROLE_AUTH")
@Entity
public class PpemtPersonInfoAuth extends JpaEntityOfAvailabilityPermissionBase<PersonInfoAuthority>
		implements RestoreAvailabilityPermission {

	@Override
	public PersonInfoAuthority toDomain() {
		return new PersonInfoAuthority(this);
	}

}
