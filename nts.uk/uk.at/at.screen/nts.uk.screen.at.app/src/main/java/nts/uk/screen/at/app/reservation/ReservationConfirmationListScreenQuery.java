package nts.uk.screen.at.app.reservation;

import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.GetEmployeeReferenceRangeService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class ReservationConfirmationListScreenQuery {

    @Inject
    private GetEmployeeReferenceRangeService domainService;

    public ReservationConfirmationListDto getReservationConfirmationListStartupInfo(String companyId) {
        ReservationConfirmationListDto dto = new ReservationConfirmationListDto();
        Optional<BentoReservationSetting> bentoReservationSetting = domainService.getReservationConfirmationListStartupInfo(companyId);
        if (bentoReservationSetting.isPresent()) {
            BentoReservationSetting rawBentoReservationSetting = bentoReservationSetting.get();
            dto.setOperationDistinction(rawBentoReservationSetting.getOperationDistinction());
        }

        return dto;
    }
}