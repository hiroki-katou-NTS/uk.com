package nts.uk.ctx.at.record.app.command.reservation;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveService;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;

public class BentoReservationCommandHandler extends CommandHandler<BentoReservationCommand> {
	
	private BentoMenuRepository bentoMenuRepository;

	@Override
	protected void handle(CommandHandlerContext<BentoReservationCommand> context) {
		val require = new RequireImpl(bentoMenuRepository);
		
		val command = context.getCommand();
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements BentoReserveService.Require {
		
		private final BentoMenuRepository bentoMenuRepository;

		@Override
		public BentoMenu getBentoMenu() {
			return bentoMenuRepository.find().get();
		}

		@Override
		public void reserve(BentoReservation bentoReservation) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
