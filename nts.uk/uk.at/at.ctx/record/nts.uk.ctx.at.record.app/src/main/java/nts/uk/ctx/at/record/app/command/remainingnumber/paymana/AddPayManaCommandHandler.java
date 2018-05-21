package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataService;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPayManaCommandHandler extends CommandHandler<PayManaRemainCommand>{

	@Inject
	private PayoutManagementDataService payoutManaDataService;
	
	@Override
	protected void handle(CommandHandlerContext<PayManaRemainCommand> context) {
		PayManaRemainCommand command = context.getCommand();
		String cId = AppContexts.user().companyId();
		String newIDPayout = IdentifierUtil.randomUniqueId();
		String newIDSub = IdentifierUtil.randomUniqueId();
		PayoutManagementData payMana = new PayoutManagementData(newIDPayout,cId, command.getSID(), command.getUnknownDate(), command.getDayOff(), command.getExpiredDate(), command.getLawAtr(),
				command.getOccurredDays(), command.getUnUsedDays(), command.getStateAtr());
		SubstitutionOfHDManagementData subMana = new SubstitutionOfHDManagementData(newIDSub, cId, command.getSID(),command.isSubUnknownDate(), command.getSubDayoffDate(), command.getRequiredDays(), command.getRemainDays());
		PayoutSubofHDManagement paySub = new PayoutSubofHDManagement(newIDPayout, newIDSub, new BigDecimal(0), TargetSelectionAtr.MANUAL.value);
		
		payoutManaDataService.addPayoutManagement(command.getPickUp(), command.getPause(), payMana, subMana, paySub);
	}



}
