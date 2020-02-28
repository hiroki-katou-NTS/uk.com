package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;

import java.util.Optional;
import java.util.List;

/**
* 平均賃金計算設定
*/
public interface AverageWageCalculationSetRepository
{

    List<AverageWageCalculationSet> getAllAverageWageCalculationSet();

    List<StatementCustom> getStatemetPaymentItem(String cd);

    List<StatementCustom> getStatemetAttendanceItem(String cid);

    List<StatementCustom> getAllStatemetPaymentItem(String cd);

    List<StatementCustom> getAllStatemetAttendanceItem(String cid);

    Optional<AverageWageCalculationSet> getAverageWageCalculationSetById(String cid);

    void add(AverageWageCalculationSet domain);

    void update(AverageWageCalculationSet domain);

    void remove(String cid);

}
