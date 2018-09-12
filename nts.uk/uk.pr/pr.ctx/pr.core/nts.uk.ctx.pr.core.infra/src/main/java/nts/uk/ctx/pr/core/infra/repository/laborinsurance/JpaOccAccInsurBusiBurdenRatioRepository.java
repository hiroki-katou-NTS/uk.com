package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusiBurdenRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusiBurdenRatioRepository;

import java.util.List;

public class JpaOccAccInsurBusiBurdenRatioRepository extends JpaRepository implements OccAccInsurBusiBurdenRatioRepository {
    @Override
    public List<OccAccInsurBusiBurdenRatio> getEmpInsurBusBurRatioByHisId(String hisId) {
        return null;
    }

    @Override
    public void add(List<OccAccInsurBusiBurdenRatio> domain) {

    }

    @Override
    public void update(List<OccAccInsurBusiBurdenRatio> domain) {

    }

    @Override
    public void remove(String hisId) {

    }
}
