/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.securitypolicy.lockoutdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.app.find.securitypolicy.lockoutdata.dto.LockOutDataUserDto;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class LockOutDataUserFinder.
 */
@Stateless
public class LockOutDataUserFinder {
	
	/** The lock out data repository. */
	@Inject
	private LockOutDataRepository lockOutDataRepository;
	
	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;
	
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<LockOutDataUserDto> findAll(){
		
		List<LockOutDataUserDto> lstLockOutDataUserDto = new ArrayList<LockOutDataUserDto>();
		String contractCd = AppContexts.user().contractCode();
		
		List<LockOutData> lstLockOutData = lockOutDataRepository.findByContractCode(contractCd);
		lstLockOutData.forEach(item->{
			LockOutDataUserDto lockOutDataUserDto = new LockOutDataUserDto();
			if(item != null){
				lockOutDataUserDto.setLockOutDateTime(item.getLockOutDateTime());
				lockOutDataUserDto.setLogType((item.getLogType().value));
				lockOutDataUserDto.setUserId(item.getUserId());
			}
			Optional<UserImport> findByUserId = userAdapter.findByUserId(item.getUserId());
			if(findByUserId.isPresent()){
				lockOutDataUserDto.setLoginId(findByUserId.get().getLoginId());
				lockOutDataUserDto.setUserName(findByUserId.get().getUserName());
			}
			lstLockOutDataUserDto.add(lockOutDataUserDto);
			
		});
		
		return lstLockOutDataUserDto;
	}

}
