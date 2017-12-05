package nts.uk.ctx.sys.auth.app.find.grant.roleindividual;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.RoleIndividualGrantDto;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.UserDto;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualComService;
import nts.uk.ctx.sys.auth.dom.grant.service.RoleIndividualService;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

@Stateless
public class RoleIndividualFinder {

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;

	@Inject
	private RoleIndividualService roleIndividualService;
	@Inject
	private UserRepository userRepo;
	
	

	public RoleIndividualDto getScreenResult(String companyID , int roleType) {
         //String companyIDSysAdmin
		String companyIDSysAdmin = "00000000000000000";
		
		// Get List Enum ComboBox
		List<EnumConstant> enumRoleType = EnumAdaptor.convertToValueNameList(RoleType.class);
		//GetList
		List<CompanyImport> listCompanyImport = roleIndividualService.selectCompany();
		
		//GetList
		//List<PersonImport> listPerson = roleIndividualService.selectRoleType(companyID, roleType);
		List<RoleIndividualGrant> listRoleIndividualGrant= roleIndividualService.selectRoleType(companyID, roleType);
		List<RoleIndividualGrantDto> listRoleIndividualGrantDto = listRoleIndividualGrant.stream().map(c -> RoleIndividualGrantDto.fromDomain(c)).collect(Collectors.toList());
		List<String> listUserID = listRoleIndividualGrant.stream().map(c -> c.getUserId()).collect(Collectors.toList());
		List<User> listUser = userRepo.getByListUser(listUserID);
		List<UserDto> listUserDto = listUser.stream().map(c-> UserDto.fromDomain(c)).collect(Collectors.toList());
		//Add List listGrant listUserDto
		List<Object> listGrantDto = Stream.concat(listRoleIndividualGrantDto.stream(),listUserDto.stream()).collect(Collectors.toList());
		return new RoleIndividualDto(companyIDSysAdmin,enumRoleType,listCompanyImport, listGrantDto) ;

	}
	public RoleIndividualDto getCompany(){
		List<CompanyImport> listCompanyImport = roleIndividualService.selectCompany();
		return new RoleIndividualDto(null,null,listCompanyImport,null);
		
	}
}
