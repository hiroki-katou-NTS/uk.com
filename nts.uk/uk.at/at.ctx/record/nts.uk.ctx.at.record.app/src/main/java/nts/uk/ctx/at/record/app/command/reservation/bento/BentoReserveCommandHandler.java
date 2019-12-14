package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRequire;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveService;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReserveCommandHandler extends CommandHandler<BentoReserveCommand> {
	
	@Inject
	private BentoReservationRequire bentoReservationRequire;
	
	@Inject
	private StampCardRepository stampCardRepository;
	
	@Override
	protected void handle(CommandHandlerContext<BentoReserveCommand> context) {
		
		BentoReserveCommand command = context.getCommand();
		
		StampCard stampCard = stampCardRepository.getLstStampCardByLstSidAndContractCd(
				Arrays.asList(AppContexts.user().employeeId()),
				AppContexts.user().contractCode()).get(0);
		
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampCard.getStampNumber().toString());
		
		AtomTask persist1 = BentoReserveService.reserve(
				bentoReservationRequire, 
				reservationRegisterInfo, 
				new ReservationDate(command.getDate(), ReservationClosingTimeFrame.FRAME1), 
				command.getFrame1Bentos());
		
		AtomTask persist2 = BentoReserveService.reserve(
				bentoReservationRequire, 
				reservationRegisterInfo, 
				new ReservationDate(command.getDate(), ReservationClosingTimeFrame.FRAME2), 
				command.getFrame2Bentos());
		
		transaction.execute(() -> {
			persist1.run();
			persist2.run();
		});
		
	}
}
