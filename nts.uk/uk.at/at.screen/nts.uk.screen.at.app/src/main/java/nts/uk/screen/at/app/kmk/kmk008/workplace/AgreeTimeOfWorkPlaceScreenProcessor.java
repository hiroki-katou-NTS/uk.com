package nts.uk.screen.at.app.kmk.kmk008.workplace;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementTimeOfWorkPlaceRepository;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.standardtime.enums.LaborSystemtAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 画面表示を行う
 */
@Stateless
public class AgreeTimeOfWorkPlaceScreenProcessor {

    @Inject
    private AgreementTimeOfWorkPlaceRepository ofWorkPlaceRepository;

    public AgreementTimeOfWorkPlaceDto findAgreeTimeOfWorkPlace(RequestWorkPlace requestWorkPlace) {
        Optional<AgreementTimeOfWorkPlace> data = ofWorkPlaceRepository.findAgreementTimeOfWorkPlace(requestWorkPlace.getWorkplaceId(),
                EnumAdaptor.valueOf(requestWorkPlace.getLaborSystemAtr(), LaborSystemtAtr.class));

        return AgreementTimeOfWorkPlaceDto.setData(data);
    }
}
