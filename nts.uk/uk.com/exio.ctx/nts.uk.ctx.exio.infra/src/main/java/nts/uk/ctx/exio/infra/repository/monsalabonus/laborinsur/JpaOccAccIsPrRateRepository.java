package nts.uk.ctx.exio.infra.repository.monsalabonus.laborinsur;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.*;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtEmpInsurBusBurRatio;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccInsurBus;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsPrRate;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtOccAccIsPrRatePk;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaOccAccIsPrRateRepository extends JpaRepository implements OccAccIsPrRateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccIsPrRate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsPrRatePk.ocAcIsPrRtId =:ocAcIsPrRtId AND  f.occAccIsPrRatePk.hisId =:hisId ";
    private static final String SELECT_BY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsPrRatePk.hisId =:hisId";


    @Override
    public OccAccIsPrRate getOccAccIsPrRateByHisId(String hisId) {
        List<QpbmtOccAccIsPrRate> occAccIsPrRateList = this.queryProxy().query(SELECT_BY_HIS_ID, QpbmtOccAccIsPrRate.class)
                .setParameter("hisId", hisId)
                .getList();
        return new OccAccIsPrRate(hisId,toDomain(occAccIsPrRateList));
    }
    private List<OccAccInsurBusiBurdenRatio> toDomain(List<QpbmtOccAccIsPrRate> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        List<OccAccInsurBusiBurdenRatio> occAccInsurBusiBurdenRatio = new ArrayList<OccAccInsurBusiBurdenRatio>();
        entities.forEach(entity -> {
            occAccInsurBusiBurdenRatio.add( new OccAccInsurBusiBurdenRatio(entity.occAccIsPrRatePk.occAccInsurBusNo,
                    EnumAdaptor.valueOf(entity.fracClass,InsuPremiumFractionClassification.class),entity.empConRatio));
        });
        return occAccInsurBusiBurdenRatio;
    }

    @Override
    public void remove(int occAccInsurBusNo, String hisId){
        this.commandProxy().remove(QpbmtOccAccIsPrRate.class, new QpbmtOccAccIsPrRatePk(occAccInsurBusNo, hisId));
    }
}
