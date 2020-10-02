package nts.uk.ctx.at.shared.infra.repository.workrule.statutoryworktime.flex;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.workrule.statutoryworktime.flex.KshstFlxGetPrwkTime;
import nts.uk.ctx.at.shared.infra.entity.workrule.statutoryworktime.flex.KshstFlxGetPrwkTimePK;

/**
 * リポジトリ実装：フレックス勤務所定労働時間取得
 * @author shuichu_ishida
 */
@Stateless
public class JpaGetFlexPredWorkTime extends JpaRepository implements GetFlexPredWorkTimeRepository {

	/** 検索 */
	@Override
	public Optional<GetFlexPredWorkTime> find(String companyId) {
		
		return this.queryProxy()
				.find(new KshstFlxGetPrwkTimePK(companyId), KshstFlxGetPrwkTime.class)
				.map(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(GetFlexPredWorkTime domain) {

		// キー
		val key = new KshstFlxGetPrwkTimePK(domain.getCompanyId());
		
		// 登録・更新
		KshstFlxGetPrwkTime entity = this.getEntityManager().find(KshstFlxGetPrwkTime.class, key);
		if (entity == null){
			entity = new KshstFlxGetPrwkTime();
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

		this.commandProxy().remove(KshstFlxGetPrwkTime.class, new KshstFlxGetPrwkTimePK(companyId));
	}
}
