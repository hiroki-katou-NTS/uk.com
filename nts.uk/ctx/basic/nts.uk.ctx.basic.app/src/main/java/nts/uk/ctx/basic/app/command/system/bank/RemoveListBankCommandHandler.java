package nts.uk.ctx.basic.app.command.system.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.basic.dom.system.bank.Bank;
import nts.uk.ctx.basic.dom.system.bank.BankCode;
import nts.uk.ctx.basic.dom.system.bank.BankRepository;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranch;
import nts.uk.ctx.basic.dom.system.bank.branch.BankBranchRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class RemoveListBankCommandHandler extends CommandHandler<RemoveListBankCommand> {

	@Inject
	private BankRepository bankRepository;

	@Inject
	private PersonBankAccountRepository personBankAccountRepository;

	@Inject
	private BankBranchRepository banhBranchRepo;

	@Override
	protected void handle(CommandHandlerContext<RemoveListBankCommand> context) {

		RemoveListBankCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		
		List<String> bankDeleted = new ArrayList<>();
		
		command.getBank().forEach(bank -> {
			if (StringUtil.isNullOrEmpty(bank.getBranchCode(), true)) {
				Optional<Bank> domain = bankRepository.find(companyCode, bank.getBankCode());
				if (domain.isPresent()) {
					List<String> bankCodeList = new ArrayList<String>();
					bankCodeList.add(bank.getBankCode());

					// check exists person bank account
					if (personBankAccountRepository.checkExistsBankAccount(companyCode, bankCodeList)) {
						throw new BusinessException("ER008"); // ER008
					}

					List<BankBranch> branchAll = banhBranchRepo.findAll(companyCode, new BankCode(bank.getBankCode()));
					if (!branchAll.isEmpty()) {
						branchAll.forEach(item -> {
							banhBranchRepo.remove(companyCode, bank.getBankCode(), item.getBankBranchCode().v());
						});
					}
					bankRepository.remove(domain.get());
					bankDeleted.add(bank.getBankCode());
				}
			} else if (!bankDeleted.contains(bank.getBankCode())) {
				banhBranchRepo.remove(companyCode, bank.getBankCode(), bank.getBranchCode());
			}
		});
	}

}
