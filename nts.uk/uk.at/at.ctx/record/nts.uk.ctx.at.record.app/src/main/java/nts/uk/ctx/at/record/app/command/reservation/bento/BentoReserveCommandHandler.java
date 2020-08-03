package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReserveCommandHandler extends CommandHandler<BentoReserveCommand> {
	
	@Inject
	private StampCardRepository stampCardRepository;
	
	@Inject
	private BentoMenuRepository bentoMenuRepository;
	
	@Inject
	private BentoReservationRepository bentoReservationRepository;
	
	@Override
	protected void handle(CommandHandlerContext<BentoReserveCommand> context) {
		
		BentoReserveCommand command = context.getCommand();
		
		StampCard stampCard = stampCardRepository.getLstStampCardByLstSidAndContractCd(
				Arrays.asList(AppContexts.user().employeeId()),
				AppContexts.user().contractCode()).get(0);
		
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampCard.getStampNumber().toString());
		
		RequireImpl require = new RequireImpl(bentoMenuRepository, bentoReservationRepository);
		
		GeneralDateTime datetime = GeneralDateTime.now();
		
		transaction.execute(() -> {
			if(!CollectionUtil.isEmpty(command.getFrame1Bentos().values())) {
				AtomTask persist1 = BentoReserveService.reserve(
						require, 
						reservationRegisterInfo, 
						new ReservationDate(command.getDate(), ReservationClosingTimeFrame.FRAME1), 
						datetime,
						command.getFrame1Bentos(),
						Optional.of(new WorkLocationCode(command.getWorkLocationCode())));
				persist1.run();
			}
			
			if(!CollectionUtil.isEmpty(command.getFrame2Bentos().values())) {
				AtomTask persist2 = BentoReserveService.reserve(
						require, 
						reservationRegisterInfo, 
						new ReservationDate(command.getDate(), ReservationClosingTimeFrame.FRAME2), 
						datetime,
						command.getFrame2Bentos(),
						Optional.of(new WorkLocationCode(command.getWorkLocationCode())));
				persist2.run();
			}
		});
		
	}
	
	@RequiredArgsConstructor
	private static class RequireImpl implements BentoReserveService.Require {
		
		private final BentoMenuRepository bentoMenuRepository;
		
		private final BentoReservationRepository bentoReservationRepository;
		
		@Override
		public BentoMenu getBentoMenu(ReservationDate reservationDate,Optional<WorkLocationCode> workLocationCode) {
			String companyID = AppContexts.user().companyId();
			return bentoMenuRepository.getBentoMenu(companyID, reservationDate.getDate(),workLocationCode);
		}

		@Override
		public void reserve(BentoReservation bentoReservation) {
			bentoReservationRepository.add(bentoReservation);
		}
		
	}
}
