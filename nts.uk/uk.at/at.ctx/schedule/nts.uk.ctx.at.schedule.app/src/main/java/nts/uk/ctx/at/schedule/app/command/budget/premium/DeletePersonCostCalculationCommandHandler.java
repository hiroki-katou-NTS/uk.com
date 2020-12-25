package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.PersonCostCalculationCommand;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PersonCostCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PersonCostCalculationDomainService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PersonCostRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PremiumName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PremiumSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.UnitPrice;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.UseAttribute;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
@Transactional
public class DeletePersonCostCalculationCommandHandler extends CommandHandler<PersonCostCalculationCommand>{

	@Inject
	private PersonCostCalculationDomainService personCostCalculationDomainService;
	
	@Override
	protected void handle(CommandHandlerContext<PersonCostCalculationCommand> context) {
		String companyID = AppContexts.user().companyId();
		PersonCostCalculationCommand command = context.getCommand();
		this.personCostCalculationDomainService.deletePersonCostCalculation(
				new PersonCostCalculation(
						companyID, 
						command.getHistoryID(), 
						GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd"), 
						GeneralDate.fromString(command.getEndDate(), "yyyy/MM/dd"), 
						EnumAdaptor.valueOf(command.getUnitPrice(), UnitPrice.class),
						new Memo(command.getMemo()),
						command.getPremiumSets().stream()
							.map(x -> new PremiumSetting(
									x.getCompanyID(), 
									x.getHistoryID(), 
									x.getDisplayNumber(), 
									new PremiumRate(x.getRate()), 
									new PremiumName(x.getName()), 
									EnumAdaptor.valueOf(x.getUseAtr(), UseAttribute.class), 
									x.getAttendanceItems().stream().map(y -> y.getShortAttendanceID()).collect(Collectors.toList())))
							.collect(Collectors.toList()),
						PersonCostRoundingSetting.defaultValue() //画面が見直される為、今はデフォルト値を入れています。
				)
		);
	}

}
