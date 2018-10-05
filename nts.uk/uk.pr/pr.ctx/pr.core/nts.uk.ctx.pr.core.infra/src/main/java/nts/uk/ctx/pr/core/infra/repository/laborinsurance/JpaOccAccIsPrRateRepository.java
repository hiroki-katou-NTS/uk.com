package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.*;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccIsPrRate;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccIsPrRatePk;


import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class JpaOccAccIsPrRateRepository extends JpaRepository implements OccAccIsPrRateRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccIsPrRate f";
    private static final String SELECT_BY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsPrRatePk.hisId =:hisId";
    private static final String DELETE_BY_HIS_ID = "DELETE FROM QpbmtOccAccIsPrRate f "
            + "WHERE f.occAccIsPrRatePk.hisId =:hisId";


    @Override
    public OccAccIsPrRate getOccAccIsPrRateByHisId(String hisId) {
        List<QpbmtOccAccIsPrRate> occAccIsPrRateList = this.queryProxy().query(SELECT_BY_HIS_ID, QpbmtOccAccIsPrRate.class)
                .setParameter("hisId", hisId)
                .getList();

        return new OccAccIsPrRate(hisId,toDomain(occAccIsPrRateList));
    }
    private List<OccAccInsurBusiBurdenRatio> toDomain(List<QpbmtOccAccIsPrRate> entities) {

        List<OccAccInsurBusiBurdenRatio> occAccInsurBusiBurdenRatio = new ArrayList<OccAccInsurBusiBurdenRatio>();
        if (entities == null || entities.isEmpty()) {
            return occAccInsurBusiBurdenRatio;
        }
        entities.forEach(entity -> {
            occAccInsurBusiBurdenRatio.add( new OccAccInsurBusiBurdenRatio(entity.occAccIsPrRatePk.occAccInsurBusNo,
                    EnumAdaptor.valueOf(entity.fracClass,InsuPremiumFractionClassification.class),new InsuranceRate(entity.empConRatio)));
        });
        return occAccInsurBusiBurdenRatio;
    }

    @Override
    public void remove(int occAccInsurBusNo, String hisId){
        this.commandProxy().remove(QpbmtOccAccIsPrRate.class, new QpbmtOccAccIsPrRatePk(occAccInsurBusNo, hisId));
    }

	@Override
	public void remove(String hisId) {
		this.getEntityManager().createQuery(DELETE_BY_HIS_ID, QpbmtOccAccIsPrRate.class).setParameter("hisId", hisId).executeUpdate();	
	}

    @Override
    public void add(List<OccAccInsurBusiBurdenRatio> domain,String hisId) {
        this.commandProxy().insertAll(QpbmtOccAccIsPrRate.toEntity(domain,hisId));
    }

    @Override
    public void update(List<OccAccInsurBusiBurdenRatio> domain,String hisId) {
        this.commandProxy().updateAll(QpbmtOccAccIsPrRate.toEntity(domain,hisId));
    }

}
