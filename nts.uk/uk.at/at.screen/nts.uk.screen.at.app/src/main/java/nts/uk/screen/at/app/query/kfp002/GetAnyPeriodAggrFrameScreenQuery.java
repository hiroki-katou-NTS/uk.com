package nts.uk.screen.at.app.query.kfp002;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod.AnyAggrPeriodDto;
import nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodImportFinder;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * UKDesign.UniversalK.就業.KFP_任意期間集計.KFP002_任意期間の修正.A：任意期間の修正.メニュー別OCD
 * 任意期間集計枠を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GetAnyPeriodAggrFrameScreenQuery {
    @Inject
    private OptionalAggrPeriodImportFinder finder;

    public List<AnyAggrPeriodDto> getAnyPeriodAggrFrames() {
        List<AnyAggrPeriodDto> result = finder.findAll();
        if (result.isEmpty()) throw new BusinessException("Msg_3277");
        return result;
    }
}
