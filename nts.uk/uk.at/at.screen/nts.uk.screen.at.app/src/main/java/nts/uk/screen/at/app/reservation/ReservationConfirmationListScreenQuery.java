package nts.uk.screen.at.app.reservation;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 予約確認一覧
 *
 * @author 3si - Dang Huu Khai
 */
@Stateless
public class ReservationConfirmationListScreenQuery {

    @Inject
    private BentoReservationSettingRepository bentoReservationSettingRepository;

    @Inject
    private BentoMenuRepository bentoMenuRepo;

    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(String companyId) {
        ReservationConfirmationListDto dto = new ReservationConfirmationListDto();

        Optional<BentoReservationSetting> optBentoReservationSetting = bentoReservationSettingRepository.findByCId(companyId);
        if (optBentoReservationSetting.isPresent()) {
            BentoReservationSetting bentoReservationSetting = optBentoReservationSetting.get();
            dto.setOperationDistinction(bentoReservationSetting.getOperationDistinction());
        } else {
        	throw new BusinessException("Msg_1847");
        }

        BentoMenu bentoMenu = bentoMenuRepo.getBentoMenuByEndDate(companyId, GeneralDate.max());
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
            menuByOperationType = partitions.get(1);
        } else {
            menuByOperationType = partitions.get(0);
        }
        List<BentoItemDto> bentoItemList = copyBentoItemList(menuByOperationType);
        dto.setMenu(bentoItemList);

        BentoReservationClosingTime closingTime = bentoMenu.getClosingTime();
        String reservationFrameName1 = closingTime.getClosingTime1().getReservationTimeName().v();
        int reservationStartTime1 = closingTime.getClosingTime1().getStart().get().v();
        int reservationEndTime1 = closingTime.getClosingTime1().getFinish().v();
        Optional<ReservationClosingTime> closingTime2 = closingTime.getClosingTime2();
        String reservationFrameName2 = closingTime2.isPresent()? closingTime2.get().getReservationTimeName().v(): "";
        int reservationStartTime2 = closingTime2.isPresent()? closingTime2.get().getStart().get().v(): 0;
        int reservationEndTime2 = closingTime2.isPresent()? closingTime2.get().getFinish().v(): 0;

        BentoMenuDto bentoMenuDtoClosingTime = new BentoMenuDto(
                reservationFrameName1,
                reservationStartTime1,
                reservationEndTime1,
                reservationFrameName2,
                reservationStartTime2,
                reservationEndTime2
        );
        dto.setClosingTime(bentoMenuDtoClosingTime);

        return dto;
    }

    private List<BentoItemDto> copyBentoItemList(List<Bento> bentoList){
        return bentoList.stream().map(item -> {
            BentoItemDto bentoItemDto = new BentoItemDto();
            bentoItemDto.setCode(item.getFrameNo());
            bentoItemDto.setName(item.getName().v());
            return bentoItemDto;
        }).collect(Collectors.toList());
    }
}