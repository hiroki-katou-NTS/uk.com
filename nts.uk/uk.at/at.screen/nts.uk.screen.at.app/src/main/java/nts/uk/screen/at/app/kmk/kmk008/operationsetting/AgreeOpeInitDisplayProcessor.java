package nts.uk.screen.at.app.kmk.kmk008.operationsetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.ClosingDateType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Screen kmk008 G : 初期表示を行う
 */
@Stateless
public class AgreeOpeInitDisplayProcessor {

    @Inject
    I18NResourcesForUK ukResource;

    @Inject
    private AgreementOperationSettingRepository operationSettingRepository;

    public AgreementOperationSettingDto find() {

        AgreementOperationSettingDto operationSettingDto = new AgreementOperationSettingDto();

        List<EnumConstant> startingMonthEnum = EnumAdaptor.convertToValueNameList(StartingMonthType.class, ukResource);
        List<EnumConstant> closingDateType = EnumAdaptor.convertToValueNameList(ClosingDateType.class, ukResource);

        operationSettingDto.setStartingMonthEnum(startingMonthEnum);
        operationSettingDto.setClosureDateEnum(closingDateType);

        Optional<AgreementOperationSetting> data = operationSettingRepository.find(AppContexts.user().companyId());

        if (data.isPresent()){
            AgreementOperationSettingDetailDto detailDto = new AgreementOperationSettingDetailDto();

            detailDto.setStartingMonth(data.get().getStartingMonth().value);
            detailDto.setClosureDate(data.get().getClosureDate().getClosureDay().v());
            detailDto.setSpecialConditionApplicationUse(data.get().isSpecicalConditionApplicationUse());
            detailDto.setYearSpecicalConditionApplicationUse(data.get().isYearSpecicalConditionApplicationUse());
            detailDto.setLastDayOfMonth(data.get().getClosureDate().getLastDayOfMonth());
            operationSettingDto.setAgreementOperationSettingDetailDto(detailDto);
        }
        return operationSettingDto;
    }
}
