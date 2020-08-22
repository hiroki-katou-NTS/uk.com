package nts.uk.screen.at.app.reservation;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface BentoMenuScreenRepository {

    /**
     *
     * @param companyId
     * @return
     */
    BentoMenuDto findDataBentoMenu(String companyId, GeneralDate date);

    List<BentomenuJoinBentoDto> findDataBento(String companyId, GeneralDate date, BentoRequest request);

    List<WorkLocationDto> findDataWorkLocation(String companyId);
}
