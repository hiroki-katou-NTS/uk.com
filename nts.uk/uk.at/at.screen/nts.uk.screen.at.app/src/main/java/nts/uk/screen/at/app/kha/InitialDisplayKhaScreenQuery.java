package nts.uk.screen.at.app.kha;

import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class InitialDisplayKhaScreenQuery {
    @Inject
    private SupportOperationSettingRepository sosRepository;

    public  SupportOperationSettingDto get(String cid ){
        SupportOperationSetting domain = sosRepository.get(cid);
        SupportOperationSettingDto dto = new SupportOperationSettingDto(
                domain.isUsed(),
                domain.isSupportDestinationCanSpecifySupporter(),
                domain.getMaxNumberOfSupportOfDay().v()
        );
        return dto;
    }

}
