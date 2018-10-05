package nts.uk.ctx.at.record.infra.repository.monthly.roundingset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.roundingset.ItemRoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingProcessOfExcessOutsideTime;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.ctx.at.record.infra.entity.monthly.roundingset.KrcstMonExcoutRound;
import nts.uk.ctx.at.record.infra.entity.monthly.roundingset.KrcstMonItemRound;
import nts.uk.ctx.at.record.infra.entity.monthly.roundingset.KrcstMonItemRoundPK;
import nts.uk.ctx.at.record.infra.entity.monthly.roundingset.KrcstMonRoundSetPK;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;

/**
 * リポジトリ実装：月別実績の丸め設定
 * @author shuichu_ishida
 */
@Stateless
public class JpaRoundingSetOfMonthly extends JpaRepository implements RoundingSetOfMonthlyRepository {

	private static final String FIND_BY_CID_FOR_ITEM =
			"SELECT a FROM KrcstMonItemRound a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID_FOR_EXCOUT =
			"DELETE FROM KrcstMonExcoutRound a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID_FOR_ITEM =
			"DELETE FROM KrcstMonItemRound a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	/** 検索 */
	@Override
	public Optional<RoundingSetOfMonthly> find(String companyId) {

		val excoutRound = this.queryProxy().find(new KrcstMonRoundSetPK(companyId), KrcstMonExcoutRound.class);
		
		val itemRoundList = this.queryProxy()
				.query(FIND_BY_CID_FOR_ITEM, KrcstMonItemRound.class)
				.setParameter("companyId", companyId)
				.getList();
		
		if (!excoutRound.isPresent() && itemRoundList.size() == 0) return Optional.empty();
		
		return Optional.of(toDomain(companyId, excoutRound, itemRoundList));
	}
	
	/**
	 * エンティティ→ドメイン
	 * @param companyId 会社ID
	 * @param excoutRoundOpt エンティティ：時間外超過の時間丸め
	 * @param itemRoundList エンティティ：月別実績の項目丸め設定
	 * @return ドメイン：月別実績の丸め設定
	 */
	private static RoundingSetOfMonthly toDomain(String companyId,
			Optional<KrcstMonExcoutRound> excoutRoundOpt, List<KrcstMonItemRound> itemRoundList){
		
		// 時間外超過の時間丸め
		Optional<TimeRoundingOfExcessOutsideTime> timeRounding = Optional.empty();
		if (excoutRoundOpt.isPresent()){
			val excoutRound = excoutRoundOpt.get(); 
			timeRounding = Optional.of(TimeRoundingOfExcessOutsideTime.of(
					EnumAdaptor.valueOf(excoutRound.roundUnit, Unit.class),
					EnumAdaptor.valueOf(excoutRound.roundProc, RoundingProcessOfExcessOutsideTime.class)));
		}
		
		// 月別実績の項目丸め設定
		List<ItemRoundingSetOfMonthly> itemRoundSets = new ArrayList<>();
		for (val itemRound : itemRoundList){
			itemRoundSets.add(ItemRoundingSetOfMonthly.of(
					companyId,
					itemRound.PK.attendanceItemId,
					new TimeRoundingSetting(itemRound.roundUnit, itemRound.roundProc)));
		}
		
		return RoundingSetOfMonthly.of(companyId, timeRounding, itemRoundSets);
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(RoundingSetOfMonthly roundingSetOfMonthly) {

		// キー
		val companyId = roundingSetOfMonthly.getCompanyId();
		val key = new KrcstMonRoundSetPK(companyId);

		// 時間外超過の時間丸め
		val excoutRoundSetOpt = roundingSetOfMonthly.getTimeRoundingOfExcessOutsideTime();
		if (excoutRoundSetOpt.isPresent()){
			val excoutRoundSet = excoutRoundSetOpt.get();
			boolean isNeedPersist = false;
			KrcstMonExcoutRound entityExcoutRound = this.getEntityManager().find(KrcstMonExcoutRound.class, key);
			if (entityExcoutRound == null){
				isNeedPersist = true;
				entityExcoutRound = new KrcstMonExcoutRound();
				entityExcoutRound.PK = key;
			}
			entityExcoutRound.roundUnit = excoutRoundSet.getRoundingUnit().value;
			entityExcoutRound.roundProc = excoutRoundSet.getRoundingProcess().value;
			if (isNeedPersist) this.getEntityManager().persist(entityExcoutRound);
		}
		else {
			this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_EXCOUT)
					.setParameter("companyId", companyId)
					.executeUpdate();
		}
		
		// 月別実績の項目丸め設定
		val itemRoundSets = roundingSetOfMonthly.getItemRoundingSet();
		this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_ITEM)
				.setParameter("companyId", companyId)
				.executeUpdate();
		for (val itemRoundSet : itemRoundSets.values()){
			KrcstMonItemRound entityItemRound = new KrcstMonItemRound();
			entityItemRound.PK = new KrcstMonItemRoundPK(companyId, itemRoundSet.getAttendanceItemId());
			entityItemRound.roundUnit = itemRoundSet.getRoundingSet().getRoundingTime().value;
			entityItemRound.roundProc = itemRoundSet.getRoundingSet().getRounding().value;
			this.getEntityManager().persist(entityItemRound);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String companyId) {

		this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_EXCOUT)
				.setParameter("companyId", companyId)
				.executeUpdate();
		
		this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_ITEM)
				.setParameter("companyId", companyId)
				.executeUpdate();
	}

	/** Update monthly item rounding only */
	@Override
	public void persistAndUpdateMonItemRound(List<ItemRoundingSetOfMonthly> lstItemRounding, String companyId) {
		// 月別実績の項目丸め設定
		this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_ITEM)
				.setParameter("companyId", companyId)
				.executeUpdate();
		for (val itemRoundSet : lstItemRounding){
			KrcstMonItemRound entityItemRound = new KrcstMonItemRound();
			entityItemRound.PK = new KrcstMonItemRoundPK(companyId, itemRoundSet.getAttendanceItemId());
			entityItemRound.roundUnit = itemRoundSet.getRoundingSet().getRoundingTime().value;
			entityItemRound.roundProc = itemRoundSet.getRoundingSet().getRounding().value;
			this.getEntityManager().persist(entityItemRound);
		}
	}
}
