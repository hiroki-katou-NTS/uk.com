package nts.uk.ctx.at.record.infra.repository.monthlyaggrmethod.flex;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.flex.KshmtCalcMFlexCarMax;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimit;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.FlexShortageLimitRepository;

/**
 * リポジトリ実装：フレックス不足の繰越上限時間
 * @author shuichi_ishida
 */
@Stateless
public class JpaFlexShortageLimit extends JpaRepository implements FlexShortageLimitRepository {

	/** 取得 */
	@Override
	public Optional<FlexShortageLimit> get(String companyId) {
		return this.queryProxy()
				.find(companyId, KshmtCalcMFlexCarMax.class)
				.map(c -> c.toDomain());
	}
}