package nts.uk.ctx.at.shared.app.find.scherec.addset;

import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class HolidayAddSetFinder {

    @Inject
    private HolidayAddtionRepository holidayAddtionRepository;

    public HolidayAddSetDto init() {
        String cid = AppContexts.user().companyId();
        Optional<HolidayAddtionSet> hdSet = holidayAddtionRepository.findByCId(cid);
        if (hdSet.isPresent()) {
            HolidayAddSetDto dto = new HolidayAddSetDto(
                    hdSet.get().getReference().getComUniformAdditionTime().getOneDay().v(),
                    hdSet.get().getReference().getComUniformAdditionTime().getMorning().v(),
                    hdSet.get().getReference().getComUniformAdditionTime().getAfternoon().v(),
                    hdSet.get().getReference().getReferenceSet().value,
                    hdSet.get().getReference().getReferIndividualSet().isPresent() ?
                            hdSet.get().getReference().getReferIndividualSet().get().value : null
            );
            return dto;
        }
        return null;
    }

}
