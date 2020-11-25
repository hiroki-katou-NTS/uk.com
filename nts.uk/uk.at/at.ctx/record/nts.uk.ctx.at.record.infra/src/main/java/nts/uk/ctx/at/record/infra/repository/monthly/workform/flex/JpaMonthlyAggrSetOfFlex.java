package nts.uk.ctx.at.record.infra.repository.monthly.workform.flex;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.workform.flex.KrcstMonFlexAggrSet;
import nts.uk.ctx.at.record.infra.entity.monthly.workform.flex.KrcstMonFlexAggrSetPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlexRepository;

/**
 * リポジトリ実装：フレックス勤務の月別集計設定
 * @author shuichu_ishida
 */
@Stateless
public class JpaMonthlyAggrSetOfFlex extends JpaRepository implements MonthlyAggrSetOfFlexRepository {

	/** 検索 */
	@Override
	public Optional<MonthlyAggrSetOfFlex> find(String companyId) {
		
		return this.queryProxy()
				.find(new KrcstMonFlexAggrSetPK(companyId), KrcstMonFlexAggrSet.class)
				.map(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(MonthlyAggrSetOfFlex domain) {

		// キー
		val key = new KrcstMonFlexAggrSetPK(domain.getCompanyId());
		
		// 登録・更新
		KrcstMonFlexAggrSet entity = this.getEntityManager().find(KrcstMonFlexAggrSet.class, key);
		if (entity == null){
			entity = new KrcstMonFlexAggrSet();
			entity.fromDomainForPersist(domain);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.fromDomainForUpdate(domain);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String companyId) {

		this.commandProxy().remove(KrcstMonFlexAggrSet.class, new KrcstMonFlexAggrSetPK(companyId));
	}
}
