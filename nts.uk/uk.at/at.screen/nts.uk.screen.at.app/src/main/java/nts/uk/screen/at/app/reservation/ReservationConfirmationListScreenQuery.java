package nts.uk.screen.at.app.reservation;

import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.GetEmployeeReferenceRangeService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 予約確認一覧
 *
 * @author 3si - Dang Huu Khai
 */
@Stateless
public class ReservationConfirmationListScreenQuery {

    @Inject
    private GetEmployeeReferenceRangeService domainService;

    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(String companyId) {
        ReservationConfirmationListDto dto = new ReservationConfirmationListDto();
        Optional<BentoReservationSetting> optBentoReservationSetting = domainService.getBentoReservationSetting(companyId);
        if (optBentoReservationSetting.isPresent()) {
            BentoReservationSetting bentoReservationSetting = optBentoReservationSetting.get();
            dto.setOperationDistinction(bentoReservationSetting.getOperationDistinction());
        }

        return dto;
    }
}