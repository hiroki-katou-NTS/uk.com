package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtElementRangeSetting;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTableHistoryPk;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaElementRangeSettingRepository extends JpaRepository implements ElementRangeSettingRepository {

	@Override
	public List<ElementRangeSetting> getAllElementRangeSetting(List<String> historyIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ElementRangeSetting> getElementRangeSettingById(String historyId, String companyId,
			String wageTableCode) {
		Optional<QpbmtElementRangeSetting> optEntity = this.queryProxy()
				.find(new QpbmtWageTableHistoryPk(companyId, wageTableCode, historyId), QpbmtElementRangeSetting.class);
		return optEntity.isPresent() ? Optional.of(optEntity.get().toDomain()) : Optional.empty();
	}

	@Override
	public void add(ElementRangeSetting domain, String companyId, String wageTableCode) {
		this.commandProxy().insert(QpbmtElementRangeSetting.fromDomain(domain, companyId, wageTableCode));
	}

	@Override
	public void update(ElementRangeSetting domain, String companyId, String wageTableCode) {
		this.commandProxy().update(QpbmtElementRangeSetting.fromDomain(domain, companyId, wageTableCode));
	}

	@Override
	public void remove(String historyId, String companyId, String wageTableCode) {
		this.commandProxy().remove(QpbmtElementRangeSetting.class,
				new QpbmtWageTableHistoryPk(companyId, wageTableCode, historyId));
	}

}
