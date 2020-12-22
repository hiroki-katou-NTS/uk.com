package nts.uk.ctx.at.schedule.app.command.budget.premium;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.UpdateHistPersonCostCalculationCommand;
import nts.uk.ctx.at.schedule.dom.budget.premium.*;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;


@Stateless
@Transactional
public class UpdateHistPersonCostCalculationCommandHandler extends CommandHandler<UpdateHistPersonCostCalculationCommand> {
    @Inject
    PersonCostCalculationDomainService service;

    @Override
    protected void handle(CommandHandlerContext<UpdateHistPersonCostCalculationCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val priceRounding = command.getPersonCostRoundingSetting().getUnitPriceRounding();
        val roundingOfPremium = new UnitPriceRoundingSetting(EnumAdaptor.valueOf(priceRounding, UnitPriceRounding.class));
        val unit = command.getPersonCostRoundingSetting().getUnit();
        val rounding = command.getPersonCostRoundingSetting().getRounding();
        val amountRoundingSetting = new AmountRoundingSetting(AmountUnit.valueOf(unit), AmountRounding.valueOf(rounding));
        val roundingSetting = new PersonCostRoundingSetting(roundingOfPremium, amountRoundingSetting);
        val cid = AppContexts.user().companyId();
        val unitPrice = EnumAdaptor.valueOf(command.getUnitPrice(), UnitPrice.class);
        val domain = new PersonCostCalculation(
                roundingSetting,
                cid,
                new Remarks(command.getRemarks()),
                null
                ,
                Optional.of(unitPrice),
                EnumAdaptor.valueOf(command.getHowToSetUnitPrice(), HowToSetUnitPrice.class),
                new WorkingHoursUnitPrice(command.getWorkingHoursUnitPrice()),
                command.getHistoryId()
        );
        DatePeriod period = new DatePeriod(command.getStartDate(), command.getEndDate());
        service.updateHistPersonCalculation(domain,command.historyId, period);

    }
}
