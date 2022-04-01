package nts.uk.ctx.at.record.app.query.reservation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReservationQuery {
	@Inject
	private BentoReservationRepository bentoReservationRepo;

	@Inject
	private BentoMenuHistRepository bentoMenuRepo;

	@Inject
	private ReservationSettingRepository reservationSettingRepository;

	@Inject
	private GetStampCardQuery getStampCardQuery;

	public ReservationDto findAll(ReservationDateParam param) {
		GeneralDate date = GeneralDate.fromString(param.getDate(), "yyyy/MM/dd");
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		// 1: 会社IDと枠NO一覧によって予約設定を取得する
		ReservationSetting reservationSetting = reservationSettingRepository.getReservationSettingByOpDist(companyId, OperationDistinction.BY_COMPANY.value);
		// 2: 予約設定＝NULLの場合
		if(reservationSetting==null) {
			throw new BusinessException("Msg_2285");
		}
		// 3: 打刻カードを全て取得する
		Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(Arrays.asList(employeeId));
		if (!stampCards.containsKey(employeeId)) {
			throw new BusinessException("Msg_3241");
		}
		StampNumber stampNumber = stampCards.get(employeeId);
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampNumber.toString());
		// 4: 弁当予約を取得
		List<BentoReservation> bentoReservationLst = bentoReservationRepo.findList(
				reservationRegisterInfo, 
				new ReservationDate(date, EnumAdaptor.valueOf(1, ReservationClosingTimeFrame.class)));
		// 5: 取得する
		List<Bento> menu = bentoMenuRepo.getBento(companyId, date, Optional.empty());
		if(menu.isEmpty()) {
			throw new BusinessException("Msg_1604");
		}
		// 6: create
		Map<ReservationClosingTimeFrame, Boolean> orderAtr = new HashMap<>();
		for(BentoReservation bentoReservation : bentoReservationLst) {
			orderAtr.put(bentoReservation.getReservationDate().getClosingTimeFrame(), bentoReservation.isOrdered());
		}
		BentoMenuByClosingTime bentoMenuByClosingTime = BentoMenuByClosingTime.createForCurrent(
				AppContexts.user().roles().forAttendance(), 
				reservationSetting, 
				menu, 
				orderAtr,
				date);
		return new ReservationDto(
				bentoReservationLst.stream().map(x -> BentoReservationDto.fromDomain(x)).collect(Collectors.toList()), 
				BentoMenuByClosingTimeDto.fromDomain(bentoMenuByClosingTime));
	}
	
}
