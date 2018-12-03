/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkpjob;

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
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.PositionAutoCalSettingExport;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingExport;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCal;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCalPK;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCalPK_;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkpjob.KshmtAutoWkpJobCal_;

/**
 * The Class JpaWkpJobAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpJobAutoCalSettingRepository extends JpaRepository implements WkpJobAutoCalSettingRepository {

	private static final String GET_ALL_WORKPLACE_JOB = "SELECT	DISTINCT "+
			"w.WKPCD, "+
			"w.WKP_NAME, "+
			"j.JOB_CD, "+
			"j.JOB_NAME, "+
			"k.EARLY_OT_TIME_LIMIT, " +
			"k.EARLY_MID_OT_TIME_LIMIT, "+
			"k.NORMAL_OT_TIME_LIMIT, "+
			"k.NORMAL_MID_OT_TIME_LIMIT, "+
			"k.LEGAL_OT_TIME_LIMIT,	"+
			"k.LEGAL_MID_OT_TIME_LIMIT,	"+
			"k.FLEX_OT_TIME_LIMIT,	"+
			"k.REST_TIME_LIMIT,	"+
			"k.LATE_NIGHT_TIME_LIMIT, "+
			"k.EARLY_OT_TIME_ATR, "+
			"k.EARLY_MID_OT_TIME_ATR,"+
			"k.NORMAL_OT_TIME_ATR, "+
			"k.NORMAL_MID_OT_TIME_ATR, "+
			"k.LEGAL_OT_TIME_ATR, "+
			"k.LEGAL_MID_OT_TIME_ATR, "+
			"k.FLEX_OT_TIME_ATR, "+
			"k.REST_TIME_ATR, "+
			"k.LATE_NIGHT_TIME_ATR, "+
			"k.RAISING_CALC_ATR, "+
			"k.SPECIFIC_RAISING_CALC_ATR, "+
			"k.LEAVE_EARLY, "+
			"k.LEAVE_LATE, "+
			"k.DIVERGENCE "+
			"FROM  KSHMT_AUTO_WKP_JOB_CAL k "+
			"INNER JOIN BSYMT_JOB_INFO j ON k.JOBID = j.JOB_ID AND k.CID = j.CID "+
			"INNER JOIN BSYMT_WORKPLACE_INFO w ON w.WKPID = k.WPKID AND k.CID = j.CID "+
			"WHERE k.CID = ?cid "+
			"ORDER BY w.WKPCD, j.JOB_CD ";

	@Override
	public List<WkpJobAutoCalSettingExport> getWkpJobSettingToExport(String cid) {
		List<Object[]> resultQuery = null;
		try {
			resultQuery = (List<Object[]>) this.getEntityManager().createNativeQuery(GET_ALL_WORKPLACE_JOB)
					.setParameter("cid", cid)
					.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		return resultQuery.stream().map(item -> convertToExport(item)).collect(Collectors.toList());
	}

	private WkpJobAutoCalSettingExport convertToExport(Object[] obj){
		return new WkpJobAutoCalSettingExport(
				obj[0].toString(),
				obj[1].toString(),
				obj[2].toString(),
				obj[3].toString(),
				((BigDecimal) obj[4]).intValue(),
				((BigDecimal) obj[5]).intValue(),
				((BigDecimal) obj[6]).intValue(),
				((BigDecimal) obj[7]).intValue(),
				((BigDecimal) obj[8]).intValue(),
				((BigDecimal) obj[9]).intValue(),
				((BigDecimal) obj[10]).intValue(),
				((BigDecimal) obj[11]).intValue(),
				((BigDecimal) obj[12]).intValue(),
				((BigDecimal) obj[13]).intValue(),
				((BigDecimal) obj[14]).intValue(),
				((BigDecimal) obj[15]).intValue(),
				((BigDecimal) obj[16]).intValue(),
				((BigDecimal) obj[17]).intValue(),
				((BigDecimal) obj[18]).intValue(),
				((BigDecimal) obj[19]).intValue(),
				((BigDecimal) obj[20]).intValue(),
				((BigDecimal) obj[21]).intValue(),
				((BigDecimal) obj[22]).intValue(),
				((BigDecimal) obj[23]).intValue(),
				((BigDecimal) obj[24]).intValue(),
				((BigDecimal) obj[25]).intValue(),
				((BigDecimal) obj[26]).intValue());
	}

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
	private KshmtAutoWkpJobCal toEntity(WkpJobAutoCalSetting wkpJobAutoCalSetting) {
		Optional<KshmtAutoWkpJobCal> optinal = this.queryProxy()
				.find(new KshmtAutoWkpJobCalPK(wkpJobAutoCalSetting.getCompanyId().v(),
						wkpJobAutoCalSetting.getJobId().v(), wkpJobAutoCalSetting.getJobId().v()),
						KshmtAutoWkpJobCal.class);
		KshmtAutoWkpJobCal entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KshmtAutoWkpJobCal();
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
		KshmtAutoWkpJobCalPK kshmtAutoJobCalSetPK = new KshmtAutoWkpJobCalPK(companyId, wkpId, jobId);

		Optional<KshmtAutoWkpJobCal> optKshmtAutoWkpJobCal = this.queryProxy().find(kshmtAutoJobCalSetPK,
				KshmtAutoWkpJobCal.class);

		if (!optKshmtAutoWkpJobCal.isPresent()) {
			return Optional.empty();
		}

		return Optional
				.of(new WkpJobAutoCalSetting(new JpaWkpJobAutoCalSettingGetMemento(optKshmtAutoWkpJobCal.get())));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository#delete(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId, String jobId) {
		this.commandProxy().remove(KshmtAutoWkpJobCal.class, new KshmtAutoWkpJobCalPK(cid, wkpId, jobId));

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
		CriteriaQuery<KshmtAutoWkpJobCal> cq = builder.createQuery(KshmtAutoWkpJobCal.class);
		Root<KshmtAutoWkpJobCal> root = cq.from(KshmtAutoWkpJobCal.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KshmtAutoWkpJobCal_.kshmtAutoWkpJobCalPK)
				.get(KshmtAutoWkpJobCalPK_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return em.createQuery(cq).getResultList().stream()
				.map(entity -> new WkpJobAutoCalSetting(new JpaWkpJobAutoCalSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
