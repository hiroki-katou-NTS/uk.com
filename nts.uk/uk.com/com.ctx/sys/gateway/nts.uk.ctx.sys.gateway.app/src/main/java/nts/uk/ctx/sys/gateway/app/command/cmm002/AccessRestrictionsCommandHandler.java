package nts.uk.ctx.sys.gateway.app.command.cmm002;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.gateway.dom.accessrestrictions.AccessRestrictions;
import nts.uk.ctx.sys.gateway.dom.accessrestrictions.AccessRestrictionsRepository;
import nts.uk.ctx.sys.gateway.dom.loginold.ContractCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class AccessRestrictionsCommandHandler {

	@Inject
	private AccessRestrictionsRepository repo;
	
	/**
	 * アクセス制限を追加する
	 */
	public void insertAccessRestrictions() {
		String contractCode = AppContexts.user().contractCode();
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		if (!domain.isPresent()) {
			/*
			 * 1: create()
			 * 2: persist()
			 */
			repo.insert(new AccessRestrictions(NotUseAtr.NOT_USE, new ContractCode(contractCode), new ArrayList<>()));
		}
	}
	
	/**
	 * 許可するIPアドレスを追加する
	 */
	public void addAllowdIpAddress(AllowedIPAddressUpdateCommand command) {
		String contractCode = AppContexts.user().contractCode();
		/* 1:get(ログイン者の契約コード) */
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		if (domain.isPresent()) {
			domain.get().addIPAddress(command.allowedIPAddressNew.toDomain());
			/* 2:persist() */
			repo.update(new AccessRestrictions(command.accessLimitUseAtr, contractCode, domain.get().getAllowedIPaddress()));
		}
	}
	
	/**
	 * IPアドレスの制限設定を更新する
	 */
	public void updateAllowdIpAddress(AllowedIPAddressUpdateCommand command) {
		String contractCode = AppContexts.user().contractCode();
		/* 1:get(ログイン者の契約コード) */
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		/* 2:persist() */
		if (domain.isPresent()) {
			/* 3:許可IPアドレスを更新する(許可IPアドレス_NEW) */
			domain.get().updateIPAddress(command.allowedIPAddressOld.toDomain(), command.allowedIPAddressNew.toDomain());
			/* 4:persist() */
			repo.update(new AccessRestrictions(command.accessLimitUseAtr, contractCode, domain.get().getAllowedIPaddress()));
		}
	}
	
	/**
	 * 許可するIPアドレスを削除する
	 */
	public void deleteAllowdIpAddress(AllowedIPAddressCommand command) {
		String contractCode = AppContexts.user().contractCode();
		/* 1: get(ログイン者の契約コード) */
		Optional<AccessRestrictions> domain = repo.get(new ContractCode(contractCode));
		if (domain.isPresent()) {
			/* 2: 許可IPアドレスを削除する(IPアドレス) */
			domain.get().deleteIPAddress(command.toDomain().getStartAddress());
			/* 3:persist() */
			repo.update(domain.get());
		}
	}
}
