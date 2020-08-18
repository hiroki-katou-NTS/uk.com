package nts.uk.screen.at.app.reservation;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.GetEmployeeReferenceRangeService;
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
    private GetEmployeeReferenceRangeService domainService;

    @Inject
    private BentoMenuRepository bentoMenuRepo;

    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(String companyId) {
        ReservationConfirmationListDto dto = new ReservationConfirmationListDto();

        Optional<BentoReservationSetting> optBentoReservationSetting = domainService.getBentoReservationSetting(companyId);
        if (optBentoReservationSetting.isPresent()) {
            BentoReservationSetting bentoReservationSetting = optBentoReservationSetting.get();
            dto.setOperationDistinction(bentoReservationSetting.getOperationDistinction());
        }

        BentoMenu bentoMenuByEndDate = bentoMenuRepo.getBentoMenuByEndDate(companyId, GeneralDate.max());
        dto.setClosingTime(bentoMenuByEndDate.getClosingTime());
        List<Bento> bentoList = bentoMenuByEndDate.getMenu();

        List<List<Bento>> partitions = new ArrayList<>(
                bentoList.stream()
                        .collect(Collectors.partitioningBy(item -> item.getWorkLocationCode().isPresent()))
                        .values()
        );
        List<Bento> bentoMenuByLocation = partitions.get(0);
        List<Bento> bentoMenuByCompany = partitions.get(1);

        if (dto.getOperationDistinction() == OperationDistinction.BY_COMPANY) {
            dto.setMenu(bentoMenuByCompany);
        } else {
            dto.setMenu(bentoMenuByLocation);
        }

        return dto;
    }
}