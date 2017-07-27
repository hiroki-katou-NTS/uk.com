package find.roles;
import lombok.Value;
import nts.uk.ctx.bs.person.dom.person.role.PersonInforRole;
/**
 * The Class PersonInforRoleDto
 * @author lanlt
 *
 */
@Value
public class PersonInforRoleDto {
	String companyId;
	String roleId;
	String roleCode;
	String roleName;
	
	public static PersonInforRoleDto fromDomain(PersonInforRole domain) {
		
		return new PersonInforRoleDto(domain.getCompanyId(),domain.getRoleId(), domain.getRoleCode().v(), domain.getRoleName().v());
		
	}
}
