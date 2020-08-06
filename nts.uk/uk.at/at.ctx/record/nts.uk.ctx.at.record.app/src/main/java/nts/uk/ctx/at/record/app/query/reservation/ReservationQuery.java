package nts.uk.ctx.at.record.app.query.reservation;

import java.util.Arrays;
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
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentomenuAdapter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.SWkpHistExport;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReservationQuery {
	@Inject
	private BentoReservationRepository bentoReservationRepo;
	
	@Inject
	private BentoMenuRepository bentoMenuRepo;
	
	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private BentomenuAdapter bentomenuAdapter;

	@Inject
	private BentoReservationSettingRepository bentoReservationSettingRepository;

	public ReservationDto findAll(ReservationDateParam param) {
		GeneralDate date = GeneralDate.fromString(param.getDate(), "yyyy/MM/dd");
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		Optional<WorkLocationCode> workLocationCode = Optional.of(new WorkLocationCode(null));

		Optional<BentoReservationSetting> bentoReservationSettings = bentoReservationSettingRepository.findByCId(companyId);

		// get data work place history
		Optional<SWkpHistExport> hisItems = this.bentomenuAdapter
				.findBySid(employeeId,date);

		val checkOperation = bentoReservationSettings.get().getOperationDistinction().value;
		if (checkOperation == OperationDistinction.BY_LOCATION.value){
			workLocationCode = Optional.of(new WorkLocationCode(hisItems.get().getWorkplaceCode()));
		}

        StampCard stampCard = stampCardRepository.getLstStampCardByLstSidAndContractCd(
				Arrays.asList(employeeId),
				AppContexts.user().contractCode()).get(0);
		
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampCard.getStampNumber().toString());
		//1 get*(予約対象日,カード番号)
		val listBento = bentoReservationRepo.findList(reservationRegisterInfo, new ReservationDate(date, EnumAdaptor.valueOf(param.getClosingTimeFrame(), ReservationClosingTimeFrame.class))) ;
		//2 get(会社ID, 予約日)
		String companyId = AppContexts.user().companyId();
		val bento = bentoMenuRepo.getBentoMenu(companyId, date);
		//3 締め時刻別のメニュー
		BentoMenuByClosingTime bentoMenuClosingTime = bento.getByClosingTime();
		return new ReservationDto(listBento.stream().map(x -> BentoReservationDto.fromDomain(x)).collect(Collectors.toList()), BentoMenuByClosingTimeDto.fromDomain(bentoMenuClosingTime));
	}
	
}
