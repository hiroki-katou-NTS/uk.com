package nts.uk.screen.at.app.reservation;

import nts.arc.time.GeneralDate;

import java.util.Optional;

public interface BentoMenuScreenRepository {

    /**
     *
     * @param companyId
     * @return
     */
    BentoMenuDto findDataBentoMenu(String companyId, GeneralDate date);
}
