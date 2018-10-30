package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionItemDetailSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.DeductionItemDetailSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtDdtItemDetailSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtDdtItemDetailSetPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaDeductionItemDetailSetRepository extends JpaRepository implements DeductionItemDetailSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtDdtItemDetailSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.ddtItemDetailSetPk.histId =:histId ";

    @Override
    public List<DeductionItemDetailSet> getAllDeductionItemDetailSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtDdtItemDetailSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<DeductionItemDetailSet> getDeductionItemDetailSetById(String histId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtDdtItemDetailSet.class)
        .setParameter("histId", histId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(DeductionItemDetailSet domain){
        this.commandProxy().insert(QpbmtDdtItemDetailSet.toEntity(domain));
    }

    @Override
    public void update(DeductionItemDetailSet domain){
        this.commandProxy().update(QpbmtDdtItemDetailSet.toEntity(domain));
    }

    @Override
    public void remove(String histId){
        this.commandProxy().remove(QpbmtDdtItemDetailSet.class, new QpbmtDdtItemDetailSetPk(histId));
    }
}
