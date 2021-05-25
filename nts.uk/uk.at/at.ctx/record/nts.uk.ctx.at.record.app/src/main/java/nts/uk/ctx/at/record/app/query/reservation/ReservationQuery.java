package nts.uk.ctx.at.record.app.query.reservation;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentomenuAdapter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.SWkpHistExport;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Inject
	private GetStampCardQuery getStampCardQuery;

	public ReservationDto findAll(ReservationDateParam param) {
		GeneralDate date = GeneralDate.fromString(param.getDate(), "yyyy/MM/dd");
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		// 運用区分＝会社別: 勤務場所コード＝NULL
		Optional<WorkLocationCode> workLocationCode = Optional.empty();

		//運用区分を取得(会社ID)
		Optional<BentoReservationSetting> bentoReservationSettings = bentoReservationSettingRepository.findByCId(companyId);

		// 勤務場所を取得 (社員ID,　基準日)
		Optional<SWkpHistExport> hisItems = this.bentomenuAdapter.findBySid(employeeId,date);
        int checkOperation = -1;
        if(bentoReservationSettings.isPresent())
            checkOperation = bentoReservationSettings.get().getOperationDistinction().value;
		// 運用区分＝職場別
		if (checkOperation == OperationDistinction.BY_LOCATION.value) {
			if (!hisItems.isPresent()) {
				throw new RuntimeException("Invalid workplace history");
			}
			// 勤務場所の弁当メニューを取得	(勤務場所コード＝取得した勤務場所)
			workLocationCode = Optional.ofNullable(hisItems.get().getWorkLocationCd() == null ? null :
					new WorkLocationCode(hisItems.get().getWorkLocationCd()));
		}

		Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(Arrays.asList(employeeId));
		if (!stampCards.containsKey(employeeId)) throw new BusinessException("Invalid Stamp Number");
		StampNumber stampNumber = stampCards.get(employeeId);
		ReservationRegisterInfo reservationRegisterInfo = new ReservationRegisterInfo(stampNumber.toString());
		//1 get*(予約対象日,カード番号)
		val listBento = bentoReservationRepo.findList(reservationRegisterInfo, new ReservationDate(date, EnumAdaptor.valueOf(param.getClosingTimeFrame(), ReservationClosingTimeFrame.class))) ;
		//2 get(会社ID, 予約日)
		val bento = bentoMenuRepo.getBentoMenu(companyId, date,workLocationCode);
		//3 締め時刻別のメニュー
		BentoMenuByClosingTime bentoMenuClosingTime = bento.getByClosingTime(workLocationCode);
		return new ReservationDto(listBento.stream().map(x -> BentoReservationDto.fromDomain(x)).collect(Collectors.toList()), BentoMenuByClosingTimeDto.fromDomain(bentoMenuClosingTime),
				workLocationCode.isPresent()? workLocationCode.get().v() : null );
	}
	
}
