/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.singlesignon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.employee.EmployeeInfoDtoImport;
import nts.uk.ctx.sys.gateway.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.person.PersonInfoImport;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.OtherSysAccountRepository;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UserFinder.
 */
@Stateless
public class UserInfoFinder {

	/** The person info adapter. */
	@Inject
	private PersonInfoAdapter personInfoAdapter;

	/** The employee info adapter. */
	@Inject
	private EmployeeInfoAdapter employeeInfoAdapter;

	/** The window account repository. */
	@Inject
	private WindowAccountRepository windowAccountRepository;
	
	/** The other sys account repository. */
	@Inject
	private OtherSysAccountRepository otherSysAccountRepository;
	
	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;

	/**
	 * Find list user info.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	public List<UserDto> findListUserInfo(GeneralDate baseDate, Boolean isScreenC) {

		// Get Company Id
		String companyId = AppContexts.user().companyId();

		// get employee code
		List<EmployeeInfoDtoImport> listEmployee = this.employeeInfoAdapter.getEmployeesAtWorkByBaseDate(companyId,
				baseDate);
		
		List<String> listPersonId = new ArrayList<>();
		List<UserDto> listUserMap = new ArrayList<>();
		List<UserImport> listUser = new ArrayList<>();
		List<UserDto> listUserAccount = new ArrayList<>();
		Set<String> listSubPersonId = new HashSet<>();		

		// Step 1 - add employee info
		// check listEmployee is empty
		if(listEmployee.isEmpty()){
			return listUserMap;			
		}		
		listEmployee.forEach(employee -> {
			UserDto userDto = new UserDto();
			userDto.setEmployeeCode(employee.getEmployeeCode());
			userDto.setPersonId(employee.getPersonId());
			userDto.setEmployeeId(employee.getEmployeeId());
			listUserAccount.add(userDto);
			listSubPersonId.add(employee.getPersonId());
		});
		
		// check listSubPersonId is empty
		if (listSubPersonId.isEmpty()) {
			return listUserMap;
		}
		
		// reject duplicate element, remove element == null or element is empty
		listPersonId = listSubPersonId.stream().filter(personId -> (personId != null && !personId.isEmpty())).distinct()
				.collect(Collectors.toList());
		
		// Step 2 - add person info
		List<PersonInfoImport> listPerson = this.personInfoAdapter.getListPersonInfo(listPersonId);
		
		// check listPerson is empty
		if(listPerson.isEmpty()){
			return listUserMap;
		}
		Map<String, PersonInfoImport> mapPerson = listPerson.stream()
				.collect(Collectors.toMap(PersonInfoImport::getPersonId, Function.identity()));

		listUserAccount.forEach(item -> {
			PersonInfoImport personInfoImport = mapPerson.get(item.getPersonId());
			if (personInfoImport != null) {
				item.setPersonName(personInfoImport.getPersonName());
			}
		});

		// Step 3 - add user info
		for (String personId : listPersonId) {
			Optional<UserImport> user = userAdapter.findUserByAssociateId(personId);
			if (user.isPresent()) {
				listUser.add(user.get());
			}
		}
		
		// check list user is empty
		if(listUser.isEmpty()){
			return listUserMap;
		}
		Map<String, UserImport> mapUser = listUser.stream()
				.collect(Collectors.toMap(UserImport::getAssociatePersonId, Function.identity()));

		listUserAccount.forEach(item -> {
			UserImport user = mapUser.get(item.getPersonId());
			if (user != null) {
				item.setLoginId(user.getLoginId().toString());
				item.setUserId(user.getUserId());
			}

		});

		listUserAccount.forEach(item -> {
			if (mapUser.get(item.getPersonId()) != null && mapPerson.get(item.getPersonId()) != null) {
				listUserMap.add(item);
			}
		});

		return loadUserSetting(listUserMap, isScreenC);
				
	}
	
	public List<UserDto> loadUserSetting(List<UserDto> listUserMap, Boolean isScreenC){	
		
		if(isScreenC == true){		
			listUserMap.forEach(w -> {			
				Optional<OtherSysAccount> opOtherSysAcc = otherSysAccountRepository.findByUserId(w.getUserId());				
				if(opOtherSysAcc.isPresent()){
					w.setIsSetting(true);
				}else{
					w.setIsSetting(false);
				}
			});	
				
		}else{		
			listUserMap.forEach(w -> {
				List<WindowAccount> winAcc = this.windowAccountRepository.findByUserId(w.getUserId());

				if (!winAcc.isEmpty()) {
					w.setIsSetting(true);
				} else {
					w.setIsSetting(false);
				}
			});
		}
		return listUserMap;
	}	
}
