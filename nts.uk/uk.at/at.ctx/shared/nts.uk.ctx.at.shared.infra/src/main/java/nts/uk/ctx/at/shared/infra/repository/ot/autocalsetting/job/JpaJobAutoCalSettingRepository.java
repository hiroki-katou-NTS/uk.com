/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.PositionAutoCalSettingExport;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KshmtAutoJobCalSet;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KshmtAutoJobCalSetPK;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KshmtAutoJobCalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.job.KshmtAutoJobCalSet_;

/**
 * The Class JpaJobAutoCalSettingRepository.
 */
@Stateless
public class JpaJobAutoCalSettingRepository extends JpaRepository implements JobAutoCalSettingRepository {

	private static final String SELECT_ALL_POSITION_BY_CID = " SELECT " +
			"w.JOB_CD, " +
			"w.JOB_NAME, " +
			"CASE  WHEN EARLY_OT_TIME_LIMIT IS NULL then 'NO' ELSE 'YES'  END as REGISTERED, " +
			"k.EARLY_OT_TIME_LIMIT, " +
			"k.EARLY_MID_OT_TIME_LIMIT, " +
			"k.NORMAL_OT_TIME_LIMIT, " +
			"k.NORMAL_MID_OT_TIME_LIMIT, " +
			"k.LEGAL_OT_TIME_LIMIT, " +
			"k.LEGAL_MID_OT_TIME_LIMIT, " +
			"k.FLEX_OT_TIME_LIMIT, " +
			"k.REST_TIME_LIMIT, " +
			"k.LATE_NIGHT_TIME_LIMIT, " +
			"k.EARLY_OT_TIME_ATR, " +
			"k.EARLY_MID_OT_TIME_ATR, " +
			"k.NORMAL_OT_TIME_ATR, " +
			"k.NORMAL_MID_OT_TIME_ATR, " +
			"k.LEGAL_OT_TIME_ATR, " +
			"k.LEGAL_MID_OT_TIME_ATR, " +
			"k.FLEX_OT_TIME_ATR, " +
			"k.REST_TIME_ATR, " +
			"k.LATE_NIGHT_TIME_ATR, " +
			"k.RAISING_CALC_ATR, " +
			"k.SPECIFIC_RAISING_CALC_ATR, " +
			"k.LEAVE_EARLY, " +
			"k.LEAVE_LATE, " +
			"k.DIVERGENCE " +
			"FROM BSYMT_JOB_INFO w LEFT JOIN KSHMT_AUTO_JOB_CAL_SET k on w.JOB_ID = k.JOBID  AND w.CID = k.CID " +
			"WHERE w.CID = ?cid " +
			"ORDER BY w.JOB_CD";

	@Override
	public List<PositionAutoCalSettingExport> getPositionSettingToExport(String cid) {
		List<Object[]> resultQuery = null;
		try {
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(SELECT_ALL_POSITION_BY_CID)
					.setParameter("cid", cid)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery.stream().map(item -> convertToExport(item)).collect(Collectors.toList());
	}

	private PositionAutoCalSettingExport convertToExport(Object[] obj){
		return new PositionAutoCalSettingExport(
				obj[0].toString(),
				obj[1].toString(),
				obj[2].toString(),
				obj[3] == null ? 0 : ((BigDecimal) obj[3]).intValue(),
				obj[4] == null ? 0 : ((BigDecimal) obj[4]).intValue(),
				obj[5] == null ? 0 : ((BigDecimal) obj[5]).intValue(),
				obj[6] == null ? 0 : ((BigDecimal) obj[6]).intValue(),
				obj[7] == null ? 0 : ((BigDecimal) obj[7]).intValue(),
				obj[8] == null ? 0 : ((BigDecimal) obj[8]).intValue(),
				obj[9] == null ? 0 : ((BigDecimal) obj[9]).intValue(),
				obj[10] == null ? 0 : ((BigDecimal) obj[10]).intValue(),
				obj[11] == null ? 0 : ((BigDecimal) obj[11]).intValue(),
				obj[12] == null ? 0 : ((BigDecimal) obj[12]).intValue(),
				obj[13] == null ? 0 : ((BigDecimal) obj[13]).intValue(),
				obj[14] == null ? 0 : ((BigDecimal) obj[14]).intValue(),
				obj[15] == null ? 0 : ((BigDecimal) obj[15]).intValue(),
				obj[16] == null ? 0 : ((BigDecimal) obj[16]).intValue(),
				obj[17] == null ? 0 : ((BigDecimal) obj[17]).intValue(),
				obj[18] == null ? 0 : ((BigDecimal) obj[18]).intValue(),
				obj[19] == null ? 0 : ((BigDecimal) obj[19]).intValue(),
				obj[20] == null ? 0 : ((BigDecimal) obj[20]).intValue(),
				obj[21] == null ? 0 : ((BigDecimal) obj[21]).intValue(),
				obj[22] == null ? 0 : ((BigDecimal) obj[22]).intValue(),
				obj[23] == null ? 0 : ((BigDecimal) obj[23]).intValue(),
				obj[24] == null ? 0 : ((BigDecimal) obj[24]).intValue(),
				obj[25] == null ? 0 : ((BigDecimal) obj[25]).intValue());
	}
	
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
	private KshmtAutoJobCalSet toEntity(JobAutoCalSetting jobAutoCalSetting) {
		Optional<KshmtAutoJobCalSet> optinal = this.queryProxy().find(
				new KshmtAutoJobCalSetPK(jobAutoCalSetting.getCompanyId().v(), jobAutoCalSetting.getJobId().v()),
				KshmtAutoJobCalSet.class);
		KshmtAutoJobCalSet entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KshmtAutoJobCalSet();
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
		KshmtAutoJobCalSetPK kshmtAutoJobCalSetPK = new KshmtAutoJobCalSetPK(companyId, jobId);

		Optional<KshmtAutoJobCalSet> optKshmtAutoJobCalSet = this.queryProxy().find(kshmtAutoJobCalSetPK,
				KshmtAutoJobCalSet.class);

		if (!optKshmtAutoJobCalSet.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new JobAutoCalSetting(new JpaJobAutoCalSettingGetMemento(optKshmtAutoJobCalSet.get())));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.JobAutoCalSettingRepository#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String jobId) {
		this.commandProxy().remove(KshmtAutoJobCalSet.class, new KshmtAutoJobCalSetPK(cid, jobId));

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
		CriteriaQuery<KshmtAutoJobCalSet> cq = builder.createQuery(KshmtAutoJobCalSet.class);
		Root<KshmtAutoJobCalSet> root = cq.from(KshmtAutoJobCalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KshmtAutoJobCalSet_.kshmtAutoJobCalSetPK)
				.get(KshmtAutoJobCalSetPK_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new JobAutoCalSetting(new JpaJobAutoCalSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
