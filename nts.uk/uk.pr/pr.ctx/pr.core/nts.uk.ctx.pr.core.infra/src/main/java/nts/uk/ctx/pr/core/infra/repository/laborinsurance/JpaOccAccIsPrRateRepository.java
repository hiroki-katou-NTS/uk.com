package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.InsuPremiumFractionClassification;
import nts.uk.ctx.pr.core.dom.laborinsurance.InsuranceRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccInsurBusiBurdenRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRate;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccIsPrRateRepository;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtOccAccIsPrRate;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class JpaOccAccIsPrRateRepository extends JpaRepository implements OccAccIsPrRateRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtOccAccIsPrRate f";
    private static final String SELECT_BY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.occAccIsPrRatePk.hisId =:hisId AND f.occAccIsPrRatePk.cid = :cid";
    private static final String DELETE_BY_HIS_ID = "DELETE FROM QpbmtOccAccIsPrRate f"
            + " WHERE f.occAccIsPrRatePk.cid =:cid AND f.occAccIsPrRatePk.hisId =:hisId";
    private static final String UPDATE_BY_HIS_ID_AND_CID = "UPDATE FROM QpbmtOccAccIsPrRate f "
    		+ " SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth"
    		+ " WHERE f.occAccIsPrRatePk.hisId =:hisId AND f.occAccIsPrRatePk.cid =:cid";
    private static final String SELECT_BY_CID =  "SELECT f.occAccIsPrRatePk.hisId, f.startYearMonth, f.endYearMonth FROM QpbmtOccAccIsPrRate f "
   		+ " WHERE  f.occAccIsPrRatePk.cid =:cid"
    		+ " GROUP BY f.occAccIsPrRatePk.hisId, f.startYearMonth, f.endYearMonth"
    		+ " ORDER BY f.startYearMonth";
   
    @Override
    public OccAccIsPrRate getOccAccIsPrRateByHisId(String cid, String hisId) {
        List<QpbmtOccAccIsPrRate> occAccIsPrRateList = this.queryProxy().query(SELECT_BY_HIS_ID, QpbmtOccAccIsPrRate.class)
                .setParameter("hisId", hisId)
                .getList();

        return new OccAccIsPrRate(hisId,toDomain(occAccIsPrRateList));
    }
    
    @Override
    public OccAccIsHis getOccAccIsHisByCid(String cid) {
        List<QpbmtOccAccIsPrRate> occAccIsPrRateList = this.queryProxy().query(SELECT_BY_CID, QpbmtOccAccIsPrRate.class)
                .setParameter("cid", cid)
                .getList();

        return new OccAccIsHis(cid ,QpbmtOccAccIsPrRate.toDomainHis(occAccIsPrRateList));
    }
    
    private List<OccAccInsurBusiBurdenRatio> toDomain(List<QpbmtOccAccIsPrRate> entities) {

        List<OccAccInsurBusiBurdenRatio> occAccInsurBusiBurdenRatio = new ArrayList<OccAccInsurBusiBurdenRatio>();
        if (entities == null || entities.isEmpty()) {
            return occAccInsurBusiBurdenRatio;
        }
        entities.forEach(entity -> {
            occAccInsurBusiBurdenRatio.add( new OccAccInsurBusiBurdenRatio(entity.occAccIsPrRatePk.occAccIsBusNo,
                    EnumAdaptor.valueOf(entity.fracClass,InsuPremiumFractionClassification.class),new InsuranceRate(entity.empConRatio)));
        });
        return occAccInsurBusiBurdenRatio;
    }

	@Override
	public void remove(String cid, String hisId) {
		this.getEntityManager().createQuery(DELETE_BY_HIS_ID, QpbmtOccAccIsPrRate.class).setParameter("cid", cid).setParameter("hisId", hisId).executeUpdate();	
	}

    @Override
    public void add(List<OccAccInsurBusiBurdenRatio> domain, String cId, YearMonthHistoryItem yearMonthHistory) {
        this.commandProxy().insertAll(QpbmtOccAccIsPrRate.toEntity(domain, cId, yearMonthHistory));
    }

    @Override
    public void update(List<OccAccInsurBusiBurdenRatio> domain, String cId, YearMonthHistoryItem yearMonthHistory) {
        this.commandProxy().updateAll(QpbmtOccAccIsPrRate.toEntity(domain, cId, yearMonthHistory));
    }

	@Override
	public void update(String cid, YearMonthHistoryItem item) {
		this.getEntityManager().createQuery(UPDATE_BY_HIS_ID_AND_CID, QpbmtOccAccIsPrRate.class)
		.setParameter("startYearMonth", item.start().v())
		.setParameter("endYearMonth", item.end().v())
		.setParameter("hisId", item.identifier()).setParameter("cid", cid).executeUpdate();	
	}

}
