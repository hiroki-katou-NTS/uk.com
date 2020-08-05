package nts.uk.screen.at.app.reservation;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import java.util.Optional;

public class BentoMenuSetScreenProcessor {

    @Inject
    private BentoMenuScreenRepository repo;

    public Optional<BentoMenuDto> findDataBentoMenu() {
        String companyID = AppContexts.user().companyId();
        GeneralDate date = GeneralDate.fromString("9999/12/31", "yyyy/MM/dd");
        return this.repo.findDataBentoMenu(companyID,date);
    }
}
