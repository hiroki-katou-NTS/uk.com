package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.Arrays;
import java.util.Map;
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
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
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

	@Inject
	private GetStampCardQuery getStampCardQuery;

	@Override
	protected void handle(CommandHandlerContext<BentoReserveCommand> context) {

		BentoReserveCommand command = context.getCommand();

 		Optional<WorkLocationCode> workLocationCode = command.getWorkLocationCode() != null?
				Optional.of(new WorkLocationCode(command.getWorkLocationCode())): Optional.empty();

		String employeeId = AppContexts.user().employeeId();
		Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(Arrays.asList(employeeId));
		if (!stampCards.containsKey(employeeId)) throw new RuntimeException("Invalid Stamp Number");
		StampNumber stampNumber = stampCards.get(employeeId);
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampNumber.toString());
		
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
						workLocationCode);
				persist1.run();
			}
			
			if(!CollectionUtil.isEmpty(command.getFrame2Bentos().values())) {
				AtomTask persist2 = BentoReserveService.reserve(
						require, 
						reservationRegisterInfo, 
						new ReservationDate(command.getDate(), ReservationClosingTimeFrame.FRAME2), 
						datetime,
						command.getFrame2Bentos(),
						workLocationCode);
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
