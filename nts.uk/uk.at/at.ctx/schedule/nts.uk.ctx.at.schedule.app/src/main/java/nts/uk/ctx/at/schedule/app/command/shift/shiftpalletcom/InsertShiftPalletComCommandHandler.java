package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
@Stateless
public class InsertShiftPalletComCommandHandler extends CommandHandler<InsertShiftPalletComCommand> {

	@Inject
	private ShiftPalletsComRepository repo;
	
	@Inject
	private ShiftPalletsOrgRepository orgRepository;
	@Override
	protected void handle(CommandHandlerContext<InsertShiftPalletComCommand> context) {
		// TODO Auto-generated method stub
		InsertShiftPalletComCommand command = context.getCommand();
		ShiftPalletsCom newShiftPalletsCom = command.toDomain();
		ShiftPalletsOrg newShiftPalletsOrg = command.toDom();
		if (command.workplaceId.isEmpty()) {
			repo.add(newShiftPalletsCom);
		}
		orgRepository.add(newShiftPalletsOrg);

	}

}
