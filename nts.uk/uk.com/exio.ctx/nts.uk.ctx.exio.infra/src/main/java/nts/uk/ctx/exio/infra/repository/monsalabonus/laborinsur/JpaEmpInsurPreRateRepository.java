package nts.uk.ctx.exio.infra.repository.monsalabonus.laborinsur;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurPreRate;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurPreRateRepository;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtEmpInsurPreRate;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtEmpInsurPreRatePk;

@Stateless
public class JpaEmpInsurPreRateRepository extends JpaRepository implements EmpInsurPreRateRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpInsurPreRate f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurPreRatePk.hisId =:hisId AND  f.empInsurPreRatePk.empPreRateId =:empPreRateId ";
    private static final String SELECT_BY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurPreRatePk.hisId =:hisId";
    
    @Override
    public List<EmpInsurPreRate> getAllEmpInsurPreRate(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtEmpInsurPreRate.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpInsurPreRate> getEmpInsurPreRateById(String hisId, String empPreRateId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpInsurPreRate.class)
        .setParameter("hisId", hisId)
        .setParameter("empPreRateId", empPreRateId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmpInsurPreRate domain){
        this.commandProxy().insert(QpbmtEmpInsurPreRate.toEntity(domain));
    }

    @Override
    public void update(EmpInsurPreRate domain){
        this.commandProxy().update(QpbmtEmpInsurPreRate.toEntity(domain));
    }

    @Override
    public void remove(String hisId, String empPreRateId){
        this.commandProxy().remove(QpbmtEmpInsurPreRate.class, new QpbmtEmpInsurPreRatePk(hisId, empPreRateId)); 
    }

	@Override
	public List<EmpInsurPreRate> getEmpInsurPreRateByCid(String hisId) {
		return this.queryProxy().query(SELECT_BY_HIS_ID, QpbmtEmpInsurPreRate.class)
		        .setParameter("hisId", hisId)
		        .getList(c->c.toDomain());
	}
}
