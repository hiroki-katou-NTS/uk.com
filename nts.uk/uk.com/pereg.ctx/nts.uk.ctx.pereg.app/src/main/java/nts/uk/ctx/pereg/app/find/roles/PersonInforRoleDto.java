package nts.uk.ctx.pereg.app.find.roles;
import lombok.Value;
import nts.uk.ctx.pereg.dom.roles.PersonInforRole;
/**
 * The Class PersonInforRoleDto
 * @author lanlt
 *
 */
@Value
public class PersonInforRoleDto {
	private String companyId;
	private String roleId;
	private String roleCode;
	private String roleName;
	
	public static PersonInforRoleDto fromDomain(PersonInforRole domain) {
		
		return new PersonInforRoleDto(domain.getCompanyId(),domain.getRoleId(), domain.getRoleCode().v(), domain.getRoleName().v());
		
	}
}
