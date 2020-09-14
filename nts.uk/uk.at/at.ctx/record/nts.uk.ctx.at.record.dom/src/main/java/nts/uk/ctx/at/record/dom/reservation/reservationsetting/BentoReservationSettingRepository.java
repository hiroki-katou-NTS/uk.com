package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import java.util.Optional;

public interface BentoReservationSettingRepository {

    /**
     * Find all BentoReservationSetting
     * @param companyId
     * @return
     */
    Optional<BentoReservationSetting> findByCId(String companyId);

    public void add(BentoReservationSetting bentoReservationSetting);

    public void update(BentoReservationSetting bentoReservationSetting);

}
