/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KrcmtCalcSetJob;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KrcmtCalcSetJobPK;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KrcmtCalcSetJobPK_;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KrcmtCalcSetJob_;

/**
 * The Class JpaJobAutoCalSettingRepository.
 */
@Stateless
public class JpaJobAutoCalSettingRepository extends JpaRepository implements JobAutoCalSettingRepository {

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSetting)
	 */
	@Override
	public void update(JobAutoCalSetting jobAutoCalSetting) {
		this.commandProxy().update(this.toEntity(jobAutoCalSetting));
		this.getEntityManager().flush();

	}

	/**
	 * To entity.
	 *
	 * @param jobAutoCalSetting the job auto cal setting
	 * @return the kshmt auto job cal set
	 */
	private KrcmtCalcSetJob toEntity(JobAutoCalSetting jobAutoCalSetting) {
		Optional<KrcmtCalcSetJob> optinal = this.queryProxy().find(
				new KrcmtCalcSetJobPK(jobAutoCalSetting.getCompanyId().v(), jobAutoCalSetting.getJobId().v()),
				KrcmtCalcSetJob.class);
		KrcmtCalcSetJob entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KrcmtCalcSetJob();
		}
		JpaJobAutoCalSettingSetMemento memento = new JpaJobAutoCalSettingSetMemento(entity);
		jobAutoCalSetting.saveToMemento(memento);
		return entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingRepository#getAllJobAutoCalSetting(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<JobAutoCalSetting> getJobAutoCalSetting(String companyId, String jobId) {
		KrcmtCalcSetJobPK krcmtCalcSetJobPK = new KrcmtCalcSetJobPK(companyId, jobId);

		Optional<KrcmtCalcSetJob> optKrcmtCalcSetJob = this.queryProxy().find(krcmtCalcSetJobPK,
				KrcmtCalcSetJob.class);

		if (!optKrcmtCalcSetJob.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new JobAutoCalSetting(new JpaJobAutoCalSettingGetMemento(optKrcmtCalcSetJob.get())));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingRepository#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String jobId) {
		this.commandProxy().remove(KrcmtCalcSetJob.class, new KrcmtCalcSetJobPK(cid, jobId));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingRepository#add(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSetting)
	 */
	@Override
	public void add(JobAutoCalSetting jobAutoCalSetting) {
		this.commandProxy().insert(this.toEntity(jobAutoCalSetting));
		this.getEntityManager().flush();

	}
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSettingRepository#getAllJobAutoCalSetting(java.lang.String)
	 */
	@Override
	public List<JobAutoCalSetting> getAllJobAutoCalSetting(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtCalcSetJob> cq = builder.createQuery(KrcmtCalcSetJob.class);
		Root<KrcmtCalcSetJob> root = cq.from(KrcmtCalcSetJob.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KrcmtCalcSetJob_.krcmtCalcSetJobPK)
				.get(KrcmtCalcSetJobPK_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new JobAutoCalSetting(new JpaJobAutoCalSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
