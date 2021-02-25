package nts.uk.screen.at.infra.shift.workcycle;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternVal;
import nts.uk.screen.at.app.ksm003.find.WorkCycleQueryRepository;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaWorkCycleScreenRepository extends JpaRepository implements WorkCycleQueryRepository {

    private static final String SELECT_ALL = "SELECT f from KdpstDailyPatternSet f ";

    private static final String GET_ALL_BY_CID = SELECT_ALL + "WHERE f.kdpstDailyPatternSetPK.cid = :cid ORDER BY f.kdpstDailyPatternSetPK.patternCd ASC";

    private static final String GET_BY_CID_AND_CODE = SELECT_ALL + "WHERE f.kdpstDailyPatternSetPK.cid = :cid AND f.kdpstDailyPatternSetPK.patternCd = :code";

    private static final String GET_INFO_BY_CID_AND_CODE = "SELECT f FROM KdpstDailyPatternVal f WHERE f.kdpstDailyPatternValPK.cid = :cid and f.kdpstDailyPatternValPK.patternCd = :code ORDER BY f.kdpstDailyPatternValPK.dispOrder";

    /**
     * [3] get
     * @param cid
     * @param code
     * @return
     */
    @Override
    public Optional<WorkCycle> getByCidAndCode(String cid, String code) {
        val workCycle = this.queryProxy().query(GET_BY_CID_AND_CODE, KdpstDailyPatternSet.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .getSingle();
        if (workCycle.isPresent()) {
            val workCycleInfos =  this.queryProxy()
                    .query(GET_INFO_BY_CID_AND_CODE, KdpstDailyPatternVal.class)
                    .setParameter("cid", cid)
                    .setParameter("code", workCycle.get().getKdpstDailyPatternSetPK().patternCd)
                    .getList();
            return Optional.of(KdpstDailyPatternSet.toDomainGet(workCycle.get(), workCycleInfos));
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
        val workCycles = this.queryProxy().query(GET_ALL_BY_CID, KdpstDailyPatternSet.class).setParameter("cid", cid).getList();
        if (!workCycles.isEmpty()) {
            workCycles.stream().forEach(i -> {
                val workCycleInfos = this.queryProxy().query(GET_INFO_BY_CID_AND_CODE, KdpstDailyPatternVal.class).setParameter("cid", cid).setParameter("code", i.getKdpstDailyPatternSetPK().patternCd).getList();
                result.add(KdpstDailyPatternSet.toDomainGet(i, workCycleInfos));
            });
        }
        return result;
    }

}
