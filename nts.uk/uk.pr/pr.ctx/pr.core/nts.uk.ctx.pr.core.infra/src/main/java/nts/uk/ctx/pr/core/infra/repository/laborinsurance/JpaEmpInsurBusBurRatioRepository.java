package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatio;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurBusBurRatioRepository;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtEmpInsurBusBurRatio;


@Stateless
public class JpaEmpInsurBusBurRatioRepository extends JpaRepository implements EmpInsurBusBurRatioRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpInsurBusBurRatio f";
    private static final String SELECT_BY_HIS_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurBusBurRatioPk.hisId =:hisId ORDER BY f.empInsurBusBurRatioPk.empPreRateId DESC";
    private static final String DELETE_BY_HIS_ID = "DELETE FROM QpbmtEmpInsurBusBurRatio f "
            + "WHERE f.empInsurBusBurRatioPk.hisId =:hisId";

    @Override
    public void add(List<EmpInsurBusBurRatio> domain){
        this.commandProxy().insertAll(QpbmtEmpInsurBusBurRatio.toEntity(domain));
    }

    @Override
    public void update(List<EmpInsurBusBurRatio> domain){
        this.commandProxy().updateAll(QpbmtEmpInsurBusBurRatio.toEntity(domain));
    }

    @Override
    public void remove(String hisId){
    	this.getEntityManager().createQuery(DELETE_BY_HIS_ID, QpbmtEmpInsurBusBurRatio.class).setParameter("hisId", hisId).executeUpdate();
    }

	@Override
	public List<EmpInsurBusBurRatio> getEmpInsurBusBurRatioByHisId(String hisId) {
		return this.queryProxy().query(SELECT_BY_HIS_ID, QpbmtEmpInsurBusBurRatio.class)
		        .setParameter("hisId", hisId)
		        .getList(c->c.toDomain());
	}
}
