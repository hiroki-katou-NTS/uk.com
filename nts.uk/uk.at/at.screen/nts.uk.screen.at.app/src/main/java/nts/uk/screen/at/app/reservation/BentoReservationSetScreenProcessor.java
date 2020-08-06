package nts.uk.screen.at.app.reservation;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class BentoReservationSetScreenProcessor {

    @Inject
    private BentoReservationScreenRepository repo;

    public Optional<BentoReservationSettingDto> findDataBentoReservationSetting(String companyId) {
        return this.repo.findDataBentoRervation(companyId);
    }
}
