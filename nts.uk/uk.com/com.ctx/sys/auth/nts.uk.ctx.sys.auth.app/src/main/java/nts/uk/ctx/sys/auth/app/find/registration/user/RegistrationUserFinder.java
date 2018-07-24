package nts.uk.ctx.sys.auth.app.find.registration.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.company.CompanyImport;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInfoAdapter;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class RegistrationUserFinder {
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;
	
	@Inject
	private UserRepository userRepo;

	public List<CompanyImportDto> getCompanyImportList() {
		List<CompanyImport> listCompanyImport = new ArrayList<>();
		LoginUserContext user = AppContexts.user();
		if(user.roles().forSystemAdmin() != null) {
			// Get list Company Information
			listCompanyImport = companyAdapter.findAllCompanyImport();
		}
		else {
			// get company by cid
			listCompanyImport.add(companyAdapter.findCompanyByCid(user.companyId()));
		}
		return listCompanyImport.stream().map(c -> CompanyImportDto.fromDomain(c)).collect(Collectors.toList());
	}
	
	public List<UserDto> getLoginUserListByCurrentCID(String cid) {
//		String cid = AppContexts.user().companyId();
		// get list Associated Person ID = EmployeeInfoImport. Personal ID
		List<String> listAssociatePersonId = new ArrayList<>();
		employeeInfoAdapter.getEmployeesAtWorkByBaseDate(cid, GeneralDate.today()).stream().forEach(c -> listAssociatePersonId.add(c.getPersonId()));
		return userRepo.getListUserByListAsID(listAssociatePersonId).stream().map(c -> UserDto.fromDomain(c)).collect(Collectors.toList());
	}

	public List<UserDto> getLoginUserListByContractCode() {
		return userRepo.getByContractCode(AppContexts.user().contractCode()).stream().map(c -> UserDto.fromDomain(c)).collect(Collectors.toList());
	}
	
	public UserDto getUserByUserId(String userId) {
		if(!userRepo.getByUserID(userId).isPresent())
			return null;
		return UserDto.fromDomain(userRepo.getByUserID(userId).get());
	}

}
