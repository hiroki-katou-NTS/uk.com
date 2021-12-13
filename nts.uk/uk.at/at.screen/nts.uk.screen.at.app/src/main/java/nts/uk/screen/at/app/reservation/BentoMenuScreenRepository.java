package nts.uk.screen.at.app.reservation;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface BentoMenuScreenRepository {

    BentoMenuDto findDataBentoMenu(String companyId, GeneralDate date);

    List<WorkLocationDto> findDataWorkLocation(String companyId);
}
