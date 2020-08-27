package nts.uk.ctx.at.schedule.app.command.shiftmanagement.shiftwork.shiftpalet;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.shift.management.CopyShiftPaletteByOrgService;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * <<Command>> Jd：組織別シフトパレットの複製 or 組織別シフトパレットを複製する (OCD)
 *
 */
@Stateless
public class DuplicateOrgShiftPaletHandler extends CommandHandler<DuplicateOrgShiftPaletCommand> {

	@Inject
	private ShiftPalletsOrgRepository shiftPalletsOrgRepository;

	@Override
	protected void handle(CommandHandlerContext<DuplicateOrgShiftPaletCommand> context) {
		DuplicateOrgShiftPaletCommand command = context.getCommand();
		// 1:
		Optional<ShiftPalletsOrg> shiftPallets = shiftPalletsOrgRepository.findShiftPalletOrg(command.getTargetUnit(),
				command.getTargetID(), command.getOriginalPage());
		RequireImpl require = new RequireImpl(shiftPalletsOrgRepository);
		// 2:
		AtomTask persist = CopyShiftPaletteByOrgService.duplicate(require, shiftPallets.get(),
				command.getDestinationPage(), new ShiftPalletName(command.getDestinationName()), command.isOverwrite());

		// 3:
		transaction.execute(() -> {
			persist.run();
		});

	}

	@AllArgsConstructor
	private static class RequireImpl implements CopyShiftPaletteByOrgService.Require {

		private final ShiftPalletsOrgRepository shiftPalletsOrgRepository;

		@Override
		public boolean exists(TargetOrgIdenInfor targeOrg, int page) {
			return shiftPalletsOrgRepository.exists(targeOrg, page);
		}

		@Override
		public void delete(TargetOrgIdenInfor targeOrg, int page) {
			shiftPalletsOrgRepository.delete(targeOrg, page);
		}

		@Override
		public void add(ShiftPalletsOrg shiftPalletsOrg) {
			shiftPalletsOrgRepository.add(shiftPalletsOrg);
		}
	}

}
