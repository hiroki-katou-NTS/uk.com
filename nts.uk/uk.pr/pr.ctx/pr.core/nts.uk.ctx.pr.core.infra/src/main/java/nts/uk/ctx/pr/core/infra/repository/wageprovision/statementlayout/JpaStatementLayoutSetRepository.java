package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutSetPk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaStatementLayoutSetRepository extends JpaRepository implements StatementLayoutSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementLayoutSet f";
    private static final String SELECT_BY_HISTORY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.statementLayoutSetPk.histId =:histId ";

    @Override
    public List<StatementLayoutSet> getAllStatementLayoutSet(){
        return Collections.emptyList();
    }

    @Override
    public Optional<StatementLayoutSet> getStatementLayoutSetById(String histId){
        List<QpbmtStatementLayoutSet> statementLayoutSetEntityList = this.queryProxy().query(SELECT_BY_HISTORY_ID, QpbmtStatementLayoutSet.class)
                .setParameter("histId", histId)
                .getList();
        List<SettingByCtg> settingByCtgList = statementLayoutSetEntityList.stream().map(x -> x.toDomain()).collect(Collectors.toList());
        return  statementLayoutSetEntityList.isEmpty() ? Optional.empty() : Optional.of(new StatementLayoutSet(histId,
                statementLayoutSetEntityList.get(0).layoutPattern, settingByCtgList));
    }

    @Override
    public void add(StatementLayoutSet domain){

    }

    @Override
    public void update(StatementLayoutSet domain){
    }

    @Override
    public void remove(String histId){
        for(CategoryAtr category : CategoryAtr.values()) {
            this.commandProxy().remove(QpbmtStatementLayoutSet.class, new QpbmtStatementLayoutSetPk(histId, category.value));
        }
    }

}
