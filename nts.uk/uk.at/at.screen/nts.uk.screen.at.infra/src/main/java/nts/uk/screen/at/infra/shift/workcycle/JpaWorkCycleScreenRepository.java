package nts.uk.screen.at.infra.shift.workcycle;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCycle;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCycleDtl;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCyclePK;
import nts.uk.screen.at.app.ksm003.find.WorkCycleQueryRepository;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaWorkCycleScreenRepository extends JpaRepository implements WorkCycleQueryRepository {

    private static final String SELECT_ALL = "SELECT f from KscmtWorkingCycle f ";

    private static final String GET_ALL_BY_CID = SELECT_ALL + "WHERE f.kscmtWorkingCyclePK.cid = :cid ORDER BY f.kscmtWorkingCyclePK.workCycleCode ASC";

    private static final String GET_BY_CID_AND_CODE = SELECT_ALL + "WHERE f.kscmtWorkingCyclePK.cid = :cid AND f.kscmtWorkingCyclePK.workCycleCode = :code";

    private static final String GET_INFO_BY_CID_AND_CODE = "SELECT f FROM KscmtWorkingCycleDtl f WHERE f.kscmtWorkingCycleDtlPK.cid = :cid and f.kscmtWorkingCycleDtlPK.workingCycleCode = :code";

    /**
     * [3] get
     * @param cid
     * @param code
     * @return
     */
    @Override
    public Optional<WorkCycle> getByCidAndCode(String cid, String code) {
        val workCycle = this.queryProxy().query(GET_BY_CID_AND_CODE, KscmtWorkingCycle.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .getSingle();
        if (workCycle.isPresent()) {
            val workCycleInfos =  this.queryProxy()
                    .query(GET_INFO_BY_CID_AND_CODE, KscmtWorkingCycleDtl.class)
                    .setParameter("cid", cid)
                    .setParameter("code", workCycle.get().getKscmtWorkingCyclePK().workCycleCode)
                    .getList();
            return Optional.of(KscmtWorkingCycle.toDomain(workCycle.get(), workCycleInfos));
        }
        return Optional.empty();
    }

    /**
     * [4] 会社の勤務サイクルリストを取得する
     * @param cid
     * @return
     */
    @Override
    public List<WorkCycle> getByCid(String cid) {
        List<WorkCycle> result = new ArrayList<>();
        val workCycles = this.queryProxy().query(GET_ALL_BY_CID, KscmtWorkingCycle.class).setParameter("cid", cid).getList();
        if (!workCycles.isEmpty()) {
            workCycles.stream().forEach(i -> {
                val workCycleInfos = this.queryProxy().query(GET_INFO_BY_CID_AND_CODE, KscmtWorkingCycleDtl.class).setParameter("cid", cid).setParameter("code", i.getKscmtWorkingCyclePK().workCycleCode).getList();
                result.add(KscmtWorkingCycle.toDomain(i, workCycleInfos));
            });
        }
        return result;
    }

}
