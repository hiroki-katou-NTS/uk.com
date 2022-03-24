package nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting;

import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthlyDto;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormat;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionDefaultFormatRepository;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 規定フォーマットを取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AnyPeriodCorrectionDefaultFormatSettingQuery {
    @Inject
    private AnyPeriodCorrectionDefaultFormatRepository defaultFormatRepo;

    @Inject
    private AnyPeriodCorrectionFormatSettingRepository formatSettingRepo;

    public AnyPeriodCorrectionFormatDto getDefaultFormatSetting() {
        String companyId = AppContexts.user().companyId();
        Optional<AnyPeriodCorrectionDefaultFormat> defaultFormat = defaultFormatRepo.get(companyId);
        if (defaultFormat.isPresent()) {
            return formatSettingRepo.get(companyId, defaultFormat.get().getCode().v())
                    .map(setting -> new AnyPeriodCorrectionFormatDto(
                            setting.getCode().v(),
                            setting.getName().v(),
                            setting.getSheetSetting().getListSheetCorrectedMonthly().stream().map(SheetCorrectedMonthlyDto::fromDomain).collect(Collectors.toList())
                    )).orElse(null);
        } else {
            return null;
        }
    }
}
