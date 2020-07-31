package nts.uk.ctx.at.schedule.infra.repository.shift.workcycle;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleDtlRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCycleDtl;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCycleDtlPK;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaWorkCycleDtlRepository extends JpaRepository implements WorkCycleDtlRepository {

    private static final String SELECT_ALL = "SELECT f FROM KscmtWorkingCycleDtl f ";

    private static final String SELECT_BY_CODE = SELECT_ALL + "WHERE f.kscmtWorkingCycleDtlPK.cid = :cid and f.kscmtWorkingCycleDtlPK.workingCycleCode = :code";

    private static final String DELETE_ALL_BY_CODE = "DELETE FROM KscmtWorkingCycleDtl f WHERE f.kscmtWorkingCycleDtlPK.cid = :cid and f.kscmtWorkingCycleDtlPK.workingCycleCode = :code";

    @Override
    public List<WorkCycleInfo> getByCode(String cid, String code) {
        return this.queryProxy().query(SELECT_BY_CODE, KscmtWorkingCycleDtl.class).setParameter("cid", cid).setParameter("code", code)
                .getList(x -> KscmtWorkingCycleDtl.toDomain(x));
    }

    @Override
    public void add(WorkCycle item) {
        List<KscmtWorkingCycleDtl> itemAddded = KscmtWorkingCycleDtl.toEntity(item);
        itemAddded.stream().forEach(i -> this.commandProxy().insert(i));
    }

    @Override
    public void update(WorkCycle item) {
        List<KscmtWorkingCycleDtl> itemUpdated = KscmtWorkingCycleDtl.toEntity(item);
        this.queryProxy().query(DELETE_ALL_BY_CODE, KscmtWorkingCycleDtl.class).setParameter("cid", item.getCid()).setParameter("code", item.getCode());
        itemUpdated.stream().forEach(i -> this.commandProxy().insert(i));
    }

    @Override
    public void delete(String cid, String code, int dispOrder) {
        KscmtWorkingCycleDtlPK key = new KscmtWorkingCycleDtlPK(cid, code, dispOrder);
        this.commandProxy().remove(KscmtWorkingCycleDtl.class, key);
    }
}
