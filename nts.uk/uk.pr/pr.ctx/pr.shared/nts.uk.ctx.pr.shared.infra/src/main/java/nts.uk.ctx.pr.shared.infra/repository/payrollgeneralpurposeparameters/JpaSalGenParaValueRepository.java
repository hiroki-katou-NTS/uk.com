package nts.uk.ctx.pr.shared.infra.repository.payrollgeneralpurposeparameters;

import java.util.Optional;
import java.util.List;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValue;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValueRepository;
import nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenParamValue;
import nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenParamValuePk;

@Stateless
public class JpaSalGenParaValueRepository extends JpaRepository implements SalGenParaValueRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalGenParamValue f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParamValuePk.hisId =:hisId AND  f.salGenParamValuePk.selection =:selection ";

    @Override
    public List<SalGenParaValue> getAllSalGenParaValue(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalGenParamValue.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalGenParaValue> getSalGenParaValueById(String hisId, Integer selection){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalGenParamValue.class)
        .setParameter("hisId", hisId)
        .setParameter("selection", selection)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalGenParaValue domain){
        this.commandProxy().insert(QpbmtSalGenParamValue.toEntity(domain));
    }

    @Override
    public void update(SalGenParaValue domain){
        this.commandProxy().update(QpbmtSalGenParamValue.toEntity(domain));
    }

    @Override
    public void remove(String hisId, Integer selection){
        this.commandProxy().remove(QpbmtSalGenParamValue.class, new QpbmtSalGenParamValuePk(hisId, selection));
    }
}
