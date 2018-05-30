package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataService;
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
		String newIDsplit = IdentifierUtil.randomUniqueId();
		int stateAtr = DigestionAtr.UNUSED.value;
		boolean unknowDate = false;
		if (command.getRemainDays().equals(0)) {
			stateAtr = DigestionAtr.USED.value;
		}
		PayoutManagementData payMana = new PayoutManagementData(newIDPayout,cId, command.getEmployeeId(), unknowDate, command.getDayOff(), command.getExpiredDate(), command.getLawAtr(),
				command.getOccurredDays(), command.getRemainDays(), stateAtr);
		SubstitutionOfHDManagementData subMana = new SubstitutionOfHDManagementData(newIDSub, cId, command.getEmployeeId(), unknowDate, command.getSubDayoffDate(), command.getSubDays(),command.getPickUp() ? command.getRemainDays():command.getSubDays());
		SubstitutionOfHDManagementData splitMana = new SubstitutionOfHDManagementData(newIDsplit, cId, command.getEmployeeId(), unknowDate, command.getHolidayDate(), command.getRequiredDays(),command.getPickUp() ? command.getRemainDays(): command.getRequiredDays());
		List<String> error = payoutManaDataService.addPayoutManagement(command.getPickUp(), command.getPause(), command.getCheckedSplit(), payMana, subMana, splitMana,
				command.getRequiredDays(), command.getClosureId());
		return error;
	}



}
