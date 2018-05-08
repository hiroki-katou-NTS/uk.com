package nts.uk.ctx.at.record.infra.repository.workrecord.operationsetting;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFunRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiPerformanceFun;
import nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting.KrcmtDaiPerformanceFunPk;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaDaiPerformanceFunRepository extends JpaRepository implements DaiPerformanceFunRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtDaiPerformanceFun f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.daiPerformanceFunPk.cid =:cid ";

    @Override
    public List<DaiPerformanceFun> getAllDaiPerformanceFun(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtDaiPerformanceFun.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<DaiPerformanceFun> getDaiPerformanceFunById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, KrcmtDaiPerformanceFun.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(DaiPerformanceFun domain){
        this.commandProxy().insert(KrcmtDaiPerformanceFun.toEntity(domain));
    }

    @Override
    public void update(DaiPerformanceFun domain){
        KrcmtDaiPerformanceFun newDaiPerformanceFun = KrcmtDaiPerformanceFun.toEntity(domain);
        KrcmtDaiPerformanceFun updateDaiPerformanceFun = this.queryProxy().find(newDaiPerformanceFun.daiPerformanceFunPk, KrcmtDaiPerformanceFun.class).get();
        if (null == updateDaiPerformanceFun) {
            return;
        }
        updateDaiPerformanceFun.comment = newDaiPerformanceFun.comment;
        updateDaiPerformanceFun.isCompleteConfirmOneMonth = newDaiPerformanceFun.isCompleteConfirmOneMonth;
        updateDaiPerformanceFun.isDisplayAgreementThirtySix = newDaiPerformanceFun.isDisplayAgreementThirtySix;
        updateDaiPerformanceFun.isFixClearedContent = newDaiPerformanceFun.isFixClearedContent;
        updateDaiPerformanceFun.isDisplayFlexWorker = newDaiPerformanceFun.isDisplayFlexWorker;
        updateDaiPerformanceFun.isUpdateBreak = newDaiPerformanceFun.isUpdateBreak;
        updateDaiPerformanceFun.isSettingTimeBreak = newDaiPerformanceFun.isSettingTimeBreak;
        updateDaiPerformanceFun.isDayBreak = newDaiPerformanceFun.isDayBreak;
        updateDaiPerformanceFun.isSettingAutoTime = newDaiPerformanceFun.isSettingAutoTime;
        updateDaiPerformanceFun.isUpdateEarly = newDaiPerformanceFun.isUpdateEarly;
        updateDaiPerformanceFun.isUpdateOvertime = newDaiPerformanceFun.isUpdateOvertime;
        updateDaiPerformanceFun.isUpdateOvertimeWithinLegal = newDaiPerformanceFun.isUpdateOvertimeWithinLegal;
        updateDaiPerformanceFun.isFixContentAuto = newDaiPerformanceFun.isFixContentAuto;
        this.commandProxy().update(updateDaiPerformanceFun);
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KrcmtDaiPerformanceFun.class, new KrcmtDaiPerformanceFunPk(cid)); 
    }
}
