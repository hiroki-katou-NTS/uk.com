package nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting;

import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthlyDto;
import nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全てのフォーマット設定を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AnyPeriodCorrectionFormatSettingAllQuery {
    @Inject
    private AnyPeriodCorrectionFormatSettingRepository repository;

    public List<AnyPeriodCorrectionFormatDto> getSettings() {
        return repository.getAll(AppContexts.user().companyId()).stream()
                .map(setting -> new AnyPeriodCorrectionFormatDto(
                        setting.getCode().v(),
                        setting.getName().v(),
                        setting.getSheetSetting().getListSheetCorrectedMonthly().stream().map(SheetCorrectedMonthlyDto::fromDomain).collect(Collectors.toList())
                )).collect(Collectors.toList());
    }
}
