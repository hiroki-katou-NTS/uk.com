package nts.uk.ctx.pr.core.dom.laborinsurance;

import java.util.List;

public interface OccAccInsurBusiBurdenRatioRepository {
    List<OccAccInsurBusiBurdenRatio> getEmpInsurBusBurRatioByHisId(String hisId);

    void add(List<OccAccInsurBusiBurdenRatio> domain);

    void update(List<OccAccInsurBusiBurdenRatio> domain);

    void remove(String hisId);
}
