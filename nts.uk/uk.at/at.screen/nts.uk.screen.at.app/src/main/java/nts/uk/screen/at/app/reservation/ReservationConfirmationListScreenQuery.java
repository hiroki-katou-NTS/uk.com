package nts.uk.screen.at.app.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationOrderMngAtr;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;

/**
 * 予約確認一覧
 *
 * @author 3si - Dang Huu Khai
 */
@Stateless
public class ReservationConfirmationListScreenQuery {

    @Inject
    private ReservationSettingRepository bentoReservationSettingRepository;

    @Inject
    private BentoMenuHistRepository bentoMenuRepo;

    /**
     * 社員参照範囲を取得する
     * @param companyId
     * @return
     */
    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(String companyId, GeneralDate startDate, GeneralDate endDate) {
        ReservationConfirmationListDto dto = new ReservationConfirmationListDto();
        // 取得する(会社ID)
        Optional<ReservationSetting> optBentoReservationSetting = bentoReservationSettingRepository.findByCId(companyId);
        //    取得したドメインモデル「弁当予約設定」がないの場合
        //　　エラーメッセージ「Msg_1847」を表示、A画面へ戻る
        if (optBentoReservationSetting.isPresent()) {
            ReservationSetting bentoReservationSetting = optBentoReservationSetting.get();
            dto.setOperationDistinction(bentoReservationSetting.getOperationDistinction());
            dto.setOrderMngAtr(bentoReservationSetting.getCorrectionContent().getOrderMngAtr()==ReservationOrderMngAtr.CAN_MANAGE);
        } else {
            throw new BusinessException("Msg_3247");
        }

        // 取得する(会社ID、年月日) 会社ID＝ログイン会社ID,年月日＝9999/12/31
        List<BentoMenuHistory> bentoMenuLst = bentoMenuRepo.findByCompanyPeriod(companyId, new DatePeriod(startDate, endDate));

        List<Bento> menu = bentoMenuLst.stream().flatMap(x -> x.getMenu().stream()).collect(Collectors.toList());
        List<List<Bento>> partitions = new ArrayList<>(
                menu.stream()
                        .collect(Collectors.partitioningBy(item -> item.getWorkLocationCode().isPresent()))
                        .values()
        );

        List<Bento> menuByOperationType;
        OperationDistinction operationDistinction = dto.getOperationDistinction();
        if (operationDistinction == OperationDistinction.BY_COMPANY) {
            menuByOperationType = partitions.get(0);
        } else {
            menuByOperationType = partitions.get(1);
        }
        List<BentoItemDto> bentoItemList = copyBentoItemList(menuByOperationType);
        dto.setMenu(bentoItemList);

        ReservationRecTimeZone reservationRecTimeZone1 = optBentoReservationSetting.get().getReservationRecTimeZoneLst()
        		.stream().filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME1).findAny().get();
        
        String reservationFrameName1 = reservationRecTimeZone1.getReceptionHours().getReceptionName().v();
        int reservationStartTime1 = reservationRecTimeZone1.getReceptionHours().getStartTime().v();
        int reservationEndTime1 = reservationRecTimeZone1.getReceptionHours().getEndTime().v();

        String reservationFrameName2 = "";
        Integer reservationStartTime2 = null;
        Integer reservationEndTime2 = null;
    	Optional<ReservationRecTimeZone> opReservationRecTimeZone2 = optBentoReservationSetting.get().getReservationRecTimeZoneLst()
        		.stream().filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME2).findAny();
    	if(opReservationRecTimeZone2.isPresent()) {
    		reservationFrameName2 = opReservationRecTimeZone2.get().getReceptionHours().getReceptionName().v();
            reservationStartTime2 = opReservationRecTimeZone2.get().getReceptionHours().getStartTime().v();
            reservationEndTime2 = opReservationRecTimeZone2.get().getReceptionHours().getEndTime().v();
    	}

		ReservationClosingTimeDto timeFrame = new ReservationClosingTimeDto(
                reservationFrameName1,
                reservationStartTime1,
                reservationEndTime1,
                reservationFrameName2,
                reservationStartTime2,
                reservationEndTime2
        );
        dto.setClosingTime(timeFrame);

        return dto;
    }

    private List<BentoItemDto> copyBentoItemList(List<Bento> bentoList){
        return bentoList.stream().map(item -> {
            BentoItemDto bentoItemDto = new BentoItemDto();
            bentoItemDto.setCode(item.getFrameNo());
			if (item.getWorkLocationCode().isPresent()) {
				bentoItemDto.setLocationCode(item.getWorkLocationCode().get().v());
			} else {
				bentoItemDto.setLocationCode(null);
			}
            bentoItemDto.setName(item.getName().v());
            return bentoItemDto;
        }).collect(Collectors.toList());
    }
}