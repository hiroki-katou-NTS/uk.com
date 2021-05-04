package nts.uk.ctx.at.record.infra.repository.jobmanagement.workchangeableperiodsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecordreferencesetting.ManHourRecordReferenceSetting;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecordreferencesetting.ManHourRecordReferenceSettingRepository;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.workchangeableperiodsetting.KrcmtTaskPastPeriod;

@Stateless
public class JpaManHourRecordReferenceSettingRepository extends JpaRepository implements ManHourRecordReferenceSettingRepository{

	@Override
	public void insert(ManHourRecordReferenceSetting domain) {
		this.commandProxy().insert(new KrcmtTaskPastPeriod(domain));
	}

	@Override
	public void delete(ManHourRecordReferenceSetting domain) {
		this.commandProxy().remove(new KrcmtTaskPastPeriod(domain));
	}

	@Override
	public void update(ManHourRecordReferenceSetting domain) {
		this.commandProxy().update(new KrcmtTaskPastPeriod(domain));
	}

	@Override
	public Optional<ManHourRecordReferenceSetting> get(String companyId) {
		return this.queryProxy().find(companyId, KrcmtTaskPastPeriod.class).map(c->c.toDomain());
	}

}
