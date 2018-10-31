package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSetRepository;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStatementLayoutSetRepository extends JpaRepository implements StatementLayoutSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementLayoutSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.statementLayoutSetPk.histId =:histId ";

    @Override
    public List<StatementLayoutSet> getAllStatementLayoutSet(){
        return Collections.emptyList();
    }

    @Override
    public Optional<StatementLayoutSet> getStatementLayoutSetById(String histId){
        return Optional.empty();
    }

    @Override
    public void add(StatementLayoutSet domain){

    }

    @Override
    public void update(StatementLayoutSet domain){
    }

    @Override
    public void remove(String histId){
    }
}
