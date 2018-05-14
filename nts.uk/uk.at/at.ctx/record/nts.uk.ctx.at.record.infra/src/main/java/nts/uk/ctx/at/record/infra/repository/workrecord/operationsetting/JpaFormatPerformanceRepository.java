package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtFormatPerformance;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtFormatPerformancePk;

@Stateless
public class JpaFormatPerformanceRepository extends JpaRepository implements FormatPerformanceRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtFormatPerformance f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.formatPerformancePk.cid =:cid ";

    @Override
    public List<FormatPerformance> getAllFormatPerformance(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtFormatPerformance.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<FormatPerformance> getFormatPerformanceById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, KrcmtFormatPerformance.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(FormatPerformance domain){
        this.commandProxy().insert(KrcmtFormatPerformance.toEntity(domain));
    }

    @Override
    public void update(FormatPerformance domain){
        KrcmtFormatPerformance newFormatPerformance = KrcmtFormatPerformance.toEntity(domain);
        KrcmtFormatPerformance updateFormatPerformance = this.queryProxy().find(newFormatPerformance.formatPerformancePk, KrcmtFormatPerformance.class).get();
        if (null == updateFormatPerformance) {
            return;
        }
        updateFormatPerformance.settingUnitType = newFormatPerformance.settingUnitType;
        this.commandProxy().update(updateFormatPerformance);
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KrcmtFormatPerformance.class, new KrcmtFormatPerformancePk(cid)); 
    }
}
