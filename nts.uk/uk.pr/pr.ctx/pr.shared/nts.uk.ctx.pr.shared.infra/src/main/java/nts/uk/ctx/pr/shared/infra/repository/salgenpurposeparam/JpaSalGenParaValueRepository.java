package nts.uk.ctx.pr.shared.infra.repository.salgenpurposeparam;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaValue;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaValueRepository;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenParamValue;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenParamValuePk;

@Stateless
public class JpaSalGenParaValueRepository extends JpaRepository implements SalGenParaValueRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSalGenParamValue f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParamValuePk.hisId =:hisId ";

    @Override
    public List<SalGenParaValue> getAllSalGenParaValue(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtSalGenParamValue.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalGenParaValue> getSalGenParaValueById(String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtSalGenParamValue.class)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalGenParaValue domain){
        this.commandProxy().insert(QqsmtSalGenParamValue.toEntity(domain));
    }

    @Override
    public void update(SalGenParaValue domain){
        this.commandProxy().update(QqsmtSalGenParamValue.toEntity(domain));
    }

    @Override
    public void remove(String hisId){
        this.commandProxy().remove(QqsmtSalGenParamValue.class, new QqsmtSalGenParamValuePk(hisId));
    }
}
