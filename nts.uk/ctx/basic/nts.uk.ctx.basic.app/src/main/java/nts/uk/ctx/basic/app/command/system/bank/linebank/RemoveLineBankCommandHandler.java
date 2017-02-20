package nts.uk.ctx.basic.app.command.system.bank.linebank;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.system.bank.linebank.LineBankRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class RemoveLineBankCommandHandler extends CommandHandler<RemoveLineBankCommand> {
	@Inject
	private LineBankRepository lineBankRepository;

	@Inject
	private PersonBankAccountRepository personBankAccountRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveLineBankCommand> context) {
		RemoveLineBankCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		String lineBankCode = context.getCommand().toDomain(companyCode).getLineBankCode().v();
		List<String> lineCodeList = new ArrayList<String>();
		lineCodeList.add(command.getLineBankCode());
		
		// check exists linebank in person_bank_account (xem linebank co ng nao su dung k, neu co ng su dung thi k dc xoa)
		if (personBankAccountRepository.checkExistsLineBankAccount(companyCode, lineCodeList)) {
			throw new BusinessException("ER008");
		}

		lineBankRepository.remove(companyCode, lineBankCode);
	}
}
