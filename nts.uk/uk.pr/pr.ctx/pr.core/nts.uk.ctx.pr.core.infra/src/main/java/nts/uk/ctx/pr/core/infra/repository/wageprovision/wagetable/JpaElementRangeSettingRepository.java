package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtElementRangeSetting;

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
	public Optional<ElementRangeSetting> getElementRangeSettingById(String historyId) {
		Optional<QpbmtElementRangeSetting> optEntity = this.queryProxy().find(historyId,
				QpbmtElementRangeSetting.class);
		return optEntity.isPresent() ? Optional.of(optEntity.get().toDomain()) : Optional.empty();
	}

	@Override
	public void add(ElementRangeSetting domain) {
		this.commandProxy().insert(QpbmtElementRangeSetting.fromDomain(domain));
	}

	@Override
	public void update(ElementRangeSetting domain) {
		this.commandProxy().update(QpbmtElementRangeSetting.fromDomain(domain));
	}

	@Override
	public void remove(String historyId) {
		// TODO Auto-generated method stub

	}

}
