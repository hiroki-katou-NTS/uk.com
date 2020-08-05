package nts.uk.screen.at.app.reservation;

import java.util.Optional;

public interface BentoReservationScreenRepository {

    /**
     *
     * @param companyId
     * @return
     */
    Optional<BentoReservationSettingDto> findDataBentoRervation(String companyId);
}
