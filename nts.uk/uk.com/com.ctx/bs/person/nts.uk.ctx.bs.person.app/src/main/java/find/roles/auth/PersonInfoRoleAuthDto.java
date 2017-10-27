package find.roles.auth;

import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.role.auth.PersonInfoRoleAuth;
@Value
public class PersonInfoRoleAuthDto {
	private String roleId;
	private int allowMapUpload;
	private int allowMapBrowse;
	private int allowDocRef;
	private int allowDocUpload;
	private int allowAvatarUpload;
	private int allowAvatarRef;
	public static PersonInfoRoleAuthDto fromDomain(PersonInfoRoleAuth domain) {
		return new PersonInfoRoleAuthDto(
				domain.getRoleId(), domain.getAllowMapUpload().value, 
				domain.getAllowMapBrowse().value,domain.getAllowDocRef().value,
				domain.getAllowDocUpload().value, domain.getAllowAvatarUpload().value,
				domain.getAllowAvatarRef().value);
		
	}
}
