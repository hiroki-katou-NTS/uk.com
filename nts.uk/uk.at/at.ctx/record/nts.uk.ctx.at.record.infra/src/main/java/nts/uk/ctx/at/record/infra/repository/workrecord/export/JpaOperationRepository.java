package nts.uk.ctx.at.record.infra.repository.workrecord.export;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.operation.OperationExcelRepo;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiPerformEdFun;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtFormatPerformance;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtMonPerformanceFun;

@Stateless
public class JpaOperationRepository extends JpaRepository implements OperationExcelRepo
{

    private static final String SELECT_ALL_QUERY_STRING_DAILY = "SELECT f FROM KrcmtDaiPerformEdFun f";
    private static final String SELECT_BY_KEY_STRING_DAILY = SELECT_ALL_QUERY_STRING_DAILY + " WHERE  f.daiPerformanceFunPk.cid =:cid ";

    //Monthly
    private static final String SELECT_ALL_QUERY_STRING_MONTHLY = "SELECT a FROM KrcmtMonPerformanceFun a";
    private static final String SELECT_BY_KEY_STRING_MONTHLY = SELECT_ALL_QUERY_STRING_MONTHLY + " WHERE  a.monPerformanceFunPk.cid =:cid ";
    //format
    private static final String SELECT_ALL_QUERY_STRING_FORMAT = "SELECT b FROM KrcmtFormatPerformance b";
    private static final String SELECT_BY_KEY_STRING_FORMAT = SELECT_ALL_QUERY_STRING_FORMAT + " WHERE  b.formatPerformancePk.cid =:cid ";

    @Override
    public Optional<FormatPerformance> getFormatPerformanceById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING_FORMAT, KrcmtFormatPerformance.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }
  
    @Override
    public Optional<DaiPerformanceFun> getDaiPerformanceFunById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING_DAILY, KrcmtDaiPerformEdFun.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }


    @Override
    public Optional<MonPerformanceFun> getMonPerformanceFunById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING_MONTHLY, KrcmtMonPerformanceFun.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

}
