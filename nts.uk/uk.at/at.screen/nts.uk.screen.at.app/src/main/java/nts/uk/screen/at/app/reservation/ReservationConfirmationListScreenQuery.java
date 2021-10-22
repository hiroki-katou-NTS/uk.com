package nts.uk.screen.at.app.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
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
    private BentoMenuRepository bentoMenuRepo;

    /**
     * 社員参照範囲を取得する
     * @param companyId
     * @return
     */
    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(String companyId) {
        ReservationConfirmationListDto dto = new ReservationConfirmationListDto();

        // 取得する(会社ID)
        Optional<ReservationSetting> optBentoReservationSetting = bentoReservationSettingRepository.findByCId(companyId);
        //    取得したドメインモデル「弁当予約設定」がないの場合
        //　　エラーメッセージ「Msg_1847」を表示、A画面へ戻る
        if (optBentoReservationSetting.isPresent()) {
            ReservationSetting bentoReservationSetting = optBentoReservationSetting.get();
            dto.setOperationDistinction(bentoReservationSetting.getOperationDistinction());
        } else {
            throw new BusinessException("Msg_1847");
        }

        // 取得する(会社ID、年月日) 会社ID＝ログイン会社ID,年月日＝9999/12/31
        BentoMenu bentoMenu = bentoMenuRepo.getBentoMenuByEndDate(companyId, GeneralDate.max());

        // 取得したドメインモデル「弁当メニュー」がないの場合
        //　　エラーメッセージ「Msg_1848」を表示、A画面へ戻る
        if (bentoMenu == null) {
            throw new BusinessException("Msg_1848");
        }

        List<Bento> menu = bentoMenu.getMenu();
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

//        BentoReservationClosingTime closingTime = bentoMenu.getClosingTime();
//        String reservationFrameName1 = closingTime.getClosingTime1().getReservationTimeName().v();
//        int reservationStartTime1 = closingTime.getClosingTime1().getStart().get().v();
//        int reservationEndTime1 = closingTime.getClosingTime1().getFinish().v();
//        Optional<ReservationClosingTime> closingTime2 = closingTime.getClosingTime2();
//
//        String reservationFrameName2 = "";
//        Integer reservationStartTime2 = null;
//        Integer reservationEndTime2 = null;
//        if (closingTime2.isPresent()){
//            ReservationClosingTime ct2 = closingTime2.get();
//            reservationFrameName2 = ct2.getReservationTimeName().v();
//            if (ct2.getStart().isPresent()){
//                reservationStartTime2 = ct2.getStart().get().v();
//            }
//            reservationEndTime2 = ct2.getFinish().v();
//        }
//
//
//		ReservationClosingTimeDto timeFrame = new ReservationClosingTimeDto(
//                reservationFrameName1,
//                reservationStartTime1,
//                reservationEndTime1,
//                reservationFrameName2,
//                reservationStartTime2,
//                reservationEndTime2
//        );
//        dto.setClosingTime(timeFrame);

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