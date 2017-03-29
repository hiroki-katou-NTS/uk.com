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

@Stateless
@Transactional
public class UpdatePersonalUnitPriceCommandHander extends CommandHandler<UpdatePersonalUnitPriceCommand> {
	@Inject
	private PersonalUnitPriceRepository personalUnitPriceRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdatePersonalUnitPriceCommand> context) {
		UpdatePersonalUnitPriceCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();

		PersonalUnitPrice domain = PersonalUnitPrice.createFromJavaType(companyCode, command.getPersonalUnitPriceCode(),
				command.getPersonalUnitPriceName(), command.getPersonalUnitPriceShortName(), command.getUniteCode(),
				command.getMemo(), command.getFixPaymentAtr(), command.getFixPaymentMonthly(),
				command.getFixPaymentDayMonth(), command.getFixPaymentDaily(), command.getFixPaymentHoursly(),
				command.getDisplaySet(), command.getPaymentSettingType(), command.getUnitPriceAtr());
		domain.validate();
		
		// Check exist of data to be updated base on personalUnitPriceCode.
		Optional<PersonalUnitPrice> unitPrice = personalUnitPriceRepository.find(companyCode,
				command.getPersonalUnitPriceCode());
		if (!unitPrice.isPresent()) {
			throw new BusinessException("ER026");
		}
		personalUnitPriceRepository.update(domain);
	}

}
