package nts.uk.screen.at.app.reservation;

import java.util.Optional;

public interface BentoReservationScreenRepository {

    /**
     *
     * @param companyId
     * @return
     */
    BentoReservationSettingDto findDataBentoRervation(String companyId);
}
