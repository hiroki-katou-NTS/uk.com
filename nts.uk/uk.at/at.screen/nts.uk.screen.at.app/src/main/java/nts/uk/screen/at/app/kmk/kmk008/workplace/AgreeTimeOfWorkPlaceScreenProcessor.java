package nts.uk.screen.at.app.kmk.kmk008.workplace;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 画面表示を行う
 */
@Stateless
public class AgreeTimeOfWorkPlaceScreenProcessor {

    @Inject
    private Workplace36AgreedHoursRepository ofWorkPlaceRepository;

    public WorkPlaceListCodesDto findAll(int laborSystemAtr) {

        WorkPlaceListCodesDto listCodesDto = new WorkPlaceListCodesDto();
        // get list workplaces have been setting
        List<String> workPlaceIds = ofWorkPlaceRepository
                .findWorkPlaceSetting(EnumAdaptor.valueOf(laborSystemAtr, LaborSystemtAtr.class));

        listCodesDto.setWorkPlaceIds(workPlaceIds);
        return listCodesDto;
    }

    public AgreementTimeOfWorkPlaceDto findAgreeTimeOfWorkPlace(RequestWorkPlace requestWorkPlace) {
        Optional<AgreementTimeOfWorkPlace> data = ofWorkPlaceRepository.getByWorkplaceId(requestWorkPlace.getWorkplaceId(),EnumAdaptor.valueOf(requestWorkPlace.getLaborSystemAtr(), LaborSystemtAtr.class));

        return AgreementTimeOfWorkPlaceDto.setData(data);
    }
}
