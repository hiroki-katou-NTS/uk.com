package nts.uk.ctx.exio.infra.repository.monsalabonus.laborinsur;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHis;
import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurHisRepository;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtEmpInsurHis;
import nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur.QpbmtEmpInsurHisPk;

@Stateless
public class JpaEmpInsurHisRepository extends JpaRepository implements EmpInsurHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpInsurHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurHisPk.cid =:cid AND  f.empInsurHisPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurHisPk.cid =:cid ";
    
    @Override
    public List<EmpInsurHis> getAllEmpInsurHis(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtEmpInsurHis.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmpInsurHis> getEmpInsurHisById(String cid, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpInsurHis.class)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EmpInsurHis domain){
        this.commandProxy().insert(QpbmtEmpInsurHis.toEntity(domain));
    }

    @Override
    public void update(EmpInsurHis domain){
        this.commandProxy().update(QpbmtEmpInsurHis.toEntity(domain));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtEmpInsurHis.class, new QpbmtEmpInsurHisPk(cid, hisId)); 
    }

	@Override
	public List<EmpInsurHis> getEmpInsurHisByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, QpbmtEmpInsurHis.class).setParameter("cid", cid).
				getList(item -> item.toDomain());
	}
}
