package nts.uk.ctx.at.schedule.infra.repository.dailypattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.DailyPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSetPK;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternVal;

/**
 * The Class JpaDailyPatternRepository.
 */
@Stateless
public class JpaDailyPatternRepository extends JpaRepository implements DailyPatternRepository {

	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;

	/** The Constant FIRST_LENGTH. */
	public static final int FIRST_LENGTH = 1;
	private static final String SELECT_ALL = "SELECT f from KdpstDailyPatternSet f ";

	private static final String GET_ALL_BY_CID = SELECT_ALL + "WHERE f.kdpstDailyPatternSetPK.cid = :cid ORDER BY f.kdpstDailyPatternSetPK.patternCd ASC";

	private static final String GET_BY_CID_AND_CODE = SELECT_ALL + "WHERE f.kdpstDailyPatternSetPK.cid = :cid AND f.kdpstDailyPatternSetPK.patternCd = :code";

	private static final String GET_INFO_BY_CID_AND_CODE = "SELECT f FROM KdpstDailyPatternVal f WHERE f.kdpstDailyPatternValPK.cid = :cid and f.kdpstDailyPatternValPK.patternCd = :code";

	private static final String DELETE_ALL_INFO_BY_CODE = "DELETE FROM KdpstDailyPatternVal f WHERE f.kdpstDailyPatternValPK.cid = :cid and f.kdpstDailyPatternValPK.patternCd = :code";


	/**
	 * [1] insert（勤務サイクル）
	 * @param item
	 */
	@Override
	public void add(WorkCycle item) {
		// Add work cycle
		this.commandProxy().insert(KdpstDailyPatternSet.toEntity(item));
		// Add work cycle detail
		List<KdpstDailyPatternVal> infos = KdpstDailyPatternVal.toEntity(item);
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
		val oldItem = this.getEntityManager().find(KdpstDailyPatternSet.class,new KdpstDailyPatternSetPK(item.getCid(),item.getCode().v()) );
		this.commandProxy().update(oldItem.updateEntity(item));
		// Delete detail
		deleteDetail(item.getCid(),item.getCode().v());
		// Update detail
		List<KdpstDailyPatternVal> infos = KdpstDailyPatternVal.toEntity(item);
		infos.stream().forEach(i -> {
			this.commandProxy().insert(i);
		});
	}

	private void deleteDetail(String cid, String code) {
		this.getEntityManager().createQuery(DELETE_ALL_INFO_BY_CODE, KdpstDailyPatternVal.class)
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

	/**
	 * [5] exists（会社ID, 勤務サイクルコード)
	 * @param cid
	 * @param code
	 * @return
	 */
	@Override
	public boolean exists(String cid, String code) {
		val result = this.queryProxy().query(GET_BY_CID_AND_CODE, KdpstDailyPatternSet.class)
				.setParameter("cid", cid)
				.setParameter("code", code)
				.getSingle();
		if (result.isPresent())
			return true;
		return false;
	}

	@Override
	public void delete(String cid, String code) {
		KdpstDailyPatternSetPK key = new KdpstDailyPatternSetPK(cid, code);
		this.commandProxy().remove(KdpstDailyPatternSet.class, key);

		this.getEntityManager().createQuery(DELETE_ALL_INFO_BY_CODE, KdpstDailyPatternVal.class)
				.setParameter("cid", cid)
				.setParameter("code", code).executeUpdate();
	}


}
