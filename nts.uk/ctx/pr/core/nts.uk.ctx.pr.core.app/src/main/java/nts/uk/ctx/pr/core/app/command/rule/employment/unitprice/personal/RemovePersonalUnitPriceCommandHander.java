package nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.personal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal.PersonalUnitPrice;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal.PersonalUnitPriceRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh
 *
 */
@Stateless
@Transactional
public class RemovePersonalUnitPriceCommandHander extends CommandHandler<RemovePersonalUnitPriceCommand> {

	@Inject
	private PersonalUnitPriceRepository personalUnitPriceRepository;

	@Override
	protected void handle(CommandHandlerContext<RemovePersonalUnitPriceCommand> context) {
		RemovePersonalUnitPriceCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();

		// Check exist personal unit price
		Optional<PersonalUnitPrice> unitPrice = personalUnitPriceRepository.find(companyCode,
				command.getPersonalUnitPriceCode());
		if (!unitPrice.isPresent()) {
			throw new BusinessException("対象が検索できません");
		}

		personalUnitPriceRepository.remove(companyCode, command.getPersonalUnitPriceCode());
	}

}
