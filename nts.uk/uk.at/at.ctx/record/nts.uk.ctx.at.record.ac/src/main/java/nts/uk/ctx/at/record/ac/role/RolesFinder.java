package nts.uk.ctx.at.record.ac.role;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.role.Role;
import nts.uk.ctx.at.record.dom.workrecord.role.RoleAdapter;


@Stateless
public class RolesFinder implements RoleAdapter{

	@Override
	public List<Role> getRolesById(String companyId, List<String> roleIds) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
