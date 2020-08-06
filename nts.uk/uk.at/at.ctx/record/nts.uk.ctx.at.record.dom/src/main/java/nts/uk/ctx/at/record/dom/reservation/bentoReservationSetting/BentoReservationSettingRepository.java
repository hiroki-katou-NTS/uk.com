package nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting;

import java.util.Optional;

public interface BentoReservationSettingRepository {

    /**
     * Find all BentoReservationSetting
     * @param companyId
     * @return
     */
    Optional<BentoReservationSetting> findByCId(String companyId);
}
