package nts.uk.screen.at.app.query.kfp002;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatDto;
import nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting.AnyPeriodCorrectionFormatSettingAllQuery;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GetAllAnyPeriodCorrectionFormatScreenQuery {
    @Inject
    private AnyPeriodCorrectionFormatSettingAllQuery formatSettingQuery;

    public List<AnyPeriodCorrectionFormatDto> getAllFormat() {
        List<AnyPeriodCorrectionFormatDto> result = formatSettingQuery.getSettings();
        if (result.isEmpty()) throw new BusinessException("Msg_1402");
        return result;
    }
}
