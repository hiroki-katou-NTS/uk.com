package nts.uk.screen.at.app.reservation;


public interface BentoReservationScreenRepository {

    /**
     *
     * @param companyId
     * @return
     */
    BentoReservationSettingDto findDataBentoRervation(String companyId);
}
