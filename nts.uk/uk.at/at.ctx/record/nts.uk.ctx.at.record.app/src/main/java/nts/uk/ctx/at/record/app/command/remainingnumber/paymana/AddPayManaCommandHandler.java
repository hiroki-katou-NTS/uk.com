package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataService;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddPayManaCommandHandler extends CommandHandlerWithResult<PayManaRemainCommand, List<String>>{

	@Inject
	private PayoutManagementDataService payoutManaDataService;
	
	@Override
	protected List<String> handle(CommandHandlerContext<PayManaRemainCommand> context) {
		PayManaRemainCommand command = context.getCommand();
		String cId = AppContexts.user().companyId();
		String newIDPayout = IdentifierUtil.randomUniqueId();
		String newIDSub = IdentifierUtil.randomUniqueId();
		int stateAtr = 0;
		Double UnUsedDays = new Double(0);
		boolean unknowDate = false;
		if (command.getRemainDays() == 0){
			stateAtr = 1;
		}
		PayoutManagementData payMana = new PayoutManagementData(newIDPayout,cId, command.getEmployeeId(), unknowDate, command.getDayOff(), command.getExpiredDate(), command.getLawAtr(),
				command.getOccurredDays(), UnUsedDays, stateAtr);
		SubstitutionOfHDManagementData subMana = new SubstitutionOfHDManagementData(newIDSub, cId, command.getEmployeeId(), unknowDate, command.getSubDayoffDate(), command.getRequiredDays(), command.getRemainDays());
		PayoutSubofHDManagement paySub = new PayoutSubofHDManagement(newIDPayout, newIDSub, new BigDecimal(0), TargetSelectionAtr.MANUAL.value);
		
		List<String> error = payoutManaDataService.addPayoutManagement(command.getPickUp(), command.getPause(), command.getRemainDays(), payMana, subMana, paySub, command.getOccurredDays(), command.getSubDays(), command.getClosureId());
		return error;
	}



}
