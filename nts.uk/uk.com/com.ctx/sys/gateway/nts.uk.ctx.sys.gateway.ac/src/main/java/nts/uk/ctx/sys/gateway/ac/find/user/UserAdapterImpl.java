/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.ac.find.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.pub.user.UserExport;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImport;

/**
 * The Class UserAdapterImpl.
 */
@Stateless
public class UserAdapterImpl implements UserAdapter {
	
	/** The user publisher. */
	@Inject
	private UserPublisher userPublisher;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter
	 * #findUserByContractAndLoginId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<UserImport> findUserByContractAndLoginId(String contractCode, String loginId) {
		Optional<UserExport> user = this.userPublisher.getUserByContractAndLoginId(contractCode, loginId);
		
		// Check found or not!
		if (user.isPresent()) {
			return this.covertToImportDomain(user);
		}
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter
	 * #findUserByAssociateId(java.lang.String)
	 */
	@Override
	public Optional<UserImport> findUserByAssociateId(String associatePersonId) {
		Optional<UserExport> user = this.userPublisher.getUserByAssociateId(associatePersonId);
		
		// Check found or not!
		if (user.isPresent()) {
			return this.covertToImportDomain(user);
		}
		return Optional.empty();
	}
	
	/**
	 * Covert to import domain.
	 *
	 * @param user the user
	 * @return the optional
	 */
	private Optional<UserImport> covertToImportDomain(Optional<UserExport> user) {
		UserExport userInfo = user.get();
		return Optional.of(UserImport.builder()
				.userId(userInfo.getUserID())
				.userName(userInfo.getUserName())
				.mailAddress(userInfo.getMailAddress())
				.loginId(userInfo.getLoginID())
				.associatePersonId(userInfo.getAssociatedPersonID())
				.password(userInfo.getPassword())
				.expirationDate(userInfo.getExpirationDate())
				.contractCode(userInfo.getContractCode())
				.build());
	}



	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter#getListUsersByListPersonIds(java.util.List)
	 */
	@Override
	public List<UserImport> getListUsersByListPersonIds(List<String> listPersonIds) {
		return this.userPublisher.getListUserByListAsId(listPersonIds).stream().map(userInfo -> 		
		UserImport.builder()
		.userId(userInfo.getUserID())
		.userName(userInfo.getUserName())
		.mailAddress(userInfo.getMailAddress())
		.loginId(userInfo.getLoginID())
		.associatePersonId(userInfo.getAssociatedPersonID())
		.password(userInfo.getPassword())
		.expirationDate(userInfo.getExpirationDate())
		.contractCode(userInfo.getContractCode())
		.build()).collect(Collectors.toList());
	}

	@Override
	public Optional<UserImport> findByUserId(String userId) {
		Optional<UserExport> optUserExport = this.userPublisher.getByUserId(userId);
		if (optUserExport.isPresent()) {
			return this.covertToImportDomain(optUserExport);
		}
		return Optional.empty();
	}

}
