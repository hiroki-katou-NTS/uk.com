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
import nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto.RoleTypeDto;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonImport;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrant;
import nts.uk.ctx.sys.auth.dom.grant.roleindividual.RoleIndividualGrantRepository;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserName;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;

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

	private final String COMPANY_ID_SYSADMIN = "000000000000-0000";

	public RoleIndividualDto findByCompanyAndRoleType(String companyID, int roleType) {

		// Get list RoleIndividualGrant
		if (roleType != RoleType.COMPANY_MANAGER.value)
			companyID = COMPANY_ID_SYSADMIN;
		
		List<RoleIndividualGrant> listRoleIndividualGrant = roleIndividualGrantRepo.findByCompanyIdAndRoleType(companyID, roleType);
		if (listRoleIndividualGrant.isEmpty()) {
			return new RoleIndividualDto(COMPANY_ID_SYSADMIN, new ArrayList<RoleIndividualGrantDto>());
		}

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
					roleIndividualGrant.getRoleId(),
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

	public List<RoleTypeDto> GetRoleType(){
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;
		
		List<RoleTypeDto> roleTypeDtos = new ArrayList<>();
		for (RoleType r : RoleType.values()) {
			roleTypeDtos.add(new RoleTypeDto(r.value, r.nameId, r.description));
		}
		return roleTypeDtos;
	}
	
	public List<RoleIndividualGrantDto> getRoleGrants(String roleId) {
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;
		List<RoleIndividualGrantDto> rGrants = new ArrayList<>();
		
		if(roleId == null)
			return rGrants;
		
		List<RoleIndividualGrant> ListRoleGrants = new ArrayList<>();
		ListRoleGrants = this.roleIndividualGrantRepo.findByCompanyRole(companyId, roleId);

		List<String> userId = ListRoleGrants.stream().map(c -> c.getUserId()).distinct().collect(Collectors.toList());
        
        if(userId.size()==0){
			return rGrants;
		}
        List<User> listUsers = userRepo.getByListUser(userId);
        
        List<String> userIdRequest = new ArrayList<String>();
        for(User user : listUsers){
        	if(user.getAssociatedPersonID() != null){
        		userIdRequest.add(user.getAssociatedPersonID());
        	}
        }
        
        if(userIdRequest.size() > 0){
        	List<PersonImport> listPerson = personAdapter.findByPersonIds(userIdRequest);
        	for(User user:listUsers){
        		if(user.getAssociatedPersonID() != null){
	        		for(PersonImport person: listPerson){
	        			if(user.getAssociatedPersonID().equals(person.getPersonId())){
	        				user.setUserName(new UserName(person.getPersonName()));
	        			}
	        		}
        		}
        	}
        }
		
		for (RoleIndividualGrant rGrant : ListRoleGrants) {
            User user = listUsers.stream().filter(c -> c.getUserID().equals(rGrant.getUserId())).findFirst().get();
            rGrants.add(RoleIndividualGrantDto.fromDomain(rGrant, user.getUserName().v(), user.getLoginID().v()));

		}
		return rGrants;

	}
	
	public RoleIndividualGrantDto getRoleGrant(String userId, String roleId){
		String companyId = AppContexts.user().companyId();
		if (companyId == null)
			return null;
		
		if (userId == null || roleId == null)
			return null;
		
		Optional<RoleIndividualGrant> rGrant = this.roleIndividualGrantRepo.findByKey(userId, companyId, roleId);
		
		try {
			Optional<User> user = userRepo.getByUserID(rGrant.get().getUserId());
			if(user.get().getAssociatedPersonID() != null){
				List<String> PersonID = new ArrayList<>();
				PersonID.add(user.get().getAssociatedPersonID());
				
				List<PersonImport> listPerson = personAdapter.findByPersonIds(PersonID);
				if(listPerson.size()>0){
					user.get().setUserName(new UserName(listPerson.get(0).getPersonName()));
				}
			}
			return RoleIndividualGrantDto.fromDomain(rGrant.get(), user.get().getUserName().v(), user.get().getLoginID().v());
		} catch (Exception e) {
			return null;
		}
	}
	
}
