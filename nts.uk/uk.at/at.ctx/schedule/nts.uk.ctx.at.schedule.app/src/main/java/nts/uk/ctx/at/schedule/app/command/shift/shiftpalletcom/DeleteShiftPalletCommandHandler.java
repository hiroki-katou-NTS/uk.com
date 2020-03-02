package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteShiftPalletCommandHandler extends CommandHandler<DeleteShiftPalletComCommand>{

	@Inject
	private ShiftPalletsComRepository repocom;
	
	@Inject
	private ShiftPalletsOrgRepository repoOrg;

	@Override
	
	protected void handle(CommandHandlerContext<DeleteShiftPalletComCommand> context) {
		DeleteShiftPalletComCommand command = context.getCommand();
		if (StringUtil.isNullOrEmpty(command.getWorkplaceId(), true)) {
			String companyId = AppContexts.user().companyId();
			//<<Command>> 会社別シフトパレットを削除する															
			repocom.deleteByPage(companyId, command.getGroupNo());
		} else {
			String workplaceId = command.getWorkplaceId();
			//<<Command>> 職場で使うシフトパレットを削除する
			repoOrg.deleteByWorkPlaceId(workplaceId, command.getGroupNo());
		}
		
	}
}
