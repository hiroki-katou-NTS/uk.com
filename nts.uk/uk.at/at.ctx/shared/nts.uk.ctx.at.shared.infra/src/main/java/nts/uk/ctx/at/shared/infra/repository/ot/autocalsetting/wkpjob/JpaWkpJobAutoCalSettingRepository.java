/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkpjob;

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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KrcmtCalcSetWkpJob;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KrcmtCalcSetWkpJobPK;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KrcmtCalcSetWkpJobPK_;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KrcmtCalcSetWkpJob_;

/**
 * The Class JpaWkpJobAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpJobAutoCalSettingRepository extends JpaRepository implements WkpJobAutoCalSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpJobAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * autocalsetting.WkpJobAutoCalSetting)
	 */
	@Override
	public void update(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		this.commandProxy().update(this.toEntity(wkpJobAutoCalSetting));
		this.getEntityManager().flush();
	}
	
	/**
	 * To entity.
	 *
	 * @param wkpJobAutoCalSetting the wkp job auto cal setting
	 * @return the kshmt auto wkp job cal
	 */
	private KrcmtCalcSetWkpJob toEntity(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		Optional<KrcmtCalcSetWkpJob> optinal = this.queryProxy()
				.find(new KrcmtCalcSetWkpJobPK(wkpJobAutoCalSetting.getCompanyId().v(),
						wkpJobAutoCalSetting.getJobId().v(), wkpJobAutoCalSetting.getJobId().v()),
						KrcmtCalcSetWkpJob.class);
		KrcmtCalcSetWkpJob entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KrcmtCalcSetWkpJob();
		}
		JpaWkpJobAutoCalSettingSetMemento memento = new JpaWkpJobAutoCalSettingSetMemento(entity);
		wkpJobAutoCalSetting.saveToMemento(memento);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpJobAutoCalSettingRepository#getAllWkpJobAutoCalSetting(java.lang.
	 * String, java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpJobAutoCalSetting> getWkpJobAutoCalSetting(String companyId, String wkpId, String jobId) {
		KrcmtCalcSetWkpJobPK krcmtCalcSetJobPK = new KrcmtCalcSetWkpJobPK(companyId, wkpId, jobId);

		Optional<KrcmtCalcSetWkpJob> optKrcmtCalcSetWkpJob = this.queryProxy().find(krcmtCalcSetJobPK,
				KrcmtCalcSetWkpJob.class);

		if (!optKrcmtCalcSetWkpJob.isPresent()) {
			return Optional.empty();
		}

		return Optional
				.of(new WkpJobAutoCalSetting(new JpaWkpJobAutoCalSettingGetMemento(optKrcmtCalcSetWkpJob.get())));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository#delete(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId, String jobId) {
		this.commandProxy().remove(KrcmtCalcSetWkpJob.class, new KrcmtCalcSetWkpJobPK(cid, wkpId, jobId));

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository#add(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSetting)
	 */
	@Override
	public void add(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		this.commandProxy().insert(this.toEntity(wkpJobAutoCalSetting));
		this.getEntityManager().flush();
		
	}
	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository#getAllWkpJobAutoCalSetting(java.lang.String)
	 */
	@Override
	public List<WkpJobAutoCalSetting> getAllWkpJobAutoCalSetting(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtCalcSetWkpJob> cq = builder.createQuery(KrcmtCalcSetWkpJob.class);
		Root<KrcmtCalcSetWkpJob> root = cq.from(KrcmtCalcSetWkpJob.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KrcmtCalcSetWkpJob_.krcmtCalcSetWkpJobPK)
				.get(KrcmtCalcSetWkpJobPK_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new WkpJobAutoCalSetting(new JpaWkpJobAutoCalSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
