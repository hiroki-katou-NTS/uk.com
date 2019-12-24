package nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset;

import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.AverageWageCalculationSetRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.StatementCustom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
/**
 * 平均賃金計算設定: Finder
 */
public class AverageWageCalculationSetFinder {

    @Inject
    private AverageWageCalculationSetRepository finder;

    public DataDisplayAverageDto getStatemetItem() {
        String cid = AppContexts.user().companyId();
        List lstDisplay = new ArrayList<>();
        AverageWageCalculationSetDto averageWageCalculationSet = finder.getAverageWageCalculationSetById(cid).map(item -> AverageWageCalculationSetDto.fromDomain(item)).orElse(null);
        List<StatementCustom> listStatementCustom = finder.getStatemetPaymentItem(cid);
        List<StatementDto> statemetPaymentItem = listStatementCustom.stream().map(item -> new StatementDto(item)).collect(Collectors.toList());
        List<StatementCustom> listStatemetAttendanceItem = finder.getStatemetAttendanceItem(cid);
        List<StatementDto> statemetAttendanceItem = listStatemetAttendanceItem.stream().map(item -> new StatementDto(item)).collect(Collectors.toList());
        DataDisplayAverageDto data = new DataDisplayAverageDto(averageWageCalculationSet, statemetPaymentItem, statemetAttendanceItem);
        return data;
    }

    public List<StatementDto> getStatemetItemByCategory(Integer categoryAtr) {
        String cid = AppContexts.user().companyId();

        if(categoryAtr == null) return new ArrayList<>();

        if(CategoryAtr.PAYMENT_ITEM.value == categoryAtr.intValue()) {
            return finder.getAllStatemetPaymentItem(cid).stream().map(item -> new StatementDto(item)).collect(Collectors.toList());
        } else if(CategoryAtr.ATTEND_ITEM.value == categoryAtr.intValue()){
            return finder.getAllStatemetAttendanceItem(cid).stream().map(item -> new StatementDto(item)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
