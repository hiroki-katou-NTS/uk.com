package nts.uk.ctx.at.record.infra.repository.jobmanagement.workchangeableperiodsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.WorkChangeablePeriodSetting;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.WorkChangeablePeriodSettingRepository;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.workchangeableperiodsetting.KrcmtTaskPastPeriod;

@Stateless
public class JpaWorkChangeablePeriodSettingRepository extends JpaRepository implements WorkChangeablePeriodSettingRepository{

	@Override
	public void insert(WorkChangeablePeriodSetting domain) {
		this.commandProxy().insert(new KrcmtTaskPastPeriod(domain));
	}

	@Override
	public void delete(WorkChangeablePeriodSetting domain) {
		this.commandProxy().remove(new KrcmtTaskPastPeriod(domain));
	}

	@Override
	public void update(WorkChangeablePeriodSetting domain) {
		this.commandProxy().update(new KrcmtTaskPastPeriod(domain));
	}

	@Override
	public Optional<WorkChangeablePeriodSetting> get(String companyId) {
		return this.queryProxy().find(companyId, KrcmtTaskPastPeriod.class).map(c->c.toDomain());
	}

}
