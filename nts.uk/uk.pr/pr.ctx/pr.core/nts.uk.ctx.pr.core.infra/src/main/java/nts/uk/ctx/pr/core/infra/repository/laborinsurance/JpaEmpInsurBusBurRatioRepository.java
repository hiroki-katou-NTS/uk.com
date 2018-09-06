package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatio;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatioRepository;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtEmpInsurBusBurRatio;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtEmpInsurBusBurRatioPk;

@Stateless
public class JpaEmpInsurBusBurRatioRepository extends JpaRepository implements EmpInsurBusBurRatioRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpInsurBusBurRatio f";
    private static final String SELECT_BY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurBusBurRatioPk.hisId =:hisId";
    

    @Override
    public void add(EmpInsurBusBurRatio domain){
        this.commandProxy().insert(QpbmtEmpInsurBusBurRatio.toEntity(domain));
    }

    @Override
    public void update(EmpInsurBusBurRatio domain){
        this.commandProxy().update(QpbmtEmpInsurBusBurRatio.toEntity(domain));
    }

    @Override
    public void remove(String hisId, int empPreRateId){
        this.commandProxy().remove(QpbmtEmpInsurBusBurRatio.class, new QpbmtEmpInsurBusBurRatioPk(hisId, empPreRateId)); 
    }

	@Override
	public List<EmpInsurBusBurRatio> getEmpInsurBusBurRatioByHisId(String hisId) {
		return this.queryProxy().query(SELECT_BY_HIS_ID, QpbmtEmpInsurBusBurRatio.class)
		        .setParameter("hisId", hisId)
		        .getList(c->c.toDomain());
	}
}
