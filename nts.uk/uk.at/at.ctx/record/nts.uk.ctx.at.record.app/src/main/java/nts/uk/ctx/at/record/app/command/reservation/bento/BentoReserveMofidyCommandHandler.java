package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveModifyService;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReserveMofidyCommandHandler extends CommandHandler<BentoReserveCommand> {
	
	@Inject
	private BentoMenuRepository bentoMenuRepository;
	
	@Inject
	private BentoReservationRepository bentoReservationRepository;
	
	@Inject
	private ReservationSettingRepository reservationSettingRepository;

	@Inject
	private GetStampCardQuery getStampCardQuery;

	@Override
	protected void handle(CommandHandlerContext<BentoReserveCommand> context) {
		String companyID = AppContexts.user().companyId();
		
		BentoReserveCommand command = context.getCommand();
		GeneralDate date = GeneralDate.fromString(command.getDate(), "yyyy/MM/dd");

		// Get WorkLocationCode by Cid
		Optional<WorkLocationCode> workLocationCode = command.getWorkLocationCode() != null?
				Optional.of(new WorkLocationCode(command.getWorkLocationCode())): Optional.empty();
		// End

		String employeeId = AppContexts.user().employeeId();
		Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(Arrays.asList(employeeId));
		if (!stampCards.containsKey(employeeId)) throw new RuntimeException("Invalid Stamp Number");
		StampNumber stampNumber = stampCards.get(employeeId);
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampNumber.toString());
		
		RequireImpl require = new RequireImpl(bentoMenuRepository, bentoReservationRepository, reservationSettingRepository);
		
		GeneralDateTime datetime = GeneralDateTime.now();
        AtomTask persist1 = BentoReserveModifyService.reserve(
                require, 
                reservationRegisterInfo, 
                new ReservationDate(date, ReservationClosingTimeFrame.FRAME1), 
                datetime,
                command.getFrame1Bentos(),
                1,
                companyID,
				workLocationCode);
        
        AtomTask persist2 = BentoReserveModifyService.reserve(
                require, 
                reservationRegisterInfo, 
                new ReservationDate(date, ReservationClosingTimeFrame.FRAME2),
                datetime,
                command.getFrame2Bentos(),
                2,
                companyID,
				workLocationCode);
		
		transaction.execute(() -> {
            persist1.run();
            persist2.run();
		});
	}
	
	@AllArgsConstructor
	private static class RequireImpl implements BentoReserveModifyService.Require {
		
		private final BentoMenuRepository bentoMenuRepository;
		
		private final BentoReservationRepository bentoReservationRepository;
		
		private final ReservationSettingRepository reservationSettingRepository;

		@Override
		public BentoMenu getBentoMenu(ReservationDate reservationDate,Optional<WorkLocationCode> workLocationCode) {
			String companyID = AppContexts.user().companyId();
			return bentoMenuRepository.getBentoMenu(companyID, reservationDate.getDate(),workLocationCode);
		}

		@Override
		public Optional<BentoReservation> getBefore(ReservationRegisterInfo registerInfor,
				ReservationDate reservationDate) {
			return bentoReservationRepository.find(registerInfor, reservationDate);
		}

		@Override
		public void reserve(BentoReservation bentoReservation) {
			bentoReservationRepository.add(bentoReservation);
		}

		@Override
		public void delete(BentoReservation bentoReservation) {
			bentoReservationRepository.delete(bentoReservation);
		}

		@Override
		public ReservationRecTimeZone getReservationSetByOpDistAndFrameNo(String companyID, int frameNo,
				int operationDistinction) {
			return reservationSettingRepository.getReservationSetByOpDistAndFrameNo(companyID, frameNo, operationDistinction);
		}
		
	}
}
