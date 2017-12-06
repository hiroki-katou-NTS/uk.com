package nts.uk.ctx.sys.auth.app.find.grant.roleindividual;

import java.util.List;

import lombok.Value;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.UserDto;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;

@Value
public class RoleIndividualDto {
   
	private String companyIDSysAdmin; 
	private List<EnumConstant> enumRoleType;
	private List<CompanyImport> listCompany;
	//private List<PersonImport> listPerson;
	private List<Object> listGrantDto; 
}   
