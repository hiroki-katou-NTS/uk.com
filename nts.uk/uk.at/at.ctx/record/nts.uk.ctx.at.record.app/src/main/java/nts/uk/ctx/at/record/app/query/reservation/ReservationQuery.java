package nts.uk.ctx.at.record.app.query.reservation;

import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReservationQuery {
	@Inject
	private BentoReservationRepository bentoReservationRepo;
	
	@Inject
	private BentoMenuRepository bentoMenuRepo;
	
	public ReservationDto findAll(ReservationDateParam param) {
		GeneralDate date = GeneralDate.fromString(param.getDate(), "yyyy/MM/dd");
		String userId = "hdfgdgfdufdfdfdg";
		//1 get*(予約対象日,カード番号)
		// val dataLunch = bentoReservationRepo.find(new ReservationRegisterInfo(userId), new ReservationDate(param.getDate(), EnumAdaptor.valueOf(param.getClosingTimeFrame(), ReservationClosingTimeFrame.class)));
		val listBento = bentoReservationRepo.findList(new ReservationRegisterInfo(userId), new ReservationDate(date, EnumAdaptor.valueOf(param.getClosingTimeFrame(), ReservationClosingTimeFrame.class))) ;
		//2 get(会社ID, 予約日)
		String companyId = AppContexts.user().companyId();
		val bento = bentoMenuRepo.getBentoMenu(companyId, date);
		//3 締め時刻別のメニュー
		BentoMenuByClosingTime bentoMenuClosingTime = bento.getByClosingTime();
		return new ReservationDto(listBento.stream().map(x -> BentoReservationDto.fromDomain(x)).collect(Collectors.toList()), bentoMenuClosingTime);
	}
	
}
