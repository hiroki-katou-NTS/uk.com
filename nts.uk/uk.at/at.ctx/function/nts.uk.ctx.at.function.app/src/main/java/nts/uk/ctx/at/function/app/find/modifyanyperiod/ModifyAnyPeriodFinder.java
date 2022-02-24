package nts.uk.ctx.at.function.app.find.modifyanyperiod;


import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyPerformanceCodeDto;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormat;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSetting;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.enums.PCSmartPhoneAtt;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class ModifyAnyPeriodFinder {

    @Inject
    private AnyPeriodCorrectionDefaultFormatRepository anyPeriodCorrectionDefaultFormatRepository;

    @Inject
    private AnyPeriodCorrectionFormatSettingRepository formatSettingRepository;

    public List<ModifyAnyPeriodDto> getAllModifyAnyPeriod() {
        String cid = AppContexts.user().companyId();
        LoginUserContext login = AppContexts.user();
        String companyId = login.companyId();

        List<ModifyAnyPeriodDto> modifyAnyPeriodDtos = new ArrayList<ModifyAnyPeriodDto>();

        List<AnyPeriodCorrectionFormatSetting> listDailyFormat = this.formatSettingRepository
                .getAll(companyId);
        for (AnyPeriodCorrectionFormatSetting format : listDailyFormat) {
            Optional<AnyPeriodCorrectionDefaultFormat> optionalFormat = this.anyPeriodCorrectionDefaultFormatRepository.get(companyId);
            boolean defaultInitial = optionalFormat.isPresent()&&optionalFormat.get().getCode().equals(format.getCode());
            ModifyAnyPeriodDto dto = new ModifyAnyPeriodDto(format.getCode().v(),
                    format.getName().v(), defaultInitial);
        }
        modifyAnyPeriodDtos
                .sort((c1, c2) -> c1.getCode().compareTo(c2.getCode()));

        return modifyAnyPeriodDtos;

    }

    public ModifyAnyPeriodDto getByCode(String code) {
        String companyId = AppContexts.user().companyId();
        List<ModifyAnyPeriodDto> modifyAnyPeriodDtos = new ArrayList<ModifyAnyPeriodDto>();
        Optional<AnyPeriodCorrectionFormatSetting> optionalFormatSetting = this.formatSettingRepository
                .get(companyId,code);
        Optional<AnyPeriodCorrectionDefaultFormat> optionalFormat = this.anyPeriodCorrectionDefaultFormatRepository.get(companyId);

        if(optionalFormatSetting.isPresent()){
            boolean defaultInitial = optionalFormat.isPresent()&&optionalFormat.get().getCode().equals(optionalFormatSetting.get().getCode());
            return new ModifyAnyPeriodDto(optionalFormatSetting.get().getCode().v(),
                    optionalFormatSetting.get().getName().v(), defaultInitial);
        }
        return null;

    }
}
