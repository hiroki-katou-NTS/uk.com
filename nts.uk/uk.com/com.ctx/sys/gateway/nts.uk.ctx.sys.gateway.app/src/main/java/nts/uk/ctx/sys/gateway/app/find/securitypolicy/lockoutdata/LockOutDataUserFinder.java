/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.find.securitypolicy.lockoutdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.app.find.securitypolicy.lockoutdata.dto.LockOutDataDto;
import nts.uk.ctx.sys.gateway.app.find.securitypolicy.lockoutdata.dto.LockOutDataUserDto;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutData;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.SearchUser;
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
	public List<LockOutDataUserDto> findAll() {

		List<LockOutDataUserDto> lstLockOutDataUserDto = new ArrayList<LockOutDataUserDto>();
		String contractCd = AppContexts.user().contractCode();
		//get list LockOutData
		List<LockOutData> lstLockOutData = lockOutDataRepository.findByContractCode(contractCd);
			lstLockOutData.forEach(item -> {
				LockOutDataUserDto lockOutDataUserDto = new LockOutDataUserDto();
				if (item != null) {
					lockOutDataUserDto.setLockOutDateTime(item.getLockOutDateTime());
					lockOutDataUserDto.setLogType((item.getLogType().value));
					lockOutDataUserDto.setUserId(item.getUserId());
				}
				Optional<UserImportNew> findByUserId = userAdapter.findByUserId(item.getUserId());
				findByUserId.ifPresent(value -> {
					lockOutDataUserDto.setLoginId(value.getLoginId().trim());
					lockOutDataUserDto.setUserName(value.getUserName().get());
				});
				lstLockOutDataUserDto.add(lockOutDataUserDto);

			});
		return lstLockOutDataUserDto;
	}

	/**
	 * Find and return users by userId
	 * @author Nguyen Van Hanh
	 * @param userId
	 * @return SearchUser
	 */
	public SearchUser findByUserId(String userId) {
		Optional<UserImportNew> user = userAdapter.findByUserId(userId);
		if(user.isPresent())
			return SearchUser.convertToDto(user.get());

		return null;
	}
	
	/**
	 * Find lock out data by user id.
	 *
	 * @param UserId the user id
	 * @return the lock out data dto
	 */
	public LockOutDataDto findLockOutDataByUserId(String UserId) {
		Optional<LockOutData> optLockOutData = lockOutDataRepository.findByUserId(UserId);
		if (!optLockOutData.isPresent()) {
			return null;
		}
		LockOutData lockOutData = optLockOutData.get();
		return new LockOutDataDto(lockOutData.getUserId(), lockOutData.getLockOutDateTime(),
				lockOutData.getLogType().value, lockOutData.getContractCode().v(), lockOutData.getLoginMethod().value);
	}

}
