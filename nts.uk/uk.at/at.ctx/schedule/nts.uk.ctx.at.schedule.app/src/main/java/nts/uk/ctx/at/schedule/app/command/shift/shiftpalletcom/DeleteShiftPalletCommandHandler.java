package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteComRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrgRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hieult
 *
 */
@Stateless
public class DeleteShiftPalletCommandHandler extends CommandHandler<DeleteShiftPalletComCommand>{

	@Inject
	private ShiftPaletteComRepository repocom;
	
	@Inject
	private ShiftPaletteOrgRepository repoOrg;

	@Override
	
	protected void handle(CommandHandlerContext<DeleteShiftPalletComCommand> context) {
		//<<Command>> 会社別シフトパレットを削除する	
		DeleteShiftPalletComCommand command = context.getCommand();
		if (StringUtil.isNullOrEmpty(command.getWorkplaceId(), true)) {
			String companyId = AppContexts.user().companyId();
			//<<Command>> 会社別シフトパレットを削除する															
			repocom.deleteByPage(companyId, command.getGroupNo());
		} else {
		//<<Command>> 職場で使うシフトパレットを削除する	
			String workplaceId = command.getWorkplaceId();
			repoOrg.deleteByWorkPlaceId(workplaceId, command.getGroupNo());
		}
		
	}
}
