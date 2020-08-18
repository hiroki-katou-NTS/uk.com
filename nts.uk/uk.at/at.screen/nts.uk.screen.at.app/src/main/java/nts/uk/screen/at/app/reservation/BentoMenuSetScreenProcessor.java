package nts.uk.screen.at.app.reservation;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class BentoMenuSetScreenProcessor {

    @Inject
    private BentoMenuScreenRepository bentoMenuScreenRepository;

    @Inject
    private BentoReservationScreenRepository bentoReservationScreenRepository;

    public BentoMenuJoinBentoSettingDto findDataBentoMenu() {
        String companyID = AppContexts.user().companyId();
        GeneralDate generalDate = GeneralDate.max();
        BentoMenuDto bentoMenuDto = bentoMenuScreenRepository.findDataBentoMenu(companyID,generalDate);

        BentoReservationSettingDto reservationSettingDto = bentoReservationScreenRepository.findDataBentoRervation(companyID);

        return BentoMenuJoinBentoSettingDto.SetData(bentoMenuDto,reservationSettingDto);
    }

    public BentoMenuDto getBentoMenuByHist() {
        String companyID = AppContexts.user().companyId();
        GeneralDate generalDate = GeneralDate.max();
        return bentoMenuScreenRepository.findDataBentoMenu(companyID,generalDate);
    }
}
