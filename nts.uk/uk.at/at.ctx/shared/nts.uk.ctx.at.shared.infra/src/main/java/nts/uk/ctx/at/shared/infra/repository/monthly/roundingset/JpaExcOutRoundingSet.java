package nts.uk.ctx.at.shared.infra.repository.monthly.roundingset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.ItemRoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.monthly.roundingset.RoundingProcessOfExcessOutsideTime;
import nts.uk.ctx.at.shared.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.ctx.at.shared.infra.entity.monthly.roundingset.KrcstMonExcOutRound;
import nts.uk.ctx.at.shared.infra.entity.monthly.roundingset.KrcstMonItemRound;
import nts.uk.ctx.at.shared.infra.entity.monthly.roundingset.KrcstMonItemRoundPK;
import nts.uk.ctx.at.shared.infra.entity.monthly.roundingset.KrcstMonRoundSetPK;

/**
 * リポジトリ実装：月別実績の丸め設定
 * @author shuichu_ishida
 */
@Stateless
public class JpaExcOutRoundingSet extends JpaRepository implements RoundingSetOfMonthlyRepository {

	private static final String REMOVE_BY_CID_FOR_EXCOUT =
			"DELETE FROM KrcstMonExcoutRound a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID_FOR_ITEM =
			"DELETE FROM KrcstMonItemRound a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	/** 検索 */
	@Override
	public Optional<TimeRoundingOfExcessOutsideTime> find(String companyId) {

		Optional<KrcstMonExcOutRound> excoutRound = this.queryProxy().find(new KrcstMonRoundSetPK(companyId), KrcstMonExcOutRound.class);
		
		return toDomain(companyId, excoutRound);
	}
	
	/**
	 * エンティティ→ドメイン
	 * @param companyId 会社ID
	 * @param excoutRoundOpt エンティティ：時間外超過の時間丸め
	 * @param itemRoundList エンティティ：月別実績の項目丸め設定
	 * @return ドメイン：月別実績の丸め設定
	 */
	private static Optional<TimeRoundingOfExcessOutsideTime> toDomain(String companyId,
			Optional<KrcstMonExcOutRound> excoutRoundOpt){
		
		// 時間外超過の時間丸め
		Optional<TimeRoundingOfExcessOutsideTime> timeRounding = Optional.empty();
		if (excoutRoundOpt.isPresent()){
			val excoutRound = excoutRoundOpt.get(); 
			timeRounding = Optional.of(TimeRoundingOfExcessOutsideTime.of(companyId,
					EnumAdaptor.valueOf(excoutRound.roundUnit, Unit.class),
					EnumAdaptor.valueOf(excoutRound.roundProc, RoundingProcessOfExcessOutsideTime.class)));
		}
		return timeRounding;
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TimeRoundingOfExcessOutsideTime roundingSetOfMonthly) {

		// キー
		val companyId = roundingSetOfMonthly.getCompanyId();
		val key = new KrcstMonRoundSetPK(companyId);

		// 時間外超過の時間丸め
		boolean isNeedPersist = false;
		KrcstMonExcOutRound entityExcoutRound = this.getEntityManager().find(KrcstMonExcOutRound.class, key);
		if (entityExcoutRound == null){
			isNeedPersist = true;
			entityExcoutRound = new KrcstMonExcOutRound();
			entityExcoutRound.PK = key;
		}
		entityExcoutRound.roundUnit = roundingSetOfMonthly.getRoundingUnit().value;
		entityExcoutRound.roundProc = roundingSetOfMonthly.getRoundingProcess().value;
		if (isNeedPersist) this.getEntityManager().persist(entityExcoutRound);
	}
	
	/** 削除 */
	@Override
	public void remove(String companyId) {

		this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_EXCOUT)
				.setParameter("companyId", companyId)
				.executeUpdate();
	}

	@Override
	public void persistAndUpdateMonItemRound(List<ItemRoundingSetOfMonthly> lstItemRounding, String companyId) {
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
