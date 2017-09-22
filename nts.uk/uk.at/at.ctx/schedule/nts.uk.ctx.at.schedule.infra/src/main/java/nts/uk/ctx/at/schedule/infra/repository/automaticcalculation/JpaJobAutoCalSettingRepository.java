package nts.uk.ctx.at.schedule.infra.repository.automaticcalculation;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoJobCalSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.autocalsetting.KshmtAutoJobCalSetPK;

@Stateless
public class JpaJobAutoCalSettingRepository extends JpaRepository implements JobAutoCalSettingRepository {
	@Override
	public void update(JobAutoCalSetting jobAutoCalSetting) {
		Optional<KshmtAutoJobCalSet> optional = this.queryProxy().find(
				new KshmtAutoJobCalSetPK(jobAutoCalSetting.getCompanyId().v(), jobAutoCalSetting.getJobId().v()),
				KshmtAutoJobCalSet.class);

		if (!optional.isPresent()) {
			throw new RuntimeException("Auto Job not existed.");
		}

		KshmtAutoJobCalSet entity = optional.get();
		jobAutoCalSetting.saveToMemento(new JpaJobAutoCalSettingSetMemento(entity));
		this.commandProxy().update(entity);
		
	}

	@Override
	public Optional<JobAutoCalSetting> getAllJobAutoCalSetting(String companyId, String jobId) {
		KshmtAutoJobCalSetPK kshmtAutoJobCalSetPK = new KshmtAutoJobCalSetPK(companyId, jobId);

		Optional<KshmtAutoJobCalSet> optKshmtAutoJobCalSet = this.queryProxy().find(kshmtAutoJobCalSetPK, KshmtAutoJobCalSet.class);

		if (!optKshmtAutoJobCalSet.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(new JobAutoCalSetting(new JpaJobAutoCalSettingGetMemento(optKshmtAutoJobCalSet.get())));
	}
	
	@Override
	public void delete(String cid, String jobId) {
		this.commandProxy().remove(KshmtAutoJobCalSet.class, new KshmtAutoJobCalSetPK(cid, jobId));

	}

}
