package nts.uk.ctx.at.record.infra.repository.monthly.mergetable;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMergePk;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonRemain;

/**
 * リポジトリ実装：残数系
 * @author shuichi_ishida
 */
@Stateless
public class JpaRemainMerge extends JpaRepository implements RemainMergeRepository {

	/** 検索 */
	@Override
	public Optional<RemainMerge> find(MonthMergeKey key) {
		
		return this.queryProxy()
				.find(new KrcdtMonMergePk(
						key.getEmployeeId(),
						key.getYearMonth().v(),
						key.getClosureId().value,
						key.getClosureDate().getClosureDay().v(),
						(key.getClosureDate().getLastDayOfMonth() ? 1 : 0)),
						KrcdtMonRemain.class)
				.map(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(MonthMergeKey key, RemainMerge domains) {
		
		// キー
		val entityKey = new KrcdtMonMergePk(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		
		// 登録・更新
		KrcdtMonRemain entity = this.getEntityManager().find(KrcdtMonRemain.class, entityKey);
		if (entity == null){
			entity = new KrcdtMonRemain();
			entity.setKrcdtMonRemainPk(entityKey);
			entity.toEntityRemainMerge(domains);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.toEntityRemainMerge(domains);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(MonthMergeKey key) {
		
		this.commandProxy().remove(KrcdtMonRemain.class,
				new KrcdtMonMergePk(
						key.getEmployeeId(),
						key.getYearMonth().v(),
						key.getClosureId().value,
						key.getClosureDate().getClosureDay().v(),
						(key.getClosureDate().getLastDayOfMonth() ? 1 : 0)));
	}
}
