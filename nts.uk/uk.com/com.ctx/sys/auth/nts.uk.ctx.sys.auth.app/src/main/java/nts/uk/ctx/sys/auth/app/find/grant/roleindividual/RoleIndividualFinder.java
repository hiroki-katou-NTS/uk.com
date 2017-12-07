package nts.uk.ctx.sys.auth.app.find.grant.roleindividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.RoleIndividualGrantDto;
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.RoleIndividualGrantMetaDto;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

@Stateless
public class RoleIndividualFinder {

	@Inject
	private RoleIndividualGrantRepository roleIndividualGrantRepo;

	@Inject
	private UserRepository userRepo;

	@Inject
	private PersonAdapter personAdapter;
	
    @Inject
    private CompanyAdapter companyAdapter;

	private final String COMPANY_ID_SYSADMIN = "00000000000000000";

	public RoleIndividualDto findByCompanyAndRoleType(String companyID, int roleType) {

		// Get list RoleIndividualGrant
		List<RoleIndividualGrant> listRoleIndividualGrant = roleIndividualGrantRepo.findByCompanyIdAndRoleType(companyID, roleType);

		// Get list User information
		List<String> listUserID = listRoleIndividualGrant.stream().map(c -> c.getUserId()).distinct().collect(Collectors.toList());
		List<User> listUser = userRepo.getByListUser(listUserID);
		List<String> listAssPersonID = listUser.stream().map(c -> c.getAssociatedPersonID()).distinct().collect(Collectors.toList());
		List<PersonImport> listPerson = personAdapter.findByPersonIds(listAssPersonID);
		
		// Build RoleIndividualGrantDto
		List<RoleIndividualGrantDto> listRoleIndividualGrantDto = new ArrayList<>();
		for (RoleIndividualGrant roleIndividualGrant: listRoleIndividualGrant) {
			// Filter get User
			User user = listUser.stream().filter(c -> c.getUserID().equals(roleIndividualGrant.getUserId())).findFirst().get();
			String userName = user.getUserName().v();
			String loginID = user.getLoginID().v();
			// Filter get Person
			if (!StringUtil.isNullOrEmpty(user.getAssociatedPersonID(), false)) {
				Optional<PersonImport> optPerson = listPerson.stream().filter(c -> c.getPersonId().equals(user.getAssociatedPersonID())).findFirst();
				if (optPerson.isPresent())
					userName = optPerson.get().getPersonName();
			}
		  
			// Add to list
			RoleIndividualGrantDto dto = new RoleIndividualGrantDto(
					roleIndividualGrant.getCompanyId(),
					roleIndividualGrant.getRoleType().value,
					loginID,
					roleIndividualGrant.getUserId(), 
					userName,
					roleIndividualGrant.getValidPeriod().start(), 
					roleIndividualGrant.getValidPeriod().end());
			listRoleIndividualGrantDto.add(dto);
		}

		return new RoleIndividualDto(COMPANY_ID_SYSADMIN, listRoleIndividualGrantDto);

	}
	
	public RoleIndividualGrantMetaDto getMetadata() {
		
		// Get List Enum RoleType
		List<EnumConstant> enumRoleType = EnumAdaptor.convertToValueNameList(RoleType.class,  RoleType.SYSTEM_MANAGER, RoleType.COMPANY_MANAGER, RoleType.GROUP_COMAPNY_MANAGER);
		
		// Get list Company Information
		List<CompanyImport> listCompanyImport = companyAdapter.findAllCompany();

		return new RoleIndividualGrantMetaDto(enumRoleType, listCompanyImport);
	}

}
