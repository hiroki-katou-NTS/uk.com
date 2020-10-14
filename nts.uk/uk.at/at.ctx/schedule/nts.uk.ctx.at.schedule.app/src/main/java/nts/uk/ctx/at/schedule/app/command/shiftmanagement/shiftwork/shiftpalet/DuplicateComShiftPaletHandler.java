package nts.uk.ctx.at.schedule.app.command.shiftmanagement.shiftwork.shiftpalet;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.CopyCompanyShiftPaletteService;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteComRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * <<Command>> 会社別シフトパレットを複製する
 *
 */
@Stateless
public class DuplicateComShiftPaletHandler extends CommandHandler<DuplicateComShiftPaletCommand> {

	@Inject
	private ShiftPaletteComRepository shiftpaletRepository;

	@Override
	protected void handle(CommandHandlerContext<DuplicateComShiftPaletCommand> context) {
		DuplicateComShiftPaletCommand command = context.getCommand();
		// 1:
		Optional<ShiftPaletteCom> shiftPalletsCom = shiftpaletRepository.findShiftPallet(AppContexts.user().companyId(),
				command.getOriginalPage());
		RequireImpl require = new RequireImpl(shiftpaletRepository);
		// 2:
		AtomTask persist = CopyCompanyShiftPaletteService.duplicate(require, shiftPalletsCom.get(), command.getDestinationPage(),
				new ShiftPaletteName(command.getDestinationName()), command.isOverwrite());

		// 3:
		transaction.execute(() -> {
			persist.run();
		});
	}

	@AllArgsConstructor
	private static class RequireImpl implements CopyCompanyShiftPaletteService.Require {

		private final ShiftPaletteComRepository shiftpaletRepository;

		@Override
		public boolean exists(String companyID, int page) {
			return shiftpaletRepository.exists(companyID, page);
		}

		@Override
		public void deleteByPage(String companyID, int page) {
			shiftpaletRepository.deleteByPage(companyID, page);

		}

		@Override
		public void add(ShiftPaletteCom shiftPalletsCom) {
			shiftpaletRepository.add(shiftPalletsCom);

		}
	}

}
