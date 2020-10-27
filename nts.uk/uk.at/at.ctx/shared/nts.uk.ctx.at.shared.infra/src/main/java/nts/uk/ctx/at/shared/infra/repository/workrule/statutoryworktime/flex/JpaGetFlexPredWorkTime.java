package nts.uk.ctx.at.shared.infra.repository.workrule.statutoryworktime.flex;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.workrule.statutoryworktime.flex.KshmtLegaltimeFlexCom;
import nts.uk.ctx.at.shared.infra.entity.workrule.statutoryworktime.flex.KshmtLegaltimeFlexComPK;

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
				.find(new KshmtLegaltimeFlexComPK(companyId), KshmtLegaltimeFlexCom.class)
				.map(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(GetFlexPredWorkTime domain) {

		// キー
		val key = new KshmtLegaltimeFlexComPK(domain.getCompanyId());
		
		// 登録・更新
		KshmtLegaltimeFlexCom entity = this.getEntityManager().find(KshmtLegaltimeFlexCom.class, key);
		if (entity == null){
			entity = new KshmtLegaltimeFlexCom();
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

		this.commandProxy().remove(KshmtLegaltimeFlexCom.class, new KshmtLegaltimeFlexComPK(companyId));
	}
}
