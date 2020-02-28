package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatioRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurPreRate;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtEmpInsurPreRate;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtEmpInsurPreRatePk;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Stateless
public class JpaEmpInsurPreRateRepository extends JpaRepository implements EmpInsurBusBurRatioRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpInsurPreRate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurPreRatePk.cid =:cid AND  f.empInsurPreRatePk.historyId =:historyId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING  + " WHERE  f.empInsurPreRatePk.cid =:cid ORDER BY f.startYearMonth DESC";
    private static final String DELETE_BY_HIS_ID = "DELETE FROM QpbmtEmpInsurPreRate f "
            + "WHERE f.empInsurPreRatePk.cid =:cid AND f.empInsurPreRatePk.historyId =:hisId";
    
    @Override
    public List<EmpInsurBusBurRatio> getEmpInsurPreRateById(String cid, String historyId){
    	Optional<QpbmtEmpInsurPreRate> empInsurPreRate = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpInsurPreRate.class)
    			.setParameter("cid", cid)
    			.setParameter("historyId", historyId)
    			.getSingle();
    	if(empInsurPreRate.isPresent()) {
    		return empInsurPreRate.get().toDomain();
    	}
    	return new ArrayList<EmpInsurBusBurRatio>();
    }
    
    @Override
    public EmpInsurHis getEmpInsurHisByCid(String cid){
    	List<YearMonthHistoryItem> item = this.queryProxy().query(SELECT_BY_CID, QpbmtEmpInsurPreRate.class)
    			.setParameter("cid", cid)
    			.getList(c -> c.toDomainYearMonth());
    	EmpInsurHis empInsurHis = new EmpInsurHis(cid , item);
    	return empInsurHis;	
    }
    
    @Override
	public void add(EmpInsurPreRate domain, String cId, YearMonthHistoryItem item) {
		this.commandProxy().insert(QpbmtEmpInsurPreRate.toEntity(domain, cId, item));
	}

	@Override
	public void update(EmpInsurPreRate domain, String cId, YearMonthHistoryItem item) {
		this.commandProxy().update(QpbmtEmpInsurPreRate.toEntity(domain, cId, item));
	}
	
	@Override
	public void update(YearMonthHistoryItem item, String cId) {
		Optional<QpbmtEmpInsurPreRate> empInsurPreRate = this.queryProxy().find(new QpbmtEmpInsurPreRatePk(cId, item.identifier()), QpbmtEmpInsurPreRate.class);
		if (empInsurPreRate.isPresent()) {
			empInsurPreRate.get().startYearMonth = item.start().v();
			empInsurPreRate.get().endYearMonth = item.end().v();
			this.commandProxy().update(empInsurPreRate.get());
		}
	}

	@Override
	public void remove(String cid, String historyId) {
		this.getEntityManager().createQuery(DELETE_BY_HIS_ID, QpbmtEmpInsurPreRate.class).setParameter("cid", cid).setParameter("hisId", historyId).executeUpdate();
	}
}
