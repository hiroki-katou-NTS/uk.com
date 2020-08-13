package nts.uk.ctx.at.schedule.infra.repository.shift.workcycle;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCycle;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCycleDtl;
import nts.uk.ctx.at.schedule.infra.entity.shift.workcycle.KscmtWorkingCyclePK;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaWorkCycleRepository extends JpaRepository implements WorkCycleRepository {

    private static final String SELECT_ALL = "SELECT f from KscmtWorkingCycle f ";

    private static final String GET_ALL_BY_CID = SELECT_ALL + "WHERE f.kscmtWorkingCyclePK.cid = :cid ORDER BY f.kscmtWorkingCyclePK.workCycleCode ASC";

    private static final String GET_BY_CID_AND_CODE = SELECT_ALL + "WHERE f.kscmtWorkingCyclePK.cid = :cid AND f.kscmtWorkingCyclePK.workCycleCode = :code";

    private static final String GET_INFO_BY_CID_AND_CODE = "SELECT f FROM KscmtWorkingCycleDtl f WHERE f.kscmtWorkingCycleDtlPK.cid = :cid and f.kscmtWorkingCycleDtlPK.workingCycleCode = :code";

    private static final String DELETE_ALL_INFO_BY_CODE = "DELETE FROM KscmtWorkingCycleDtl f WHERE f.kscmtWorkingCycleDtlPK.cid = :cid and f.kscmtWorkingCycleDtlPK.workingCycleCode = :code";


    /**
     * [1] insert（勤務サイクル）
     * @param item
     */
    @Override
    public void add(WorkCycle item) {
        // Add work cycle
        this.commandProxy().insert(KscmtWorkingCycle.toEntity(item));
        // Add work cycle detail
        List<KscmtWorkingCycleDtl> infos = KscmtWorkingCycleDtl.toEntity(item);
        infos.stream().forEach(i -> {
            this.commandProxy().insert(i);
        });
    }

    /**
     * [2] update（勤務サイクル）
     * @param item
     */
    @Override
    public void update(WorkCycle item) {
        val oldItem = this.getEntityManager().find(KscmtWorkingCycle.class,new KscmtWorkingCyclePK(item.getCid(),item.getCode().v()) );
        this.commandProxy().update(oldItem.updateEntity(item));
        // Delete detail
        deleteDetail(item.getCid(),item.getCode().v());
        // Update detail
        List<KscmtWorkingCycleDtl> infos = KscmtWorkingCycleDtl.toEntity(item);
        infos.stream().forEach(i -> {
            this.commandProxy().insert(i);
        });
    }

    private void deleteDetail(String cid, String code) {
        this.getEntityManager().createQuery(DELETE_ALL_INFO_BY_CODE, KscmtWorkingCycleDtl.class)
                .setParameter("cid", cid)
                .setParameter("code", code).executeUpdate();
        this.getEntityManager().flush();
    }
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

    /**
     * [5] exists（会社ID, 勤務サイクルコード)
     * @param cid
     * @param code
     * @return
     */
    @Override
    public boolean exists(String cid, String code) {
        val result = this.queryProxy().query(GET_BY_CID_AND_CODE, KscmtWorkingCycle.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .getSingle();
        if (result.isPresent())
            return true;
        return false;
    }

    @Override
    public void delete(String cid, String code) {
        KscmtWorkingCyclePK key = new KscmtWorkingCyclePK(cid, code);
        this.commandProxy().remove(KscmtWorkingCycle.class, key);
        this.queryProxy().query(DELETE_ALL_INFO_BY_CODE, KscmtWorkingCycleDtl.class).setParameter("cid", cid).setParameter("code", code);
    }




}
