package nts.uk.ctx.basic.app.command.system.bank.linebank;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.basic.dom.system.bank.linebank.LineBankRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccount;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonUseSetting;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class TransferLineBankCommandHandler extends CommandHandler<TransferLineBankCommand> {
	@Inject
	private PersonBankAccountRepository personBankAccountRepository;

	@Inject
	private LineBankRepository lineBankRepository;

	@Override
	protected void handle(CommandHandlerContext<TransferLineBankCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		TransferLineBankCommand command = context.getCommand();
		String oldLineBankCode = command.getOldLineBankCode();
		String newLineBankCode = command.getNewLineBankCode();
		int allowDelete = command.getAllowDelete();

		if (StringUtil.isNullOrEmpty(oldLineBankCode, true) || StringUtil.isNullOrEmpty(newLineBankCode, true)) {
			throw new BusinessException("＊が選択されていません。");// ER007
		}

		boolean duplicateCode = oldLineBankCode.equals(newLineBankCode);

		if (duplicateCode) {
			throw new BusinessException("統合元と統合先で同じコードの＊が選択されています。\r\n  ＊を確認してください。");// ER009
		}

		List<PersonBankAccount> listPersonBankAcc = personBankAccountRepository.findAllLineBank(companyCode,
				oldLineBankCode);
		listPersonBankAcc.forEach(x -> {
			PersonBankAccount bankAccount = x;
			PersonUseSetting useSet1 = bankAccount.getUseSet1();
			PersonUseSetting useSet2 = bankAccount.getUseSet2();
			PersonUseSetting useSet3 = bankAccount.getUseSet3();
			PersonUseSetting useSet4 = bankAccount.getUseSet4();
			PersonUseSetting useSet5 = bankAccount.getUseSet5();

			useSet1 = useSet(oldLineBankCode, useSet1, command);
			useSet2 = useSet(oldLineBankCode, useSet2, command);
			useSet3 = useSet(oldLineBankCode, useSet3, command);
			useSet4 = useSet(oldLineBankCode, useSet4, command);
			useSet5 = useSet(oldLineBankCode, useSet5, command);

			PersonBankAccount domain = new PersonBankAccount(companyCode, x.getPersonID(), x.getHistId(),
					x.getStartYearMonth(), x.getEndYearMonth(), useSet1, useSet2, useSet3, useSet4, useSet5);

			personBankAccountRepository.update(domain);
		});
		//allowDelete = 1 la cho phep xoa
		//listPersonBankAcc.isEmpty() kiem tra xem lineBankCode can xoa co ton tai k de xoa
		if (allowDelete==1 && !listPersonBankAcc.isEmpty()) {
			lineBankRepository.remove(companyCode, oldLineBankCode);
		}
	}

	private PersonUseSetting useSet(String lineBankCode, PersonUseSetting bankAccount,
			TransferLineBankCommand command) {
		if (bankAccount.getFromLineBankCd().equals(lineBankCode)) {
			bankAccount.setFromLineBankCd(command.getNewLineBankCode());
		}
		return bankAccount;
	}

}
