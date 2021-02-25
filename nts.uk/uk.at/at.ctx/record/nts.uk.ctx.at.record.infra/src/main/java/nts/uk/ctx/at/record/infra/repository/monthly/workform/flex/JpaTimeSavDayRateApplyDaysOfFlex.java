package nts.uk.ctx.at.record.infra.repository.monthly.workform.flex;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.workform.flex.KrcstMonFlexApplyDays;
import nts.uk.ctx.at.record.infra.entity.monthly.workform.flex.KrcstMonFlexApplyDaysPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.TSDRApplyDaysOfFlexRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.TimeSavDayRateApplyDaysOfFlex;

/**
 * リポジトリ実装：フレックス勤務の時短日割適用日数
 * @author shuichu_ishida
 */
@Stateless
public class JpaTimeSavDayRateApplyDaysOfFlex extends JpaRepository implements TSDRApplyDaysOfFlexRepository {

	/** 検索 */
	@Override
	public Optional<TimeSavDayRateApplyDaysOfFlex> find(String companyId) {
		
		return this.queryProxy()
				.find(new KrcstMonFlexApplyDaysPK(companyId), KrcstMonFlexApplyDays.class)
				.map(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(TimeSavDayRateApplyDaysOfFlex domain) {

		// キー
		val key = new KrcstMonFlexApplyDaysPK(domain.getCompanyId());
		
		// 登録・更新
		KrcstMonFlexApplyDays entity = this.getEntityManager().find(KrcstMonFlexApplyDays.class, key);
		if (entity == null){
			entity = new KrcstMonFlexApplyDays();
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

		this.commandProxy().remove(KrcstMonFlexApplyDays.class, new KrcstMonFlexApplyDaysPK(companyId));
	}
}
