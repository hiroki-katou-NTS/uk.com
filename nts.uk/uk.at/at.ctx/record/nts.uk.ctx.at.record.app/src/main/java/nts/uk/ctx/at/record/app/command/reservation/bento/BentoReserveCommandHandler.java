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
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReserveService;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BentoReserveCommandHandler extends CommandHandler<BentoReserveCommand> {
	
	@Inject
	private BentoMenuHistRepository bentoMenuRepository;
	
	@Inject
	private BentoReservationRepository bentoReservationRepository;

	@Inject
	private GetStampCardQuery getStampCardQuery;
	
	@Inject
	private ReservationSettingRepository reservationSettingRepository;

	@Override
	protected void handle(CommandHandlerContext<BentoReserveCommand> context) {
		String companyID = AppContexts.user().companyId();
		
		BentoReserveCommand command = context.getCommand();
		GeneralDate date = GeneralDate.fromString(command.getDate(), "yyyy/MM/dd");
		int closingTimeFrameNo = command.getClosingTimeFrameNo();

 		Optional<WorkLocationCode> workLocationCode = command.getWorkLocationCode() != null?
				Optional.of(new WorkLocationCode(command.getWorkLocationCode())): Optional.empty();

		String employeeId = AppContexts.user().employeeId();
		Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(Arrays.asList(employeeId));
		if (!stampCards.containsKey(employeeId)) throw new RuntimeException("Invalid Stamp Number");
		StampNumber stampNumber = stampCards.get(employeeId);
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampNumber.toString());
		
		RequireImpl require = new RequireImpl(bentoMenuRepository, bentoReservationRepository, reservationSettingRepository);
		
		GeneralDateTime datetime = GeneralDateTime.now();
		
		transaction.execute(() -> {
			if(!CollectionUtil.isEmpty(command.getFrame1Bentos().values()) && closingTimeFrameNo == 1) {
				AtomTask persist1 = BentoReserveService.reserve(
						require, 
						reservationRegisterInfo, 
						new ReservationDate(date, ReservationClosingTimeFrame.FRAME1), 
						datetime,
						command.getFrame1Bentos(),
						companyID,
						workLocationCode);
				persist1.run();
			}
			
			if(!CollectionUtil.isEmpty(command.getFrame2Bentos().values()) && closingTimeFrameNo == 2) {
				AtomTask persist2 = BentoReserveService.reserve(
						require, 
						reservationRegisterInfo, 
						new ReservationDate(date, ReservationClosingTimeFrame.FRAME2), 
						datetime,
						command.getFrame2Bentos(),
						companyID,
						workLocationCode);
				persist2.run();
			}
		});
		
	}
	
	@RequiredArgsConstructor
	private static class RequireImpl implements BentoReserveService.Require {
		
		private final BentoMenuHistRepository bentoMenuRepository;
		
		private final BentoReservationRepository bentoReservationRepository;
		
		private final ReservationSettingRepository reservationSettingRepository;
		
		@Override
		public BentoMenuHistory getBentoMenu(ReservationDate reservationDate,Optional<WorkLocationCode> workLocationCode) {
			String companyID = AppContexts.user().companyId();
			return bentoMenuRepository.findByCompanyDate(companyID, reservationDate.getDate()).get();
		}

		@Override
		public void reserve(BentoReservation bentoReservation) {
			bentoReservationRepository.add(bentoReservation);
		}

		@Override
		public ReservationRecTimeZone getReservationSetByOpDistAndFrameNo(String companyID, int frameNo,
				int operationDistinction) {
			return reservationSettingRepository.getReservationSetByOpDistAndFrameNo(companyID, frameNo, operationDistinction);
		}
		
	}
}
