package nts.uk.ctx.at.schedule.app.command.shiftmanagement.shiftwork.shiftpalet;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.shift.management.CopyCompanyShiftPaletteService;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * <<Command>> 会社別シフトパレットを複製する
 *
 */
@Stateless
public class DuplicateComShiftPaletHandler extends CommandHandler<DuplicateComShiftPaletCommand> {

	@Inject
	private ShiftPalletsComRepository shiftpaletRepository;

	@Override
	protected void handle(CommandHandlerContext<DuplicateComShiftPaletCommand> context) {
		DuplicateComShiftPaletCommand command = context.getCommand();
		// 1:
		Optional<ShiftPalletsCom> shiftPalletsCom = shiftpaletRepository.findShiftPallet(AppContexts.user().companyId(),
				command.getOriginalPage());
		RequireImpl require = new RequireImpl(shiftpaletRepository);
		// 2:
		AtomTask persist = CopyCompanyShiftPaletteService.duplicate(require, shiftPalletsCom.get(), command.getDestinationPage(),
				new ShiftPalletName(command.getDestinationName()), command.isOverwrite());

		// 3:
		transaction.execute(() -> {
			persist.run();
		});
	}

	@AllArgsConstructor
	private static class RequireImpl implements CopyCompanyShiftPaletteService.Require {

		private final ShiftPalletsComRepository shiftpaletRepository;

		@Override
		public boolean exists(String companyID, int page) {
			return shiftpaletRepository.exists(companyID, page);
		}

		@Override
		public void deleteByPage(String companyID, int page) {
			shiftpaletRepository.deleteByPage(companyID, page);

		}

		@Override
		public void add(ShiftPalletsCom shiftPalletsCom) {
			shiftpaletRepository.add(shiftPalletsCom);

		}
	}

}
