package nts.uk.ctx.basic.pubimp.system.bank.personaccount;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.ctx.basic.pub.system.bank.personaccount.PersonBankAccountPub;

@RequestScoped
public class PersonBankAccountPubImp implements PersonBankAccountPub {
	@Inject
	private PersonBankAccountRepository repository;
	
	@Override
	public boolean checkExistsBankAccount(String companyCode, List<String> bankCodeList) {
		return repository.checkExistsBankAccount(companyCode, bankCodeList);
	}

}
